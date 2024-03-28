package jrcet.frame.HText.Len;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class Len {

    public static HashMap<String, String > SeparatorMap = new HashMap<>() {
        {
            put("换行", "\n");
            put("空格", " ");
            put("制表", "\t");
            put("逗号", "\0");
            put("空白", "");
        }
    };

    public static String length(String input) {

//        input = Helper.getContent(input).replace(parseSeparator(separator), "");

        return String.format("字符长度：%s\n字节长度：%s",wordLength(input),byteLength(input));

    }

//    public static String parseSeparator(String separator){
//        for(String key : SeparatorMap.keySet()){
//            if(Objects.equals(key, separator)){
//                return SeparatorMap.get(key);
//            }
//        }
//        return separator;
//    }

    public static String wordLength(String input){
        return Integer.toString(input.length());
    }

    public static String byteLength(String input){
        return Integer.toString(input.getBytes(StandardCharsets.UTF_8).length);
    }
}
