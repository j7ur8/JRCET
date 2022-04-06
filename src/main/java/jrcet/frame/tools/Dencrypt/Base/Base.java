package jrcet.frame.tools.Dencrypt.Base;

/**
 * @实现Base家族加解密的类
 */

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

public class Base {

    final static java.util.Base64.Encoder b64encoder = java.util.Base64.getEncoder();
    final static java.util.Base64.Decoder b64decoder = java.util.Base64.getDecoder();


    public static String door(String caseString, String type, String text) {
        String result = null;
        try {
            Method targetMethod = Base.class.getMethod(caseString.toLowerCase() + type, String.class);
            result = (String) targetMethod.invoke(new Base(), text);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
    public String b16encode(String text){
        return "b16encode";
    }

    public String b16decode(String text){
        return "b16decode";
    }

    public String b32encode(String text){
        return "b32encode";
    }

    public String b32decode(String text){
        return "b32decode";
    }

    public String b58encode(String text){
        return "b58encode";
    }

    public String b58decode(String text){
        return "b58decode";
    }

    public String b64encode(String text){
        byte[] textByte;
        textByte = text.getBytes(StandardCharsets.UTF_8);
        return b64encoder.encodeToString(textByte);
    }

    public String b64decode(String text){
        StringBuilder newEncodeStr = new StringBuilder();
        for(int x=0;x<text.length();x++){
            String ch = Character.toString(text.charAt(x));
            if(Pattern.matches("[a-zA-Z0-9+/]",ch)){
                newEncodeStr.append(ch);
            };
        }
        text = new String(b64decoder.decode(String.valueOf(newEncodeStr)), StandardCharsets.UTF_8);
        return text;
    }

    public String b85encode(String text){
        return "b85encode";
    }

    public String b85decode(String text){
        return "b85decode";
    }

    public String b91encode(String text){
        return "b91encode";
    }

    public String b91decode(String text){
        return "b91decode";
    }

    public String b92encode(String text){
        return "b92encode";
    }

    public String b92decode(String text){
        return "b92decode";
    }
//    private p64
}
