package jrcet.frame.Intruder;


import jrcet.frame.Tools.Captcha.Captcha;
import jrcet.frame.Dencrypt.Aes.Aes;
import jrcet.frame.Dencrypt.Ascii.Ascii;
import jrcet.frame.Dencrypt.Base.Base;
import jrcet.frame.Dencrypt.Md5.Md5;
import jrcet.frame.Dencrypt.Rsa.Rsa;
import jrcet.frame.Dencrypt.Unicode.Unicode;
import jrcet.help.Helper;


import javax.swing.*;

import java.util.ArrayList;
import java.util.Objects;

import static jrcet.frame.Intruder.IntruderComponent.IntruderComponentPanel;


public class Intruder {

    public static ArrayList<JComponent> IntruderModuleComponentList = new ArrayList<>();
    private static String tmpPayload;

    private static JComponent tmpRootPanel = IntruderComponentPanel;


    public static void aseProcessor() {
        JTextField ivField = (JTextField) Helper.getComponent(tmpRootPanel, "IntruderMainAesIvField"); assert ivField!=null;
        JComboBox<String> ivBox = (JComboBox<String>) Helper.getComponent(tmpRootPanel, "IntruderMainAesIvBox"); assert ivBox!=null;
        JTextField keyField = (JTextField) Helper.getComponent(tmpRootPanel, "IntruderMainAesKeyField");assert keyField!=null;
        JComboBox<String> keyBox = (JComboBox<String>) Helper.getComponent(tmpRootPanel, "IntruderMainAesKeyBox");assert keyBox!=null;
        JComboBox<String> modeBox = (JComboBox<String>) Helper.getComponent(tmpRootPanel, "IntruderMainAesModeBox");assert modeBox!=null;
        JComboBox<String> typeBox = (JComboBox<String>) Helper.getComponent(tmpRootPanel, "IntruderMainAesTypeBox");assert typeBox!=null;

        String aesIv = ivField.getText();
        String aesIvType = (String) ivBox.getSelectedItem();
        String aesKey = keyField.getText();
        String aesKeyType = (String) keyBox.getSelectedItem();
        String aesMode = (String) modeBox.getSelectedItem();
        String aesType = (String) typeBox.getSelectedItem();

        try {
            switch (Objects.requireNonNull(aesType)){
                case "Decrypt":
                    tmpPayload = Aes.Decrypt(tmpPayload, aesMode, aesKey, aesKeyType, aesIv, aesIvType);
                    break;
                case "Encrypt":
                    tmpPayload = Aes.Encrypt(tmpPayload, aesMode, aesKey, aesKeyType, aesIv, aesIvType);
                    break;
            }
        }catch (Exception ignore){

        }

    }

    public static void desProcessor(){
        try{
//            szm tools = new szm(Base64.getDecoder().decode("Z12/y2Lfzlc="));
//            tmpPayload = tools.encode(tmpPayload);
//            return tmpPayload;
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void md5Processor(){

        tmpPayload = Md5.stringToMd5(tmpPayload);

    }

    public static void rsaProcessor(){
        JTextArea publicArea = (JTextArea) Helper.getComponent(tmpRootPanel, "IntruderMainRsaPublicArea"); assert publicArea!=null;
        JTextArea privateArea = (JTextArea) Helper.getComponent(tmpRootPanel, "IntruderMainRsaPrivateArea"); assert privateArea!=null;
        JComboBox<String> modeBox = (JComboBox<String>) Helper.getComponent(tmpRootPanel, "IntruderMainRsaTypeBox"); assert modeBox!=null;

        String rsaPublicKey = publicArea.getText();
        String rsaPrivateKey = privateArea.getText();
        String rsaMode = (String) modeBox.getSelectedItem();

        try{
            switch (Objects.requireNonNull(rsaMode)){
                case "Decrypt":
                    tmpPayload = Rsa.Decrypt(tmpPayload, Rsa.getPrivateKey(rsaPrivateKey));
                    break;
                case "Encrypt":
                    tmpPayload = Rsa.Encrypt(tmpPayload, Rsa.getPublicKey(rsaPublicKey));
                    break;
            }
        }catch (Exception ignore){}
    }

    public static void baseProcessor(){
        JComboBox<String> modeBox = (JComboBox<String>) Helper.getComponent(tmpRootPanel, "IntruderMainBaseModeBox"); assert modeBox!=null;
        JComboBox<String> typeBox = (JComboBox<String>) Helper.getComponent(tmpRootPanel, "IntruderMainBaseTypeBox"); assert typeBox!=null;

        String baseMode = (String) modeBox.getSelectedItem(); assert baseMode != null;
        String baseType = (String) typeBox.getSelectedItem(); assert baseType != null;

        try{
            switch (Objects.requireNonNull(baseType)){
                case "Decrypt":
                    tmpPayload = Base.decrypt(tmpPayload, baseMode);
                    break;
                case "Encrypt":
                    tmpPayload = Base.encrypt(tmpPayload, baseMode);
                    break;
            }
        }catch (Exception ignore){}
    }

    public static void asciiProcessor(){
        JTextField separator1Field = (JTextField) Helper.getComponent(tmpRootPanel, "IntruderMainAsciiSeparator1Field"); assert separator1Field!=null;
        JTextField separator2Field = (JTextField) Helper.getComponent(tmpRootPanel, "IntruderMainAsciiSeparator2Field"); assert separator2Field!=null;
        JComboBox<String> typeBox = (JComboBox<String>) Helper.getComponent(tmpRootPanel, "IntruderMainAsciiTypeBox"); assert typeBox!=null;

        String inputSeparator = separator1Field.getText();
        String outputSeparator = separator2Field.getText();
        String asciiMode = (String) typeBox.getSelectedItem();

        switch (Objects.requireNonNull(asciiMode)){
            case "Decrypt":
                tmpPayload = Ascii.decrypt(tmpPayload, inputSeparator, outputSeparator);
                break;
            case "Encrypt":
                tmpPayload = Ascii.encrypt(tmpPayload, inputSeparator, outputSeparator);
                break;
        }

    }

    public static void unicodeProcessor(){
        JComboBox<String> modeBox = (JComboBox<String>) Helper.getComponent(tmpRootPanel, "IntruderMainUnicodeTypeBox"); assert modeBox!=null;

        String unicodeMode = (String) modeBox.getSelectedItem();

        switch (Objects.requireNonNull(unicodeMode)){
            case "Decrypt":
                tmpPayload = Unicode.unicodeToString(tmpPayload);
                break;
            case "Encrypt":
                tmpPayload = Unicode.stringToUnicode(tmpPayload);
                break;
        }
    }

    public static String invokeCaptcha(byte[] currentPayload) {
        tmpPayload= Captcha.identifyCaptcha();
        return tmpPayload;
    }

    public static String invokeDiy(byte[] currentPayload)  {
//        String name= new String(currentPayload);
//        String name = Des.encrypt("lianyitech", new String(currentPayload));
        String captcha = Captcha.identifyCaptcha();

        return captcha;
    }
}
