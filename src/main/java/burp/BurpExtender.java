package burp;


import jrcet.frame.tools.Intruder.Intruder;
import jrcet.help.Helper;
import jrcet.diycomponents.DiyJTextArea.ui.rtextarea.RTextArea;
import jrcet.frame.Jrcet;
import jrcet.frame.tools.RScript.RScript;
import jrcet.help.ShutdownThread;

import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;
import java.util.*;
import java.util.List;

import static jrcet.frame.tools.RScript.RScriptComponent.RScriptComponentPanel;

public class BurpExtender implements IBurpExtender, ITab, IContextMenuFactory, IIntruderPayloadProcessor{
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
        callbacks.registerContextMenuFactory(this);
        callbacks.registerIntruderPayloadProcessor(this);
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
            RTextArea tTextArea = (RTextArea) Helper.getComponent(RScriptComponentPanel,"RScriptMainEditor");
            assert tTextArea != null;
            tTextArea.setText(new RScript().initScript(messages, helpers));
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
}