package jrcet.frame.tools.Dencrypt.Md5;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5 {

    public static String stringToMd5(String text) {
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(
                    text.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有这个md5算法！");
        }
        String tmpPayload = new BigInteger(1, secretBytes).toString(16);
        for (int i = 0; i < 32 - tmpPayload.length(); i++) {
            tmpPayload = "0" + tmpPayload;
        }

        return tmpPayload;
    }

}
