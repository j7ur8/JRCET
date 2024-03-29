package jrcet.frame.HText.Sort;

import diycomponents.DiyJButton;
import diycomponents.DiyJTextAreaScrollPane;
import diycomponents.DiyVariablePanel;
import help.Helper;
import diycomponents.DiyJComponent;




import javax.swing.*;
import java.awt.*;

public class SortComponent extends DiyJComponent {

    public static JComponent SortComponentPanel = null;
    public static DiyJTextAreaScrollPane SortInputArea = Helper.createDiyJTextAreaScrollPane("SortInputArea");

    public static DiyJTextAreaScrollPane SortOutputArea = Helper.createDiyJTextAreaScrollPane("SortOutputArea");

    @Override
    public JComponent component() {

        SortComponentPanel = new JPanel(new GridBagLayout());
        SortComponentPanel.setBackground(Color.WHITE);
        SortComponentPanel.setName("SortComponentPanel");
        SortComponentPanel.setPreferredSize(new Dimension(0,0));

        DiyJButton SortExecuteButton = new DiyJButton("Sort");
        SortExecuteButton.setName("SortExecuteButton");
        SortComponentPanel.add(SortExecuteButton,new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        SortComponentPanel.add(SortEditorPanel(),new GridBagConstraints(
                0,1,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return SortComponentPanel;
    }

    public JComponent SortEditorPanel() {
        DiyVariablePanel SortEditorPanel = new DiyVariablePanel(
                SortInputArea,"SortInputEditor",
                SortOutputArea, "SortOutputEditor",
                DiyVariablePanel.View.HORIZONTAL

        );
        SortEditorPanel.setPreferredSize(new Dimension(0,0));

        return SortEditorPanel;
    }

}
