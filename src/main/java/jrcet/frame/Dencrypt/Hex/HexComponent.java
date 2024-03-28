package jrcet.frame.Dencrypt.Hex;

import diycomponents.*;

import javax.swing.*;
import java.awt.*;


public class HexComponent extends DiyJComponent {

    public static JComponent HexComponentPanel = null;

    public static DiyJTextAreaScrollPane HexPlainArea = new DiyJTextAreaScrollPane("HexPlainArea");

    public static DiyJTextAreaScrollPane HexCipherArea = new DiyJTextAreaScrollPane("HexCipherArea");
    public JComponent component(){

        HexComponentPanel = new JPanel(new GridBagLayout());
        HexComponentPanel.setName("HexComponentPanel");
        HexComponentPanel.setBackground(Color.WHITE);

        HexComponentPanel.add(HexMenuPanel(),new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        HexComponentPanel.add(HexFunctionPanel(),new GridBagConstraints(
                0,1,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        HexComponentPanel.add(HexAreaPanel(),new GridBagConstraints(
                0,2,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));


        return HexComponentPanel;
    }

    public JComponent HexMenuPanel(){
        JPanel HexMenuPanel = new JPanel(new GridBagLayout());
        HexMenuPanel.setName("HexMenuPanel");
        HexMenuPanel.setPreferredSize(new Dimension(0,30));

        DiyJComboBox<String> HexMenuPlainBox = new DiyJComboBox<>(new String[]{"空白","换行","逗号","空格","制表"});
        HexMenuPlainBox.setName("HexMenuPlainBox");
        HexMenuPlainBox.setPreferredSize(new Dimension(0,0));
        HexMenuPanel.add(HexMenuPlainBox,new GridBagConstraints(
                0,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        DiyJComboBox<String> HexMenuCipherBox = new DiyJComboBox<>(new String[]{"空格","空白","换行","逗号","制表"});
        HexMenuCipherBox.setName("HexMenuCipherBox");
        HexMenuCipherBox.setPreferredSize(new Dimension(0,0));
        HexMenuPanel.add(HexMenuCipherBox,new GridBagConstraints(
                1,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return HexMenuPanel;
    }

    public JComponent HexFunctionPanel(){
        JPanel HexFunctionPanel = new JPanel(new GridBagLayout());
        HexFunctionPanel.setName("HexMenuPanel");
        HexFunctionPanel.setPreferredSize(new Dimension(0,30));


        DiyJButton HexFunctionDecrypt = new DiyJButton("Decrypt");
        HexFunctionDecrypt.setName("HexFunctionDecryptButton");
        HexFunctionPanel.add(HexFunctionDecrypt,new GridBagConstraints(
                0,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        DiyJButton HexFunctionEncryptButton = new DiyJButton("Encrypt");
        HexFunctionEncryptButton.setName("HexFunctionEncryptButton");
        HexFunctionPanel.add(HexFunctionEncryptButton,new GridBagConstraints(
                1,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));



        return HexFunctionPanel;
    }

    public JComponent HexAreaPanel(){
        DiyVariablePanel HexAreaPanel = new DiyVariablePanel(
                HexPlainArea,"HexPlainArea",
                HexCipherArea, "HexCipherArea",
                DiyVariablePanel.View.HORIZONTAL
        );
        HexAreaPanel.setName("HexAreaPanel");
        HexAreaPanel.setPreferredSize(new Dimension(0,0));

        return HexAreaPanel;
    }
}
