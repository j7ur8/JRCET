package jrcet.diycomponents;

import burp.lib.Helper;
import jrcet.Main;
import jrcet.frame.setting.Setting;
import jrcet.frame.tools.Dencrypt.Base.BaseComponent;
import jrcet.frame.tools.Dencrypt.Unicode.Unicode;
import jrcet.frame.tools.Dencrypt.Unicode.UnicodeComponent;
import sun.awt.AWTAccessor;
import sun.rmi.runtime.NewThreadAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static jrcet.frame.setting.Setting.class2ClickedDiyJTabBorderColor;
import static jrcet.frame.setting.Setting.class2DefaultDiyJTabBorderColor;
import static jrcet.frame.tools.Dencrypt.DencryptComponent.changeMainPanelBySticker;
import static jrcet.frame.tools.Dencrypt.DencryptComponent.getNewStickerLabel;

public class DiyJAddLabel extends JLabel implements MouseListener {

    private JComponent mapPanel = null;

    public DiyJAddLabel(String labelName) {
        setOpaque(true);
        setText(labelName);
        setBackground(Color.WHITE);
        setHorizontalAlignment(JLabel.CENTER);
        setFont(new Font("微软雅黑", Font.PLAIN,12));
        setPreferredSize(new Dimension(50,15));
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(1,0,0,0),BorderFactory.createMatteBorder(0,0,1,0,class2DefaultDiyJTabBorderColor)));

        addMouseListener(this);
    }

    public DiyJAddLabel(String labelName,boolean flag){
        this(labelName);
        if(flag){
            setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(2,0,0,0),BorderFactory.createMatteBorder(0,0,2,0,class2ClickedDiyJTabBorderColor)));
        }

    }

    public void setPanel(JComponent targetPanel){
        mapPanel = targetPanel;
    }

    public JComponent getPanel(){
        return mapPanel!=null?mapPanel:new JPanel();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {

        for(Component i:this.getParent().getComponents()){
            if(i instanceof DiyJAddLabel){
                ((DiyJAddLabel)i).setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(1,0,0,0),BorderFactory.createMatteBorder(0,0,1,0,class2DefaultDiyJTabBorderColor)));
            }
        }


        DiyJAddLabel eLabel = (DiyJAddLabel)e.getSource();
        String[] eNameArray = eLabel.getName().split("(?=[A-Z])");
        String eName = eNameArray[eNameArray.length-2];
        String eIndex = eName.substring(eName.length()-1);
        if(Objects.equals(eName, "Add")){
            JPanel eTagTabPanel = (JPanel) eLabel.getParent();
            DiyJAddLabel newStickerLabel = getNewStickerLabel(eTagTabPanel);
            eTagTabPanel.add(newStickerLabel,eTagTabPanel.getComponents().length-1);
            eTagTabPanel.updateUI();

            newStickerLabel.mousePressed(new MouseEvent(newStickerLabel,e.getID(),e.getWhen(),e.getModifiers(),e.getX(),e.getY(),e.getClickCount(),e.isPopupTrigger()));
        }else{
            eLabel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(2,0,0,0),BorderFactory.createMatteBorder(0,0,2,0,class2ClickedDiyJTabBorderColor)));
            JComponent rootPanel;
            switch (eNameArray[0]){
                case "Base":
                    changeMainPanelBySticker("BaseComponentPanel", BaseComponent.MainPanelHashMap, eIndex, new GridBagConstraints(
                            0,1,
                            2,1,
                            1,1,
                            GridBagConstraints.CENTER,
                            GridBagConstraints.BOTH,
                            new Insets(0,0,0,0),
                            0,0
                    ));
                    break;
                case "Unicode":
                    changeMainPanelBySticker("UnicodeComponentPanel", UnicodeComponent.MainPanelHashMap, eIndex, new GridBagConstraints(
                            0,1,
                            2,1,
                            1,1,
                            GridBagConstraints.CENTER,
                            GridBagConstraints.BOTH,
                            new Insets(5,5,5,5),
                            0,0
                    ));
                    break;
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