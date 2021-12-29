package jrcet.frame.tools;

import jrcet.diycomponents.DiyJComponent;
import jrcet.diycomponents.DiyJLabel;
import jrcet.diycomponents.DiyJPanel;
import jrcet.frame.tools.RScript.RScriptComponent;

import javax.swing.*;
import java.awt.*;

public class ToolsComponent extends DiyJComponent {

    private JComponent RScript = RScript();
    public ToolsComponent(){}

    @Override
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
        toolsPanel.add(RScript,defaultToolPanelProperty);

        return toolsPanel;
    }

    private JComponent toolsMenuPanel(){
        JPanel toolsMenuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
        toolsMenuPanel.setOpaque(true);
        toolsMenuPanel.setBackground(Color.WHITE);

        DiyJLabel toolsMenuRScriptLabel = new DiyJLabel("RScript");
        toolsMenuRScriptLabel.setMapPanel(RScript);
        toolsMenuRScriptLabel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(2,0,0,0),BorderFactory.createMatteBorder(0,0,2,0,new Color(255,102,51))));
        toolsMenuPanel.add(toolsMenuRScriptLabel);

        DiyJLabel toolsMenuJSEncryptLabel = new DiyJLabel(("JSEncrypt"));
        toolsMenuJSEncryptLabel.setMapPanel(RScript);
        toolsMenuJSEncryptLabel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(2,0,0,0),BorderFactory.createMatteBorder(0,0,1,0,new Color(203,208,209))));
        toolsMenuPanel.add(toolsMenuJSEncryptLabel);

        return toolsMenuPanel;
    }

    private JComponent toolsMenuBorderPanel(){
        JPanel toolsMenuBorderPanel = new JPanel();
        toolsMenuBorderPanel.setOpaque(true);
        toolsMenuBorderPanel.setBackground(Color.WHITE);
        toolsMenuBorderPanel.setBorder(BorderFactory.createMatteBorder(0,0,1,0,new Color(203,208,209)));
        return toolsMenuBorderPanel;
    }

    private JComponent RScript(){
        DiyJComponent RScriptPanelInstance = new RScriptComponent();
        JComponent RScriptPanel = RScriptPanelInstance.main();
        return RScriptPanel;
    }

//    private JComponent JSEncrypt(){
//
//    }
}