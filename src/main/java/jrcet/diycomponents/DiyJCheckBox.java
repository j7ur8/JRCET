package jrcet.diycomponents;

import jrcet.frame.tools.Intruder.Intruder;
import jrcet.frame.tools.Intruder.IntruderComponent;
import jrcet.lib.Helper;
import jrcet.diycomponents.DiyJTextArea.ui.rtextarea.RTextArea;
import jrcet.frame.tools.RScript.RScript;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Objects;

import static jrcet.frame.tools.Intruder.IntruderComponent.IntruderComponentPanel;
import static jrcet.frame.tools.RScript.RScriptComponent.RScriptComponentPanel;

public class DiyJCheckBox extends JCheckBox implements ItemListener {

    public DiyJCheckBox(String name){
        super(name);

        addItemListener(this);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

        DiyJCheckBox eCheckBox = (DiyJCheckBox) e.getSource();
        String eCheckBoxName = eCheckBox.getName();

        switch (eCheckBoxName){
            case "RScriptMenuSessionBox":
                RTextArea tTextArea = (RTextArea) Helper.getComponent(RScriptComponentPanel,"RScriptMainEditor");
                assert tTextArea != null;
                if(eCheckBox.isSelected() && RScript.messages!=null && RScript.helpers!=null){
                    tTextArea.setText(new RScript().initSessionScript(RScript.messages, RScript.helpers));
                }else if(!eCheckBox.isSelected() && RScript.messages!=null && RScript.helpers!=null){
                    tTextArea.setText(new RScript().initScript(RScript.messages, RScript.helpers));
                }else{
                    eCheckBox.setSelected(false);
                }
                break;
            case "IntruderMainControlAesBox":
            case "IntruderMainControlRsaBox":
            case "IntruderMainControlBaseBox":
            case "IntruderMainControlAsciiBox":
            case "IntruderMainControlUnicodeBox":
                JLabel eLabel = (JLabel) Helper.getComponent(IntruderComponentPanel, "IntruderMainControlShowPanel"); assert eLabel != null;
                String boxText = eCheckBox.getText();
                String labelText = eLabel.getText();
                if(eCheckBox.isSelected()){
                    Intruder.registerProcessor(boxText);
                    if(Objects.equals(labelText, "")){
                        eLabel.setText(labelText+boxText);
                    }else{
                        eLabel.setText(labelText+"->"+boxText);
                    }
                }else{
                    Intruder.unregisterProcessor(boxText);
                    if(labelText.contains("->")){
                        labelText=labelText.replace(boxText+"->", "").replace("->"+boxText, "");
                    }else{
                        labelText=labelText.replace(boxText, "");
                    }
                    eLabel.setText(labelText);
                }
                IntruderComponentPanel.updateUI();
                break;
        }
    }
}
