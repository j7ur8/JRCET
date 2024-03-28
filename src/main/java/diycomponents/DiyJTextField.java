package diycomponents;

import help.Helper;

import javax.swing.*;
import java.awt.*;

public class DiyJTextField extends JPanel{

    private final JLabel titleLabel;
    private final JTextField bodyField;

    public  DiyJTextField(String title){

        setLayout(new GridBagLayout());

        titleLabel = new JLabel(title);
        titleLabel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1,1,1,1, Helper.gray),BorderFactory.createEmptyBorder(0,2,1,2)));
        bodyField = new JTextField();
        bodyField.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1,0,1,1, Helper.gray),BorderFactory.createEmptyBorder(0,2,1,2)));

        add(titleLabel,  new GridBagConstraints(
                0,0,
                1,1,
                0,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        add(bodyField,  new GridBagConstraints(
                1,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));
    }

    public void setText(String text){
        bodyField.setText(text);
    }

    public String getText(){
        return bodyField.getText();
    }

    public JLabel getTitleLabel(){
        return titleLabel;
    }

    public JTextField getBodyField(){
        return bodyField;
    }

    public void setName(String name){
        super.setName(name.replace("Field","Panel"));
        titleLabel.setName(name.replace("Field","Label"));
        bodyField.setName(name);
    }

}
