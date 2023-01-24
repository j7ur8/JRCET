/**
 * Copyright (c) 2019 c0ny1 (https://github.com/c0ny1/captcha-killer)
 * License: MIT
 */
package jrcet.help.d4ocr.network;

import burp.BurpExtender;
import burp.IHttpRequestResponse;
import burp.IRequestInfo;
import jrcet.help.Helper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpClient {
    private final String url;
    private String protocol;
    private String method;
    private String httpversion;
    private String host;
    private int port;
    private String path;
    private HttpService service;
    private final Map<String,String> headers = new HashMap<>();
    private String data;
    private String raw;

    public HttpClient(String url, String raw) throws IOException {
        this.url = url;
        this.raw = processLine(raw);
        //解析标签
//        parseLabel();
        //解析Request各个属性
        parserRequest();
        //更新Content-Length
        updateContentLength();
    }

    /**
     * 将请求包头中\n替换为\r\n,因为\n可能会导致某些服务器报错，无法正确识别请求包。
     * @param reqraw
     * @return
     */
    public String processLine(String reqraw){

        String header = null;
        String body = null;
        String request = null;

        if(reqraw.startsWith("GET")){
            header = reqraw;
            request = header;
        }else if(reqraw.startsWith("POST")){
            int n = reqraw.contains("\n\n") ? reqraw.indexOf("\n\n") : reqraw.indexOf("\r\n\r\n");
            header = reqraw.substring(0, n).trim();
            body = reqraw.substring(n + 1, reqraw.length()).trim();
            if(header.contains("\n") && !header.contains("\r\n")){
                header = header.replace("\n","\r\n");
            }
            request = header + "\r\n\r\n" + body;
        }else{
            return reqraw;
        }

        return request;
    }


    public String getHttpService(){
        return service.toString();
    }

    public String getRaw(){
        return this.raw;
    }


    /**
     * 解析标签，可以参考下
     */
//    public void parseLabel() throws IOException {
//        LableParser parser = new LableParser(byteImg);
//
//        raw = parser.parseAllLable(raw);
//
//    }

    private void parserRequest(){

        if(Helper.isURL(this.url)){
            service = new HttpService(this.url);
            try {
                IRequestInfo requestInfo = BurpExtender.helpers.analyzeRequest(service, this.raw.getBytes());
                requestInfo.getBodyOffset();

                this.method = requestInfo.getMethod();

                for (String header : requestInfo.getHeaders()) {
                    if (header.contains(this.method) && header.contains("HTTP/")) {
                        this.path = header.split(" ")[1];
                        this.httpversion = header.split(" ")[2];
                        continue;
                    }

                    if (header.indexOf(":") > 0) {
                        String key = header.substring(0, header.indexOf(":"));
                        String value = Helper.trimStart(header.substring(header.indexOf(":")+1));
                        this.headers.put(key, value);
                    }
                }

                if (this.method.equals("POST")) {
                    this.data = this.raw.substring(requestInfo.getBodyOffset(), this.raw.length());
                }
            }catch (Exception ignored){
            }
        }

    }

    /**
     * 更新请求包的Content-Length头
     * 注意：不更新该头部，可能会导致服务端无法获取完整的请求信息。
     * @return
     */
    public void updateContentLength(){
        /**
         * 在处理GET数据包时,要注意包结果严格来讲最后要有两个\r\n。有的web服务器对数据包要求比较严格，可能会导致请求识别。
         * 该问题曾出现在请求某网站的验证码时，返回了403状态。
         */
        if(method.equals("POST")) {
            int length = data.length();
            headers.put("Content-Length", String.valueOf(length));
            StringBuilder reqLine = new StringBuilder(String.format("%s %s %s", method, path, httpversion));
            reqLine.append("\r\n");
            for(Map.Entry<String,String> header:headers.entrySet()){
                String line = String.format("%s: %s",header.getKey(),header.getValue());
                reqLine.append(line);
                reqLine.append("\r\n");
            }

            reqLine.append("\r\n");
            reqLine.append(data);
            this.raw = reqLine.toString();
        }
    }


    public byte[] doRequest(){
        byte[] req = raw.getBytes();
        try {
            IHttpRequestResponse reqrsp = BurpExtender.callbacks.makeHttpRequest(service, req);
            return reqrsp.getResponse();
        }catch (Exception e){
            e.printStackTrace();
            BurpExtender.stdout.println(e);
        }
        return null;

    }
}
