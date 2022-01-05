package jrcet.diycomponents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

public class DiyJLabel<Q> extends JLabel implements MouseListener {

    private Map<String,JComponent> mapPanel=new HashMap<String,JComponent>();
    private Map<String,Q> mapStream = new HashMap<String,Q>();

    public Boolean clicked=false;

    public DiyJLabel(String labelName){
        addMouseListener(this);
        setText(labelName);
        setBackground(Color.WHITE);
        setOpaque(true);
        setHorizontalAlignment(JLabel.CENTER);
        setFont(new Font("微软雅黑",0,14));
        setPreferredSize(new Dimension(80,27));
    }

    public void setMapPanel(JComponent targetPanel){
        mapPanel.put(getText(),targetPanel);
    }

    public void setMapStream(String k,Q v){
        mapStream.put(k,v);
    }

    public JComponent getMapPanel(String name){
        return mapPanel.get(name);
    }

    public Q getMapStream(String k){
        return mapStream.get(k);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {

        for(Component i:this.getParent().getComponents()){
            if(i instanceof DiyJLabel){
                DiyJLabel ii=(DiyJLabel)i;
                ii.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(1,0,0,0),BorderFactory.createMatteBorder(0,0,1,0,new Color(203,208,209))));
                ii.clicked=false;
            }
        }

        if(mapPanel.get(getText())!=null){
            DiyJLabel hitButtonLabel = (DiyJLabel)e.getSource();
            hitButtonLabel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(2,0,0,0),BorderFactory.createMatteBorder(0,0,2,0,new Color(255,102,51))));
            JComponent parentPanel=(JPanel)hitButtonLabel.getParent().getParent();
            int parentComponentsNums = parentPanel.getComponents().length;
            parentPanel.remove(parentComponentsNums-1);
            GridBagConstraints defaultContentPanel = new GridBagConstraints();
            defaultContentPanel.gridy = 1; defaultContentPanel.gridx= 0;
            defaultContentPanel.gridwidth=parentComponentsNums-1;
            defaultContentPanel.fill=GridBagConstraints.BOTH;
            defaultContentPanel.weightx = 100; defaultContentPanel.weighty = 100;
            parentPanel.add(mapPanel.get(getText()),defaultContentPanel);
            parentPanel.validate();
            parentPanel.repaint();
        }

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
