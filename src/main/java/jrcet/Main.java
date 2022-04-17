package jrcet;

import burp.lib.Helper;
import jrcet.frame.Jrcet;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static burp.lib.Helper.getComponent;
import static burp.lib.Helper.treeComponent;


public class Main {

    public static ArrayList<String> JrcetComponentList =new ArrayList<>();
    public static JComponent JrcetPanel=new Jrcet().main();

    public static void main(String[] args) {
        
        JFrame JrcetFrame = new JFrame("J7ur8's Remote Code Execute Tools");

        //setContentPane需放在前面，不然需要更改界面尺寸才会显示。

        JrcetFrame.setContentPane(JrcetPanel);

        JrcetFrame.setResizable(true);
        JrcetFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JrcetFrame.setSize(1200, 1000);
        centerInScreen(JrcetFrame);
        JrcetFrame.setVisible(true);

//        Helper.travelComponent(Main.JrcetPanel);
//
//        for(String i :Helper.treeComponent(JrcetComponentList)){
//            System.out.println(i);
//        }
    }

    public static void centerInScreen(Window win) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = ( screenSize.width - win.getWidth())/2;
        int y = ( screenSize.height - win.getHeight())/2;
        win.setLocation(x,  y);
    }

}
