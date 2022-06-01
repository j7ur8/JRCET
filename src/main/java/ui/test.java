package ui;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.internal.messaging.saaj.util.Base64;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jrcet.frame.tools.Dencrypt.Jwt.Jwt;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class test {
    private static final String Jwt_Secret = "111111111111111111111111";

    public static void main(String[] args) throws Exception {
// the key would be read from your application configuration instead.

        SecretKey secretKey = generalKey();

        JwtBuilder jwtBuilder = Jwts.builder().signWith(secretKey, SignatureAlgorithm.HS256);
        jwtBuilder.setHeader(Jwt.readJsonString("{\n" +
                "  \"typ\": \"JTc\",\n" +
                "  \"alg\": \"HS256\"\n" +
                "}"));
        jwtBuilder.setClaims(Jwt.readJsonString("{\n" +
                "  \"sub\": 1234567890,\n" +
                "  \"name\": \"John Doe\",\n" +
                "  \"iat\": 15162390\n" +
                "}"));

        String jwt = jwtBuilder.compact();
        System.out.println(jwt);
        parseJwt(jwt);

    }

    public static SecretKey generalKey() {
        byte[] stringKey =  Jwt_Secret.getBytes();
        byte[] encodedKey = Base64.encode(stringKey);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "HmacSHA256");
        return key;
    }

    public static void parseJwt(String jwt){
        String[] jwtStringArray = jwt.split("\\.");
        System.out.println(jwtStringArray.length);
        return ;
//        String headerJsonString = Base.decrypt(jwtStringArray[0], "Base64");
//        String dataJsonString = Base.decrypt(jwtStringArray[1], "Base64");
//        try{
//            System.out.println(beautyJson(headerJsonString));
//            System.out.println(beautyJson(dataJsonString));
//        }catch (Exception e){
//            e.printStackTrace();
//        }
    }

    public static String beautyJson(String json){
        ObjectMapper mapper = new ObjectMapper();
        try {
            Object obj = mapper.readValue(json, Object.class);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
