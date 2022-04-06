package jrcet.frame.tools.RScript;

import jrcet.diycomponents.DiyJButton;
import jrcet.diycomponents.DiyJCheckBox;
import jrcet.diycomponents.DiyJComponent;
import jrcet.diycomponents.DiyJTextArea.ui.rsyntaxtextarea.RSyntaxTextArea;
import jrcet.diycomponents.DiyJTextArea.ui.rsyntaxtextarea.SyntaxConstants;
import jrcet.diycomponents.DiyJTextArea.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import java.awt.*;

public class RScriptComponent extends DiyJComponent {

    public static JPanel RScriptComponentPanel = new JPanel(new GridBagLayout());
    public static RSyntaxTextArea RScriptMainEditor = new RSyntaxTextArea();

    @Override
    public JComponent main() {

        RScriptComponentPanel.setName("RScriptComponentPanel");
        RScriptComponentPanel.setOpaque(false);
        RScriptComponentPanel.setBackground(Color.WHITE);

        RScriptMainEditor.setName("RScriptMainEditor");
        RScriptMainEditor.setPreferredSize(new Dimension(0,0));
        RScriptMainEditor.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        RScriptMainEditor.setCodeFoldingEnabled(true);

        RTextScrollPane RScriptMainEditorScroll = new RTextScrollPane(RScriptMainEditor);
        RScriptMainEditorScroll.setName("RScriptMainEditorScroll");

        RScriptComponentPanel.add(RScriptMenuTabPanel(),new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        RScriptComponentPanel.add(RScriptMainEditorScroll,new GridBagConstraints(
                0,1,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return RScriptComponentPanel;
    }

    private JComponent RScriptMenuTabPanel(){

        JPanel RScriptMenuTabPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,5,5));
        RScriptMenuTabPanel.setOpaque(false);
        RScriptMenuTabPanel.setBackground(Color.WHITE);
        RScriptMenuTabPanel.setName("RScriptMenuTabPanel");
        RScriptMenuTabPanel.setPreferredSize(new Dimension(0,40));
        RScriptMenuTabPanel.setBorder(BorderFactory.createMatteBorder(0,0,1,0,new Color(203,208,209)));
        RScriptMenuTabPanel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        DiyJCheckBox RScriptMenuTabSessionButton = new DiyJCheckBox("Session");
        RScriptMenuTabSessionButton.setName("RScriptMenuTabSessionButton");
        RScriptMenuTabPanel.add(RScriptMenuTabSessionButton);

        DiyJButton RScriptMenuTabCopyButton = new DiyJButton("Copy");
        RScriptMenuTabCopyButton.setName("RScriptMenuTabCopyButton");
        RScriptMenuTabPanel.add(RScriptMenuTabCopyButton);

        return RScriptMenuTabPanel;
    }

}
