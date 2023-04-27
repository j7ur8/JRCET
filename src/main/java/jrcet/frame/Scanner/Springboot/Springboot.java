package jrcet.frame.Scanner.Springboot;

import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.http.message.responses.HttpResponse;
import jrcet.frame.Setting.Setting;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;

import static burp.MyExtender.api;

public class Springboot {

    public static String[] dict_paths=new String[]{"/swagger-resources", "/actuator/env","/druid/index.html","/doc.html"};
    private static final HashSet<String> url_set = new HashSet<>();

    private static String current_url;
    public static void doScan(HttpRequestResponse httpRequestResponse){

        if(!check(httpRequestResponse)) return;

        ArrayList<String> path_list = get_path_list(httpRequestResponse);

        HttpRequest httpRequest = httpRequestResponse.httpRequest();

        for(String path : path_list){
            httpRequest = httpRequest.withPath(path);
            httpRequestResponse = api.http().issueRequest(httpRequest);
            HttpResponse httpResponse = httpRequestResponse.httpResponse();
        }

        url_set.add(current_url);
    }

    private static ArrayList<String> get_path_list(HttpRequestResponse httpRequestResponse){
        URL url = null;

        try{
            url = new URL(httpRequestResponse.httpRequest().url());
        }catch (Exception e){
            api.logging().error().println(e.getMessage());
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
        for(String dict_path : dict_paths){
            for(String root_path : root_paths){
                if(Setting.DEBUG)api.logging().output().println(root_path+dict_path);
                path_list.add(root_path+dict_path);
            }
        }

        return path_list;
    }

    private static boolean check(HttpRequestResponse httpRequestResponse){
        current_url = httpRequestResponse.httpRequest().withPath("/").url();
        return !url_set.contains(current_url);
    }
}
