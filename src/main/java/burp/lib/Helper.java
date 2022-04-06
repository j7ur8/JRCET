package burp.lib;

import jrcet.diycomponents.DiyJTabLabel;
import jrcet.diycomponents.DiyJTextArea.ui.rtextarea.RTextScrollPane;
import jrcet.frame.Jrcet;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class Helper {


    /*
    组件类函数
     */

//    public static JComponent findComponent(String ComponentName){
//        JComponent rootComponent = Jrcet.JrcetComponentPanel;
//        for( Component i : rootComponent.getComponents()){
//            travelComponent((JComponent)i);
////            System.out.println(i.getName());
//        }
//
//
//        return new JPanel();
//    }

    public static void travelComponent(JComponent tComponent){
        for( Component i : tComponent.getComponents()){
            JComponent ii = (JComponent) i;

//            System.out.println(Arrays.asList(String.valueOf(i.getClass()).split("^([^.]*\\.)*")).get(1));
            switch (Arrays.asList(String.valueOf(i.getClass()).split("^([^.]*\\.)*")).get(1)){
                case "JPanel":
                    System.out.println("Name: "+ii.getName());
                    travelComponent(ii);
                    break;
                case "DiyJTabLabel" :
                    System.out.println("Name: "+ii.getName());
                    List<String> r = Arrays.asList(ii.getName().split("(?=[A-Z])"));
                    JComponent j = ((DiyJTabLabel) i).getMapPanel(r.get(r.size()-2));
                    travelComponent(j);
                    break;
                case "RTextScrollPane":
                    System.out.println("Name: "+((RTextScrollPane)i).getViewport().getComponent(0).getName());
                    break;
            }

        }
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
