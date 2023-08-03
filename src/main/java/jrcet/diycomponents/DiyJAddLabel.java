package jrcet.diycomponents;

import jrcet.frame.Dencrypt.Hex.HexComponent;
import jrcet.frame.Dencrypt.Jwt.JwtComponent;
import jrcet.frame.HText.Case.CaseComponent;
import jrcet.frame.HText.Format.FormatComponent;
import jrcet.frame.HText.IPUnit.IPUnitComponent;
import jrcet.frame.HText.Len.LenComponent;
import jrcet.frame.HText.Regex.RegexComponent;
import jrcet.help.Helper;
import jrcet.frame.Dencrypt.Aes.AesComponent;
import jrcet.frame.Dencrypt.Ascii.AsciiComponent;
import jrcet.frame.Dencrypt.Base.BaseComponent;
import jrcet.frame.Dencrypt.Rsa.RsaComponent;
import jrcet.frame.Dencrypt.Unicode.UnicodeComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Objects;

import static jrcet.frame.Setting.Setting.orange;
import static jrcet.frame.Setting.Setting.gray;

public class DiyJAddLabel extends JLabel implements MouseListener {

    private JComponent mapPanel = null;

    public DiyJAddLabel(String labelName) {
        setText(labelName);
        setBackground(Color.WHITE);
        setHorizontalAlignment(JLabel.CENTER);
        setPreferredSize(new Dimension(50,15));
        setFont(new Font("微软雅黑", Font.PLAIN,12));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(1,0,0,0),
                BorderFactory.createMatteBorder(0,0,1,0, gray)
        ));

        addMouseListener(this);
    }

    public DiyJAddLabel(String labelName, boolean flag){
        this(labelName);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(2,0,0,0),
                BorderFactory.createMatteBorder(0,0,2,0, orange)
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
                ((DiyJAddLabel)i).setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(1,0,0,0),BorderFactory.createMatteBorder(0,0,1,0, gray)));
            }
        }

        DiyJAddLabel eLabel = (DiyJAddLabel)e.getSource();
        String[] eNameArray = (" "+eLabel.getName()).split("(?=[A-Z])");
        StringBuilder k= new StringBuilder();
        for(int l=1; l<eNameArray.length;l++){
            k.append(eNameArray[l].length()==1?eNameArray[l]:(eNameArray[l-1].length()==1?eNameArray[l]:""));
        }
        String repairedName = k.toString();

        String eName = eNameArray[eNameArray.length-2];
        String eIndex = eName.substring(eName.length()-1);

        if(Objects.equals(eName, "Add")){
            JPanel eTagTabPanel = (JPanel) eLabel.getParent();
            DiyJAddLabel nStickerLabel = setNewStickerLabel(repairedName, eTagTabPanel);
            nStickerLabel.mousePressed(new MouseEvent(nStickerLabel,e.getID(),e.getWhen(),e.getModifiers(),e.getX(),e.getY(),e.getClickCount(),e.isPopupTrigger()));
        }else{
            eLabel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(2,0,0,0),BorderFactory.createMatteBorder(0,0,2,0, orange)));
            changeMainPanelBySticker(repairedName, eIndex);
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
            case "Format":
                tGMap = FormatComponent.ComponentConstraintHashMap;
                tPanel = FormatComponent.FormatComponentPanel;
                tCMap = FormatComponent.MainPanelHashMap;
                break;
            case "Jwt":
                tGMap = JwtComponent.ComponentConstraintHashMap;
                tPanel = JwtComponent.JwtComponentPanel;
                tCMap = JwtComponent.MainPanelHashMap;
                break;
            case "Hex":
                tGMap = HexComponent.ComponentConstraintHashMap;
                tPanel = HexComponent.HexComponentPanel;
                tCMap = HexComponent.MainPanelHashMap;
                break;
            case "Case":
                tGMap = CaseComponent.ComponentConstraintHashMap;
                tPanel = CaseComponent.CaseComponentPanel;
                tCMap = CaseComponent.MainPanelHashMap;
                break;
            case "IPUnit":
                tGMap = IPUnitComponent.ComponentConstraintHashMap;
                tPanel = IPUnitComponent.IPUnitComponentPanel;
                tCMap = IPUnitComponent.MainPanelHashMap;
                break;
            case "Regex":
                tGMap = RegexComponent.ComponentConstraintHashMap;
                tPanel = RegexComponent.RegexComponentPanel;
                tCMap = RegexComponent.MainPanelHashMap;
                break;
            case "Len":
                tGMap = LenComponent.ComponentConstraintHashMap;
                tPanel = LenComponent.LenComponentPanel;
                tCMap = LenComponent.MainPanelHashMap;

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

    public DiyJAddLabel setNewStickerLabel(String eName, JPanel eTagTabPanel){

        JComponent nPanel = null;
        String nIndex = String.valueOf(eTagTabPanel.getComponents().length);
        String nComponentName = eTagTabPanel.getComponent(0).getName().replace("1", nIndex);
        switch (eName){
            case "Base":
                nPanel = new BaseComponent().BaseMainPanel();
                BaseComponent.MainPanelHashMap.put(nIndex, nPanel);
                break;
            case "Unicode":
                nPanel = new UnicodeComponent().UnicodeMainPanel();
                UnicodeComponent.MainPanelHashMap.put(nIndex, nPanel);
                break;
            case "Aes":
                nPanel = new AesComponent().AesMainPanel();
                AesComponent.MainPanelHashMap.put(nIndex, nPanel);
                break;
            case "Rsa":
                nPanel = new RsaComponent().RsaMainPanel();
                RsaComponent.MainPanelHashMap.put(nIndex, nPanel);
            case "Ascii":
                nPanel = new AsciiComponent().AsciiMainPanel();
                AsciiComponent.MainPanelHashMap.put(nIndex, nPanel);
                break;
            case "Format":
                nPanel = new FormatComponent().FormatMainPanel();
                FormatComponent.MainPanelHashMap.put(nIndex, nPanel);
                break;
            case "Hex":
                nPanel = new HexComponent().HexMainPanel();
                HexComponent.MainPanelHashMap.put(nIndex, nPanel);
                break;
            case "Jwt":
                nPanel = new JwtComponent().JwtMainPanel();
                JwtComponent.MainPanelHashMap.put(nIndex, nPanel);
                break;
            case "Case":
                nPanel = new CaseComponent().CaseMainPanel();
                CaseComponent.MainPanelHashMap.put(nIndex, nPanel);
                break;
            case "IPUnit":
                nPanel = new IPUnitComponent().IPUnitMainPanel();
                IPUnitComponent.MainPanelHashMap.put(nIndex, nPanel);
                break;
            case "Regex":
                nPanel = new RegexComponent().RegexMainPanel();
                RegexComponent.MainPanelHashMap.put(nIndex, nPanel);
                break;
            case "Len":
                nPanel = new LenComponent().LenMainPanel();
                LenComponent.MainPanelHashMap.put(nIndex, nPanel);
                break;
        }

        DiyJAddLabel nStickerLabel = new DiyJAddLabel(nIndex);
        nStickerLabel.setName(nComponentName);
        nStickerLabel.setPanel(nPanel);

        eTagTabPanel.add(nStickerLabel,eTagTabPanel.getComponents().length-1);
        eTagTabPanel.updateUI();

        return nStickerLabel;
    }

}