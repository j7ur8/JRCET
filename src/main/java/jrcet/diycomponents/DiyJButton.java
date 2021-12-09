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
        addMouseListener(this);
        setFont(new Font("宋体",0,12));
        setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        setOpaque(true);
        setText(text);
        this.addActionListener( (e)->{

//            for(Component i:this.getParent().getComponents()){
//                if(i instanceof DiyJButton){
//                    DiyJButton ii=(DiyJButton)i;
//                    ii.setBackground(new Color(238,238,238));
//                    ii.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
//                    ii.clicked=false;
//                }
//            }
//            this.clicked=true;
//            this.setBackground(new Color(251,251,251));
//            if (this.getHeight()==25){
//                this.setBorder(BorderFactory.createMatteBorder(0,0,2,0,new Color(255,102,51)));
//            }
//
//            JPanel nowJButtonParent= (JPanel) this.getParent();
//            String targetLoadedPanelName=e.getActionCommand();
//            JComponent targetLoadedPanel=variablePanelMap.get(targetLoadedPanelName);
//            nowJButtonParent.remove(nowJButtonParent.getComponents().length-1);
//            nowJButtonParent.add(targetLoadedPanel);
//            nowJButtonParent.revalidate();
//            nowJButtonParent.validate();
//            nowJButtonParent.repaint();

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
        if (e.getSource() != this || clicked) return;
        setBackground(new Color(251,251,251));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() != this || clicked) return;
        setBackground(new Color(238,238,238));
    }

}
