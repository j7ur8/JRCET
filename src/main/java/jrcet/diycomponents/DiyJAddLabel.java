package jrcet.diycomponents;

import burp.lib.Helper;
import jrcet.Main;
import jrcet.frame.tools.Dencrypt.Aes.AesComponent;
import jrcet.frame.tools.Dencrypt.Base.BaseComponent;
import jrcet.frame.tools.Dencrypt.Rsa.RsaComponent;
import jrcet.frame.tools.Dencrypt.Unicode.UnicodeComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Objects;

import static jrcet.frame.setting.Setting.class2ClickedDiyJTabBorderColor;
import static jrcet.frame.setting.Setting.class2DefaultDiyJTabBorderColor;

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
            switch (eNameArray[0]){
                case "Base":
                    changeMainPanelBySticker("Base", BaseComponent.MainPanelHashMap, eIndex, new GridBagConstraints(
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
                    changeMainPanelBySticker("Unicode", UnicodeComponent.MainPanelHashMap, eIndex, new GridBagConstraints(
                            0,1,
                            2,1,
                            1,1,
                            GridBagConstraints.CENTER,
                            GridBagConstraints.BOTH,
                            new Insets(5,5,5,5),
                            0,0
                    ));
                    break;
                case "Aes":
                    changeMainPanelBySticker("Aes", AesComponent.MainPanelHashMap, eIndex, new GridBagConstraints(
                            0,1,
                            2,1,
                            1,1,
                            GridBagConstraints.CENTER,
                            GridBagConstraints.BOTH,
                            new Insets(5,5,5,5),
                            0,0
                    ));
                    break;
                case "Rsa":
                    changeMainPanelBySticker("Rsa", RsaComponent.MainPanelHashMap, eIndex, new GridBagConstraints(
                            0,1,
                            2,1,
                            1,0.7,
                            GridBagConstraints.CENTER,
                            GridBagConstraints.BOTH,
                            new Insets(5,5,5,5),
                            0,0
                    ));
                    break;
            }
        }
    }

    public static DiyJAddLabel getNewStickerLabel(JPanel eTagTabPanel){
        JComponent nPanel = null;
        String nName = String.valueOf(eTagTabPanel.getComponents().length);
        String nComponentName = eTagTabPanel.getComponent(0).getName().replace("1", nName);
        switch (eTagTabPanel.getName().split("(?=[A-Z])")[0]){
            case "Base":
                BaseComponent.MainPanelHashMap.put(nName,BaseComponent.getNewMainPanel());
                break;
            case "Unicode":
                UnicodeComponent.MainPanelHashMap.put(nName,UnicodeComponent.getNewMainPanel());
                break;
            case "Aes":
                AesComponent.MainPanelHashMap.put(nName,AesComponent.getNewMainPanel());
                break;
            case "Rsa":
                RsaComponent.MainPanelHashMap.put(nName,RsaComponent.getNewMainPanel());
        }

        DiyJAddLabel nStickerLabel = new DiyJAddLabel(nName);
        nStickerLabel.setName(nComponentName);
        nStickerLabel.setPanel(nPanel);

        return nStickerLabel;
    }

    public static void changeMainPanelBySticker(String tComponentName, HashMap<String,JComponent> map, String eIndex, GridBagConstraints gbc){
        JComponent rootPanel = Helper.getComponent(Main.JrcetPanel,tComponentName+"ComponentPanel");
        rootPanel.remove(rootPanel.getComponents().length-1);
        rootPanel.add(map.get(eIndex),gbc);
        rootPanel.updateUI();
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