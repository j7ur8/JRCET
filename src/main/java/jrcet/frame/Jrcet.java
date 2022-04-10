package jrcet.frame;

import javax.swing.*;
import java.awt.*;

import jrcet.diycomponents.DiyJComponent;
import jrcet.diycomponents.DiyJTabLabel;
import jrcet.frame.exploit.ExploitComponent;
import jrcet.frame.setting.Setting;
import jrcet.frame.setting.SettingComponent;
import jrcet.frame.tools.ToolsComponent;

public class Jrcet extends DiyJComponent {

    public final JComponent SettingComponentPanel = SettingComponentPanel();
    public final JComponent ExploitComponentPanel = ExploitComponentPanel();
    public final JComponent ToolsComponentPanel = ToolsComponentPanel();

    public final JComponent DefaultComponentPanel = ToolsComponentPanel;


    public JComponent main(){

        JComponent JrcetComponentPanel = new JPanel(new GridBagLayout());
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

        JrcetComponentPanel.add(DefaultComponentPanel, new GridBagConstraints(0,1,
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

        DiyJTabLabel JrcetMenuSettingLabel = new DiyJTabLabel("Setting",Setting.class1DefaultDiyJTabBorderColor,Setting.class1ClickedDiyJTabBorderColor);
        JrcetMenuSettingLabel.setName("JrcetMenuSettingLabel");
        JrcetMenuSettingLabel.setMapPanel(SettingComponentPanel);
        JrcetMenuPanel.add(JrcetMenuSettingLabel);

        DiyJTabLabel JrcetMenuExploitLabel = new DiyJTabLabel("Exploit", Setting.class1DefaultDiyJTabBorderColor,Setting.class1ClickedDiyJTabBorderColor);
        JrcetMenuExploitLabel.setName("JrcetMenuExploitLabel");
        JrcetMenuExploitLabel.setMapPanel(ExploitComponentPanel);
        JrcetMenuPanel.add(JrcetMenuExploitLabel);

        DiyJTabLabel JrcetMenuToolsLabel = new DiyJTabLabel("Tools",Setting.class1DefaultDiyJTabBorderColor,Setting.class1ClickedDiyJTabBorderColor,true);
        JrcetMenuToolsLabel.setName("JrcetMenuToolsLabel");
        JrcetMenuToolsLabel.setMapPanel(ToolsComponentPanel);
        JrcetMenuPanel.add(JrcetMenuToolsLabel);

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


    private static JComponent ExploitComponentPanel(){
        DiyJComponent ExploitComponentInstance=new ExploitComponent();
        return ExploitComponentInstance.main();
    }

    private static JComponent ToolsComponentPanel(){
        DiyJComponent ToolsComponentInstance=new ToolsComponent();
        return ToolsComponentInstance.main();
    }

    private static JComponent SettingComponentPanel(){
        DiyJComponent SettingComponentInstance=new SettingComponent();
        return SettingComponentInstance.main();
    }

}
