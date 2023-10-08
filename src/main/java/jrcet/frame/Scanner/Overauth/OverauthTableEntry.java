package jrcet.frame.Scanner.Overauth;

import burp.api.montoya.http.message.params.ParsedHttpParameter;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.http.message.responses.HttpResponse;

import java.util.ArrayList;

public class OverauthTableEntry {
    private String RequestNumber;
    private String RequestTool;
    private String RequestMethod;
    private String RequestHost;
    private String RequestPath;
    private String RequestTime;

    private String ResponseTime;

    private Integer RowIndex;

    private String Length;

    private HttpRequest HighAuthRequest = null;

    private HttpResponse HighAuthResponse = null;

    private HttpRequest LowAuthRequest = null;

    private HttpResponse LowAuthResponse = null;


    private HttpRequest UnAuthRequest = null;

    private HttpResponse UnAuthResponse = null;


    private String OverAuth;

    private String UnAuth;

    private ArrayList<ParsedHttpParameter> HorizontalOverAuthParameters;

    private boolean Removed = false;

    private String Code;

    public void setCode(String code) {
        Code = code;
    }

    public String getCode(){
        return Code;
    }

//    public Boolean Check = false;
    public OverauthTableEntry(String[] inf){
        this.RequestNumber = inf[0];
        this.RequestTool = inf[1];
        this.RequestMethod = inf[2];
        this.RequestHost = inf[3];
        this.RequestPath = inf[4];
        this.Length = inf[5];
        this.RequestTime = inf[6];
        this.ResponseTime = inf[7];
    }


    public void setHorizontalOverAuthParameters(ArrayList<ParsedHttpParameter> parsedHttpParameters){
        this.HorizontalOverAuthParameters = parsedHttpParameters;
    }

    public ArrayList<ParsedHttpParameter> getHorizontalOverAuthParameters(){
        return HorizontalOverAuthParameters;
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

    public void setRowIndex(Integer id){
        this.RowIndex = id;
    }

    public Integer getRowIndex(){
        return RowIndex;
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



    public void setHighAuthRequest(HttpRequest request){
        this.HighAuthRequest = request;
    }

    public HttpRequest getHighAuthRequest() {
        return HighAuthRequest;
    }


    public void setHighAuthResponse(HttpResponse response){
        this.HighAuthResponse = response;
    }

    public void setLowAuthRequest(HttpRequest resquest){
        this.LowAuthRequest = resquest;
    }


    public HttpRequest getLowAuthRequest() {
        return LowAuthRequest;
    }

    public void setLowAuthResponse(HttpResponse response){
        this.LowAuthResponse = response;
    }

    public HttpResponse getLowAuthResponse() {
        return LowAuthResponse;
    }

    public void setUnAuthRequest(HttpRequest resquest){
        this.UnAuthRequest = resquest;
    }

    public HttpRequest getUnAuthRequest() {
        return UnAuthRequest;
    }

    public HttpResponse getUnAuthResponse(){
        return UnAuthResponse;
    }
    public void setUnAuthResponse(HttpResponse response){
        this.UnAuthResponse = response;
    }

    public HttpResponse getHighAuthResponse() {
        return HighAuthResponse;
    }


    public void setUnAuth(String aTrue) {
        this.UnAuth = aTrue;
    }

    public void setOverAuth(String aTrue) {
        this.OverAuth = aTrue;
    }
}
