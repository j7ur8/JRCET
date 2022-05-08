package jrcet.frame.tools.Dencrypt.Ascii;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import burp.lib.Helper;

public class Ascii {

    public static String char2Ascii(String text){

        boolean isSingle = text.length()==1;

        String flag = isSingle?null:text.substring(0, 1);

        String eString = isSingle?text:Helper.getContent(text.substring(1));
        char[] eCharArray = eString.toCharArray();

        StringBuilder eStringBuilder = new StringBuilder();
//        eStringBuilder.append("分割符号:`").append(flag).append("`\n");
        for(int i=0; i<eCharArray.length; i++){
            eStringBuilder.append((int)eCharArray[i]);
            if(i!=eCharArray.length-1){
                eStringBuilder.append(flag);
            }
        }

        return eStringBuilder.toString();
    }

    public static String ascii2Char(String text){

        String flag = null;
        String regex = "[0-9]([^0-9])[0-9]";
        boolean isSingle = Helper.isNumeric(text);
        Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(text);

        if(matcher.find()){
            flag = matcher.group(1);
        }else if(!isSingle){
            return "无法找到分割符";
        }

        String[] eStringArray = isSingle?new String[]{text}:Helper.getContent(text).split(flag);
        StringBuilder eStringBuilder = new StringBuilder();
//        eStringBuilder.append("分割符号:`").append(flag).append("`\n");
        for(int i=0; i<eStringArray.length; i++){
            int eInt = Integer.parseInt(eStringArray[i]);
            eStringBuilder.append((char)eInt);
            if(i!=eStringArray.length-1){
                eStringBuilder.append(flag);
            }
        }

        return eStringBuilder.toString();
    }




}
