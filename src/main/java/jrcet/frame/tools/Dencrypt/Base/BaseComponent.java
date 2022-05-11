package jrcet.frame.tools.Dencrypt.Base;

import jrcet.lib.Helper;
import jrcet.diycomponents.DiyJAddLabel;
import jrcet.diycomponents.DiyJComponent;
import jrcet.frame.setting.Setting;

import javax.swing.*;
import java.awt.*;
import java.util.*;


public class BaseComponent extends DiyJComponent {

    public static HashMap<String, GridBagConstraints> ComponentConstraintHashMap = new HashMap<>();
    public static HashMap<String, JComponent> MainPanelHashMap = new HashMap<>();
    public static JPanel BaseComponentPanel = null;

    public JComponent main(){

        BaseComponentPanel = new JPanel(new GridBagLayout());
        BaseComponentPanel.setName("BaseComponentPanel");
        BaseComponentPanel.setBackground(Color.WHITE);

        BaseComponentPanel.add(BaseTagBlackPanel(), new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        BaseComponentPanel.add(BaseTagTabPanel(),new GridBagConstraints(
                1,0,
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        MainPanelHashMap.put("1", BaseMainPanel());
        Helper.setConstraints(ComponentConstraintHashMap, BaseComponentPanel, MainPanelHashMap.get("1"),new GridBagConstraints(
                0,1,
                2,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return BaseComponentPanel;
    }

    public JComponent BaseTagBlackPanel(){
        JComponent BaseTagBlackPanel = new JPanel();
        BaseTagBlackPanel.setName("BaseTagBlackPanel");
        BaseTagBlackPanel.setOpaque(false);
        BaseTagBlackPanel.setBorder(BorderFactory.createMatteBorder(0,0,1,0,Setting.class4DefaultDiyJTabBorderColor));
        return BaseTagBlackPanel;
    }

    public JComponent BaseTagTabPanel(){
        JPanel BaseTagTabPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,0,0));
        BaseTagTabPanel.setName("BaseTagTabPanel");
//        BaseTagTabPanel.setOpaque(false);
        BaseTagTabPanel.setBorder(BorderFactory.createMatteBorder(0,0,0,0,new Color(203,208,209)));

        DiyJAddLabel BaseTagTabSticker1Label = new DiyJAddLabel("1",true);
        BaseTagTabSticker1Label.setName("BaseTagTabSticker1Label");
        BaseTagTabSticker1Label.setPanel(MainPanelHashMap.get("1"));
        BaseTagTabPanel.add(BaseTagTabSticker1Label);

        DiyJAddLabel BaseTabAddLabel = new DiyJAddLabel("···");
        BaseTabAddLabel.setName("BaseTabAddLabel");
        BaseTagTabPanel.add(BaseTabAddLabel);

        return BaseTagTabPanel;
    }

    public JComponent BaseMainPanel(){
        JComponent BaseMainPanel = new JPanel(new GridBagLayout());
        BaseMainPanel.setName("BaseMainPanel");
        BaseMainPanel.setOpaque(false);
        BaseMainPanel.setBackground(Color.WHITE);


        return BaseMainPanel;
    }

}
