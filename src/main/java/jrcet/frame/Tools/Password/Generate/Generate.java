package jrcet.frame.Tools.Password.Generate;

import jrcet.frame.Tools.HText.Parsepy.Parsepy;
import jrcet.help.Helper;

import java.util.*;

public class Generate {


    public static String YearStart = "1950";
    public static String YearEnd = "2022";

    public static boolean SpecialChar = false;
    public static boolean Digital4Suffix = false;
    public static boolean Digital6Suffix = false;
    public static boolean Digital8Suffix = false;
    public static boolean AbbreviationPrefix = true;
    public static boolean FullNamePrefix = true;

    public static String Name;
    public static ArrayList<String> NameList;
    public static ArrayList<String> AbbreviationPrefixList;
    public static ArrayList<String> FullNamePrefixList;
    public static ArrayList<String> Digital4SuffixList;
    public static ArrayList<String> Digital6SuffixList;
    public static ArrayList<String> Digital8SuffixList;


    public static ArrayList<String> YearList;
    public static ArrayList<String> SpecialCharList = new ArrayList<>(Arrays.asList("!", "@", "#"));

    public static String[] DayMonthArray = new String[]{"0101","0102","0103","0104","0105","0106","0107","0108","0109","0110","0111","0112","0113","0114","0115","0116","0117","0118","0119","0120","0121","0122","0123","0124","0125","0126","0127","0128","0129","0130","0131","0201","0202","0203","0204","0205","0206","0207","0208","0209","0210","0211","0212","0213","0214","0215","0216","0217","0218","0219","0220","0221","0222","0223","0224","0225","0226","0227","0228","0229","0301","0302","0303","0304","0305","0306","0307","0308","0309","0310","0311","0312","0313","0314","0315","0316","0317","0318","0319","0320","0321","0322","0323","0324","0325","0326","0327","0328","0329","0330","0331","0401","0402","0403","0404","0405","0406","0407","0408","0409","0410","0411","0412","0413","0414","0415","0416","0417","0418","0419","0420","0421","0422","0423","0424","0425","0426","0427","0428","0429","0430","0501","0502","0503","0504","0505","0506","0507","0508","0509","0510","0511","0512","0513","0514","0515","0516","0517","0518","0519","0520","0521","0522","0523","0524","0525","0526","0527","0528","0529","0530","0531","0601","0602","0603","0604","0605","0606","0607","0608","0609","0610","0611","0612","0613","0614","0615","0616","0617","0618","0619","0620","0621","0622","0623","0624","0625","0626","0627","0628","0629","0630","0701","0702","0703","0704","0705","0706","0707","0708","0709","0710","0711","0712","0713","0714","0715","0716","0717","0718","0719","0720","0721","0722","0723","0724","0725","0726","0727","0728","0729","0730","0731","0801","0802","0803","0804","0805","0806","0807","0808","0809","0810","0811","0812","0813","0814","0815","0816","0817","0818","0819","0820","0821","0822","0823","0824","0825","0826","0827","0828","0829","0830","0831","0901","0902","0903","0904","0905","0906","0907","0908","0909","0910","0911","0912","0913","0914","0915","0916","0917","0918","0919","0920","0921","0922","0923","0924","0925","0926","0927","0928","0929","0930","1001","1002","1003","1004","1005","1006","1007","1008","1009","1010","1011","1012","1013","1014","1015","1016","1017","1018","1019","1020","1021","1022","1023","1024","1025","1026","1027","1028","1029","1030","1031","1101","1102","1103","1104","1105","1106","1107","1108","1109","1110","1111","1112","1113","1114","1115","1116","1117","1118","1119","1120","1121","1122","1123","1124","1125","1126","1127","1128","1129","1130","1201","1202","1203","1204","1205","1206","1207","1208","1209","1210","1211","1212","1213","1214","1215","1216","1217","1218","1219","1220","1221","1222","1223","1224","1225","1226","1227","1228","1229","1230","1231"};

    public static String result;

    public static void genPassword(String name){

        Name = name.toLowerCase();
        setNameList();
        setYearList();
        setAbbreviationPrefixList();
        setFullNamePrefixList();

        if(Digital4Suffix){
             setDigital4SuffixList();
             setPassword(Digital4SuffixList);
        }
        if(Digital6Suffix){
            setDigital6SuffixList();
            setPassword(Digital6SuffixList);
        }
        if(Digital8Suffix){
            setDigital8SuffixList();
            setPassword(Digital8SuffixList);
        }

    }

    public static void setNameList(){
        Parsepy.initialPYMap();
        NameList = Parsepy.parsePinYin(Name);
    }

    public static void setYearList(){

        YearList = new ArrayList<>();
        int yearStart = Integer.parseInt(YearStart);
        int yearEnd   = Integer.parseInt(YearEnd);

        for(int i=yearStart; i<=yearEnd; i++ ){
            YearList.add(Integer.toString(i));
        }
    }

    public static void setFullNamePrefixList(){

        FullNamePrefixList = new ArrayList<>();

        FullNamePrefixList.add(Name);
        FullNamePrefixList.add(Helper.topUpper(Name));
    }


    public static void setAbbreviationPrefixList(){

        AbbreviationPrefixList = new ArrayList<>();

        String abbreviation = Parsepy.abbreviationName(NameList);
        AbbreviationPrefixList.add(abbreviation);
        AbbreviationPrefixList.add(Helper.topUpper(abbreviation));

    }

    public static void setDigital4SuffixList(){

        Digital4SuffixList = new ArrayList<>();
        String [] digital4ArrayFromFile = Helper.readStream(Generate.class.getResourceAsStream("digital-4.txt")).split("\n");

        if(SpecialChar){
            for(String s : digital4ArrayFromFile){
                Digital4SuffixList.add(s);
                for(String specialChar : SpecialCharList){
                    Digital4SuffixList.add(s+specialChar);
                    Digital4SuffixList.add(specialChar+s);
                }
            }
            for(String s : DayMonthArray){
                Digital4SuffixList.add(s);
                for(String specialChar : SpecialCharList){
                    Digital4SuffixList.add(s+specialChar);
                    Digital4SuffixList.add(specialChar+s);
                }
            }
        }else{
            Digital4SuffixList.addAll(Arrays.asList(digital4ArrayFromFile));
            Digital4SuffixList.addAll(Arrays.asList(DayMonthArray));
        }
    }

    public static void setDigital6SuffixList(){

        Digital6SuffixList = new ArrayList<>();
        String [] digital6ArrayFromFile = Helper.readStream(Generate.class.getResourceAsStream("digital-6.txt")).split("\n");
        String tmpString;
        if(SpecialChar){
            for(String year : YearList){
                for(String date : DayMonthArray){
                    tmpString = year.substring(2,4)+date;
                    Digital6SuffixList.add(tmpString);
                    for(String specialChar : SpecialCharList){
                        Digital6SuffixList.add(tmpString+specialChar);
                        Digital6SuffixList.add(specialChar+tmpString);
                    }
                }
            }
            for(String s : digital6ArrayFromFile){
                Digital6SuffixList.add(s);
                for(String specialChar : SpecialCharList){
                    Digital6SuffixList.add(s+specialChar);
                    Digital6SuffixList.add(specialChar+s);
                }
            }
        }else{
            for(String year : YearList){
                for(String date : DayMonthArray){
                    Digital6SuffixList.add(year.substring(2,4)+date);
                }
            }
            Digital6SuffixList.addAll(Arrays.asList(digital6ArrayFromFile));
        }
    }

    public static void setDigital8SuffixList(){

        Digital8SuffixList = new ArrayList<>();
        String [] digital8ArrayFromFile = Helper.readStream(Generate.class.getResourceAsStream("digital-8.txt")).split("\n");
        String tmpString;
        if(SpecialChar){
            for(String year : YearList){
                for(String date : DayMonthArray){
                    tmpString = year+date;
                    Digital8SuffixList.add(tmpString);
                    for(String specialChar : SpecialCharList){
                        Digital8SuffixList.add(tmpString+specialChar);
                        Digital8SuffixList.add(specialChar+tmpString);
                    }
                }
            }
            for(String s : digital8ArrayFromFile){
                Digital8SuffixList.add(s);
                for(String specialChar : SpecialCharList){
                    Digital8SuffixList.add(s+specialChar);
                    Digital8SuffixList.add(specialChar+s);
                }
            }
        }else{
            for(String year : YearList){
                for(String date : DayMonthArray){
                    Digital8SuffixList.add(year.substring(2,4)+date);
                }
            }
            Digital8SuffixList.addAll(Arrays.asList(digital8ArrayFromFile));
        }
    }

    public static void setPassword(ArrayList<String> suffixList){

        StringBuilder resultBuilder = new StringBuilder();
        if(AbbreviationPrefix){
            for(String abbreviationPrefix : AbbreviationPrefixList){
                for(String suffix : suffixList){
                    resultBuilder.append(abbreviationPrefix).append(suffix).append("\n");
                }
            }
        }

        if(FullNamePrefix){
            for(String abbreviationPrefix : FullNamePrefixList){
                for(String suffix : suffixList){
                    resultBuilder.append(abbreviationPrefix).append(suffix).append("\n");
                }
            }
        }
        result = resultBuilder.toString();
    }


}
