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

    private Integer RowIndex;
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

    public void setRowIndex(Integer rowIndex){
        RowIndex = rowIndex;
    }

    public Integer getRowIndex() {
        return RowIndex;
    }

    public void setRemoved(boolean removed) {
        Removed = removed;
    }

    public boolean getRemoved(){
        return Removed;
    }

    public void setLength(String length){
        Length = length;
    }

    public String getLength(){
        return Length;
    }

    public void setResponseTime(String responseTime){
        this.ResponseTime = responseTime;
    }

    public String getResponseTime(){
        return ResponseTime;
    }

    public void setRequestNumber(String requestNumber){
        this.RequestNumber = requestNumber;
    }

    public String getRequestNumber(){
        return RequestNumber;
    }

    public void setRequestHost(String requestHost) {
        RequestHost = requestHost;
    }

    public String getRequestHost(){
        return RequestHost;
    }


    public void setRequestMethod(String requestMethod) {
        RequestMethod = requestMethod;
    }

    public String getRequestMethod(){
        return RequestMethod;
    }

    public void setRequestPath(String requestPath) {
        RequestPath = requestPath;
    }

    public String getRequestPath(){
        return RequestPath;
    }

    public void setRequestTime(String requestTime) {
        RequestTime = requestTime;
    }

    public String getRequestTime(){
        return RequestTime;
    }

    public void setRequestTool(String requestTool) {
        RequestTool = requestTool;
    }

    public String getRequestTool(){
        return RequestTool;
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


    public HttpResponse getRawResponse(){
        return RawResponse;
    }
    public HttpResponse getFastjsonResponse(){
        return FastjsonResponse;
    }


    public void setDnslog(String dnslog) {
        Dnslog = dnslog;
    }

    public void setFastjson(String fastjson) {
        Fastjson = fastjson;
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
