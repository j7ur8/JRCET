package jrcet.frame.Dencrypt.Base;

import jrcet.diycomponents.*;
import jrcet.help.Helper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;



public class BaseComponent extends DiyJComponent {

    public static JComponent BaseComponentPanel = null;

    public static DiyJTextAreaScrollPane BasePlainArea = new DiyJTextAreaScrollPane("BasePlainArea");

    public static DiyJTextAreaScrollPane BaseCipherArea = new DiyJTextAreaScrollPane("BaseCipherArea");
    public JComponent component(){

        BaseComponentPanel = new JPanel(new GridBagLayout());
        BaseComponentPanel.setName("BaseComponentPanel");
        BaseComponentPanel.setBackground(Color.WHITE);

        BaseComponentPanel.add(BaseMenuPanel(),new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        BaseComponentPanel.add(BaseFunctionPanel(),new GridBagConstraints(
                0,1,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        BaseComponentPanel.add(BaseAreaPanel(),new GridBagConstraints(
                0,2,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));


        return BaseComponentPanel;
    }

    public JComponent BaseMenuPanel(){
        JPanel BaseMenuPanel = new JPanel(new GridBagLayout());
        BaseMenuPanel.setName("BaseMenuPanel");
        BaseMenuPanel.setPreferredSize(new Dimension(0,30));

        DiyJComboBox<String> BaseMenuTypeBox = new DiyJComboBox<>(new String[]{"Base16","Base32","Base36","Base58","Base62","Base64","Base85","Base91","Base92","Base128"});
        BaseMenuTypeBox.setName("BaseMenuTypeBox");
        BaseMenuTypeBox.setPreferredSize(new Dimension(0,0));
        BaseMenuPanel.add(BaseMenuTypeBox,new GridBagConstraints(
                0,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return BaseMenuPanel;
    }

    public JComponent BaseFunctionPanel(){
        JPanel BaseFunctionPanel = new JPanel(new GridBagLayout());
        BaseFunctionPanel.setName("BaseMenuPanel");
        BaseFunctionPanel.setPreferredSize(new Dimension(0,30));


        DiyJButton BaseFunctionDecrypt = new DiyJButton("Decrypt");
        BaseFunctionDecrypt.setName("BaseFunctionDecryptButton");
        BaseFunctionPanel.add(BaseFunctionDecrypt,new GridBagConstraints(
                0,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        DiyJButton BaseFunctionEncryptButton = new DiyJButton("Encrypt");
        BaseFunctionEncryptButton.setName("BaseFunctionEncryptButton");
        BaseFunctionPanel.add(BaseFunctionEncryptButton,new GridBagConstraints(
                1,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));



        return BaseFunctionPanel;
    }

    public JComponent BaseAreaPanel(){
        DiyVariablePanel BaseAreaPanel = new DiyVariablePanel(
                BasePlainArea,"BasePlainArea",
                BaseCipherArea, "BaseCipherArea",
                DiyVariablePanel.View.HORIZONTAL
        );
        BaseAreaPanel.setName("BaseAreaPanel");
        BaseAreaPanel.setPreferredSize(new Dimension(0,0));

        return BaseAreaPanel;
    }
}
