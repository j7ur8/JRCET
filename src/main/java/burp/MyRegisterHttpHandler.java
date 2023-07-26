package burp;

import burp.api.montoya.core.Annotations;
import burp.api.montoya.http.HttpService;
import burp.api.montoya.http.handler.*;
import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.http.message.responses.HttpResponse;
import jrcet.frame.Scanner.Overauth.OverauthTableEntry;
import jrcet.help.Helper;
import org.xm.Similarity;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.*;

import static burp.MyExtender.API;
import static jrcet.frame.Scanner.Overauth.OverauthComponent.*;

public class MyRegisterHttpHandler implements HttpHandler {

    public String RequestNumber = "1";
    public ArrayList<String> UrlList = new ArrayList<>();
    public static HashMap<String, OverauthTableEntry> RequestMap= new HashMap<>();

    public String[] blackExtensionList = new String[]{"js","png","jpg","jpeg","gif","txt","html","pdf","xls","xlsx","word","ppt","zip","xml"};
    @Override
    public RequestToBeSentAction handleHttpRequestToBeSent(HttpRequestToBeSent requestToBeSent) {

        String requestNumber = RequestNumber;
        String requestTool = requestToBeSent.toolSource().toolType().toolName();
        String requestHost = requestToBeSent.httpService().host();
        switch (requestTool){
            case "Extensions":
            case "Intruder":
                return RequestToBeSentAction.continueWith(requestToBeSent);
        }

        JTextField hostField = (JTextField) Helper.getComponent(OverauthComponent,"OverauthMenuHostField");
        assert hostField != null;
        if(!Objects.equals(hostField.getText(), requestHost)){
            return RequestToBeSentAction.continueWith(requestToBeSent);
        }

        String requestPath = requestToBeSent.path();
        for(String ext:blackExtensionList){
            if(requestPath.endsWith(ext)){
                return RequestToBeSentAction.continueWith(requestToBeSent);
            }
        }

        String requestMethod = requestToBeSent.method();

        String requestTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());

        String[] inf = new String[]{requestNumber, requestTool, requestMethod, requestHost, requestPath,"", requestTime, ""};

        // 设置table
        ((DefaultTableModel)getTable().getModel()).addRow(inf);

        //设置高权限Editor的
        OverauthTableEntry overauthTableEntry = new OverauthTableEntry(inf);
        overauthTableEntry.setHighAuthRequest(requestToBeSent);
        RequestMap.put(requestNumber, overauthTableEntry);

        //设置请求的识别码，方便识别返回包
        Annotations requestAnnotations = requestToBeSent.annotations().withNotes(RequestNumber);
        RequestNumber = Integer.toString(Integer.parseInt(RequestNumber)+1);

        return RequestToBeSentAction.continueWith(requestToBeSent,requestAnnotations);
    }

    @Override
    public ResponseReceivedAction handleHttpResponseReceived(HttpResponseReceived responseReceived) {

        String requestNumber = responseReceived.annotations().notes();
        if(requestNumber==null){
            return ResponseReceivedAction.continueWith(responseReceived);
        }
//
        String responseLength = Integer.toString(responseReceived.body().length());
        String responseTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
        int responseLengthColumn = 5;
        int responseTimeColumn = 7;

        (getTable().getModel()).setValueAt(responseLength,Integer.parseInt(requestNumber)-1,responseLengthColumn);
        (getTable().getModel()).setValueAt(responseTime,Integer.parseInt(requestNumber)-1,responseTimeColumn);

        RequestMap.get(requestNumber).setLength(responseLength);
        RequestMap.get(requestNumber).setResponseTime(responseTime);
        RequestMap.get(requestNumber).setHighAuthResponse(responseReceived);
        HttpResponse iSimplifyHighAuthhttpResponse = responseReceived.withBody(responseReceived.body().subArray(0, Math.min(20000, Integer.parseInt(responseLength))));
        RequestMap.get(requestNumber).setSimplifyHighAuthResponse(iSimplifyHighAuthhttpResponse);

        if(!UrlList.contains(RequestMap.get(requestNumber).HighAuthRequest.url())){
            UrlList.add(RequestMap.get(requestNumber).HighAuthRequest.url());

            JTextField highAuthField = (JTextField) Helper.getComponent(OverauthComponent,"OverauthMenuHighauthField");assert highAuthField != null;
            JTextField lowAuthField = (JTextField) Helper.getComponent(OverauthComponent,"OverauthMenuLowauthField");assert lowAuthField != null;
            String highAuth = highAuthField.getText();
            String lowAuth = lowAuthField.getText();
            HttpRequest highAuthHttpRequest = RequestMap.get(requestNumber).HighAuthRequest;
            HttpService httpService = highAuthHttpRequest.httpService();

            //发送低权限请求
            String highAuthHttpReuqestString = highAuthHttpRequest.toString();
            HttpRequest lowAuthHttpRequest = HttpRequest.httpRequest(httpService,highAuthHttpReuqestString.replace(highAuth,lowAuth));
            new authCheckWorker("lowAuth",requestNumber,lowAuthHttpRequest).execute();

            //发送未授权请求
            HttpRequest unAuthHttpRequest =  HttpRequest.httpRequest(httpService,highAuthHttpReuqestString.replace(highAuth,""));
            new authCheckWorker("unAuth",requestNumber,unAuthHttpRequest).execute();
        }

        return ResponseReceivedAction.continueWith(responseReceived);
    }
    public static class authCheckWorker extends SwingWorker<String, Void> {
        public String number;
        public String type;
        public HttpRequest httpRequest;
        public HttpRequestResponse httpRequestResponse;

        public authCheckWorker(String type, String number, HttpRequest httpRequest) {
            this.number = number;
            this.type = type;
            this.httpRequest = httpRequest;

        }

        @Override
        protected String doInBackground() {
            httpRequestResponse = API.http().sendRequest(httpRequest);
            return null;
        }

        @Override
        protected void done() {
            HttpResponse ihttpResponse = httpRequestResponse.response();
            HttpResponse iSimplifyhttpResponse = ihttpResponse.withBody(ihttpResponse.body().subArray(0,Math.min(20000,ihttpResponse.body().length())));

            HttpResponse highAuthHttpResponse = RequestMap.get(number).HighAuthResponse;
            switch (type){
                case "unAuth":
                    RequestMap.get(number).setUnAuthRequest(httpRequestResponse.request());
                    RequestMap.get(number).setUnAuthResponse(ihttpResponse);
                    RequestMap.get(number).setSimplifyUnAuthResponse(iSimplifyhttpResponse);

                    double unAuthSimilarity = Similarity.charBasedSimilarity(highAuthHttpResponse.bodyToString(),ihttpResponse.bodyToString());
                    if(unAuthSimilarity>=0.9){
                        RequestMap.get(number).UnAuth="True";
                        getTable().getModel().setValueAt("True", Integer.parseInt(number)-1,9);
                    }

                    break;
                case "lowAuth":
                    RequestMap.get(number).setLowAuthRequest(httpRequestResponse.request());
                    RequestMap.get(number).setUnAuthResponse(ihttpResponse);
                    RequestMap.get(number).setSimplifyLowAuthResponse(iSimplifyhttpResponse);
                    double lowAuthSimilarity = Similarity.charBasedSimilarity(highAuthHttpResponse.bodyToString(),ihttpResponse.bodyToString());
                    if(lowAuthSimilarity>=0.9){
                        RequestMap.get(number).OverAuth="True";
                        getTable().getModel().setValueAt("True", Integer.parseInt(number)-1,8);
                    }
                    break;
            }
        }
    }

    public static JTable getTable(){
        JTable tTbale = (JTable) Helper.getComponent(OverauthComponent,"OverauthLoggerTable");
        assert tTbale != null;

        return tTbale;
    }
}
