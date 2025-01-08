package jrcet.frame.Scanner.Springboot;


import burp.api.montoya.http.handler.HttpRequestToBeSent;
import burp.api.montoya.http.handler.HttpResponseReceived;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

import burp.api.montoya.http.message.MimeType;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.http.message.responses.HttpResponse;
import diycomponents.DiyJComboBox;
import diycomponents.DiyJLogTable;
import help.Helper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import static burp.MyExtender.BurpAPI;
import static jrcet.frame.Scanner.Springboot.SpringbootComponent.SpringbootComponentPanel;

public class Springboot {

    private static String SpringbootLoggerTableSerialNumber = "0";
    private static final ReentrantLock SpringbootTableSerialNumberLock = new ReentrantLock();
    private static final HashMap<String, Integer> SpringbootColumnMap = new HashMap<>(){
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
            put("Type", 9);
            put("Vul", 10);
        }
    };
    public static final String SPRINGBOOT = "SPRINGBOOT";

    public static ArrayList<String> SpringbootCheckedUrlList = new ArrayList<>();
    public static ArrayList<String> SpringbootCheckPathList = new ArrayList<>();
    public static HashMap<String, SpringbootTableEntry> SpringbootLoggerTableEntryMap = new HashMap<>();
    public static boolean SpringbootCheck = false;
    public static String springbootCheckRequest(HttpRequestToBeSent requestToBeSent){

        String serialNumber = getSpringbootLoggerTableSerialNumber();
        String[] checkPath = new String[]{
                "/swagger-resources",
                "/actuator/health",
                "/druid/index.html",
                "/doc.html",
                "/monitor"
        };
        String[] rootPath = Helper.get2DeepUrls(requestToBeSent.path());
        for(String p:rootPath){
            for(String c: checkPath){
                SpringbootCheckPathList.add(p+c);
            }
        }

        SpringbootTableEntry rawEntry = new SpringbootTableEntry();
        rawEntry.setRawRequest(requestToBeSent);
        SpringbootLoggerTableEntryMap.put(serialNumber,rawEntry);

        return SPRINGBOOT+serialNumber;
    }

    public static void springbootCheckResponse(HttpResponseReceived responseReceived, String springbootSerialNumber){
        SpringbootLoggerTableEntryMap.get(springbootSerialNumber).setRawResponse(responseReceived);

        new SpringbootCheckWorker(springbootSerialNumber).execute();
    }

    public static class SpringbootCheckWorker extends SwingWorker<Void, Void>  {

        public HttpRequest httpRequest;

        public SpringbootCheckWorker(String serialNumber){
            httpRequest = SpringbootLoggerTableEntryMap.get(serialNumber).getRawRequest();

        }
        @Override
        protected Void doInBackground() throws InterruptedException {
            for(String path: SpringbootCheckPathList){

                HttpRequest newHttpRequest = httpRequest.withPath(path).withMethod("GET").withBody("");
                String serialNumber  = getSpringbootLoggerTableSerialNumber();

                String requestUrl = newHttpRequest.url();
                if(SpringbootCheckedUrlList.contains(requestUrl)) continue;

                SpringbootCheckedUrlList.add(requestUrl);

                String requestTool   = "Extensions";
                String requestMethod = newHttpRequest.method();
                String requestHost   = newHttpRequest.httpService().host();
                String requestPath   = newHttpRequest.path();
                String requestTime   = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());

                HttpResponse httpResponse = BurpAPI.http().sendRequest(httpRequest).response();
                String responseCode = Integer.toString(httpResponse.statusCode());
                int responseLength = httpResponse.body().length();
                HttpResponse ihttpResponse = responseLength<5000?httpResponse:httpResponse.withBody(httpResponse.body().subArray(0, 4999));

                String responseTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
                String[] inf = new String[]{
                        serialNumber,
                        requestTool,
                        requestMethod,
                        requestHost,
                        requestPath,
                        responseCode,
                        String.valueOf(responseLength),
                        requestTime,
                        responseTime,
                        "",
                        "",
                };

                if(path.contains("doc.html")) {
                    inf[9]="Doc";
                } else if(path.contains("swagger")) {
                    inf[9]="Swagger";
                } else if (path.contains("druid")) {
                    inf[9]="Druid";
                } else {
                    inf[9]="Actuator";
                }

                SpringbootLoggerTableEntryMap.put(serialNumber, new SpringbootTableEntry(inf));
                SpringbootLoggerTableEntryMap.get(serialNumber).setRawRequest(newHttpRequest);
                SpringbootLoggerTableEntryMap.get(serialNumber).setRawResponse(ihttpResponse);

                if(
                        !responseCode.startsWith("4") && !responseCode.startsWith("5") &&
                        (
                                (httpResponse.statedMimeType()== MimeType.JSON && httpResponse.bodyToString().toLowerCase().contains("status")) ||
                                (httpResponse.statedMimeType()== MimeType.JSON && httpResponse.bodyToString().toLowerCase().contains("swagger")) ||
                                (httpResponse.statedMimeType()== MimeType.HTML && httpResponse.bodyToString().toLowerCase().contains("webjars")) ||
                                (httpResponse.bodyToString().toLowerCase().contains("druid") || httpResponse.toString().contains("login.html"))
                        )
                ){
                    SpringbootLoggerTableEntryMap.get(serialNumber).setVul("True");
                }

                if(Objects.equals(SpringbootLoggerTableEntryMap.get(serialNumber).getVul(), "True")){
                    ((DefaultTableModel)getSpringbootLoggerTable().getModel()).addRow(SpringbootLoggerTableEntryMap.get(serialNumber).toTableRow());
                }

                Thread.sleep(1000);
            }
            return null;
        }
    }

    public static void clearSpringbootTable(){
        ((DefaultTableModel)getSpringbootLoggerTable().getModel()).setRowCount(0);
        SpringbootLoggerTableSerialNumber = "0";
        SpringbootCheckedUrlList.clear();
        SpringbootLoggerTableEntryMap.clear();
    }

    private static String getSpringbootLoggerTableSerialNumber() {
        SpringbootTableSerialNumberLock.lock();
        SpringbootLoggerTableSerialNumber = Integer.toString(Integer.parseInt(SpringbootLoggerTableSerialNumber)+1);
        SpringbootTableSerialNumberLock.unlock();
        return SpringbootLoggerTableSerialNumber;
    }

    public static DiyJLogTable getSpringbootLoggerTable(){
        return (DiyJLogTable) Helper.getComponent(SpringbootComponentPanel, "SpringbootLoggerTable");
    }

    public static String getSpringbootSerialNumber(int row){
        return (String) getSpringbootLoggerTable().getValueAt(row,0);
    }

    public static void setSpringbootLoggerTableValueAt(String value, Integer rowIndex, String columnName){
        getSpringbootLoggerTable().getModel().setValueAt(value, rowIndex, SpringbootColumnMap.get(columnName));
    }

}
