package jrcet.frame.Dencrypt.Url;

import jrcet.diycomponents.*;
import jrcet.help.Helper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;



public class UrlComponent extends DiyJComponent {
    public static JComponent UrlComponentPanel = null;

    public static DiyJTextAreaScrollPane UrlPlainArea = new DiyJTextAreaScrollPane("UrlPlainArea");

    public static DiyJTextAreaScrollPane UrlCipherArea = new DiyJTextAreaScrollPane("UrlCipherArea");

    public JComponent component(){

        UrlComponentPanel = new JPanel(new GridBagLayout());
        UrlComponentPanel.setName("UrlComponentPanel");
        UrlComponentPanel.setBackground(Color.WHITE);


        UrlComponentPanel.add(UrlFunctionPanel(),new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        UrlComponentPanel.add(UrlAreaPanel(),new GridBagConstraints(
                0,1,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return UrlComponentPanel;
    }

    public JComponent UrlFunctionPanel(){
        JPanel UrlFunctionPanel = new JPanel(new GridBagLayout());
        UrlFunctionPanel.setName("UrlFunctionPanel");

        DiyJButton UrlFunctionDecryptButton = new DiyJButton("Decrypt");
        UrlFunctionDecryptButton.setName("UrlFunctionDecryptButton");
        UrlFunctionPanel.add(UrlFunctionDecryptButton,new GridBagConstraints(
                0,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        DiyJButton UrlFunctionEncryptButton = new DiyJButton("Encrypt");
        UrlFunctionEncryptButton.setName("UrlFunctionEncryptButton");
        UrlFunctionPanel.add(UrlFunctionEncryptButton,new GridBagConstraints(
                1,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return UrlFunctionPanel;

    }

    public JComponent UrlAreaPanel(){
        DiyVariablePanel UrlAreaPanel = new DiyVariablePanel(
                UrlPlainArea,"UrlPlainArea",
                UrlCipherArea, "UrlCipherArea",
                DiyVariablePanel.View.HORIZONTAL
        );

        UrlAreaPanel.setName("UrlAreaPanel");
        UrlAreaPanel.setPreferredSize(new Dimension(0,0));

        return UrlAreaPanel;
    }

}
