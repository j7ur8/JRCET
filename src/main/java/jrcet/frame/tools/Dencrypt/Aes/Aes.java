package jrcet.frame.tools.Dencrypt.Aes;

import burp.lib.Helper;
import com.google.common.primitives.Bytes;
import org.jetbrains.annotations.NotNull;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.lang.invoke.SwitchPoint;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;

import static jrcet.frame.tools.Dencrypt.Base.Base.b64decoder;
import static jrcet.frame.tools.Dencrypt.Base.Base.b64encoder;

public class Aes {

    private static String text;
    private static byte[] key;
    private static byte[] iv;
    private static String returned;

    public static String Encrypt(String plainText, String Mode, String Key, String KeyType, String Iv, String IvType) throws Exception {

        repairParam(plainText,Mode,Key,KeyType,Iv,IvType);

        if(returned!=null) return  returned;

        StringBuilder plainTextBuilder;
        Cipher cipher = Cipher.getInstance(Mode);

        switch (Mode){

            case "AES/ECB/NoPadding":
                plainTextBuilder = new StringBuilder(plainText);
                for(String i:Collections.nCopies(16- plainTextBuilder.length()%16,"\000")){
                    plainTextBuilder.append(i);
                }
                plainText = plainTextBuilder.toString();
            case "AES/ECB/PKCS5Padding":
                cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"));
                break;
            case "AES/CBC/NoPadding":
                plainTextBuilder = new StringBuilder(plainText);
                for(String i:Collections.nCopies(16- plainTextBuilder.length()%16,"\000")){
                    plainTextBuilder.append(i);
                }
                plainText = plainTextBuilder.toString();
            case "AES/CBC/PKCS5Padding":
                cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"), new IvParameterSpec(iv));
                break;
        }

        return b64encoder.encodeToString(cipher.doFinal(plainText.getBytes(StandardCharsets.ISO_8859_1)));

    }


    // 解密
    public static String Decrypt(String cipherText, String Mode, String Key, String keyType, String Iv, String IvType) throws Exception {


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

        return new String(cipher.doFinal(b64decoder.decode(cipherText)), StandardCharsets.ISO_8859_1);

    }

    private static void repairParam(String cipherText, String Mode, String Key, String KeyType, String Iv, String IvType) {

        if (cipherText == null) {
            returned = "请输入需要解密的字符串";
        }
        if (Mode == null) {
            returned = "请选择模式";
        }
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
