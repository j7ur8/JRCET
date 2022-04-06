package jrcet;

import burp.lib.Helper;
import jrcet.frame.Jrcet;
import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        
        JFrame JrcetFrame = new JFrame("J7ur8's Remote Code Execute Tools");
        Container JrcetPanel=new Jrcet().main();
        JrcetFrame.setContentPane(JrcetPanel);
        JrcetFrame.setResizable(true);
        JrcetFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JrcetFrame.setSize(1200, 1000);
        centerInScreen(JrcetFrame);

        JrcetFrame.setVisible(true);
        Helper.travelComponent((JComponent) JrcetPanel);
    }

    public static void centerInScreen(Window win) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = ( screenSize.width - win.getWidth())/2;
        int y = ( screenSize.height - win.getHeight())/2;
        win.setLocation(x,  y);
    }

}
