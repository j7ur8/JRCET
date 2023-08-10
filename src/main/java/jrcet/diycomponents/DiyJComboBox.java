package jrcet.diycomponents;

import jrcet.frame.HText.Format.Format;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class DiyJComboBox<E> extends JComboBox<E>  {

    public DiyJComboBox(E[] targetList){
        super(targetList);
        ((JLabel)getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
    }

}
