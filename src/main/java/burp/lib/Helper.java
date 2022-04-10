package burp.lib;

import jrcet.diycomponents.DiyJAddLabel;
import jrcet.diycomponents.DiyJTabLabel;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static jrcet.Main.JrcetComponentList;

public class Helper {

    /*
    组件类函数
     */
    private static int deep = 1;

    //寻找指定名字的组件，并返回该组件的Panel
    public static JComponent getComponent(JComponent rootComponent, String tComponentName){

        if (Objects.equals(rootComponent.getName(), tComponentName)){
            return rootComponent;
        }

        for( Component i : rootComponent.getComponents()) {
            if (Objects.equals(i.getName(), tComponentName)){
                return (JComponent) i;
            }
            switch (Arrays.asList(String.valueOf(i.getClass()).split("^([^.]*\\.)*")).get(1)) {
                case "JPanel":
                case "JList":
                    JComponent cj = getComponent((JComponent) i,tComponentName);
                    if(cj!=null) return cj;
                    break;
                case "DiyJAddLabel":
                case "DiyJTabLabel":
                    String[] cn = i.getName().split("(?=[A-Z])");
                    StringBuilder k= new StringBuilder();
                    for(int l=1; l<cn.length;l++){
                        k.append(cn[l].length()==1?cn[l]:(cn[l-1].length()==1?cn[l]:""));
                    }
                    if(k.toString().equals("")) k.append(cn[cn.length-2]);

                    JComponent cd = getComponent(getRenderedComponent(i, cn, k),tComponentName);
                    if(cd!=null) return cd;
                    break;
                case "RTextScrollPane":
                case "JScrollPane":
                    JComponent cs = getComponent(((JScrollPane)i).getViewport(),tComponentName);
                    if(cs!=null) return cs;
                    break;
            }
        }
        return null;
    }

    // 遍历传入组件下的全部组件
    public static void travelComponent(JComponent tComponent){

        if(deep==1){
            JrcetComponentList.add(String.join("", tComponent.getName()));
        }

        for( Component i : tComponent.getComponents()){

            switch (Arrays.asList(String.valueOf(i.getClass()).split("^([^.]*\\.)*")).get(1)){
                case "JPanel":
                    JrcetComponentList.add(String.join("", Collections.nCopies(deep, "    "))+i.getName());
                    deep+=1;
                    travelComponent((JComponent) i);
                    deep-=1;
                    break;
                case "DiyJAddLabel":
                case "DiyJTabLabel" :
                    JrcetComponentList.add(String.join("", Collections.nCopies(deep, "    "))+i.getName());
                    String[] cn = i.getName().split("(?=[A-Z])");
                    StringBuilder k= new StringBuilder();
                    for(int l=1; l<cn.length;l++){
                        k.append(cn[l].length()==1?cn[l]:(cn[l-1].length()==1?cn[l]:""));
                    }

                    JComponent c = getRenderedComponent(i, cn, k);
                    JrcetComponentList.add(String.join("", Collections.nCopies(deep+1, "    "))+c.getName());
                    deep+=2;
                    travelComponent(c);
                    deep-=2;
                    break;
                case "RTextScrollPane":
                case "JScrollPane":
                    JComponent viewPanel = (JComponent) ((JScrollPane)i).getViewport().getComponent(0);
                    JrcetComponentList.add(String.join("", Collections.nCopies(deep, "    "))+viewPanel.getName()+"("+i.getName()+")");
                    deep+=1;
                    travelComponent(viewPanel);
                    deep-=1;
                    break;
                case "JList":
                    JrcetComponentList.add(String.join("", Collections.nCopies(deep, "    "))+i.getName());
                    break;
            }
        }
    }

    private static JComponent getRenderedComponent(Component i, String[] cn, StringBuilder k) {
        JComponent c;
        try{
            c = ((DiyJTabLabel) i).getPanel();
        }catch (Exception e){
            c = ((DiyJAddLabel) i).getPanel();
        }
        return c;
    }

    //实现类似Tree命令效果的图形显示
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

//    public static
    /*
    文件类函数
     */

    public static int CharCount(String srcText, String findText) {
        int count = 0;
        Pattern p = Pattern.compile(findText);
        Matcher m = p.matcher(srcText);
        while (m.find()) {
            count++;
        }
        return count;
    }

    //读取文件并返回字符串
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
