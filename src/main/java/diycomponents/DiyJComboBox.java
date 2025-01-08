package diycomponents;

import jrcet.frame.Scanner.Springboot.Springboot;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import static burp.MyExtender.BurpAPI;

public class DiyJComboBox<E> extends JComboBox<E>  {

    public DiyJComboBox(E[] targetList){
        super(targetList);
        addItemListener(new DiyJComboBoxItemListener());
        ((JLabel)getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
    }

    static class DiyJComboBoxItemListener implements ItemListener{
        @Override
        public void itemStateChanged(ItemEvent e) {

        }
    }
}
