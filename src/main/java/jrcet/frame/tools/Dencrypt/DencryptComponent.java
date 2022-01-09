package jrcet.frame.tools.Dencrypt;

import jrcet.diycomponents.DiyJComponent;
import jrcet.diycomponents.DiyJTabLabel;
import jrcet.frame.setting.Setting;

import javax.swing.*;
import java.awt.*;

public class DencryptComponent extends DiyJComponent {

    public JComponent main(){
        JPanel DencryptPanel = new JPanel();
        DencryptPanel.setOpaque(true);
        DencryptPanel.setBackground(Color.RED);

        DiyJTabLabel testTab = new DiyJTabLabel("test", Setting.class1DefaultDiyJTabBorderColor,Setting.class2ClickedDiyJTabBorderColor);
        DencryptPanel.add(testTab);

        return DencryptPanel;
    };
}
