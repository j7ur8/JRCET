package jrcet.frame.Tools.RScript;

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

    @Override
    public JComponent main() {

        RScriptComponentPanel.setName("RScriptComponentPanel");
        RScriptComponentPanel.setBackground(Color.WHITE);

        RScriptComponentPanel.add(RScriptMenuTabPanel(),new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        RScriptComponentPanel.add(RScriptMainPanel(),new GridBagConstraints(
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

    public JComponent RScriptMenuTabPanel(){

        JPanel RScriptMenuTabPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,5,5));
        RScriptMenuTabPanel.setOpaque(false);
        RScriptMenuTabPanel.setBackground(Color.WHITE);
        RScriptMenuTabPanel.setName("RScriptMenuTabPanel");
        RScriptMenuTabPanel.setPreferredSize(new Dimension(0,40));
        RScriptMenuTabPanel.setBorder(BorderFactory.createMatteBorder(0,0,1,0,new Color(203,208,209)));

        DiyJCheckBox RScriptMenuSessionBox = new DiyJCheckBox("Session");
        RScriptMenuSessionBox.setName("RScriptMenuSessionBox");
        RScriptMenuTabPanel.add(RScriptMenuSessionBox);

        DiyJButton RScriptMenuCopyButton = new DiyJButton("Copy");
        RScriptMenuCopyButton.setName("RScriptMenuCopyButton");
        RScriptMenuTabPanel.add(RScriptMenuCopyButton);

        return RScriptMenuTabPanel;
    }

    public JComponent RScriptMainPanel(){
        JComponent RScriptMainPanel = new JPanel(new GridBagLayout());
        RScriptMainPanel.setName("RScriptMainPanel");
        RScriptMainPanel.setBackground(Color.WHITE);

        RScriptMainPanel.add(RScriptMainBorderPanel(),new GridBagConstraints(
                0,0,
                1,1,
                0.2,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        RScriptMainPanel.add(RScriptMainEditorScrollPane(),new GridBagConstraints(
                1,0,
                1,1,
                0.6,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        RScriptMainPanel.add(RScriptMainBorderPanel(),new GridBagConstraints(
                2,0,
                1,1,
                0.2,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return RScriptMainPanel;
    }

    public JComponent RScriptMainEditorScrollPane(){

        RSyntaxTextArea RScriptMainEditor = new RSyntaxTextArea();
        RScriptMainEditor.setName("RScriptMainEditor");
        RScriptMainEditor.setLineWrap(true);
        RScriptMainEditor.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        RScriptMainEditor.setCodeFoldingEnabled(true);

        RTextScrollPane RScriptMainEditorScrollPane = new RTextScrollPane(RScriptMainEditor);
        RScriptMainEditorScrollPane.setHorizontalScrollBarPolicy(RTextScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        RScriptMainEditorScrollPane.setPreferredSize(new Dimension(0,0));
        RScriptMainEditorScrollPane.setName("RScriptMainEditorScrollPane");
        RScriptMainEditorScrollPane.setBorder(null);

        return RScriptMainEditorScrollPane;
    }

    public JComponent RScriptMainBorderPanel(){
        JComponent RScriptMainBorderPanel = new JPanel();
        RScriptMainBorderPanel.setName("RScriptMainBorderPanel");

        return RScriptMainBorderPanel;
    }


}
