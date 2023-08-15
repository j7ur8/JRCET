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

    private static final JComponent tmpRootPanel = IntruderComponentPanel;




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
        tmpPayload= Captcha.identifyCaptchaForIntruder();
        return tmpPayload;
    }

    public static String invokeDiy(byte[] currentPayload)  {
//        String name= new String(currentPayload);
//        String name = Des.encrypt("lianyitech", new String(currentPayload));
        String captcha = Captcha.identifyCaptchaForIntruder();

        return captcha;
    }
}
