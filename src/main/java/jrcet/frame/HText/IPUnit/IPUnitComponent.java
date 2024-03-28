package jrcet.frame.HText.IPUnit;


import diycomponents.*;
import help.Helper;

import javax.swing.*;
import java.awt.*;

public class IPUnitComponent extends DiyJComponent {

    public static JComponent IPUnitComponentPanel = null;

    public static DiyJTextAreaScrollPane IPUnitInputArea = Helper.createDiyJTextAreaScrollPane("IPUnitInputArea");

    public static DiyJTextAreaScrollPane IPUnitOutputArea = Helper.createDiyJTextAreaScrollPane("IPUnitOutputArea");
    @Override
    public JComponent component() {

        IPUnitComponentPanel = new JPanel(new GridBagLayout());
        IPUnitComponentPanel.setBackground(Color.WHITE);
        IPUnitComponentPanel.setName("IPUnitComponentPanel");
        IPUnitComponentPanel.setPreferredSize(new Dimension(0,0));


        DiyJButton IPUnitPrintButton = new DiyJButton("打印IP段内的IP");
        IPUnitPrintButton.setName("IPUnitPrintButton");
        IPUnitComponentPanel.add(IPUnitPrintButton,new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        DiyJButton IPUnitBelongButton = new DiyJButton("判断IP是否属于IP段");
        IPUnitBelongButton.setName("IPUnitBelongButton");
        IPUnitComponentPanel.add(IPUnitBelongButton,new GridBagConstraints(
                1,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        IPUnitComponentPanel.add(IPUnitEditorPanel(),new GridBagConstraints(
                0,1,
                2,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));


        return IPUnitComponentPanel;
    }

    public JComponent IPUnitEditorPanel(){
        DiyVariablePanel IPUnitEditorPanel = new DiyVariablePanel(
                IPUnitInputArea, "IPUnitInputEditor",
                IPUnitOutputArea, "IPUnitOutputEditor",
                DiyVariablePanel.View.HORIZONTAL
        );
        IPUnitEditorPanel.setPreferredSize(new Dimension(0,0 ));
        return IPUnitEditorPanel;
    }
}
