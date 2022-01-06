package jrcet;

import jrcet.frame.Index;
import javax.swing.*;
import java.awt.*;

public class Start {

    public static void main(String[] args) {


        JFrame frame = new JFrame("J7ur8's Remote Code Execute Tools");
        Container root=new Index().main();
        frame.setContentPane(root);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 1000);
        centerInScreen(frame);

        frame.setVisible(true);
    }

    public static void centerInScreen(Window win) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = ( screenSize.width - win.getWidth())/2;
        int y = ( screenSize.height - win.getHeight())/2;
        win.setLocation(x,  y);
    }

}
