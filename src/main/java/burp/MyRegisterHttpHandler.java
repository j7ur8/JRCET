package burp;

import burp.api.montoya.core.Annotations;
import burp.api.montoya.http.HttpService;
import burp.api.montoya.http.handler.*;
import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.http.message.params.HttpParameterType;
import burp.api.montoya.http.message.params.ParsedHttpParameter;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.http.message.responses.HttpResponse;
import jrcet.frame.Scanner.Overauth.OverauthTableEntry;
import jrcet.help.Helper;
import org.xm.Similarity;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

import static burp.MyExtender.API;
import static jrcet.frame.Scanner.Overauth.OverauthComponent.*;

public class MyRegisterHttpHandler implements HttpHandler {

    public String RequestNumber = "1";
    public static ArrayList<String> UrlList = new ArrayList<>();
    public static HashMap<String, OverauthTableEntry> RequestMap = new HashMap<>();

    public String[] blackExtensionList = new String[]{"js","png","jpg","jpeg","gif","txt","html","pdf","xls","xlsx","word","ppt","zip","xml","gif","js"};
    @Override
    public RequestToBeSentAction handleHttpRequestToBeSent(HttpRequestToBeSent requestToBeSent) {

        //不接受Extensions和Intruder的流量
        String requestTool = requestToBeSent.toolSource().toolType().toolName();
        switch (requestTool) {
            case "Extensions", "Intruder" -> {
                return RequestToBeSentAction.continueWith(requestToBeSent);
            }
        }

        //不接受不期望的流量
        String requestHost = requestToBeSent.httpService().host();
        String targetHost = getField("OverauthMenuHostField").getText();
        if(Objects.equals(targetHost, "") || !requestHost.contains(targetHost)){
            return RequestToBeSentAction.continueWith(requestToBeSent);
        }

        //不接受已检查过的url
        if(UrlList.contains(requestToBeSent.url())){
            return RequestToBeSentAction.continueWith(requestToBeSent);
        }

        //不接受静态文件的请求
        String requestPath = requestToBeSent.path();
        for(String ext:blackExtensionList){
            requestPath = (requestPath.split("\\?"))[0];
            if(requestPath.endsWith(ext)){
                return RequestToBeSentAction.continueWith(requestToBeSent);
            }
        }

//        API.logging().output().println("UrlList的大小："+UrlList.size());
//        API.logging().output().println("UrlList New："+requestToBeSent.url());

        //判断是否可能有水平越权的字段
        StringBuilder FlagAuth =  new StringBuilder();
        List<ParsedHttpParameter> highAuthParameterList = requestToBeSent.parameters();
        for(ParsedHttpParameter highAuthParameter : highAuthParameterList){
            if(highAuthParameter.type()!= HttpParameterType.COOKIE && Helper.isNumeric(highAuthParameter.value())){
                FlagAuth.append(highAuthParameter.name()).append("=").append(highAuthParameter.value()).append(";");
            }
        }


        // 设置table
        String requestMethod = requestToBeSent.method();
        String requestTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
        String[] inf = new String[]{RequestNumber, requestTool, requestMethod, requestHost, requestPath,"", requestTime, "", "", "", FlagAuth.toString()};

        ((DefaultTableModel)getTable().getModel()).addRow(inf);

        //设置本次请求的rowEntry
        OverauthTableEntry rowEntry = new OverauthTableEntry(inf);
        rowEntry.setHighAuthRequest(requestToBeSent);
        RequestMap.put(RequestNumber, rowEntry);

        //设置请求的识别码，方便识别返回包
        Annotations requestAnnotations = requestToBeSent.annotations().withNotes(RequestNumber);

        //row+1
        RequestNumber = Integer.toString(Integer.parseInt(RequestNumber)+1);

        return RequestToBeSentAction.continueWith(requestToBeSent,requestAnnotations);
    }

    @Override
    public ResponseReceivedAction handleHttpResponseReceived(HttpResponseReceived responseReceived) {

        //判断是否为期望的返回包
        String requestNumber = responseReceived.annotations().notes();
        if(requestNumber==null){
            return ResponseReceivedAction.continueWith(responseReceived);
        }

//        API.logging().output().println("返回包的标识："+requestNumber);

        //设置返回包
        RequestMap.get(requestNumber).setHighAuthResponse(responseReceived);
//        API.logging().output().println("成功设置responseReceived");

        //设置返回包长度
        String responseLength = Integer.toString(responseReceived.body().length());
//        API.logging().output().println("返回包长度:"+responseLength);
        int responseLengthColumn = 5; (getTable().getModel()).setValueAt(responseLength,Integer.parseInt(requestNumber)-1,responseLengthColumn);
        RequestMap.get(requestNumber).setLength(responseLength);

        //设置返回包时间
        String responseTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
        int responseTimeColumn = 7; (getTable().getModel()).setValueAt(responseTime,Integer.parseInt(requestNumber)-1,responseTimeColumn);
        RequestMap.get(requestNumber).setResponseTime(responseTime);

        //设置返回包简化版
        HttpResponse iSimplifyHighAuthhttpResponse = responseReceived.withBody(responseReceived.body().subArray(0, Math.min(20000, Integer.parseInt(responseLength))));
        RequestMap.get(requestNumber).setSimplifyHighAuthResponse(iSimplifyHighAuthhttpResponse);


        // 判断url是否已进行过越权测试，如果进行了，则不再测试越权
        HttpRequest highAuthHttpRequest = RequestMap.get(requestNumber).HighAuthRequest;
        HttpService httpService = highAuthHttpRequest.httpService();


        //获取鉴权字段
        String highAuth = getField("OverauthMenuHighauthField").getText();
        String lowAuth = getField("OverauthMenuLowauthField").getText();

        //如果未设置高权限鉴权字段，将不进行检测
//        API.logging().output().println(highAuth);
        if(Objects.equals(highAuth, "")){
//            API.logging().output().println("未设置高权限字段");
            return ResponseReceivedAction.continueWith(responseReceived);
        }

        String highAuthHttpReuqestString = highAuthHttpRequest.toString();
        //如果设置了低权限鉴权字段，发送低权限请求
        if(!Objects.equals(lowAuth, "")){
//            API.logging().output().println("发送低权限请求包");
            HttpRequest lowAuthHttpRequest = HttpRequest.httpRequest(httpService,highAuthHttpReuqestString.replace(highAuth,lowAuth));
            new authCheckWorker("lowAuth",requestNumber,lowAuthHttpRequest).execute();
        }

        //发送未授权请求
//        API.logging().output().println("发送未授权请求包");
        HttpRequest unAuthHttpRequest =  HttpRequest.httpRequest(httpService,highAuthHttpReuqestString.replace(highAuth,""));
        new authCheckWorker("unAuth",requestNumber,unAuthHttpRequest).execute();

        //将url加入到已测试列表
        UrlList.add(highAuthHttpRequest.url());
//        API.logging().output().println("UrlList Add："+highAuthHttpRequest.url());

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
            switch (type) {
                case "unAuth" -> {
                    RequestMap.get(number).setUnAuthRequest(httpRequestResponse.request());
                    RequestMap.get(number).setUnAuthResponse(ihttpResponse);
                    RequestMap.get(number).setSimplifyUnAuthResponse(iSimplifyhttpResponse);
                    double unAuthSimilarity = Similarity.charBasedSimilarity(highAuthHttpResponse.bodyToString(), ihttpResponse.bodyToString());
                    if (unAuthSimilarity >= 0.9) {
                        RequestMap.get(number).UnAuth = "True";
                        getTable().getModel().setValueAt("True", Integer.parseInt(number) - 1, 9);
                    }
                }
                case "lowAuth" -> {
                    RequestMap.get(number).setLowAuthRequest(httpRequestResponse.request());
                    RequestMap.get(number).setUnAuthResponse(ihttpResponse);
                    RequestMap.get(number).setSimplifyLowAuthResponse(iSimplifyhttpResponse);
                    double lowAuthSimilarity = Similarity.charBasedSimilarity(highAuthHttpResponse.bodyToString(), ihttpResponse.bodyToString());
                    if (lowAuthSimilarity >= 0.9) {
                        RequestMap.get(number).OverAuth = "True";
                        getTable().getModel().setValueAt("True", Integer.parseInt(number) - 1, 8);
                    }
                }
            }
        }
    }

    public static JTable getTable(){
        return (JTable) Helper.getComponent(OverauthComponent,"OverauthLoggerTable");
    }

    public static JTextField getField(String filedName){
        return (JTextField) Helper.getComponent(OverauthComponent,filedName);
    }

    public static String getRequestNumber(int row){
        return (String) getTable().getValueAt(row,0);
    }
}
