package jrcet.frame.HText.Format;

import jrcet.diycomponents.*;
import jrcet.help.Helper;

import javax.swing.*;
import java.awt.*;

public class FormatComponent extends DiyJComponent {

    public static JComponent FormatComponentPanel = null;

    public static DiyJTextAreaScrollPane FormatInputArea = Helper.createDiyJTextAreaScrollPane("FormatInputArea");

    public static DiyJTextAreaScrollPane FormatOutputArea = Helper.createDiyJTextAreaScrollPane("FormatOutputArea");
    @Override
    public JComponent component() {

        FormatComponentPanel = new JPanel(new GridBagLayout());
        FormatComponentPanel.setBackground(Color.WHITE);
        FormatComponentPanel.setName("FormatComponentPanel");

        FormatComponentPanel.add(FormatMenuPanel(),new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        DiyJButton FormatExecuteButton = new DiyJButton("Format");
        FormatExecuteButton.setName("FormatExecuteButton");
        FormatComponentPanel.add(FormatExecuteButton, new GridBagConstraints(
                0,1,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        FormatComponentPanel.add(FormatEditorPanel(),new GridBagConstraints(
                0,2,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));


        return FormatComponentPanel;
    }


    public JComponent FormatMenuPanel(){
        JPanel FormatMenuPanel = new JPanel(new GridBagLayout());
        FormatMenuPanel.setName("FormatMenuPanel");
        FormatMenuPanel.setBackground(Color.WHITE);
        FormatMenuPanel.setPreferredSize(new Dimension(0,30));

        DiyJTextField FormatMenuTargetField = new DiyJTextField("Target");
        FormatMenuTargetField.setName("FormatMenuTargetField");
        FormatMenuTargetField.setPreferredSize(new Dimension(0,0));

        DiyJTextField FormatMenuResultField = new DiyJTextField("Result");
        FormatMenuResultField.setName("FormatMenuResultField");
        FormatMenuResultField.setPreferredSize(new Dimension(0,0));

        DiyJTextField FormatMenuKeywordField = new DiyJTextField("Keyword");
        FormatMenuKeywordField.setName("FormatMenuKeywordField");
        FormatMenuKeywordField.setPreferredSize(new Dimension(0,0));

        JComboBox<String> FormatMenuModeBox = new JComboBox<>(new String[]{"Change Separator","Add AllFix","Add Suffix","Add Prefix","Remove AllFix","Remove Suffix","Remove Prefix"});
        FormatMenuModeBox.setName("FormatMenuModeBox");
        FormatMenuModeBox.setPreferredSize(new Dimension(0,0));

        FormatMenuPanel.add(FormatMenuTargetField, new GridBagConstraints(
                0,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));
        FormatMenuPanel.add(FormatMenuResultField, new GridBagConstraints(
                1,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));
        FormatMenuPanel.add(FormatMenuKeywordField, new GridBagConstraints(
                2,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));
        FormatMenuPanel.add(FormatMenuModeBox, new GridBagConstraints(
                3,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));


        return FormatMenuPanel;
    }

    public JComponent FormatEditorPanel(){
        DiyVariablePanel FormatEditorPanel = new DiyVariablePanel(
                FormatInputArea, "FormatInputEditor",
                FormatOutputArea, "FormatOutputEditor",
                DiyVariablePanel.View.HORIZONTAL
        );
        FormatEditorPanel.setPreferredSize(new Dimension(0,0));

        return FormatEditorPanel;
    }

}
