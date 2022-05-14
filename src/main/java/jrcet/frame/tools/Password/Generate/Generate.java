package jrcet.frame.tools.Password.Generate;

import jrcet.frame.tools.HText.Parsepy.Parsepy;
import jrcet.lib.Helper;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Generate {

    public static String allPassword="";
    public static String digital4Password="";
    public static String digital6Password="";
    public static String digital8Password="";
    public static String sDigital4Password="";
    public static String sDigital6Password="";
    public static String sDigital8Password="";

    public static HashMap<String, String> prefixMap;
    public static HashMap<String, ArrayList<String>> suffixMap;
    public static ArrayList<String> nameList;
    public static String[] specialChar = new String[]{"!","@","#"};


    public static void genPassword(String name){

        Parsepy.initialPYMap();
        nameList = Parsepy.parsePinYin(name);
        prefixMap = genPrefix();
        suffixMap = gerSuffix();

        genSZM4DigitalPassword();
        genSZM6DigitalPassword();
        genSZM8DigitalPassword();

    }

    public static void genSZM4DigitalPassword(){

//        for(String key: prefixMap.keySet()){
//
//        }

    }

    public static void genSZM6DigitalPassword(){

    }

    public static void genSZM8DigitalPassword(){

    }

    public static HashMap<String, String> genPrefix(){

        HashMap<String, String> prefixMap = new HashMap<>();
        StringBuilder name = new StringBuilder();
        StringBuilder nameSZM = new StringBuilder();
        for(String s: nameList){
            nameSZM.append(s.charAt(0));
            name.append(s);
        }

        prefixMap.put("SZM", nameSZM.toString());
        char[] szmCharArray = nameSZM.toString().toCharArray();
        szmCharArray[0]-=32;
        prefixMap.put("onlyFirstUpSZM", String.valueOf(szmCharArray));
        prefixMap.put("allUpSZM", prefixMap.get("onlyFirstUpSZM").toUpperCase(Locale.ROOT));

        prefixMap.put("name", name.toString());
        char[] nameCharArray = name.toString().toCharArray();
        nameCharArray[0]-=32;
        prefixMap.put("onlyFirstUpName",  String.valueOf(nameCharArray));

        return prefixMap;
    }

    public static HashMap<String, ArrayList<String>> gerSuffix(){
        //jrcet/frame/tools/Password/Generate/digital-4.txt

        HashMap<String, ArrayList<String>> suffixMap = new HashMap<>();
        ArrayList<String> digital4Content = new ArrayList<>(Arrays.asList(Helper.readStream(Generate.class.getResourceAsStream("digital-4.txt")).split("\n")));
        ArrayList<String> digital6Content = new ArrayList<>(Arrays.asList(Helper.readStream(Generate.class.getResourceAsStream("digital-6.txt")).split("\n")));
        ArrayList<String> digital8Content = new ArrayList<>(Arrays.asList(Helper.readStream(Generate.class.getResourceAsStream("digital-8.txt")).split("\n")));

        suffixMap.put("digital4", digital4Content);
        suffixMap.put("digital6", digital6Content);
        suffixMap.put("digital8", digital8Content);

        return suffixMap;
    }

}
