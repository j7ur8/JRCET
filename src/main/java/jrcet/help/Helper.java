package jrcet.help;

import jrcet.diycomponents.DiyJAddLabel;
import jrcet.diycomponents.DiyJChangeLabel;
import jrcet.diycomponents.DiyJLabel;
import jrcet.diycomponents.DiyJTabLabel;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static jrcet.Main.JrcetComponentList;

public class Helper {

    /*
    组件类函数
     */
    private static int deep = 1;

    public static int getComponentPos(JComponent rootComponent, String tComponentName){

        Component[] components = rootComponent.getComponents();
        for(int i = 0; i<components.length; i++){
            if(Objects.equals(components[i].getName(), tComponentName)){
                return i;
            }
        }
        return -1;
    }

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
                case "DiyJComboBox":
                case "RSyntaxTextArea":
                    JComponent cj = getComponent((JComponent) i,tComponentName);
                    if(cj!=null) return cj;
                    break;
                case "DiyJAddLabel":
                case "DiyJTabLabel":
                    JComponent cd = getComponent(getRenderedComponent(i),tComponentName);
                    if(cd!=null) return cd;
                    break;
                case "RTextScrollPane":
                case "JScrollPane":
                    JComponent cs = getComponent((JComponent) ((JScrollPane)i).getViewport().getView(),tComponentName);
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
                    JComponent c = getRenderedComponent(i);
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
                case "DiyJComboBox":
                case "JList":
                    JrcetComponentList.add(String.join("", Collections.nCopies(deep, "    "))+i.getName());
                    break;
            }
        }
    }

    private static JComponent getRenderedComponent(Component i) {
        JComponent c;
        try{
            c = ((DiyJTabLabel) i).getPanel();
        }catch (Exception e){
            c = ((DiyJAddLabel) i).getPanel();
        }
        return c;
    }

    public static String getContent(String text) {
        return Helper.isFile(text)?Helper.readFile(text):text;
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

    /*
    字符操作
     */

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        try {
            for (int i = 0; i < len; i += 2) {
                data[i/2] = (byte) (
                         (Character.digit(s.charAt(i), 16) << 4)
                        + Character.digit(s.charAt(i+1), 16)
                );
            }
        } catch (Exception ignored) {

        }
        return data;
    }

    public static String listToStringByN(List<String> list){
        StringBuilder textContentBuilder = new StringBuilder();
        for(int i=0;i<list.size();i++){
            textContentBuilder.append(list.get(i));
            if(i!=list.size()-1){
                textContentBuilder.append("\n");
            }
        }

        return textContentBuilder.toString();
    }




    /*
    是否为合法域名
     */
    public static boolean isDomain(String str) {
        String regex = "^((?!-)[A-Za-z0-9-]"
                + "{1,63}(?<!-)\\.)"
                + "+[A-Za-z]{2,6}";

        Pattern p = Pattern.compile(regex);

        if (str == null) {
            return false;
        }
        Matcher m = p.matcher(str);
        return m.matches();
    }



    /*
    是否合法IP
     */
    public static Boolean isIpAddress(String s){

        String regex = "(((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(s);
        return m.matches();

    }
    /*
    判断是否全为数字
     */
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }
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

    public static void set4DiyLabel(JComponent tPanel, String @NotNull [] tStrings){
        for(int i=0;i<tStrings.length;i++){
            String s = tStrings[i];
            DiyJLabel tmpLabel = new DiyJLabel(s);
            tmpLabel.setPreferredSize(new Dimension(0,30));
            tmpLabel.setName(tPanel.getName().replace("Panel","Label"+ i +"Panel"));

            tPanel.add(tmpLabel, new GridBagConstraints(
                    i-((i/4)*4), i/4,
                    1,1,
                    0.25,0,
                    GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH,
                    new Insets(0,1,2,1),
                    0,0
            ));
        }
        if(tStrings.length%4!=0){
            tPanel.add(Helper.blackPanel(),new GridBagConstraints(
                    tStrings.length%4, (tStrings.length-1)/4,
                    4-tStrings.length%4,1,
                    0.25*(4-tStrings.length%4),0,
                    GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH,
                    new Insets(0,0,0,0),
                    0,0
            ));
        }
    }

    public static void set4DiyJChangeLabel(JComponent tPanel, String @NotNull [] tStrings, HashMap<String, JComponent> stringJComponentHashMap){
        for(int i=0;i<tStrings.length;i++){
            String s = tStrings[i];
            DiyJChangeLabel tmpLabel = new DiyJChangeLabel(s);
            if(stringJComponentHashMap.containsKey(s)){
                tmpLabel.setPanel(stringJComponentHashMap.get(s));
            }
            tmpLabel.setPreferredSize(new Dimension(0,30));
            tmpLabel.setBorder(null);
            tmpLabel.setName(tPanel.getName().replace("Panel","Label"+ i +"Panel"));

            tPanel.add(tmpLabel, new GridBagConstraints(
                    i-((i/4)*4), i/4,
                    1,1,
                    0.25,0,
                    GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH,
                    new Insets(0,1,2,1),
                    0,0
            ));
        }
        if(tStrings.length%4!=0){
            tPanel.add(Helper.blackPanel(),new GridBagConstraints(
                    tStrings.length%4, (tStrings.length-1)/4,
                    4-tStrings.length%4,1,
                    0.25*(4-tStrings.length%4),0,
                    GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH,
                    new Insets(0,0,0,0),
                    0,0
            ));
        }
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

    public static void appendWriteFile(String filename, String fileContent){
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(filename,true));
            out.write(fileContent);
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFile(String filename, int maxLength){
        StringBuilder content = new StringBuilder();
        try {
            FileInputStream fis = new FileInputStream(filename);
            if(fis.available()>maxLength) return "文件太大";
            BufferedReader in = new BufferedReader(new InputStreamReader(fis, StandardCharsets.UTF_8));
            String line = "";
            while ((line = in.readLine()) != null) {
                content.append(line).append("\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }


    public static String topUpper(String word){
        char[] charArray = word.toCharArray();
        charArray[0]-=32;
        return String.valueOf(charArray);
    }

    public static String readStream(InputStream in) {
        try {
            //<1>创建字节数组输出流，用来输出读取到的内容
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //<2>创建缓存大小
            byte[] buffer = new byte[1024]; // 1KB
            //每次读取到内容的长度
            int len = -1;
            //<3>开始读取输入流中的内容
            while ((len = in.read(buffer)) != -1) { //当等于-1说明没有数据可以读取了
                baos.write(buffer, 0, len);   //把读取到的内容写到输出流中
            }
            //<4> 把字节数组转换为字符串
            String content = baos.toString();
            //<5>关闭输入流和输出流
            in.close();
            baos.close();
            //<6>返回字符串结果
            return content;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public static boolean isFile(String filename){
        File file = new File(filename);

        return file.isFile();
    }

    public static boolean isDict(String filename){
        File file = new File(filename);

        return file.isDirectory();
    }

    public static void setConstraints(HashMap<String,GridBagConstraints> tHasMap, JComponent pComponent, JComponent tComponent, GridBagConstraints tGBC){
        tHasMap.put(tComponent.getName(), tGBC);
        pComponent.add(tComponent, tGBC);
    }

    public static JComponent blackPanel(){
        JPanel blackPanel = new JPanel();
        blackPanel.setName("HelperBlackPanel");
        blackPanel.setPreferredSize(new Dimension(0,0));

        return blackPanel;
    }

    public static JComponent blackPanel(Color color){
        JPanel blackPanel = new JPanel();
        blackPanel.setName("HelperBlackPanel");
        blackPanel.setBackground(color);
        blackPanel.setPreferredSize(new Dimension(0,0));
        return blackPanel;
    }

    public static String list2String(List<String> list, String separator){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0; i<list.size()-1; i++){
            stringBuilder.append(list.get(i)).append(separator);
        }
        stringBuilder.append(list.get(list.size()-1));

        return stringBuilder.toString();
    }

}
