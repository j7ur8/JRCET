package jrcet.frame.Dencrypt.Rsa;

import jrcet.diycomponents.*;
import jrcet.help.Helper;
import jrcet.frame.Setting.Setting;

import javax.swing.*;

import java.awt.*;
import java.security.KeyPair;
import java.util.HashMap;

import static jrcet.frame.Dencrypt.Base.Base.b64encoder;


public class RsaComponent extends DiyJComponent {

    public static DiyJTextAreaScrollPane RsaPublicArea = new DiyJTextAreaScrollPane("RsaPublicArea");

    public static DiyJTextAreaScrollPane RsaPrivateArea = new DiyJTextAreaScrollPane("RsaPrivateArea");

    public static DiyJTextAreaScrollPane RsaPlainArea = new DiyJTextAreaScrollPane("RsaPlainArea");

    public static DiyJTextAreaScrollPane RsaCipherArea = new DiyJTextAreaScrollPane("RsaCipherArea");
    public static JComponent RsaComponentPanel = null;


    public RsaComponent(){
        try{
            KeyPair keyPair = Rsa.getKeyPair();
            RsaPublicArea.setText(new String(b64encoder.encode(keyPair.getPublic().getEncoded())));
            RsaPrivateArea.setText(new String(b64encoder.encode(keyPair.getPrivate().getEncoded())));
        }catch (Exception ignored){

        }


    }
    public JComponent component(){

        RsaComponentPanel = new JPanel(new GridBagLayout());
        RsaComponentPanel.setName("RsaComponentPanel");
        RsaComponentPanel.setBackground(Color.WHITE);
        RsaComponentPanel.setPreferredSize(new Dimension(0,0));

        RsaComponentPanel.add(RsaKeyPanel(), new GridBagConstraints(
                0,0,
                1,1,
                1,0.3,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        RsaComponentPanel.add(RsaFunctionPanel(), new GridBagConstraints(
                0,1,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        RsaComponentPanel.add(RsaAreaPanel(),new GridBagConstraints(
                0,2,
                1,1,
                1,0.7,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));


        return RsaComponentPanel;
    }

    public JComponent RsaKeyPanel(){
        DiyVariablePanel RsaKeyPanel = new DiyVariablePanel(
                RsaPublicArea, "RsaPublicArea",
                RsaPrivateArea, "RsaPrivateArea",
                DiyVariablePanel.View.HORIZONTAL
        );
        RsaKeyPanel.setName("RsaKeyPanel");
        RsaKeyPanel.setPreferredSize(new Dimension(0,0));

        return RsaKeyPanel;
    }

    public JComponent RsaFunctionPanel(){
        JPanel RsaFunctionPanel = new JPanel(new GridBagLayout());
        RsaFunctionPanel.setName("RsaFunctionPanel");
        RsaFunctionPanel.setPreferredSize(new Dimension(0,30));

        DiyJButton RsaFunctionDecryptButton = new DiyJButton("Decrypt");
        RsaFunctionDecryptButton.setName("RsaFunctionDecryptButton");
        RsaFunctionPanel.add(RsaFunctionDecryptButton, new GridBagConstraints(
                0,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        DiyJButton RsaFunctionEncryptButton = new DiyJButton("Encrypt");
        RsaFunctionEncryptButton.setName("RsaFunctionEncryptButton");
        RsaFunctionPanel.add(RsaFunctionEncryptButton, new GridBagConstraints(
                1,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return RsaFunctionPanel;
    }

    public JComponent RsaAreaPanel(){
        DiyVariablePanel RsaAreaPanel = new DiyVariablePanel(
                RsaPlainArea,"RsaPlainArea",
                RsaCipherArea, "RsaCipherArea",
                DiyVariablePanel.View.HORIZONTAL
        );

        RsaAreaPanel.setName("RsaAreaPanel");
        RsaAreaPanel.setPreferredSize(new Dimension(0,0));

        return RsaAreaPanel;
    }

}
