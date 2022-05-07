package jrcet.diycomponents;

import burp.lib.Helper;
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
            DiyJAddLabel nStickerLabel = setNewStickerLabel(eTagTabPanel);
            nStickerLabel.mousePressed(new MouseEvent(nStickerLabel,e.getID(),e.getWhen(),e.getModifiers(),e.getX(),e.getY(),e.getClickCount(),e.isPopupTrigger()));
        }else{

            eLabel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(2,0,0,0),BorderFactory.createMatteBorder(0,0,2,0,class2ClickedDiyJTabBorderColor)));
            changeMainPanelBySticker(eNameArray[0], eIndex);
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

    public void changeMainPanelBySticker(String type, String eIndex){

        JComponent tPanel = null;
        HashMap<String,JComponent> tCMap = null;
        HashMap<String,GridBagConstraints> tGMap = null;

        switch (type){
            case "Base":
                tGMap = BaseComponent.ComponentConstraintHashMap;
                tPanel = BaseComponent.BaseComponentPanel;
                tCMap = BaseComponent.MainPanelHashMap;
                break;
            case "Aes":
                tGMap = AesComponent.ComponentConstraintHashMap;
                tPanel = AesComponent.AesComponentPanel;
                tCMap = AesComponent.MainPanelHashMap;
                break;
            case "Rsa":
                tGMap = RsaComponent.ComponentConstraintHashMap;
                tPanel = RsaComponent.RsaComponentPanel;
                tCMap = RsaComponent.MainPanelHashMap;
                break;
            case "Unicode":
                tGMap = UnicodeComponent.ComponentConstraintHashMap;
                tPanel = UnicodeComponent.UnicodeComponentPanel;
                tCMap = UnicodeComponent.MainPanelHashMap;
                break;
            case "Ascii":
                tGMap = AsciiComponent.ComponentConstraintHashMap;
                tPanel = AsciiComponent.AsciiComponentPanel;
                tCMap = AsciiComponent.MainPanelHashMap;
                break;
        }
        assert tPanel != null;
        assert tGMap != null;
        assert tCMap != null;

        String eComponentName = type+"MainPanel";
        GridBagConstraints tGridBagConstraints = tGMap.get(eComponentName);
        int tPos = Helper.getComponentPos(tPanel,eComponentName);
        assert tPos != -1;

        tPanel.remove(tPos);
        tPanel.add(tCMap.get(eIndex), tGridBagConstraints);
        tPanel.updateUI();
    }

    public DiyJAddLabel setNewStickerLabel(JPanel eTagTabPanel){

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
            case "Ascii":
                nPanel = new AsciiComponent().AsciiMainPanel();
                AsciiComponent.MainPanelHashMap.put(nName, nPanel);
                break;
        }

        DiyJAddLabel nStickerLabel = new DiyJAddLabel(nName);
        nStickerLabel.setName(nComponentName);
        nStickerLabel.setPanel(nPanel);

        eTagTabPanel.add(nStickerLabel,eTagTabPanel.getComponents().length-1);
        eTagTabPanel.updateUI();

        return nStickerLabel;
    }

}