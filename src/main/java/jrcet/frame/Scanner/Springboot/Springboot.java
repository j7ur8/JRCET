package jrcet.frame.Scanner.Springboot;


import burp.MyRegisterHttpHandler;
import burp.api.montoya.http.handler.HttpRequestToBeSent;
import burp.api.montoya.http.handler.HttpResponseReceived;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.http.message.responses.HttpResponse;
import diycomponents.DiyJLogTable;
import help.Helper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import static jrcet.frame.Scanner.Springboot.SpringbootComponent.SpringbootComponentPanel;

public class Springboot {

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
    public static String SpringbootLoggerTableSerialNumber = "0";
    private static final ReentrantLock SpringbootTableSerialNumberLock = new ReentrantLock();
    public static ArrayList<String> SpringbootCheckedUrlList = new ArrayList<>();
    public static HashMap<String, SpringbootTableEntry> SpringbootLoggerTableEntryMap = new HashMap<>();

    public static String[] SpringbootCheckPaths;

    public static boolean SpringbootCheck = false;


    public static String springbootCheckRequest(HttpRequestToBeSent requestToBeSent){

        String serialNumber = getSpringbootLoggerTableSerialNumber();
        String requestPath  = requestToBeSent.path().split("\\?")[0];
        String RootUrl = requestToBeSent.url();

        if(Objects.equals(requestPath, "/")){
            SpringbootCheckPaths = new String[]{
                    "/swagger-resources",
                    "/actuator/health",
                    "/druid/index.html",
                    "/doc.html",
                    "/monitor"
            };
        }else{
            String requestRootPath = requestPath.split("/")[1];

            RootUrl = requestToBeSent.withPath("/"+requestRootPath).url();
            if(SpringbootCheckedUrlList.contains(RootUrl)){
                return "";
            }
            SpringbootCheckPaths = new String[]{
                    "/swagger-resources",
                    "/actuator/health",
                    "/druid/index.html",
                    "/doc.html",
                    "/monitor",
                    String.format("/%s/swagger-resources",requestRootPath),
                    String.format("/%s/actuator/health",requestRootPath),
                    String.format("/%s/druid/index.html",requestRootPath),
                    String.format("/%s/doc.html",requestRootPath),
                    String.format("/%s/monitor",requestRootPath)
            };
        }

        String requestTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
        String[] inf       = new String[]{
                serialNumber,
                requestToBeSent.toolSource().toolType().toolName(),
                requestToBeSent.method(),
                requestToBeSent.httpService().host(),
                requestPath,
                "",
                "",
                requestTime,
                "",
                "",
                "",
        };

        String type="Raw";
        inf[9] = type;

        ((DefaultTableModel)getSpringbootLoggerTable().getModel()).addRow(inf);

        if(!SpringbootLoggerTableEntryMap.containsKey(serialNumber)){
            SpringbootTableEntry rowEntry = new SpringbootTableEntry(inf);
            rowEntry.setRawRequest(requestToBeSent);
            rowEntry.setRowIndex(getSpringbootLoggerTable().getRowByValue(serialNumber));
            rowEntry.setRootUrl(RootUrl);
            SpringbootLoggerTableEntryMap.put(serialNumber, rowEntry);
        }

        return  SPRINGBOOT+serialNumber;
    }

    public static void springbootCheckResponse(HttpResponseReceived responseReceived, String springbootSerialNumber){

        int    responseLen    = responseReceived.body().length();
        String responseCode   = Integer.toString(responseReceived.statusCode());
        String responseLength = Integer.toString(responseLen);
        String responseTime   = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());

        HttpResponse simplifyRawhttpResponse = responseLen<5000?responseReceived:responseReceived.withBody(responseReceived.body().subArray(0, 5000));

        setSpringbootLoggerTableValueAt(
                responseLength,
                SpringbootLoggerTableEntryMap.get(springbootSerialNumber).getRowIndex(),
                "Length"
        );
        setSpringbootLoggerTableValueAt(
                responseTime,
                SpringbootLoggerTableEntryMap.get(springbootSerialNumber).getRowIndex(),
                "responseTime"
        );
        setSpringbootLoggerTableValueAt(
                responseCode,
                SpringbootLoggerTableEntryMap.get(springbootSerialNumber).getRowIndex(),
                "Code"
        );

        SpringbootLoggerTableEntryMap.get(springbootSerialNumber).setCode(responseCode);
        SpringbootLoggerTableEntryMap.get(springbootSerialNumber).setLength(responseLength);
        SpringbootLoggerTableEntryMap.get(springbootSerialNumber).setResponseTime(responseTime);
        SpringbootLoggerTableEntryMap.get(springbootSerialNumber).setRawResponse(simplifyRawhttpResponse);
        SpringbootCheckedUrlList.add(SpringbootLoggerTableEntryMap.get(springbootSerialNumber).getRawRequest().url());
        if(!SpringbootCheckedUrlList.contains(SpringbootLoggerTableEntryMap.get(springbootSerialNumber).getRootUrl())){
            SpringbootCheckedUrlList.add(SpringbootLoggerTableEntryMap.get(springbootSerialNumber).getRootUrl());
        }

        new springCheckWorker(springbootSerialNumber).execute();
    }


    public static class springCheckWorker extends SwingWorker<Void, Void> {

        public String SerialNumber;
        public springCheckWorker(String serialNumber){
            this.SerialNumber  = serialNumber;
        }

        @Override
        protected Void doInBackground() throws Exception {
            for(String path: SpringbootCheckPaths){

                HttpRequest httpRequest = SpringbootLoggerTableEntryMap.get(SerialNumber).getRawRequest().withPath(path).withMethod("GET").withBody("");

                String requestUrl = httpRequest.url();
                if(SpringbootCheckedUrlList.contains(requestUrl)) continue;

                SpringbootCheckedUrlList.add(requestUrl);

                String serialNumber  = getSpringbootLoggerTableSerialNumber();
                String requestMethod = httpRequest.method();
                String requestHost   = httpRequest.httpService().host();
                String requestTool   = "Extensions";
                String requestPath   = httpRequest.path();
                String requestTime   = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
                String[] inf = new String[]{
                        serialNumber,
                        requestTool,
                        requestMethod,
                        requestHost,
                        requestPath,
                        "",
                        "",
                        requestTime,
                        "",
                        "",
                        "",
                };

                if(path.contains("doc.html")){
                    inf[9]="Doc";
                }else if(path.contains("swagger-resources")){
                    inf[9]="Swagger";
                } else if (path.contains("druid/index.html")) {
                    inf[9]="Druid";
                }else{
                    inf[9]="Actuator";
                }

                ((DefaultTableModel)getSpringbootLoggerTable().getModel()).addRow(inf);

                SpringbootTableEntry springbootTableEntry = new SpringbootTableEntry(inf);
                springbootTableEntry.setRowIndex(getSpringbootLoggerTable().getRowByValue(serialNumber));

                SpringbootLoggerTableEntryMap.put(serialNumber, springbootTableEntry);

                new MyRegisterHttpHandler.checkWorker("springboot", serialNumber, httpRequest).execute();

                Thread.sleep(1000);
            }

            return null;
        }
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

    public static void clearSpringbootTable(){
        ((DefaultTableModel)getSpringbootLoggerTable().getModel()).setRowCount(0);
        SpringbootLoggerTableSerialNumber = "0";
//        SpringbootCheckedUrlList.clear();
        SpringbootLoggerTableEntryMap.clear();
    }

}
