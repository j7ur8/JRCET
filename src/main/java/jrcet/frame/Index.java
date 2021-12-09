package jrcet.frame;

import javax.swing.*;
import java.awt.*;

import jrcet.diycomponents.DiyJButton;
import jrcet.diycomponents.DiyJComponent;
import jrcet.frame.exploit.ExploitComponent;
import jrcet.frame.setting.SettingComponent;
import jrcet.frame.tools.ToolsComponent;

public class Index extends DiyJComponent {

    public Index(){}

    public JComponent main(){
        JPanel root = new JPanel(new GridBagLayout());
        root.setOpaque(true);
        root.setBackground(Color.orange);

        GridBagConstraints topPanelProperty = new GridBagConstraints();
        topPanelProperty.gridx = topPanelProperty.gridy = 0;
        topPanelProperty.fill = GridBagConstraints.BOTH;
        topPanelProperty.anchor = GridBagConstraints.NORTHWEST;

        root.add(rootTopPanel(),topPanelProperty);
//        JPanel panel2 = new JPanel();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 1; gbc.gridx= 0;
        gbc.fill=GridBagConstraints.BOTH;
        gbc.anchor=GridBagConstraints.NORTHWEST;
        gbc.weightx = 100; gbc.weighty = 100;


        DiyJComponent m=new ExploitComponent();
        root.add(m.main(),gbc);
        return root;
    }

    private JComponent rootTopPanel(){
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));

        topPanel.setOpaque(true);
        topPanel.setBackground(Color.YELLOW);

        DiyJButton setButton = new DiyJButton("Setting");

        settingDiyJButton(setButton);

        setButton.variablePanelMap.put(setButton.getText(), Setting());
        topPanel.add(setButton);

        DiyJButton exploitButton = new DiyJButton("Exploit");

        settingDiyJButton(exploitButton);
//        exploitButton.setBackground(new Color(251,251,251));
        exploitButton.variablePanelMap.put(exploitButton.getText(), Exploit());
        exploitButton.setBorder(BorderFactory.createMatteBorder(0,0,2,0,new Color(255,102,51)));
        exploitButton.clicked=true;
        topPanel.add(exploitButton);

        DiyJButton toolsButton = new DiyJButton("Tools");
        settingDiyJButton(toolsButton);
        toolsButton.variablePanelMap.put(toolsButton.getText(), Tools());
        topPanel.add(toolsButton);
        return topPanel;
    }

    private void settingDiyJButton(DiyJButton targetJButton){
        targetJButton.setFont(new Font("微软雅黑",0,14));
//        targetJButton
        targetJButton.setPreferredSize(new Dimension(70,25));
        targetJButton.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        targetJButton.setBackground(Color.WHITE);
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
