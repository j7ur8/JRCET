package burp;


import jrcet.frame.Index;
import jrcet.frame.tools.JSEncrypt.Utils;
import jrcet.frame.tools.RScript.RScript;

import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;
import java.util.*;
import java.util.List;

import static jrcet.frame.tools.RScript.RScriptComponent.RScriptJTextArea;
import static jrcet.frame.tools.RScript.RScriptComponent.RScriptPanel;
import static jrcet.frame.tools.ToolsComponent.toolsMenuRScriptLabel;

public class BurpExtender implements IBurpExtender, ITab, IContextMenuFactory, IIntruderPayloadProcessor{
    public static IBurpExtenderCallbacks callbacks;
    public static IExtensionHelpers helpers;
    public static PrintWriter stdout;
    private final JComponent t=new Index().main();

    public BurpExtender(){
        Runtime.getRuntime().addShutdownHook(new ShutdownThread());
    }

    @Override
    public void registerExtenderCallbacks(IBurpExtenderCallbacks callbacks) {

        stdout = new PrintWriter(callbacks.getStdout(), true);
        BurpExtender.callbacks = callbacks;
        helpers = callbacks.getHelpers();
        callbacks.setExtensionName("J7ur8's RCE Tool");
        callbacks.registerContextMenuFactory(this);
        callbacks.addSuiteTab(this);
        callbacks.registerIntruderPayloadProcessor(this);
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
            toolsMenuRScriptLabel.setMapStream("IHttpRequestResponse",messages);
            toolsMenuRScriptLabel.setMapStream("IExtensionHelpers",helpers);
            RScriptJTextArea.setText(new RScript().initScript((IHttpRequestResponse[]) toolsMenuRScriptLabel.getMapStream("IHttpRequestResponse"),(IExtensionHelpers) toolsMenuRScriptLabel.getMapStream("IExtensionHelpers")));
            RScriptPanel.validate();
            RScriptPanel.repaint();
        });

        return Collections.singletonList(RScriptItem);
    }


    @Override
    public String getProcessorName() {
        return "JRCET's JSEncrypt";
    }

    @Override
    public byte[] processPayload(byte[] currentPayload, byte[] originalPayload, byte[] baseValue) {
        String payload = new String(currentPayload);
        String newPayload = Utils.sendPayload(payload);
        stdout.println(newPayload);
        return helpers.stringToBytes(newPayload);
    }
}