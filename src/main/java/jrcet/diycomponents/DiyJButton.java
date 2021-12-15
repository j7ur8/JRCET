package jrcet.diycomponents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

public class DiyJButton extends JButton implements MouseListener {

    public boolean clicked=false;
    public Map<String,JComponent> variablePanelMap=new HashMap<String,JComponent>();
    public DiyJButton(String text) {
        setPreferredSize(new Dimension(160,30));
        addMouseListener(this);
        setFocusPainted(false);
        setFont(new Font("微软雅黑",0,14));
        setOpaque(true);
        setText(text);

        this.addActionListener( (e)->{

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

}
