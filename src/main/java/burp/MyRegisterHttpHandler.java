package burp;

import burp.api.montoya.collaborator.CollaboratorClient;
import burp.api.montoya.collaborator.CollaboratorPayload;
import burp.api.montoya.collaborator.Interaction;
import burp.api.montoya.core.Annotations;
import burp.api.montoya.core.ByteArray;
import burp.api.montoya.http.HttpService;
import burp.api.montoya.http.handler.*;
import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.http.message.params.HttpParameterType;
import burp.api.montoya.http.message.params.ParsedHttpParameter;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.http.message.responses.HttpResponse;
import burp.api.montoya.internal.ObjectFactoryLocator;
import jrcet.frame.Scanner.Fastjson.Fastjson;
import jrcet.frame.Scanner.Fastjson.FastjsonTableEntry;
import jrcet.frame.Scanner.Overauth.OverauthTableEntry;
import jrcet.frame.Scanner.Springboot.SpringbootTableEntry;
import jrcet.help.Helper;
import jrcet.help.Similarity.CharBasedSimilarity;
import jrcet.help.Similarity.util.StringUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

import static burp.MyExtender.API;
import static jrcet.frame.Scanner.Fastjson.Fastjson.*;
import static jrcet.frame.Scanner.Overauth.Overauth.*;
import static jrcet.frame.Scanner.Springboot.Springboot.*;
import static jrcet.frame.Scanner.Springboot.Springboot.setSpringbootLoggerTableValueAt;

public class MyRegisterHttpHandler implements HttpHandler {

    /*
    auth
     */
    public final String AUTH = "AUTH";
    public String AuthCheckRequestNumber = "1";
    public static ArrayList<String> AuthCheckUrlList = new ArrayList<>();

    public static HashMap<String, OverauthTableEntry> AuthCheckEntryMap = new HashMap<>();

    /*
    fastjson
     */
    public final String FASTJSON = "FASTJSON";
    public String FastjsonCheckRequestNumber = "1";
    public static ArrayList<String> FastjsonCheckUrlList = new ArrayList<>();
    public static HashMap<String, FastjsonTableEntry> FastjsonEntryMap = new HashMap<>();


    /*
    springboot
     */
    public final String SPRINGBOOT = "SPRINGBOOT";
    public String SpringbootCheckRequestNumber = "1";
    public static ArrayList<String> SpringbootCheckUrlList = new ArrayList<>();
    public static HashMap<String, SpringbootTableEntry> SpringbootEntryMap = new HashMap<>();

    public static String[] SpringbootCheckPaths;
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
        if(debug)API.logging().output().println("请求地址："+requestPath);
        for(String ext: BlackExtensionList){
            requestPath = (requestPath.split("\\?"))[0];
            if(requestPath.endsWith(ext)){
                return RequestToBeSentAction.continueWith(requestToBeSent);
            }
        }

        //不接受已检查过的url
        //sprintboot检查过的url
        String springbootNote = "";
        if(debug) API.logging().output().println("springboot检查是否开启 "+getSpringbootMenuWorkBox().isSelected());
        if(debug)API.logging().output().println("springboot是否检查过 "+requestToBeSent.url()+" : "+ SpringbootCheckUrlList.contains(requestToBeSent.url()));
        if(Objects.equals(requestMethod, "GET") && getSpringbootMenuWorkBox().isSelected() && !SpringbootCheckUrlList.contains(requestToBeSent.url())){
            if(debug)API.logging().output().println("springboot开始检查: "+requestToBeSent.url());
            springbootNote = springbootCheckRequest(requestToBeSent);
        }

        //fastjson检查过的url
        String fastjsonNote = "";
        if(debug) API.logging().output().println("fastjson检查是否开启 "+getFastjsonMenuWorkBox().isSelected());
        if(debug)API.logging().output().println("fastjson是否检查过 "+requestToBeSent.url()+" : "+FastjsonCheckUrlList.contains(requestToBeSent.url()));

        if(getFastjsonMenuWorkBox().isSelected() && !FastjsonCheckUrlList.contains(requestToBeSent.url())){
            if(debug)API.logging().output().println("fastjson开始检查: "+requestToBeSent.url());
            fastjsonNote = fastjsonCheckRequest(requestToBeSent);

        }

        //Overauth检查过的Url
        String authCheckNote = "";
        if(debug)API.logging().output().println("authcheck是否检查过 "+requestToBeSent.url()+" : "+AuthCheckUrlList.contains(requestToBeSent.url()));
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

    public String springbootCheckRequest(HttpRequestToBeSent requestToBeSent){


        String requestNumber = SpringbootCheckRequestNumber;
        String requestPath = requestToBeSent.path();
        int rowIndex = Integer.parseInt(requestNumber) - 1;

        if(Objects.equals(requestPath, "/")){
            SpringbootCheckPaths = new String[]{"/swagger-resources", "/actuator/env", "/druid/index.html", "/doc.html"};
        }else{
            String rootPath = requestPath.split("/")[1];
            SpringbootCheckPaths = new String[]{"/swagger-resources", "/actuator/env", "/druid/index.html", "/doc.html","/"+rootPath+"/swagger-resources", "/"+rootPath+"/actuator/env", "/"+rootPath+"/druid/index.html", "/"+rootPath+"/doc.html"};
        }

        String requestTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
        String[] inf = new String[]{
                requestNumber,
                requestToBeSent.toolSource().toolType().toolName(),
                requestToBeSent.method(),
                requestToBeSent.httpService().host(),
                requestPath,
                "",
                requestTime,
                "",
                "",
                "",
        };

        if(debug)API.logging().output().println("设置springboot的table");
        ((DefaultTableModel)getSpringbootLoggerTable().getModel()).addRow(inf);

        SpringbootTableEntry rowEntry;
        if(debug)API.logging().output().println("设置springboot的rowEntry");
        if(!SpringbootEntryMap.containsKey(requestNumber)){
            String type="Raw";
            rowEntry = new SpringbootTableEntry(inf);
            rowEntry.setType(type);
            rowEntry.setRawRequest(requestToBeSent);
            setSpringbootLoggerTableValueAt(type,rowIndex,"Type");
            SpringbootEntryMap.put(requestNumber, rowEntry);
        }

        SpringbootCheckRequestNumber = Integer.toString(Integer.parseInt(SpringbootCheckRequestNumber)+1);

        return  SPRINGBOOT+requestNumber;
    }

    public void springbootCheckResponse(HttpResponseReceived responseReceived, String springbootRequestNumber){
        int rowIndex = Integer.parseInt(springbootRequestNumber) - 1;

        if(debug)API.logging().output().println("springboot check 处理row: "+Integer.parseInt(springbootRequestNumber));

        String responseLength = Integer.toString(responseReceived.body().length());
        if(debug)API.logging().output().println("springboot Raw返回包长度:"+responseLength);
        setSpringbootLoggerTableValueAt(responseLength, rowIndex, "Length");
        SpringbootEntryMap.get(springbootRequestNumber).setLength(responseLength);

        //设置返回包时间
        String responseTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
        if(debug)API.logging().output().println("springboot Raw返回包时间:"+responseTime);
        setSpringbootLoggerTableValueAt(responseTime, rowIndex, "responseTime");
        SpringbootEntryMap.get(springbootRequestNumber).setResponseTime(responseTime);

        if(debug)API.logging().output().println("设置springboot Raw返回包");
        //设置Raw返回包
        SpringbootEntryMap.get(springbootRequestNumber).setRawResponse(responseReceived);
        //设置Raw返回包简化版

        if(debug)API.logging().output().println("设置springboot SimplifyRaw返回包");
        HttpResponse simplifyRawhttpResponse = responseReceived.withBody(responseReceived.body().subArray(0, Math.min(20000, Integer.parseInt(responseLength))));
        SpringbootEntryMap.get(springbootRequestNumber).setSimplifyRawResponse(simplifyRawhttpResponse);


        for(String path: SpringbootCheckPaths){
            if(debug)API.logging().output().println("springboot处理path: "+path);

            HttpRequest httpRequest = SpringbootEntryMap.get(springbootRequestNumber).getRawRequest().withPath(path); //.withParameter(null);
//            if(debug)API.logging().output().println(httpRequest);

            String requestUrl = httpRequest.url();
            if(SpringbootCheckUrlList.contains(requestUrl)) continue;

            String requestNumber = SpringbootCheckRequestNumber;
            String requestMethod = httpRequest.method();
            String requestHost = httpRequest.httpService().host();
            String requestTool = "Extensions";
            String requestPath = httpRequest.path();
            String requestTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
            String[] inf = new String[]{
                    requestNumber,
                    requestTool,
                    requestMethod,
                    requestHost,
                    requestPath,
                    "",
                    requestTime,
                    "",
                    "",
                    "",
            };
            if(debug)API.logging().output().println("springboot添加新行: "+requestNumber);
            ((DefaultTableModel)getSpringbootLoggerTable().getModel()).addRow(inf);
            SpringbootTableEntry springbootTableEntry = new SpringbootTableEntry(inf);
            if(path.contains("doc.html")){
                springbootTableEntry.setType("Doc");
            }else if(path.contains("swagger-resources")){
                springbootTableEntry.setType("Swagger");
            } else if (path.contains("druid/index.html")) {
                springbootTableEntry.setType("Druid");
            }else{
                springbootTableEntry.setType("Actuator");
            }

            SpringbootEntryMap.put(requestNumber, springbootTableEntry);

            SpringbootCheckUrlList.add(requestUrl);
            SpringbootCheckRequestNumber=Integer.toString(Integer.parseInt(SpringbootCheckRequestNumber)+1);
            new checkWorker("springboot", requestNumber, httpRequest).execute();
        }

        if(debug)API.logging().output().println("添加此处请求url到springboot已检测列表");
        SpringbootCheckUrlList.add(SpringbootEntryMap.get(springbootRequestNumber).getRawRequest().url());
    }

    public String fastjsonCheckRequest(HttpRequestToBeSent requestToBeSent){

        if(debug)API.logging().output().println("是否是json请求："+Fastjson.check(requestToBeSent));
        if(!Fastjson.check(requestToBeSent)) return "";

        String requestNumber = FastjsonCheckRequestNumber;
        String requestHost = requestToBeSent.httpService().host();
        String requestMethod = requestToBeSent.method();
        String requestTool = requestToBeSent.toolSource().toolType().toolName();
        String requestPath = requestToBeSent.path();
        String requestTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
        String[] inf = new String[]{requestNumber, requestTool, requestMethod, requestHost, requestPath, "", requestTime, "", "", ""};

        if(debug)API.logging().output().println("设置fastjson的table");
        ((DefaultTableModel)getFastjsonLoggerTable().getModel()).addRow(inf);

        if(debug)API.logging().output().println("设置fastjson的rowentry");
        FastjsonTableEntry rowEntry = new FastjsonTableEntry(inf);
        FastjsonEntryMap.put(requestNumber,rowEntry);
        rowEntry.setRawRequest(requestToBeSent);

        FastjsonCheckRequestNumber = Integer.toString(Integer.parseInt(FastjsonCheckRequestNumber)+1);

        return FASTJSON+requestNumber;
    }

    public void fastjsonCheckResponse(HttpResponseReceived responseReceived, String fastjsonRequestNumber){

        int rowIndex = Integer.parseInt(fastjsonRequestNumber) - 1;
        if(debug)API.logging().output().println("fastjson chekcn 处理row: "+Integer.parseInt(fastjsonRequestNumber));

        //设置Fastjson
        String responseLength = Integer.toString(responseReceived.body().length());
        if(debug)API.logging().output().println("fastjson Raw返回包长度:"+responseLength);
        setFastjsonLoggerTableValueAt(responseLength, rowIndex, "Length");
        FastjsonEntryMap.get(fastjsonRequestNumber).setLength(responseLength);

        //设置返回包时间
        String responseTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
        if(debug)API.logging().output().println("fastjson Raw返回包时间:"+responseTime);
        setFastjsonLoggerTableValueAt(responseTime, rowIndex, "responseTime");
        FastjsonEntryMap.get(fastjsonRequestNumber).setResponseTime(responseTime);

        //设置Raw返回包
        FastjsonEntryMap.get(fastjsonRequestNumber).setRawResponse(responseReceived);
        //设置Raw返回包简化版
        HttpResponse simplifyHighAuthhttpResponse = responseReceived.withBody(responseReceived.body().subArray(0, Math.min(20000, Integer.parseInt(responseLength))));
        FastjsonEntryMap.get(fastjsonRequestNumber).setSimplifyRawResponse(simplifyHighAuthhttpResponse);

        //进行fastjson检查
        if(debug)API.logging().output().println("fastjson 进行检查");
        String randomString = Helper.createRandomString(8);
        CollaboratorClient collaboratorClient = API.collaborator().createClient();
        CollaboratorPayload collaboratorPayload = collaboratorClient.generatePayload(randomString);

        FastjsonEntryMap.get(fastjsonRequestNumber).setDNSClient(collaboratorClient);
        FastjsonEntryMap.get(fastjsonRequestNumber).setDNSPayload(collaboratorPayload);

        if(debug)API.logging().output().println("设置dnslog字段");
        setFastjsonLoggerTableValueAt(collaboratorPayload.toString(),rowIndex,"DnsLog");
        String body =  ("\ufeff{,/*aab*/,'x_' : {/*aab*/\"@type\":\"java.net.InetSocketAddress\"{\"address\":/*aab*/,/*aa\"b*/ \"val\" :\""+collaboratorPayload+"\"}}}");

        HttpRequest fastjsonRequest = FastjsonEntryMap.get(fastjsonRequestNumber).getRawRequest().withBody(ByteArray.byteArray(body.getBytes(StandardCharsets.UTF_8)));

        new checkWorker("fastjson", fastjsonRequestNumber, fastjsonRequest).execute();

        new dnslogWorker(fastjsonRequestNumber).execute();
        if(debug)API.logging().output().println("检查完毕，将url添加到fastjson已检查列表");
        FastjsonCheckUrlList.add(FastjsonEntryMap.get(fastjsonRequestNumber).getRawRequest().url());
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


    public void authCheckResponse(HttpResponseReceived responseReceived, String authRequestNumber){

        if(debug)API.logging().output().println("返回包的标识："+authRequestNumber);

        //设置返回包
        AuthCheckEntryMap.get(authRequestNumber).setHighAuthResponse(responseReceived);
        if(debug)API.logging().output().println("成功设置responseReceived");

        //设置返回包长度
        String responseLength = Integer.toString(responseReceived.body().length());
        if(debug)API.logging().output().println("返回包长度:"+responseLength);
        setOverauthLoggerTableValueAt(responseLength,Integer.parseInt(authRequestNumber)-1,"Length");
        AuthCheckEntryMap.get(authRequestNumber).setLength(responseLength);

        //设置返回包时间
        String responseTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
        setOverauthLoggerTableValueAt(responseTime,Integer.parseInt(authRequestNumber)-1, "responseTime");
        AuthCheckEntryMap.get(authRequestNumber).setResponseTime(responseTime);

        //设置返回包简化版
        HttpResponse iSimplifyHighAuthhttpResponse = responseReceived.withBody(responseReceived.body().subArray(0, Math.min(20000, Integer.parseInt(responseLength))));
        AuthCheckEntryMap.get(authRequestNumber).setSimplifyHighAuthResponse(iSimplifyHighAuthhttpResponse);


        // 测试越权
        HttpRequest highAuthHttpRequest = AuthCheckEntryMap.get(authRequestNumber).HighAuthRequest;
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
            new checkWorker("lowAuth",authRequestNumber,lowAuthHttpRequest).execute();
        }

        //发送未授权请求
        if(debug)API.logging().output().println("发送未授权请求包");
        HttpRequest unAuthHttpRequest =  HttpRequest.httpRequest(httpService,highAuthHttpReuqestString.replace(highAuth,""));
        new checkWorker("unAuth",authRequestNumber,unAuthHttpRequest).execute();

        //将url加入到已测试列表
        if(debug)API.logging().output().println("UrlList Add："+highAuthHttpRequest.url());
        AuthCheckUrlList.add(highAuthHttpRequest.url());

    }

    public static class dnslogWorker extends SwingWorker<Void, Void>{

        public String requestNumber;

        public dnslogWorker(String requestNumber){
            this.requestNumber = requestNumber;
        }

        @Override
        protected Void doInBackground() throws Exception {
            if(debug)API.logging().output().println("进行dnslog检查");
            CollaboratorClient collaboratorClient= FastjsonEntryMap.get(requestNumber).getDNSClient();
            CollaboratorPayload collaboratorPayload = FastjsonEntryMap.get(requestNumber).getDNSPayload();
            List<Interaction> interactionList;
            for(int i=0; i<20; i++){
                API.logging().output().println("test");

                interactionList = collaboratorClient.getInteractions(ObjectFactoryLocator.FACTORY.interactionIdFilter(collaboratorPayload.id().toString()));
                if(interactionList.size()!=0){
                    if(debug)API.logging().output().println("设置是否存在fastjson漏洞");
                    setFastjsonLoggerTableValueAt("True", Integer.parseInt(requestNumber) - 1, "FastJson");
                    break;
                }
                Thread.sleep(1000);
            }
            if(debug)API.logging().output().println("fastjson 检查完毕");
            return null;
        }
    }

    public static class checkWorker extends SwingWorker<Void, Void> {
        public String number;
        public String type;
        public HttpRequest httpRequest;
        public HttpRequestResponse httpRequestResponse;

        public checkWorker(String type, String number, HttpRequest httpRequest) {
            this.type = type;
            this.number = number;
            this.httpRequest = httpRequest;

        }
        @Override
        protected Void doInBackground() {
            if(debug) API.logging().output().println("start sendRequest work");
            httpRequestResponse = API.http().sendRequest(httpRequest);
            HttpRequest  ihttpRequest = httpRequestResponse.request();
            HttpResponse ihttpResponse = httpRequestResponse.response();
            HttpResponse iSimplifyhttpResponse = ihttpResponse.withBody(ihttpResponse.body().subArray(0,Math.min(20000,ihttpResponse.body().length())));
            int rowIndex = Integer.parseInt(number) - 1;
            switch (type) {
                case "unAuth" -> {
                    AuthCheckEntryMap.get(number).setUnAuthRequest(ihttpRequest);
                    AuthCheckEntryMap.get(number).setUnAuthResponse(ihttpResponse);
                    AuthCheckEntryMap.get(number).setSimplifyUnAuthResponse(iSimplifyhttpResponse);
                    double unAuthSimilarity = CharBasedSimilarity.getInstance().getSimilarity(AuthCheckEntryMap.get(number).HighAuthResponse.bodyToString(), ihttpResponse.bodyToString());
                    if (unAuthSimilarity >= 0.9) {
                        AuthCheckEntryMap.get(number).UnAuth = "True";
                        setOverauthLoggerTableValueAt("True", rowIndex, "UnAuth");
                    }
                }
                case "lowAuth" -> {
                    AuthCheckEntryMap.get(number).setLowAuthRequest(ihttpRequest);
                    AuthCheckEntryMap.get(number).setUnAuthResponse(ihttpResponse);
                    AuthCheckEntryMap.get(number).setSimplifyLowAuthResponse(iSimplifyhttpResponse);
                    double lowAuthSimilarity = CharBasedSimilarity.getInstance().getSimilarity(AuthCheckEntryMap.get(number).HighAuthResponse.bodyToString(), ihttpResponse.bodyToString());
                    if (lowAuthSimilarity >= 0.9) {
                        AuthCheckEntryMap.get(number).OverAuth = "True";
                        setOverauthLoggerTableValueAt("True", rowIndex, "OverAuth");
                    }
                }

                case "fastjson" -> {
                    if(debug)API.logging().output().println("设置fastjson的fastjsonRequest");
                    FastjsonEntryMap.get(number).setFastjsonRequest(ihttpRequest);
                    FastjsonEntryMap.get(number).setFastjsonResponse(ihttpResponse);
                    FastjsonEntryMap.get(number).setSimplifyFastjsonResponse(iSimplifyhttpResponse);
                }

                case "springboot" -> {
                    if(debug)API.logging().output().println("发起springboot请求:"+ihttpRequest.path());
                    String responseLength = Integer.toString(ihttpResponse.body().length());
                    String responseTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
                    SpringbootEntryMap.get(number).setRawRequest(ihttpRequest);
                    SpringbootEntryMap.get(number).setRawResponse(ihttpResponse);
                    SpringbootEntryMap.get(number).setSimplifyRawResponse(iSimplifyhttpResponse);
                    SpringbootEntryMap.get(number).setLength(responseLength);
                    SpringbootEntryMap.get(number).setResponseTime(responseTime);

                    setSpringbootLoggerTableValueAt(responseLength, rowIndex, "Length");
                    setSpringbootLoggerTableValueAt(responseTime, rowIndex, "responseTime");
                    setSpringbootLoggerTableValueAt(SpringbootEntryMap.get(number).getType(),rowIndex,"Type");
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
