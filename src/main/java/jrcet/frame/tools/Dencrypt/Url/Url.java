package jrcet.frame.tools.Dencrypt.Url;

import jrcet.frame.tools.Dencrypt.Hex.Hex;

import java.util.Objects;

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

    public static String encodeAll(String string){

        String[] strings = string.split("");
        StringBuilder stringBuilder = new StringBuilder();
        for(String s: strings){
            stringBuilder.append("%").append(Hex.encrypt(s, "", ""));
        }

        return stringBuilder.toString();
    }

    public static String decode(String string){
        String[] strings = string.split("");
        String s,tmp;
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0; i< strings.length; i++){
            s = strings[i];
            if(Objects.equals(s, "%")){
                tmp = strings[i+1]+strings[i+2];
                stringBuilder.append(Hex.decrypt(tmp,"",""));
                i+=2;
            }else{
                stringBuilder.append(s);
            }
        }

        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        System.out.println(encodeAll("asadf\"'[]{};':/.,<>?!@#$%^&*()_+=-~"));
        System.out.println(decode("asdf%22%27%5b%5d%7b%7d%3b%27%3a%2f%2e%2c%3c%3e%3f%21%40%23%24%25%5e%26%2a%28%29%5f%2b%3d%2d%7e"));
    }

}
