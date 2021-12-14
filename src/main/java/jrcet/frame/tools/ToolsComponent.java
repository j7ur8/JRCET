package jrcet.frame.tools;

import jrcet.diycomponents.DiyJComponent;
import jrcet.diycomponents.DiyJLabel;

import javax.swing.*;
import java.awt.*;

public class ToolsComponent extends DiyJComponent {

    public ToolsComponent(){}

    public JComponent main(){
        JPanel toolsPanel = new JPanel(new GridBagLayout());
        toolsPanel.setOpaque(true);
        toolsPanel.setBackground(Color.WHITE);

        GridBagConstraints toolsMenuPanelProperty = new GridBagConstraints();
        toolsMenuPanelProperty.gridx=0;toolsMenuPanelProperty.gridy=0;
        toolsPanel.add(toolsMenuPanel(), toolsMenuPanelProperty);

        GridBagConstraints toolsMenuBorderPanelProperty = new GridBagConstraints();
        toolsMenuBorderPanelProperty.gridx=1;toolsMenuBorderPanelProperty.gridy=0;
        toolsMenuBorderPanelProperty.weightx=100;toolsMenuBorderPanelProperty.weighty=0;
        toolsMenuBorderPanelProperty.fill=GridBagConstraints.BOTH;
        toolsPanel.add(toolsMenuBorderPanel(),toolsMenuBorderPanelProperty);

        GridBagConstraints defaultToolPanelProperty = new GridBagConstraints();
        defaultToolPanelProperty.gridx=0;defaultToolPanelProperty.gridy=1;
        defaultToolPanelProperty.weightx=100;defaultToolPanelProperty.weighty=100;
        defaultToolPanelProperty.gridheight=1;defaultToolPanelProperty.gridwidth=2;
        defaultToolPanelProperty.fill=GridBagConstraints.BOTH;
        toolsPanel.add(RScriptPanel(),defaultToolPanelProperty);

        return toolsPanel;
    }

    private JComponent toolsMenuPanel(){
        JPanel toolsMenuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
        toolsMenuPanel.setOpaque(true);
        toolsMenuPanel.setBackground(Color.WHITE);

        JLabel toolsMenuRScriptLabel = new DiyJLabel("RScript");
        toolsMenuRScriptLabel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(2,0,0,0),BorderFactory.createMatteBorder(0,0,2,0,new Color(255,102,51))));
        toolsMenuPanel.add(toolsMenuRScriptLabel);

        return toolsMenuPanel;
    }

    private JComponent RScriptPanel(){
        JPanel RScriptPanel = new JPanel(new GridBagLayout());
        RScriptPanel.setOpaque(true);
        RScriptPanel.setBackground(Color.WHITE);

        JTextArea RScriptJTextArea = new JTextArea();
        GridBagConstraints RScriptJTextAreaProperty = new GridBagConstraints();
        RScriptJTextAreaProperty.gridx=0;RScriptJTextAreaProperty.gridy=0;
        RScriptJTextAreaProperty.weightx=100;RScriptJTextAreaProperty.weighty=100;
        RScriptJTextAreaProperty.fill=GridBagConstraints.BOTH;
        RScriptPanel.add(RScriptJTextArea,RScriptJTextAreaProperty);

        JPanel RScriptMenuPanel = new JPanel();
        RScriptMenuPanel.setPreferredSize(new Dimension(300,0));
        GridBagConstraints RScriptMenuPanelProperty = new GridBagConstraints();
        RScriptMenuPanelProperty.gridx=1;RScriptMenuPanelProperty.gridy=0;
        RScriptMenuPanelProperty.weighty=100;RScriptMenuPanelProperty.weightx=0;
        RScriptMenuPanelProperty.fill=GridBagConstraints.BOTH;
        RScriptPanel.add(RScriptMenuPanel,RScriptMenuPanelProperty);

        return RScriptPanel;
    }

    private JComponent toolsMenuBorderPanel(){
        JPanel toolsMenuBorderPanel = new JPanel();
        toolsMenuBorderPanel.setOpaque(true);
        toolsMenuBorderPanel.setBackground(Color.WHITE);
        toolsMenuBorderPanel.setBorder(BorderFactory.createMatteBorder(0,0,1,0,new Color(203,208,209)));
        return toolsMenuBorderPanel;
    }


}