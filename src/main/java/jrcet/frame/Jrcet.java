package jrcet.frame;

import com.coreyd97.BurpExtenderUtilities.VariableViewPanel;
import jrcet.diycomponents.DiyJComponent;
import jrcet.diycomponents.DiyJTabLabel;
import jrcet.frame.Asset.AssetComponent;
import jrcet.frame.Dencrypt.DencryptComponent;
import jrcet.frame.HText.HTextComponent;
import jrcet.frame.Intruder.IntruderComponent;
import jrcet.frame.Scanner.ScannerComponent;
import jrcet.frame.Setting.Setting;
import jrcet.frame.Setting.SettingComponent;
import jrcet.frame.Tools.ToolsComponent;

import javax.swing.*;
import java.awt.*;


public class Jrcet extends DiyJComponent {

    public static JComponent JrcetComponentPanel = null;

    public JComponent main(){

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
        JrcetTabbedPane.add("Setting", new SettingComponent().main());
        JrcetTabbedPane.add("Exploit",new JPanel());
        JrcetTabbedPane.add("Dencrypt", new DencryptComponent().main());
        JrcetTabbedPane.add("HText", new HTextComponent().main());
        JrcetTabbedPane.add("Tools",new ToolsComponent().main());
        JrcetTabbedPane.add("Intruder",new IntruderComponent().main());
        JrcetTabbedPane.add("Scanner",new ScannerComponent().main());
        JrcetTabbedPane.add("Asset",new AssetComponent().main());

        JrcetTabbedPane.setSelectedIndex(4);

        return  JrcetTabbedPane;
    }


}
