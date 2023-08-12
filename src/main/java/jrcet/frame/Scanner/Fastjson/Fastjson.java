package jrcet.frame.Scanner.Fastjson;

import burp.api.montoya.http.handler.HttpRequestToBeSent;
import burp.api.montoya.http.message.HttpHeader;
import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.http.message.responses.HttpResponse;
import jrcet.frame.Setting.Setting;
import jrcet.help.Helper;

import javax.swing.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import static burp.MyExtender.*;
import static jrcet.frame.Scanner.Fastjson.FastjsonComponent.FastjsonComponentPanel;

public class Fastjson {
    public static String DNSLOG = "";
    public static String TOKEN = "";

    private static HashMap<String, Integer> ColumnMap =  new HashMap<>() {
        {
            put("#", 0);
            put("Tool", 1);
            put("Method", 2);
            put("Host", 3);
            put("Path", 4);
            put("Length", 5);
            put("requestTime", 6);
            put("responseTime", 7);
            put("DnsLog", 8);
            put("FastJson", 9);
        }
    };

    public static boolean check(HttpRequestToBeSent requestToBeSent){

        List<HttpHeader> httpHeaders = requestToBeSent.headers();
        for(HttpHeader httpHeader: httpHeaders){
//            API.logging().output().println(httpHeader.name()+":"+httpHeader.value());
            if(Objects.equals(httpHeader.name().toLowerCase(), "content-type") && httpHeader.value().toLowerCase().contains("application/json")){
                return true;
            }
        }
        return false;
    }

    public static JCheckBox getFastjsonMenuWorkBox(){
        return (JCheckBox) Helper.getComponent(FastjsonComponentPanel, "FastjsonMenuWorkBox");
    }

    public static JTable getFastjsonLoggerTable(){
        return (JTable) Helper.getComponent(FastjsonComponentPanel, "FastjsonLoggerTable");
    }

    public static void setFastjsonLoggerTableValueAt(String value,Integer rowIndex, String ColumnName){
//        API.logging().output().println(rowIndex+":"+ColumnMap.get(ColumnName));
        getFastjsonLoggerTable().getModel().setValueAt(value, rowIndex, ColumnMap.get(ColumnName));
    }

    public static String getFastjsonRequestNumber(int row){
        return (String) getFastjsonLoggerTable().getValueAt(row,0);
    }

}
