//package jrcet.frame.tools.Dencrypt.Jwt;
//
////import com.fasterxml.jackson.databind.ObjectMapper;
////import io.jsonwebtoken.JwtBuilder;
////import io.jsonwebtoken.Jwts;
////import io.jsonwebtoken.SignatureAlgorithm;
//import jrcet.frame.tools.Dencrypt.Base.Base;
//
//import javax.crypto.SecretKey;
//import javax.crypto.spec.SecretKeySpec;
//import java.util.ArrayList;
//import java.util.HashMap;
//
//public class Jwt {
//
//
//    public static String JwtModeType = null;
//
//    public static String generateJwt(String header, String data, String secret, String mode){
//        HashMap<String, Object> headerMap = readJsonString(header);
//        HashMap<String, Object> dataMap = readJsonString(data);
//
//        JwtBuilder jwtBuilder = Jwts.builder();
//        jwtBuilder.setHeader(headerMap);
//        jwtBuilder.setClaims(dataMap);
//
//        String returnString = "";
//        SecretKey key = null;
//        switch (mode){
//            case "HS256":
//                key = generalKey(secret, mode);
//                jwtBuilder.signWith(key, SignatureAlgorithm.HS256);
//                returnString = jwtBuilder.compact();
//                break;
//        }
//
//        return returnString;
//    }
//
//    public static ArrayList<String> parseJwt(String jwt){
//
//        ArrayList<String> returnArrayList = new ArrayList<>();
//        String[] jwtStringArray = jwt.split("\\.");
//        if(jwtStringArray.length<2) return null;
//
//        try{
//            String headerJsonString = Base.decrypt(jwtStringArray[0], "Base64");
//            String dataJsonString = Base.decrypt(jwtStringArray[1], "Base64");
//            returnArrayList.add(beautyJson(headerJsonString));
//            returnArrayList.add(beautyJson(dataJsonString));
//            return returnArrayList;
//        }catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public static String beautyJson(String json){
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            Object obj = mapper.readValue(json, Object.class);
//            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
//        }catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public static HashMap<String,Object> readJsonString(String json){
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            return mapper.readValue(json, HashMap.class);
//        }catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public static SecretKey generalKey(String secret, String mode) {
//        byte[] stringKey =  secret.getBytes();
//        byte[] encodedKey = Base.b64encoder.encode(stringKey);
//        SecretKey key = null;
//        switch (mode){
//            case "HS256":
//                key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "HmacSHA256");
//                break;
//        }
//
//        return key;
//    }
//
//}
