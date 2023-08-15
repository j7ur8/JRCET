package jrcet.frame.Dencrypt.Url;

import jrcet.frame.Dencrypt.Hex.Hex;

import java.util.Objects;

public class Url {

    public static String encodeAll(String string){
        String res = Hex.encrypt(string,"","%");
        return "%"+res.substring(0,res.length()-1);
    }

    public static String decode(String string){
        return Hex.decrypt(string.replace("%",""),"","");
    }

    public static void main(String[] args) {
        System.out.println(encodeAll("asadf\"'[]{};':/.,<>?!@#$%^&*()_+=-~"));
        System.out.println(decode("asdf%22%27%5b%5d%7b%7d%3b%27%3a%2f%2e%2c%3c%3e%3f%21%40%23%24%25%5e%26%2a%28%29%5f%2b%3d%2d%7e"));
    }

}
