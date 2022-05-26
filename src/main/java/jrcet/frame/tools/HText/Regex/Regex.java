package jrcet.frame.tools.HText.Regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {

    public static String handle(String input, String regex){

        Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(input);
        StringBuilder stringBuilder = new StringBuilder();
        while(matcher.find()){
            stringBuilder.append("\n").append(matcher.group(1));
        }

        return stringBuilder.substring(1);
    }
}
