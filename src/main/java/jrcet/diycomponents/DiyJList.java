package jrcet.diycomponents;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class DiyJList<E> extends JList<E> implements ListSelectionListener {

    public DiyJList(E[] targetList){
        super(targetList);
        addListSelectionListener(this);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(e.getValueIsAdjusting()){
        }
    }
}
