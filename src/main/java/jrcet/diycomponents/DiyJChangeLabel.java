package jrcet.diycomponents;

import jrcet.frame.Setting.Setting;
import jrcet.help.Helper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

import static jrcet.frame.Dencrypt.Base.BaseComponent.BaseComponentPanel;

public class DiyJChangeLabel extends DiyJTabLabel  {

    public boolean FirstClicked = false;
    public boolean SecondClicked = false;

    public DiyJChangeLabel(String labelName){
        super(labelName);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }


    @Override
    public void mousePressed(MouseEvent e) {
        if (SecondClicked){
            SecondClicked = false;
            return;
        }else{
            FirstClicked = !FirstClicked;
            SecondClicked = true;
        }

        if(mapPanel!=null){
            DiyJChangeLabel eLabel = (DiyJChangeLabel)e.getSource();
            String eLabelName = eLabel.getName();
            String eLabelText = eLabel.getText();
            eLabelName = eLabelName.startsWith("JwtMainControlMode")?"JwtMainControlMode":eLabelName;
            eLabelName = eLabelName.startsWith("BaseMainControlMode")?"BaseMainControlMode":eLabelName;
            JComponent rootPanel = null;
            DiyJLabel tFieldLabel = null;
            if(eLabel.getBorder()!=null ){
                eLabel.setBorder(null);
                switch (eLabelName) {
                    case "BaseMainControlMode":
                        tFieldLabel = (DiyJLabel) Helper.getComponent(BaseComponentPanel, "BaseMainControlFieldLabel");
                        assert tFieldLabel != null;
                        tFieldLabel.setText("");
                        break;
                    case "JwtMainControlMode":
//                        Jwt.JwtModeType = null;
                        rootPanel = (JComponent) eLabel.getParent().getParent();
                        rootPanel.remove(rootPanel.getComponentCount()-1);
                        rootPanel.remove(rootPanel.getComponentCount()-1);
                        rootPanel.add(Helper.blackPanel(),new GridBagConstraints(
                                0,rootPanel.getComponentCount(),
                                1,1,
                                1,0,
                                GridBagConstraints.CENTER,
                                GridBagConstraints.BOTH,
                                new Insets(0,0,0,0),
                                0,0
                        ));
                        rootPanel.add(Helper.blackPanel(),new GridBagConstraints(
                                0,rootPanel.getComponentCount(),
                                1,1,
                                1,1,
                                GridBagConstraints.CENTER,
                                GridBagConstraints.BOTH,
                                new Insets(0,0,0,0),
                                0,0
                        ));
                        rootPanel.updateUI();
                        break;
                }
            }else{
                for(Component i:this.getParent().getComponents()){
                    if(i instanceof DiyJChangeLabel){
                        DiyJChangeLabel ii=(DiyJChangeLabel)i;
                        ii.setBorder(null);
                    }
                }
                eLabel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(2,0,0,0),BorderFactory.createMatteBorder(0,0,2,0,Setting.orange)));
                switch (eLabelName){
                    case "BaseMainControlMode":
                        tFieldLabel = (DiyJLabel) Helper.getComponent(BaseComponentPanel, "BaseMainControlFieldLabel");
                        assert tFieldLabel != null;
                        tFieldLabel.setText("当前模式: "+eLabelText);
                        break;
                    case "JwtMainControlMode":
//                        Jwt.JwtModeType = eLabelText;
                        rootPanel = (JComponent) eLabel.getParent().getParent();
                        rootPanel.remove(rootPanel.getComponentCount()-1);
                        rootPanel.remove(rootPanel.getComponentCount()-1);
                        rootPanel.add(mapPanel,new GridBagConstraints(
                                0,rootPanel.getComponentCount(),
                                1,1,
                                1,0,
                                GridBagConstraints.CENTER,
                                GridBagConstraints.BOTH,
                                new Insets(0,0,0,0),
                                0,0
                        ));
                        rootPanel.add(Helper.blackPanel(),new GridBagConstraints(
                                0,rootPanel.getComponentCount(),
                                1,1,
                                1,1,
                                GridBagConstraints.CENTER,
                                GridBagConstraints.BOTH,
                                new Insets(0,0,0,0),
                                0,0
                        ));
                        rootPanel.updateUI();
                        break;
                }
            }

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
