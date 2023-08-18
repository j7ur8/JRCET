package jrcet.frame.Scanner.Springboot;


import burp.MyRegisterHttpHandler;
import burp.api.montoya.http.handler.HttpRequestToBeSent;
import burp.api.montoya.http.handler.HttpResponseReceived;

import java.text.SimpleDateFormat;
import java.util.*;

import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.http.message.responses.HttpResponse;
import jrcet.help.Helper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import static burp.MyExtender.API;
import static jrcet.frame.Scanner.Springboot.SpringbootComponent.SpringbootComponentPanel;

public class Springboot {

    private static final HashMap<String, Integer> SpringbootColumnMap = new HashMap<>(){
        {
            put("#", 0);
            put("Tool", 1);
            put("Method", 2);
            put("Host", 3);
            put("Path", 4);
            put("Length", 5);
            put("requestTime", 6);
            put("responseTime", 7);
            put("Type", 8);
            put("Vul", 9);
        }
    };
    public static final String SPRINGBOOT = "SPRINGBOOT";
    public static String SpringbootTableSerialNumber = "1";
    public static ArrayList<String> SpringbootCheckedUrlList = new ArrayList<>();
    public static HashMap<String, SpringbootTableEntry> SpringbootTableEntryMap = new HashMap<>();

    public static String[] SpringbootCheckPaths;

    public static boolean SpringbootDebug = true;

    public static JCheckBox getSpringbootMenuWorkBox(){
        return (JCheckBox) Helper.getComponent(SpringbootComponentPanel, "SpringbootMenuWorkBox");
    }

    public static JTable getSpringbootLoggerTable(){
        return (JTable) Helper.getComponent(SpringbootComponentPanel, "SpringbootLoggerTable");
    }

    public static String getSpringbootSerialNumber(int row){
        return (String) getSpringbootLoggerTable().getValueAt(row,0);
    }

    public static void setSpringbootLoggerTableValueAt(String value, Integer rowIndex, String columnName){
        getSpringbootLoggerTable().getModel().setValueAt(value, rowIndex, SpringbootColumnMap.get(columnName));
    }



    public static String springbootCheckRequest(HttpRequestToBeSent requestToBeSent){


        String serialNumber = SpringbootTableSerialNumber;
        String requestPath  = requestToBeSent.path();

        if(Objects.equals(requestPath, "/")){
            SpringbootCheckPaths = new String[]{
                    "/swagger-resources",
                    "/actuator/env",
                    "/druid/index.html",
                    "/doc.html"
            };
        }else{
            String requestRootPath = requestPath.split("/")[1];
            SpringbootCheckPaths = new String[]{
                    "/swagger-resources",
                    "/actuator/env",
                    "/druid/index.html",
                    "/doc.html",
                    String.format("/%s/swagger-resources",requestRootPath),
                    String.format("/%s/actuator/env",requestRootPath),
                    String.format("/%s/druid/index.html",requestRootPath),
                    String.format("/%s/doc.html",requestRootPath)
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
                requestTime,
                "",
                "",
                "",
        };

        if(!SpringbootTableEntryMap.containsKey(serialNumber)){
            String type="Raw";
            inf[8] = type;
            SpringbootTableEntry rowEntry = new SpringbootTableEntry(inf);
            rowEntry.setRawRequest(requestToBeSent);
            SpringbootTableEntryMap.put(serialNumber, rowEntry);
        }

        ((DefaultTableModel)getSpringbootLoggerTable().getModel()).addRow(inf);

        SpringbootTableSerialNumber = Integer.toString(Integer.parseInt(SpringbootTableSerialNumber)+1);
        return  SPRINGBOOT+serialNumber;
    }

    public static void springbootCheckResponse(HttpResponseReceived responseReceived, String springbootSerialNumber){

        Integer rowIndex = Integer.parseInt(springbootSerialNumber) - 1;

        if(SpringbootDebug)API.logging().output().printf("springbootCheckResponse处理第 %s 个返回包\n", springbootSerialNumber);

        String responseLength = Integer.toString(responseReceived.body().length());
        String responseTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
        HttpResponse simplifyRawhttpResponse = responseReceived.withBody(responseReceived.body().subArray(0, Math.min(20000, Integer.parseInt(responseLength))));

        setSpringbootLoggerTableValueAt(responseLength, rowIndex, "Length");
        setSpringbootLoggerTableValueAt(responseTime, rowIndex, "responseTime");


        SpringbootTableEntryMap.get(springbootSerialNumber).setLength(responseLength);
        SpringbootTableEntryMap.get(springbootSerialNumber).setResponseTime(responseTime);
        SpringbootTableEntryMap.get(springbootSerialNumber).setRawResponse(responseReceived);
        SpringbootTableEntryMap.get(springbootSerialNumber).setSimplifyRawResponse(simplifyRawhttpResponse);
        SpringbootCheckedUrlList.add(SpringbootTableEntryMap.get(springbootSerialNumber).getRawRequest().url());

        if(SpringbootDebug)API.logging().output().println("springbootCheckResponse开始进行深度检查");
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

                HttpRequest httpRequest = SpringbootTableEntryMap.get(SerialNumber).getRawRequest().withPath(path);

                String requestUrl = httpRequest.url();
                if(SpringbootCheckedUrlList.contains(requestUrl)) continue;

                String serialNumber = SpringbootTableSerialNumber;
                String requestMethod = httpRequest.method();
                String requestHost = httpRequest.httpService().host();
                String requestTool = "Extensions";
                String requestPath = httpRequest.path();
                String requestTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
                String[] inf = new String[]{
                        serialNumber,
                        requestTool,
                        requestMethod,
                        requestHost,
                        requestPath,
                        "",
                        requestTime,
                        "",
                        "",
                        "",
                };

                if(path.contains("doc.html")){
                    inf[8]="Doc";
                }else if(path.contains("swagger-resources")){
                    inf[8]="Swagger";
                } else if (path.contains("druid/index.html")) {
                    inf[8]="Druid";
                }else{
                    inf[8]="Actuator";
                }

                SpringbootTableEntry springbootTableEntry = new SpringbootTableEntry(inf);
                ((DefaultTableModel)getSpringbootLoggerTable().getModel()).addRow(inf);

                SpringbootCheckedUrlList.add(requestUrl);
                SpringbootTableEntryMap.put(serialNumber, springbootTableEntry);

                SpringbootTableSerialNumber =Integer.toString(Integer.parseInt(SpringbootTableSerialNumber)+1);
                new MyRegisterHttpHandler.checkWorker("springboot", serialNumber, httpRequest).execute();
                Thread.sleep(1000);
            }


            return null;
        }
    }

}
