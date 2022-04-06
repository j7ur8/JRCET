package burp;


import jrcet.frame.Jrcet;
import jrcet.frame.tools.JSEncrypt.JSEncrypt;
import jrcet.frame.tools.RScript.RScript;

import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;
import java.util.*;
import java.util.List;

import static jrcet.frame.tools.RScript.RScriptComponent.RScriptMainEditor;
import static jrcet.frame.tools.RScript.RScriptComponent.RScriptComponentPanel;
import static jrcet.frame.tools.ToolsComponent.ToolsMenuRScriptLabel;

public class BurpExtender implements IBurpExtender, ITab, IContextMenuFactory, IIntruderPayloadProcessor{
    public static IBurpExtenderCallbacks callbacks;
    public static IExtensionHelpers helpers;
    public static PrintWriter stdout;
    private final JComponent t=new Jrcet().main();

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
            ToolsMenuRScriptLabel.setMapStream("IHttpRequestResponse",messages);
            ToolsMenuRScriptLabel.setMapStream("IExtensionHelpers",helpers);
            RScriptMainEditor.setText(new RScript().initScript((IHttpRequestResponse[]) ToolsMenuRScriptLabel.getMapStream("IHttpRequestResponse"),(IExtensionHelpers) ToolsMenuRScriptLabel.getMapStream("IExtensionHelpers")));
            RScriptComponentPanel.validate();
            RScriptComponentPanel.repaint();
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
        String newPayload = JSEncrypt.sendPayload(payload);
        stdout.println(newPayload);
        return helpers.stringToBytes(newPayload);
    }
}