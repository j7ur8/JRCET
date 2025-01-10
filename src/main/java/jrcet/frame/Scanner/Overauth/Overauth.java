package jrcet.frame.Scanner.Overauth;

import burp.MyRegisterHttpHandler;
import burp.api.montoya.core.ByteArray;
import burp.api.montoya.http.handler.HttpRequestToBeSent;
import burp.api.montoya.http.handler.HttpResponseReceived;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.http.message.responses.HttpResponse;
import diycomponents.DiyJLogTable;
import help.Helper;
import help.StringUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;

import static jrcet.frame.Scanner.Overauth.OverauthComponent.*;

public class Overauth {

    private static final ReentrantLock RequestIdLock = new ReentrantLock();

    private static final ReentrantLock AddLock = new ReentrantLock();

    private static Integer RequestId = 0;

    private static final HashMap<String, Integer> ColumnMap = new HashMap<>(){
        {
            put("#", 0);
            put("Tool", 1);
            put("Method", 2);
            put("Host", 3);
            put("Path", 4);
            put("Code", 5);
            put("Length", 6);
            put("RequestTime", 7);
            put("ResponseTime", 8);
            put("LowAuth", 9);
            put("UnAuth", 10);
            put("FlatAuth", 11);
        }
    };

    public static final String AUTH = "AUTH";
    public static boolean OverauthCheck = false;
    public static HashMap<String, OverauthTableEntry> OverAuthTableEntryMap = new HashMap<>();

    public static HashMap<String, String> OverAuthPathMap = new HashMap<>();

    public static String authCheckRequest(HttpRequestToBeSent requestToBeSent){

        String RequestHost = requestToBeSent.httpService().host();
        String overauthTargetHost = getOverauthMenuHostField().getText();
        if(Objects.equals(overauthTargetHost, "") || !RequestHost.contains(overauthTargetHost)){
            return "";
        }

        String highAuth = getOverauthMenuHighauthField().getText();
        String lowAuth  = getOverauthMenuLowauthField().getText();

        if(StringUtil.isBlank(highAuth)){
            return "";
        }

        String RequestPath   = requestToBeSent.path();
        if(OverAuthTableEntryMap.containsKey(RequestPath)){
            return "";
        }

        String RequestNumber = Integer.toString(getOverauthRequestId());
        String RequestTool            = requestToBeSent.toolSource().toolType().toolName();
        String RequestMethod          = requestToBeSent.method();
        String RequestTime     = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());


        ByteArray highAuthByteArray = requestToBeSent.toByteArray();
        int highAuthLen = ByteArray.byteArray(highAuth).length();
        int highPos = highAuthByteArray.indexOf(highAuth);

        HttpRequest LowAuthRequest = HttpRequest.httpRequest(highAuthByteArray.subArray(0,highPos).withAppended(lowAuth).withAppended(highAuthByteArray.subArray(highPos+highAuthLen,highAuthByteArray.length()))).withService(requestToBeSent.httpService());
        HttpRequest UnAuthRequest = HttpRequest.httpRequest(highAuthByteArray.subArray(0,highPos).withAppended(highAuthByteArray.subArray(highPos+highAuthLen,highAuthByteArray.length()))).withService(requestToBeSent.httpService());

        OverauthTableEntry overauthTableEntry = new OverauthTableEntry();
        overauthTableEntry.setRequestNumber(RequestNumber);
        overauthTableEntry.setRequestTool(RequestTool);
        overauthTableEntry.setRequestMethod(RequestMethod);
        overauthTableEntry.setRequestHost(RequestHost);
        overauthTableEntry.setRequestPath(RequestPath);
        overauthTableEntry.setRequestTime(RequestTime);
        overauthTableEntry.setHighAuthRequest(requestToBeSent);
        overauthTableEntry.setLowAuthRequest(LowAuthRequest);
        overauthTableEntry.setUnAuthRequest(UnAuthRequest);

        String[] inf = new String[]{
                RequestNumber,
                RequestTool,
                RequestMethod,
                RequestHost,
                RequestPath,
                "",
                "",
                RequestTime,
                "",
                "",
                ""
        };


        AddLock.lock();
        ((DefaultTableModel) getOverauthLoggerTable().getModel()).addRow(inf);
        OverAuthTableEntryMap.put(RequestPath, overauthTableEntry);
        OverAuthPathMap.put(RequestNumber, RequestPath);
        AddLock.unlock();


        //        //设置有水平越权的字段
//        ArrayList<ParsedHttpParameter> HorizontalOverAuthParameters = new ArrayList<>();
//        List<ParsedHttpParameter> highAuthParameterList = requestToBeSent.parameters();
//        for(ParsedHttpParameter highAuthParameter : highAuthParameterList){
//            if(highAuthParameter.type()!= HttpParameterType.COOKIE && Helper.isNumeric(highAuthParameter.value()) && !StringUtil.isBlank(highAuthParameter.value())){
//                HorizontalOverAuthParameters.add(highAuthParameter);
//            }
//        }

        return AUTH+RequestNumber;
    }

    public static void authCheckResponse(HttpResponseReceived responseReceived, String RequestNumber){

        String ResponseLength = Integer.toString(responseReceived.body().length());
        String ResponseCode   = String.valueOf(responseReceived.statusCode());
        String ResponseTime   = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());

        int rowIndex = getRowByRequestNumber(RequestNumber);

        setTableValueAt(
                ResponseLength,
                rowIndex,
                "Length"
        );
        setTableValueAt(
                ResponseTime,
                rowIndex,
                "ResponseTime"
        );

        setTableValueAt(
                ResponseCode,
                rowIndex,
                "Code"
        );
        String RequestPath = OverAuthPathMap.get(RequestNumber);
        OverauthTableEntry overAuthTableEntryMap = OverAuthTableEntryMap.get(RequestPath);
        overAuthTableEntryMap.setHighAuthResponse(responseReceived);
        overAuthTableEntryMap.setLength(ResponseLength);
        overAuthTableEntryMap.setResponseTime(ResponseTime);

        new MyRegisterHttpHandler.checkWorker("UnAuth",RequestNumber, overAuthTableEntryMap.getUnAuthRequest()).execute();

        new MyRegisterHttpHandler.checkWorker("LowAuth",RequestNumber, overAuthTableEntryMap.getLowAuthRequest()).execute();

    }

    public void setRequestResponse(String requestNumber) {
        String RequestPath = OverAuthPathMap.get(requestNumber);
        OverauthTableEntry overAuthTableEntry = OverAuthTableEntryMap.get(RequestPath);

        HttpRequest HighAuthRequest = overAuthTableEntry.getHighAuthRequest();
        HttpRequest LowAuthRequest = overAuthTableEntry.getLowAuthRequest();
        HttpRequest UnAuthRequest = overAuthTableEntry.getUnAuthRequest();

        HttpResponse HighAuthResponse = overAuthTableEntry.getHighAuthResponse();
        HttpResponse LowAuthResponse = overAuthTableEntry.getLowAuthResponse();
        HttpResponse UnAuthResponse = overAuthTableEntry.getUnAuthResponse();

        OverauthAuthHighauthRequestEditor.setRequest(HighAuthRequest);
        OverauthAuthLowauthRequestEditor.setRequest(LowAuthRequest);
        OverauthAuthUnauthRequestEditor.setRequest(UnAuthRequest);

        OverauthAuthHighauthResponseEditor.setResponse(HighAuthResponse);
        OverauthAuthLowauthResponseEditor.setResponse(LowAuthResponse);
        OverauthAuthUnauthResponseEditor.setResponse(UnAuthResponse);

    }


    private static Integer getOverauthRequestId() {
        RequestIdLock.lock();
        RequestId = RequestId+1;
        RequestIdLock.unlock();
        return RequestId;
    }

    public static void clearOverauthTable(){


        OverAuthTableEntryMap = new HashMap<>();
        OverAuthPathMap = new HashMap<>();
        ((DefaultTableModel)getOverauthLoggerTable().getModel()).setRowCount(0);
        RequestId = 0;
    }

    public static void setTableValueAt(String value, Integer rowIndex, String columnName){
        getOverauthLoggerTable().getModel().setValueAt(value, rowIndex, ColumnMap.get(columnName));
    }

    public static String getTableValueAt(int row, int column){
        return (String)getOverauthLoggerTable().getModel().getValueAt(row,column);
    }

    public static int getRowByRequestNumber(String requestNumber){
        return getOverauthLoggerTable().getRowByTopColum(requestNumber);
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




}
