package jrcet.frame.Scanner.Springboot;

import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.http.message.responses.HttpResponse;

public class SpringbootTableEntry {

    //"#","Tool","Method","Host","Path","Length","requestTime","responseTime","Doc","Druid","Swagger","Actuator"
    private String RequestNumber;
    private String RequestTool;
    private String RequestMethod;
    private String RequestHost;
    private String RequestPath;
    private String RequestTime;

    private String ResponseTime;

    private String Length;

    private HttpRequest RawRequest;

    private HttpResponse RawResponse;

    private HttpResponse SimplifyRawResponse;


    private HttpRequest DocRequest;

    private HttpResponse DocResponse;

    private HttpResponse SimplifyDocResponse;


    private HttpRequest DruidRequest;

    private HttpResponse DruidResponse;

    private HttpResponse SimplifyDruidResponse;

    private HttpRequest ActuatorRequest;

    private HttpResponse ActuatorResponse;

    private HttpResponse SimplifyActuatorResponse;


    private HttpRequest SwaggerRequest;

    private HttpResponse SwaggerResponse;

    private HttpRequest SimplifySwaggerRequest;

    private HttpResponse SimplifySwaggerResponse;

    private boolean Removed = false;

    private String Type = "Raw";


    public SpringbootTableEntry(){}
    public SpringbootTableEntry(String[] inf){
        this.RequestNumber = inf[0];
        this.RequestTool = inf[1];
        this.RequestMethod = inf[2];
        this.RequestHost = inf[3];
        this.RequestPath = inf[4];
        this.Length = inf[5];
        this.RequestTime = inf[6];
        this.ResponseTime = inf[7];
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

    public void setSimplifyRawResponse(HttpResponse httpResponse){
        SimplifyRawResponse = httpResponse;
    }
    public HttpResponse getSimplifyRawResponse(){
        return SimplifyRawResponse;
    }


    public void setDocRequest(HttpRequest httpRequest){
        this.DocRequest = httpRequest;
    }

    public HttpRequest getDocRequest(){
        return DocRequest;
    }

    public void setDocResponse(HttpResponse httpResponse){
        DocResponse = httpResponse;
    }

    public HttpResponse getDocResponse(){
        return DocResponse;
    }

    public void setSimplifyDocResponse(HttpResponse httpResponse){
        SimplifyDocResponse = httpResponse;
    }
    public HttpResponse getSimplifyDocResponse(){
        return SimplifyDocResponse;
    }

    public void setDruidRequest(HttpRequest httpRequest){
        this.DruidRequest = httpRequest;
    }

    public HttpRequest getDruidRequest(){
        return DruidRequest;
    }

    public void setDruidResponse(HttpResponse httpResponse){
        DruidResponse = httpResponse;
    }

    public HttpResponse getDruidResponse(){
        return DruidResponse;
    }

    public void setSimplifyDruidResponse(HttpResponse httpResponse){
        SimplifyDruidResponse = httpResponse;
    }
    public HttpResponse getSimplifyDruidResponse(){
        return SimplifyDruidResponse;
    }

    public void setActuatorRequest(HttpRequest httpRequest){
        this.ActuatorRequest = httpRequest;
    }

    public HttpRequest getActuatorRequest(){
        return ActuatorRequest;
    }

    public void setActuatorResponse(HttpResponse httpResponse){
        ActuatorResponse = httpResponse;
    }

    public HttpResponse getActuatorResponse(){
        return ActuatorResponse;
    }

    public void setSimplifyActuatorResponse(HttpResponse httpResponse){
        SimplifyActuatorResponse = httpResponse;
    }
    public HttpResponse getSimplifyActuatorResponse(){
        return SimplifyActuatorResponse;
    }
    public void setSwaggerRequest(HttpRequest httpRequest){
        this.SwaggerRequest = httpRequest;
    }

    public HttpRequest getSwaggerRequest(){
        return SwaggerRequest;
    }

    public void setSwaggerResponse(HttpResponse httpResponse){
        SwaggerResponse = httpResponse;
    }

    public HttpResponse getSwaggerResponse(){
        return SwaggerResponse;
    }

    public void setSimplifySwaggerResponse(HttpResponse httpResponse){
        SimplifySwaggerResponse = httpResponse;
    }
    public HttpResponse getSimplifySwaggerResponse(){
        return SimplifySwaggerResponse;
    }

}
