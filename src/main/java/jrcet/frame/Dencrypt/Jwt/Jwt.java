package jrcet.frame.Dencrypt.Jwt;

import com.alibaba.fastjson.JSONObject;
import jrcet.help.Helper;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Objects;

import static burp.MyExtender.BurpAPI;
import static jrcet.frame.Dencrypt.Jwt.JwtComponent.JwtComponentPanel;

public class Jwt{

    public static JLabel getJwtMenuVerifyLabel(){
        return (JLabel) Helper.getComponent(JwtComponentPanel, "JwtMenuVerifyLabel");
    }

    public static JComboBox<?> getJwtMenuTypeBox(){
        return (JComboBox<?>) Helper.getComponent(JwtComponentPanel, "JwtMenuTypeBox");
    }

    public static boolean ifValidJWT(String jwtToken){

        String[] tokens = jwtToken.split("\\.");

        return tokens.length >= 2 && Helper.isUrlBase64(tokens[0]) && Helper.isUrlBase64(tokens[1]);
    }

    public static String encrypt(String header, String payload, String signature, String privateKey){
        String type = (String) getJwtMenuTypeBox().getSelectedItem();
        String message = Helper.base64UrlEncode2String(header)+"."+Helper.base64UrlEncode2String(payload);
        String result = "";
        try{
            switch (Objects.requireNonNull(type)){
                case "HS256" -> {
                    result = HMACSHA256Sign(message, signature);
                }
                case  "HS384" -> {
                    result = HMACSHA384Sign(message, signature);
                }
                case "HS512" -> {
                    result = HMACSHA512Sign(message, signature);
                }
                case "RS256" -> {
                    result = RSASHA256Sign(message, privateKey);
                }
                case "RS384" -> {
                    result = RSASHA384Sign(message, privateKey);
                }
                case "RS512" -> {
                    result = RSASHA512Sign(message, privateKey);
                }
                case "ES256" -> {
                    result = ECDSASHA256Sign(message, privateKey);
                }
                case "ES384" -> {
                    result = ECDSASHA384Sign(message, privateKey);
                }
                case "ES512" -> {
                    result = ECDSASHA512Sign(message, privateKey);
                }
            }
        }catch ( Exception e){
            BurpAPI.logging().error().println(e);
        }
        return message + "." + result;
    }

    public static boolean verify(String JwtToken,String header, String message, String secret, String privateKey,String publicKey){

        String result = encrypt(header,message,secret,privateKey);

        return JwtToken.equals(result);
    }

    //HS256

    public static String HMACSign(String type, String message, String secret) throws Exception{
        SecretKeySpec signingKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
        Mac mac = Mac.getInstance(type);
        mac.init(signingKey);
        return Helper.base64UrlEncode2String(mac.doFinal(message.getBytes()));
    }

    public static boolean HMACVerify(String type, String text, String sign ,String secret)throws Exception{
        return sign.equals(HMACSign(type, text, secret));
    }

    public static String HMACSHA256Sign(String text, String secret) throws Exception {
        return HMACSign("HmacSHA256",text,secret);
    }

    public static boolean HMACSHA256Verify(String text, String sign,String secret) throws Exception {
        return HMACVerify("HmacSHA256",text,sign,secret);
    }
    //HS384
    public static String HMACSHA384Sign(String text, String secret) throws Exception {
        return HMACSign("HmacSHA384",text,secret);
    }

    public static boolean HMACSHA384Verify(String text, String sign, String secret) throws Exception {
        return HMACVerify("HmacSHA384",text,sign, secret);
    }
    //HS512
    public static String HMACSHA512Sign(String message, String secret) throws Exception {
       return HMACSign("HmacSHA512",message,secret);
    }

    public static boolean HMACSHA512Verify(String text, String sign,String secret) throws Exception {
        return HMACVerify("HmacSHA512",text, sign,secret);
    }

    //RSA ShaSigh and shaverify
    public static String SHASign(String type,String message, String privateKey) throws Exception {
        privateKey = privateKey.replace("\n","");
        if(privateKey.startsWith("-----BEGIN PRIVATE KEY-----")){
            privateKey = privateKey.substring("-----BEGIN PRIVATE KEY-----".length());
        }
        if(privateKey.endsWith("-----END PRIVATE KEY-----")){
            privateKey = privateKey.substring(0,privateKey.length()-"-----END PRIVATE KEY-----".length());
        }

        PrivateKey pk = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(Helper.base64Decode2Byte(privateKey)));

        Signature privateSignature = Signature.getInstance(type);
        privateSignature.initSign(pk);
        privateSignature.update(message.getBytes(StandardCharsets.UTF_8));
        return Helper.base64UrlEncode2String(privateSignature.sign());
    }

    public static Boolean SHAVerify(String type, String text, String signature, String publicKey) {
        publicKey = publicKey.replace("\n","");
        if(publicKey.startsWith("-----BEGIN PUBLIC KEY-----")){
            publicKey = publicKey.substring("-----BEGIN PUBLIC KEY-----".length());
        }
        if(publicKey.endsWith("-----END PUBLIC KEY-----")){
            publicKey = publicKey.substring(0,publicKey.length()-"-----END PUBLIC KEY-----".length());
        }

        try{
            PublicKey pk = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Helper.base64Decode2Byte(publicKey)));

            Signature sign = Signature.getInstance(type);
            sign.initVerify(pk);
            sign.update(text.getBytes(StandardCharsets.UTF_8));

            return sign.verify(Helper.base64UrlDecode2Byte((signature)));
        }catch (Exception e){
            return false;
        }
    }

    public static String RSASHA256Sign(String message, String privateKey) throws Exception {
        return SHASign("SHA256withRSA", message, privateKey);
    }

    public static Boolean RSASHA256Verify(String text, String signature, String publicKey) {
        return SHAVerify("SHA256withRSA",text, signature, publicKey);
    }
    public static String RSASHA384Sign( String message, String privateKey) throws Exception {
        return SHASign("SHA384withRSA", message, privateKey);
    }

    public static Boolean RSASHA384Verify(String text, String signature, String publicKey) {
        return SHAVerify("SHA384withRSA",text,signature,publicKey);
    }

    public static String RSASHA512Sign( String message, String privateKey) throws Exception {
        return SHASign("SHA512withRSA", message, privateKey);
    }

    public static Boolean RSASHA512Verify(String text, String signature, String publicKey ) {
        return SHAVerify("SHA512withRSA", text, signature, publicKey);
    }


    //RSA ShaSigh and shaverify
    public static String ECDSign(String type,String message, String privateKey) throws Exception {
        privateKey = privateKey.replace("\n","");
        if(privateKey.startsWith("-----BEGIN PRIVATE KEY-----")){
            privateKey = privateKey.substring("-----BEGIN PRIVATE KEY-----".length());
        }
        if(privateKey.endsWith("-----END PRIVATE KEY-----")){
            privateKey = privateKey.substring(0,privateKey.length()-"-----END PRIVATE KEY-----".length());
        }

        PrivateKey pk = KeyFactory.getInstance("EC").generatePrivate(new PKCS8EncodedKeySpec(Helper.base64Decode2Byte(privateKey)));
        Signature signature = Signature.getInstance(type);
        signature.initSign(pk);

        signature.update(message.getBytes());

        return Helper.base64UrlEncode2String(signature.sign());

    }

    public static Boolean ECDVerify(String type, String text, String signature, String publicKey) {
        publicKey = publicKey.replace("\n","");
        if(publicKey.startsWith("-----BEGIN PUBLIC KEY-----")){
            publicKey = publicKey.substring("-----BEGIN PUBLIC KEY-----".length());
        }
        if(publicKey.endsWith("-----END PUBLIC KEY-----")){
            publicKey = publicKey.substring(0,publicKey.length()-"-----END PUBLIC KEY-----".length());
        }

        try{
            PublicKey pk = KeyFactory.getInstance("EC").generatePublic(new X509EncodedKeySpec(Helper.base64Decode2Byte(publicKey)));

            Signature sign = Signature.getInstance(type);
            sign.initVerify(pk);
            sign.update(text.getBytes(StandardCharsets.UTF_8));

            return sign.verify(Helper.base64UrlDecode2Byte((signature)));
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static String ECDSASHA256Sign( String message, String privateKey) throws Exception {
        return ECDSign("SHA256withECDSAinP1363format", message, privateKey);
    }

    public static Boolean ECDSASHA256Verify(String text, String signature, String publicKey ) {
        return ECDVerify("SHA256withECDSAinP1363format", text, signature, publicKey);
    }

    public static String ECDSASHA384Sign( String message, String privateKey) throws Exception {
        return ECDSign("SHA384withECDSAinP1363format", message, privateKey);
    }

    public static Boolean ECDSASHA384Verify(String text, String signature, String publicKey ) {
        return ECDVerify("SHA384withECDSAinP1363format", text, signature, publicKey);
    }


    public static String ECDSASHA512Sign( String message, String privateKey) throws Exception {
        return ECDSign("SHA512withECDSAinP1363format", message, privateKey);
    }

    public static Boolean ECDSASHA512Verify(String text, String signature, String publicKey ) {
        return ECDVerify("SHA512withECDSAinP1363format", text, signature, publicKey);
    }


    //RSA ShaSigh and shaverify
    public static String PSSSign(String type,String message, String privateKey) throws Exception {
        privateKey = privateKey.replace("\n","");
        if(privateKey.startsWith("-----BEGIN PRIVATE KEY-----")){
            privateKey = privateKey.substring("-----BEGIN PRIVATE KEY-----".length());
        }
        if(privateKey.endsWith("-----END PRIVATE KEY-----")){
            privateKey = privateKey.substring(0,privateKey.length()-"-----END PRIVATE KEY-----".length());
        }

        PrivateKey pk = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(Helper.base64Decode2Byte(privateKey)));
        Signature signature = Signature.getInstance(type);
        signature.initSign(pk);

        signature.update(message.getBytes());

        return Helper.base64UrlEncode2String(signature.sign());

    }

    public static Boolean PSSVerify(String type, String text, String signature, String publicKey) {
        publicKey = publicKey.replace("\n","");
        if(publicKey.startsWith("-----BEGIN PUBLIC KEY-----")){
            publicKey = publicKey.substring("-----BEGIN PUBLIC KEY-----".length());
        }
        if(publicKey.endsWith("-----END PUBLIC KEY-----")){
            publicKey = publicKey.substring(0,publicKey.length()-"-----END PUBLIC KEY-----".length());
        }

        try{
            PublicKey pk = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Helper.base64Decode2Byte(publicKey)));

            Signature sign = Signature.getInstance(type);
            sign.initVerify(pk);
            sign.update(text.getBytes(StandardCharsets.UTF_8));

            return sign.verify(Helper.base64UrlDecode2Byte((signature)));
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static String RSAPSSSHA256Sign( String message, String privateKey) throws Exception {
        return PSSSign("SHA256withRSA/PSS", message, privateKey);
    }

    public static Boolean RSAPSSSHA256Verify(String text, String signature, String publicKey ) {
        return PSSVerify("SHA256withRSA/PSS", text, signature, publicKey);
    }


//    public static void main(String[] args) throws Exception{
//
//        System.out.println(RSAPSSSHA256Sign("eyJhbGciOiJFUzUxMiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0","-----BEGIN PRIVATE KEY-----\n" +
//                "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQC7VJTUt9Us8cKj\n" +
//                "MzEfYyjiWA4R4/M2bS1GB4t7NXp98C3SC6dVMvDuictGeurT8jNbvJZHtCSuYEvu\n" +
//                "NMoSfm76oqFvAp8Gy0iz5sxjZmSnXyCdPEovGhLa0VzMaQ8s+CLOyS56YyCFGeJZ\n" +
//                "qgtzJ6GR3eqoYSW9b9UMvkBpZODSctWSNGj3P7jRFDO5VoTwCQAWbFnOjDfH5Ulg\n" +
//                "p2PKSQnSJP3AJLQNFNe7br1XbrhV//eO+t51mIpGSDCUv3E0DDFcWDTH9cXDTTlR\n" +
//                "ZVEiR2BwpZOOkE/Z0/BVnhZYL71oZV34bKfWjQIt6V/isSMahdsAASACp4ZTGtwi\n" +
//                "VuNd9tybAgMBAAECggEBAKTmjaS6tkK8BlPXClTQ2vpz/N6uxDeS35mXpqasqskV\n" +
//                "laAidgg/sWqpjXDbXr93otIMLlWsM+X0CqMDgSXKejLS2jx4GDjI1ZTXg++0AMJ8\n" +
//                "sJ74pWzVDOfmCEQ/7wXs3+cbnXhKriO8Z036q92Qc1+N87SI38nkGa0ABH9CN83H\n" +
//                "mQqt4fB7UdHzuIRe/me2PGhIq5ZBzj6h3BpoPGzEP+x3l9YmK8t/1cN0pqI+dQwY\n" +
//                "dgfGjackLu/2qH80MCF7IyQaseZUOJyKrCLtSD/Iixv/hzDEUPfOCjFDgTpzf3cw\n" +
//                "ta8+oE4wHCo1iI1/4TlPkwmXx4qSXtmw4aQPz7IDQvECgYEA8KNThCO2gsC2I9PQ\n" +
//                "DM/8Cw0O983WCDY+oi+7JPiNAJwv5DYBqEZB1QYdj06YD16XlC/HAZMsMku1na2T\n" +
//                "N0driwenQQWzoev3g2S7gRDoS/FCJSI3jJ+kjgtaA7Qmzlgk1TxODN+G1H91HW7t\n" +
//                "0l7VnL27IWyYo2qRRK3jzxqUiPUCgYEAx0oQs2reBQGMVZnApD1jeq7n4MvNLcPv\n" +
//                "t8b/eU9iUv6Y4Mj0Suo/AU8lYZXm8ubbqAlwz2VSVunD2tOplHyMUrtCtObAfVDU\n" +
//                "AhCndKaA9gApgfb3xw1IKbuQ1u4IF1FJl3VtumfQn//LiH1B3rXhcdyo3/vIttEk\n" +
//                "48RakUKClU8CgYEAzV7W3COOlDDcQd935DdtKBFRAPRPAlspQUnzMi5eSHMD/ISL\n" +
//                "DY5IiQHbIH83D4bvXq0X7qQoSBSNP7Dvv3HYuqMhf0DaegrlBuJllFVVq9qPVRnK\n" +
//                "xt1Il2HgxOBvbhOT+9in1BzA+YJ99UzC85O0Qz06A+CmtHEy4aZ2kj5hHjECgYEA\n" +
//                "mNS4+A8Fkss8Js1RieK2LniBxMgmYml3pfVLKGnzmng7H2+cwPLhPIzIuwytXywh\n" +
//                "2bzbsYEfYx3EoEVgMEpPhoarQnYPukrJO4gwE2o5Te6T5mJSZGlQJQj9q4ZB2Dfz\n" +
//                "et6INsK0oG8XVGXSpQvQh3RUYekCZQkBBFcpqWpbIEsCgYAnM3DQf3FJoSnXaMhr\n" +
//                "VBIovic5l0xFkEHskAjFTevO86Fsz1C2aSeRKSqGFoOQ0tmJzBEs1R6KqnHInicD\n" +
//                "TQrKhArgLXX4v3CddjfTRJkFWDbE/CkvKZNOrcf1nhaGCPspRJj2KUkj1Fhl9Cnc\n" +
//                "dn/RsYEONbwQSjIfMPkvxF+8HQ==\n" +
//                "-----END PRIVATE KEY-----"));
//
//        System.out.println(RSAPSSSHA256Verify("eyJhbGciOiJQUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0","iOeNU4dAFFeBwNj6qdhdvm-IvDQrTa6R22lQVJVuWJxorJfeQww5Nwsra0PjaOYhAMj9jNMO5YLmud8U7iQ5gJK2zYyepeSuXhfSi8yjFZfRiSkelqSkU19I-Ja8aQBDbqXf2SAWA8mHF8VS3F08rgEaLCyv98fLLH4vSvsJGf6ueZSLKDVXz24rZRXGWtYYk_OYYTVgR1cg0BLCsuCvqZvHleImJKiWmtS0-CymMO4MMjCy_FIl6I56NqLE9C87tUVpo1mT-kbg5cHDD8I7MjCW5Iii5dethB4Vid3mZ6emKjVYgXrtkOQ-JyGMh6fnQxEFN1ft33GX2eRHluK9eg","-----BEGIN PUBLIC KEY-----\n" +
//                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAu1SU1LfVLPHCozMxH2Mo\n" +
//                "4lgOEePzNm0tRgeLezV6ffAt0gunVTLw7onLRnrq0/IzW7yWR7QkrmBL7jTKEn5u\n" +
//                "+qKhbwKfBstIs+bMY2Zkp18gnTxKLxoS2tFczGkPLPgizskuemMghRniWaoLcyeh\n" +
//                "kd3qqGElvW/VDL5AaWTg0nLVkjRo9z+40RQzuVaE8AkAFmxZzow3x+VJYKdjykkJ\n" +
//                "0iT9wCS0DRTXu269V264Vf/3jvredZiKRkgwlL9xNAwxXFg0x/XFw005UWVRIkdg\n" +
//                "cKWTjpBP2dPwVZ4WWC+9aGVd+Gyn1o0CLelf4rEjGoXbAAEgAqeGUxrcIlbjXfbc\n" +
//                "mwIDAQAB\n" +
//                "-----END PUBLIC KEY-----"));
//    }



}