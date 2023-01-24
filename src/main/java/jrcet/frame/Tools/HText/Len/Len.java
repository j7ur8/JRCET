package jrcet.frame.Tools.HText.Len;

import jrcet.help.Helper;

import java.util.HashMap;
import java.util.Objects;

public class Len {

    public static HashMap<String, String > SeparatorMap = new HashMap<String, String>() {
        {
            put("换行","\n");
            put("空格"," ");
            put("制表","\t");
            put("逗号","\0");
            put("空白","");
        }
    };

    public static String handle(String input, String separator) {

        input = Helper.getContent(input).replace(parseSeparator(separator), "");

        return Integer.toString(input.length());
    }

    public static String parseSeparator(String separator){
        for(String key : SeparatorMap.keySet()){
            if(Objects.equals(key, separator)){
                return SeparatorMap.get(key);
            }
        }
        return separator;
    }
}
