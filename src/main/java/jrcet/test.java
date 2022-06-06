package jrcet;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.internal.messaging.saaj.util.Base64;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jrcet.frame.Jrcet;
import jrcet.frame.tools.Dencrypt.Jwt.Jwt;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.awt.event.KeyEvent;

public class test {
    private static final String Jwt_Secret = "111111111111111111111111";

    public static void main(String[] args) throws Exception {
        JFrame JrcetFrame = new JFrame("J7ur8's Remote Code Execute Tools");

        //setContentPane需放在前面，不然需要更改界面尺寸才会显示。
        JTabbedPane jTabbedPane =new JTabbedPane();
        JrcetFrame.setContentPane(jTabbedPane);

        JrcetFrame.setResizable(true);
        JrcetFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JrcetFrame.setSize(1200, 1000);
//        centerInScreen(JrcetFrame);
        JrcetFrame.setVisible(true);

        JPanel jPanelFirst = new javax.swing.JPanel();
        JPanel jPanelSecond = new javax.swing.JPanel();
        JPanel jPanelThird = new javax.swing.JPanel();

//        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanelFirst.setLayout(null);
        jTabbedPane.addTab("tab1", jPanelFirst);

        jPanelSecond.setLayout(null);
        jTabbedPane.addTab("tab2", jPanelSecond);

        jPanelThird.setLayout(null);
        jTabbedPane.addTab("tab3", jPanelThird);


    }

}
