package burp;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import jrcet.frame.tools.Dencrypt.Url.Url;
import jrcet.frame.tools.Intruder.Intruder;
import jrcet.frame.tools.Scanner.Fastjson.Fastjson;
import jrcet.frame.tools.Scanner.Fastjson.FastjsonComponent;
import jrcet.frame.tools.Scanner.Scanner;
import jrcet.help.Helper;
import jrcet.frame.Jrcet;
//import jrcet.frame.tools.RScript.RScript;
import jrcet.help.ShutdownThread;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.PrintWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;



public class BurpExtender implements IBurpExtender, ITab, IContextMenuFactory, IIntruderPayloadProcessor, IScannerCheck {
    public static IBurpExtenderCallbacks callbacks;
    public static IExtensionHelpers helpers;
    public static PrintWriter stdout;

    public BurpExtender(){
        Runtime.getRuntime().addShutdownHook(new ShutdownThread());
    }

    @Override
    public void registerExtenderCallbacks(IBurpExtenderCallbacks callbacks) {

        BurpExtender.callbacks = callbacks;
        stdout = new PrintWriter(callbacks.getStdout(), true);
        helpers = callbacks.getHelpers();


        callbacks.addSuiteTab(this);
        callbacks.setExtensionName("JRCET");
//        callbacks.registerScannerCheck(this);
        callbacks.registerContextMenuFactory(this);
        callbacks.registerIntruderPayloadProcessor(this);

//        Helper.dbConnector = Helper.getConnector();
//        JComponent AssetMainResultUnitPanel = Helper.getComponent(AssetComponentPanel,"AssetMainResultUnitPanel"); assert AssetMainResultUnitPanel!=null;
//        Asset.initResultUnitPanel(AssetMainResultUnitPanel,Asset.getAsset(0,Asset.dataNumber));
//        AssetMainResultUnitPanel.updateUI();
//        Asset.registerHotKey();
    }

    @Override
    public Component getUiComponent() {
        return new Jrcet().main();
    }

    @Override
    public String getTabCaption() {
        return "JRCET";
    }

    @Override
    public List<JMenuItem> createMenuItems(IContextMenuInvocation iContextMenuInvocation) {

        JMenuItem RScriptItem = new JMenuItem("RScript");
        IHttpRequestResponse[] messages = iContextMenuInvocation.getSelectedMessages();

        RScriptItem.addActionListener(e -> {
//            RTextArea tTextArea = (RTextArea) Helper.getComponent(RScriptComponentPanel,"RScriptMainEditor");
//            assert tTextArea != null;
////            tTextArea.setText(new RScript().initScript(messages, helpers));
        });

        return Collections.singletonList(RScriptItem);
    }


    @Override
    public String getProcessorName() {
        return "JIntruder";
    }

    @Override
    public byte[] processPayload(byte[] currentPayload, byte[] originalPayload, byte[] baseValue) {

        String newPayload = Intruder.involeIntruder(currentPayload);
//        stdout.println(newPayload);
        return helpers.stringToBytes(newPayload);
    }

    @Override
    public List<IScanIssue> doPassiveScan(IHttpRequestResponse baseRequestResponse) {
        List<IScanIssue> iScanIssues = new ArrayList<>();

        IRequestInfo iRequestInfo = helpers.analyzeRequest(baseRequestResponse);

        //Scanner.checkUrl根据Scan.UrlScanList中是否存在相同的协议+地址+端口+路径组合返回True或False
        //并再扫描结束后再将url加入Scan.UrlScanList队列
        URL URLObject = iRequestInfo.getUrl();
        String url = URLObject.toString();
        if(Scanner.checkUrl(url)){
            return null;
        }
        byte[] requestPackage = baseRequestResponse.getRequest();
        HashMap<String, Object> jsonParameterMap = Scanner.getJsonParameter(requestPackage);

        //更新request package的内容，并依次发包
        //get
//        ParserConfig.getGlobalInstance().setSafeMode(true);
        HashMap<String, String> getParameterMap = (HashMap<String, String>) jsonParameterMap.get("get");
        HashMap<String, String> postParameterMap = (HashMap<String, String>) jsonParameterMap.get("post");
        for(String key : getParameterMap.keySet()){
            JSONObject jsonObject = (JSONObject) JSON.parse(getParameterMap.get(key));
            jsonObject.put("x", Scanner.ReplaceNumber);
            String randomSubDomain = Helper.createRandomString(6);
            String payload = Url.encode(jsonObject.toJSONString().replace(Scanner.ReplaceNumberString, Scanner.FastjsonPayload.replace("{randomStr}",randomSubDomain)));
            byte[] nRequestPackage = helpers.updateParameter(requestPackage,helpers.buildParameter(key,payload,IParameter.PARAM_URL));
            IHttpRequestResponse nRequestResponse = callbacks.makeHttpRequest(baseRequestResponse.getHttpService(), nRequestPackage);
            IResponseInfo nResponseInfo = helpers.analyzeResponse(nRequestResponse.getResponse());

            Helper.sleep(1000);
            if(Scanner.verifyVul(randomSubDomain)){

                iScanIssues.add(new CustomScanIssue(
                        nRequestResponse.getHttpService(),
                        URLObject,
                        new IHttpRequestResponse[]{nRequestResponse},
                        "fastjson",
                        "fastjson Deserialization vulnerability",
                        "High"
                ));
//                callbacks.issueAlert("发现fastjson反序列化漏洞");
                Fastjson.FastjsonLogEntry nFastjsonLogEntry = new Fastjson.FastjsonLogEntry(url,iRequestInfo.getMethod(),Integer.toString(nResponseInfo.getStatusCode()),randomSubDomain,"FastJson Unserialization",nRequestResponse);
                Fastjson.FastjsonLogEntries.add(nFastjsonLogEntry);
                JTable FastjsonInfoTable = (JTable) Helper.getComponent(FastjsonComponent.FastjsonComponentPanel,"FastjsonInfoTable");
                assert FastjsonInfoTable != null;
                DefaultTableModel fastjsonDefaultTableModel = (DefaultTableModel) FastjsonInfoTable.getModel();
                fastjsonDefaultTableModel.addRow(new Object[]{nFastjsonLogEntry.url(),nFastjsonLogEntry.method(),nFastjsonLogEntry.status(),nFastjsonLogEntry.payload(),nFastjsonLogEntry.result(),new SimpleDateFormat("HH:mm:ss").format(new Date())});
            }

        }
        //post
        for(String key : postParameterMap.keySet()){
            JSONObject jsonObject = (JSONObject) JSON.parse(postParameterMap.get(key));
            jsonObject.put("x", Scanner.ReplaceNumber);
            String randomSubDomain = Helper.createRandomString(6);
            String payload = Url.encode(jsonObject.toJSONString().replace(Scanner.ReplaceNumberString, Scanner.FastjsonPayload.replace("{randomStr}",randomSubDomain)));
            byte[] nRequestPackage = helpers.updateParameter(requestPackage,helpers.buildParameter(key,payload,IParameter.PARAM_BODY));
            IHttpRequestResponse nRequestResponse = callbacks.makeHttpRequest(baseRequestResponse.getHttpService(), nRequestPackage);
            IResponseInfo nResponseInfo = helpers.analyzeResponse(nRequestResponse.getResponse());
            Helper.sleep(1000);
            if(Scanner.verifyVul(randomSubDomain)){
                iScanIssues.add(new CustomScanIssue(
                        nRequestResponse.getHttpService(),
                        URLObject,
                        new IHttpRequestResponse[]{nRequestResponse},
                        "fastjson",
                        "fastjson Deserialization vulnerability",
                        "High"
                ));
//                callbacks.issueAlert("发现fastjson反序列化漏洞");
                Fastjson.FastjsonLogEntry nFastjsonLogEntry = new Fastjson.FastjsonLogEntry(url,iRequestInfo.getMethod(),Integer.toString(nResponseInfo.getStatusCode()),randomSubDomain,"FastJson Unserialization",nRequestResponse);
                Fastjson.FastjsonLogEntries.add(nFastjsonLogEntry);
                JTable FastjsonInfoTable = (JTable) Helper.getComponent(FastjsonComponent.FastjsonComponentPanel,"FastjsonInfoTable");
                assert FastjsonInfoTable != null;
                DefaultTableModel fastjsonDefaultTableModel = (DefaultTableModel) FastjsonInfoTable.getModel();
                fastjsonDefaultTableModel.addRow(new Object[]{nFastjsonLogEntry.url(),nFastjsonLogEntry.method(),nFastjsonLogEntry.status(),nFastjsonLogEntry.payload(),nFastjsonLogEntry.result(),new SimpleDateFormat("HH:mm:ss").format(new Date())});
            }
        }


        return iScanIssues;
    }

    @Override
    public List<IScanIssue> doActiveScan(IHttpRequestResponse baseRequestResponse, IScannerInsertionPoint insertionPoint) {
        return null;
    }

    @Override
    public int consolidateDuplicateIssues(IScanIssue existingIssue, IScanIssue newIssue) {
        return 0;
    }

}