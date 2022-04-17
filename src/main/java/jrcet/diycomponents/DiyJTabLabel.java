package jrcet.diycomponents;

import jrcet.frame.setting.Setting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DiyJTabLabel extends DiyJLabel implements MouseListener {

    private JComponent mapPanel=null;
    private final Map<String,Object> mapStream = new HashMap<>();


    public DiyJTabLabel(String labelName){
        super(labelName);
        addMouseListener(this);
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(1,0,0,0),BorderFactory.createMatteBorder(0,0,1,0,Setting.class2DefaultDiyJTabBorderColor)));
    }

    public DiyJTabLabel(String labelName,boolean flag){
        super(labelName);
        addMouseListener(this);
        if(flag){
            setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(2,0,0,0),BorderFactory.createMatteBorder(0,0,2,0, Setting.class2ClickedDiyJTabBorderColor)));
        }
    }

    public void setPanel(JComponent targetPanel){
        mapPanel=targetPanel;
    }

    public JComponent getPanel(){
        return mapPanel==null?new JPanel():mapPanel;
    }

    public void setMapStream(String k,Object v){
        mapStream.put(k,v);
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
                ii.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(1,0,0,0),BorderFactory.createMatteBorder(0,0,1,0, Setting.class2DefaultDiyJTabBorderColor)));
            }
        }

        if(mapPanel!=null){
            DiyJLabel hitButtonLabel = (DiyJLabel)e.getSource();
            hitButtonLabel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(2,0,0,0),BorderFactory.createMatteBorder(0,0,2,0,Setting.class2ClickedDiyJTabBorderColor)));
            JComponent parentPanel=(JPanel)hitButtonLabel.getParent().getParent();
            int parentComponentsNums = parentPanel.getComponents().length;
            parentPanel.remove(parentComponentsNums-1);
            parentPanel.add(mapPanel,new GridBagConstraints(
                    0,1,
                    parentComponentsNums-1,1,
                    1,1,
                    GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH,
                    new Insets(0,0,0,0),
                    0,0
            ));
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
