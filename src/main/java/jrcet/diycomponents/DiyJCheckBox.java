package jrcet.diycomponents;

import burp.IExtensionHelpers;
import burp.IHttpRequestResponse;
import jrcet.frame.tools.RScript.RScript;

import javax.swing.*;

import static jrcet.frame.tools.RScript.RScriptComponent.RScriptMainEditor;
import static jrcet.frame.tools.RScript.RScriptComponent.RScriptComponentPanel;
import static jrcet.frame.tools.ToolsComponent.ToolsMenuRScriptLabel;

public class DiyJCheckBox extends JCheckBox {

    public DiyJCheckBox(String name){
        super(name);
        addChangeListener(e -> {
            DiyJCheckBox targetCheckBox = (DiyJCheckBox) e.getSource();
            IHttpRequestResponse[] messages = (IHttpRequestResponse[]) ToolsMenuRScriptLabel.getMapStream("IHttpRequestResponse");
            IExtensionHelpers helpers = (IExtensionHelpers) ToolsMenuRScriptLabel.getMapStream("IExtensionHelpers");

            if(targetCheckBox.isSelected() && messages!=null && helpers!=null){
                RScriptMainEditor.setText(new RScript().initSessionScript(messages,helpers));
            }else if(!targetCheckBox.isSelected() && messages!=null && helpers!=null){
                RScriptMainEditor.setText(new RScript().initScript(messages,helpers));
            }else{
                targetCheckBox.setSelected(false);
            }
            RScriptComponentPanel.validate();
            RScriptComponentPanel.repaint();
        });
    }
}
