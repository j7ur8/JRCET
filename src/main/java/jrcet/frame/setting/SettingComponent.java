package jrcet.frame.setting;

import jrcet.diycomponents.DiyJComponent;

import javax.swing.*;
import java.awt.*;

public class SettingComponent extends DiyJComponent {

    public SettingComponent(){}

    public JComponent main() {

        JPanel SettingComponentPanel = new JPanel(null);
        SettingComponentPanel.setOpaque(false);
        SettingComponentPanel.setName("SettingComponentPanel");
        SettingComponentPanel.setBackground(Color.ORANGE);
        SettingComponentPanel.setBounds(0,25,800,60);

        return SettingComponentPanel;
    }
}
