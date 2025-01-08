package jrcet.frame.Scanner.Springboot;

import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.http.message.responses.HttpResponse;

public class SpringbootTableEntry {

    private String RequestNumber;
    private String RequestTool;
    private String RequestMethod;
    private String RequestHost;
    private String RequestPath;
    private String RootUrl;
    private Integer RowIndex;
    private String RequestTime;

    private String ResponseTime;

    private String Length;

    private HttpRequest RawRequest;

    private HttpResponse RawResponse;

    private boolean Removed = false;
    private String Type = "Raw";
    private String Code;
    private String Vul;
    public void setCode(String code) {
        Code = code;
    }
    public String getCode(){
        return Code;
    }

    public void setRootUrl(String rootUrl) {
        RootUrl = rootUrl;
    }

    public String getRootUrl(){
        return RootUrl;
    }

    public void setVul(String vul){
        Vul = vul;
    }

    public String getVul(){
        return Vul;
    }
    public SpringbootTableEntry(){}
    public SpringbootTableEntry(String[] inf){
        this.RequestNumber = inf[0];
        this.RequestTool = inf[1];
        this.RequestMethod = inf[2];
        this.RequestHost = inf[3];
        this.RequestPath = inf[4];
        this.Code = inf[5];
        this.Length = inf[6];
        this.RequestTime = inf[7];
        this.ResponseTime = inf[8];
        this.Type = inf[9];
        this.Vul = inf[10];
    }

    public String[] toTableRow(){
        return new String[]{
                RequestNumber,
                RequestTool,
                RequestMethod,
                RequestHost,
                RequestPath,
                Code,
                Length,
                RequestTime,
                ResponseTime,
                Type,
                Vul
        };
    }


    public void setType(String type){
        this.Type = type;
    }

    public String getType(){
        return Type;
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

    public Integer getRowIndex(){
        return RowIndex;
    }

    public void setRowIndex(Integer rowIndex){
        this.RowIndex = rowIndex;
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


    public void setRawRequest(HttpRequest httpRequest){
        this.RawRequest = httpRequest;
    }

    public HttpRequest getRawRequest(){
        return RawRequest;
    }

    public void setRawResponse(HttpResponse httpResponse){
        RawResponse = httpResponse;
    }

    public HttpResponse getRawResponse(){
        return RawResponse;
    }


}
