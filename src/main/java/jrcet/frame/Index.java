package jrcet.frame;

import javax.swing.*;
import java.awt.*;

import jrcet.diycomponents.DiyJComponent;
import jrcet.diycomponents.DiyJLabel;
import jrcet.frame.exploit.ExploitComponent;
import jrcet.frame.setting.SettingComponent;
import jrcet.frame.tools.ToolsComponent;

public class Index extends DiyJComponent {

    public Index(){}

    public JComponent main(){
        JPanel rootPanel = new JPanel(new GridBagLayout());
        rootPanel.setOpaque(true);
        rootPanel.setBackground(Color.orange);

        GridBagConstraints rootTopPanelProperty = new GridBagConstraints();
        rootTopPanelProperty.gridx = rootTopPanelProperty.gridy = 0;
        rootTopPanelProperty.fill = GridBagConstraints.BOTH;
        rootPanel.add(rootTopPanel(),rootTopPanelProperty);

        GridBagConstraints defaultRootCenterPanelProperty = new GridBagConstraints();
        defaultRootCenterPanelProperty.gridy = 1; defaultRootCenterPanelProperty.gridx= 0;
        defaultRootCenterPanelProperty.fill=GridBagConstraints.BOTH;
        defaultRootCenterPanelProperty.weightx = 100; defaultRootCenterPanelProperty.weighty = 100;
        JComponent defaultRootCenterPanel=new ExploitComponent().main();
        rootPanel.add(defaultRootCenterPanel, defaultRootCenterPanelProperty);

        return rootPanel;
    }

    private JComponent rootTopPanel(){
        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));

         menuPanel.setOpaque(true);
         menuPanel.setBackground(Color.YELLOW);

        DiyJLabel setLabel = new DiyJLabel("Setting");
        setLabel.setMapPanel(Setting());
        menuPanel.add(setLabel);

        DiyJLabel exploitLabel = new DiyJLabel("Exploit");
        exploitLabel.clicked=true;
        exploitLabel.setMapPanel(Exploit());
        exploitLabel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(2,0,0,0),BorderFactory.createMatteBorder(0,0,2,0,new Color(255,102,51))));
        menuPanel.add(exploitLabel);

        DiyJLabel toolsLabel = new DiyJLabel("Tools");
        toolsLabel.setMapPanel(Tools());
        menuPanel.add(toolsLabel);

        return  menuPanel;
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
