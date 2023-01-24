package jrcet.diycomponents;

import jrcet.diycomponents.DiyJTextArea.ui.rsyntaxtextarea.RSyntaxTextArea;
import jrcet.help.Helper;
import jrcet.diycomponents.DiyJTextArea.ui.rtextarea.RTextArea;
//import jrcet.frame.tools.RScript.RScript;

import javax.swing.*;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import static jrcet.frame.Tools.RScript.RScriptComponent.RScriptComponentPanel;

public class DiyJCheckBox extends JCheckBox implements ItemListener {

    public DiyJCheckBox(String name){
        super(name);

        addItemListener(this);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

        DiyJCheckBox eCheckBox = (DiyJCheckBox) e.getSource();
        String eCheckBoxName = eCheckBox.getName();
        JComponent rootPanel = null;
        JTextField eField = null;
        switch (eCheckBoxName){
            case "RScriptMenuSessionBox":
                RTextArea tTextArea = (RTextArea) Helper.getComponent(RScriptComponentPanel,"RScriptMainEditor");
                assert tTextArea != null;
//                if(eCheckBox.isSelected() && RScript.messages!=null && RScript.helpers!=null){
//                    tTextArea.setText(new RScript().initSessionScript(RScript.messages, RScript.helpers));
//                }else if(!eCheckBox.isSelected() && RScript.messages!=null && RScript.helpers!=null){
//                    tTextArea.setText(new RScript().initScript(RScript.messages, RScript.helpers));
//                }else{
//                    eCheckBox.setSelected(false);
//                }
                break;

        }
    }
    public void closeCheckBox(JComponent rootComponent, int pos){
        for(int i=0; i<rootComponent.getComponents().length; i++){
            if(i!=pos && rootComponent.getComponent(i).getClass().toString().contains("JCheckBox")){
                ((JCheckBox)rootComponent.getComponent(i)).setSelected(false);
            }
        }
    }
}
