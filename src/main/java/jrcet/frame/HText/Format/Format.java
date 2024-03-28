package jrcet.frame.HText.Format;

import help.Helper;

import javax.swing.*;
import java.util.HashMap;
import java.util.Objects;

import static jrcet.frame.HText.Format.FormatComponent.FormatComponentPanel;

public class Format {

    public static String Fix = "";
    public static String Target = "";
    public static String Result = "";
    public static HashMap<String, String > SeparatorMap = new HashMap<>() {
        {
            put("\\n", "\n");
            put("\\t", "\t");
            put("\\0", "\0");
            put("\\r", "\r");
            put("\\r\\n", "\r\n");
        }
    };

    public static String formatting(String text){

        Fix = getKeyword();
        Target = getTarget();
        Result = getResult();

//        API.logging().output().println(Fix);
//        API.logging().output().println(Target);
//        API.logging().output().println(Result);

        parseSeparator();
        String ReturnedString = "";
        switch (getMode()) {
            case "Change Separator" -> ReturnedString = text.replace(Target, Result);
            case "Add AllFix" ->
                    ReturnedString = Fix + text.replace("\n", Fix + "\n" + Fix) + Fix;
            case "Add Suffix" -> ReturnedString = text.replace("\n", Fix + "\n") + Fix;
            case "Add Prefix" -> ReturnedString = Fix + text.replace("\n", "\n" + Fix);
            case "Remove AllFix" ->{

                ReturnedString = text.replace(Fix + "\n", "\n");

                ReturnedString = ReturnedString.replace( "\n" + Fix, "\n");

                if(ReturnedString.startsWith(Fix)){
                    ReturnedString = ReturnedString.substring(Fix.length());
                }

                if(ReturnedString.endsWith(Fix)){
                    ReturnedString = ReturnedString.substring(0,ReturnedString.length()-Fix.length());
                }

                if(ReturnedString.endsWith(Fix+"\n")){
                    ReturnedString = ReturnedString.substring(0,ReturnedString.length()-Fix.length()-2)+"\n";
                }

            }
            case "Remove Prefix" -> {
                ReturnedString = text.replace("\n" + Fix, "\n");
                if(ReturnedString.startsWith(Fix)){
                    ReturnedString = ReturnedString.substring(Fix.length());
                }
            }

            case "Remove Suffix" -> {
                ReturnedString = text.replace(Fix + "\n", "\n");
                if(ReturnedString.endsWith(Fix)){
                    ReturnedString = ReturnedString.substring(0,ReturnedString.length()-Fix.length());
                }
                if(ReturnedString.endsWith(Fix+"\n")){
                    ReturnedString = ReturnedString.substring(0,ReturnedString.length()-Fix.length()-2)+"\n";
                }
            }

        }
        return ReturnedString;
    }


    public static void parseSeparator(){
        for(String key : SeparatorMap.keySet()){
            Fix = Objects.equals(Fix, key)?SeparatorMap.get(key): Fix;
            Target = Objects.equals(Target, key)?SeparatorMap.get(key):Target;
            Result = Objects.equals(Result, key)?SeparatorMap.get(key):Result;
        }
    }

    public static JComboBox<?> getFormatMenuModeBox(){
        return (JComboBox<?>) Helper.getComponent(FormatComponentPanel,"FormatMenuModeBox");
    }

    public static String getMode(){
        return (String) getFormatMenuModeBox().getSelectedItem();
    }

    public static JTextField getFormatMenuKeywordField(){
        return (JTextField) Helper.getComponent(FormatComponentPanel,"FormatMenuKeywordField");
    }

    public static String getKeyword(){
        return getFormatMenuKeywordField().getText();
    }

    public static JTextField getFormatMenuTargetField(){
        return (JTextField) Helper.getComponent(FormatComponentPanel,"FormatMenuTargetField");
    }

    public static String getTarget(){
        return getFormatMenuTargetField().getText();
    }

    public static JTextField getFormatMenuResultField(){
        return (JTextField) Helper.getComponent(FormatComponentPanel,"FormatMenuResultField");
    }

    public static String getResult(){
        return getFormatMenuResultField().getText();
    }


}
