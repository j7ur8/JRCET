package jrcet.frame.Dencrypt.Des;

import jrcet.diycomponents.*;
import jrcet.help.Helper;

import javax.swing.*;
import java.awt.*;


public class DesComponent extends DiyJComponent {

    public static JComponent DesComponentPanel = null;

    public static DiyJTextAreaScrollPane DesPlainArea = Helper.createDiyJTextAreaScrollPane("DesPlainArea");

    public static DiyJTextAreaScrollPane DesCipherArea = Helper.createDiyJTextAreaScrollPane("DesCipherArea");
    public JComponent component(){

        DesComponentPanel = new JPanel(new GridBagLayout());
        DesComponentPanel.setName("DesComponentPanel");
        DesComponentPanel.setBackground(Color.WHITE);

        DesComponentPanel.add(DesMenuPanel(),new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        DesComponentPanel.add(DesFunctionPanel(),new GridBagConstraints(
                0,1,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        DesComponentPanel.add(DesAreaPanel(),new GridBagConstraints(
                0,2,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(5,5,5,5),
                0,0
        ));

        return DesComponentPanel;
    }

    public JComponent DesMenuPanel(){
        JPanel DesMenuPanel = new JPanel(new GridBagLayout());
        DesMenuPanel.setName("DesMenuPanel");
        DesMenuPanel.setPreferredSize(new Dimension(0,30));

        DiyJComboBox<String> DesMenuModeBox = new DiyJComboBox<>(new String[]{
                "DES/ECB/PKCS5Padding",
                "DES/ECB/NoPadding",
                "DES/CBC/PKCS5Padding",
                "DES/CBC/NoPadding"
        });
        DesMenuModeBox.setName("DesMenuModeBox");
        DesMenuModeBox.setPreferredSize(new Dimension(0,0));
        DesMenuPanel.add(DesMenuModeBox, new GridBagConstraints(
                DesMenuPanel.getComponentCount(),0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));


        DiyJTextField DesMenuIvField = new DiyJTextField("Iv");
        DesMenuIvField.setName("DesMenuIvField");
        DesMenuIvField.setPreferredSize(new Dimension(0,0));
        DesMenuPanel.add(DesMenuIvField, new GridBagConstraints(
                DesMenuPanel.getComponentCount(),0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        DiyJComboBox<String> DesMenuIvBox = new DiyJComboBox<>(new String[]{"Raw", "Base64", "Hex"});
        DesMenuIvBox.setName("DesMenuIvBox");
        DesMenuIvBox.setPreferredSize(new Dimension(100,0));
        DesMenuPanel.add(DesMenuIvBox, new GridBagConstraints(
                DesMenuPanel.getComponentCount(),0,
                1,1,
                0,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        DiyJTextField DesMenuKeyField = new DiyJTextField("Key");
        DesMenuKeyField.setName("DesMenuKeyField");
        DesMenuKeyField.setPreferredSize(new Dimension(0,0));
        DesMenuPanel.add(DesMenuKeyField, new GridBagConstraints(
                DesMenuPanel.getComponentCount(),0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        DiyJComboBox<String> DesMenuKeyBox = new DiyJComboBox<>(new String[]{"Raw", "Base64", "Hex"});
        DesMenuKeyBox.setName("DesMenuKeyBox");
        DesMenuKeyBox.setPreferredSize(new Dimension(100,0));
        DesMenuPanel.add(DesMenuKeyBox, new GridBagConstraints(
                DesMenuPanel.getComponentCount(),0,
                1,1,
                0,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));


        return DesMenuPanel;
    }

    public JComponent DesFunctionPanel(){
        JPanel DesFunctionPanel = new JPanel(new GridBagLayout());
        DesFunctionPanel.setName("DesFunctionPanel");
        DesFunctionPanel.setPreferredSize(new Dimension(0,30));

        DiyJButton DesFunctionDecryptButton = new DiyJButton("Decrypt");
        DesFunctionDecryptButton.setName("DesFunctionDecryptButton");
        DesFunctionPanel.add(DesFunctionDecryptButton, new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));


        DiyJButton DesFunctionEncryptButton = new DiyJButton("Encrypt");
        DesFunctionEncryptButton.setName("DesFunctionEncryptButton");
        DesFunctionPanel.add(DesFunctionEncryptButton, new GridBagConstraints(
                1,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));


        return DesFunctionPanel;
    }
    public JComponent DesAreaPanel(){
        DiyVariablePanel DesAreaPanel = new DiyVariablePanel(
                DesPlainArea,"DesRawArea",
                DesCipherArea, "DesEncArea",
                DiyVariablePanel.View.HORIZONTAL
        );

        DesAreaPanel.setName("DesAreaPanel");
        DesAreaPanel.setPreferredSize(new Dimension(0,0));

        return DesAreaPanel;

    }



}
