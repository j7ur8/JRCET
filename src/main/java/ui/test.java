package ui;

import jrcet.frame.tools.Dencrypt.Aes.Aes;

import java.io.*;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import static jrcet.frame.tools.Dencrypt.Base.Base.b64decoder;
import static jrcet.frame.tools.Dencrypt.Base.Base.b64encoder;
import static jrcet.frame.tools.Dencrypt.Unicode.Unicode.unicodeToString;

public class test {

    // 加密
    public static String Encrypt(String sSrc, String sKey) throws Exception {
        if (sKey == null) {
            System.out.print("Key为空null");
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            System.out.print("Key长度不是16位");
            return null;
        }
        byte[] raw = sKey.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");//"算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
        return b64encoder.encodeToString(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
    }

    public static String Decrypt(String sSrc, String sKey) throws Exception {
        try {
            // 判断Key是否正确
            if (sKey == null) {
                System.out.print("Key为空null");
                return null;
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
                System.out.print("Key长度不是16位");
                return null;
            }
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = b64decoder.decode(sSrc);//先用base64解密
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original,"utf-8");
                return originalString;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        String Key = "1234567890abcdef111111";
        String Iv = "1234567890abcdef";

        String plainText = "www.gowhere.so";

        String Mode = "AES/CBC/NoPadding    ";
        String KeyType = "Raw";
        String IvType = "Raw";
        String cipherText = Aes.Encrypt(plainText, Mode, Key, KeyType, Iv, IvType);
        System.out.println("加密后的字串是：" + cipherText);

        // 解密
        String DeString  = Aes.Decrypt(cipherText, Mode, Key, KeyType, Iv, IvType);
        System.out.println("解密后的字串是：" + DeString);
    }
}

