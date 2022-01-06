package jrcet.frame.tools.Solibrary;

import jrcet.diycomponents.DiyJComponent;

import javax.swing.*;
import java.awt.*;

public class SoLibraryComponent extends DiyJComponent {

    @Override
    public JComponent main() {
        JPanel SoLibraryPanel = new JPanel(new GridBagLayout());
        SoLibraryPanel.setOpaque(true);
        SoLibraryPanel.setBackground(Color.YELLOW);
        return SoLibraryPanel;
    }
}
