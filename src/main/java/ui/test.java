package ui;

import burp.lib.Helper;

import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;

public class test {

    public static void main(String[] args) throws IOException {

        String lineBuffer = "";
        String fileLocation = "C:\\Users\\J7ur8\\Desktop\\Jrcet.coffee";
        ArrayList<String> lineList = new ArrayList<>();
        ArrayList<String> renderedLineList = new ArrayList<>();
        BufferedReader fileBuffer = new BufferedReader(new InputStreamReader(new FileInputStream(fileLocation), StandardCharsets.UTF_8));

        while ((lineBuffer = fileBuffer.readLine()) != null) {
            lineList.add(lineBuffer);
        }

//        System.out.println(lineList.size()); //├─   └─ │

        for(int i=0; i<=lineList.size()-1;i++){

            int j = i+1;
            int nowLineTabCount = CharCount(lineList.get(i), " ");

            if(i==lineList.size()-1){
                renderedLineList.add(Pattern.compile(" ([^ ]*)$").matcher(lineList.get(i)).replaceAll(" └─$1"));
            }

            while(j<lineList.size()) {
                int tmpTabCount = CharCount(lineList.get(j)," ");
                if(nowLineTabCount < tmpTabCount &&  j==lineList.size()-1){
                    renderedLineList.add(Pattern.compile(" ([^ ]*)$").matcher(lineList.get(i)).replaceAll(" └─$1"));
                    break;
                }
                if(nowLineTabCount > tmpTabCount) {
                    renderedLineList.add(Pattern.compile(" ([^ ]*)$").matcher(lineList.get(i)).replaceAll(" └─$1"));
                    break;
                }

                if( nowLineTabCount == tmpTabCount ) {
                    renderedLineList.add(Pattern.compile(" ([^ ]*)$").matcher(lineList.get(i)).replaceAll(" ├─$1"));
                    break;
                }
                j++;
            }
        }

        for (int i=0; i<renderedLineList.size()-1;i++){

            int j = i+1;
            int pos = renderedLineList.get(i).indexOf("├");

            while (j<renderedLineList.size()-1){

                if(j==1 || renderedLineList.get(j).indexOf("│") == pos || renderedLineList.get(j).indexOf("└") == pos){
                    break;
                }

                if(renderedLineList.get(j).indexOf("├") == pos){
                    j=j+1;
                    continue;
                }

                StringBuilder lineBuilder = new StringBuilder(renderedLineList.get(j));
                if(!Objects.equals(renderedLineList.get(j), " ")){
                    renderedLineList.set(j,lineBuilder.replace(pos,pos+1,"│").toString());
                }
                j=j+1;
            }

        }

        for(String line:renderedLineList) {
            System.out.println(line);
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

