package jrcet.frame.Scanner.Overauth;

import jrcet.diycomponents.DiyJComponent;

import javax.swing.*;
import java.awt.*;

public class OverauthComponent extends DiyJComponent {

    public static JComponent OverauthComponent = null;

    @Override
    public JComponent main() {
        OverauthComponent = new JPanel(new GridBagLayout());
        OverauthComponent.setName("OverauthComponent");
        OverauthComponent.setBackground(Color.BLUE);

        return OverauthComponent;
    }
}
