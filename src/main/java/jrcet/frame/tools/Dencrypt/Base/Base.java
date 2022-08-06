package jrcet.frame.tools.Dencrypt.Base;

import java.nio.charset.StandardCharsets;

public class Base {

    public final static java.util.Base64.Encoder b64encoder = java.util.Base64.getEncoder();
    public final static java.util.Base64.Decoder b64decoder = java.util.Base64.getDecoder();


    public static String encrypt(String text, String mode){
        String returnString = "";
        switch (mode){
            case "Base64":
                returnString = new String(b64encoder.encode(text.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
                break;
        }

        return returnString;
    }

    public static String decrypt(String text, String mode){

        String returnString = "";
        switch (mode){
            case "Base64":
                returnString = new String(b64decoder.decode(text.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
                break;
        }

        return returnString;
    }



}
