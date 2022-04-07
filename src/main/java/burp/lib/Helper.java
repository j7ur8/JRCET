package burp.lib;

import jrcet.diycomponents.DiyJAddLabel;
import jrcet.diycomponents.DiyJTabLabel;
import jrcet.diycomponents.DiyJTextArea.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helper {

    /*
    组件类函数
     */
    private static int deep = 1;
    public static ArrayList<String> JrcetComponentList =new ArrayList<>();

    public static void travelComponent(JComponent tComponent){

        if(deep==1){
//            System.out.println(String.join("", tComponent.getName()));
            JrcetComponentList.add(String.join("", tComponent.getName()));
        }

        if(tComponent instanceof JScrollPane){
            Component s = ((JScrollPane)tComponent).getViewport().getComponent(0);
//            System.out.println(String.join("", Collections.nCopies(deep, "    "))+s.getName());
            JrcetComponentList.add(String.join("", Collections.nCopies(deep, "    "))+s.getName());
            deep+=1;
            travelComponent((JComponent)s);
            deep-=1;
        }

        for( Component i : tComponent.getComponents()){
            JComponent ii = (JComponent) i;
            switch (Arrays.asList(String.valueOf(i.getClass()).split("^([^.]*\\.)*")).get(1)){
                case "JPanel":
//                    System.out.println(String.join("", Collections.nCopies(deep, "    "))+ii.getName());
                    JrcetComponentList.add(String.join("", Collections.nCopies(deep, "    "))+ii.getName());
                    deep+=1;
                    travelComponent(ii);
                    deep-=1;
                    break;
                case "DiyJAddLabel":
//                    System.out.println(String.join("", Collections.nCopies(deep, "    "))+ii.getName());
                    JrcetComponentList.add(String.join("", Collections.nCopies(deep, "    "))+ii.getName());
                    List<String> ar = Arrays.asList(ii.getName().split("(?=[A-Z])"));
                    JComponent aj = ((DiyJAddLabel) i).getMapPanel(ar.get(ar.size()-2));
//                    System.out.println(String.join("", Collections.nCopies(deep+1, "    "))+aj.getName());
                    JrcetComponentList.add(String.join("", Collections.nCopies(deep+1, "    "))+aj.getName());
                    deep+=2;
                    travelComponent(aj);
                    deep-=2;
                    break;
                case "DiyJTabLabel" :
//                    System.out.println(String.join("", Collections.nCopies(deep, "    "))+ii.getName());
                    JrcetComponentList.add(String.join("", Collections.nCopies(deep, "    "))+ii.getName());
                    String[] tr = ii.getName().split("(?=[A-Z])");
                    StringBuilder k= new StringBuilder(); int flag = 0;
                    for(String ttr :  tr){
                        if(ttr.length()==1){
                            k.append(ttr);
                            flag=1;
                        }else if(flag==1){
                            k.append(ttr);
                            flag=0;
                        }
                    }
                    JComponent tj = ((DiyJTabLabel) i).getMapPanel( k.length()==0? Arrays.asList(tr).get(Arrays.asList(tr).size()-2) : k.toString() );
//                    System.out.println(String.join("", Collections.nCopies(deep+1, "    "))+tj.getName());
                    JrcetComponentList.add(String.join("", Collections.nCopies(deep+1, "    "))+tj.getName());
                    deep+=2;
                    travelComponent(tj);
                    deep-=2;
                    break;
                case "RTextScrollPane":
//                    System.out.println(String.join("", Collections.nCopies(deep, "    "))+((RTextScrollPane)i).getViewport().getComponent(0).getName());
                    JrcetComponentList.add(String.join("", Collections.nCopies(deep, "    "))+((RTextScrollPane)i).getViewport().getComponent(0).getName());
                    break;
            }
        }
    }

    public static ArrayList<String> treeComponent(ArrayList<String> targetList){

        ArrayList<String> JrcetTreeList =new ArrayList<>();
        for(int i=0; i<=targetList.size()-1;i++){
            int j = i+1;
            int nowLineTabCount = CharCount(targetList.get(i), " ");

            if(i==targetList.size()-1){
                JrcetTreeList.add(Pattern.compile(" ([^ ]*)$").matcher(targetList.get(i)).replaceAll(" └─$1"));
            }

            while(j<targetList.size()) {
                int tmpTabCount = CharCount(targetList.get(j)," ");
                if(nowLineTabCount < tmpTabCount &&  j==targetList.size()-1){
                    JrcetTreeList.add(Pattern.compile(" ([^ ]*)$").matcher(targetList.get(i)).replaceAll(" └─$1"));
                    break;
                }
                if(nowLineTabCount > tmpTabCount) {
                    JrcetTreeList.add(Pattern.compile(" ([^ ]*)$").matcher(targetList.get(i)).replaceAll(" └─$1"));
                    break;
                }

                if( nowLineTabCount == tmpTabCount ) {
                    JrcetTreeList.add(Pattern.compile(" ([^ ]*)$").matcher(targetList.get(i)).replaceAll(" ├─$1"));
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

        return JrcetTreeList;
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
    /*
    文件类函数
     */
    public static String readFile(String filename) {

        StringBuilder content = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(filename), StandardCharsets.UTF_8));
            String line = "";
            while ((line = in.readLine()) != null) {
                content.append(line).append("\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    public static void writeFile(String Content,String filename) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(filename));
        out.write(Content);
        out.close();
    }

    public static boolean isFile(String filename){
        File file = new File(filename);

        return file.isFile();
    }



}
