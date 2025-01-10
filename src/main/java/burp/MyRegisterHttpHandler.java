package burp;

import burp.api.montoya.core.Annotations;
import burp.api.montoya.http.handler.*;
import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.http.message.responses.HttpResponse;
import help.StringUtil;
import jrcet.frame.Scanner.Overauth.Overauth;
import jrcet.frame.Scanner.Overauth.OverauthTableEntry;

import javax.swing.*;

import java.util.Objects;

import static burp.MyExtender.BurpAPI;
import static help.Similarity.StringSimilarity.similarity;
import static jrcet.frame.Scanner.Fastjson.Fastjson.*;
import static jrcet.frame.Scanner.Javascript.Javascript.*;
import static jrcet.frame.Scanner.Overauth.Overauth.*;
import static jrcet.frame.Scanner.Springboot.Springboot.*;

public class MyRegisterHttpHandler implements HttpHandler {

    public String[] BlackExtensionList = new String[]{
            "png","jpg","jpeg","gif","txt","html","pdf","xls","xlsx","word","ppt","zip","xml","gif","css","svg","otf","woff","woff2","ico","tff","js"
    };



    public MyRegisterHttpHandler() throws Exception {
        Class.forName("org.sqlite.JDBC");
    }

    @Override
    public RequestToBeSentAction handleHttpRequestToBeSent(HttpRequestToBeSent requestToBeSent) {

        String requestUrl = requestToBeSent.url();
        String requestPath = requestToBeSent.path().split("\\?")[0];

        //不接受Extensions和Intruder的流量
        String requestTool = requestToBeSent.toolSource().toolType().toolName();
        switch (requestTool) {
            case "Extensions", "Intruder" -> {
                return RequestToBeSentAction.continueWith(requestToBeSent);
            }
        }

        //不接受OPTIONS请求
        if(Objects.equals(requestToBeSent.method(), "OPTIONS")){
            return RequestToBeSentAction.continueWith(requestToBeSent);
        };

        //不接受静态文件的请求
        for(String ext: BlackExtensionList){
            if(requestPath.endsWith(ext)){
                return RequestToBeSentAction.continueWith(requestToBeSent);
            }
        }

        //javascript检查
        if(requestPath.endsWith("js")){
            if( JavascriptCheck && !JavascriptCheckedUrlList.contains(requestUrl)){
                return RequestToBeSentAction.continueWith(
                        requestToBeSent.withRemovedHeader("If-Modified-Since"),
                        requestToBeSent.annotations().withNotes(JAVASCRIPT+"¥¥"+requestUrl)
                );
            }
            return RequestToBeSentAction.continueWith(requestToBeSent);
        }

        //sprintboot检查
        String springbootNote = "";
        if( SpringbootCheck && !SpringbootCheckedUrlList.contains(requestUrl)){
            springbootNote = springbootCheckRequest(requestToBeSent);
        }


        //fastjson检查
        String fastjsonNote = "";

        if(FastjsonCheck && !FastjsonCheckUrlList.contains(requestUrl)){
            fastjsonNote = fastjsonCheckRequest(requestToBeSent);
        }

        //Overauth检查过的Url
        String authCheckNote = "";
        if(OverauthCheck){
            authCheckNote = Overauth.authCheckRequest(requestToBeSent);
        }

        Annotations requestAnnotations = requestToBeSent.annotations().withNotes(
                fastjsonNote+","+authCheckNote+","+springbootNote
        );

        return RequestToBeSentAction.continueWith(requestToBeSent, requestAnnotations);
    }

    @Override
    public ResponseReceivedAction handleHttpResponseReceived(HttpResponseReceived responseReceived) {

        String requestNotes = responseReceived.annotations().notes();

        if(requestNotes!=null && requestNotes.startsWith(JAVASCRIPT+"¥¥")){
            javascriptCheckResponse(responseReceived);
            return ResponseReceivedAction.continueWith(responseReceived);
        }

        //判断是否为Fastjson期望的返回包
        String fastjsonRequestNumber = getRequestNumber(FASTJSON, requestNotes);
        if(!fastjsonRequestNumber.equals("")){
            fastjsonCheckResponse(responseReceived,fastjsonRequestNumber);
        }

        //判断是否为OverAuth期望的返回包
        String overAuthRequestNumber = getRequestNumber(Overauth.AUTH, requestNotes);
        if(!overAuthRequestNumber.equals("") && !overAuthRequestNumber.equals("null")){
            Overauth.authCheckResponse(responseReceived,overAuthRequestNumber);
        }

        //判断是否为Sprintboot期望的返回包
        String springbootRequestNumber = getRequestNumber(SPRINGBOOT, requestNotes);
        if(!StringUtil.isBlank(springbootRequestNumber)){
            springbootCheckResponse(responseReceived,springbootRequestNumber);
        }

        return ResponseReceivedAction.continueWith(responseReceived);
    }

    public static class checkWorker extends SwingWorker<Void, Void> {
        public String RequestNumber;
        public String Type;
        public HttpRequest httpRequest;
        public checkWorker(String Type, String RequestNumber, HttpRequest httpRequest) {
            this.Type = Type;
            this.RequestNumber = RequestNumber;
            this.httpRequest = httpRequest;
        }
        @Override
        protected Void doInBackground() {

            HttpRequestResponse httpRequestResponse = BurpAPI.http().sendRequest(httpRequest);

            HttpResponse httpResponse = httpRequestResponse.response();
            int    responseLength = httpResponse.body().length();

            String result = "";


            switch (Type) {
                case "UnAuth","LowAuth" -> {

                    String RequestPath = OverAuthPathMap.get(RequestNumber);
                    OverauthTableEntry overauthTableEntry = OverAuthTableEntryMap.get(RequestPath);
                    HttpResponse HighAuthResponse = overauthTableEntry.getHighAuthResponse();
                    double similarity = similarity(HighAuthResponse.bodyToString(), httpResponse.bodyToString());
                    if (similarity >= 0.9 && similarity <= 1.0) {
                        result = "True";
                    }
                    int rowIndex = getRowByRequestNumber(RequestNumber);
                    if(Type.equals("UnAuth")){
                        overauthTableEntry.setUnAuth(result);
                        overauthTableEntry.setUnAuthResponse(httpResponse);
                        Overauth.setTableValueAt(result, rowIndex, "UnAuth");
                    }else{
                        overauthTableEntry.setLowAuth(result);
                        overauthTableEntry.setLowAuthResponse(httpResponse);
                        Overauth.setTableValueAt(result, rowIndex, "LowAuth");
                    }
                }

                case "fastjson" -> {
                    HttpResponse ihttpResponse = responseLength<5000?httpResponse:httpResponse.withBody(httpResponse.body().subArray(0, 4999));
                    FastjsonLoggerTableEntryMap.get(RequestNumber).setFastjsonRequest(httpRequest);
                    FastjsonLoggerTableEntryMap.get(RequestNumber).setFastjsonResponse(ihttpResponse);
                    break;
                }
            }


            return null;
        }

    }

    public static String getRequestNumber(String type,String notes){
        if(StringUtil.isBlank(notes)){
            return "";
        }
        String[] noteArray = notes.split(",");
        for(String note: noteArray){
            if(note.startsWith(type)){
                return note.replace(type,"");
            }
        }
        return "";
    }
}
