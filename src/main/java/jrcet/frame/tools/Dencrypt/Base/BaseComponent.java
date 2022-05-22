package jrcet.frame.tools.Dencrypt.Base;

import javafx.scene.shape.HLineTo;
import jrcet.diycomponents.DiyJComboBox;
import jrcet.diycomponents.DiyJTextArea.ui.rsyntaxtextarea.RSyntaxTextArea;
import jrcet.diycomponents.DiyJTextArea.ui.rsyntaxtextarea.SyntaxConstants;
import jrcet.diycomponents.DiyJTextArea.ui.rtextarea.RTextScrollPane;
import jrcet.frame.tools.Dencrypt.Base.Base;
import jrcet.frame.tools.Dencrypt.Base.BaseComponent;
import jrcet.lib.Helper;
import jrcet.diycomponents.DiyJAddLabel;
import jrcet.diycomponents.DiyJComponent;
import jrcet.frame.setting.Setting;

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

        Base.initBase();
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
        Helper.setConstraints(ComponentConstraintHashMap, BaseComponentPanel, MainPanelHashMap.get("1"), new GridBagConstraints(
                0,1,
                2,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(5,5,5,5),
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

        RSyntaxTextArea BaseMainPlainArea = new RSyntaxTextArea();
        BaseMainPlainArea.setName("BaseMainPlainArea");
        BaseMainPlainArea.setCodeFoldingEnabled(true);
        BaseMainPlainArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        BaseMainPlainArea.addKeyListener(new BaseMainKeyListener());

        RTextScrollPane BaseMainPlainScrollPane = new RTextScrollPane(BaseMainPlainArea);
        BaseMainPlainScrollPane.setName("BaseMainPlainScrollPane");
        BaseMainPlainScrollPane.setPreferredSize(new Dimension(0,0));
        BaseMainPlainScrollPane.setBorder(BorderFactory.createMatteBorder(1,0,1,0, Setting.class2DefaultDiyJTabBorderColor));

        return BaseMainPlainScrollPane;

    }

    public JComponent BaseMainCipherScrollPane(){

        //setCodeFoldingEnabled需要在setSyntaxEditingStyle前面
        RSyntaxTextArea BaseMainCipherArea = new RSyntaxTextArea();
        BaseMainCipherArea.setName("BaseMainCipherArea");
        BaseMainCipherArea.setLineWrap(true);
        BaseMainCipherArea.setCodeFoldingEnabled(true);
        BaseMainCipherArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        BaseMainCipherArea.addKeyListener(new BaseMainKeyListener());

        RTextScrollPane BaseMainCipherScrollPane = new RTextScrollPane(BaseMainCipherArea);
        BaseMainCipherScrollPane.setName("BaseMainCipherScrollPane");
        BaseMainCipherScrollPane.setHorizontalScrollBarPolicy(RTextScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        BaseMainCipherScrollPane.setPreferredSize(new Dimension(0,0));
        BaseMainCipherScrollPane.setBorder(null);

        return BaseMainCipherScrollPane;
    }

    public JComponent BaseMainControlPanel(){

        JComponent BaseMainControlPanel = new JPanel(new GridBagLayout());
        BaseMainControlPanel.setName("BaseMainControlPanel");

        DiyJComboBox<String> BaseMainControlModeBox = new DiyJComboBox<>(new String[]{"Base64","Base32","Base58"});
        BaseMainControlModeBox.setName("BaseMainControlModeBox");
        BaseMainControlModeBox.setPreferredSize(new Dimension(0,30));

        BaseMainControlPanel.add(BaseMainControlModeBox, new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(5,0,0,0),
                0,0
        ));

        BaseMainControlPanel.add(Helper.blackPanel(), new GridBagConstraints(
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
                RSyntaxTextArea eTextArea = (RSyntaxTextArea) e.getSource();
                JComponent rootPanel = (JComponent) eTextArea.getParent().getParent().getParent();
                RSyntaxTextArea tTextArea;
                switch (eTextArea.getName()){
                    case "BaseMainPlainArea":
                        tTextArea = (RSyntaxTextArea) Helper.getComponent(rootPanel, "BaseMainCipherArea"); assert tTextArea!=null;
                        tTextArea.setText(Base.encrypt(eTextArea.getText()));
                        break;
                    case "BaseMainCipherArea":
                        tTextArea = (RSyntaxTextArea) Helper.getComponent(rootPanel, "BaseMainPlainArea"); assert tTextArea!=null;
                        tTextArea.setText(Base.decrypt(eTextArea.getText()));
                        break;
                }
            }
        }
    }
    

}
