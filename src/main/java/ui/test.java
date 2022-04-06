package ui;

import burp.lib.Helper;

import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;

public class test {

    public static void main(String[] args) throws IOException {

        String lineBuffer = "";
        String fileLocation = "C:\\Users\\J7ur8\\Desktop\\Jrcet.coffee";
        ArrayList<String> lineList = new ArrayList<>();
        ArrayList<String> renderedLineList1 = new ArrayList<>();
        ArrayList<String> renderedLineList2 = new ArrayList<>();
        BufferedReader fileBuffer = new BufferedReader(new InputStreamReader(new FileInputStream(fileLocation), StandardCharsets.UTF_8));

        while ((lineBuffer = fileBuffer.readLine()) != null) {
            lineList.add(lineBuffer);
        }

        System.out.println(lineList.size());
        for(int i=0; i<lineList.size()-1;i++){
            int j = i+1;
            int nowLineTabCount = CharCount(lineList.get(i), "\t");
            //├─   └─ │
            while(j<lineList.size()-1) {
                int tmpTabCount = CharCount(lineList.get(j),"\t");
                if(nowLineTabCount > tmpTabCount) {
                    renderedLineList1.add(Pattern.compile("\t([^\t]*)$").matcher(lineList.get(i)).replaceAll("\t└─$1"));
                    break;
                }

                if( nowLineTabCount == tmpTabCount ) {
//                    if(j==i+1){
                    renderedLineList1.add(Pattern.compile("\t([^\t]*)$").matcher(lineList.get(i)).replaceAll("\t├─$1"));
                    break;
//                    }
//                    renderedLineList1.add(Pattern.compile("\t([^\t]*)$").matcher(lineList.get(i)).replaceAll("\t└─$1"));
//                    break;
                }
                j++;

            }
        }

        for (int i=0; i<renderedLineList1.size()-1;i++){

        }
        for(String c:renderedLineList1){
            System.out.println(c);
        }

    }

    public static int CharCount(String srcText, String findText) {
        int count = 0;
        Pattern p = Pattern.compile(findText);
        Matcher m = p.matcher(srcText);
        while (m.find()) {
            count++;
        }
        return count;
    }
}

