package jrcet.frame.Dencrypt.Des;

import jrcet.help.Helper;
import jrcet.help.StringUtil;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;

import java.nio.charset.StandardCharsets;

import static burp.MyExtender.API;
import static jrcet.frame.Dencrypt.Des.DesComponent.DesComponentPanel;
import static jrcet.frame.Dencrypt.Base.Base.b64decoder;
import static jrcet.frame.Dencrypt.Base.Base.b64encoder;
import static jrcet.help.Helper.getByteByType;

public class Des {

    private static byte[] inputTextByte;
    private static    int len;
    private static byte[] key;
    private static byte[] iv;
    private static String returned;


    public static JComboBox<?> getModeBox(){
        return (JComboBox<?>) Helper.getComponent(DesComponentPanel, "DesMenuModeBox");
    }

    public static JComboBox<?> getKeyBox(){
        return (JComboBox<?>) Helper.getComponent(DesComponentPanel, "DesMenuKeyBox");
    }

    public static JComboBox<?> getIvBox(){
        return (JComboBox<?>) Helper.getComponent(DesComponentPanel, "DesMenuIvBox");
    }

    public static JTextField getIvField(){
        return (JTextField) Helper.getComponent(DesComponentPanel, "DesMenuIvField");
    }

    public static JTextField getKeyField(){
        return (JTextField) Helper.getComponent(DesComponentPanel, "DesMenuKeyField");
    }

    public static String Encrypt(String plainText){

        String Mode = (String) getModeBox().getSelectedItem();
        String Key = getKeyField().getText();
        String KeyType = (String) getKeyBox().getSelectedItem();
        String Iv = getIvField().getText();
        String IvType = (String) getIvBox().getSelectedItem();

//        API.logging().output().printf("Mode: %s, Key: %s, KeyType: %s, Iv: %s, IvType: %s\n",Mode,Key,KeyType,Iv,IvType);
        String result = "";
        try{
            result  = Encrypt(plainText, Mode, Key, KeyType, Iv, IvType);
        }catch (Exception e){
            API.logging().error().println(result);
        }

        return result;
    }

    public static String Decrypt(String plainText){

        String Mode = (String) getModeBox().getSelectedItem();
        String Key = getKeyField().getText();
        String KeyType = (String) getKeyBox().getSelectedItem();
        String Iv = getIvField().getText();
        String IvType = (String) getIvBox().getSelectedItem();

        String result = "";
        try{
            result  = Decrypt(plainText, Mode, Key, KeyType, Iv, IvType);
        }catch (Exception e){
            API.logging().error().println(result);
        }

        return result;
    }

    public static String Encrypt(String plainText, String Mode, String Key, String KeyType, String Iv, String IvType) throws Exception {

        repairParam(plainText,Key,KeyType,Iv,IvType);

//        API.logging().output().printf("Mode: %s, Key: %s, KeyType: %s, Iv: %s, IvType: %s\n",Mode,new String(key),KeyType,new String(iv),IvType);

        if(returned!=null) return returned;

        Cipher cipher = Cipher.getInstance(Mode);
        byte[] plainTextByte = new byte[(len%8==0?len/8:len/8+1)*8];

        switch (Mode){
            case "DES/ECB/NoPadding":
                for(int i=0; i<plainTextByte.length;i++){
                    if(i<len) {
                        plainTextByte[i]=inputTextByte[i];
                        continue;
                    }
                    plainTextByte[i] = (byte)Integer.parseInt("00",16);
                }
                inputTextByte = plainTextByte;
            case "DES/ECB/PKCS5Padding":
                cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "DES"));
                break;
            case "DES/CBC/NoPadding":
                for(int i=0; i<plainTextByte.length;i++){
                    if(i<len) {
                        plainTextByte[i]=inputTextByte[i];
                        continue;
                    }
                    plainTextByte[i] = (byte)Integer.parseInt("00",16);
                }
                inputTextByte = plainTextByte;
            case "DES/CBC/PKCS5Padding":
                cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "DES"), new IvParameterSpec(iv));
                break;
        }

        return b64encoder.encodeToString(cipher.doFinal(inputTextByte));

    }

    // 解密
    public static String Decrypt(String cipherText, String Mode, String Key, String KeyType, String Iv, String IvType) throws Exception {

        repairParam(cipherText,Key, KeyType,Iv,IvType);
        if(returned!=null) return returned;

        Cipher cipher = Cipher.getInstance(Mode);
        switch (Mode) {
            case "DES/ECB/NoPadding", "DES/ECB/PKCS5Padding" ->
                    cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "DES"));
            case "DES/CBC/NoPadding", "DES/CBC/PKCS5Padding" ->
                    cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "DES"), new IvParameterSpec(iv));
        }

        return new String(cipher.doFinal(b64decoder.decode(inputTextByte)),StandardCharsets.UTF_8).replaceAll("\\u0000","");
    }

    private static void repairParam(String InputText, String Key, String KeyType, String Iv, String IvType) {

        returned=null;
        key=null;
        iv=null;

        if (StringUtil.isBlank(InputText)) {
            returned = "请输入需要解密的字符串";
        }

        //对InputText处理
        assert InputText != null;
        inputTextByte = InputText.getBytes(StandardCharsets.UTF_8);
        len = inputTextByte.length;

        //设置key
        byte[] tmpKey = getByteByType(Key, KeyType);
        key = new byte[8];
        for (int j = 0; j < 8; j++) {
            if (j < tmpKey.length) {
                key[j] = tmpKey[j];
            } else {
                key[j] = (byte)Integer.parseInt("00",16);
            }
        }

        //设置iv
        byte[] tmpIv = getByteByType(Iv, IvType);
        iv = new byte[8];
        for (int j = 0; j < 8; j++) {
            if (j < tmpIv.length) {
                iv[j] = tmpIv[j];
            } else {
                iv[j] = (byte)Integer.parseInt("00",16);
            }
        }
    }

}
