package jrcet.frame.Scanner.Overauth;

import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.http.message.responses.HttpResponse;

public class OverauthTableEntry {
    public String RequestNumber;
    public String RequestTool;
    public String RequestMethod;
    public String RequestHost;
    public String RequestPath;
    public String RequestTime;

    public String ResponseTime;

    public String Length;

    public HttpRequest HighAuthRequest;

    public HttpResponse HighAuthResponse;
    public HttpResponse simplifyHighAuthResponse;

    public HttpRequest LowAuthRequest;

    public HttpResponse LowAuthResponse;

    public HttpResponse simplifyLowAuthResponse;

    public HttpRequest UnAuthRequest;

    public HttpResponse UnAuthResponse;

    public HttpResponse simplifyUnAuthResponse;

    public String OverAuth;

    public String UnAuth;

    public boolean Removed = false;

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

    public void setHighAuthRequest(HttpRequest request){
        this.HighAuthRequest = request;
    }

    public void setHighAuthResponse(HttpResponse response){
        this.HighAuthResponse = response;
    }

    public void setLowAuthRequest(HttpRequest resquest){
        this.LowAuthRequest = resquest;
    }

    public void setLowAuthResponse(HttpResponse response){
        this.LowAuthResponse = response;
    }

    public void setUnAuthRequest(HttpRequest resquest){
        this.UnAuthRequest = resquest;
    }

    public void setUnAuthResponse(HttpResponse response){
        this.UnAuthResponse = response;
    }

    public void setResponseTime(String time){
        this.ResponseTime = time;
    }

    public void setLength(String length){
        this.Length = length;
    }

    public void setSimplifyUnAuthResponse(HttpResponse response) {
        this.simplifyUnAuthResponse = response;
    }

    public void setSimplifyHighAuthResponse(HttpResponse response) {
        this.simplifyHighAuthResponse = response;
    }

    public void setSimplifyLowAuthResponse(HttpResponse response) {
        this.simplifyLowAuthResponse = response;
    }
}
