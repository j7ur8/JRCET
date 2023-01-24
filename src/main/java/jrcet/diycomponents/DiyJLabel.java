package jrcet.diycomponents;

import jrcet.help.Helper;
import jrcet.diycomponents.DiyJTextArea.ui.rsyntaxtextarea.RSyntaxTextArea;
import jrcet.frame.Tools.Dencrypt.Aes.Aes;
import jrcet.frame.Tools.Dencrypt.Rsa.Rsa;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;


public class DiyJLabel extends JLabel implements MouseListener{

    public DiyJLabel(String labelName){
        setOpaque(true);
        setText(labelName);
        setBackground(Color.WHITE);
        setHorizontalAlignment(JLabel.CENTER);
        setFont(new Font("微软雅黑", Font.PLAIN,14));
        setPreferredSize(new Dimension(80,27));
        addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

        DiyJLabel eLabel = (DiyJLabel) e.getSource();
        String eLabelName = eLabel.getName();
        JTextField tField = null;
        JComponent rootPanel = null;
        String tText = eLabel.getText();
        switch (eLabelName){
            case "AesMainControlEncryptLabel":
            case "AesMainControlDecryptLabel":
                Aes((JComponent) eLabel.getParent().getParent().getParent(), eLabel.getText());
                break;
            case "RsaMainControlEncryptLabel":
            case "RsaMainControlDecryptLabel":
                Rsa((JComponent) eLabel.getParent().getParent().getParent(), eLabel.getText());
                break;
            case "AsciiMainControlNewlineLabel":
            case "AsciiMainControlCommaLabel":
            case "AsciiMainControlTabLabel":
            case "AsciiMainControlNullLabel":
            case "AsciiMainControlSpaceLabel":
                rootPanel = (JComponent) eLabel.getParent().getParent();
                tField = (JTextField) Helper.getComponent(rootPanel, "AsciiMainControlCipherSeparatorField");
                if(tField==null){
                    tField = (JTextField) Helper.getComponent(rootPanel, "AsciiMainControlPlainSeparatorField"); assert tField!=null;
                }
                tField.setText(eLabel.getText());
                break;
            case "HexMainControlNewlineLabel":
            case "HexMainControlCommaLabel":
            case "HexMainControlTabLabel":
            case "HexMainControlNullLabel":
            case "HexMainControlSpaceLabel":
                rootPanel = (JComponent) eLabel.getParent().getParent();
                tField = (JTextField) Helper.getComponent(rootPanel, "HexMainControlCipherSeparatorField");
                if(tField==null){
                    tField = (JTextField) Helper.getComponent(rootPanel, "HexMainControlPlainSeparatorField"); assert tField!=null;
                }
                tField.setText(eLabel.getText());
                break;

        }
    }

    private void Aes(JComponent rootComponent, String type){
        JList<String> ModeList = (JList) Helper.getComponent(rootComponent,"AesMainControlModeList");  assert ModeList != null;
        RSyntaxTextArea PlaintextArea = (RSyntaxTextArea) Helper.getComponent(rootComponent,"AesMainPlaintextArea");assert PlaintextArea !=null;
        RSyntaxTextArea CiphertextArea = (RSyntaxTextArea) Helper.getComponent(rootComponent,"AesMainCiphertextArea");assert CiphertextArea != null;
        JTextField KeyField = (JTextField) Helper.getComponent(rootComponent,"AesMainControlKeyField");assert KeyField != null;
        JTextField IvField = (JTextField) Helper.getComponent(rootComponent,"AesMainControlIvField");assert IvField !=null;
        DiyJComboBox<String> KeyTypeBox = (DiyJComboBox) Helper.getComponent(rootComponent,"AesMainControlKeyBox"); assert KeyTypeBox !=null;
        DiyJComboBox<String> IvTypeBox  = (DiyJComboBox) Helper.getComponent(rootComponent,"AesMainControlIvBox");assert IvTypeBox !=null;

        try{
            if(Objects.equals(type, "Encrypt")){
                CiphertextArea.setText(Aes.Encrypt(PlaintextArea.getText(),ModeList.getSelectedValue(),KeyField.getText(), (String) KeyTypeBox.getSelectedItem(),IvField.getText(), (String) IvTypeBox.getSelectedItem()));
            }else{
                PlaintextArea.setText(Aes.Decrypt(CiphertextArea.getText(),ModeList.getSelectedValue(),KeyField.getText(), (String) KeyTypeBox.getSelectedItem(),IvField.getText(), (String) IvTypeBox.getSelectedItem()));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void Rsa(JComponent rootComponent,String type)  {
        RSyntaxTextArea PlaintextArea = (RSyntaxTextArea) Helper.getComponent(rootComponent,"RsaMainPlaintextArea");assert PlaintextArea != null;
        RSyntaxTextArea CiphertextArea = (RSyntaxTextArea) Helper.getComponent(rootComponent,"RsaMainCiphertextArea"); assert CiphertextArea != null;
        RSyntaxTextArea PublicArea = (RSyntaxTextArea) Helper.getComponent(rootComponent,"RsaMainControlPublicArea");assert PublicArea != null;
        RSyntaxTextArea PrivateArea = (RSyntaxTextArea) Helper.getComponent(rootComponent,"RsaMainControlPrivateArea");assert PrivateArea !=null;

        try{
            if(Objects.equals(type,"Encrypt")){
                CiphertextArea.setText(Rsa.Encrypt(PlaintextArea.getText(), Rsa.getPublicKey(PublicArea.getText())));
            }else{
                PlaintextArea.setText(Rsa.Decrypt(CiphertextArea.getText(), Rsa.getPrivateKey(PrivateArea.getText())));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
