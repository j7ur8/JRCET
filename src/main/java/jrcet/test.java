package jrcet;

import java.util.Arrays;

public class test {

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

    public static void main(String[] args) {
        String strings = "#输入文件地址或者字符串".replace("",",");
        System.out.println(strings);
    }
}
