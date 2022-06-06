package jrcet.frame.tools.HText.Parsepy;

import jrcet.diycomponents.DiyJComponent;
import jrcet.diycomponents.DiyJTextArea.ui.rsyntaxtextarea.RSyntaxTextArea;
import jrcet.diycomponents.DiyJTextArea.ui.rsyntaxtextarea.SyntaxConstants;
import jrcet.diycomponents.DiyJTextArea.ui.rtextarea.RTextScrollPane;
import jrcet.help.Helper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ParsepyComponent extends DiyJComponent {

    public static JComponent ParsepyComponentPanel = null;

    @Override
    public JComponent main() {

        ParsepyComponentPanel = new JPanel(new GridBagLayout());
        ParsepyComponentPanel.setBackground(Color.WHITE);
        ParsepyComponentPanel.setName("ParsepyComponentPanel");

        ParsepyComponentPanel.add(ParsepyMainPanel(),new GridBagConstraints(
                0,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return ParsepyComponentPanel;
    }

    public JComponent ParsepyMainPanel(){
        JPanel ParsepyMainPanel = new JPanel(new GridBagLayout());
        ParsepyMainPanel.setName("ParsepyMainPanel");
        ParsepyMainPanel.setBackground(Color.WHITE);
        ParsepyMainPanel.setPreferredSize(new Dimension(0,0));

        ParsepyMainPanel.add(ParsepyMainBorderPanel(),new GridBagConstraints(
                0,0,
                1,1,
                0.2,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        ParsepyMainPanel.add(ParsepyMainInputAreaScrollPane(),new GridBagConstraints(
                1,0,
                1,1,
                0.6,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        ParsepyMainPanel.add(ParsepyMainBorderPanel(),new GridBagConstraints(
                2,0,
                1,1,
                0.2,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return ParsepyMainPanel;
    }

    public JComponent ParsepyMainInputAreaScrollPane(){
        RSyntaxTextArea ParsepyMainInputArea = new RSyntaxTextArea();
        ParsepyMainInputArea.setName("ParsepyMainInputArea");
        ParsepyMainInputArea.setCodeFoldingEnabled(true);
        ParsepyMainInputArea.setLineWrap(true);
        ParsepyMainInputArea.setText("#请输入文件路径或\\n分割的字符串...");
        ParsepyMainInputArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        ParsepyMainInputArea.addKeyListener(new ParsepyMainInputAreaKeyListener());

        RTextScrollPane ParsepyMainInputAreaScrollPane = new RTextScrollPane(ParsepyMainInputArea);
        ParsepyMainInputAreaScrollPane.setHorizontalScrollBarPolicy(RTextScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        ParsepyMainInputAreaScrollPane.setName("ParsepyMainInputAreaScrollPane");
        ParsepyMainInputAreaScrollPane.setPreferredSize(new Dimension(0,0));
        ParsepyMainInputAreaScrollPane.setBorder(null);

        return  ParsepyMainInputAreaScrollPane;
    }
    public JComponent ParsepyMainBorderPanel(){
        JPanel ParsepyMainBorderPanel = new JPanel();
        ParsepyMainBorderPanel.setName("ParsepyMainBorderPanel");

        return ParsepyMainBorderPanel;
    }

    static class ParsepyMainInputAreaKeyListener implements KeyListener {

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
                if ("ParsepyMainInputArea".equals(eArea.getName())) {
                    outputArea = (RSyntaxTextArea) Helper.getComponent((JComponent) eArea.getParent().getParent().getParent(), "ParsepyMainInputArea");
                    assert outputArea != null;
                    outputArea.setText(Parsepy.getPYInitial(eArea.getText()));
                    outputArea.updateUI();
                }
            }
        }
    }

}
