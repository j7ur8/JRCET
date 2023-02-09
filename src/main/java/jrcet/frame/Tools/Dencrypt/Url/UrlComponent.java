package jrcet.frame.Tools.Dencrypt.Url;

import jrcet.diycomponents.DiyJTextAreaScrollPane;
import jrcet.help.Helper;
import jrcet.diycomponents.DiyJAddLabel;
import jrcet.diycomponents.DiyJComponent;
import jrcet.frame.Setting.Setting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;



public class UrlComponent extends DiyJComponent {

    public static HashMap<String, GridBagConstraints> ComponentConstraintHashMap = new HashMap<>();
    public static HashMap<String, JComponent> MainPanelHashMap = new HashMap<>();

    public static JComponent UrlComponentPanel = null;

    public JComponent main(){

        UrlComponentPanel = new JPanel(new GridBagLayout());
        UrlComponentPanel.setName("UrlComponentPanel");
        UrlComponentPanel.setBackground(Color.WHITE);


        UrlComponentPanel.add(UrlTagTabPanel(),new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        MainPanelHashMap.put("1", UrlMainPanel());
        Helper.setConstraints(ComponentConstraintHashMap,UrlComponentPanel,getUrlMainPanel("1"),new GridBagConstraints(
                0,1,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(5,5,5,5),
                0,0
        ));

        return UrlComponentPanel;
    }


    public JComponent UrlTagTabPanel(){

        JPanel UrlTagTabPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,0,0));
        UrlTagTabPanel.setName("UrlTagTabPanel");
        UrlTagTabPanel.setBackground(Color.WHITE);

        DiyJAddLabel UrlTagTabSticker1Label = new DiyJAddLabel("1",true);
        UrlTagTabSticker1Label.setName("UrlTagTabSticker1Label");
        UrlTagTabSticker1Label.setPanel(getUrlMainPanel("1"));
        UrlTagTabPanel.add(UrlTagTabSticker1Label);

        DiyJAddLabel UrlTabAddLabel = new DiyJAddLabel("···");
        UrlTabAddLabel.setName("UrlTabAddLabel");
        UrlTagTabPanel.add(UrlTabAddLabel);

        return UrlTagTabPanel;
    }

    public JComponent UrlMainPanel(){

        JComponent UrlMainPanel = new JPanel(new GridBagLayout());
        UrlMainPanel.setName("UrlMainPanel");
        UrlMainPanel.setPreferredSize(new Dimension(0,0));

        UrlMainPanel.add(Helper.blackPanel(),new GridBagConstraints(
                0,0,
                1,2,
                0.2,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        UrlMainPanel.add(UrlMainStringScrollPane(), new GridBagConstraints(
                1,0,
                1,1,
                0.6,0.5,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        UrlMainPanel.add(UrlMainUrlScrollPane(), new GridBagConstraints(
                1,1,
                1,1,
                0.6,0.5,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        UrlMainPanel.add(BaseMainControlPanel(),new GridBagConstraints(
                2,0,
                1,2,
                0.2,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return UrlMainPanel;
    }


    public JComponent UrlMainStringScrollPane(){

        DiyJTextAreaScrollPane UrlMainStringAreaScrollPane = new DiyJTextAreaScrollPane("UrlMainStringArea");

        UrlMainStringAreaScrollPane.addKeyListener(new UrlMainKeyListener());


        return UrlMainStringAreaScrollPane;

    }

    public JComponent UrlMainUrlScrollPane(){

        DiyJTextAreaScrollPane UrlMainUrlAreaScrollPane = new DiyJTextAreaScrollPane("UrlMainUrlArea");

        UrlMainUrlAreaScrollPane.addKeyListener(new UrlMainKeyListener());



        return UrlMainUrlAreaScrollPane;
    }

    public JComponent BaseMainControlPanel(){
        JPanel BaseMainControlPanel = new JPanel(new GridBagLayout());
        BaseMainControlPanel.setName("BaseMainControlPanel");
        BaseMainControlPanel.setPreferredSize(new Dimension(0,0));

        JCheckBox BaseMainControlModeBox = new JCheckBox("Url Encode All");
        BaseMainControlModeBox.setName("BaseMainControlModeBox");
        BaseMainControlModeBox.setPreferredSize(new Dimension(0,20));

        BaseMainControlPanel.add(BaseMainControlModeBox,new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(5,0,0,0),
                0,0
        ));

        BaseMainControlPanel.add(Helper.blackPanel(),new GridBagConstraints(
                0,1,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return BaseMainControlPanel;
    }

    public JComponent getUrlMainPanel(String TagName){
        return MainPanelHashMap.containsKey(TagName)?(MainPanelHashMap.get(TagName)!=null?MainPanelHashMap.get(TagName):Helper.blackPanel()):Helper.blackPanel();
    }

    static class UrlMainKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e){
        }

        @Override
        public void keyPressed(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {

            if( (e.getModifiers()== InputEvent.CTRL_MASK || e.getModifiers() == InputEvent.META_MASK) && e.getKeyCode()==71){
                JTextArea eArea = (JTextArea) e.getSource();
                JTextArea outputArea = null;
                JComponent rootPanel = (JComponent) eArea.getParent().getParent().getParent();
                String tText = "未成功";
                JCheckBox tCheckBox = (JCheckBox) Helper.getComponent(UrlComponentPanel, "BaseMainControlModeBox"); assert tCheckBox !=null;
                switch (eArea.getName()){
                    case "UrlMainStringArea":
                        outputArea = (JTextArea) Helper.getComponent(rootPanel, "UrlMainUrlArea" );
                        if(tCheckBox.isSelected()){
                            tText = Url.encodeAll(eArea.getText());
                        }else{
                            tText = Url.encode(eArea.getText());
                        }
                        break;
                    case "UrlMainUrlArea":
                        outputArea = (JTextArea) Helper.getComponent(rootPanel, "UrlMainStringArea" );
                        tText=Url.decode(eArea.getText());
                        break;
                }
                assert outputArea != null;
                outputArea.setText(tText);
            }
        }
    }


}
