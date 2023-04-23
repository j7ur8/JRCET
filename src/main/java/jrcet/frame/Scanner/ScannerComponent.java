package jrcet.frame.Scanner;

import jrcet.diycomponents.DiyJComponent;

import javax.swing.*;
import java.awt.*;

public class ScannerComponent extends DiyJComponent {

    public static JComponent ScannerComponentPanel = null;

    public JComponent main(){

        ScannerComponentPanel = new JPanel(new GridBagLayout());
        ScannerComponentPanel.setName("ScannerComponentPanel");
        ScannerComponentPanel.setBackground(Color.WHITE);

        return ScannerComponentPanel;
    }

}
