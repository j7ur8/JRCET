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

        GridBagConstraints rootTopBorderPanelProperty = new GridBagConstraints();
        rootTopBorderPanelProperty.gridx = 1; rootTopBorderPanelProperty.gridy=0;
        rootTopBorderPanelProperty.fill=GridBagConstraints.BOTH;
        rootTopBorderPanelProperty.weighty=0;rootTopBorderPanelProperty.weightx=100;
        rootPanel.add(rootTopBorderPanel(),rootTopBorderPanelProperty);

        GridBagConstraints defaultRootCenterPanelProperty = new GridBagConstraints();
        defaultRootCenterPanelProperty.gridwidth=2;
        defaultRootCenterPanelProperty.gridy = 1; defaultRootCenterPanelProperty.gridx= 0;
        defaultRootCenterPanelProperty.fill=GridBagConstraints.BOTH;
        defaultRootCenterPanelProperty.weightx = 100; defaultRootCenterPanelProperty.weighty = 100;
        JComponent defaultRootCenterPanel=new ExploitComponent().main();
        rootPanel.add(defaultRootCenterPanel, defaultRootCenterPanelProperty);

        return rootPanel;
    }

    private JComponent rootTopPanel(){
        JPanel rootTopPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
         rootTopPanel.setOpaque(true);

        DiyJLabel setLabel = new DiyJLabel("Setting");
        setLabel.setBorder(BorderFactory.createMatteBorder(0,0,1,0,new Color(203,208,209)));
        setLabel.setMapPanel(Setting());
        rootTopPanel.add(setLabel);

        DiyJLabel exploitLabel = new DiyJLabel("Exploit");
        exploitLabel.setBorder(BorderFactory.createMatteBorder(0,0,1,0,new Color(203,208,209)));
        exploitLabel.clicked=true;
        exploitLabel.setMapPanel(Exploit());
        exploitLabel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(2,0,0,0),BorderFactory.createMatteBorder(0,0,2,0,new Color(255,102,51))));
        rootTopPanel.add(exploitLabel);

        DiyJLabel toolsLabel = new DiyJLabel("Tools");
        toolsLabel.setBorder(BorderFactory.createMatteBorder(0,0,1,0,new Color(203,208,209)));
        toolsLabel.setMapPanel(Tools());
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
