package jrcet.diycomponents;

import burp.IExtensionHelpers;
import burp.IHttpRequestResponse;
import jrcet.frame.tools.RScript.RScript;

import javax.swing.*;

public class DiyJCheckBox extends JCheckBox {

    public DiyJCheckBox(String name){
        super(name);
        addChangeListener(e -> {
            DiyJCheckBox targetCheckBox = (DiyJCheckBox) e.getSource();
            JComponent toolsPanel = (JComponent) targetCheckBox.getParent().getParent().getParent().getComponent(0);
            DiyJLabel RScriptLabel = (DiyJLabel) toolsPanel.getComponent(0);
            IHttpRequestResponse[] messages = (IHttpRequestResponse[]) RScriptLabel.getMapStream("IHttpRequestResponse");
            IExtensionHelpers helpers = (IExtensionHelpers) RScriptLabel.getMapStream("IExtensionHelpers");

            if(targetCheckBox.isSelected() && messages!=null && helpers!=null){
                JScrollPane RScriptScroll = (JScrollPane) RScriptLabel.getMapPanel("RScript").getComponent(1);
                JTextArea RScriptTextArea = (JTextArea) RScriptScroll.getViewport().getComponent(0);
                RScriptTextArea.setText(new RScript().initSessionScript(messages,helpers));
            }else if(!targetCheckBox.isSelected() && messages!=null && helpers!=null){
                JScrollPane RScriptScroll = (JScrollPane) RScriptLabel.getMapPanel("RScript").getComponent(1);
                JTextArea RScriptTextArea = (JTextArea) RScriptScroll.getViewport().getComponent(0);
                RScriptTextArea.setText(new RScript().initScript(messages,helpers));
            }else{
                targetCheckBox.setSelected(false);
            }
            toolsPanel.validate();
            toolsPanel.repaint();
        });
    }
}
