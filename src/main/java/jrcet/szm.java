package jrcet;

import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * Des加解密工具类
 *
 * @author Lee
 */
public class szm {

//aaaaaa：**

//private  final byte[] DESkey = lmkProperties.getDesKey().getBytes();// 设置**，略去
//private  final byte[] DESkey = "abcdefgh".getBytes();// 设置**，略去  至少8位

//bbbb：偏移量
//private  final byte[] DESIV = lmkProperties.getDesIv().getBytes();// 设置向量，略去
//private  final byte[] DESIV = "12345678".getBytes();// 设置向量，略去  至少8位

    static AlgorithmParameterSpec iv = null;// 加密算法的参数接口，IvParameterSpec是它的一个实现
    private static SecretKey key = null;


    public szm(byte[] desKey) throws Exception {
        DESKeySpec keySpec = new DESKeySpec(desKey);// 设置**参数
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");// 获得**工厂
        key = keyFactory.generateSecret(keySpec);// 得到**对象
    }

    /**
     * 加密
     * @param data 待加密的数据
     * @return 加密后的数据
     * @throws Exception
     */
    public String encode(String data) throws Exception {
        Cipher enCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");// 得到加密对象Cipher
        enCipher.init(Cipher.ENCRYPT_MODE, key);// 设置工作模式为加密模式，给出**和向量
        byte[] pasByte = enCipher.doFinal(data.getBytes("utf-8"));
        //BASE64Encoder base64Encoder = new BASE64Encoder();
        return Base64.getEncoder().encodeToString(pasByte);
        //return base64Encoder.encode(pasByte);
    }

    /**
     * 解密
     * @param data  解密前的数据
     * @return 解密后的数据
     * @throws Exception
     */
    public String decode(String data) throws Exception {
        Cipher deCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        deCipher.init(Cipher.DECRYPT_MODE, key, iv);
        //BASE64Decoder base64Decoder = new BASE64Decoder();
        //byte[] pasByte = deCipher.doFinal(base64Decoder.decodeBuffer(data));
        byte[] pasByte = deCipher.doFinal(Base64.getDecoder().decode(data));
        return new String(pasByte, "UTF-8");
    }

    public static void main(String[] args) throws Exception {
        szm tools = new szm(Base64.getDecoder().decode("Z12/y2Lfzlc="));
        String data = "11111";
        System.out.println("加密:" + tools.encode(data));

        String data1 = tools.encode(data);

        System.out.println("解密:" + tools.decode(data1));
    }


}