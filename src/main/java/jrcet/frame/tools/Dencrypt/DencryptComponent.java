package jrcet.frame.tools.Dencrypt;

import jrcet.diycomponents.DiyJComponent;
import jrcet.diycomponents.DiyJTabLabel;
import jrcet.frame.setting.Setting;
import jrcet.frame.tools.Dencrypt.Base.BaseComponent;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class DencryptComponent extends DiyJComponent {

    //设置默认显示的DencryptPanel
    public static String DefaultDencryptPanel = "Base";
    //HashMap DencryptPanelMap实现一个存储ArrayList<Object>的结构，键名为加解密类型。
    public static HashMap<String,ArrayList<JComponent>> DencryptPanelMap = new HashMap<>();

    public DencryptComponent(){

        DencryptPanelMap.put("Base",new ArrayList<>(Collections.singletonList(new BaseComponent())));
        DencryptPanelMap.put("AES",new ArrayList<>(Collections.singletonList(AESPanel())));

    }

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
        DencryptComponentPanel.add(getDefaultDencryptPanel(DefaultDencryptPanel),new GridBagConstraints(
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
        DencryptMenuBaseLabel.setMapPanel(getDefaultDencryptPanel(DencryptMenuBaseLabel.getText()));
        DencryptMenuPanel.add(DencryptMenuBaseLabel);

        //AES
        DiyJTabLabel DencryptMenuAESLabel = new DiyJTabLabel("AES", Setting.class3DefaultDiyJTabBorderColor,Setting.class3ClickedDiyJTabBorderColor);
        DencryptMenuAESLabel.setMapPanel(getDefaultDencryptPanel(DencryptMenuAESLabel.getText()));
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

    /*
    根据传入的值从DencryptPanelMap中获取默认的Panel，失败则返回一个空Panel
     */
    private static JComponent getDefaultDencryptPanel(String dencryptName){
        if(DencryptPanelMap.containsKey(dencryptName)){
            ArrayList<JComponent> targetDencryptArrayList = DencryptPanelMap.get(dencryptName);
            if ( (targetDencryptArrayList.get(0))  instanceof  DiyJComponent){
                return  ((DiyJComponent)targetDencryptArrayList.get(0)).main();
            }
            return BlackPanel();
        }
        return BlackPanel();
    }



    public static JComponent AESPanel(){
        JPanel AESPanel = new JPanel();
        AESPanel.setName("AES");
        AESPanel.setOpaque(true);
        AESPanel.setBackground(Color.PINK);

        return AESPanel;
    }

    public static JComponent BlackPanel(){
        JPanel BlackPanel = new JPanel();
        BlackPanel.setName("DencryptBlackPanel");
        BlackPanel.setOpaque(true);
        BlackPanel.setBackground(Color.PINK);

        return BlackPanel;
    }

}
