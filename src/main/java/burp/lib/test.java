package burp.lib;

import jrcet.frame.Index;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class test {

    public static void main(String[] args) throws IOException, InterruptedException {
        JFrame frame = new JFrame("J7ur8's Remote Code Execute Tools");
//        Container root=new Index().main();
        Container root = new JPanel();
        root.setBackground(Color.BLUE);
        RSyntaxTextArea textArea = new RSyntaxTextArea();
        textArea.setText("<?php\neval(\"phpinfo();\");\n?>");
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        textArea.setCodeFoldingEnabled(true);
        RTextScrollPane sp = new RTextScrollPane(textArea);
        root.add(sp);
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
