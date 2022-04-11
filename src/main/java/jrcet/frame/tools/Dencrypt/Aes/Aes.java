package jrcet.frame.tools.Dencrypt.Aes;

import org.jetbrains.annotations.NotNull;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static jrcet.frame.tools.Dencrypt.Base.Base.b64decoder;
import static jrcet.frame.tools.Dencrypt.Base.Base.b64encoder;

public class Aes {

    public static String Encrypt(String plainText, String Mode, String Key, String Iv) throws Exception {

        Key = repairKey(Key);

        StringBuilder plainTextBuilder = new StringBuilder(plainText);
        for(String i:Collections.nCopies(16- plainTextBuilder.length()%16,"\000")){
            plainTextBuilder.append(i);
        }
        plainText = plainTextBuilder.toString();

        Cipher cipher = Cipher.getInstance(Mode);
        switch (Mode){
            case "AES/ECB/NoPadding":
            case "AES/ECB/PKCS5Padding":
                cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(Key.getBytes(StandardCharsets.UTF_8), "AES"));
                break;
            case "AES/CBC/NoPadding":
            case "AES/CBC/PKCS5Padding":
                cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(Key.getBytes(StandardCharsets.UTF_8), "AES"), new IvParameterSpec(Iv.getBytes()));
                break;
        }

        return b64encoder.encodeToString(cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8)));

    }


    // 解密
    public static String Decrypt(String cipherText, String Mode, String Key, String Iv) throws Exception {

        Key = repairKey(Key);

        Cipher cipher = Cipher.getInstance(Mode);
        switch (Mode){
            case "AES/ECB/NoPadding":
            case "AES/ECB/PKCS5Padding":
                cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(Key.getBytes(StandardCharsets.UTF_8), "AES"));
                break;
            case "AES/CBC/NoPadding":
            case "AES/CBC/PKCS5Padding":
                cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(Key.getBytes(StandardCharsets.UTF_8), "AES"), new IvParameterSpec(Iv.getBytes()));
                break;
        }

        return new String(cipher.doFinal(b64decoder.decode(cipherText)), StandardCharsets.UTF_8);

    }

    @NotNull
    private static String repairKey(String Key) {
        int keyLength = Key.length()<16?16:(Key.length()>24?32:24);
        StringBuilder keyBuilder = new StringBuilder(Key);
        for(String i: Collections.nCopies(keyLength- keyBuilder.length()%keyLength,"\000")){
            keyBuilder.append(i);
        }
        Key = keyBuilder.toString();
        return Key;
    }
}
