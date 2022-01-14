package jrcet.frame.tools.Dencrypt;

import jrcet.diycomponents.DiyJAddLabel;
import jrcet.diycomponents.DiyJComponent;
import jrcet.diycomponents.DiyJTabLabel;
import jrcet.frame.setting.Setting;
import jrcet.frame.tools.Dencrypt.Base.BaseComponent;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class DencryptComponent extends DiyJComponent {

    public static final BaseComponent BasePanelInstance = new BaseComponent();
    public static final JComponent BasePanel = BasePanelInstance.main();
    public static final JComponent AESPanel = AESPanel();

    public static JComponent DencryptMenuPanel;

    public static DencryptMenuTabPanel DencryptBaseMenuTabInstance;
    public static DencryptMenuTabPanel DencryptAESMenuTabInstance;
    public static JComponent DencryptBaseMenuTabPanel;
    public static JComponent DencryptAESMenuTabPanel;
    public static Object nowPanelInstance;

    public static HashMap<String,ArrayList<Object>> FunctionPanelMap = new HashMap<>();

    {

        DencryptBaseMenuTabInstance= new DencryptMenuTabPanel();
        DencryptBaseMenuTabPanel = DencryptBaseMenuTabInstance.main(BasePanel);

        DencryptAESMenuTabInstance = new DencryptMenuTabPanel();
        DencryptAESMenuTabPanel = DencryptAESMenuTabInstance.main(AESPanel);

        DencryptMenuPanel = DencryptMenuPanel();

        nowPanelInstance = BasePanelInstance;
        FunctionPanelMap.put("Base",new ArrayList<>(Collections.singletonList(BasePanelInstance)));
//        FunctionPanelMap.put("AES",new ArrayList<>(Collections.singletonList(DencryptAESMenuTabInstance)));
    }

    public JPanel main(){
        JPanel DencryptPanel = new JPanel(new GridBagLayout());
        DencryptPanel.setOpaque(true);
        DencryptPanel.setBackground(Color.WHITE);

        DencryptPanel.add(DencryptMenuBorderPanel(),new GridBagConstraints(
                0,0,
                1,1,
                0.5,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        DencryptPanel.add(DencryptMenuPanel,new GridBagConstraints(
                1,0,
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        DencryptPanel.add(DencryptMenuBorderPanel(),new GridBagConstraints(
                2,0,
                1,1,
                0.5,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        DencryptPanel.add(DencryptBaseMenuTabPanel,new GridBagConstraints(
                0,2,
                3,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return DencryptPanel;
    }

    public static JComponent DencryptMenuPanel(){

        JPanel DencryptMenuPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        DencryptMenuPanel.setOpaque(false);
        DencryptMenuPanel.setBackground(Color.WHITE);

        DiyJTabLabel BaseTab = new DiyJTabLabel("Base",Setting.class3DefaultDiyJTabBorderColor,Setting.class3ClickedDiyJTabBorderColor,true);
        DencryptMenuPanel.add(BaseTab);
        BaseTab.setMapPanel(DencryptBaseMenuTabPanel);

        DiyJTabLabel AESTab = new DiyJTabLabel("AES", Setting.class3DefaultDiyJTabBorderColor,Setting.class3ClickedDiyJTabBorderColor);
        DencryptMenuPanel.add(AESTab);
        AESTab.setMapPanel(DencryptAESMenuTabPanel);

        diyJTabLabel(DencryptMenuPanel);
        return DencryptMenuPanel;
    }

    private static void diyJTabLabel(JComponent DencryptMenuPanel){
        for(Component targetJTabLabel :DencryptMenuPanel.getComponents()){
            targetJTabLabel.setFont(new Font("微软雅黑", Font.PLAIN,12));
            targetJTabLabel.setPreferredSize(new Dimension(50,20));
        }
    }

    public JComponent DencryptMenuBorderPanel(){
        JPanel DencryptMenuBorderPanel = new JPanel();
        DencryptMenuBorderPanel.setOpaque(false);
        DencryptMenuBorderPanel.setBackground(Color.WHITE);
        DencryptMenuBorderPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(1,0,0,0),BorderFactory.createMatteBorder(0,0,1,0,Setting.class1DefaultDiyJTabBorderColor)));
        DencryptMenuBorderPanel.setPreferredSize(new Dimension(0,0));

        return DencryptMenuBorderPanel;
    }


     public class DencryptMenuTabPanel{

        public  JPanel DencryptMenuTabPanel = new JPanel(new GridBagLayout());
        public  JPanel DencryptMenuTabListPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,0,0));
        public Integer tabNums=1;

        public JComponent main(JComponent defaultPanel){
            DencryptMenuTabPanel.setOpaque(false);
            DencryptMenuTabPanel.setPreferredSize(new Dimension(0,0));
            DencryptMenuTabPanel.setName(defaultPanel.getName());

            DencryptMenuTabListPanel.setOpaque(false);
            DencryptMenuTabListPanel.setBorder(BorderFactory.createMatteBorder(0,0,0,0,new Color(203,208,209)));

            DiyJAddLabel defaultDiyJTabLabel = new DiyJAddLabel("1",true);
            DencryptMenuTabListPanel.add(defaultDiyJTabLabel);
            defaultDiyJTabLabel.setMapPanel(defaultPanel);

            DiyJAddLabel defaultAddDiyJTabLabel = new DiyJAddLabel("···");
            DencryptMenuTabListPanel.add(defaultAddDiyJTabLabel);

            DencryptMenuTabPanel.add(DencryptMenuTabListPanel,new GridBagConstraints(
                    0,0,
                    1,1,
                    1,0,
                    GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH,
                    new Insets(0,0,0,0),
                    0,0
            ));
            DencryptMenuTabPanel.add(defaultPanel,new GridBagConstraints(
                    0,1,
                    1,1,
                    1,1,
                    GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH,
                    new Insets(0,0,0,0),
                    0,0
            ));

            return DencryptMenuTabPanel;
        }

    }


    public static JComponent AESPanel(){
        JPanel AESPanel = new JPanel();
        AESPanel.setName("AES");
        AESPanel.setOpaque(true);
        AESPanel.setBackground(Color.PINK);

        return AESPanel;
    }


}
