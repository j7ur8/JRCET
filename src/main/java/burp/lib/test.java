package burp.lib;

import java.io.UnsupportedEncodingException;
import java.util.regex.*;

public class test {

    final static java.util.Base64.Encoder encoder = java.util.Base64.getEncoder();
    final static java.util.Base64.Decoder decoder = java.util.Base64.getDecoder();

    public static void main(String[] args) {

        String username = "Miracle Lu哈哈哈na";
        String password = "p@你好";

        // 加密
        System.out.println("====  [加密后] 用户名/密码  =====");
        System.out.println(test.encode(username));
        System.out.println(test.encode(password));

        // 解密
        String encodeStr = test.encode(username);
        StringBuilder newEncodeStr = new StringBuilder();
        for(int x=0;x<encodeStr.length();x++){
            String ch = Character.toString(encodeStr.charAt(x));
            if(Pattern.matches("[a-zA-Z0-9+/]",ch)){
                newEncodeStr.append(ch);
            };
        }
//        System.out.println(newEncodeStr);
        System.out.println("\n====  [解密后] 用户名/密码  =====");
        System.out.println(test.decode(String.valueOf(newEncodeStr)));
        System.out.println(test.decode("cEDkvaDlpb0"));
    }

    /**
     * 给字符串加密
     * @param text
     * @return
     */
    public static String encode(String text) {
        byte[] textByte = new byte[0];
        try {
            textByte = text.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String encodedText = encoder.encodeToString(textByte);
        return encodedText;
    }

    /**
     * 将加密后的字符串进行解密
     * @param encodedText
     * @return
     */
    public static String decode(String encodedText) {
        String text = null;
        try {
            text = new String(decoder.decode(encodedText), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return text;
    }


}
