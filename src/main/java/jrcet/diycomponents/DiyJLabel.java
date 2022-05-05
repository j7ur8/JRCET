package jrcet.diycomponents;

import burp.lib.Helper;
import jrcet.Main;
import jrcet.diycomponents.DiyJTextArea.ui.rsyntaxtextarea.RSyntaxTextArea;
import jrcet.frame.tools.Dencrypt.Aes.Aes;
import jrcet.frame.tools.Dencrypt.Rsa.Rsa;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static jrcet.frame.tools.Dencrypt.Base.Base.b64encoder;

public class DiyJLabel extends JLabel implements MouseListener{

    public DiyJLabel(String labelName){
        setLabelProperty(labelName);
        addMouseListener(this);
    }

    private void setLabelProperty(String labelName){
        setOpaque(true);
        setText(labelName);
        setBackground(Color.WHITE);
        setHorizontalAlignment(JLabel.CENTER);
        setFont(new Font("微软雅黑", Font.PLAIN,14));
        setPreferredSize(new Dimension(80,27));
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        DiyJLabel eLabel = (DiyJLabel) e.getSource();

        switch (eLabel.getName()){
            case "AesMainControlEncryptLabel":
            case "AesMainControlDecryptLabel":
                Aes((JComponent) eLabel.getParent().getParent().getParent(), eLabel.getText());
                break;
            case "RsaMainControlEncryptLabel":
            case "RsaMainControlDecryptLabel":
                Rsa((JComponent) eLabel.getParent().getParent().getParent(), eLabel.getText());
                break;
        }
    }

    private void Aes(JComponent rootComponent, String type){
        JList<String> ModeList = (JList) Helper.getComponent(rootComponent,"AesMainControlModeList");
        RSyntaxTextArea PlaintextArea = (RSyntaxTextArea) Helper.getComponent(rootComponent,"AesMainPlaintextArea");
        RSyntaxTextArea CiphertextArea = (RSyntaxTextArea) Helper.getComponent(rootComponent,"AesMainCiphertextArea");
        JTextField KeyField = (JTextField) Helper.getComponent(rootComponent,"AesMainControlKeyField");
        JTextField IvField = (JTextField) Helper.getComponent(rootComponent,"AesMainControlIvField");
        DiyJComboBox<String> KeyTypeBox = (DiyJComboBox) Helper.getComponent(rootComponent,"AesMainControlKeyBox");
        DiyJComboBox<String> IvTypeBox  = (DiyJComboBox) Helper.getComponent(rootComponent,"AesMainControlIvBox");

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
        RSyntaxTextArea PlaintextArea = (RSyntaxTextArea) Helper.getComponent(rootComponent,"AesMainControlIvBox");
        RSyntaxTextArea CiphertextArea = (RSyntaxTextArea) Helper.getComponent(rootComponent,"RsaMainCiphertextArea");
        RSyntaxTextArea PublicArea = (RSyntaxTextArea) Helper.getComponent(rootComponent,"RsaMainControlPublicArea");
        RSyntaxTextArea PrivateArea = (RSyntaxTextArea) Helper.getComponent(rootComponent,"RsaMainControlPrivateArea");
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
