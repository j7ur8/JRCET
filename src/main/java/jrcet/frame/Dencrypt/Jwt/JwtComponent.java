package jrcet.frame.Dencrypt.Jwt;

import jrcet.diycomponents.*;

import javax.swing.*;
import java.awt.*;


public class JwtComponent extends DiyJComponent {

    public static JComponent JwtComponentPanel = null;

    public static DiyJTextAreaScrollPane JwtPlainHeaderArea = new DiyJTextAreaScrollPane("JwtPlainHeaderArea");

    public static DiyJTextAreaScrollPane JwtPlainPayloadArea = new DiyJTextAreaScrollPane("JwtPlainPayloadArea");

    public static DiyJTextAreaScrollPane JwtPlainSecretArea = new DiyJTextAreaScrollPane("JwtPlainSignatureArea");

    public static DiyJTextAreaScrollPane JwtPlainPublicArea = new DiyJTextAreaScrollPane("JwtPlainPublicArea");

    public static DiyJTextAreaScrollPane JwtPlainPrivateArea = new DiyJTextAreaScrollPane("JwtPlainPrivateArea");


    public static DiyJTextAreaScrollPane JwtCipherArea = new DiyJTextAreaScrollPane("JwtCipherArea");
    public JComponent component(){

        JwtComponentPanel = new JPanel(new GridBagLayout());
        JwtComponentPanel.setName("JwtComponentPanel");
        JwtComponentPanel.setBackground(Color.WHITE);

        JwtComponentPanel.add(JwtMenuPanel(),new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        JwtComponentPanel.add(JwtFunctionPanel(),new GridBagConstraints(
                0,1,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        JwtComponentPanel.add(JwtAreaPanel(),new GridBagConstraints(
                0,2,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return JwtComponentPanel;
    }

    public JComponent JwtMenuPanel(){
        JPanel JwtMenuPanel = new JPanel(new GridBagLayout());
        JwtMenuPanel.setName("JwtMenuPanel");
        JwtMenuPanel.setPreferredSize(new Dimension(0,30));

        DiyJComboBox<String> JwtMenuTypeBox = new DiyJComboBox<>(new String[]{"HS256","HS384","HS512","RS256","RS384","RS512","ES256","ES384","ES512"});
        JwtMenuTypeBox.setName("JwtMenuTypeBox");
        JwtMenuTypeBox.setPreferredSize(new Dimension(0,0));
        JwtMenuPanel.add(JwtMenuTypeBox,new GridBagConstraints(
                0,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));


        DiyJComboBox<String> JwtMenuSecretBox = new DiyJComboBox<>(new String[]{"Raw Of Secret","Base64 Of Secret"});
        JwtMenuSecretBox.setName("JwtMenuSecretBox");
        JwtMenuSecretBox.setPreferredSize(new Dimension(0,0));
        JwtMenuPanel.add(JwtMenuSecretBox,new GridBagConstraints(
                1,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        JLabel JwtMenuVerifyLabel = new JLabel("invalid signature");
        JwtMenuVerifyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JwtMenuVerifyLabel.setName("JwtMenuVerifyLabel");
        JwtMenuVerifyLabel.setPreferredSize(new Dimension(0,0));
        JwtMenuVerifyLabel.setForeground(Color.RED);
        JwtMenuPanel.add(JwtMenuVerifyLabel,new GridBagConstraints(
                2,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));


        return JwtMenuPanel;
    }

    public JComponent JwtFunctionPanel(){
        JPanel JwtFunctionPanel = new JPanel(new GridBagLayout());
        JwtFunctionPanel.setName("JwtMenuPanel");
        JwtFunctionPanel.setPreferredSize(new Dimension(0,30));

        DiyJButton JwtFunctionEncryptButton = new DiyJButton("Encrypt");
        JwtFunctionEncryptButton.setName("JwtFunctionEncryptButton");
        JwtFunctionPanel.add(JwtFunctionEncryptButton,new GridBagConstraints(
                0,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        DiyJButton JwtFunctionVerifyButton = new DiyJButton("Verify");
        JwtFunctionVerifyButton.setName("JwtFunctionVerifyButton");
        JwtFunctionPanel.add(JwtFunctionVerifyButton,new GridBagConstraints(
                1,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));


        return JwtFunctionPanel;
    }

    public JComponent JwtAreaPanel(){
        JwtCipherArea.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1,0,0,0,Color.gray),"JWT Token"));
        DiyVariablePanel JwtAreaPanel = new DiyVariablePanel(
                JwtCipherArea,"JwtPlainArea",
                JwtPlainAreaPanel(), "JwtCipherArea",
                DiyVariablePanel.View.HORIZONTAL
        );
        JwtAreaPanel.setName("JwtAreaPanel");
        JwtAreaPanel.setPreferredSize(new Dimension(0,0));

        return JwtAreaPanel;
    }

    public JComponent JwtPlainAreaPanel(){
        JPanel JwtPlainAreaPanel = new JPanel(new GridBagLayout());
        JwtPlainAreaPanel.setName("JwtPlainAreaPanel");

        JwtPlainHeaderArea.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1,0,0,0,Color.gray),"Header"));
        JwtPlainAreaPanel.add(JwtPlainHeaderArea,new GridBagConstraints(
                0,0,
                2,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        JwtPlainPayloadArea.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1,0,0,0,Color.gray),"Payload"));
        JwtPlainAreaPanel.add(JwtPlainPayloadArea,new GridBagConstraints(
                0,1,
                2,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        JwtPlainSecretArea.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1,0,0,0,Color.gray),"SignatureArea"));
        JwtPlainAreaPanel.add(JwtPlainSecretArea,new GridBagConstraints(
                0,2,
                2,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        JwtPlainPublicArea.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1,0,0,0,Color.gray),"PublicArea"));
        JwtPlainAreaPanel.add(JwtPlainPublicArea,new GridBagConstraints(
                0,3,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        JwtPlainPrivateArea.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1,0,0,0,Color.gray),"PrivateArea"));
        JwtPlainAreaPanel.add(JwtPlainPrivateArea,new GridBagConstraints(
                1,3,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));


        return JwtPlainAreaPanel;

    }



}
