package jrcet.diycomponents;

import jrcet.diycomponents.DiyJTextArea.ui.rsyntaxtextarea.RSyntaxTextArea;
import jrcet.frame.Tools.Password.Generate.Generate;
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
            case "GenerateMainMenuModeSpecialcharBox":
                rootPanel = (JComponent) eCheckBox.getParent();
                if(eCheckBox.isSelected()){
                    Generate.SpecialChar = true;
                    RSyntaxTextArea eTextArea = (RSyntaxTextArea) Helper.getComponent(rootPanel, "GenerateMainMenuModeSpecialcharArea"); assert eTextArea!=null;
                    for(String s: eTextArea.getText().split("\n")){
                        if(s.equals("")) break;
                        if(s.length()!=1){
                            eTextArea.setText("输入错误");
                            break;
                        }else{
                            Generate.SpecialCharList.add(s);
                        }
                    }
                }else{
                    Generate.SpecialChar = false;
                }
                break;
            case "GenerateMainMenuModeDigital4Box":
                if(eCheckBox.isSelected()){
                    rootPanel = (JComponent) eCheckBox.getParent();
                    closeCheckBox(rootPanel, Helper.getComponentPos(rootPanel, eCheckBoxName));
                    Generate.Digital4Suffix = true;
                    eField = (JTextField) Helper.getComponent(rootPanel, "GenerateMainMenuModeDateField");assert eField!=null;
                    Generate.YearStart = eField.getText().split("-")[0];
                    Generate.YearEnd = eField.getText().split("-")[1];
                }else{
                    Generate.Digital4Suffix = false;
                    Generate.YearStart = "1950";
                    Generate.YearEnd = "2022";
                }

                break;
            case "GenerateMainMenuModeDigital6Box":
                if(eCheckBox.isSelected()){
                    rootPanel = (JComponent) eCheckBox.getParent();
                    closeCheckBox(rootPanel, Helper.getComponentPos(rootPanel, eCheckBoxName));
                    Generate.Digital6Suffix = true;
                    eField = (JTextField) Helper.getComponent(rootPanel, "GenerateMainMenuModeDateField");assert eField!=null;
                    Generate.YearStart = eField.getText().split("-")[0];
                    Generate.YearEnd = eField.getText().split("-")[1];
                }else{
                    Generate.Digital6Suffix = false;
                    Generate.YearStart = "1950";
                    Generate.YearEnd = "2022";
                }

                break;
            case "GenerateMainMenuModeDigital8Box":
                if(eCheckBox.isSelected()){
                    rootPanel = (JComponent) eCheckBox.getParent();
                    closeCheckBox(rootPanel, Helper.getComponentPos(rootPanel, eCheckBoxName));
                    Generate.Digital8Suffix = true;
                    eField = (JTextField) Helper.getComponent(rootPanel, "GenerateMainMenuModeDateField");assert eField!=null;
                    Generate.YearStart = eField.getText().split("-")[0];
                    Generate.YearEnd = eField.getText().split("-")[1];
                }else{
                    Generate.Digital8Suffix = false;
                    Generate.YearStart = "1950";
                    Generate.YearEnd = "2022";
                }
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
