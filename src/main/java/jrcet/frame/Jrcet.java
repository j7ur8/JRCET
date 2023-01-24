package jrcet.frame;

import jrcet.diycomponents.DiyJComponent;
import jrcet.diycomponents.DiyJTabLabel;
import jrcet.frame.Intruder.IntruderComponent;
import jrcet.frame.Scanner.ScannerComponent;
import jrcet.frame.Setting.Setting;
import jrcet.frame.Setting.SettingComponent;
import jrcet.frame.Tools.ToolsComponent;

import javax.swing.*;
import java.awt.*;


public class Jrcet extends DiyJComponent {

    public static JComponent JrcetComponentPanel = null;
    private final JComponent SettingComponentPanel = SettingComponentPanel();
    private final JComponent AssetComponentPanel = AssetComponentPanel();
    private final JComponent ScannerComponentPanel = ScannerComponentPanel();
    private final JComponent IntruderComponentPanel = IntruderComponentPanel();
    private final JComponent ExploitComponentPanel = ExploitComponentPanel();
    private final JComponent ToolsComponentPanel = ToolsComponentPanel();


    public JComponent main(){

        JrcetComponentPanel = new JPanel(new GridBagLayout());
        JrcetComponentPanel.setName("JrcetComponentPanel");
        JrcetComponentPanel.setOpaque(false);
        JrcetComponentPanel.setBackground(Color.WHITE);

        JrcetComponentPanel.add(JrcetMenuPanel(),new GridBagConstraints(
                0,0,
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        JrcetComponentPanel.add(JrcetMenuBorderPanel(),new GridBagConstraints(
                1,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));


        JrcetComponentPanel.add(JrcetShowPanel(), new GridBagConstraints(0,1,
                2,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return JrcetComponentPanel;
    }

    private JComponent JrcetMenuPanel(){
        JPanel JrcetMenuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
        JrcetMenuPanel.setName("JrcetMenuPanel");
        JrcetMenuPanel.setOpaque(false);

        DiyJTabLabel JrcetMenuSettingLabel = new DiyJTabLabel("Setting");
        JrcetMenuSettingLabel.setName("JrcetMenuSettingLabel");
        JrcetMenuSettingLabel.setPanel(SettingComponentPanel);
        JrcetMenuPanel.add(JrcetMenuSettingLabel);

        DiyJTabLabel JrcetMenuExploitLabel = new DiyJTabLabel("Exploit");
        JrcetMenuExploitLabel.setName("JrcetMenuExploitLabel");
        JrcetMenuExploitLabel.setPanel(ExploitComponentPanel);
        JrcetMenuPanel.add(JrcetMenuExploitLabel);

        DiyJTabLabel JrcetMenuToolsLabel = new DiyJTabLabel("Tools", true);
        JrcetMenuToolsLabel.setName("JrcetMenuToolsLabel");
        JrcetMenuToolsLabel.setPanel(ToolsComponentPanel);
        JrcetMenuPanel.add(JrcetMenuToolsLabel);

        DiyJTabLabel JrcetMenuIntruderLabel = new DiyJTabLabel("Intruder");
        JrcetMenuIntruderLabel.setName("JrcetMenuIntruderLabel");
        JrcetMenuIntruderLabel.setPanel(IntruderComponentPanel);
        JrcetMenuPanel.add(JrcetMenuIntruderLabel);

        DiyJTabLabel JrcetMenuScannerLabel = new DiyJTabLabel("Scanner");
        JrcetMenuScannerLabel.setName("JrcetMenuScannerLabel");
        JrcetMenuScannerLabel.setPanel(ScannerComponentPanel);
        JrcetMenuPanel.add(JrcetMenuScannerLabel);

        DiyJTabLabel JrcetMenuAssetLabel = new DiyJTabLabel("Asset");
        JrcetMenuAssetLabel.setName("JrcetMenuToolsLabel");
        JrcetMenuAssetLabel.setPanel(AssetComponentPanel);
        JrcetMenuPanel.add(JrcetMenuAssetLabel);

        return  JrcetMenuPanel;
    }

    private JComponent JrcetMenuBorderPanel() {
        JPanel JrcetMenuBorderPanel = new JPanel();
        JrcetMenuBorderPanel.setName("JrcetMenuBorderPanel");
        JrcetMenuBorderPanel.setOpaque(true);
        JrcetMenuBorderPanel.setBackground(Color.WHITE);
        JrcetMenuBorderPanel.setBorder(BorderFactory.createMatteBorder(0,0,1,0,new Color(203,208,209)));
        return JrcetMenuBorderPanel;
    }


    private JComponent JrcetShowPanel(){
        JComponent showPanel = new JPanel();
        switch (Setting.JrcetShowPanel){
            case "Intruder":
                showPanel = IntruderComponentPanel;
                break;
            case "Scanner":
                showPanel = ScannerComponentPanel;
                break;
            case "Tools":
                showPanel=ToolsComponentPanel;
                break;
            case "Asset":
                showPanel = AssetComponentPanel;
                break;
            case "Exploit":
                showPanel = ExploitComponentPanel;
                break;
        }
        return showPanel;
    }

    private JComponent IntruderComponentPanel(){
        DiyJComponent IntruderComponentInstance = new IntruderComponent();
        return IntruderComponentInstance.main();
    }

    private JComponent ScannerComponentPanel() {
        return new ScannerComponent().main();
    }


    private JComponent ToolsComponentPanel(){
        DiyJComponent ToolsComponentInstance=new ToolsComponent();
        return ToolsComponentInstance.main();
    }

    private JComponent SettingComponentPanel(){
        DiyJComponent SettingComponentInstance=new SettingComponent();
        return SettingComponentInstance.main();
    }

    private JComponent AssetComponentPanel(){
        return new JPanel();
//        return new AssetComponent().main();
    }

    private JComponent ExploitComponentPanel(){
        return new JPanel();
    }


}
