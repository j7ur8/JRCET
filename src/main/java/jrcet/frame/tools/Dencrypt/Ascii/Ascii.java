package jrcet.frame.tools.Dencrypt.Ascii;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jrcet.lib.Helper;

public class Ascii {

    public static HashMap<String, String > SeparatorMap = new HashMap<String, String>() {
        {
            put("换行","\n");
            put("制表","\t");
            put("逗号",",");
            put("空格"," ");
        }
    };


    public static String encrypt(String text, String separator, String separator2){

        boolean isSingle = text.length()==1;
        
        String eStringArray = isSingle?text:Helper.getContent(text).replace(parseSeparator(separator),"");
        char[] eCharArray = eStringArray.toCharArray();
        StringBuilder eStringBuilder = new StringBuilder();
        for(int i=0; i<eCharArray.length; i++){
            eStringBuilder.append((int)eCharArray[i]);
            if(i!=eCharArray.length-1){
                eStringBuilder.append(parseSeparator(separator2));
            }
        }

        return eStringBuilder.toString();
    }

    public static String decrypt(String text, String separator, String separator2){

        boolean isSingle = Helper.isNumeric(text);


        String[] eStringArray = isSingle?new String[]{text}:Helper.getContent(text).split(parseSeparator(separator));
        StringBuilder eStringBuilder = new StringBuilder();
        for(int i=0; i<eStringArray.length; i++){
            int eInt = Integer.parseInt(eStringArray[i]);
            eStringBuilder.append((char)eInt);
            if(i!=eStringArray.length-1){
                eStringBuilder.append(parseSeparator(separator2));
            }
        }

        return eStringBuilder.toString();
    }

    public static String parseSeparator(String separator){
        for(String key : SeparatorMap.keySet()){
            if(Objects.equals(separator,key)){
                return SeparatorMap.get(key);
            }
        }
        return separator;
    }


}
