package jrcet.frame.tools.HText.Format;

import java.util.HashMap;
import java.util.Objects;

public class Format {

    public static String FormatMode="Change Separator";
    public static String FormatFixValue;
    public static String FormatSourceSeparator;
    public static String FormatDestinationSeparator;

    public static HashMap<String, String > SeparatorMap = new HashMap<String, String>() {
        {
            put("\\n","\n");
            put("\\t","\t");
            put("\\0","\0");
        }
    };

    public static String ReturnedString;

    public static void initFormat(){};

    public static String formatting(String text){

//        initSeparatorMap();
        parseInput();

        switch (FormatMode){
            case "Change Separator":
                ReturnedString = text.replace(FormatSourceSeparator,FormatDestinationSeparator);
                break;
            case "Add AllFix":
                ReturnedString = FormatFixValue + text.replace("\n",FormatFixValue+"\n"+FormatFixValue) + FormatFixValue;
                break;
            case "Add Suffix":
                ReturnedString = text.replace("\n", FormatFixValue+"\n") + FormatFixValue;
                break;
            case "Add Prefix":
                ReturnedString = FormatFixValue + text.replace("\n", "\n"+FormatFixValue);
                break;
            case "Remove AllFix":
                ReturnedString = text.substring(1,text.length()-1).replace(FormatFixValue+"\n"+FormatFixValue,"\n");
                break;
            case "Remove Prefix":
                ReturnedString =  text.substring(1).replace("\n"+FormatFixValue,"\n");
                break;
            case "Remove Suffix":
                ReturnedString = text.substring(0,text.length()-1).replace(FormatFixValue+"\n","\n");
                break;
        }
        return ReturnedString;
    }

//    public static void initSeparatorMap(){
//        SeparatorMap.put("\\n","\n");
//        SeparatorMap.put("\\t","\t");
//        SeparatorMap.put("\\0","\0");
//    }

    public static void parseInput(){
        for(String key : SeparatorMap.keySet()){
            FormatFixValue = Objects.equals(FormatFixValue, key)?SeparatorMap.get(key):FormatFixValue;
            FormatSourceSeparator = Objects.equals(FormatSourceSeparator, key)?SeparatorMap.get(key):FormatSourceSeparator;
            FormatDestinationSeparator = Objects.equals(FormatDestinationSeparator, key)?SeparatorMap.get(key):FormatDestinationSeparator;
        }

    }



}
