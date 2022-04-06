package jrcet.diycomponents;

import jrcet.frame.setting.Setting;
import jrcet.frame.tools.Dencrypt.Base.BaseComponent;
import jrcet.frame.tools.Dencrypt.DencryptComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DiyJAddLabel extends JLabel implements MouseListener {


    private final Map<String, JComponent> mapPanel= new HashMap<>();

    public DiyJAddLabel(String labelName,boolean flag) {
        setOpaque(true);
        setText(labelName);
        setBackground(Color.WHITE);
        setHorizontalAlignment(JLabel.CENTER);
        setFont(new Font("微软雅黑", Font.PLAIN,12));
        setPreferredSize(new Dimension(50,15));
        setBorder(BorderFactory.createMatteBorder(0,1,1,0,Setting.class4DefaultDiyJTabBorderColor));
        addMouseListener(this);
        setBorder(BorderFactory.createMatteBorder(0,1,0,0,Setting.class4DefaultDiyJTabBorderColor));

    }


    public void setMapPanel(JComponent targetPanel){
        mapPanel.put(getText(),targetPanel);
    }
    public JComponent getMapPanel(String name){
        return mapPanel.get(name);
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