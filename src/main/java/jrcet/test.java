package jrcet;

import jrcet.diycomponents.DiyJTextAreaScrollPane;
import jrcet.frame.Setting.Setting;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class test {


    public static void main(String[] args) {
//        test a = new test();
        JFrame JrcetFrame = new JFrame("J7ur8's Remote Code Execute Tools");

        DiyJTextAreaScrollPane diyJTextAreaScrollPane = new DiyJTextAreaScrollPane("testArea");
        //setContentPane需放在前面，不然需要更改界面尺寸才会显示。
        JrcetFrame.setContentPane(diyJTextAreaScrollPane);

        JrcetFrame.setResizable(true);
        JrcetFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JrcetFrame.setSize(1200, 1000);
        JrcetFrame.setVisible(true);
    }

}
