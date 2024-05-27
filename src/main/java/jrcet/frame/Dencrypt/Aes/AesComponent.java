package jrcet.frame.Dencrypt.Aes;

import diycomponents.*;
import help.Helper;

import javax.swing.*;
import java.awt.*;


public class AesComponent extends DiyJComponent {

    public static JComponent AesComponentPanel = null;

    public static DiyJTextAreaScrollPane AesPlainArea = Helper.createDiyJTextAreaScrollPane("AesPlainArea");

    public static DiyJTextAreaScrollPane AesCipherArea = Helper.createDiyJTextAreaScrollPane("AesCipherArea");
    public JComponent component(){

        AesComponentPanel = new JPanel(new GridBagLayout());
        AesComponentPanel.setName("AesComponentPanel");
        AesComponentPanel.setBackground(Color.WHITE);

        AesComponentPanel.add(AesMenuPanel(),new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        AesComponentPanel.add(AesFunctionPanel(),new GridBagConstraints(
                0,1,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        AesComponentPanel.add(AesAreaPanel(),new GridBagConstraints(
                0,2,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(5,5,5,5),
                0,0
        ));

        return AesComponentPanel;
    }

    public JComponent AesMenuPanel(){
        JPanel AesMenuPanel = new JPanel(new GridBagLayout());
        AesMenuPanel.setName("AesMenuPanel");
        AesMenuPanel.setPreferredSize(new Dimension(0,30));

        DiyJComboBox<String> AesMenuModeBox = new DiyJComboBox<>(new String[]{
                "AES/ECB/PKCS5Padding",
                "AES/ECB/NoPadding",
                "AES/CBC/PKCS5Padding",
                "AES/CBC/NoPadding",
                "AES/CFB/PKCS5Padding",
                "AES/CFB/NoPadding"
        });
        AesMenuModeBox.setName("AesMenuModeBox");
        AesMenuModeBox.setPreferredSize(new Dimension(0,0));
        AesMenuPanel.add(AesMenuModeBox, new GridBagConstraints(
                AesMenuPanel.getComponentCount(),0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));


        DiyJTextField AesMenuIvField = new DiyJTextField("Iv");
        AesMenuIvField.setName("AesMenuIvField");
        AesMenuIvField.setPreferredSize(new Dimension(0,0));
        AesMenuPanel.add(AesMenuIvField, new GridBagConstraints(
                AesMenuPanel.getComponentCount(),0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        DiyJComboBox<String> AesMenuIvBox = new DiyJComboBox<>(new String[]{"Raw", "Base64", "Hex"});
        AesMenuIvBox.setName("AesMenuIvBox");
        AesMenuIvBox.setPreferredSize(new Dimension(100,0));
        AesMenuPanel.add(AesMenuIvBox, new GridBagConstraints(
                AesMenuPanel.getComponentCount(),0,
                1,1,
                0,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        DiyJTextField AesMenuKeyField = new DiyJTextField("Key");
        AesMenuKeyField.setName("AesMenuKeyField");
        AesMenuKeyField.setPreferredSize(new Dimension(0,0));
        AesMenuPanel.add(AesMenuKeyField, new GridBagConstraints(
                AesMenuPanel.getComponentCount(),0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        DiyJComboBox<String> AesMenuKeyBox = new DiyJComboBox<>(new String[]{"Raw", "Base64", "Hex"});
        AesMenuKeyBox.setName("AesMenuKeyBox");
        AesMenuKeyBox.setPreferredSize(new Dimension(100,0));
        AesMenuPanel.add(AesMenuKeyBox, new GridBagConstraints(
                AesMenuPanel.getComponentCount(),0,
                1,1,
                0,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));


        return AesMenuPanel;
    }

    public JComponent AesFunctionPanel(){
        JPanel AesFunctionPanel = new JPanel(new GridBagLayout());
        AesFunctionPanel.setName("AesFunctionPanel");
        AesFunctionPanel.setPreferredSize(new Dimension(0,30));

        DiyJButton AesFunctionDecryptButton = new DiyJButton("Decrypt");
        AesFunctionDecryptButton.setName("AesFunctionDecryptButton");
        AesFunctionPanel.add(AesFunctionDecryptButton, new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));


        DiyJButton AesFunctionEncryptButton = new DiyJButton("Encrypt");
        AesFunctionEncryptButton.setName("AesFunctionEncryptButton");
        AesFunctionPanel.add(AesFunctionEncryptButton, new GridBagConstraints(
                1,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));


        return AesFunctionPanel;
    }
    public JComponent AesAreaPanel(){
        DiyVariablePanel AesAreaPanel = new DiyVariablePanel(
                AesPlainArea,"AesRawArea",
                AesCipherArea, "AesEncArea",
                DiyVariablePanel.View.HORIZONTAL
        );

        AesAreaPanel.setName("AesAreaPanel");
        AesAreaPanel.setPreferredSize(new Dimension(0,0));

        return AesAreaPanel;

    }



}
