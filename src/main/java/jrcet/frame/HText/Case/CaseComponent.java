package jrcet.frame.HText.Case;


import diycomponents.*;
import help.Helper;

import javax.swing.*;
import java.awt.*;


public class CaseComponent extends DiyJComponent {

    public static JComponent CaseComponentPanel = null;

    public static DiyJTextAreaScrollPane CaseLowerArea = Helper.createDiyJTextAreaScrollPane("CaseLowerArea");
    public static DiyJTextAreaScrollPane CaseUpperArea = Helper.createDiyJTextAreaScrollPane("CaseUpperArea");

    @Override
    public JComponent component() {

        Case.initCase();
        CaseComponentPanel = new JPanel(new GridBagLayout());
        CaseComponentPanel.setBackground(Color.WHITE);
        CaseComponentPanel.setName("CaseComponentPanel");
        CaseComponentPanel.setPreferredSize(new Dimension(0,0));


        DiyJButton CaseLowerButton = new DiyJButton("Lower");
        CaseLowerButton.setName("CaseLowerButton");
        CaseComponentPanel.add(CaseLowerButton,new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        DiyJButton CaseUpperButton = new DiyJButton("Upper");
        CaseUpperButton.setName("CaseUpperButton");
        CaseComponentPanel.add(CaseUpperButton,new GridBagConstraints(
                1,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));


        CaseComponentPanel.add(CaseEditorPanel(),new GridBagConstraints(
                0,1,
                2,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return CaseComponentPanel;
    }

    public JComponent CaseEditorPanel(){
        DiyVariablePanel CaseEditorPanel = new DiyVariablePanel(
                CaseLowerArea, "CaseLowerEditor",
                CaseUpperArea, "CaseUpperEditor",
                DiyVariablePanel.View.HORIZONTAL
        );

        CaseEditorPanel.setPreferredSize(new Dimension(0,0));

        return CaseEditorPanel;
    }

}
