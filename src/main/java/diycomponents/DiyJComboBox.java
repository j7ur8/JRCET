package diycomponents;

import javax.swing.*;

public class DiyJComboBox<E> extends JComboBox<E>  {

    public DiyJComboBox(E[] targetList){
        super(targetList);
        ((JLabel)getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
    }

}
