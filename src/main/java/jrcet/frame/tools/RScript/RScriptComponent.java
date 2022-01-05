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

    @Override
    public JComponent main() {
        JPanel RScriptPanel = new JPanel(new GridBagLayout());
        RScriptPanel.setOpaque(true);
        RScriptPanel.setBackground(Color.WHITE);

        JPanel RScriptMenuPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,5,5));
        RScriptMenuPanel.setOpaque(true);
        RScriptMenuPanel.setBackground(Color.WHITE);
        RScriptMenuPanel.setPreferredSize(new Dimension(0,40));
        RScriptMenuPanel.setBorder(BorderFactory.createMatteBorder(0,0,1,0,new Color(203,208,209)));
        RScriptMenuPanel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        DiyJCheckBox RScriptMenuSessionCheck = new DiyJCheckBox("Session");
        RScriptMenuPanel.add(RScriptMenuSessionCheck);

        DiyJButton RScriptMenuCopyButton = new DiyJButton("Copy");
        RScriptMenuPanel.add(RScriptMenuCopyButton);

        GridBagConstraints RScriptMenuPanelProperty = new GridBagConstraints();
        RScriptMenuPanelProperty.gridx=0;RScriptMenuPanelProperty.gridy=0;
        RScriptMenuPanelProperty.weightx=1;RScriptMenuPanelProperty.weighty=0;
        RScriptMenuPanelProperty.fill=GridBagConstraints.BOTH;
        RScriptPanel.add(RScriptMenuPanel,RScriptMenuPanelProperty);

        RSyntaxTextArea RScriptJTextArea = new RSyntaxTextArea();
        RScriptJTextArea.setPreferredSize(new Dimension(0,0));
        RScriptJTextArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        RScriptJTextArea.setCodeFoldingEnabled(true);
        RTextScrollPane RScriptJTextAreaScroll = new RTextScrollPane(RScriptJTextArea);
        GridBagConstraints RScriptJTextAreaScrollProperty = new GridBagConstraints();
        RScriptJTextAreaScrollProperty.gridx=0;RScriptJTextAreaScrollProperty.gridy=1;
        RScriptJTextAreaScrollProperty.weightx=100;RScriptJTextAreaScrollProperty.weighty=100;
        RScriptJTextAreaScrollProperty.fill=GridBagConstraints.BOTH;
        RScriptPanel.add(RScriptJTextAreaScroll,RScriptJTextAreaScrollProperty);

        return RScriptPanel;
    }


}
