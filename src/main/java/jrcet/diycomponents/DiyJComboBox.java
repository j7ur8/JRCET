package jrcet.diycomponents;

import jrcet.frame.exploit.php.PbootCMS;
import jrcet.frame.exploit.php.ThinkPHP;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;
import java.util.Objects;

public class DiyJComboBox extends JComboBox implements ItemListener {
    public String[] types = new String[]{"PHP","Java","Nodejs","Other"};
    public DiyJComboBox(String[] targetList){
        super(targetList);
        addItemListener(this);

    }


    @Override
    public void itemStateChanged(ItemEvent e) {

        DiyJComboBox tmpComboBox = (DiyJComboBox)e.getSource();

        if(e.getStateChange()!= ItemEvent.SELECTED){
            return;
        }
        if(tmpComboBox.getParent()!=null){
            if(!Arrays.asList(types).contains(e.getItem())){
                String nowSelectItem= (String) e.getItem();
                System.out.println(tmpComboBox.getParent());
                JLabel nowSelectLabel=(JLabel) tmpComboBox.getParent().getComponent(4);
                nowSelectLabel.setText("               当前选择："+nowSelectItem);
                DiyJComponent targetPanel=null;
                switch (Objects.requireNonNull(nowSelectItem)){
                    case "ThinkPHP":
                        targetPanel=new ThinkPHP();
                        break;
                    case "PbootCMS":
                        targetPanel=new PbootCMS();
                        break;
                    case "Shiro":
//                    targetPanel=new Shiro();
                        break;
                    case "FastJson":
//                    FastJson();
                        break;
                }
                if(targetPanel!=null) {
                    JPanel parentPanel= (JPanel) tmpComboBox.getParent().getParent();
                    parentPanel.remove(1);
                    GridBagConstraints targetPanelProperty = new GridBagConstraints();
                    targetPanelProperty.gridy=1;targetPanelProperty.gridx=0;
                    targetPanelProperty.weightx=100;targetPanelProperty.weighty=100;
                    targetPanelProperty.fill = GridBagConstraints.BOTH;
                    parentPanel.add(targetPanel.main(),targetPanelProperty);
                    parentPanel.validate();
                    parentPanel.repaint();
                }

                return;
            }

            DiyJComboBox targetComboBox = (DiyJComboBox) tmpComboBox.getParent().getComponent(3);

            String selectedItem =  (String) tmpComboBox.getSelectedItem();
            String[] tmpTargetList = new String[]{"选择其他"};
            switch (Objects.requireNonNull(selectedItem)){
                case  "PHP":
                    tmpTargetList = new String[]{"ThinkPHP", "PbootCMS"};
                    break;
                case "Java":
                    tmpTargetList = new String[]{"Shiro", "FastJson"};
                    break;
                case "Python":
                case "Nodejs":
                case "Other":
                    break;
            }

            targetComboBox.removeAllItems();
            for (String s : tmpTargetList) {
                targetComboBox.addItem(s);
            }
        }

    }
}
