package jrcet.frame.Dencrypt.Ascii;

import diycomponents.*;

import javax.swing.*;
import java.awt.*;


public class AsciiComponent extends DiyJComponent {

    public static JComponent AsciiComponentPanel = null;

    public static DiyJTextAreaScrollPane AsciiPlainArea = new DiyJTextAreaScrollPane("AsciiPlainArea");

    public static DiyJTextAreaScrollPane AsciiCipherArea = new DiyJTextAreaScrollPane("AsciiCipherArea");
    public JComponent component(){

        AsciiComponentPanel = new JPanel(new GridBagLayout());
        AsciiComponentPanel.setName("AsciiComponentPanel");
        AsciiComponentPanel.setBackground(Color.WHITE);

        AsciiComponentPanel.add(AsciiMenuPanel(),new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        AsciiComponentPanel.add(AsciiFunctionPanel(),new GridBagConstraints(
                0,1,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        AsciiComponentPanel.add(AsciiAreaPanel(),new GridBagConstraints(
                0,2,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));


        return AsciiComponentPanel;
    }

    public JComponent AsciiMenuPanel(){
        JPanel AsciiMenuPanel = new JPanel(new GridBagLayout());
        AsciiMenuPanel.setName("AsciiMenuPanel");
        AsciiMenuPanel.setPreferredSize(new Dimension(0,30));

        DiyJComboBox<String> AsciiMenuPlainBox = new DiyJComboBox<>(new String[]{"空白","换行","逗号","空格","制表"});
        AsciiMenuPlainBox.setName("AsciiMenuPlainBox");
        AsciiMenuPlainBox.setPreferredSize(new Dimension(0,0));
        AsciiMenuPanel.add(AsciiMenuPlainBox,new GridBagConstraints(
                0,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        DiyJComboBox<String> AsciiMenuCipherBox = new DiyJComboBox<>(new String[]{"空格","空白","换行","逗号","制表"});
        AsciiMenuCipherBox.setName("AsciiMenuCipherBox");
        AsciiMenuCipherBox.setPreferredSize(new Dimension(0,0));
        AsciiMenuPanel.add(AsciiMenuCipherBox,new GridBagConstraints(
                1,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return AsciiMenuPanel;
    }

    public JComponent AsciiFunctionPanel(){
        JPanel AsciiFunctionPanel = new JPanel(new GridBagLayout());
        AsciiFunctionPanel.setName("AsciiMenuPanel");
        AsciiFunctionPanel.setPreferredSize(new Dimension(0,30));


        DiyJButton AsciiFunctionDecrypt = new DiyJButton("Decrypt");
        AsciiFunctionDecrypt.setName("AsciiFunctionDecryptButton");
        AsciiFunctionPanel.add(AsciiFunctionDecrypt,new GridBagConstraints(
                0,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        DiyJButton AsciiFunctionEncryptButton = new DiyJButton("Encrypt");
        AsciiFunctionEncryptButton.setName("AsciiFunctionEncryptButton");
        AsciiFunctionPanel.add(AsciiFunctionEncryptButton,new GridBagConstraints(
                1,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));



        return AsciiFunctionPanel;
    }

    public JComponent AsciiAreaPanel(){
        DiyVariablePanel AsciiAreaPanel = new DiyVariablePanel(
                AsciiPlainArea,"AsciiPlainArea",
                AsciiCipherArea, "AsciiCipherArea",
                DiyVariablePanel.View.HORIZONTAL
        );
        AsciiAreaPanel.setName("AsciiAreaPanel");
        AsciiAreaPanel.setPreferredSize(new Dimension(0,0));

        return AsciiAreaPanel;
    }
}
