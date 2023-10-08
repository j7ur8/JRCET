package burp;

import burp.api.montoya.core.Annotations;
import burp.api.montoya.http.handler.*;
import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.http.message.MimeType;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.http.message.responses.HttpResponse;
import jrcet.help.StringUtil;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import static burp.MyExtender.BurpAPI;
import static jrcet.frame.Scanner.Fastjson.Fastjson.*;
import static jrcet.frame.Scanner.Overauth.Overauth.*;
import static jrcet.frame.Scanner.Springboot.Springboot.*;
import static jrcet.frame.Scanner.Springboot.Springboot.setSpringbootLoggerTableValueAt;
import static jrcet.help.Similarity.StringSimilarity.similarity;

public class MyRegisterHttpHandler implements HttpHandler {

    public String[] BlackExtensionList = new String[]{"js","png","jpg","jpeg","gif","txt","html","pdf","xls","xlsx","word","ppt","zip","xml","gif","js","css","svg","otf","woff","woff2"};

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
        String requestPath = requestToBeSent.path();
        for(String ext: BlackExtensionList){
            requestPath = (requestPath.split("\\?"))[0];
            if(requestPath.endsWith(ext)){
                return RequestToBeSentAction.continueWith(requestToBeSent);
            }
        }

        //sprintboot检查过的url
        String springbootNote = "";

        if(getSpringbootMenuWorkBox().isSelected() && !SpringbootCheckedUrlList.contains(requestToBeSent.url())){
            springbootNote = springbootCheckRequest(requestToBeSent);
        }


        //fastjson检查过的url
        String fastjsonNote = "";

        if(getFastjsonMenuWorkBox().isSelected() && !FastjsonCheckUrlList.contains(requestToBeSent.url())){
            fastjsonNote = fastjsonCheckRequest(requestToBeSent);
        }

        //Overauth检查过的Url
        String authCheckNote = "";
        if(!AuthCheckUrlList.contains(requestToBeSent.url())){
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
                    rowIndex = AuthCheckEntryMap.get(number).getRowIndex();

                    AuthCheckEntryMap.get(number).setUnAuthRequest(ihttpRequest);
                    AuthCheckEntryMap.get(number).setUnAuthResponse(ihttpResponse);

                    double unAuthSimilarity = similarity(AuthCheckEntryMap.get(number).getHighAuthResponse().bodyToString(), ihttpResponse.bodyToString());

                    if (unAuthSimilarity >= 0.9 && unAuthSimilarity <=1.0) {
                        AuthCheckEntryMap.get(number).setUnAuth("True");
                        setOverauthLoggerTableValueAt("True", rowIndex, "UnAuth");
                    }
                }
                case "lowAuth" -> {
                    rowIndex = AuthCheckEntryMap.get(number).getRowIndex();

                    AuthCheckEntryMap.get(number).setLowAuthRequest(ihttpRequest);
                    AuthCheckEntryMap.get(number).setLowAuthResponse(ihttpResponse);

                    double lowAuthSimilarity = similarity(AuthCheckEntryMap.get(number).getHighAuthResponse().bodyToString(), ihttpResponse.bodyToString());

                    if (lowAuthSimilarity >= 0.9 && lowAuthSimilarity <=1.0) {
                        AuthCheckEntryMap.get(number).setOverAuth("True");
                        setOverauthLoggerTableValueAt("True", rowIndex, "OverAuth");
                    }
                }

                case "fastjson" -> {
                    FastjsonEntryMap.get(number).setFastjsonRequest(ihttpRequest);
                    FastjsonEntryMap.get(number).setFastjsonResponse(ihttpResponse);
                }

                case "springboot" -> {
                    rowIndex = SpringbootTableEntryMap.get(number).getRowIndex();
//                    BurpAPI.logging().output().println(responseCode+":"+(!responseCode.equals("404") && !responseCode.startsWith("5"))+":"+SpringbootTableEntryMap.get(number).getType());
                    if(!responseCode.equals("404") && !responseCode.startsWith("5")){
                        switch (SpringbootTableEntryMap.get(number).getType()){
                            case "Swagger", "Actuator" -> {
                                if(httpResponse.statedMimeType()== MimeType.JSON){
                                    SpringbootTableEntryMap.get(number).setVul("True");
                                    setSpringbootLoggerTableValueAt("True",SpringbootTableEntryMap.get(number).getRowIndex(),"Vul");
                                }
                            }
                            case "Doc" -> {
                                if(httpResponse.statedMimeType()== MimeType.HTML){
                                    SpringbootTableEntryMap.get(number).setVul("True");
                                    setSpringbootLoggerTableValueAt("True",SpringbootTableEntryMap.get(number).getRowIndex(),"Vul");
                                }
                            }
                            case "Druid" -> {
                                if(httpResponse.bodyToString().contains("Druid Stat Index") || httpResponse.toString().contains("login.html")){
                                    SpringbootTableEntryMap.get(number).setVul("True");
                                    setSpringbootLoggerTableValueAt("True",SpringbootTableEntryMap.get(number).getRowIndex(),"Vul");
                                }
                            }
                        }
                    }


                    String responseTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());

                    setSpringbootLoggerTableValueAt(String.valueOf(responseLength), rowIndex, "Length");
                    setSpringbootLoggerTableValueAt(responseTime, rowIndex, "responseTime");
                    setSpringbootLoggerTableValueAt(responseCode, rowIndex, "Code");

                    SpringbootTableEntryMap.get(number).setCode(responseCode);
                    SpringbootTableEntryMap.get(number).setRawRequest(ihttpRequest);
                    SpringbootTableEntryMap.get(number).setRawResponse(ihttpResponse);
                    SpringbootTableEntryMap.get(number).setLength(String.valueOf(responseLength));
                    SpringbootTableEntryMap.get(number).setResponseTime(responseTime);

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
