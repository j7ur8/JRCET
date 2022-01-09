package jrcet.frame;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

import jrcet.diycomponents.DiyJComponent;
import jrcet.diycomponents.DiyJLabel;
import jrcet.diycomponents.DiyJTabLabel;
import jrcet.frame.exploit.ExploitComponent;
import jrcet.frame.setting.Setting;
import jrcet.frame.setting.SettingComponent;
import jrcet.frame.tools.ToolsComponent;

public class Index extends DiyJComponent {

    private final JComponent setting = Setting();
    private final JComponent exploit = Exploit();
    private final JComponent tools = Tools();
    public Index(){}

    public JComponent main(){
        JPanel rootPanel = new JPanel(new GridBagLayout());
        rootPanel.setOpaque(true);
        rootPanel.setBackground(Color.WHITE);

        GridBagConstraints rootTopPanelProperty = new GridBagConstraints(
                0,0,
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        );
        rootPanel.add(rootTopPanel(),rootTopPanelProperty);

        GridBagConstraints rootTopBorderPanelProperty = new GridBagConstraints(
                1,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        );
        rootPanel.add(rootTopBorderPanel(),rootTopBorderPanelProperty);

        GridBagConstraints defaultRootCenterPanelProperty = new GridBagConstraints(0,1,
                2,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        );
        rootPanel.add(exploit, defaultRootCenterPanelProperty);

        return rootPanel;
    }

    private JComponent rootTopPanel(){
        JPanel rootTopPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
        rootTopPanel.setOpaque(true);

        DiyJTabLabel setLabel = new DiyJTabLabel("Setting",Setting.class1DefaultDiyJTabBorderColor,Setting.class1ClickedDiyJTabBorderColor);
        setLabel.setMapPanel(setting);
        rootTopPanel.add(setLabel);

        DiyJTabLabel exploitLabel = new DiyJTabLabel("Exploit", Setting.class1DefaultDiyJTabBorderColor,Setting.class1ClickedDiyJTabBorderColor,true);
        exploitLabel.setMapPanel(exploit);
        rootTopPanel.add(exploitLabel);

        DiyJTabLabel toolsLabel = new DiyJTabLabel("Tools",Setting.class1DefaultDiyJTabBorderColor,Setting.class1ClickedDiyJTabBorderColor);
        toolsLabel.setMapPanel(tools);
        rootTopPanel.add(toolsLabel);

        return  rootTopPanel;
    }

    private JComponent rootTopBorderPanel() {
        JPanel rootTopBorderPanel = new JPanel();
        rootTopBorderPanel.setOpaque(true);
        rootTopBorderPanel.setBackground(Color.WHITE);
        rootTopBorderPanel.setBorder(BorderFactory.createMatteBorder(0,0,1,0,new Color(203,208,209)));
        return rootTopBorderPanel;
    }


    private static JComponent Exploit(){
        DiyJComponent exploitPanel=new ExploitComponent();
        return exploitPanel.main();
    }

    private static JComponent Tools(){
        DiyJComponent toolsPanel=new ToolsComponent();
        return toolsPanel.main();
    }

    private static JComponent Setting(){
        DiyJComponent settingPanel=new SettingComponent();
        return settingPanel.main();
    }

}
