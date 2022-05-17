package jrcet.diycomponents;

import jrcet.frame.exploit.php.PbootCMS.PbootCMSComponent;
import jrcet.frame.exploit.php.ThinkPHP.ThinkPHPComponent;
import jrcet.frame.tools.Dencrypt.Base.Base;
import jrcet.frame.tools.HText.Format.Format;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;
import java.util.Objects;

public class DiyJComboBox<E> extends JComboBox<E> implements ItemListener {

    public String[] types = new String[]{"PHP","Java","Nodejs","Other"};

    public DiyJComboBox(E[] targetList){
        super(targetList);
        addItemListener(this);
        setPreferredSize(new Dimension(70,30));
    }


    @Override
    public void itemStateChanged(ItemEvent e) {

        DiyJComboBox<String> eComboBox = (DiyJComboBox<String>) e.getSource();
        String eName = eComboBox.getName();

        if(e.getStateChange() == ItemEvent.SELECTED){
            String eItem = (String) eComboBox.getSelectedItem();;
            switch (eName){
                case "FormatMainControlModeBox":
                    Format.FormatMode = eItem;
                    break;
                case "BaseMainControlModeBox":
                    Base.BaseMode = eItem;
                    break;
            }
        }
    }
}
