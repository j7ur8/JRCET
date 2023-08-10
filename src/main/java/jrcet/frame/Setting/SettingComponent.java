package jrcet.frame.Setting;

import jrcet.diycomponents.DiyJComponent;

import javax.swing.*;
import java.awt.*;

public class SettingComponent extends DiyJComponent {

    public static JComponent SettingComponentPanel = null;
    public SettingComponent(){}

    public JComponent component() {

        SettingComponentPanel = new JPanel(null);
        SettingComponentPanel.setName("SettingComponentPanel");
        SettingComponentPanel.setBackground(Color.ORANGE);
        SettingComponentPanel.setBounds(0,25,800,60);


        return SettingComponentPanel;
    }
}
