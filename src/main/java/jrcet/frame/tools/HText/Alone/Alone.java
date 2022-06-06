package jrcet.frame.tools.HText.Alone;

import jrcet.help.Helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;

public class Alone {

    public static String removeDuplication(String text) {

        String textContent = getContent(text);

        ArrayList<String> textContentList = new ArrayList<>(Arrays.asList(textContent.split("\n")));
        LinkedHashSet<String> textContentHashSet = new LinkedHashSet<>(textContentList);
        textContentList = new ArrayList<>(textContentHashSet);
        String returnedString = Helper.listToStringByN(textContentList);

        return returnedString;
    }

    public static String getContent(String text) {
        return Helper.isFile(text)?Helper.readFile(text):text;
    }
}
