package jrcet.frame.Tools.Dencrypt.Base;

import jrcet.diycomponents.*;
import jrcet.help.Helper;
import jrcet.frame.Setting.Setting;




import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
    

public class BaseComponent extends DiyJComponent {

    public static HashMap<String, GridBagConstraints> ComponentConstraintHashMap = new HashMap<>();
    public static HashMap<String, JComponent> MainPanelHashMap = new HashMap<>();
    public static JPanel BaseComponentPanel = null;

    public JComponent main(){

        BaseComponentPanel = new JPanel(new GridBagLayout());
        BaseComponentPanel.setName("BaseComponentPanel");
        BaseComponentPanel.setBackground(Color.WHITE);

        BaseComponentPanel.add(BaseTagTabPanel(),new GridBagConstraints(
                0,0,
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        MainPanelHashMap.put("1", BaseMainPanel());
        Helper.setConstraints(ComponentConstraintHashMap, BaseComponentPanel, MainPanelHashMap.get("1"), new GridBagConstraints(
                0,1,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(5,5,5,5),
                0,0
        ));

        return BaseComponentPanel;
    }


    public JComponent BaseTagTabPanel(){
        JPanel BaseTagTabPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,0,0));
        BaseTagTabPanel.setName("BaseTagTabPanel");
        BaseTagTabPanel.setBackground(Color.WHITE);

        DiyJAddLabel BaseTagTabSticker1Label = new DiyJAddLabel("1",true);
        BaseTagTabSticker1Label.setName("BaseTagTabSticker1Label");
        BaseTagTabSticker1Label.setPanel(getBaseMainPanel("1"));
        BaseTagTabPanel.add(BaseTagTabSticker1Label);

        DiyJAddLabel BaseTabAddLabel = new DiyJAddLabel("···");
        BaseTabAddLabel.setName("BaseTabAddLabel");
        BaseTagTabPanel.add(BaseTabAddLabel);

        return BaseTagTabPanel;
    }

    public JComponent BaseMainPanel(){
        JComponent BaseMainPanel = new JPanel(new GridBagLayout());
        BaseMainPanel.setName("BaseMainPanel");
        BaseMainPanel.setBackground(Color.WHITE);
        BaseMainPanel.setPreferredSize(new Dimension(0,0));
        
        BaseMainPanel.add(Helper.blackPanel(),new GridBagConstraints(
                0,0,
                1,2,
                0.2,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        BaseMainPanel.add(BaseMainPlainScrollPane(), new GridBagConstraints(
                1,0,
                1,1,
                0.6,0.5,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        BaseMainPanel.add(BaseMainCipherScrollPane(), new GridBagConstraints(
                1,1,
                1,1,
                0.6,0.5,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        BaseMainPanel.add(BaseMainControlPanel(),new GridBagConstraints(
                2,0,
                1,2,
                0.2,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return BaseMainPanel;
    }


    public JComponent BaseMainPlainScrollPane(){

        DiyJTextAreaScrollPane BaseMainPlainAreaScrollPane = new DiyJTextAreaScrollPane("BaseMainPlainArea");
        BaseMainPlainAreaScrollPane.addKeyListener(new BaseMainKeyListener());

        return BaseMainPlainAreaScrollPane;
    }

    public JComponent BaseMainCipherScrollPane(){

        DiyJTextAreaScrollPane BaseMainCipherAreaScrollPane = new DiyJTextAreaScrollPane("BaseMainCipherArea");
        BaseMainCipherAreaScrollPane.addKeyListener(new BaseMainKeyListener());

        return BaseMainCipherAreaScrollPane;
    }

    public JComponent BaseMainControlPanel(){

        JComponent BaseMainControlPanel = new JPanel(new GridBagLayout());
        BaseMainControlPanel.setName("BaseMainControlPanel");
        BaseMainControlPanel.setPreferredSize(new Dimension(0,0));

        BaseMainControlPanel.add(BaseMainControlFieldPanel(),new GridBagConstraints(
                0,BaseMainControlPanel.getComponentCount(),
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(5,0,0,0),
                0,0
        ));

        BaseMainControlPanel.add(BaseMainControlModePanel(),new GridBagConstraints(
                0,BaseMainControlPanel.getComponentCount(),
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(5,0,0,0),
                0,0
        ));

        BaseMainControlPanel.add(Helper.blackPanel(),new GridBagConstraints(
                0,BaseMainControlPanel.getComponentCount(),
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return BaseMainControlPanel;
    }


    public JComponent BaseMainControlFieldPanel(){
        JComponent BaseMainControlFieldPanel = new JPanel(new GridBagLayout());
        BaseMainControlFieldPanel.setName("BaseMainControlModePanel");
        BaseMainControlFieldPanel.setPreferredSize(new Dimension(0,25));
        BaseMainControlFieldPanel.setBackground(null);

        DiyJLabel BaseMainControlFieldLabel = new DiyJLabel("请选择模式");
        BaseMainControlFieldLabel.setName("BaseMainControlFieldLabel");
        BaseMainControlFieldLabel.setBackground(null);

        BaseMainControlFieldPanel.add(BaseMainControlFieldLabel,new GridBagConstraints(
                0,BaseMainControlFieldPanel.getComponentCount(),
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return BaseMainControlFieldPanel;
    }

    public JComponent BaseMainControlModePanel(){
        JComponent BaseMainControlModePanel = new JPanel(new GridBagLayout());
        BaseMainControlModePanel.setName("BaseMainControlModePanel");

        HashMap<String, JComponent> jComponentHashMap = new HashMap<String, JComponent>(){
            {
                put("Base16",Helper.blackPanel());
                put("Base32",Helper.blackPanel());
                put("Base36",Helper.blackPanel());
                put("Base58",Helper.blackPanel());
                put("Base62",Helper.blackPanel());
                put("Base64",Helper.blackPanel());
                put("Base85",Helper.blackPanel());
                put("Base91",Helper.blackPanel());
                put("Base92",Helper.blackPanel());
                put("Base128",Helper.blackPanel());
            }
        };

        String[] labelNames = new String[]{"Base16","Base32","Base36","Base58","Base62","Base64","Base85","Base91","Base92","Base128"};

        Helper.set4DiyJChangeLabel(BaseMainControlModePanel, labelNames, jComponentHashMap);

        return BaseMainControlModePanel;
    }

    public JComponent getBaseMainPanel(String TagName){
        return MainPanelHashMap.containsKey(TagName)?(MainPanelHashMap.get(TagName)!=null?MainPanelHashMap.get(TagName):Helper.blackPanel()):Helper.blackPanel();
    }


    static class BaseMainKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e){
        }

        @Override
        public void keyPressed(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {

            if( (e.getModifiers()== InputEvent.CTRL_MASK || e.getModifiers() == InputEvent.META_MASK) && e.getKeyCode()==71){
                JTextArea eTextArea = (JTextArea) e.getSource();
                JComponent rootPanel = (JComponent) eTextArea.getParent().getParent().getParent();
                JTextArea tTextArea;
                DiyJLabel tLabel;
                switch (eTextArea.getName()){
                    case "BaseMainPlainArea":
                        tTextArea = (JTextArea) Helper.getComponent(rootPanel, "BaseMainCipherArea"); assert tTextArea!=null;
                        tLabel = (DiyJLabel) Helper.getComponent(BaseComponentPanel, "BaseMainControlFieldLabel"); assert tLabel!=null;

                        tTextArea.setText(Base.encrypt(eTextArea.getText(),tLabel.getText().split(": ")[1]));
                        break;
                    case "BaseMainCipherArea":
                        tTextArea = (JTextArea) Helper.getComponent(rootPanel, "BaseMainPlainArea"); assert tTextArea!=null;
                        tLabel = (DiyJLabel) Helper.getComponent(BaseComponentPanel, "BaseMainControlFieldLabel"); assert tLabel!=null;
                        tTextArea.setText(Base.decrypt(eTextArea.getText(), tLabel.getText().split(": ")[1]));
                        break;
                }
            }
        }
    }
    

}
