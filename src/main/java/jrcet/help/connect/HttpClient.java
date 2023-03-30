package jrcet.help.connect;

import burp.BurpExtender;
import com.sun.org.apache.xerces.internal.xs.StringList;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class HttpClient {
    private String  Method;
    private String Path;
    private String Url;
    private RequestBody Body;
    private String Protocol;
    private OkHttpClient HttpClient;
    public static void main(String[] args) throws Exception {
        String requestPacket="GET /reddragonmag/a/images/captcha?d=0.8849168032703398 HTTP/1.1\n" +
                "Host: aftp.tfol.net\n" +
                "Cookie: javamg.session.id=6669ffa0f0ea46ae89dd4f0a15f23397; JSESSIONID=55DBC3109A927384ABC271ED98C17C05; Hm_lvt_8119551003482a317108c16dc2b22508=1679641059; Hm_lpvt_8119551003482a317108c16dc2b22508=1679643559\n" +
                "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:109.0) Gecko/20100101 Firefox/111.0\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8\n" +
                "Accept-Language: en-US,en;q=0.5\n" +
                "Accept-Encoding: gzip, deflate\n" +
                "Upgrade-Insecure-Requests: 1\n" +
                "Sec-Fetch-Dest: document\n" +
                "Sec-Fetch-Mode: navigate\n" +
                "Sec-Fetch-Site: none\n" +
                "Sec-Fetch-User: ?1\n" +
                "Te: trailers\n" +
                "Connection: close\n" +
                "\n";
        String url = "https://aftp.tfol.net/reddragonmag/a/images/captcha?d=0.8849168032703398";

        HttpClient httpClient = new HttpClient(url,requestPacket);
        byte[] responseText = httpClient.doRequest();
//        System.out.println(responseText);

    }
    public HttpClient(String url, String requestPacket) throws Exception {

        BurpExtender.stdout.println("httpclient");
        requestPacket=requestPacket.replace("\r\n","\n");
        if(!requestPacket.contains("\n\n")){
            throw new Exception("请求包格式错误1");
        }
        String[] requestPacketArray = requestPacket.split("\n\n");
//        System.out.println(requestPacketArray.length);
        if(requestPacketArray.length!=2 ){
            if(requestPacketArray.length==1 && requestPacket.endsWith("\n\n")){
                Body = RequestBody.create(new byte[]{});
            }else{
                throw new Exception("请求包格式错误2");
            }
        }else{
            Body = RequestBody.create(requestPacketArray[1].getBytes());
        }

        if(!requestPacketArray[0].startsWith("GET") && !requestPacketArray[0].startsWith("POST")){
            throw new Exception("请求包格式错误2");
        }

        List<String> headers = new ArrayList<>(Arrays.asList(requestPacketArray[0].split("\n")));
        String[] requestPacketFirstLine=headers.get(0).split(" ");

        if(requestPacketFirstLine.length!=3){
            throw new Exception("请求包格式错误");
        }
        Url = url;
        Method = requestPacketFirstLine[0];
        Protocol = requestPacketFirstLine[2];
        headers.remove(0);

        BasicParamsInterceptor basicParamsInterceptor = new BasicParamsInterceptor.Builder()
                .addHeaderLinesList(headers)
                .build();

        HttpClient = new OkHttpClient.Builder()
                .connectTimeout(5000, TimeUnit.MILLISECONDS) //连接超时
                .readTimeout(5000, TimeUnit.MILLISECONDS) //读取超时
                .writeTimeout(5000, TimeUnit.MILLISECONDS) //写入超时
                .addInterceptor(basicParamsInterceptor) //添加拦截器
                .retryOnConnectionFailure(true) //是否重试
                .sslSocketFactory(SSLSocketClient.getSSLSocketFactory(),SSLSocketClient.getX509TrustManager())//配置
                .hostnameVerifier(SSLSocketClient.getHostnameVerifier())//配置
                .build();
    }

    public byte[] doRequest(){
        Request request;
        byte[] test;
        try{
            if(Objects.equals(Method, "GET")){
                 request = new Request.Builder()
                        .url(Url)
                        .build();
            }else{
                request = new Request.Builder()
                        .url(Url)
                        .post(Body)
                        .build();
            }
            Response response = HttpClient.newCall(request).execute();
            test = Objects.requireNonNull(response.body()).bytes();
        }catch (Exception e){
            test=e.toString().getBytes();
        }
        return test;
    }


}
