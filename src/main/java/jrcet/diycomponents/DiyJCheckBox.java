package jrcet.diycomponents;


import jrcet.help.Helper;

//import jrcet.frame.tools.RScript.RScript;

import javax.swing.*;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


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
