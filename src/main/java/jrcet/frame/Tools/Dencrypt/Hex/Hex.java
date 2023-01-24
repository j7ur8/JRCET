package jrcet.frame.Tools.Dencrypt.Hex;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Objects;

public class Hex {

    private static final String hexString = "0123456789ABCDEFabcdef";
    public static HashMap<String, String > SeparatorMap = new HashMap<String, String>() {
        {
            put("换行","\n");
            put("制表","\t");
            put("逗号",",");
            put("空格"," ");
            put("空白", "");
        }
    };


    public static String encrypt(String text, String inputSeparator, String outputSeparator) {
        String hexRaw = String.format("%x", new BigInteger(1, text.replace(parseSeparator(inputSeparator),"").getBytes(StandardCharsets.UTF_8)));
        char[] hexRawArr = hexRaw.toCharArray();
        StringBuilder hexFmtStr = new StringBuilder();

        for (int i = 0; i < hexRawArr.length; i++) {
            hexFmtStr.append(hexRawArr[i]).append(hexRawArr[++i]).append(parseSeparator(outputSeparator));
        }

        return hexFmtStr.toString();
    }

    public static String decrypt(String text, String inputSeparator, String outputSeparator) {

        inputSeparator = parseSeparator(inputSeparator);
        outputSeparator = parseSeparator(outputSeparator);
        String hexString =text.replace(inputSeparator,"");
        String resString = new String(hexToByteArray(hexString), StandardCharsets.UTF_8);

        if(Objects.equals(outputSeparator, "")){
            return resString.replace("",outputSeparator);
        }

        return resString.replace("",outputSeparator).substring(1);
//        byte[] byteArr = new byte[strArr.length - 1];
//        for (int i = 1; i < strArr.length; i++) {
//            Integer hexInt = Integer.decode("0X" + strArr[i]);
//            byteArr[i - 1] = hexInt.byteValue();
//        }

//        return new String(byteArr, StandardCharsets.UTF_8);
    }

    public static byte[] hexToByteArray(String hexString){
        int l = hexString.length();
        byte[] result;
        if (l % 2 ==1){
            l++;
            result = new byte[l/2];
            hexString="0"+hexString;
        }else{
            result = new byte[l/2];
        }

        int j=0;
        for (int i=0; i<l; i+=2){
            result[j]=(byte)Integer.parseInt(hexString.substring(i,i+2) ,16);
            j++;
        }
        return result;
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
