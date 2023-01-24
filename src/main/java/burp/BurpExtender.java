package burp;



import jrcet.frame.Intruder.Intruder;

import jrcet.frame.Jrcet;
import jrcet.help.ShutdownThread;

import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;
import java.util.*;
import java.util.List;



public class BurpExtender implements IBurpExtender, ITab, IContextMenuFactory, IIntruderPayloadProcessor, IScannerCheck {
    public static IBurpExtenderCallbacks callbacks;
    public static IExtensionHelpers helpers;
    public static PrintWriter stdout;
    public static PrintWriter errors;

    public BurpExtender(){
        Runtime.getRuntime().addShutdownHook(new ShutdownThread());
    }

    @Override
    public void registerExtenderCallbacks(IBurpExtenderCallbacks callbacks) {

        BurpExtender.callbacks = callbacks;
        stdout = new PrintWriter(callbacks.getStdout(), true);
        errors = new PrintWriter(callbacks.getStderr(), true);
        helpers = callbacks.getHelpers();


        callbacks.addSuiteTab(this);
        callbacks.setExtensionName("JRCET");
        callbacks.registerScannerCheck(this);
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

        String newPayload = "";
        if(Arrays.equals(currentPayload, "captcha".getBytes())){
            newPayload = Intruder.invokeCaptcha(currentPayload);
        }else{
            newPayload = Intruder.invokeIntruder(currentPayload);
        }
//        stdout.println(newPayload);
        return helpers.stringToBytes(newPayload);
    }

    @Override
    public List<IScanIssue> doPassiveScan(IHttpRequestResponse baseRequestResponse) {
        List<IScanIssue> iScanIssues = new ArrayList<>();


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