package jrcet.frame.tools.Intruder;

import jrcet.frame.tools.Dencrypt.Aes.Aes;
import jrcet.lib.Helper;

import javax.swing.*;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static jrcet.frame.tools.Intruder.IntruderComponent.IntruderComponentPanel;

public class Intruder {

    private static String processor;
    private static String aesIv;
    private static String aesIvType;
    private static String aesKey;
    private static String aesKeyType;
    private static String aesMode;
    private static String aesType;

    private static String sPayload;

    static public String createIntruder(byte[] currentPayload) {

        setProcessor();

        sPayload = new String(currentPayload, StandardCharsets.UTF_8);

        switch (processor){
             case "Aes":
                 return aseProcessor();
         }

        return "aa";
    }

    public static void setProcessor(){
        processor =  "Aes";
    }

    public static String aseProcessor() {
        JTextField ivField = (JTextField) Helper.getComponent(IntruderComponentPanel, "IntruderMainAesIvField"); assert ivField!=null;
        JComboBox<String> ivBox = (JComboBox<String>) Helper.getComponent(IntruderComponentPanel, "IntruderMainAesIvBox"); assert ivBox!=null;
        JTextField keyField = (JTextField) Helper.getComponent(IntruderComponentPanel, "IntruderMainAesKeyField");assert keyField!=null;
        JComboBox<String> keyBox = (JComboBox<String>) Helper.getComponent(IntruderComponentPanel, "IntruderMainAesKeyBox");assert keyBox!=null;
        JComboBox<String> modeBox = (JComboBox<String>) Helper.getComponent(IntruderComponentPanel, "IntruderMainAesModeBox");assert modeBox!=null;
        JComboBox<String> typeBox = (JComboBox<String>) Helper.getComponent(IntruderComponentPanel, "IntruderMainAesTypeBox");assert typeBox!=null;

        aesIv = ivField.getText();
        aesIvType = (String) ivBox.getSelectedItem();
        aesKey = keyField.getText();
        aesKeyType = (String) keyBox.getSelectedItem();
        aesMode = (String) modeBox.getSelectedItem();
        aesType = (String) typeBox.getSelectedItem();

        try {
            switch (Objects.requireNonNull(aesType)){
                case "Decrypt":
                    return Aes.Decrypt(sPayload,aesMode, aesKey, aesKeyType, aesIv, aesIvType);
                case "Encrypt":
                    return Aes.Encrypt(sPayload,aesMode, aesKey, aesKeyType, aesIv, aesIvType);
            }
        }catch (Exception ignore){}

        return "Error";
    }

}
