package jrcet.frame.Scanner.Fastjson;

import burp.api.montoya.collaborator.CollaboratorClient;
import burp.api.montoya.collaborator.CollaboratorPayload;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.http.message.responses.HttpResponse;
import jrcet.frame.Dencrypt.Base.Base;

public class FastjsonTableEntry {



    //"#","Tool","Method","Host","Path","Length","requestTime","responseTime","Dnslog","Fastjson"
    private String RequestNumber;
    private String RequestTool;
    private String RequestMethod;
    private String RequestHost;
    private String RequestPath;
    private String RequestTime;

    private CollaboratorClient DNSClient;

    private CollaboratorPayload DNSPayload;
    private String ResponseTime;

    private String Length;

    private String Dnslog;

    private String Fastjson;

    private HttpRequest RawRequest;

    private HttpRequest FastjsonRequest;

    private HttpResponse RawResponse;

    private HttpResponse FastjsonResponse;

    private HttpResponse SimplifyRawResponse;

    private HttpResponse SimplifyFastjsonResponse;

    private boolean Removed = false;

    public void setRemoved(boolean removed) {
        Removed = removed;
    }

    public boolean getRemoved(){
        return Removed;
    }

    public FastjsonTableEntry(String[] inf){
        this.RequestNumber = inf[0];
        this.RequestTool = inf[1];
        this.RequestMethod = inf[2];
        this.RequestHost = inf[3];
        this.RequestPath = inf[4];
        this.Length = inf[5];
        this.RequestTime = inf[6];
        this.ResponseTime = inf[7];
    }

    public void setDNSClient (CollaboratorClient collaboratorClient){
        this.DNSClient = collaboratorClient;
    }

    public void setDNSPayload(CollaboratorPayload collaboratorPayload){
        this.DNSPayload = collaboratorPayload;
    }

    public CollaboratorClient getDNSClient(){
        return DNSClient;
    }

    public CollaboratorPayload getDNSPayload(){
        return DNSPayload;
    }
    public HttpRequest getRawRequest(){
        return RawRequest;
    }

    public HttpRequest getFastjsonRequest(){
        return FastjsonRequest;
    }

    public HttpResponse getSimplifyFastjsonResponse(){
        return SimplifyFastjsonResponse;
    }

    public HttpResponse getSimplifyRawResponse(){
        return SimplifyRawResponse;
    }
    public HttpResponse getFastjsonResponse(){
        return FastjsonResponse;
    }
    public void setResponseTime(String responseTime){
        ResponseTime = responseTime;
    }
    public void setSimplifyRawResponse(HttpResponse httpResponse){
        SimplifyRawResponse = httpResponse;
    }

    public void setSimplifyFastjsonResponse(HttpResponse httpResponse){
        SimplifyFastjsonResponse = httpResponse;
    }

    public void setDnslog(String dnslog) {
        Dnslog = dnslog;
    }

    public void setFastjson(String fastjson) {
        Fastjson = fastjson;
    }

    public void setLength(String length){
        Length = length;
    }

    public void setRawRequest(HttpRequest httpRequest){
        RawRequest = httpRequest;
    }

    public void setFastjsonRequest(HttpRequest httpRequest){
        FastjsonRequest = httpRequest;
    }

    public void setRawResponse(HttpResponse httpResponse){
        RawResponse = httpResponse;
    }

    public void setFastjsonResponse(HttpResponse httpResponse){
        FastjsonResponse = httpResponse;
    }



}
