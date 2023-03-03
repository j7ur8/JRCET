package jrcet.frame.Tools.Dencrypt.Des;

import jrcet.help.Helper;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.util.Base64;

public class Des {

    /**
     * 偏移变量，固定占8位字节
     */
    private final static String IV_PARAMETER = "lianyite";
    /**
     * 密钥算法
     */
    private static final String ALGORITHM = "DES";
    /**
     * 加密/解密算法-工作模式-填充模式
     */
    private static final String CIPHER_ALGORITHM = "DES/ECB/PKCS5Padding";
    /**
     * 默认编码
     */
    private static final String CHARSET = "utf-8";

    /**
     * 生成key
     *
     * @param password
     * @return
     * @throws Exception
     */
    private static Key generateKey(String password) throws Exception {
        DESKeySpec dks = new DESKeySpec(password.getBytes(CHARSET));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        return keyFactory.generateSecret(dks);
    }


    /**
     * DES加密字符串
     *
     * @param password 加密密码，长度不能够小于8位
     * @param data 待加密字符串
     * @return 加密后内容
     */
    public static String encrypt(String password, String data) {
        if (password== null || password.length() < 8) {
            throw new RuntimeException("加密失败，key不能小于8位");
        }
        if (data == null)
            return null;
        try {
            Key secretKey = generateKey(password);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
//            IvParameterSpec iv = new IvParameterSpec(IV_PARAMETER.getBytes(CHARSET));
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] bytes = cipher.doFinal(data.getBytes(CHARSET));

            //JDK1.8及以上可直接使用Base64，JDK1.7及以下可以使用BASE64Encoder
            //Android平台可以使用android.util.Base64
            return new String(Base64.getEncoder().encode(bytes));

        } catch (Exception e) {
            e.printStackTrace();
            return data;
        }
    }

    /**
     * DES解密字符串
     *
     * @param password 解密密码，长度不能够小于8位
     * @param data 待解密字符串
     * @return 解密后内容
     */
    public static String decrypt(String password, String data) {
        if (password== null || password.length() < 8) {
            throw new RuntimeException("加密失败，key不能小于8位");
        }
        if (data == null)
            return null;
        try {
            Key secretKey = generateKey(password);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
//            IvParameterSpec iv = new IvParameterSpec(IV_PARAMETER.getBytes(CHARSET));
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(data.getBytes(CHARSET))), CHARSET);
        } catch (Exception e) {
            e.printStackTrace();
            return data;
        }
    }

    /**
     * DES加密文件
     *
     * @param srcFile  待加密的文件
     * @param destFile 加密后存放的文件路径
     * @return 加密后的文件路径
     */
//    public static String encryptFile(String password, String srcFile, String destFile) {
//
//        if (password== null || password.length() < 8) {
//            throw new RuntimeException("加密失败，key不能小于8位");
//        }
//        try {
//            IvParameterSpec iv = new IvParameterSpec(IV_PARAMETER.getBytes(CHARSET));
//            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
//            cipher.init(Cipher.ENCRYPT_MODE, generateKey(key), iv);
//            InputStream is = Files.newInputStream(Paths.get(srcFile));
//            OutputStream out = Files.newOutputStream(Paths.get(destFile));
//            CipherInputStream cis = new CipherInputStream(is, cipher);
//            byte[] buffer = new byte[1024];
//            int r;
//            while ((r = cis.read(buffer)) > 0) {
//                out.write(buffer, 0, r);
//            }
//            cis.close();
//            is.close();
//            out.close();
//            return destFile;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return null;
//    }

    /**
     * DES解密文件
     *
     * @param srcFile  已加密的文件
     * @param destFile 解密后存放的文件路径
     * @return 解密后的文件路径
     */
//    public static String decryptFile(String password, String srcFile, String destFile) {
//        if (password== null || password.length() < 8) {
//            throw new RuntimeException("加密失败，key不能小于8位");
//        }
//        try {
//            File file = new File(destFile);
//            if (!file.exists()) {
//                file.getParentFile().mkdirs();
//                file.createNewFile();
//            }
//            IvParameterSpec iv = new IvParameterSpec(IV_PARAMETER.getBytes(CHARSET));
//            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
//            cipher.init(Cipher.DECRYPT_MODE, generateKey(key), iv);
//            InputStream is = Files.newInputStream(Paths.get(srcFile));
//            OutputStream out = Files.newOutputStream(Paths.get(destFile));
//            CipherOutputStream cos = new CipherOutputStream(out, cipher);
//            byte[] buffer = new byte[1024];
//            int r;
//            while ((r = is.read(buffer)) >= 0) {
//                cos.write(buffer, 0, r);
//            }
//            cos.close();
//            is.close();
//            out.close();
//            return destFile;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return null;
//    }
//    public static void main(String[] args) {
//        String a= encrypt("lianyitech","lifang");
//        System.out.println(a);
//    }
    public static void main(String[] args) {
//        System.out.println(decrypt("lianyitech","Iv/B6cFc6f7hJsTV3HHCUg=="));
        System.out.println(decrypt(new String(Helper.base64Decode("dPHcZQvoZ0qC5AmKkjGXrw==")),"nPHT6L960brRpOfddlpJGC2MOzXrpOU/Iiu22qGteejQESSFsxPNt3lg/ryZhYyHM9SbHggxXJl2ngargHbKmxTwV0/rXahrNxlKtObnCS5nDaYMaMpB//ny04mE11Q6OalOj2mdYiUJnmJNbfdV9iUTUglwNhyGnrIwe33wbPx9bUssUdUptS0t1rrSu21+5h9SaBbBatmpY0RzI1PbkQ=="));
    }
}