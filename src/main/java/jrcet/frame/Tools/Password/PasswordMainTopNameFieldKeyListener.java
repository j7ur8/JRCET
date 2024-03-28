package jrcet.frame.Tools.Password;

import help.Helper;

import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PasswordMainTopNameFieldKeyListener implements KeyListener {
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if( (e.getModifiersEx() == InputEvent.CTRL_MASK || e.getModifiers() == InputEvent.META_MASK) && e.getKeyCode()==71){
            JTextField sField = (JTextField) e.getSource();
            String v = sField.getText();
            JComponent rootComponent = (JComponent) sField.getParent().getParent();
            JTextArea tTextArea = (JTextArea) Helper.getComponent(rootComponent,"PasswordCentralDictArea");assert tTextArea != null;

            JTextField PasswordRightRulesLengthField = (JTextField) Helper.getComponent(rootComponent, "PasswordRightRulesLengthField");assert PasswordRightRulesLengthField != null;
            JTextField PasswordRightAccountnameField = (JTextField) Helper.getComponent(rootComponent,"PasswordRightAccountnameField"); assert PasswordRightAccountnameField != null;
            JTextField PasswordRightBirthdayField = (JTextField) Helper.getComponent(rootComponent, "PasswordRightBirthdayField");assert PasswordRightBirthdayField != null;
            JTextField PasswordRightPhoneField = (JTextField) Helper.getComponent(rootComponent, "PasswordRightPhoneField");assert PasswordRightPhoneField != null;
            JTextField PasswordRightIdcardField = (JTextField) Helper.getComponent(rootComponent, "PasswordRightIdcardField");assert PasswordRightIdcardField != null;
            JTextField PasswordRightSaveoneField = (JTextField) Helper.getComponent(rootComponent, "PasswordRightSaveoneField");
            assert PasswordRightSaveoneField!=null;
            JTextField PasswordRightSaveallField = (JTextField) Helper.getComponent(rootComponent, "PasswordRightSaveallField");
            assert PasswordRightSaveallField!=null;

            JCheckBox PasswordRightRulesComplexity1Box = (JCheckBox) Helper.getComponent(rootComponent, "PasswordRightRulesComplexity1Box");assert PasswordRightRulesComplexity1Box != null;
            JCheckBox PasswordRightRulesComplexity2Box = (JCheckBox) Helper.getComponent(rootComponent, "PasswordRightRulesComplexity2Box");assert PasswordRightRulesComplexity2Box != null;
            JCheckBox PasswordRightRulesComplexity3Box = (JCheckBox) Helper.getComponent(rootComponent, "PasswordRightRulesComplexity3Box");assert PasswordRightRulesComplexity3Box != null;
            JCheckBox PasswordRightRulesAccount1Box = (JCheckBox) Helper.getComponent(rootComponent, "PasswordRightRulesAccount1Box");assert PasswordRightRulesAccount1Box != null;
            JCheckBox PasswordRightRulesAccount2Box = (JCheckBox) Helper.getComponent(rootComponent, "PasswordRightRulesAccount2Box");assert PasswordRightRulesAccount2Box != null;
            JCheckBox PasswordRightRulesSequence1Box = (JCheckBox) Helper.getComponent(rootComponent, "PasswordRightRulesSequence1Box");assert PasswordRightRulesSequence1Box != null;
            JCheckBox PasswordRightRulesSequence2Box = (JCheckBox) Helper.getComponent(rootComponent, "PasswordRightRulesSequence2Box");assert PasswordRightRulesSequence2Box != null;
            JCheckBox PasswordRightSaveifLabel = (JCheckBox) Helper.getComponent(rootComponent,"PasswordRightSaveifLabel");
            assert PasswordRightSaveifLabel != null;
            boolean ifCapitalLetter,ifDigital,ifSpecialChar,ifAccount1,ifAccount2,ifSequence1,ifSequence2,ifSave;
            String passwordLength,birthday,phone,idcard,oneSavePath,allSavePath,accountName;

            ifCapitalLetter = PasswordRightRulesComplexity1Box.isSelected();
            ifDigital = PasswordRightRulesComplexity2Box.isSelected();
            ifSpecialChar = PasswordRightRulesComplexity3Box.isSelected();
            ifAccount1 = PasswordRightRulesAccount1Box.isSelected();
            ifAccount2 = PasswordRightRulesAccount2Box.isSelected();
            ifSequence1 = PasswordRightRulesSequence1Box.isSelected();
            ifSequence2 = PasswordRightRulesSequence2Box.isSelected();
            ifSave = PasswordRightSaveifLabel.isSelected();
            passwordLength = PasswordRightRulesLengthField.getText();
            accountName = PasswordRightAccountnameField.getText();
            birthday = PasswordRightBirthdayField.getText();
            phone = PasswordRightPhoneField.getText();
            idcard = PasswordRightIdcardField.getText();
            oneSavePath = PasswordRightSaveoneField.getText();
            allSavePath = PasswordRightSaveallField.getText();
//            System.out.println("包含大写字母: "+ifCapitalLetter+
//                             "\n包含数字:"+ifDigital+
//                             "\n包含特殊符号:"+ifSpecialChar+
//                             "\n是否包含账号:"+ifAccount1+
//                             "\n是否包含账号:"+ifAccount2+
//                             "\n是否规律1:"+ifSequence1+
//                             "\n是否规律2:"+ifSequence2+
//                             "\n密码长度:"+passwordLength+
//                             "\n账号名:"+accountName+
//                             "\n出生日:"+birthday+
//                             "\n手机号:"+phone+
//                             "\n身份证:"+idcard+
//                             "\n地址1:"+oneSavePath+
//                             "\n地址2:"+allSavePath+
//                             "\n自动保存:"+ifSave);

            Password.setRule(ifCapitalLetter,ifDigital,ifSpecialChar,ifAccount1,ifAccount2,ifSequence1,ifSequence2,ifSave,passwordLength,accountName,birthday,phone,idcard,oneSavePath,allSavePath);

            Password.writeArea(v,tTextArea);


        }
    }
}
