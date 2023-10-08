package jrcet.frame.Scanner.Overauth;

import burp.MyRegisterHttpHandler;
import burp.api.montoya.http.HttpService;
import burp.api.montoya.http.handler.HttpRequestToBeSent;
import burp.api.montoya.http.handler.HttpResponseReceived;
import burp.api.montoya.http.message.params.HttpParameterType;
import burp.api.montoya.http.message.params.ParsedHttpParameter;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.http.message.responses.HttpResponse;
import jrcet.diycomponents.DiyJList;
import jrcet.diycomponents.DiyJLogTable;
import jrcet.help.Helper;
import jrcet.help.StringUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

import static jrcet.frame.Scanner.Overauth.OverauthComponent.OverauthComponentPanel;

public class Overauth {

    private static final HashMap<String, Integer> ColumnMap = new HashMap<>(){
        {
            put("#", 0);
            put("Tool", 1);
            put("Method", 2);
            put("Host", 3);
            put("Path", 4);
            put("Code", 5);
            put("Length", 6);
            put("requestTime", 7);
            put("responseTime", 8);
            put("OverAuth", 9);
            put("UnAuth", 10);
            put("FlatAuth", 11);
        }
    };

    public static final String AUTH = "AUTH";
    public static String OverauthTableSerialNumber = "0";
    private static final ReentrantLock OverauthTableSerialNumberLock = new ReentrantLock();

    public static ArrayList<String> AuthCheckUrlList = new ArrayList<>();

    public static HashMap<String, OverauthTableEntry> AuthCheckEntryMap = new HashMap<>();

    public static boolean OverauthDebug = true;

    public static void setOverauthLoggerTableValueAt(String value, Integer rowIndex, String columnName){
        getOverauthLoggerTable().getModel().setValueAt(value, rowIndex, ColumnMap.get(columnName));
    }

    public static DiyJLogTable getOverauthLoggerTable(){
        return (DiyJLogTable) Helper.getComponent(OverauthComponentPanel,"OverauthLoggerTable");
    }

    private static JTextField getField(String filedName){
        return (JTextField) Helper.getComponent(OverauthComponentPanel,filedName);
    }

    public static JTextField getOverauthMenuHighauthField(){
        return getField("OverauthMenuHighauthField");
    }

    public static JTextField getOverauthMenuHostField(){
        return getField("OverauthMenuHostField");
    }

    public static JTextField getOverauthMenuLowauthField(){
        return getField("OverauthMenuLowauthField");
    }

    public static String getOverAuthSerialNumber(int row){
        return (String) getOverauthLoggerTable().getValueAt(row,0);
    }

    public static DiyJList getOverauthLoggerList(){
        return (DiyJList) Helper.getComponent(OverauthComponentPanel, "OverauthLoggerList");
    }


    public static String authCheckRequest(HttpRequestToBeSent requestToBeSent){
        //不接受不期望的流量
        //Overauth不期望的域名
        String requestHost = requestToBeSent.httpService().host();
        String overauthTargetHost = getOverauthMenuHostField().getText();
        if(Objects.equals(overauthTargetHost, "") || !requestHost.contains(overauthTargetHost)){
            return "";
        }

        AuthCheckUrlList.add(requestToBeSent.url());

        //设置有水平越权的字段

        ArrayList<ParsedHttpParameter> HorizontalOverAuthParameters = new ArrayList<>();
        List<ParsedHttpParameter> highAuthParameterList = requestToBeSent.parameters();
        for(ParsedHttpParameter highAuthParameter : highAuthParameterList){
            if(highAuthParameter.type()!= HttpParameterType.COOKIE && Helper.isNumeric(highAuthParameter.value()) && !StringUtil.isBlank(highAuthParameter.value())){
                HorizontalOverAuthParameters.add(highAuthParameter);
            }
        }


        // 设置authcheck table
        String requestNumber = getOverauthTableSerialNumber();
        String requestMethod = requestToBeSent.method();
        String requestTool   = requestToBeSent.toolSource().toolType().toolName();
        String requestPath   = requestToBeSent.path();
        String requestTime   = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SS").format(new Date());
        String[] inf         = new String[]{
                requestNumber,
                requestTool,
                requestMethod,
                requestHost,
                requestPath,
                "",
                "",
                requestTime,
                "",
                "",
                ""
        };

        ((DefaultTableModel) getOverauthLoggerTable().getModel()).addRow(inf);

        OverauthTableEntry rowEntry = new OverauthTableEntry(inf);
        rowEntry.setHighAuthRequest(requestToBeSent);
        rowEntry.setRowIndex(getOverauthLoggerTable().getRowByValue(requestNumber));
        rowEntry.setHorizontalOverAuthParameters(HorizontalOverAuthParameters);

        AuthCheckEntryMap.put(requestNumber, rowEntry);

        return AUTH + requestNumber;
    }

    public static void authCheckResponse(HttpResponseReceived responseReceived, String authRequestNumber){


        int responseLen = responseReceived.body().length();
        String responseLength = Integer.toString(responseLen);
        String responseCode   = String.valueOf(responseReceived.statusCode());
        String responseTime   = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SS").format(new Date());

        HttpResponse highAuthResponse = responseLen<5000?responseReceived:responseReceived.withBody(responseReceived.body().subArray(0, 4999));

        setOverauthLoggerTableValueAt(
                responseLength,
                AuthCheckEntryMap.get(authRequestNumber).getRowIndex(),
                "Length"
        );
        setOverauthLoggerTableValueAt(
                responseTime,
                AuthCheckEntryMap.get(authRequestNumber).getRowIndex(),
                "responseTime"
        );

        setOverauthLoggerTableValueAt(
                responseCode,
                AuthCheckEntryMap.get(authRequestNumber).getRowIndex(),
                "Code"
        );

        AuthCheckEntryMap.get(authRequestNumber).setCode(responseCode);
        AuthCheckEntryMap.get(authRequestNumber).setLength(responseLength);
        AuthCheckEntryMap.get(authRequestNumber).setResponseTime(responseTime);
        AuthCheckEntryMap.get(authRequestNumber).setHighAuthResponse(highAuthResponse);


        // 测试越权
        String highAuth = getOverauthMenuHighauthField().getText();
        String lowAuth  = getOverauthMenuLowauthField().getText();

        if(StringUtil.isBlank(highAuth)){
            return ;
        }

        HttpRequest highAuthHttpRequest  = AuthCheckEntryMap.get(authRequestNumber).getHighAuthRequest();
        HttpService httpService          = highAuthHttpRequest.httpService();

        String highAuthHttpReuqestString = highAuthHttpRequest.toString();

        if(!StringUtil.isBlank(lowAuth)){
            HttpRequest lowAuthHttpRequest = HttpRequest.httpRequest(httpService,highAuthHttpReuqestString.replace(highAuth,lowAuth));
            new MyRegisterHttpHandler.checkWorker("lowAuth", authRequestNumber, lowAuthHttpRequest).execute();
        }

        HttpRequest unAuthHttpRequest =  HttpRequest.httpRequest(httpService,highAuthHttpReuqestString.replace(highAuth,""));

        new MyRegisterHttpHandler.checkWorker("unAuth",authRequestNumber,unAuthHttpRequest).execute();

    }

    private static String getOverauthTableSerialNumber() {
        OverauthTableSerialNumberLock.lock();
        OverauthTableSerialNumber = Integer.toString(Integer.parseInt(OverauthTableSerialNumber)+1);
        OverauthTableSerialNumberLock.unlock();

        return OverauthTableSerialNumber;
    }


}
