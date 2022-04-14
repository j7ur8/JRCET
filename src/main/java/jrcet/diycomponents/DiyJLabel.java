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
                Aes(eLabel.getText());
                break;
            case "RsaMainControlEncryptLabel":
            case "RsaMainControlDecryptLabel":
                Rsa(eLabel.getText());
                break;
        }
    }

    private void Aes(String type){
        JList<String> ModeList = (JList) Helper.getComponent(Main.JrcetPanel,"AesMainControlModeList");
        RSyntaxTextArea PlaintextArea = (RSyntaxTextArea) Helper.getComponent(Main.JrcetPanel,"AesMainPlaintextArea");
        RSyntaxTextArea CiphertextArea = (RSyntaxTextArea) Helper.getComponent(Main.JrcetPanel,"AesMainCiphertextArea");
        JTextField KeyField = (JTextField) Helper.getComponent(Main.JrcetPanel,"AesMainControlKeyField");
        JTextField IvField = (JTextField) Helper.getComponent(Main.JrcetPanel,"AesMainControlIvField");
        DiyJComboBox<String> KeyTypeBox = (DiyJComboBox) Helper.getComponent(Main.JrcetPanel,"AesMainControlKeyBox");
        DiyJComboBox<String> IvTypeBox  = (DiyJComboBox) Helper.getComponent(Main.JrcetPanel,"AesMainControlIvBox");

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

    private void Rsa(String type)  {
        RSyntaxTextArea PlaintextArea = (RSyntaxTextArea) Helper.getComponent(Main.JrcetPanel,"RsaMainPlaintextArea");
        RSyntaxTextArea CiphertextArea = (RSyntaxTextArea) Helper.getComponent(Main.JrcetPanel,"RsaMainCiphertextArea");
        RSyntaxTextArea PublicArea = (RSyntaxTextArea) Helper.getComponent(Main.JrcetPanel,"RsaMainControlPublicArea");
        RSyntaxTextArea PrivateArea = (RSyntaxTextArea) Helper.getComponent(Main.JrcetPanel,"RsaMainControlPrivateArea");

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
