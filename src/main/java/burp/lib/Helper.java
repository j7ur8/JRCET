package burp.lib;

import jrcet.diycomponents.DiyJAddLabel;
import jrcet.diycomponents.DiyJTabLabel;
import jrcet.diycomponents.DiyJTextArea.ui.rtextarea.RTextScrollPane;
import jrcet.frame.Jrcet;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Helper {

    /*
    组件类函数
     */
    private static int deep = 1;

    public static void travelComponent(JComponent tComponent){
        if(deep==1){
            System.out.println(String.join("", Collections.nCopies(deep-1, "\t"))+"Name: "+tComponent.getName());
        }

        if(tComponent instanceof JScrollPane){
            Component s = ((JScrollPane)tComponent).getViewport().getComponent(0);
            System.out.println(String.join("", Collections.nCopies(deep, "\t"))+"Name: "+s.getName());
            deep+=1;
            travelComponent((JComponent)s);
            deep-=1;
        }

        for( Component i : tComponent.getComponents()){
            JComponent ii = (JComponent) i;

//            System.out.println(Arrays.asList(String.valueOf(i.getClass()).split("^([^.]*\\.)*")).get(1));
            switch (Arrays.asList(String.valueOf(i.getClass()).split("^([^.]*\\.)*")).get(1)){
                case "JPanel":
                    System.out.println(String.join("", Collections.nCopies(deep, "\t"))+"Name: "+ii.getName());
                    deep+=1;
                    travelComponent(ii);
                    deep-=1;
                    break;
                case "DiyJAddLabel":
                    System.out.println(String.join("", Collections.nCopies(deep, "\t"))+"Name: "+ii.getName());
                    List<String> ar = Arrays.asList(ii.getName().split("(?=[A-Z])"));
                    JComponent aj = ((DiyJAddLabel) i).getMapPanel(ar.get(ar.size()-2));
                    System.out.println(String.join("", Collections.nCopies(deep+1, "\t"))+"Name: "+aj.getName());
                    deep+=2;
                    travelComponent(aj);
                    deep-=2;
                    break;
                case "DiyJTabLabel" :
                    System.out.println(String.join("", Collections.nCopies(deep, "\t"))+"Name: "+ii.getName());
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
                    System.out.println(String.join("", Collections.nCopies(deep+1, "\t"))+"Name: "+tj.getName());
                    deep+=2;
                    travelComponent(tj);
                    deep-=2;
                    break;
                case "RTextScrollPane":
                    System.out.println(String.join("", Collections.nCopies(deep, "\t"))+"Name: "+((RTextScrollPane)i).getViewport().getComponent(0).getName());
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
