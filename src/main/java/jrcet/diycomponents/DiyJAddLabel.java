package jrcet.diycomponents;

import jrcet.frame.setting.Setting;
import jrcet.frame.tools.Dencrypt.Base.BaseComponent;
import jrcet.frame.tools.Dencrypt.DencryptComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DiyJAddLabel extends JLabel implements MouseListener {


    private final Map<String, JComponent> mapPanel= new HashMap<>();

    public DiyJAddLabel(String labelName) {
        setProperty(labelName);
    }

    public DiyJAddLabel(String labelName,boolean flag) {
        setProperty(labelName);
        setBorder(BorderFactory.createMatteBorder(0,1,0,0,Setting.class4DefaultDiyJTabBorderColor));

    }

    private void setProperty(String labelName){
        setOpaque(true);
        setText(labelName);
        setBackground(Color.WHITE);
        setHorizontalAlignment(JLabel.CENTER);
        setFont(new Font("微软雅黑", Font.PLAIN,12));
        setPreferredSize(new Dimension(50,15));
        setBorder(BorderFactory.createMatteBorder(0,1,1,0,Setting.class4DefaultDiyJTabBorderColor));
        addMouseListener(this);
    }

    public void setMapPanel(JComponent targetPanel){
        mapPanel.put(getText(),targetPanel);
    }
    public JComponent getMapPanel(String name){
        return mapPanel.get(name);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {

        JLabel pressedLabel = (JLabel)e.getSource();
        JComponent parentPanel=(JPanel)pressedLabel.getParent().getParent();
        if(Objects.equals(getText(), "···")){
            switch (parentPanel.getName()){
                case "Base-1":
                    JPanel targetPanel = DencryptComponent.DencryptBaseMenuTabInstance.DencryptMenuTabListPanel;
                    ArrayList<Object> basePanelList = DencryptComponent.FunctionPanelMap.get("Base");
                    int tabNums = DencryptComponent.DencryptBaseMenuTabInstance.tabNums;
                    DencryptComponent.DencryptBaseMenuTabInstance.tabNums++;
                    targetPanel.remove(targetPanel.getComponents().length-1);
                    DiyJAddLabel willAddedTab = new DiyJAddLabel(Integer.toString(++tabNums));
                    targetPanel.add(willAddedTab,-1);
                    targetPanel.add(new DiyJAddLabel("···"));
                    BaseComponent willAddedPanelInstance = new BaseComponent();
                    basePanelList.add(willAddedPanelInstance);
                    JComponent willAddedPanel = willAddedPanelInstance.main();
                    DencryptComponent.FunctionPanelMap.put("Base",basePanelList);
                    willAddedPanel.setName("Base-"+ tabNums);
                    willAddedTab.setMapPanel(willAddedPanel);
                    break;
            }
        }else if(getMapPanel(getText())!=null){

            for(Component i:this.getParent().getComponents()){
                if(i instanceof DiyJAddLabel){
                    DiyJAddLabel ii=(DiyJAddLabel)i;
                    ii.setBorder(BorderFactory.createMatteBorder(0,1,1,0,Setting.class4DefaultDiyJTabBorderColor));

                }
            }

            pressedLabel.setBorder(BorderFactory.createMatteBorder(0,1,0,0, Setting.class4DefaultDiyJTabBorderColor));

            int parentComponentsNums = parentPanel.getComponents().length;
            parentPanel.remove(parentComponentsNums-1);
            JComponent willLoadedPanel = mapPanel.get(getText());
            String name = willLoadedPanel.getName();
            String[] nameSplit = name.split("-");
            DencryptComponent.nowPanelInstance=DencryptComponent.FunctionPanelMap.get(nameSplit[0]).get(Integer.parseInt(nameSplit[1])-1);
            parentPanel.add(willLoadedPanel,new GridBagConstraints(
                    0,1,
                    parentComponentsNums-1,1,
                    1,1,
                    GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH,
                    new Insets(0,0,0,0),
                    0,0
            ));
        }
        parentPanel.validate();
        parentPanel.repaint();
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
}