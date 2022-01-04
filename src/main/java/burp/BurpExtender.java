package burp;

import javafx.beans.binding.ListExpression;
import jrcet.diycomponents.DiyJLabel;
import jrcet.frame.Index;
import jrcet.frame.tools.JSEncrypt.Utils;
import jrcet.frame.tools.RScript.RScript;
import jrcet.frame.tools.RScript.RScriptComponent;

import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;
import java.util.*;
import java.util.List;

public class BurpExtender implements IBurpExtender, ITab, IContextMenuFactory, IIntruderPayloadProcessor{
    public static IBurpExtenderCallbacks callbacks;
    public static IExtensionHelpers helpers;
    public static PrintWriter stdout;
    private final JComponent t=new Index().main();


    @Override
    public void registerExtenderCallbacks(IBurpExtenderCallbacks callbacks) {

        stdout = new PrintWriter(callbacks.getStdout(), true);
        BurpExtender.callbacks = callbacks;
        helpers = callbacks.getHelpers();
        callbacks.setExtensionName("J7ur8's RCE Tool");
        callbacks.registerContextMenuFactory(this);
        callbacks.addSuiteTab(this);
    }

    @Override
    public Component getUiComponent() {
        return t;
    }

    @Override
    public String getTabCaption() {
        return "JRCET";
    }

    @Override
    public List<JMenuItem> createMenuItems(IContextMenuInvocation iContextMenuInvocation) {

        IHttpRequestResponse[] messages = iContextMenuInvocation.getSelectedMessages();
        JMenuItem RScriptItem = new JMenuItem("RScript");

        RScriptItem.addActionListener(e -> {
            JComponent a= (JComponent) t.getComponent(0);
            DiyJLabel aJLabel = (DiyJLabel) a.getComponent(2);
            JPanel aaJPanel = (JPanel) aJLabel.getMapPanel("Tools").getComponent(0);
            DiyJLabel aaJLabel = (DiyJLabel) aaJPanel.getComponent(0);
            aaJLabel.setMapStream("IHttpRequestResponse",messages);
            aaJLabel.setMapStream("IExtensionHelpers",helpers);
            JScrollPane bb = (JScrollPane) aaJLabel.getMapPanel(aaJLabel.getText()).getComponent(1);
            JTextArea bbb = (JTextArea) bb.getViewport().getComponent(0);
            bbb.setText(new RScript().initScript((IHttpRequestResponse[]) aaJLabel.getMapStream("IHttpRequestResponse"),(IExtensionHelpers) aaJLabel.getMapStream("IExtensionHelpers")));
            a.validate();
            a.repaint();
        });

        return Arrays.asList(RScriptItem);
    }


    @Override
    public String getProcessorName() {
        return "JRCET's JSEncrypt";
    }

    @Override
    public byte[] processPayload(byte[] currentPayload, byte[] originalPayload, byte[] baseValue) {
        String payload = new String(currentPayload);
        String newPayload = Utils.sendPayload(payload);
        return helpers.stringToBytes(newPayload);
    }
}