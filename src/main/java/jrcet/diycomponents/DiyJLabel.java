package jrcet.diycomponents;

import jrcet.help.Helper;
import jrcet.frame.Dencrypt.Aes.Aes;
import jrcet.frame.Dencrypt.Rsa.Rsa;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;


public class DiyJLabel extends JLabel implements MouseListener{

    public DiyJLabel(String labelName){
        setOpaque(true);
        setText(labelName);
        setBackground(Color.WHITE);
        setHorizontalAlignment(JLabel.CENTER);
        setFont(new Font("微软雅黑", Font.PLAIN,14));
        setPreferredSize(new Dimension(80,27));
        addMouseListener(this);
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
