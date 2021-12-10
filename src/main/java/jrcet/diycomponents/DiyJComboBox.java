package jrcet.diycomponents;

import jrcet.frame.exploit.php.PbootCMS;
import jrcet.frame.exploit.php.ThinkPHP;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

        if(e.getStateChange()!=1){
            return;
        }
        if(!Arrays.asList(types).contains(e.getItem())){
            String nowSelectItem= (String) e.getItem();
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
               JPanel aa= (JPanel) tmpComboBox.getParent().getParent();
               aa.remove(1);
               GridBagConstraints gbc2 = new GridBagConstraints();
               gbc2.gridy=1;gbc2.gridx=0;
               gbc2.weightx=100;gbc2.weighty=100;
               gbc2.fill = GridBagConstraints.BOTH;
               aa.add(targetPanel.main(),gbc2);
               aa.validate();
               aa.repaint();
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
                break;
            case "Nodejs":
                break;
            case "Other":
                break;
        }

        targetComboBox.removeAllItems();
        for (String s : tmpTargetList) {
            targetComboBox.addItem(s);
        }
    }
}
