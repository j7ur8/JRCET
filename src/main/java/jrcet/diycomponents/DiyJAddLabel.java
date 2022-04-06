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
    private final Map<String, String> specialKeyMap = new HashMap<>();

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
        specialKeyMap.put("1","Default");
        specialKeyMap.put("···","Add");
    }

    public void setMapPanel(JComponent targetPanel){
        if(specialKeyMap.containsKey(getText())){
            mapPanel.put(specialKeyMap.get(getText()),targetPanel);
        }else {
            mapPanel.put(getText(),targetPanel);
        }
    }

    public JComponent getMapPanel(String k){
        if(mapPanel.containsKey(k)){
            return mapPanel.get(k);
        }else{
            JComponent blackPanel = new JPanel();
            blackPanel.setName("NullPanelBySomeLabel");
            return blackPanel;
        }

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