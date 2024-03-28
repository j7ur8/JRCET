package jrcet.frame.HText.Alone;

import diycomponents.DiyJButton;
import diycomponents.DiyJTextAreaScrollPane;
import diycomponents.DiyVariablePanel;
import diycomponents.DiyJComponent;



import javax.swing.*;
import java.awt.*;

import static help.Helper.*;


public class AloneComponent extends DiyJComponent {

    public static JComponent AloneComponentPanel = null;
    public static DiyJTextAreaScrollPane AloneInputArea = createDiyJTextAreaScrollPane("AloneInputArea");

    public static DiyJTextAreaScrollPane AloneOutputArea = createDiyJTextAreaScrollPane("AloneOutputArea");

    @Override
    public JComponent component() {

        AloneComponentPanel = new JPanel(new GridBagLayout());
        AloneComponentPanel.setName("AloneComponentPanel");
        AloneComponentPanel.setBackground(Color.WHITE);
        AloneComponentPanel.setPreferredSize(new Dimension(0,0));

        DiyJButton AloneExecuteButton = new DiyJButton("Alone");
        AloneExecuteButton.setName("AloneExecuteButton");
        AloneComponentPanel.add(AloneExecuteButton, new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        AloneComponentPanel.add(AloneEditorPanel(),new GridBagConstraints(
                0,1,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));



        return AloneComponentPanel;
    }

    public JComponent AloneEditorPanel(){
        DiyVariablePanel AloneEditorPanel = new DiyVariablePanel(
                AloneInputArea, "AloneInputEditor",
                AloneOutputArea, "AloneOutputEditor",
                DiyVariablePanel.View.HORIZONTAL
        );

        AloneEditorPanel.setPreferredSize(new Dimension(0,0));

        return AloneEditorPanel;
    }


}
