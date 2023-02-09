package jrcet.frame.Tools.HText.Sort;

import jrcet.diycomponents.DiyJTextAreaScrollPane;
import jrcet.help.Helper;
import jrcet.diycomponents.DiyJComponent;




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

        SortMainPanel.add(Helper.blackPanel(),new GridBagConstraints(
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

        SortMainPanel.add(Helper.blackPanel(),new GridBagConstraints(
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
        DiyJTextAreaScrollPane SortMainInputAreaScrollPane = new DiyJTextAreaScrollPane("SortMainInputArea");

        SortMainInputAreaScrollPane.setText("#请输入文件路径或\\n分割的字符串...");

        SortMainInputAreaScrollPane.addKeyListener(new SortMainInputAreaKeyListener());



        return  SortMainInputAreaScrollPane;
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
                JTextArea outputArea = null;
                if ("SortMainInputArea".equals(eArea.getName())) {
                    outputArea = (JTextArea) Helper.getComponent((JComponent) eArea.getParent().getParent().getParent(), "SortMainInputArea");
                    assert outputArea != null;
                    outputArea.setText(Sort.uniq(eArea.getText()));
                    outputArea.updateUI();
                }
            }
        }
    }

}
