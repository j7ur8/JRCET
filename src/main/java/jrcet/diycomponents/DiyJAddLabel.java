package jrcet.diycomponents;

import burp.lib.Helper;
import jrcet.Main;
import jrcet.frame.tools.Dencrypt.Aes.AesComponent;
import jrcet.frame.tools.Dencrypt.Ascii.AsciiComponent;
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
import static jrcet.frame.tools.Dencrypt.Aes.AesComponent.AesComponentPanel;
import static jrcet.frame.tools.Dencrypt.Base.BaseComponent.BaseComponentPanel;
import static jrcet.frame.tools.Dencrypt.Rsa.RsaComponent.RsaComponentPanel;
import static jrcet.frame.tools.Dencrypt.Unicode.UnicodeComponent.UnicodeComponentPanel;

public class DiyJAddLabel extends JLabel implements MouseListener {

    private JComponent mapPanel = null;

    public DiyJAddLabel(String labelName) {
        setOpaque(true);
        setText(labelName);
        setBackground(Color.WHITE);
        setHorizontalAlignment(JLabel.CENTER);
        setPreferredSize(new Dimension(50,15));
        setFont(new Font("微软雅黑", Font.PLAIN,12));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(1,0,0,0),
                BorderFactory.createMatteBorder(0,0,1,0,class2DefaultDiyJTabBorderColor)
        ));

        addMouseListener(this);
    }

    public DiyJAddLabel(String labelName,boolean flag){
        this(labelName);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(2,0,0,0),
                BorderFactory.createMatteBorder(0,0,2,0, class2ClickedDiyJTabBorderColor)
        ));
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
                    changeMainPanelBySticker(eNameArray[0], eIndex, new GridBagConstraints(
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
                    changeMainPanelBySticker(eNameArray[0], eIndex, new GridBagConstraints(
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
                    changeMainPanelBySticker(eNameArray[0], eIndex, new GridBagConstraints(
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
                    changeMainPanelBySticker(eNameArray[0], eIndex, new GridBagConstraints(
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

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    public GridBagConstraints getCompConstraints(){
        return new GridBagLayout().getConstraints(UnicodeComponentPanel);
    }

    public void changeMainPanelBySticker(String type, String eIndex, GridBagConstraints gbc){
        JComponent tPanel = null;
        HashMap<String,JComponent> tMap = null;
        switch (type){
            case "Base":
                tPanel = BaseComponent.BaseComponentPanel;
                tMap = BaseComponent.MainPanelHashMap;
                break;
            case "Aes":
                tPanel = AesComponent.AesComponentPanel;
                tMap = AesComponent.MainPanelHashMap;
                break;
            case "Rsa":
                tPanel = RsaComponent.RsaComponentPanel;
                tMap = RsaComponent.MainPanelHashMap;
                break;
            case "Unicode":
                tPanel = UnicodeComponent.UnicodeComponentPanel;
                tMap = UnicodeComponent.MainPanelHashMap;
                break;
            case "Ascii":
                tPanel = AsciiComponent.AsciiComponentPanel;
                tMap = AsciiComponent.MainPanelHashMap;
                break;
        }
        assert tPanel != null;
        GridBagConstraints tGridBagConstraints = ((GridBagLayout)tPanel.getLayout()).getConstraints(Helper.getComponent(tPanel,type+"MainPanel"));
        tPanel.remove(tPanel.getComponents().length-1);
        tPanel.add(tMap.get(eIndex),tGridBagConstraints);
        tPanel.updateUI();
    }

    public DiyJAddLabel getNewStickerLabel(JPanel eTagTabPanel){

        JComponent nPanel = null;
        String nName = String.valueOf(eTagTabPanel.getComponents().length);
        String nComponentName = eTagTabPanel.getComponent(0).getName().replace("1", nName);
        switch (eTagTabPanel.getName().split("(?=[A-Z])")[0]){
            case "Base":
                nPanel = new BaseComponent().BaseMainPanel();
                BaseComponent.MainPanelHashMap.put(nName, nPanel);
                break;
            case "Unicode":
                nPanel = new UnicodeComponent().UnicodeMainPanel();
                UnicodeComponent.MainPanelHashMap.put(nName, nPanel);
                break;
            case "Aes":
                nPanel = new AesComponent().AesMainPanel();
                AesComponent.MainPanelHashMap.put(nName, nPanel);
                break;
            case "Rsa":
                nPanel = new RsaComponent().RsaMainPanel();
                RsaComponent.MainPanelHashMap.put(nName, nPanel);
        }

        DiyJAddLabel nStickerLabel = new DiyJAddLabel(nName);
        nStickerLabel.setName(nComponentName);
        nStickerLabel.setPanel(nPanel);

        return nStickerLabel;
    }

}