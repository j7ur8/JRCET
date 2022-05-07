package jrcet.frame.tools.Dencrypt.Ascii;

import burp.lib.Helper;

import java.util.Arrays;

public class Ascii {

    public static String char2Ascii(String text,String flag){

        flag = flag==null?"":flag;

        String eString = Helper.getContent(text).replaceAll(flag,"");
        char[] eCharArray = eString.toCharArray();

        StringBuilder eStringBuilder = new StringBuilder();

        flag = flag.equals("") ?" ":flag;
        for(int i=0; i<eCharArray.length; i++){
            eStringBuilder.append((int)eCharArray[i]);
            if(i!=eCharArray.length-1){
                eStringBuilder.append(flag);
            }
        }

        return eStringBuilder.toString();
    }

    public static String ascii2Char(String text, String flag){

        flag = flag==null?"":flag;

        String[] eStringArray = Helper.getContent(text).split(flag);
        StringBuilder eStringBuilder = new StringBuilder();

        flag = flag.equals("") ?" ":flag;
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
