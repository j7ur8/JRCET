package burp;

import burp.api.montoya.collaborator.CollaboratorClient;
import burp.api.montoya.collaborator.CollaboratorPayload;
import burp.api.montoya.collaborator.Interaction;
import burp.api.montoya.core.Annotations;
import burp.api.montoya.http.handler.*;
import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.http.message.responses.HttpResponse;
import burp.api.montoya.internal.ObjectFactoryLocator;
import jrcet.help.Similarity.CharBasedSimilarity;
import jrcet.help.Similarity.util.StringUtil;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static burp.MyExtender.API;
import static jrcet.frame.Scanner.Fastjson.Fastjson.*;
import static jrcet.frame.Scanner.Overauth.Overauth.*;
import static jrcet.frame.Scanner.Springboot.Springboot.*;

public class MyRegisterHttpHandler implements HttpHandler {

    public String[] BlackExtensionList = new String[]{"js","png","jpg","jpeg","gif","txt","html","pdf","xls","xlsx","word","ppt","zip","xml","gif","js"};

    public static boolean debug = true;

    @Override
    public RequestToBeSentAction handleHttpRequestToBeSent(HttpRequestToBeSent requestToBeSent) {

        //不接受Extensions和Intruder的流量
        String requestTool = requestToBeSent.toolSource().toolType().toolName();
        String requestMethod = requestToBeSent.method();
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

        //不接受已检查过的url
        //sprintboot检查过的url
        String springbootNote = "";

        if(Objects.equals(requestMethod, "GET") && getSpringbootMenuWorkBox().isSelected() && !SpringbootCheckedUrlList.contains(requestToBeSent.url())){

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

            HttpRequestResponse httpRequestResponse = API.http().sendRequest(httpRequest);
            HttpRequest  ihttpRequest = httpRequestResponse.request();
            HttpResponse ihttpResponse = httpRequestResponse.response();
            HttpResponse iSimplifyhttpResponse = ihttpResponse.withBody(ihttpResponse.body().subArray(0,Math.min(20000,ihttpResponse.body().length())));
            int rowIndex = Integer.parseInt(number) - 1;
            switch (type) {
                case "unAuth" -> {
                    AuthCheckEntryMap.get(number).setUnAuthRequest(ihttpRequest);
                    AuthCheckEntryMap.get(number).setUnAuthResponse(ihttpResponse);
                    AuthCheckEntryMap.get(number).setSimplifyUnAuthResponse(iSimplifyhttpResponse);
                    double unAuthSimilarity = CharBasedSimilarity.getInstance().getSimilarity(AuthCheckEntryMap.get(number).getHighAuthResponse().bodyToString(), ihttpResponse.bodyToString());
                    if (unAuthSimilarity >= 0.9) {
                        AuthCheckEntryMap.get(number).setUnAuth("True");
                        setOverauthLoggerTableValueAt("True", rowIndex, "UnAuth");
                    }
                }
                case "lowAuth" -> {
                    AuthCheckEntryMap.get(number).setLowAuthRequest(ihttpRequest);
                    AuthCheckEntryMap.get(number).setUnAuthResponse(ihttpResponse);
                    AuthCheckEntryMap.get(number).setSimplifyLowAuthResponse(iSimplifyhttpResponse);
                    double lowAuthSimilarity = CharBasedSimilarity.getInstance().getSimilarity(AuthCheckEntryMap.get(number).getHighAuthResponse().bodyToString(), ihttpResponse.bodyToString());
                    if (lowAuthSimilarity >= 0.9) {
                        AuthCheckEntryMap.get(number).setOverAuth("True");
                        setOverauthLoggerTableValueAt("True", rowIndex, "OverAuth");
                    }
                }

                case "fastjson" -> {

                    FastjsonEntryMap.get(number).setFastjsonRequest(ihttpRequest);
                    FastjsonEntryMap.get(number).setFastjsonResponse(ihttpResponse);
                    FastjsonEntryMap.get(number).setSimplifyFastjsonResponse(iSimplifyhttpResponse);
                }

                case "springboot" -> {

                    String responseLength = Integer.toString(ihttpResponse.body().length());
                    String responseTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
                    setSpringbootLoggerTableValueAt(responseLength, rowIndex, "Length");
                    setSpringbootLoggerTableValueAt(responseTime, rowIndex, "responseTime");

                    SpringbootTableEntryMap.get(number).setRawRequest(ihttpRequest);
                    SpringbootTableEntryMap.get(number).setRawResponse(ihttpResponse);
                    SpringbootTableEntryMap.get(number).setSimplifyRawResponse(iSimplifyhttpResponse);
                    SpringbootTableEntryMap.get(number).setLength(responseLength);
                    SpringbootTableEntryMap.get(number).setResponseTime(responseTime);

                }

            }
            return null;
        }

    }

    public static String getRequestNumber(String type,String notes){
        String[] noteArray = notes.split(",");
        for(String note: noteArray){
            if(note.startsWith(type)){
                return note.replace(type,"");
            }
        }
        return "";
    }
}
