package burp;

import burp.api.montoya.core.Annotations;
import burp.api.montoya.http.HttpService;
import burp.api.montoya.http.handler.*;
import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.http.message.params.HttpParameterType;
import burp.api.montoya.http.message.params.ParsedHttpParameter;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.http.message.responses.HttpResponse;
import jrcet.frame.Scanner.Fastjson.Fastjson;
import jrcet.frame.Scanner.Fastjson.FastjsonTableEntry;
import jrcet.frame.Scanner.Overauth.OverauthTableEntry;
import jrcet.help.Helper;
import org.xm.Similarity;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.*;

import static burp.MyExtender.API;
import static jrcet.frame.Scanner.Fastjson.Fastjson.*;
import static jrcet.frame.Scanner.Overauth.Overauth.*;

public class MyRegisterHttpHandler implements HttpHandler {

    public final String AUTH = "AUTH";

    public final String FASTJSON = "FASTJSON";
    public String AuthCheckRequestNumber = "1";

    public String FastjsonCheckRequestNumber = "1";
    public static ArrayList<String> AuthCheckUrlList = new ArrayList<>();

    public static ArrayList<String> FastjsonCheckUrlList = new ArrayList<String>();
    public static HashMap<String, OverauthTableEntry> AuthCheckEntryMap = new HashMap<>();

    public static HashMap<String, FastjsonTableEntry> FastjsonEntryMap = new HashMap<>();
    public String[] BlackExtensionList = new String[]{"js","png","jpg","jpeg","gif","txt","html","pdf","xls","xlsx","word","ppt","zip","xml","gif","js"};

    public static boolean debug = true;

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
        if(debug)API.logging().output().println("请求地址："+requestPath);
        for(String ext: BlackExtensionList){
            requestPath = (requestPath.split("\\?"))[0];
            if(requestPath.endsWith(ext)){
                return RequestToBeSentAction.continueWith(requestToBeSent);
            }
        }

        //不接受已检查过的url
        //fastjson检查过的url
        String fastjsonNote = "";
        if(debug)API.logging().output().println("fastjson是否检查过 "+requestToBeSent.url()+" : "+FastjsonCheckUrlList.contains(requestToBeSent.url()));

        if(!FastjsonCheckUrlList.contains(requestToBeSent.url())){
            fastjsonNote = fastjsonCheckRequest(requestToBeSent);

        }

        //Overauth检查过的Url
        String authCheckNote = "";
        if(debug)API.logging().output().println("authcheck是否检查过 "+requestToBeSent.url()+" : "+AuthCheckUrlList.contains(requestToBeSent.url()));
        if(!AuthCheckUrlList.contains(requestToBeSent.url())){
            authCheckNote = authCheckRequest(requestToBeSent);
        }

        Annotations requestAnnotations = requestToBeSent.annotations().withNotes(
                fastjsonNote+","+authCheckNote
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

        return ResponseReceivedAction.continueWith(responseReceived);
    }

    public String fastjsonCheckRequest(HttpRequestToBeSent requestToBeSent){

        if(!getFastjsonMenuWorkBox().isSelected() || !Fastjson.check(requestToBeSent)) return "";
        String requestNumber = FastjsonCheckRequestNumber;
        String requestHost = requestToBeSent.httpService().host();
        String requestMethod = requestToBeSent.method();
        String requestTool = requestToBeSent.toolSource().toolType().toolName();
        String requestPath = requestToBeSent.path();
        String requestTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
        String[] inf = new String[]{requestNumber, requestTool, requestMethod, requestHost, requestPath, "", requestTime, "", "", ""};

        ((DefaultTableModel)getFastjsonLoggerTable().getModel()).addRow(inf);

        FastjsonTableEntry rowEntry = new FastjsonTableEntry(inf);
        FastjsonEntryMap.put(requestNumber,rowEntry);
        rowEntry.setRawRequest(requestToBeSent);

        FastjsonCheckRequestNumber = Integer.toString(Integer.parseInt(FastjsonCheckRequestNumber)+1);

        return FASTJSON+requestNumber;
    }

    public void fastjsonCheckResponse(HttpResponseReceived responseReceived, String fastjsonCheckRequestNumber){

        if(debug)API.logging().output().println("fastjson chekcn 处理row: "+Integer.parseInt(fastjsonCheckRequestNumber));

        //设置Fastjson
        String responseLength = Integer.toString(responseReceived.body().length());
        if(debug)API.logging().output().println("fastjson Raw返回包长度:"+responseLength);
        setFastjsonLoggerTableValueAt(responseLength, Integer.parseInt(fastjsonCheckRequestNumber) - 1, "Length");
        FastjsonEntryMap.get(fastjsonCheckRequestNumber).setLength(responseLength);

        //设置返回包时间
        String responseTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
        if(debug)API.logging().output().println("fastjson Raw返回包时间:"+responseTime);
        setFastjsonLoggerTableValueAt(responseTime, Integer.parseInt(fastjsonCheckRequestNumber) - 1, "responseTime");
        FastjsonEntryMap.get(fastjsonCheckRequestNumber).setResponseTime(responseTime);

        //设置Raw返回包
        FastjsonEntryMap.get(fastjsonCheckRequestNumber).setRawResponse(responseReceived);
        //设置Raw返回包简化版
        HttpResponse simplifyHighAuthhttpResponse = responseReceived.withBody(responseReceived.body().subArray(0, Math.min(20000, Integer.parseInt(responseLength))));
        FastjsonEntryMap.get(fastjsonCheckRequestNumber).setSimplifyRawResponse(simplifyHighAuthhttpResponse);

        //进行fastjson检查
        if(debug)API.logging().output().println("fastjson 进行检查");
        String randomString = Helper.createRandomString(8);
        String body =  ("\\ufeff{,/*aab*/,'x_' : {/*aab*/\"@type\":\"java.net.InetSocketAddress\"{\"address\":/*aab*/,/*aa\"b*/ \"val\" :\""+randomString+"."+DNSLOG+"\"}}}");
        HttpRequest fastjsonRequest = FastjsonEntryMap.get(fastjsonCheckRequestNumber).getRawRequest().withBody(body);

        new authCheckWorker("fastjson", fastjsonCheckRequestNumber, fastjsonRequest).execute();
        if(debug)API.logging().output().println("fastjson 检查完毕");

        FastjsonCheckUrlList.add(FastjsonEntryMap.get(fastjsonCheckRequestNumber).getRawRequest().url());

    }

    public String authCheckRequest(HttpRequestToBeSent requestToBeSent){
        //不接受不期望的流量
        //Overauth不期望的域名
        String requestHost = requestToBeSent.httpService().host();
        String overauthTargetHost = getOverauthMenuHostField().getText();
        if(Objects.equals(overauthTargetHost, "") || !requestHost.contains(overauthTargetHost)){
            return "";
        }
        if(debug)API.logging().output().println("期望域名："+requestHost);


        //设置有水平越权的字段
        StringBuilder FlagAuth =  new StringBuilder();
        List<ParsedHttpParameter> highAuthParameterList = requestToBeSent.parameters();
        for(ParsedHttpParameter highAuthParameter : highAuthParameterList){
            if(highAuthParameter.type()!= HttpParameterType.COOKIE && Helper.isNumeric(highAuthParameter.value())){
                FlagAuth.append(highAuthParameter.name()).append("=").append(highAuthParameter.value()).append(";");
            }
        }

        if(debug)API.logging().output().println("判断水平越权");

        // 设置authcheck table
        String requestNumber = AuthCheckRequestNumber;
        String requestMethod = requestToBeSent.method();
        String requestTool = requestToBeSent.toolSource().toolType().toolName();
        String requestPath = requestToBeSent.path();
        String requestTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
        String[] inf = new String[]{requestNumber, requestTool, requestMethod, requestHost, requestPath,"", requestTime, "", "", "", FlagAuth.toString()};
        if(debug)API.logging().output().println("获取tablerow");

        if(debug)API.logging().output().println("设置tablerow");
        ((DefaultTableModel) getOverauthLoggerTable().getModel()).addRow(inf);

        //设置本次请求的authcheck rowEntry
        OverauthTableEntry rowEntry = new OverauthTableEntry(inf);
        rowEntry.setHighAuthRequest(requestToBeSent);

        if(debug)API.logging().output().println("设置rowEntry");
        AuthCheckEntryMap.put(AuthCheckRequestNumber, rowEntry);

        //设置overauth请求的识别码，方便识别返回包
        //row+1
        if(debug)API.logging().output().println("设置请求识别码");if(debug)API.logging().output().println("设置请求识别码");


        AuthCheckRequestNumber = Integer.toString(Integer.parseInt(AuthCheckRequestNumber)+1);

        return AUTH + requestNumber;
    }


    public void authCheckResponse(HttpResponseReceived responseReceived, String requestNumber){

        if(debug)API.logging().output().println("返回包的标识："+requestNumber);

        //设置返回包
        AuthCheckEntryMap.get(requestNumber).setHighAuthResponse(responseReceived);
        if(debug)API.logging().output().println("成功设置responseReceived");

        //设置返回包长度
        String responseLength = Integer.toString(responseReceived.body().length());
        if(debug)API.logging().output().println("返回包长度:"+responseLength);
        setOverauthLoggerTableValueAt(responseLength,Integer.parseInt(requestNumber)-1,"Length");
        AuthCheckEntryMap.get(requestNumber).setLength(responseLength);

        //设置返回包时间
        String responseTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
        setOverauthLoggerTableValueAt(responseTime,Integer.parseInt(requestNumber)-1, "responseTime");
        AuthCheckEntryMap.get(requestNumber).setResponseTime(responseTime);

        //设置返回包简化版
        HttpResponse iSimplifyHighAuthhttpResponse = responseReceived.withBody(responseReceived.body().subArray(0, Math.min(20000, Integer.parseInt(responseLength))));
        AuthCheckEntryMap.get(requestNumber).setSimplifyHighAuthResponse(iSimplifyHighAuthhttpResponse);


        // 测试越权
        HttpRequest highAuthHttpRequest = AuthCheckEntryMap.get(requestNumber).HighAuthRequest;
        HttpService httpService = highAuthHttpRequest.httpService();


        //获取鉴权字段
        String highAuth = getOverauthMenuHighauthField().getText();
        String lowAuth = getOverauthMenuLowauthField().getText();

        //如果未设置高权限鉴权字段，将不进行检测
        if(debug)API.logging().output().println(highAuth);
        if(Objects.equals(highAuth, "")){
            if(debug)API.logging().output().println("未设置高权限字段");
            return ;
        }

        String highAuthHttpReuqestString = highAuthHttpRequest.toString();
        //如果设置了低权限鉴权字段，发送低权限请求
        if(!Objects.equals(lowAuth, "")){
            if(debug)API.logging().output().println("发送低权限请求包");
            HttpRequest lowAuthHttpRequest = HttpRequest.httpRequest(httpService,highAuthHttpReuqestString.replace(highAuth,lowAuth));
            new authCheckWorker("lowAuth",requestNumber,lowAuthHttpRequest).execute();
        }

        //发送未授权请求
        if(debug)API.logging().output().println("发送未授权请求包");
        HttpRequest unAuthHttpRequest =  HttpRequest.httpRequest(httpService,highAuthHttpReuqestString.replace(highAuth,""));
        new authCheckWorker("unAuth",requestNumber,unAuthHttpRequest).execute();

        //将url加入到已测试列表
        if(debug)API.logging().output().println("UrlList Add："+highAuthHttpRequest.url());
        AuthCheckUrlList.add(highAuthHttpRequest.url());
    }

    public static class authCheckWorker extends SwingWorker<String, Void> {
        public String number;
        public String type;
        public HttpRequest httpRequest;
        public HttpRequestResponse httpRequestResponse;

        public authCheckWorker(String type, String number, HttpRequest httpRequest) {
            this.type = type;
            this.number = number;
            this.httpRequest = httpRequest;

        }
        @Override
        protected String doInBackground() {
            if(debug) API.logging().output().println("start sendRequest work");
            httpRequestResponse = API.http().sendRequest(httpRequest);
            HttpRequest  ihttpRequest = httpRequestResponse.request();
            HttpResponse ihttpResponse = httpRequestResponse.response();
            HttpResponse iSimplifyhttpResponse = ihttpResponse.withBody(ihttpResponse.body().subArray(0,Math.min(20000,ihttpResponse.body().length())));

            switch (type) {
                case "unAuth" -> {
                    AuthCheckEntryMap.get(number).setUnAuthRequest(ihttpRequest);
                    AuthCheckEntryMap.get(number).setUnAuthResponse(ihttpResponse);
                    AuthCheckEntryMap.get(number).setSimplifyUnAuthResponse(iSimplifyhttpResponse);
                    double unAuthSimilarity = Similarity.charBasedSimilarity(AuthCheckEntryMap.get(number).HighAuthResponse.bodyToString(), ihttpResponse.bodyToString());
                    if (unAuthSimilarity >= 0.9) {
                        AuthCheckEntryMap.get(number).UnAuth = "True";
                        setOverauthLoggerTableValueAt("True", Integer.parseInt(number) - 1, "UnAuth");
                    }
                }
                case "lowAuth" -> {
                    AuthCheckEntryMap.get(number).setLowAuthRequest(ihttpRequest);
                    AuthCheckEntryMap.get(number).setUnAuthResponse(ihttpResponse);
                    AuthCheckEntryMap.get(number).setSimplifyLowAuthResponse(iSimplifyhttpResponse);
                    double lowAuthSimilarity = Similarity.charBasedSimilarity(AuthCheckEntryMap.get(number).HighAuthResponse.bodyToString(), ihttpResponse.bodyToString());
                    if (lowAuthSimilarity >= 0.9) {
                        AuthCheckEntryMap.get(number).OverAuth = "True";
                        setOverauthLoggerTableValueAt("True", Integer.parseInt(number) - 1, "OverAuth");
                    }
                }

                case "fastjson" -> {
                    if(debug)API.logging().output().println("设置fastjson的fastjsonRequest");
                    FastjsonEntryMap.get(number).setFastjsonRequest(ihttpRequest);
                    FastjsonEntryMap.get(number).setFastjsonResponse(ihttpResponse);
                    FastjsonEntryMap.get(number).setSimplifyFastjsonResponse(iSimplifyhttpResponse);
                    if(debug)API.logging().output().println("设置是否存在fastjson漏洞");
                    setFastjsonLoggerTableValueAt("True", Integer.parseInt(number) - 1, "FastJson");
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
