package jrcet.frame.Scanner.Overauth;

import burp.MyRegisterHttpHandler;
import burp.api.montoya.core.ByteArray;
import burp.api.montoya.http.HttpService;
import burp.api.montoya.http.handler.HttpRequestToBeSent;
import burp.api.montoya.http.handler.HttpResponseReceived;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.http.message.responses.HttpResponse;
import diycomponents.DiyJLogTable;
import help.Helper;
import help.StringUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;

import static burp.MyExtender.BurpAPI;
import static jrcet.frame.Scanner.Overauth.OverauthComponent.*;

public class Overauth {

    private static final ReentrantLock RequestIdLock = new ReentrantLock();

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
            put("requestTime", 7);
            put("responseTime", 8);
            put("LowAuth", 9);
            put("UnAuth", 10);
            put("FlatAuth", 11);
        }
    };

    public static final String AUTH = "AUTH";
    public static boolean OverauthCheck = false;



    public static void delDb(){
        PreparedStatement pstmt = null;
        try{

             pstmt = OverauthSqlite.getPstmt("DELETE FROM sqlite_sequence WHERE name ='overauth'");
            OverauthSqlite.uniqUpdate(pstmt);
        }catch (SQLException e){
            BurpAPI.logging().error().println(e);
        }finally {
            OverauthSqlite.close(pstmt, null);
        }
    }
    public static void initDb(){

        PreparedStatement pstmt = null;
        try{
            pstmt = OverauthSqlite.getPstmt("CREATE TABLE IF NOT EXISTS overauth (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "Tool TEXT NOT NULL," +
                    "Method TEXT NOT NULL," +
                    "Host TEXT NOT NULL," +
                    "Path TEXT UNIQUE NOT NULL," +
                    "Code TEXT," +
                    "Length TEXT," +
                    "RequestTime TEXT NOT NULL," +
                    "ResponseTime TEXT," +
                    "LowAuth TEXT," +
                    "UnAuth TEXT," +
                    "FlatAuth TEXT," +
                    "RequestService Text," +
                    "HighAuthRequest Text DEFAULT ''," +
                    "HighAuthResponse Text DEFAULT ''," +
                    "LowAuthRequest Text DEFAULT ''," +
                    "LowAuthResponse Text DEFAULT ''," +
                    "UnAuthRequest Text DEFAULT ''," +
                    "UnAuthResponse Text DEFAULT ''" +
                    ")"
            );
            OverauthSqlite.uniqUpdate(pstmt);
        }catch (SQLException e){
            BurpAPI.logging().error().println(e);
        }finally {
            OverauthSqlite.close(pstmt, null);
        }

    }

    public static boolean ifPathInDb(String path){

        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try{

            pstmt = OverauthSqlite.getPstmt("select id from overauth where path=?");
            pstmt.setString(1, path);
            rs = OverauthSqlite.uniqQuery(pstmt);
            return rs.next();

        }catch (SQLException e){
            BurpAPI.logging().error().println("select id from overauth where path=?");
            BurpAPI.logging().error().println(e);
        }finally {
            OverauthSqlite.close(pstmt, rs);
        }
        return false;
    }
    public static String authCheckRequest(HttpRequestToBeSent requestToBeSent){

        String Host = requestToBeSent.httpService().host();
        String overauthTargetHost = getOverauthMenuHostField().getText();
        if(Objects.equals(overauthTargetHost, "") || !Host.contains(overauthTargetHost)){
            return "";
        }

        String highAuth = getOverauthMenuHighauthField().getText();
        String lowAuth  = getOverauthMenuLowauthField().getText();

        if(StringUtil.isBlank(highAuth)){
            return "";
        }

        String Path   = requestToBeSent.path();
        if(ifPathInDb(Path)){
            return "";
        }

        Integer id             = getOverauthRequestId();
        String Tool            = requestToBeSent.toolSource().toolType().toolName();
        String Method          = requestToBeSent.method();
        String RequestTime     = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
        String RequestService  = requestToBeSent.httpService().toString();
        String HighAuthRequest = Helper.base64Encode2String(requestToBeSent.toByteArray().getBytes());

        ByteArray highAuthByteArray = requestToBeSent.toByteArray();
        int highAuthLen = ByteArray.byteArray(highAuth).length();
        int highPos = highAuthByteArray.indexOf(highAuth);
        String LowAuthRequest = Helper.base64Encode2String( (highAuthByteArray.subArray(0,highPos).withAppended(lowAuth).withAppended(highAuthByteArray.subArray(highPos+highAuthLen,highAuthByteArray.length()))).getBytes() );
        String UnAuthRequest = Helper.base64Encode2String( (highAuthByteArray.subArray(0,highPos).withAppended(highAuthByteArray.subArray(highPos+highAuthLen,highAuthByteArray.length()))).getBytes() );

//        String LowAuthRequest  = StringUtil.isBlank(lowAuth)?"":HighAuthRequest.replace(highAuth,lowAuth);
//        String UnAuthRequest   = HighAuthRequest.replace(highAuth,"");

        String requestNumber = Integer.toString(id);
        String[] inf = new String[]{
                requestNumber,
                Tool,
                Method,
                Host,
                Path,
                "",
                "",
                RequestTime,
                "",
                "",
                ""
        };
        ((DefaultTableModel) getOverauthLoggerTable().getModel()).addRow(inf);

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = OverauthSqlite.getPstmt("INSERT INTO overauth (id,Tool,Method,Host,Path,RequestTime,RequestService, HighAuthRequest,LowAuthRequest, UnAuthRequest) VALUES (?,?,?,?,?,?,?,?,?,?)");
            pstmt.setInt(1, id);
            pstmt.setString(2, Tool);
            pstmt.setString(3, Method);
            pstmt.setString(4, Host);
            pstmt.setString(5, Path);
            pstmt.setString(6, RequestTime);
            pstmt.setString(7, RequestService);
            pstmt.setString(8, HighAuthRequest);
            pstmt.setString(9, LowAuthRequest);
            pstmt.setString(10, UnAuthRequest);
            OverauthSqlite.uniqUpdate(pstmt);

        } catch (SQLException e) {
            BurpAPI.logging().error().println("INSERT INTO overauth (id,Tool,Method,Host,Path,RequestTime,RequestService, HighAuthRequest,LowAuthRequest, UnAuthRequest) VALUES (?,?,?,?,?,?,?,?,?,?)");
            BurpAPI.logging().error().println(Path);
            BurpAPI.logging().error().println(e);
        }finally {
            OverauthSqlite.close(pstmt, rs);
        }

        //        //设置有水平越权的字段
//        ArrayList<ParsedHttpParameter> HorizontalOverAuthParameters = new ArrayList<>();
//        List<ParsedHttpParameter> highAuthParameterList = requestToBeSent.parameters();
//        for(ParsedHttpParameter highAuthParameter : highAuthParameterList){
//            if(highAuthParameter.type()!= HttpParameterType.COOKIE && Helper.isNumeric(highAuthParameter.value()) && !StringUtil.isBlank(highAuthParameter.value())){
//                HorizontalOverAuthParameters.add(highAuthParameter);
//            }
//        }

        return AUTH+requestNumber;
    }

    public static void authCheckResponse(HttpResponseReceived responseReceived, String authRequestNumber){

        String responseLength = Integer.toString(responseReceived.body().length());
        String responseCode   = String.valueOf(responseReceived.statusCode());
        String responseTime   = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
        int id             = Integer.parseInt(authRequestNumber);

        setOverauthLoggerTableValueAt(
                responseLength,
                Integer.parseInt(authRequestNumber)-1,
                "Length"
        );
        setOverauthLoggerTableValueAt(
                responseTime,
                Integer.parseInt(authRequestNumber)-1,
                "responseTime"
        );

        setOverauthLoggerTableValueAt(
                responseCode,
                Integer.parseInt(authRequestNumber)-1,
                "Code"
        );

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = OverauthSqlite.getPstmt("update overauth set Code = ?, Length = ?, ResponseTime = ?, HighAuthResponse=? where id = ?");
            pstmt.setString(1,responseCode);
            pstmt.setString(2,responseLength);
            pstmt.setString(3,responseTime);
            pstmt.setString(4,Helper.base64Encode2String(responseReceived.toByteArray().getBytes()));
            pstmt.setInt(5, id);
            OverauthSqlite.uniqUpdate(pstmt);

        } catch (SQLException e) {
            BurpAPI.logging().error().println("update overauth set Code = ?, Length = ?, ResponseTime = ?, HighAuthResponse=? where id=?");
            BurpAPI.logging().error().println(e);
        }finally {
            OverauthSqlite.close(pstmt, rs);
        }


        try{
            pstmt = OverauthSqlite.getPstmt("select RequestService, LowAuthRequest, UnAuthRequest from overauth where id=?");
            pstmt.setInt(1, id);
            rs = OverauthSqlite.uniqQuery(pstmt);
            String RequestService  = rs.getString(1);
            byte[] LowAuthRequest = Helper.base64Decode2Byte(rs.getString(2));
            byte[] UnAuthRequest   = Helper.base64Decode2Byte(rs.getString(3));
            new MyRegisterHttpHandler.checkWorker(
                    "UnAuth", authRequestNumber, HttpRequest.httpRequest(ByteArray.byteArray(UnAuthRequest)).withService(HttpService.httpService(RequestService) )
            ).execute();

            new MyRegisterHttpHandler.checkWorker(
                    "LowAuth", authRequestNumber, HttpRequest.httpRequest(ByteArray.byteArray(LowAuthRequest)).withService(HttpService.httpService(RequestService))
            ).execute();
        }catch (SQLException e){
            BurpAPI.logging().error().println("select RequestService, HighAuthRequest, UnAuthRequest from overauth where id=?");
            BurpAPI.logging().error().println(e);
        }finally {
            OverauthSqlite.close(pstmt, rs);
        }

    }

    public void setRequestResponse(int id) {
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try{

            pstmt = OverauthSqlite.getPstmt("select HighAuthRequest, HighAuthResponse, LowAuthRequest, LowAuthResponse, UnAuthRequest, UnAuthResponse from overauth where id = ?");
            pstmt.setInt(1, id);

            rs = OverauthSqlite.uniqQuery(pstmt);
            if(rs.next()){
                byte[] HighAuthRequest = Helper.base64Decode2Byte(rs.getString(1));
                byte[] HighAuthResponse = Helper.base64Decode2Byte(rs.getString(2));
                byte[] LowAuthRequest = Helper.base64Decode2Byte(rs.getString(3));
                byte[] LowAuthResponse = Helper.base64Decode2Byte(rs.getString(4));
                byte[] UnAuthRequest = Helper.base64Decode2Byte(rs.getString(5));
                byte[] UnAuthResponse = Helper.base64Decode2Byte(rs.getString(6));

                OverauthAuthHighauthRequestEditor.setRequest(HttpRequest.httpRequest(ByteArray.byteArray(HighAuthRequest)));
                OverauthAuthHighauthResponseEditor.setResponse(HttpResponse.httpResponse(ByteArray.byteArray(HighAuthResponse)));
                OverauthAuthLowauthRequestEditor.setRequest(HttpRequest.httpRequest(ByteArray.byteArray(LowAuthRequest)));
                OverauthAuthLowauthResponseEditor.setResponse(HttpResponse.httpResponse(ByteArray.byteArray(LowAuthResponse)));
                OverauthAuthUnauthRequestEditor.setRequest(HttpRequest.httpRequest(ByteArray.byteArray(UnAuthRequest)));
                OverauthAuthUnauthResponseEditor.setResponse(HttpResponse.httpResponse(ByteArray.byteArray(UnAuthResponse)));
            }else{
                OverauthAuthHighauthRequestEditor.setRequest(null);
                OverauthAuthHighauthResponseEditor.setResponse(null);
                OverauthAuthLowauthRequestEditor.setRequest(null);
                OverauthAuthLowauthResponseEditor.setResponse(null);
                OverauthAuthUnauthRequestEditor.setRequest(null);
                OverauthAuthUnauthResponseEditor.setResponse(null);
            }

        }catch (SQLException e){
            BurpAPI.logging().error().println("select HighAuthRequest, HighAuthResponse, LowAuthRequest, LowAuthResponse, UnAuthRequest, UnAuthResponse from overauth where id = ?");
            BurpAPI.logging().error().println(e);
        }finally {
            OverauthSqlite.close(pstmt,rs);
        }

    }


    private static Integer getOverauthRequestId() {
        RequestIdLock.lock();
        RequestId = RequestId+1;
        RequestIdLock.unlock();

        return RequestId;
    }

    public static void clearOverauthTable(){
        ((DefaultTableModel)getOverauthLoggerTable().getModel()).setRowCount(0);
    }

    public static void setOverauthLoggerTableValueAt(String value, Integer rowIndex, String columnName){
        getOverauthLoggerTable().getModel().setValueAt(value, rowIndex, ColumnMap.get(columnName));
    }

    public static int getRowOfOverauthLoggerTableByValue(String id){
        return getOverauthLoggerTable().getRowByTopColum(id);
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
