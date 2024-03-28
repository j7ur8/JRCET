package jrcet.frame.HText.Regex;

import diycomponents.*;


import javax.swing.*;
import java.awt.*;

public class RegexComponent extends DiyJComponent {

    public static JComponent RegexComponentPanel = null;

    public static DiyJTextAreaScrollPane RegexInputArea = new DiyJTextAreaScrollPane("RegexInputArea");
    public static DiyJTextAreaScrollPane RegexOutputArea = new DiyJTextAreaScrollPane("RegexOutputArea");

    @Override
    public JComponent component() {

        RegexComponentPanel = new JPanel(new GridBagLayout());
        RegexComponentPanel.setBackground(Color.WHITE);
        RegexComponentPanel.setName("RegexComponentPanel");


        RegexComponentPanel.add(RegexMenuPanel(),new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        DiyJButton RegexExecuteButton = new DiyJButton("Regex");
        RegexExecuteButton.setName("RegexExecuteButton");
        RegexComponentPanel.add(RegexExecuteButton,new GridBagConstraints(
                0,1,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        RegexComponentPanel.add(RegexAreaPanel(),new GridBagConstraints(
                0,2,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return RegexComponentPanel;
    }

    public JComponent RegexMenuPanel(){
        JPanel RegexMenuPanel = new JPanel(new GridBagLayout());
        RegexMenuPanel.setName("RegexMenuPanel");
        RegexMenuPanel.setPreferredSize(new Dimension(0,30));

        DiyJTextField RegexMenuRuleField = new DiyJTextField("Rule");
        RegexMenuRuleField.setName("RegexMenuRuleField");
        RegexMenuRuleField.setPreferredSize(new Dimension(0,0));
        RegexMenuPanel.add(RegexMenuRuleField,new GridBagConstraints(
                0,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));
        return RegexMenuPanel;
    }


    public JComponent RegexAreaPanel(){
        DiyVariablePanel RegexAreaPanel = new DiyVariablePanel(
                RegexInputArea,"RegexInputArea",
                RegexOutputArea, "RegexOutputArea",
                DiyVariablePanel.View.HORIZONTAL
        );

        RegexAreaPanel.setName("RegexAreaPanel");

        return RegexAreaPanel;
    }
}
