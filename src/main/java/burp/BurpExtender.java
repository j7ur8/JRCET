package burp;

import burp.lib.Json;
import jrcet.diycomponents.DiyJComboBox;
import jrcet.diycomponents.DiyJComponent;
import jrcet.diycomponents.DiyJLabel;
import jrcet.frame.Index;
import jrcet.frame.tools.RScript.RScript;

import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.List;

public class BurpExtender implements IBurpExtender, ITab,IContextMenuFactory{
    private IBurpExtenderCallbacks callbacks;
    private IExtensionHelpers helpers;
    private PrintWriter stdout;
    private final JComponent t=new Index().main();


    @Override
    public void registerExtenderCallbacks(IBurpExtenderCallbacks callbacks) {

        stdout = new PrintWriter(callbacks.getStdout(), true);
        this.callbacks = callbacks;
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

//        stdout.println(py);
        JMenuItem RScriptItem = new JMenuItem("RScript");
        RScriptItem.addActionListener(e -> {
            JComponent a= (JComponent) t.getComponent(0);
            DiyJLabel aJLabel = (DiyJLabel) a.getComponent(2);
            JPanel aaJPanel = (JPanel) aJLabel.getMapPanel("Tools").getComponent(0);
            DiyJLabel aaJLabel = (DiyJLabel) aaJPanel.getComponent(0);
            JScrollPane bb = (JScrollPane) aaJLabel.getMapPanel(aaJLabel.getText()).getComponent(0);
//            stdout.println(bb.get);
            JTextArea bbb = (JTextArea) bb.getViewport().getComponent(0);

//            stdout.println();
            bbb.setText(new RScript().initScript(messages,helpers));
            a.validate();
            a.repaint();
        });
        return Arrays.asList(RScriptItem);
    }


}