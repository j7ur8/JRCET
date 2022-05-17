package jrcet.frame.tools.Intruder;

import jrcet.diycomponents.DiyJTextArea.ui.rsyntaxtextarea.RSyntaxTextArea;
import jrcet.frame.tools.Dencrypt.Aes.Aes;
import jrcet.frame.tools.Dencrypt.Base.Base;
import jrcet.frame.tools.Dencrypt.Rsa.Rsa;
import jrcet.frame.tools.Dencrypt.Unicode.Unicode;
import jrcet.lib.Helper;

import javax.swing.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;

import static jrcet.frame.tools.Intruder.IntruderComponent.IntruderComponentPanel;
import static jrcet.frame.tools.Intruder.IntruderComponent.IntruderModuleComponentList;

public class Intruder {

    private static String tmpPayload;

    private static JComponent RootPanel;

    private static ArrayList<String> processors = new ArrayList<>();

    public static String createIntruder(byte[] currentPayload) {

        tmpPayload = new String(currentPayload, StandardCharsets.UTF_8);

        for(JComponent jComponent : IntruderModuleComponentList){
            RootPanel = jComponent;
            switch (jComponent.getName()){
                case "IntruderMainAesPanel":
                    aseProcessor();
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

    public static void rsaProcessor(){
        RSyntaxTextArea publicArea = (RSyntaxTextArea) Helper.getComponent(RootPanel, "IntruderMainRsaPublicArea"); assert publicArea!=null;
        RSyntaxTextArea privateArea = (RSyntaxTextArea) Helper.getComponent(RootPanel, "IntruderMainRsaPrivateArea"); assert privateArea!=null;
        JComboBox<String> modeBox = (JComboBox) Helper.getComponent(RootPanel, "IntruderMainRsaTypeBox"); assert modeBox!=null;

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
        tmpPayload = tmpPayload+"-ascii";
    }

    public static void unicodeProcessor(){
        JComboBox<String> modeBox = (JComboBox<String>) Helper.getComponent(RootPanel, "IntruderMainUnicodeTypeBox"); assert modeBox!=null;

        String asciiMode = (String) modeBox.getSelectedItem();

        switch (Objects.requireNonNull(asciiMode)){
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
