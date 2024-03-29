package jrcet.frame.Scanner.Overauth;

import burp.MyRegisterHttpHandler;
import burp.api.montoya.http.HttpService;
import burp.api.montoya.http.handler.HttpRequestToBeSent;
import burp.api.montoya.http.handler.HttpResponseReceived;
import burp.api.montoya.http.message.params.HttpParameterType;
import burp.api.montoya.http.message.params.ParsedHttpParameter;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.http.message.responses.HttpResponse;
import diycomponents.DiyJList;
import diycomponents.DiyJLogTable;
import help.Helper;
import help.StringUtil;

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
    public static String OverauthLoggerTableSerialNumber = "0";
    private static final ReentrantLock OverauthLoggerTableSerialNumberLock = new ReentrantLock();

    public static ArrayList<String> OverauthCheckUrlList = new ArrayList<>();

    public static HashMap<String, OverauthTableEntry> OverauthLoggerTableEntryMap = new HashMap<>();

    public static boolean OverauthCheck = false;

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

        OverauthCheckUrlList.add(requestToBeSent.url());

        //设置有水平越权的字段

        ArrayList<ParsedHttpParameter> HorizontalOverAuthParameters = new ArrayList<>();
        List<ParsedHttpParameter> highAuthParameterList = requestToBeSent.parameters();
        for(ParsedHttpParameter highAuthParameter : highAuthParameterList){
            if(highAuthParameter.type()!= HttpParameterType.COOKIE && Helper.isNumeric(highAuthParameter.value()) && !StringUtil.isBlank(highAuthParameter.value())){
                HorizontalOverAuthParameters.add(highAuthParameter);
            }
        }


        // 设置authcheck table
        String requestNumber = getOverauthLoggerTableSerialNumber();
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

        OverauthLoggerTableEntryMap.put(requestNumber, rowEntry);

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
                OverauthLoggerTableEntryMap.get(authRequestNumber).getRowIndex(),
                "Length"
        );
        setOverauthLoggerTableValueAt(
                responseTime,
                OverauthLoggerTableEntryMap.get(authRequestNumber).getRowIndex(),
                "responseTime"
        );

        setOverauthLoggerTableValueAt(
                responseCode,
                OverauthLoggerTableEntryMap.get(authRequestNumber).getRowIndex(),
                "Code"
        );

        OverauthLoggerTableEntryMap.get(authRequestNumber).setCode(responseCode);
        OverauthLoggerTableEntryMap.get(authRequestNumber).setLength(responseLength);
        OverauthLoggerTableEntryMap.get(authRequestNumber).setResponseTime(responseTime);
        OverauthLoggerTableEntryMap.get(authRequestNumber).setHighAuthResponse(highAuthResponse);


        // 测试越权
        String highAuth = getOverauthMenuHighauthField().getText();
        String lowAuth  = getOverauthMenuLowauthField().getText();

        if(StringUtil.isBlank(highAuth)){
            return ;
        }

        HttpRequest highAuthHttpRequest  = OverauthLoggerTableEntryMap.get(authRequestNumber).getHighAuthRequest();
        HttpService httpService          = highAuthHttpRequest.httpService();

        String highAuthHttpReuqestString = highAuthHttpRequest.toString();

        if(!StringUtil.isBlank(lowAuth)){
            HttpRequest lowAuthHttpRequest = HttpRequest.httpRequest(httpService,highAuthHttpReuqestString.replace(highAuth,lowAuth));
            new MyRegisterHttpHandler.checkWorker("lowAuth", authRequestNumber, lowAuthHttpRequest).execute();
        }

        HttpRequest unAuthHttpRequest =  HttpRequest.httpRequest(httpService,highAuthHttpReuqestString.replace(highAuth,""));

        new MyRegisterHttpHandler.checkWorker("unAuth",authRequestNumber,unAuthHttpRequest).execute();

    }

    private static String getOverauthLoggerTableSerialNumber() {
        OverauthLoggerTableSerialNumberLock.lock();
        OverauthLoggerTableSerialNumber = Integer.toString(Integer.parseInt(OverauthLoggerTableSerialNumber)+1);
        OverauthLoggerTableSerialNumberLock.unlock();

        return OverauthLoggerTableSerialNumber;
    }

    public static void clearOverauthTable(){
        ((DefaultTableModel)getOverauthLoggerTable().getModel()).setRowCount(0);
        OverauthLoggerTableSerialNumber = "0";
        OverauthLoggerTableEntryMap.clear();
        OverauthCheckUrlList.clear();
    }
}
