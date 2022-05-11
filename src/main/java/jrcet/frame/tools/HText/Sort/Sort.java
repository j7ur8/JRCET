package jrcet.frame.tools.HText.Sort;

import jrcet.lib.Helper;

import java.text.Collator;
import java.util.*;

public class Sort {

    public static String uniq(String text){
        String textContent = getContent(text);
        List<String> textContentList = new ArrayList<>(Arrays.asList(textContent.split("\n")));
        Comparator<Object> cmp = Collator.getInstance(java.util.Locale.CHINA);
        Collections.sort(textContentList,cmp);

        String returnedString = Helper.listToStringByN(textContentList);

        return returnedString;
    }

    public static String getContent(String text) {
        return Helper.isFile(text)?Helper.readFile(text):text;
    }
}
