package jrcet.frame.setting;

import jrcet.diycomponents.DiyJComponent;

import javax.swing.*;
import java.awt.*;

public class SettingComponent extends DiyJComponent {

    public SettingComponent(){}

    public JComponent main() {
        JPanel centerPanel = new JPanel(null);
        centerPanel.setOpaque(true);
        centerPanel.setBackground(Color.ORANGE);
        centerPanel.setBounds(0,25,800,60);
        return centerPanel;
    }
}
