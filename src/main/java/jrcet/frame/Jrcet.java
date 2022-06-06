package jrcet.frame;

import javax.swing.*;
import java.awt.*;

import jrcet.diycomponents.DiyJComponent;
import jrcet.diycomponents.DiyJTabLabel;
import jrcet.frame.asset.AssetComponent;
import jrcet.frame.setting.SettingComponent;
import jrcet.frame.tools.ToolsComponent;

public class Jrcet extends DiyJComponent {

    public final JComponent SettingComponentPanel = SettingComponentPanel();
    public final JComponent ToolsComponentPanel = ToolsComponentPanel();
    public final JComponent AssetComponentPanel = AssetComponentPanel();

    public static JComponent JrcetComponentPanel = null;

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

        JrcetComponentPanel.add(AssetComponentPanel, new GridBagConstraints(0,1,
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
//        JrcetMenuExploitLabel.setPanel(ExploitComponentPanel);
        JrcetMenuPanel.add(JrcetMenuExploitLabel);

        DiyJTabLabel JrcetMenuToolsLabel = new DiyJTabLabel("Tools");
        JrcetMenuToolsLabel.setName("JrcetMenuToolsLabel");
        JrcetMenuToolsLabel.setPanel(ToolsComponentPanel);
        JrcetMenuPanel.add(JrcetMenuToolsLabel);

        DiyJTabLabel JrcetMenuAssetLabel = new DiyJTabLabel("Asset",true);
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


    private JComponent ToolsComponentPanel(){
        DiyJComponent ToolsComponentInstance=new ToolsComponent();
        return ToolsComponentInstance.main();
    }

    private JComponent SettingComponentPanel(){
        DiyJComponent SettingComponentInstance=new SettingComponent();
        return SettingComponentInstance.main();
    }

    private JComponent AssetComponentPanel(){
        return new AssetComponent().main();
    }


}
