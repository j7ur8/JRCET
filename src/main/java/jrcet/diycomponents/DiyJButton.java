package jrcet.diycomponents;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

public class DiyJButton extends JButton implements MouseListener, ClipboardOwner {

//    public boolean clicked=false;
//    public Map<String,JComponent> variablePanelMap=new HashMap<String,JComponent>();
    public DiyJButton(String text) {
        setPreferredSize(new Dimension(160,30));
        addMouseListener(this);
        setFocusPainted(false);
        setFont(new Font("微软雅黑",0,14));
        setOpaque(true);
        setText(text);

        this.addActionListener( (e)->{
            JButton targetButton = (JButton)e.getSource();
            JPanel targetPanel = (JPanel) targetButton.getParent().getParent();
            JScrollPane targetJScrollPane = (JScrollPane) targetPanel.getComponent(1);
            JTextArea targetJTextArea = (JTextArea) targetJScrollPane.getViewport().getComponent(0);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(targetJTextArea.getText()), this);
//            System.out.println();
//
        });
    }


    @Override
    public void mouseClicked(MouseEvent e) {


    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void lostOwnership(Clipboard clipboard, Transferable contents) {

    }
}
