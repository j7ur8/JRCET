package burp;

import jrcet.frame.Index;
import ui.test;

import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;
import java.net.MalformedURLException;

public class BurpExtender implements IBurpExtender, ITab {
    private IBurpExtenderCallbacks callbacks;
    private IExtensionHelpers helpers;
    private PrintWriter stdout;

    @Override
    public void registerExtenderCallbacks(IBurpExtenderCallbacks callbacks) {

        stdout = new PrintWriter(callbacks.getStdout(), true);
        this.callbacks = callbacks;
        helpers = callbacks.getHelpers();
        callbacks.setExtensionName("J7ur8's RCE Tool");

        callbacks.addSuiteTab(this);
    }

    @Override
    public String getTabCaption() {
        return "JRCET";
    }

    @Override
    public Component getUiComponent() {
//        try {
//            test tt=new test();
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
        JComponent t=new Index().main();

        return t;
    }


}