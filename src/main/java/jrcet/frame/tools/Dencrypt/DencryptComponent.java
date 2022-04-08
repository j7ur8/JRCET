package jrcet.frame.tools.Dencrypt;

import jrcet.diycomponents.DiyJComponent;
import jrcet.diycomponents.DiyJTabLabel;
import jrcet.frame.setting.Setting;
import jrcet.frame.tools.Dencrypt.Base.BaseComponent;

import javax.swing.*;
import java.awt.*;

public class DencryptComponent extends DiyJComponent {

    static final private JComponent BaseComponentPanel = BaseComponentPanel();
    static final private JComponent AESComponentPanel = AESComponentPanel();

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
        DencryptComponentPanel.add(BaseComponentPanel,new GridBagConstraints(
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
        DencryptMenuPanel.setOpaque(false);
        DencryptMenuPanel.setBackground(Color.WHITE);

        //添加加密Tab
        //Base
        DiyJTabLabel DencryptMenuBaseLabel = new DiyJTabLabel("Base",Setting.class3DefaultDiyJTabBorderColor,Setting.class3ClickedDiyJTabBorderColor,true);
        DencryptMenuBaseLabel.setName("DencryptMenuBaseLabel");
        DencryptMenuBaseLabel.setMapPanel(BaseComponentPanel);
        DencryptMenuPanel.add(DencryptMenuBaseLabel);

        //AES
        DiyJTabLabel DencryptMenuAESLabel = new DiyJTabLabel("AES", Setting.class3DefaultDiyJTabBorderColor,Setting.class3ClickedDiyJTabBorderColor);
        DencryptMenuAESLabel.setMapPanel(AESComponentPanel);
        DencryptMenuAESLabel.setName("DencryptMenuAESLabel");
        DencryptMenuPanel.add(DencryptMenuAESLabel);

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
        DencryptMenuBorderPanel.setName("DencryptMenuBorderPanel");
        DencryptMenuBorderPanel.setOpaque(true);
        DencryptMenuBorderPanel.setBackground(Color.YELLOW);
        DencryptMenuBorderPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(1,0,0,0),BorderFactory.createMatteBorder(0,0,1,0,Setting.class1DefaultDiyJTabBorderColor)));
        DencryptMenuBorderPanel.setPreferredSize(new Dimension(0,0));

        return DencryptMenuBorderPanel;
    }


    public static JComponent BaseComponentPanel(){
        return new BaseComponent().main();
    }

    public static JComponent AESComponentPanel(){
        JPanel AESPanel = new JPanel();
        AESPanel.setName("AES");
        AESPanel.setOpaque(true);
        AESPanel.setBackground(Color.PINK);

        return AESPanel;
    }

}
