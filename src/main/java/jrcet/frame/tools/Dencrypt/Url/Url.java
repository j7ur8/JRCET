package jrcet.frame.tools.Dencrypt.Url;

import jrcet.frame.tools.Dencrypt.Hex.Hex;

public class Url {

    public static String encode(String string){
        String[] strings = string.split("");
        StringBuilder stringBuilder = new StringBuilder();
        for(String s: strings){
            if("\"'[]{};':/.,<>?!@#$%^&*()_+=-~`|\\".contains(s)){
                stringBuilder.append("%").append(Hex.encrypt(s, "", ""));
            }else{
                stringBuilder.append(s);
            }
        }

        return stringBuilder.toString();
    }
}
