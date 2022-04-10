package jrcet.frame.tools.Dencrypt.Aes;

import jrcet.diycomponents.DiyJComponent;

import javax.swing.*;
import java.awt.*;

public class AesComponent extends DiyJComponent {
    public JComponent main(){
        JComponent AesComponentPanel = new JPanel(new GridBagLayout());
        AesComponentPanel.setName("AesComponentPanel");
        AesComponentPanel.setBackground(Color.WHITE);
        AesComponentPanel.setPreferredSize(new Dimension(0,0));

        return AesComponentPanel;
    }
}
