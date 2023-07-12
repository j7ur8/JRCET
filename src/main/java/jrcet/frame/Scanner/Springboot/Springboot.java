package jrcet.frame.Scanner.Springboot;


import jrcet.frame.Setting.Setting;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.http.message.responses.HttpResponse;

import static burp.MyExtender.API;

public class Springboot {

    public static ArrayList<String> VulPathList =new ArrayList<>(Arrays.asList("/swagger-resources", "/actuator/env", "/druid/index.html", "/doc.html"));
    private static final HashSet<String> UrlSet = new HashSet<>();

    private static String CurrentUrl;
    public static void doScan(HttpRequestResponse httpRequestResponse){

        if(!check(httpRequestResponse)) return;

        ArrayList<String> path_list = get_path_list(httpRequestResponse);

        HttpRequest httpRequest = httpRequestResponse.request();

        for(String path : path_list){

            Pattern pattern = Pattern.compile("(.*)? /");
            Matcher matcher = pattern.matcher(httpRequest.withBody("").
                    withRemovedHeader("Content-Length").
                    withRemovedHeader("Sec-Ch-Ua").
                    withRemovedHeader("Sec-Ch-Ua-Mobile").
                    withRemovedHeader("Sec-Ch-Ua-Platform").
                    withRemovedHeader("Sec-Fetch-Site").
                    withRemovedHeader("Sec-Fetch-Mode").
                    withRemovedHeader("Sec-Fetch-Dest").
                    withRemovedHeader("Last-Modified").
                    withRemovedHeader("Cache-Control").
                    withRemovedHeader("Expires").
                    withRemovedHeader("Last-Modified").
                    toString()
            );

            String result = matcher.replaceAll("GET /");

            httpRequest = HttpRequest.httpRequest(httpRequest.httpService(),result).withPath(path);
            httpRequestResponse = API.http().sendRequest(httpRequest);
            HttpResponse httpResponse = httpRequestResponse.response();
        }

        UrlSet.add(CurrentUrl);
    }

    private static ArrayList<String> get_path_list(HttpRequestResponse httpRequestResponse){
        URL url = null;

        try{
            url = new URL(httpRequestResponse.request().url());
        }catch (Exception e){
            API.logging().error().println(e.getMessage());
        }
        assert url != null;

        ArrayList<String> root_paths=new ArrayList<>();
        root_paths.add("");
        String[] roots = url.getPath().split("/");
        if(roots.length==2){
            root_paths.add("/"+roots[1]);
        }else if(roots.length>=3){
            root_paths.add("/"+roots[1]);
            root_paths.add("/"+roots[1]+"/"+roots[2]);
        }

        ArrayList<String> path_list = new ArrayList<>();
        for(String dict_path : VulPathList){
            for(String root_path : root_paths){
                if(Setting.DEBUG) API.logging().output().println(root_path+dict_path);
                path_list.add(root_path+dict_path);
            }
        }

        return path_list;
    }

    private static boolean check(HttpRequestResponse httpRequestResponse){
        CurrentUrl = httpRequestResponse.request().withPath("/").url();
        return !UrlSet.contains(CurrentUrl);
    }
}
