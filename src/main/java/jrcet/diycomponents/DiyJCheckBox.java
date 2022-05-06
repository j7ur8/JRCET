package jrcet.diycomponents;

import burp.lib.Helper;
import jrcet.diycomponents.DiyJTextArea.ui.rtextarea.RTextArea;
import jrcet.frame.tools.RScript.RScript;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import static jrcet.frame.tools.RScript.RScriptComponent.RScriptComponentPanel;

public class DiyJCheckBox extends JCheckBox implements ChangeListener {

    public DiyJCheckBox(String name){
        super(name);

        addChangeListener(this);
    }

    @Override
    public void stateChanged(ChangeEvent e) {

            DiyJCheckBox eCheckBox = (DiyJCheckBox) e.getSource();

            RTextArea tTextArea = (RTextArea) Helper.getComponent(RScriptComponentPanel,"RScriptMainEditor");
            assert tTextArea != null;
            if(eCheckBox.isSelected() && RScript.messages!=null && RScript.helpers!=null){
                tTextArea.setText(new RScript().initSessionScript(RScript.messages, RScript.helpers));
            }else if(!eCheckBox.isSelected() && RScript.messages!=null && RScript.helpers!=null){
                tTextArea.setText(new RScript().initScript(RScript.messages, RScript.helpers));
            }else{
                eCheckBox.setSelected(false);
            }
    }
}
