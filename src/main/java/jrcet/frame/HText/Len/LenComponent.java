package jrcet.frame.HText.Len;

import diycomponents.*;

import javax.swing.*;
import java.awt.*;


public class LenComponent extends DiyJComponent {

    public static JComponent LenComponentPanel = null;

    public static DiyJTextAreaScrollPane LenInputArea = new DiyJTextAreaScrollPane("LenInputArea");
    public static DiyJTextAreaScrollPane LenOutputArea = new DiyJTextAreaScrollPane("LenOutputArea");
    @Override
    public JComponent component() {

        LenComponentPanel = new JPanel(new GridBagLayout());
        LenComponentPanel.setBackground(Color.WHITE);
        LenComponentPanel.setName("LenComponentPanel");

        DiyJButton LenExecuteButton = new DiyJButton("Len");
        LenExecuteButton.setName("LenExecuteButton");
        LenComponentPanel.add(LenExecuteButton,new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        LenComponentPanel.add(LenAreaPanel(),new GridBagConstraints(
                0,1,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));
        return LenComponentPanel;
    }

    private JComponent LenAreaPanel() {
        DiyVariablePanel LenAreaPanel = new DiyVariablePanel(
                LenInputArea, "LenInputArea",
                LenOutputArea, "LenOutputArea",
                DiyVariablePanel.View.HORIZONTAL
        );

        LenAreaPanel.setName("LenAreaPanel");
        LenAreaPanel.setPreferredSize(new Dimension(0,0));

        return LenAreaPanel;
    }
}
