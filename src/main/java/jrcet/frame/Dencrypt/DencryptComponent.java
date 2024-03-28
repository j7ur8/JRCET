package jrcet.frame.Dencrypt;

import diycomponents.DiyJComponent;
import jrcet.frame.Dencrypt.Aes.AesComponent;
import jrcet.frame.Dencrypt.Ascii.AsciiComponent;
import jrcet.frame.Dencrypt.Base.BaseComponent;
import jrcet.frame.Dencrypt.Des.DesComponent;
import jrcet.frame.Dencrypt.Hex.HexComponent;
//import jrcet.frame.tools.Dencrypt.Jwt.Jwt;
import jrcet.frame.Dencrypt.Jwt.JwtComponent;
import jrcet.frame.Dencrypt.Md5.Md5Component;
import jrcet.frame.Dencrypt.Rsa.RsaComponent;
import jrcet.frame.Dencrypt.Unicode.UnicodeComponent;
import jrcet.frame.Dencrypt.Url.UrlComponent;

import javax.swing.*;
import java.awt.*;

public class DencryptComponent extends DiyJComponent {

    public static JComponent DencryptComponentPanel = null;

    public JComponent component(){

        DencryptComponentPanel = new JPanel(new GridBagLayout());
        DencryptComponentPanel.setName("DencryptComponentPanel");
        DencryptComponentPanel.setBackground(Color.WHITE);

        DencryptComponentPanel.add(DencryptTabbedPane(),new GridBagConstraints(
                0,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return DencryptComponentPanel;
    }


    public JComponent DencryptTabbedPane(){

        JTabbedPane DencryptTabbedPane = new JTabbedPane(JTabbedPane.LEFT,JTabbedPane.SCROLL_TAB_LAYOUT);
        DencryptTabbedPane.setName("DencryptTabbedPane");

        DencryptTabbedPane.add("Aes",new AesComponent().component());
        DencryptTabbedPane.add("Des",new DesComponent().component());
        DencryptTabbedPane.add("MD5",new Md5Component().component());
        DencryptTabbedPane.add("Rsa",new RsaComponent().component());
        DencryptTabbedPane.add("Hex",new HexComponent().component());
        DencryptTabbedPane.add("Jwt",new JwtComponent().component());
        DencryptTabbedPane.add("Url",new UrlComponent().component());
        DencryptTabbedPane.add("Base", new BaseComponent().component());
        DencryptTabbedPane.add("Ascii",new AsciiComponent().component());
        DencryptTabbedPane.add("Unicode",new UnicodeComponent().component());

        return DencryptTabbedPane;
    }

}
