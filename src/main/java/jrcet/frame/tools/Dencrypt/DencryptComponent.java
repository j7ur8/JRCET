package jrcet.frame.tools.Dencrypt;

import jrcet.diycomponents.DiyJComponent;
import jrcet.diycomponents.DiyJTabLabel;
import jrcet.frame.tools.Dencrypt.Aes.AesComponent;
import jrcet.frame.tools.Dencrypt.Ascii.AsciiComponent;
import jrcet.frame.tools.Dencrypt.Base.BaseComponent;
import jrcet.frame.tools.Dencrypt.Hex.HexComponent;
import jrcet.frame.tools.Dencrypt.Rsa.RsaComponent;
import jrcet.frame.tools.Dencrypt.Unicode.UnicodeComponent;

import javax.swing.*;
import java.awt.*;

public class DencryptComponent extends DiyJComponent {

    private final JComponent BaseComponentPanel = BaseComponentPanel();
    private final JComponent AesComponentPanel = AesComponentPanel();
    private final JComponent UnicodeComponentPanel = UnicodeComponentPanel();
    private final JComponent RsaComponentPanel = RsaComponentPanel();
    private final JComponent AsciiComponentPanel = AsciiComponentPanel();
    private final JComponent HexComponentPanel = HexComponentPanel();

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

        DencryptComponentPanel.add(BaseComponentPanel,new GridBagConstraints(
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

    public JComponent DencryptMenuTabPanel(){

        JPanel DencryptMenuPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        DencryptMenuPanel.setName("DencryptMenuPanel");

        //AES
        DiyJTabLabel DencryptMenuAESLabel = new DiyJTabLabel("Aes");
        DencryptMenuAESLabel.setPanel(AesComponentPanel);
        DencryptMenuAESLabel.setName("DencryptMenuAESLabel");
        DencryptMenuPanel.add(DencryptMenuAESLabel);

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

        //Base
        DiyJTabLabel DencryptMenuBaseLabel = new DiyJTabLabel("Base", true);
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



        //设置 Tab 的按钮属性（高宽等）
        for(Component label : DencryptMenuPanel.getComponents()){
            label.setFont(new Font("微软雅黑", Font.PLAIN,12));
            label.setPreferredSize(new Dimension(50,20));
        }

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
}
