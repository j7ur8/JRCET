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
        ArrayList<String> JrcetComponentList = new ArrayList<>();
        ArrayList<String> JrcetTreeList = new ArrayList<>();
        BufferedReader fileBuffer = new BufferedReader(new InputStreamReader(new FileInputStream(fileLocation), StandardCharsets.UTF_8));

        while ((lineBuffer = fileBuffer.readLine()) != null) {
            JrcetComponentList.add(lineBuffer);
        }

//        System.out.println(JrcetComponentList.size()); //├─   └─ │

        for(int i=0; i<=JrcetComponentList.size()-1;i++){

            int j = i+1;
            int nowLineTabCount = CharCount(JrcetComponentList.get(i), " ");

            if(i==JrcetComponentList.size()-1){
                JrcetTreeList.add(Pattern.compile(" ([^ ]*)$").matcher(JrcetComponentList.get(i)).replaceAll(" └─$1"));
            }

            while(j<JrcetComponentList.size()) {
                int tmpTabCount = CharCount(JrcetComponentList.get(j)," ");
                if(nowLineTabCount < tmpTabCount &&  j==JrcetComponentList.size()-1){
                    JrcetTreeList.add(Pattern.compile(" ([^ ]*)$").matcher(JrcetComponentList.get(i)).replaceAll(" └─$1"));
                    break;
                }
                if(nowLineTabCount > tmpTabCount) {
                    JrcetTreeList.add(Pattern.compile(" ([^ ]*)$").matcher(JrcetComponentList.get(i)).replaceAll(" └─$1"));
                    break;
                }

                if( nowLineTabCount == tmpTabCount ) {
                    JrcetTreeList.add(Pattern.compile(" ([^ ]*)$").matcher(JrcetComponentList.get(i)).replaceAll(" ├─$1"));
                    break;
                }
                j++;
            }
        }

        for (int i=0; i<JrcetTreeList.size()-1;i++){

            int j = i+1;
            int pos = JrcetTreeList.get(i).indexOf("├");

            while (j<JrcetTreeList.size()-1){

                if(j==1 || JrcetTreeList.get(j).indexOf("│") == pos || JrcetTreeList.get(j).indexOf("└") == pos){
                    break;
                }

                if(JrcetTreeList.get(j).indexOf("├") == pos){
                    j=j+1;
                    continue;
                }

                StringBuilder lineBuilder = new StringBuilder(JrcetTreeList.get(j));
                if(!Objects.equals(JrcetTreeList.get(j), " ")){
                    JrcetTreeList.set(j,lineBuilder.replace(pos,pos+1,"│").toString());
                }
                j=j+1;
            }

        }

        for(String line:JrcetTreeList) {
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

