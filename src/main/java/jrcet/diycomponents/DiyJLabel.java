package jrcet.diycomponents;

import burp.lib.Helper;
import jrcet.Main;
import jrcet.diycomponents.DiyJTextArea.ui.rsyntaxtextarea.RSyntaxTextArea;
import jrcet.frame.tools.Dencrypt.Aes.Aes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
        JList<String> ModeList = (JList) Helper.getComponent(Main.JrcetPanel,"AesMainControlModeList");
        RSyntaxTextArea PlaintextArea = (RSyntaxTextArea) Helper.getComponent(Main.JrcetPanel,"AesMainPlaintextArea");
        RSyntaxTextArea CiphertextArea = (RSyntaxTextArea) Helper.getComponent(Main.JrcetPanel,"AesMainCiphertextArea");
        JTextField KeyField = (JTextField) Helper.getComponent(Main.JrcetPanel,"AesMainControlKeyField");
        JTextField IvField = (JTextField) Helper.getComponent(Main.JrcetPanel,"AesMainControlIvField");
        DiyJComboBox<String> KeyTypeBox = (DiyJComboBox) Helper.getComponent(Main.JrcetPanel,"AesMainControlKeyBox");
        DiyJComboBox<String> IvTypeBox  = (DiyJComboBox) Helper.getComponent(Main.JrcetPanel,"AesMainControlIvBox");
        switch (eLabel.getName()){
            case "AesMainControlEncryptLabel":
                try {
                    CiphertextArea.setText(Aes.Encrypt(PlaintextArea.getText(),ModeList.getSelectedValue(),KeyField.getText(), (String) KeyTypeBox.getSelectedItem(),IvField.getText(), (String) IvTypeBox.getSelectedItem()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
            case "AesMainControlDecryptLabel":
                try {
                    PlaintextArea.setText(Aes.Decrypt(CiphertextArea.getText(),ModeList.getSelectedValue(),KeyField.getText(), (String) KeyTypeBox.getSelectedItem(),IvField.getText(), (String) IvTypeBox.getSelectedItem()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;

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
