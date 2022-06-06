package jrcet.frame.tools.Dencrypt.Aes;

import jrcet.help.Helper;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import static jrcet.frame.tools.Dencrypt.Base.Base.b64decoder;
import static jrcet.frame.tools.Dencrypt.Base.Base.b64encoder;

public class Aes {

    private static byte[] inputTextByte;
    private static    int len;
    private static byte[] key;
    private static byte[] iv;
    private static String returned;

    public static String Encrypt(String plainText, String Mode, String Key, String KeyType, String Iv, String IvType) throws Exception {

        repairParam(plainText,Mode,Key,KeyType,Iv,IvType);

        if(returned!=null) return  returned;

        Cipher cipher = Cipher.getInstance(Mode);
        byte[] plainTextByte = new byte[(len%16==0?len/16:len/16+1)*16];

        switch (Mode){
            case "AES/ECB/NoPadding":
                for(int i=0; i<plainTextByte.length;i++){
                    if(i<len) {
                        plainTextByte[i]=inputTextByte[i];
                        continue;
                    }
                    plainTextByte[i] = (byte)Integer.parseInt("00",16);
                }
                inputTextByte = plainTextByte;
            case "AES/ECB/PKCS5Padding":
                cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"));
                break;
            case "AES/CBC/NoPadding":
                for(int i=0; i<plainTextByte.length;i++){
                    if(i<len) {
                        plainTextByte[i]=inputTextByte[i];
                        continue;
                    }
                    plainTextByte[i] = (byte)Integer.parseInt("00",16);
                }
                inputTextByte = plainTextByte;
            case "AES/CBC/PKCS5Padding":
                cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"), new IvParameterSpec(iv));
                break;
        }

        return b64encoder.encodeToString(cipher.doFinal(inputTextByte));

    }

    // 解密
    public static String Decrypt(String cipherText, String Mode, String Key, String KeyType, String Iv, String IvType) throws Exception {

        repairParam(cipherText,Mode,Key, KeyType,Iv,IvType);
        if(returned!=null) return returned;

        Cipher cipher = Cipher.getInstance(Mode);
        switch (Mode){
            case "AES/ECB/NoPadding":
            case "AES/ECB/PKCS5Padding":
                cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"));
                break;
            case "AES/CBC/NoPadding":
            case "AES/CBC/PKCS5Padding":
                cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), new IvParameterSpec(iv));
                break;
        }

        return new String(cipher.doFinal(b64decoder.decode(inputTextByte)),StandardCharsets.UTF_8).replaceAll("\\u0000","");
    }

    private static void repairParam(String InputText, String Mode, String Key, String KeyType, String Iv, String IvType) throws UnsupportedEncodingException {
        returned=null;
        key=null;
        iv=null;

        if (InputText == null) {
            returned = "请输入需要解密的字符串";
        }
        if (Mode == null) {
            returned = "请选择模式";
        }
        //对InputText处理
        inputTextByte = InputText.getBytes(StandardCharsets.UTF_8);
        len = inputTextByte.length;
        //设置key和iv
        String[] s = new String[]{Key, Iv}, t = new String[]{KeyType, IvType};
        for (int i = 0; i < 2; i++) {
            byte[] r = new byte[0];
            switch (t[i]) {
                case "Base64":
                    r = b64decoder.decode(s[i]);
                    break;
                case "Hex":
                    r = Helper.hexStringToByteArray(s[i]);
                    break;
                case "Raw":
                    r = s[i].getBytes(StandardCharsets.ISO_8859_1);
                    break;
            }
            if (i == 0) {  //设置key
                int len = r.length <= 16 ? 16 : (r.length > 24 ? 32 : 24);
                key = new byte[len];
                for (int j = 0; j < len; j++) {
                    if (j < Key.length()) {
                        key[j] = r[j];
                    } else {
                        key[j] = (byte)Integer.parseInt("00",16);
                    }
                }
            }
            if (i == 1) { //设置iv
                iv = new byte[16];
                for (int j = 0; j < 16; j++) {
                    if (j < Iv.length()) {
                        iv[j] = r[j];
                    } else {
                        iv[j] = (byte)Integer.parseInt("00",16);
                    }
                }
            }
        }
    }

}
