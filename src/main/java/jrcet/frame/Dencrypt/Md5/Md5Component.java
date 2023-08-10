package jrcet.frame.Dencrypt.Md5;

import jrcet.diycomponents.*;
import jrcet.help.Helper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;



public class Md5Component extends DiyJComponent {

    public static JComponent Md5ComponentPanel = null;

    public static DiyJTextAreaScrollPane Md5PlainArea = new DiyJTextAreaScrollPane("Md5PlainArea");
    public static DiyJTextAreaScrollPane Md5CipherArea = new DiyJTextAreaScrollPane("Md5CipherArea");

    public JComponent component(){

        Md5ComponentPanel = new JPanel(new GridBagLayout());
        Md5ComponentPanel.setName("Md5ComponentPanel");
        Md5ComponentPanel.setBackground(Color.WHITE);


        Md5ComponentPanel.add(Md5FunctionPanel(),new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));
        Md5ComponentPanel.add(Md5AreaPanel(),new GridBagConstraints(
                0,1,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));


        return Md5ComponentPanel;
    }

    public JComponent Md5FunctionPanel(){
        JPanel Md5FunctionPanel = new JPanel(new GridBagLayout());
        Md5FunctionPanel.setName("Md5FunctionPanel");
        Md5FunctionPanel.setPreferredSize(new Dimension(0,30));

        DiyJButton Md5FunctionDecryptButton = new DiyJButton("Decrypt");
        Md5FunctionDecryptButton.setName("Md5FunctionDecryptButton");
        Md5FunctionPanel.add(Md5FunctionDecryptButton,new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        DiyJButton Md5FunctionEncryptButton = new DiyJButton("Encrypt");
        Md5FunctionEncryptButton.setName("Md5FunctionEncryptButton");
        Md5FunctionPanel.add(Md5FunctionEncryptButton,new GridBagConstraints(
                1,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return Md5FunctionPanel;
    }

    public JComponent Md5AreaPanel(){
        DiyVariablePanel Md5AreaPanel = new DiyVariablePanel(
                Md5PlainArea,"Md5PlainArea",
                Md5CipherArea,"Md5CipherArea",
                DiyVariablePanel.View.HORIZONTAL
        );
        Md5AreaPanel.setName("Md5AreaPanel");
        Md5AreaPanel.setPreferredSize(new Dimension(0,0));
        return Md5AreaPanel;
    }

}
