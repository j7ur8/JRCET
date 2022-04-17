package jrcet.frame.tools.Dencrypt;

import burp.lib.Helper;
import jrcet.Main;
import jrcet.diycomponents.DiyJAddLabel;
import jrcet.diycomponents.DiyJComponent;
import jrcet.diycomponents.DiyJTabLabel;
import jrcet.frame.setting.Setting;
import jrcet.frame.tools.Dencrypt.Aes.AesComponent;
import jrcet.frame.tools.Dencrypt.Base.BaseComponent;
import jrcet.frame.tools.Dencrypt.Rsa.RsaComponent;
import jrcet.frame.tools.Dencrypt.Unicode.UnicodeComponent;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class DencryptComponent extends DiyJComponent {

    static final private JComponent BaseComponentPanel = BaseComponentPanel();
    static final private JComponent AesComponentPanel = AesComponentPanel();
    static final private JComponent UnicodeComponentPanel = UnicodeComponentPanel();
    static final private JComponent RsaComponentPanel = RsaComponentPanel();


    public JPanel main(){
        JPanel DencryptComponentPanel = new JPanel(new GridBagLayout());
        DencryptComponentPanel.setName("DencryptComponentPanel");
        DencryptComponentPanel.setOpaque(true);
        DencryptComponentPanel.setBackground(Color.WHITE);

        /*
            DencryptMenuBorderPanel()左右复用更好。
         */
        DencryptComponentPanel.add(DencryptMenuBorderPanel(),new GridBagConstraints(
                0,0,
                1,1,
                0.5,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        /*
            DencryptMenuPanel是AES、Base这一栏目
        */
        DencryptComponentPanel.add(DencryptMenuTabPanel(),new GridBagConstraints(
                1,0,
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        DencryptComponentPanel.add(DencryptMenuBorderPanel(),new GridBagConstraints(
                2,0,
                1,1,
                0.5,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        /*
            是分页(0,1,2,3...)栏
        */
        DencryptComponentPanel.add(RsaComponentPanel,new GridBagConstraints(
                0,2,
                3,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return DencryptComponentPanel;
    }

    public static JComponent DencryptMenuTabPanel(){

        JPanel DencryptMenuPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        DencryptMenuPanel.setName("DencryptMenuPanel");
        DencryptMenuPanel.setBackground(Color.WHITE);

        //添加加密Tab
        //Base
        DiyJTabLabel DencryptMenuBaseLabel = new DiyJTabLabel("Base");
        DencryptMenuBaseLabel.setName("DencryptMenuBaseLabel");
        DencryptMenuBaseLabel.setPanel(BaseComponentPanel);
        DencryptMenuPanel.add(DencryptMenuBaseLabel);

        //AES
        DiyJTabLabel DencryptMenuAESLabel = new DiyJTabLabel("Aes");
        DencryptMenuAESLabel.setPanel(AesComponentPanel);
        DencryptMenuAESLabel.setName("DencryptMenuAESLabel");
        DencryptMenuPanel.add(DencryptMenuAESLabel);

        //Rsa
        DiyJTabLabel DencryptMenuRsaLabel = new DiyJTabLabel("Rsa",true);
        DencryptMenuRsaLabel.setPanel(RsaComponentPanel);
        DencryptMenuRsaLabel.setName("DencryptMenuRsaLabel");
        DencryptMenuPanel.add(DencryptMenuRsaLabel);

        //Unicode
        DiyJTabLabel DencryptMenuUnicodeLabel = new DiyJTabLabel("Unicode");
        DencryptMenuUnicodeLabel.setPanel(UnicodeComponentPanel);
        DencryptMenuUnicodeLabel.setName("DencryptMenuUnicodeLabel");
        DencryptMenuPanel.add(DencryptMenuUnicodeLabel);

        //设置 Tab 的按钮属性（高宽等）
        for(Component label : DencryptMenuPanel.getComponents()){
            label.setFont(new Font("微软雅黑", Font.PLAIN,12));
            label.setPreferredSize(new Dimension(50,20));
        }

        return DencryptMenuPanel;
    }

    //设置DencryptMenu组件下边框的格式
    public JComponent DencryptMenuBorderPanel(){

        JPanel DencryptMenuBorderPanel = new JPanel();
        DencryptMenuBorderPanel.setBackground(Color.WHITE);
        DencryptMenuBorderPanel.setName("DencryptMenuBorderPanel");
        DencryptMenuBorderPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(1,0,0,0),BorderFactory.createMatteBorder(0,0,1,0,Setting.class1DefaultDiyJTabBorderColor)));
        DencryptMenuBorderPanel.setPreferredSize(new Dimension(0,0));

        return DencryptMenuBorderPanel;
    }


    public static JComponent BaseComponentPanel(){
        return new BaseComponent().main();
    }

    public static JComponent AesComponentPanel(){
        return new AesComponent().main();
    }

    public static JComponent RsaComponentPanel(){
        return new RsaComponent().main();
    }

    public static JComponent UnicodeComponentPanel(){
        return new UnicodeComponent().main();
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
}
