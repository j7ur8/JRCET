package jrcet.diycomponents;

import burp.IExtensionHelpers;
import burp.IHttpRequestResponse;
import jrcet.frame.tools.RScript.RScript;

import javax.swing.*;

import static jrcet.frame.tools.RScript.RScriptComponent.RScriptJTextArea;
import static jrcet.frame.tools.RScript.RScriptComponent.RScriptPanel;
import static jrcet.frame.tools.ToolsComponent.toolsMenuRScriptLabel;

public class DiyJCheckBox extends JCheckBox {

    public DiyJCheckBox(String name){
        super(name);
        addChangeListener(e -> {
            DiyJCheckBox targetCheckBox = (DiyJCheckBox) e.getSource();
            IHttpRequestResponse[] messages = (IHttpRequestResponse[]) toolsMenuRScriptLabel.getMapStream("IHttpRequestResponse");
            IExtensionHelpers helpers = (IExtensionHelpers) toolsMenuRScriptLabel.getMapStream("IExtensionHelpers");

            if(targetCheckBox.isSelected() && messages!=null && helpers!=null){
                RScriptJTextArea.setText(new RScript().initSessionScript(messages,helpers));
            }else if(!targetCheckBox.isSelected() && messages!=null && helpers!=null){
                RScriptJTextArea.setText(new RScript().initScript(messages,helpers));
            }else{
                targetCheckBox.setSelected(false);
            }
            RScriptPanel.validate();
            RScriptPanel.repaint();
        });
    }
}
