package jrcet.frame.HText.Parsepy;


import jrcet.diycomponents.DiyJButton;
import jrcet.diycomponents.DiyJComponent;


import jrcet.diycomponents.DiyJTextAreaScrollPane;
import jrcet.diycomponents.DiyVariablePanel;
import jrcet.help.Helper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ParsepyComponent extends DiyJComponent {

    public static JComponent ParsepyComponentPanel = null;

    public static DiyJTextAreaScrollPane ParsepyInputArea = Helper.createDiyJTextAreaScrollPane("ParsepyInputArea");

    public static DiyJTextAreaScrollPane ParsepyOutputArea = Helper.createDiyJTextAreaScrollPane("ParsepyOutputArea");

    @Override
    public JComponent component() {

        ParsepyComponentPanel = new JPanel(new GridBagLayout());
        ParsepyComponentPanel.setBackground(Color.WHITE);
        ParsepyComponentPanel.setName("ParsepyComponentPanel");
        ParsepyComponentPanel.setPreferredSize(new Dimension(0,0));

        DiyJButton ParsepyParseButton = new DiyJButton("Parse");
        ParsepyParseButton.setName("ParsepyParseButton");
        ParsepyComponentPanel.add(ParsepyParseButton,new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        ParsepyComponentPanel.add(ParsepyEditorPanel(), new GridBagConstraints(
                0,1,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return ParsepyComponentPanel;
    }



    public JComponent ParsepyEditorPanel(){
        DiyVariablePanel ParsepyEditorPanel = new DiyVariablePanel(
                ParsepyInputArea,"ParsepyInputEditor",
                ParsepyOutputArea, "ParsepyOutputEditor",
                DiyVariablePanel.View.HORIZONTAL
        );

        ParsepyEditorPanel.setPreferredSize(new Dimension(0,0));
        return ParsepyEditorPanel;
    }


}
