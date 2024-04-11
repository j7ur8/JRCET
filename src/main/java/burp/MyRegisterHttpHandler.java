package burp;

import burp.api.montoya.core.Annotations;
import burp.api.montoya.http.handler.*;
import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.http.message.MimeType;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.http.message.responses.HttpResponse;
import help.StringUtil;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import static burp.MyExtender.BurpAPI;
import static jrcet.frame.Scanner.Fastjson.Fastjson.*;
import static jrcet.frame.Scanner.Javascript.Javascript.*;
import static jrcet.frame.Scanner.Overauth.Overauth.*;
import static jrcet.frame.Scanner.Springboot.Springboot.*;
import static jrcet.frame.Scanner.Springboot.Springboot.setSpringbootLoggerTableValueAt;
import static help.Similarity.StringSimilarity.similarity;

public class MyRegisterHttpHandler implements HttpHandler {

    public String[] BlackExtensionList = new String[]{
            "png","jpg","jpeg","gif","txt","html","pdf","xls","xlsx","word","ppt","zip","xml","gif","css","svg","otf","woff","woff2","ico","tff"
    };

    @Override
    public RequestToBeSentAction handleHttpRequestToBeSent(HttpRequestToBeSent requestToBeSent) {

        //不接受Extensions和Intruder的流量
        String requestTool = requestToBeSent.toolSource().toolType().toolName();
        switch (requestTool) {
            case "Extensions", "Intruder" -> {
                return RequestToBeSentAction.continueWith(requestToBeSent);
            }
        }


        //不接受静态文件的请求
        String requestPath = requestToBeSent.path().split("\\?")[0];
        for(String ext: BlackExtensionList){
            if(requestPath.endsWith(ext)){
                return RequestToBeSentAction.continueWith(requestToBeSent);
            }
        }
        
        String requestUrl = requestToBeSent.url();

        //javascript检查过的url
        String javascriptNote = "";

        if(requestPath.endsWith("js")){
            if( JavascriptCheck && !JavascriptCheckedUrlList.contains(requestUrl)){
                javascriptNote = JAVASCRIPT;
                HttpRequest httpRequest = requestToBeSent.withRemovedHeader("If-Modified-Since");

                Annotations requestAnnotations = requestToBeSent.annotations().withNotes(
                        javascriptNote+"¥¥"+requestUrl
                );
                return RequestToBeSentAction.continueWith(httpRequest, requestAnnotations);
            }
            return RequestToBeSentAction.continueWith(requestToBeSent);
        }

        //sprintboot检查过的url
        String springbootNote = "";

        if( SpringbootCheck && !SpringbootCheckedUrlList.contains(requestUrl)){
            springbootNote = springbootCheckRequest(requestToBeSent);
        }


        //fastjson检查过的url
        String fastjsonNote = "";

        if(FastjsonCheck && !FastjsonCheckUrlList.contains(requestUrl)){
            fastjsonNote = fastjsonCheckRequest(requestToBeSent);
        }

        //Overauth检查过的Url
        String authCheckNote = "";
        if(OverauthCheck && !OverauthCheckUrlList.contains(requestUrl)){
            authCheckNote = authCheckRequest(requestToBeSent);
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
        String overAuthRequestNumber = getRequestNumber(AUTH, requestNotes);
        if(!overAuthRequestNumber.equals("")){
            authCheckResponse(responseReceived,overAuthRequestNumber);
        }

        //判断是否为Sprintboot期望的返回包
        String springbootRequestNumber = getRequestNumber(SPRINGBOOT, requestNotes);
        if(!StringUtil.isBlank(springbootRequestNumber)){
            springbootCheckResponse(responseReceived,springbootRequestNumber);
        }

        return ResponseReceivedAction.continueWith(responseReceived);
    }



    public static class checkWorker extends SwingWorker<Void, Void> {
        public String number;
        public String type;
        public HttpRequest httpRequest;

        public checkWorker(String type, String number, HttpRequest httpRequest) {
            this.type = type;
            this.number = number;
            this.httpRequest = httpRequest;
        }
        @Override
        protected Void doInBackground() {

            HttpRequestResponse httpRequestResponse = BurpAPI.http().sendRequest(httpRequest);

            HttpRequest  ihttpRequest = httpRequestResponse.request();
            HttpResponse httpResponse = httpRequestResponse.response();
            int responseLength = httpResponse.body().length();
            HttpResponse ihttpResponse = responseLength<5000?httpResponse:httpResponse.withBody(httpResponse.body().subArray(0, 4999));

            String responseCode = Integer.toString(httpResponse.statusCode());

            int rowIndex;
            switch (type) {
                case "unAuth" -> {
                    rowIndex = OverauthLoggerTableEntryMap.get(number).getRowIndex();

                    OverauthLoggerTableEntryMap.get(number).setUnAuthRequest(ihttpRequest);
                    OverauthLoggerTableEntryMap.get(number).setUnAuthResponse(ihttpResponse);

                    double unAuthSimilarity = similarity(OverauthLoggerTableEntryMap.get(number).getHighAuthResponse().bodyToString(), ihttpResponse.bodyToString());

                    if (unAuthSimilarity >= 0.9 && unAuthSimilarity <=1.0) {
                        OverauthLoggerTableEntryMap.get(number).setUnAuth("True");
                        setOverauthLoggerTableValueAt("True", rowIndex, "UnAuth");
                    }
                }
                case "lowAuth" -> {
                    rowIndex = OverauthLoggerTableEntryMap.get(number).getRowIndex();

                    OverauthLoggerTableEntryMap.get(number).setLowAuthRequest(ihttpRequest);
                    OverauthLoggerTableEntryMap.get(number).setLowAuthResponse(ihttpResponse);

                    double lowAuthSimilarity = similarity(OverauthLoggerTableEntryMap.get(number).getHighAuthResponse().bodyToString(), ihttpResponse.bodyToString());

                    if (lowAuthSimilarity >= 0.9 && lowAuthSimilarity <=1.0) {
                        OverauthLoggerTableEntryMap.get(number).setOverAuth("True");
                        setOverauthLoggerTableValueAt("True", rowIndex, "OverAuth");
                    }
                }

                case "fastjson" -> {
                    FastjsonLoggerTableEntryMap.get(number).setFastjsonRequest(ihttpRequest);
                    FastjsonLoggerTableEntryMap.get(number).setFastjsonResponse(ihttpResponse);
                }

                case "springboot" -> {
                    rowIndex = SpringbootLoggerTableEntryMap.get(number).getRowIndex();
                    if(!responseCode.startsWith("4") && !responseCode.startsWith("5")){
                        switch (SpringbootLoggerTableEntryMap.get(number).getType()){
                            case "Swagger" -> {
                                if(httpResponse.statedMimeType()== MimeType.JSON && httpResponse.bodyToString().toLowerCase().contains("swagger")){
                                    SpringbootLoggerTableEntryMap.get(number).setVul("True");
                                    setSpringbootLoggerTableValueAt("True", SpringbootLoggerTableEntryMap.get(number).getRowIndex(),"Vul");
                                }
                            }
                            case "Actuator" -> {
                                if(httpResponse.statedMimeType()== MimeType.JSON && httpResponse.bodyToString().toLowerCase().contains("status")){
                                    SpringbootLoggerTableEntryMap.get(number).setVul("True");
                                    setSpringbootLoggerTableValueAt("True", SpringbootLoggerTableEntryMap.get(number).getRowIndex(),"Vul");
                                }
                            }
                            case "Doc" -> {
                                if(httpResponse.statedMimeType()== MimeType.HTML && httpResponse.bodyToString().toLowerCase().contains("webjars")){
                                    SpringbootLoggerTableEntryMap.get(number).setVul("True");
                                    setSpringbootLoggerTableValueAt("True", SpringbootLoggerTableEntryMap.get(number).getRowIndex(),"Vul");
                                }
                            }
                            case "Druid" -> {
                                if(httpResponse.bodyToString().toLowerCase().contains("druid") || httpResponse.toString().contains("login.html")){
                                    SpringbootLoggerTableEntryMap.get(number).setVul("True");
                                    setSpringbootLoggerTableValueAt("True", SpringbootLoggerTableEntryMap.get(number).getRowIndex(),"Vul");
                                }
                            }
                        }
                    }


                    String responseTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());

                    setSpringbootLoggerTableValueAt(String.valueOf(responseLength), rowIndex, "Length");
                    setSpringbootLoggerTableValueAt(responseTime, rowIndex, "responseTime");
                    setSpringbootLoggerTableValueAt(responseCode, rowIndex, "Code");

                    SpringbootLoggerTableEntryMap.get(number).setCode(responseCode);
                    SpringbootLoggerTableEntryMap.get(number).setRawRequest(ihttpRequest);
                    SpringbootLoggerTableEntryMap.get(number).setRawResponse(ihttpResponse);
                    SpringbootLoggerTableEntryMap.get(number).setLength(String.valueOf(responseLength));
                    SpringbootLoggerTableEntryMap.get(number).setResponseTime(responseTime);

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
