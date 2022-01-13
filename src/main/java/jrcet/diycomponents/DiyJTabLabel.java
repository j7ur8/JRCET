package jrcet.diycomponents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

public class DiyJTabLabel extends DiyJLabel implements MouseListener {

    private final Map<String,JComponent> mapPanel= new HashMap<>();
    private final Map<String,Object> mapStream = new HashMap<>();
    private Color defaultColor=null;
    private Color clickColor=null;


    public DiyJTabLabel(String labelName, Color defaultColor, Color clickColor){
        super(labelName);
        addMouseListener(this);
        this.defaultColor=defaultColor; this.clickColor=clickColor;
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(1,0,0,0),BorderFactory.createMatteBorder(0,0,1,0,defaultColor)));
    }

    public DiyJTabLabel(String labelName,Color defaultColor,Color clickColor,boolean flag){
        super(labelName);
        addMouseListener(this);
        this.defaultColor=defaultColor; this.clickColor=clickColor;
        if(flag){
            setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(2,0,0,0),BorderFactory.createMatteBorder(0,0,2,0,clickColor)));
        }
    }

    public void setMapPanel(JComponent targetPanel){
        mapPanel.put(getText(),targetPanel);
    }

    public void setMapStream(String k,Object v){
        mapStream.put(k,v);
    }

    public JComponent getMapPanel(String name){
        return mapPanel.get(name);
    }

    public Object getMapStream(String k){
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
                ii.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(1,0,0,0),BorderFactory.createMatteBorder(0,0,1,0,defaultColor)));
            }
        }

        if(mapPanel.get(getText())!=null){
            DiyJLabel hitButtonLabel = (DiyJLabel)e.getSource();
            hitButtonLabel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(2,0,0,0),BorderFactory.createMatteBorder(0,0,2,0,clickColor)));
            JComponent parentPanel=(JPanel)hitButtonLabel.getParent().getParent();
            int parentComponentsNums = parentPanel.getComponents().length;
            parentPanel.remove(parentComponentsNums-1);
            GridBagConstraints defaultContentPanel = new GridBagConstraints(
                    0,1,
                    parentComponentsNums-1,1,
                    1,1,
                    GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH,
                    new Insets(0,0,0,0),
                    0,0
            );
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
