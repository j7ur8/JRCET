package jrcet.frame;

import diycomponents.DiyJComponent;

import jrcet.frame.Asset.AssetComponent;
import jrcet.frame.Dencrypt.DencryptComponent;
import jrcet.frame.HText.HTextComponent;
import jrcet.frame.Scanner.ScannerComponent;
import jrcet.frame.Tools.ToolsComponent;

import javax.swing.*;
import java.awt.*;


public class Jrcet extends DiyJComponent {

    public static JComponent JrcetComponentPanel = null;

    public JComponent component(){

        JrcetComponentPanel = new JPanel(new GridBagLayout());
        JrcetComponentPanel.setName("JrcetComponentPanel");
        JrcetComponentPanel.setBackground(Color.WHITE);

        JrcetComponentPanel.add(JrcetTabbedPane(),new GridBagConstraints(
                0,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return JrcetComponentPanel;
    }

    private JComponent JrcetTabbedPane(){
        JTabbedPane JrcetTabbedPane = new JTabbedPane();
        JrcetTabbedPane.setName("JrcetTabbedPane");

        JrcetTabbedPane.setName("JrcetTabbedPane");
//        JrcetTabbedPane.add("Exploit",new JPanel());
        JrcetTabbedPane.add("Dencrypt", new DencryptComponent().component());
        JrcetTabbedPane.add("HText", new HTextComponent().component());
        JrcetTabbedPane.add("Tools",new ToolsComponent().component());
//        JrcetTabbedPane.add("Intruder",new IntruderComponent().component());
        JrcetTabbedPane.add("Scanner",new ScannerComponent().component());
        JrcetTabbedPane.add("Asset",new AssetComponent().component());

        return  JrcetTabbedPane;
    }


}
