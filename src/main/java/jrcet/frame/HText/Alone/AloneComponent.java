package jrcet.frame.HText.Alone;

import jrcet.diycomponents.DiyJTextAreaScrollPane;
import jrcet.help.Helper;
import jrcet.diycomponents.DiyJComponent;



import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class AloneComponent extends DiyJComponent {

    public static JComponent AloneComponentPanel = null;

    @Override
    public JComponent main() {

        AloneComponentPanel = new JPanel(new GridBagLayout());
        AloneComponentPanel.setName("AloneComponentPanel");

        AloneComponentPanel.add(AloneMainPanel(),new GridBagConstraints(
                0,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return AloneComponentPanel;
    }

    public JComponent AloneMainPanel(){
        JPanel AloneMainPanel = new JPanel(new GridBagLayout());
        AloneMainPanel.setName("AloneMainPanel");
        AloneMainPanel.setBackground(Color.WHITE);
        AloneMainPanel.setPreferredSize(new Dimension(0,0));


        AloneMainPanel.add(AloneMainBorderPanel(),new GridBagConstraints(
                0,0,
                1,1,
                0.2,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        AloneMainPanel.add(AloneMainInputAreaScrollPane(),new GridBagConstraints(
                1,0,
                1,1,
                0.6,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        AloneMainPanel.add(AloneMainBorderPanel(),new GridBagConstraints(
                2,0,
                1,1,
                0.2,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return AloneMainPanel;
    }

    public JComponent AloneMainInputAreaScrollPane(){

        DiyJTextAreaScrollPane AloneMainInputAreaScrollPane = new DiyJTextAreaScrollPane("AloneMainInputArea");


        AloneMainInputAreaScrollPane.setText("#请输入文件路径或\\n分割的字符串...");

        AloneMainInputAreaScrollPane.addKeyListener(new AloneMainInputAreaKeyListener());


        return AloneMainInputAreaScrollPane;
    }

    public JComponent AloneMainBorderPanel(){
        JPanel AloneMainBorderPanel = new JPanel();
        AloneMainBorderPanel.setName("AloneMainBorderPanel");

        return AloneMainBorderPanel;
    }

    static class AloneMainInputAreaKeyListener implements KeyListener{

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
                if ("AloneMainInputArea".equals(eArea.getName())) {
                    outputArea = (JTextArea) Helper.getComponent((JComponent) eArea.getParent().getParent().getParent(), "AloneMainInputArea");
                    assert outputArea != null;
                    outputArea.setText(Alone.removeDuplication(eArea.getText()));
                }
                assert outputArea != null;
                outputArea.updateUI();
            }
        }
    }
}
