package jrcet.frame.Scanner;

import jrcet.diycomponents.DiyJComponent;
import jrcet.diycomponents.DiyJTabLabel;
import jrcet.frame.Scanner.Overauth.OverauthComponent;
import jrcet.frame.Setting.Setting;

import javax.swing.*;
import java.awt.*;


public class ScannerComponent extends DiyJComponent {

    public static JComponent ScannerComponentPanel = null;

    private final JComponent OverauthComponentPanel = OverauthComponentPanel();
    public JComponent main(){

        ScannerComponentPanel = new JPanel(new GridBagLayout());
        ScannerComponentPanel.setName("ScannerComponentPanel");
        ScannerComponentPanel.setBackground(Color.WHITE);

        ScannerComponentPanel.add(ScannerMenuTabPanel(), new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,5,0),
                0,0
        ));

        ScannerComponentPanel.add(ScannerShowPanel(),new GridBagConstraints(
                0,1,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return ScannerComponentPanel;
    }

    private JComponent ScannerMenuTabPanel(){

        JPanel ScannerMenuTabPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
        ScannerMenuTabPanel.setName("ScannerMenuTabPanel");

        DiyJTabLabel ScannerMenuFastjsonLabel = new DiyJTabLabel("Fastjson");
        ScannerMenuFastjsonLabel.setName("ScannerMenuFastjsonLabel");
        ScannerMenuFastjsonLabel.setPanel(new JPanel());
        ScannerMenuTabPanel.add(ScannerMenuFastjsonLabel);

        DiyJTabLabel ScannerMenuSpringbootLabel = new DiyJTabLabel("Springboot");
        ScannerMenuSpringbootLabel.setName("ScannerMenuSpringbootLabel");
        ScannerMenuSpringbootLabel.setPanel(new JPanel());
        ScannerMenuTabPanel.add(ScannerMenuSpringbootLabel);

        DiyJTabLabel ScannerMenuOverauthLabel = new DiyJTabLabel("Overauth");
        ScannerMenuOverauthLabel.setName("ScannerMenuOverauthLabel");
        ScannerMenuOverauthLabel.setPanel(OverauthComponentPanel);
        ScannerMenuTabPanel.add(ScannerMenuOverauthLabel);

        return ScannerMenuTabPanel;
    }

    private JComponent ScannerShowPanel(){
        switch (Setting.ScannerShowPanel){
            case "Overauth":
                return OverauthComponentPanel;
        }
        return new JPanel();
    }

    private JComponent OverauthComponentPanel(){
        return new OverauthComponent().main();
    }

}
