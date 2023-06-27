package jrcet.frame.Tools.Password;

import jrcet.frame.Tools.HText.Parsepy.Parsepy;
import jrcet.help.Helper;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Password {

    //必须包含大写字母
    private static boolean ifCapitalLetter;
    //必须包含数字
    private static boolean ifDigital;
    //必须包含特殊符号
    private static boolean ifSpecialChar;
    //不能包含账号信息（大小写不敏感）
    private static boolean ifAccount1;
    //不能包含账号信息（大小写敏感）
    private static boolean ifAccount2;
    //不能包含连续字符（横向）
    private static boolean ifSequence1;
    //不能包含连续字符（斜向）
    private static boolean ifSequence2;
    //是否保存字典为文件
    private static boolean ifSave;
    //密码长度
    private static String passwordLength;
    //账号名
    private static String accountName;
    //出生日
    private static String birthday;
    //手机号
    private static String phone;
    //身份证
    private static String idcard;
    //地址1
    private static String oneSavePath;
    //地址2
    private static String allSavePath;

    private static final ArrayList<String> specialCharList = new ArrayList<>(Arrays.asList("@", "#", "!"));

    private static boolean filterValue(String password){

        int pwdLen = password.length();
        boolean upper=false,digital=false,special=false;
        for (char c : password.toCharArray()) {
            if(Character.isDigit(c)){
                digital=true;
                continue;
            }
            if(Character.isUpperCase(c)){
                upper=true;
                continue;
            }
            if(specialCharList.contains(String.valueOf(c))){
                special=true;
            }
        }
        if(ifCapitalLetter && !upper) return false;
        if(ifDigital && !digital) return false;
        if(ifSpecialChar && !special) return false;
        if(ifAccount1 && password.contains(accountName)) return false;
        if(ifAccount2 && password.toLowerCase().contains(accountName.toLowerCase())) return false;

        String[] passwordLengths = passwordLength.split("-");
        if(pwdLen<Integer.parseInt(passwordLengths[0]) || Integer.parseInt(passwordLengths[1])<pwdLen) return false;

        return true;
    }


    public static void writeArea(String v, JTextArea tTextArea){
        ArrayList<String> dictArray = generate(v);
        StringBuilder dictString= new StringBuilder();
        for(String s: dictArray){
            dictString.append(s).append("\n");
        }

        tTextArea.setText(String.valueOf(dictString));
    }

    public static void setRule(boolean b1,boolean b2,boolean b3,boolean b4,boolean b5,boolean b6,boolean b7, boolean b8,String s1,String s2,String s3,String s4,String s5,String a1,String a2){
        ifCapitalLetter=b1;
        ifDigital=b2;
        ifSpecialChar=b3;
        ifAccount1=b4;
        ifAccount2=b5;
        ifSequence1=b6;
        ifSequence2=b7;
        ifSave=b8;
        passwordLength=s1;
        accountName=s2;
        birthday=s3;
        phone=s4;
        idcard=s5;
        oneSavePath=a1;
        allSavePath=a2;
    }

    public static ArrayList<String> generate(String v){

        File file = new File(v);
        ArrayList<String> dictList = new ArrayList<>();

        if (!file.exists()) {
            ArrayList<String> nameList = parseName(v);


            ArrayList<String> digital4List = new ArrayList<>();
            ArrayList<String> digital6List = new ArrayList<>();
            ArrayList<String> digital8List = new ArrayList<>();
            ArrayList<String> sequenceWordList = new ArrayList<>();
            try {
                String line;
                BufferedReader reader;
                InputStream digital4InputStream, digital6InputStream, digital8InputStream,sequenceWordInputStream;

                digital4InputStream = Password.class.getResourceAsStream("digital-4.txt");
                assert digital4InputStream != null;
                reader = new BufferedReader(new InputStreamReader(digital4InputStream));
                while ((line = reader.readLine()) != null) {
                    digital4List.add(line);
                }
                digital4InputStream.close();

                digital6InputStream = Password.class.getResourceAsStream("digital-6.txt");
                assert digital6InputStream != null;
                reader = new BufferedReader(new InputStreamReader(digital6InputStream));
                while ((line = reader.readLine()) != null) {
                    digital6List.add(line);
                }
                digital6InputStream.close();

                digital8InputStream = Password.class.getResourceAsStream("digital-6.txt");
                assert digital8InputStream != null;
                reader = new BufferedReader(new InputStreamReader(digital8InputStream));
                while ((line = reader.readLine()) != null) {
                    digital8List.add(line);
                }
                digital8InputStream.close();

                sequenceWordInputStream = Password.class.getResourceAsStream("sequenceWord.txt");
                assert sequenceWordInputStream != null;
                reader = new BufferedReader(new InputStreamReader(sequenceWordInputStream));
                while ((line = reader.readLine()) != null) {
                    sequenceWordList.add(line);
                }
                sequenceWordInputStream.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
            generateSequenceWordPassword(nameList,sequenceWordList,dictList);
            generateDigitalPassword(nameList,digital4List,dictList);
            generateDigitalPassword(nameList,digital6List,dictList);
            generateDigitalPassword(nameList,digital8List,dictList);
        }
        return dictList;
    }

    private static ArrayList<String> parseName(String name){
        ArrayList<String> nameList = new ArrayList<>();

        String nameSimple = Parsepy.getPYInitial(name).replace("\n","");
        nameList.add(nameSimple);
        nameList.add(Helper.firstWordUp(nameSimple));
        nameList.add(name);
        nameList.add(Helper.firstWordUp(name));
        return nameList;
    }

    private static void generateDigitalPassword(ArrayList<String> nameList,ArrayList<String> suffixList,ArrayList<String> resultList){
        String[] tmpPasswordArray;

        for(String name: nameList){
            for(String suffix:suffixList){
                for(String special: specialCharList){
                    tmpPasswordArray = new String[] {name+suffix+special, name+special+suffix};
                    for(String tmpPassword:tmpPasswordArray){
                        if(filterValue(tmpPassword)){
                            resultList.add(tmpPassword);
                        }
                    }
                }
            }
        }
    }

    private static void generateSequenceWordPassword(ArrayList<String> nameList,ArrayList<String> sequenceList,ArrayList<String> resultList){
        String tmpPassword;
        for(String name: nameList) {
            for (String suffix : sequenceList) {
                tmpPassword = name+suffix;
                if(filterValue(tmpPassword)){
                    resultList.add(tmpPassword);
                }
            }
        }
    }

}
