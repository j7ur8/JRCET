package jrcet.diycomponents;

import jrcet.frame.setting.Setting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DiyJTabLabel extends DiyJLabel implements MouseListener {

    protected JComponent mapPanel=null;

    public DiyJTabLabel(String labelName){
        super(labelName);
        addMouseListener(this);
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(1,0,0,0),BorderFactory.createMatteBorder(0,0,1,0,Setting.gray)));
    }

    public DiyJTabLabel(String labelName, boolean flag){
        super(labelName);
        addMouseListener(this);
        if(flag){
            setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(2,0,0,0),BorderFactory.createMatteBorder(0,0,2,0, Setting.orange)));
        }
    }

    public void setPanel(JComponent targetPanel){

        mapPanel=targetPanel;
    }

    public JComponent getPanel(){

        return mapPanel==null?new JPanel():mapPanel;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {

        for(Component i:this.getParent().getComponents()){
            if(i instanceof DiyJLabel){
                DiyJLabel ii=(DiyJLabel)i;
                ii.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(1,0,0,0),BorderFactory.createMatteBorder(0,0,1,0, Setting.gray)));
            }
        }

        if(mapPanel!=null){
            DiyJLabel hitButtonLabel = (DiyJLabel)e.getSource();
            hitButtonLabel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(2,0,0,0),BorderFactory.createMatteBorder(0,0,2,0,Setting.orange)));
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
