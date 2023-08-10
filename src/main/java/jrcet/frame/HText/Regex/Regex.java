package jrcet.frame.HText.Regex;

import jrcet.help.Helper;

import javax.swing.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static jrcet.frame.HText.Regex.RegexComponent.RegexComponentPanel;

public class Regex {

    public static String match(String input, String regex){

        input = Helper.getContent(input);
        Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(input);
        StringBuilder stringBuilder = new StringBuilder();
        while(matcher.find()){
            stringBuilder.append("\n").append(matcher.group(1));
        }

        return stringBuilder.substring(1);
    }

    public static String match(String input){
        return match(input,"("+getRuleField().getText()+")");
    }

    public static JTextField getRuleField(){
        return (JTextField) Helper.getComponent(RegexComponentPanel,"RegexMenuRuleField");
    }

}
