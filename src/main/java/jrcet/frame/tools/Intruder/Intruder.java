package jrcet.frame.tools.Intruder;

import jrcet.diycomponents.DiyJTextArea.ui.rsyntaxtextarea.RSyntaxTextArea;
import jrcet.frame.tools.Dencrypt.Aes.Aes;
import jrcet.frame.tools.Dencrypt.Ascii.Ascii;
import jrcet.frame.tools.Dencrypt.Base.Base;
import jrcet.frame.tools.Dencrypt.Md5.Md5;
import jrcet.frame.tools.Dencrypt.Rsa.Rsa;
import jrcet.frame.tools.Dencrypt.Unicode.Unicode;
import jrcet.help.Helper;

import javax.swing.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Objects;

import static jrcet.frame.tools.Intruder.IntruderComponent.IntruderModuleComponentList;

public class Intruder {

    private static String tmpPayload;

    private static JComponent RootPanel;

    private static ArrayList<String> processors = new ArrayList<>();

    public static String involeIntruder(byte[] currentPayload) {

        tmpPayload = new String(currentPayload, StandardCharsets.UTF_8);

        for(JComponent jComponent : IntruderModuleComponentList){
            RootPanel = jComponent;
            switch (jComponent.getName()){
                case "IntruderMainAesPanel":
                    aseProcessor();
                    break;
                case "IntruderMainDesPanel":
                    desProcessor();
                    break;
                case "IntruderMainMd5Panel":
                    md5Processor();
                    break;
                case "IntruderMainRsaPanel":
                    rsaProcessor();
                    break;
                case "IntruderMainBasePanel":
                    baseProcessor();
                    break;
                case "IntruderMainAsciiPanel":
                    asciiProcessor();
                    break;
                case "IntruderMainUnicodePanel":
                    unicodeProcessor();
                    break;

            }
        }

        String returnedPayload = tmpPayload;
        return returnedPayload;
    }


    public static void aseProcessor() {
        JTextField ivField = (JTextField) Helper.getComponent(RootPanel, "IntruderMainAesIvField"); assert ivField!=null;
        JComboBox<String> ivBox = (JComboBox<String>) Helper.getComponent(RootPanel, "IntruderMainAesIvBox"); assert ivBox!=null;
        JTextField keyField = (JTextField) Helper.getComponent(RootPanel, "IntruderMainAesKeyField");assert keyField!=null;
        JComboBox<String> keyBox = (JComboBox<String>) Helper.getComponent(RootPanel, "IntruderMainAesKeyBox");assert keyBox!=null;
        JComboBox<String> modeBox = (JComboBox<String>) Helper.getComponent(RootPanel, "IntruderMainAesModeBox");assert modeBox!=null;
        JComboBox<String> typeBox = (JComboBox<String>) Helper.getComponent(RootPanel, "IntruderMainAesTypeBox");assert typeBox!=null;

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
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void md5Processor(){

        tmpPayload = Md5.stringToMd5(tmpPayload);

    }

    public static void rsaProcessor(){
        RSyntaxTextArea publicArea = (RSyntaxTextArea) Helper.getComponent(RootPanel, "IntruderMainRsaPublicArea"); assert publicArea!=null;
        RSyntaxTextArea privateArea = (RSyntaxTextArea) Helper.getComponent(RootPanel, "IntruderMainRsaPrivateArea"); assert privateArea!=null;
        JComboBox<String> modeBox = (JComboBox<String>) Helper.getComponent(RootPanel, "IntruderMainRsaTypeBox"); assert modeBox!=null;

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
        JComboBox<String> modeBox = (JComboBox<String>) Helper.getComponent(RootPanel, "IntruderMainBaseModeBox"); assert modeBox!=null;
        JComboBox<String> typeBox = (JComboBox<String>) Helper.getComponent(RootPanel, "IntruderMainBaseTypeBox"); assert typeBox!=null;

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
        JTextField separator1Field = (JTextField) Helper.getComponent(RootPanel, "IntruderMainAsciiSeparator1Field"); assert separator1Field!=null;
        JTextField separator2Field = (JTextField) Helper.getComponent(RootPanel, "IntruderMainAsciiSeparator2Field"); assert separator2Field!=null;
        JComboBox<String> typeBox = (JComboBox<String>) Helper.getComponent(RootPanel, "IntruderMainAsciiTypeBox"); assert typeBox!=null;

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
        JComboBox<String> modeBox = (JComboBox<String>) Helper.getComponent(RootPanel, "IntruderMainUnicodeTypeBox"); assert modeBox!=null;

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

    public static void registerProcessor(String processorName){
        processors.add(processorName);
    }

    public static void unregisterProcessor(String processorName){
        processors.remove(processorName);
    }

}
