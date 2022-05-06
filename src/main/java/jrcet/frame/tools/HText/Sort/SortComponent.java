package jrcet.frame.tools.HText.Sort;

import burp.lib.Helper;
import jrcet.Main;
import jrcet.diycomponents.DiyJComponent;
import jrcet.diycomponents.DiyJTextArea.ui.rsyntaxtextarea.RSyntaxTextArea;
import jrcet.diycomponents.DiyJTextArea.ui.rsyntaxtextarea.SyntaxConstants;
import jrcet.diycomponents.DiyJTextArea.ui.rtextarea.RTextScrollPane;
import jrcet.frame.setting.Setting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SortComponent extends DiyJComponent {

    public static JComponent SortComponentPanel = null;

    @Override
    public JComponent main() {

        SortComponentPanel = new JPanel(new GridBagLayout());
        SortComponentPanel.setBackground(Color.WHITE);
        SortComponentPanel.setName("SortComponentPanel");

        SortComponentPanel.add(SortMainPanel(),new GridBagConstraints(
                0,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return SortComponentPanel;
    }

    public JComponent SortMainPanel(){
        JPanel SortMainPanel = new JPanel(new GridBagLayout());
        SortMainPanel.setName("SortMainPanel");
        SortMainPanel.setBackground(Color.WHITE);
        SortMainPanel.setPreferredSize(new Dimension(0,0));

        SortMainPanel.add(SortMainBorderPanel(),new GridBagConstraints(
                0,0,
                1,1,
                0.2,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        SortMainPanel.add(SortMainInputAreaScrollPane(),new GridBagConstraints(
                1,0,
                1,1,
                0.6,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        SortMainPanel.add(SortMainBorderPanel(),new GridBagConstraints(
                2,0,
                1,1,
                0.2,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return SortMainPanel;
    }

    public JComponent SortMainInputAreaScrollPane(){
        RSyntaxTextArea SortMainInputArea = new RSyntaxTextArea();
        SortMainInputArea.setName("SortMainInputArea");
        SortMainInputArea.setCodeFoldingEnabled(true);
        SortMainInputArea.setLineWrap(true);
        SortMainInputArea.setText("#请输入文件路径或\\n分割的字符串...");
        SortMainInputArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        SortMainInputArea.addKeyListener(new SortMainInputAreaKeyListener());

        RTextScrollPane SortMainInputAreaScrollPane = new RTextScrollPane(SortMainInputArea);
        SortMainInputAreaScrollPane.setHorizontalScrollBarPolicy(RTextScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        SortMainInputAreaScrollPane.setName("SortMainInputAreaScrollPane");
        SortMainInputAreaScrollPane.setPreferredSize(new Dimension(0,0));
        SortMainInputAreaScrollPane.setBorder(null);

        return  SortMainInputAreaScrollPane;
    }
    public JComponent SortMainBorderPanel(){
        JPanel SortMainBorderPanel = new JPanel();
        SortMainBorderPanel.setName("SortMainBorderPanel");

        return SortMainBorderPanel;
    }

    static class SortMainInputAreaKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {
            if( (e.getModifiers()== InputEvent.CTRL_MASK || e.getModifiers() == InputEvent.META_MASK) && e.getKeyCode()==71){
                JTextArea eArea = (JTextArea) e.getSource();
                RSyntaxTextArea outputArea = null;
                if ("SortMainInputArea".equals(eArea.getName())) {
                    outputArea = (RSyntaxTextArea) Helper.getComponent((JComponent) eArea.getParent().getParent().getParent(), "SortMainInputArea");
                    assert outputArea != null;
                    outputArea.setText(Sort.uniq(eArea.getText()));
                    outputArea.updateUI();
                }
            }
        }
    }

}
