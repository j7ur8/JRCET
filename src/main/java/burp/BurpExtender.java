package burp;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;

public class BurpExtender implements IBurpExtender, ITab, IHttpListener,IContextMenuFactory{

    public JPanel mainPanel;
    public PrintWriter stdout;
    public IExtensionHelpers mainHelpers;
    public ITextEditor iRequestTextEditor;
    public ITextEditor iResponseTextEditor;


    @Override
    public void registerExtenderCallbacks(IBurpExtenderCallbacks callbacks) {

        callbacks.setExtensionName("BurpLearn");

        this.mainHelpers = callbacks.getHelpers();
        this.stdout = new PrintWriter(callbacks.getStdout(), true);
        this.stdout.println("hello burp!");

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                mainPanel = new JPanel(null);

                iRequestTextEditor = callbacks.createTextEditor();
                iRequestTextEditor.setEditable(false);

                iResponseTextEditor = callbacks.createTextEditor();
                iResponseTextEditor.setEditable(false);

                callbacks.customizeUiComponent(mainPanel);
                callbacks.addSuiteTab(BurpExtender.this);
            }
        });
        callbacks.registerHttpListener(this);
        callbacks.registerContextMenuFactory(this);
    }

    // 实现 ITab 接口的 getTabCaption 方法
    @Override
    public String getTabCaption() {
        return "测试";
    }

    // 实现 ITab 接口的 getUiComponent 方法
    @Override
    public Component getUiComponent() {
        return mainPanel;
    }

    @Override
    public void processHttpMessage(int toolFlag, boolean messageIsRequest, IHttpRequestResponse messageInfo) {
    }

    @Override
    public List<JMenuItem> createMenuItems(IContextMenuInvocation invocation) {
        IHttpRequestResponse[] responsesSelected = invocation.getSelectedMessages();
        stdout.println(responsesSelected);
        return Arrays.asList(new JMenuItem("JRCET"));
    }

}