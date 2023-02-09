package jrcet.frame.Tools.Dencrypt;

import jrcet.diycomponents.DiyJComponent;
import jrcet.diycomponents.DiyJTabLabel;
import jrcet.frame.Tools.Dencrypt.Aes.Aes;
import jrcet.frame.Tools.Dencrypt.Aes.AesComponent;
import jrcet.frame.Tools.Dencrypt.Ascii.AsciiComponent;
import jrcet.frame.Tools.Dencrypt.Base.BaseComponent;
import jrcet.frame.Tools.Dencrypt.Hex.HexComponent;
//import jrcet.frame.tools.Dencrypt.Jwt.Jwt;
import jrcet.frame.Tools.Dencrypt.Jwt.JwtComponent;
import jrcet.frame.Tools.Dencrypt.Md5.Md5Component;
import jrcet.frame.Tools.Dencrypt.Rsa.RsaComponent;
import jrcet.frame.Tools.Dencrypt.Unicode.UnicodeComponent;
import jrcet.frame.Tools.Dencrypt.Url.UrlComponent;

import javax.swing.*;
import java.awt.*;

import static jrcet.frame.Setting.Setting.DencryptShowPanel;

public class DencryptComponent extends DiyJComponent {

    private final JComponent BaseComponentPanel = BaseComponentPanel();
    private final JComponent AesComponentPanel = AesComponentPanel();
    private final JComponent Md5ComponentPanel = Md5ComponentPanel();
    private final JComponent UnicodeComponentPanel = UnicodeComponentPanel();
    private final JComponent RsaComponentPanel = RsaComponentPanel();
    private final JComponent AsciiComponentPanel = AsciiComponentPanel();
    private final JComponent HexComponentPanel = HexComponentPanel();
    private final JComponent JwtComponentPanel = JwtComponentPanel();
    private final JComponent UrlComponentPanel = UrlComponentPanel();

    public static JComponent DencryptComponentPanel = null;

    public JComponent main(){

        DencryptComponentPanel = new JPanel(new GridBagLayout());
        DencryptComponentPanel.setName("DencryptComponentPanel");
        DencryptComponentPanel.setBackground(Color.WHITE);

        DencryptComponentPanel.add(DencryptMenuTabPanel(),new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        DencryptComponentPanel.add(DencryptShowPanel(),new GridBagConstraints(
                0,1,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return DencryptComponentPanel;
    }

    private Component DencryptShowPanel() {
        switch (DencryptShowPanel){
            case "Aes":
                return AesComponentPanel;
            case "Md5":
                return Md5ComponentPanel;
            case "Rsa":
                return RsaComponentPanel;
            case "Hex":
                return HexComponentPanel;
            case "Jwt":
                return JwtComponentPanel;
            case "Url":
                return UrlComponentPanel;
            case "Base":
                return BaseComponentPanel;
            case "Ascii":
                return AsciiComponentPanel;
            case "Unicode":
                return UnicodeComponentPanel;
        }
        return new JPanel();
    }

    public JComponent DencryptMenuTabPanel(){

        JPanel DencryptMenuPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        DencryptMenuPanel.setName("DencryptMenuPanel");

        //AES
        DiyJTabLabel DencryptMenuAESLabel = new DiyJTabLabel("Aes",true);
        DencryptMenuAESLabel.setPanel(AesComponentPanel);
        DencryptMenuAESLabel.setName("DencryptMenuAESLabel");
        DencryptMenuPanel.add(DencryptMenuAESLabel);

        //Md5
        DiyJTabLabel DencryptMenuMd5Label = new DiyJTabLabel("Md5");
        DencryptMenuMd5Label.setPanel(Md5ComponentPanel);
        DencryptMenuMd5Label.setName("DencryptMenuMd5Label");
        DencryptMenuPanel.add(DencryptMenuMd5Label);

        //Rsa
        DiyJTabLabel DencryptMenuRsaLabel = new DiyJTabLabel("Rsa");
        DencryptMenuRsaLabel.setPanel(RsaComponentPanel);
        DencryptMenuRsaLabel.setName("DencryptMenuRsaLabel");
        DencryptMenuPanel.add(DencryptMenuRsaLabel);

        //Hex
        DiyJTabLabel DencryptMenuHexLabel = new DiyJTabLabel("Hex");
        DencryptMenuHexLabel.setPanel(HexComponentPanel);
        DencryptMenuHexLabel.setName("DencryptMenuRsaLabel");
        DencryptMenuPanel.add(DencryptMenuHexLabel);

        //Hex
        DiyJTabLabel DencryptMenuJwtLabel = new DiyJTabLabel("Jwt");
        DencryptMenuJwtLabel.setPanel(JwtComponentPanel);
        DencryptMenuJwtLabel.setName("DencryptMenuJwtLabel");
        DencryptMenuPanel.add(DencryptMenuJwtLabel);

        DiyJTabLabel DencryptMenuUrlLabel = new DiyJTabLabel("Url");
        DencryptMenuUrlLabel.setPanel(UrlComponentPanel);
        DencryptMenuUrlLabel.setName("DencryptMenuUrlLabel");
        DencryptMenuPanel.add(DencryptMenuUrlLabel);

        //Base
        DiyJTabLabel DencryptMenuBaseLabel = new DiyJTabLabel("Base");
        DencryptMenuBaseLabel.setName("DencryptMenuBaseLabel");
        DencryptMenuBaseLabel.setPanel(BaseComponentPanel);
        DencryptMenuPanel.add(DencryptMenuBaseLabel);

        //Ascii
        DiyJTabLabel DencryptMenuAsciiLabel = new DiyJTabLabel("Ascii");
        DencryptMenuAsciiLabel.setPanel(AsciiComponentPanel);
        DencryptMenuAsciiLabel.setName("DencryptMenuUnicodeLabel");
        DencryptMenuPanel.add(DencryptMenuAsciiLabel);

        //Unicode
        DiyJTabLabel DencryptMenuUnicodeLabel = new DiyJTabLabel("Unicode");
        DencryptMenuUnicodeLabel.setPanel(UnicodeComponentPanel);
        DencryptMenuUnicodeLabel.setName("DencryptMenuUnicodeLabel");
        DencryptMenuPanel.add(DencryptMenuUnicodeLabel);


        return DencryptMenuPanel;
    }


    public JComponent BaseComponentPanel(){
        return new BaseComponent().main();
    }

    public JComponent AesComponentPanel(){
        return new AesComponent().main();
    }

    public JComponent HexComponentPanel(){
        return new HexComponent().main();
    }

    public JComponent RsaComponentPanel(){
        return new RsaComponent().main();
    }

    public JComponent UnicodeComponentPanel(){
        return new UnicodeComponent().main();
    }

    public JComponent AsciiComponentPanel() {
        return  new AsciiComponent().main();
    }

    public JComponent JwtComponentPanel() { return new JwtComponent().main(); }

    public JComponent Md5ComponentPanel() { return new Md5Component().main(); }

    public JComponent UrlComponentPanel() { return new UrlComponent().main(); }
}
