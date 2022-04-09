package jrcet;

import burp.lib.Helper;
import jrcet.frame.Jrcet;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static burp.lib.Helper.getComponent;


public class Main {

    public static ArrayList<String> JrcetComponentList =new ArrayList<>();

    public static void main(String[] args) {
        
        JFrame JrcetFrame = new JFrame("J7ur8's Remote Code Execute Tools");

        //setContentPane需放在前面，不然需要更改界面尺寸才会显示。
        JComponent JrcetPanel=new Jrcet().main();
        JrcetFrame.setContentPane(JrcetPanel);

        JrcetFrame.setResizable(true);
        JrcetFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JrcetFrame.setSize(1200, 1000);
        centerInScreen(JrcetFrame);
        JrcetFrame.setVisible(true);

        // 遍历组件，存放到JrcetComponentList中
        Helper.travelComponent(JrcetPanel);

        JComponent aa= getComponent(JrcetPanel,"BaseMainBlackPanel");
        System.out.println(aa);
    }

    public static void centerInScreen(Window win) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = ( screenSize.width - win.getWidth())/2;
        int y = ( screenSize.height - win.getHeight())/2;
        win.setLocation(x,  y);
    }

}
