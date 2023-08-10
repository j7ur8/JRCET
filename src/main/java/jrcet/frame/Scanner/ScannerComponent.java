package jrcet.frame.Scanner;

import jrcet.diycomponents.DiyJComponent;
import jrcet.frame.Scanner.Fastjson.FastjsonComponent;
import jrcet.frame.Scanner.Overauth.OverauthComponent;

import javax.swing.*;
import java.awt.*;


public class ScannerComponent extends DiyJComponent {

    public static JComponent ScannerComponentPanel = null;

    public JComponent component(){

        ScannerComponentPanel = new JPanel(new GridBagLayout());
        ScannerComponentPanel.setName("ScannerComponentPanel");
        ScannerComponentPanel.setBackground(Color.WHITE);

        ScannerComponentPanel.add(ScannerTabbedPane(), new GridBagConstraints(
                0,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,5,0),
                0,0
        ));

        return ScannerComponentPanel;
    }

    private JComponent ScannerTabbedPane(){
        JTabbedPane ScannerTabbedPane = new JTabbedPane(JTabbedPane.LEFT,JTabbedPane.SCROLL_TAB_LAYOUT);
        ScannerTabbedPane.setName("ScannerTabbedPane");
        ScannerTabbedPane.add("OverAuth",new OverauthComponent().component());

        ScannerTabbedPane.add("FastJson",new FastjsonComponent().component());

        return ScannerTabbedPane;
    }


}
