package jrcet.frame.Intruder;


import burp.BurpExtender;
import jrcet.frame.Tools.Captcha.Captcha;
import jrcet.frame.Tools.Dencrypt.Aes.Aes;
import jrcet.frame.Tools.Dencrypt.Ascii.Ascii;
import jrcet.frame.Tools.Dencrypt.Base.Base;
import jrcet.frame.Tools.Dencrypt.Md5.Md5;
import jrcet.frame.Tools.Dencrypt.Rsa.Rsa;
import jrcet.frame.Tools.Dencrypt.Unicode.Unicode;
import jrcet.help.Helper;
import jrcet.help.d4ocr.network.HttpClient;


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
        String authRequestPackage = "POST /openapi/auth_code HTTP/1.1\n" +
                "Host: ai.chinatelecom.cn:9011\n" +
                "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:109.0) Gecko/20100101 Firefox/109.0\n" +
                "Accept: */*\n" +
                "Accept-Language: en-US,en;q=0.5\n" +
                "Accept-Encoding: gzip, deflate\n" +
                "Content-Type: application/json; charset=UTF-8\n" +
                "Token: undefined\n" +
                "Content-Length: 227\n" +
                "Origin: https://ai.chinatelecom.cn:3021\n" +
                "Referer: https://ai.chinatelecom.cn:3021/\n" +
                "Sec-Fetch-Dest: empty\n" +
                "Sec-Fetch-Mode: cors\n" +
                "Sec-Fetch-Site: same-site\n" +
                "Te: trailers\n" +
                "Connection: close\n" +
                "\n" +
                "{\"body\":\"vCAA3hxVbOvIyzYsNCCXeOtiXbDeRFjcc3o8UvJRMdvDwyRpaQY05c0btQYb4rDyeOMRDBgVJlyAML8UV5mYP60RwyDT6J/jIJmqY166wL5EHnlgiRAmd/T2l/G7b/xQG9y3p37JmKkmniZlSfyBJOkuEOyEAXDs92p45zC3cHVcM9w57iT4nHFBqEqN1q7RPCozxbS7kFZNyocNJlHAew==\"}";
        try {
            HttpClient httpClient = new HttpClient("https://ai.chinatelecom.cn:9011/openapi/auth_code", authRequestPackage);
            String response = httpClient.doRequest();

            BurpExtender.stdout.println(response);

            response = Helper.matchByRegular(response, "\"res\":\"(.*)\",\"code\"");
            BurpExtender.stdout.println(response);


            String res = Aes.Decrypt(response, "AES/ECB/PKCS5Padding", "Digi720ctidHuma3", "Raw", "Digi720ctidHuma3", "Raw");


            String authCode = Helper.matchByRegular(res,"authCode\": \"(.*)\"}}");
            String phoneRequestParam = "{\"userNumber\":\"18973125622\",\"authCode\":\"" + authCode + "\",\"seqid\":\"2596c704-069c-483d-928c-7ff877c5ecfa\",\"timestamp\":\"1675662374\"}";

            return Aes.Encrypt(phoneRequestParam, "AES/ECB/PKCS5Padding", "Digi720ctidHuma3", "Raw", "Digi720ctidHuma3", "Raw");
        }catch (Exception e){
            BurpExtender.stdout.println(e);
        }
        return "error";
    }
}
