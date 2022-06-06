package jrcet.frame.tools.Dencrypt.Ascii;

import jrcet.diycomponents.DiyJLabel;
import jrcet.help.Helper;
import jrcet.diycomponents.DiyJAddLabel;
import jrcet.diycomponents.DiyJComponent;
import jrcet.diycomponents.DiyJTextArea.ui.rsyntaxtextarea.RSyntaxTextArea;
import jrcet.diycomponents.DiyJTextArea.ui.rsyntaxtextarea.SyntaxConstants;
import jrcet.diycomponents.DiyJTextArea.ui.rtextarea.RTextScrollPane;
import jrcet.frame.setting.Setting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public class AsciiComponent extends DiyJComponent {

    public static HashMap<String, GridBagConstraints> ComponentConstraintHashMap = new HashMap<>();
    public static HashMap<String, JComponent> MainPanelHashMap = new HashMap<>();

    public static JComponent AsciiComponentPanel = null;

    public JComponent main(){

        AsciiComponentPanel = new JPanel(new GridBagLayout());
        AsciiComponentPanel.setName("AsciiComponentPanel");
        AsciiComponentPanel.setBackground(Color.WHITE);

        AsciiComponentPanel.add(AsciiTagTabPanel(),new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        MainPanelHashMap.put("1", AsciiMainPanel());
        Helper.setConstraints(ComponentConstraintHashMap, AsciiComponentPanel, getAsciiMainPanel("1"),new GridBagConstraints(
                0,1,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(5,5,5,5),
                0,0
        ));

        return AsciiComponentPanel;
    }

    public JComponent AsciiTagTabPanel(){

        JPanel AsciiTagTabPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,0,0));
        AsciiTagTabPanel.setName("AsciiTagTabPanel");
        AsciiTagTabPanel.setBackground(Color.WHITE);
        AsciiTagTabPanel.setBorder(BorderFactory.createMatteBorder(0,0,0,0,new Color(203,208,209)));

        DiyJAddLabel AsciiTagTabSticker1Label = new DiyJAddLabel("1",true);
        AsciiTagTabSticker1Label.setName("AsciiTagTabSticker1Label");
        AsciiTagTabSticker1Label.setPanel(getAsciiMainPanel("1"));
        AsciiTagTabPanel.add(AsciiTagTabSticker1Label);

        DiyJAddLabel AsciiTabAddLabel = new DiyJAddLabel("···");
        AsciiTabAddLabel.setName("AsciiTabAddLabel");
        AsciiTagTabPanel.add(AsciiTabAddLabel);

        return AsciiTagTabPanel;
    }

    public JComponent AsciiMainPanel(){

        JComponent AsciiMainPanel = new JPanel(new GridBagLayout());
        AsciiMainPanel.setName("AsciiMainPanel");
        AsciiMainPanel.setPreferredSize(new Dimension(0,0));

        AsciiMainPanel.add(Helper.blackPanel(),new GridBagConstraints(
                0,0,
                1,2,
                0.2,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        AsciiMainPanel.add(AsciiMainPlainScrollPane(), new GridBagConstraints(
                1,0,
                1,1,
                0.6,0.5,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        AsciiMainPanel.add(AsciiMainCipherScrollPane(), new GridBagConstraints(
                1,1,
                1,1,
                0.6,0.5,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        AsciiMainPanel.add(AsciiMainControlPanel(), new GridBagConstraints(
                2,0,
                1,2,
                0.2,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return AsciiMainPanel;
    }


    public JComponent AsciiMainPlainScrollPane(){

        RSyntaxTextArea AsciiMainPlainArea = new RSyntaxTextArea();
        AsciiMainPlainArea.setName("AsciiMainPlainArea");
        AsciiMainPlainArea.setText("#输入文件地址或者字符串");
        AsciiMainPlainArea.setLineWrap(true);
        AsciiMainPlainArea.setCodeFoldingEnabled(true);
        AsciiMainPlainArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        AsciiMainPlainArea.addKeyListener(new AsciiMainKeyListener());

        RTextScrollPane AsciiMainPlainScrollPane = new RTextScrollPane(AsciiMainPlainArea);
        AsciiMainPlainScrollPane.setName("AsciiMainPlainScrollPane");
        AsciiMainPlainScrollPane.setHorizontalScrollBarPolicy(RTextScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        AsciiMainPlainScrollPane.setPreferredSize(new Dimension(0,0));
        AsciiMainPlainScrollPane.setBorder(BorderFactory.createMatteBorder(1,0,1,0, Setting.gray));

        return AsciiMainPlainScrollPane;

    }

    public JComponent AsciiMainCipherScrollPane(){

        //setCodeFoldingEnabled需要在setSyntaxEditingStyle前面
        RSyntaxTextArea AsciiMainCipherArea = new RSyntaxTextArea();
        AsciiMainCipherArea.setText("#输入文件地址或者字符串");
        AsciiMainCipherArea.setName("AsciiMainCipherArea");
        AsciiMainCipherArea.setLineWrap(true);
        AsciiMainCipherArea.setCodeFoldingEnabled(true);
        AsciiMainCipherArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        AsciiMainCipherArea.addKeyListener(new AsciiMainKeyListener());

        RTextScrollPane AsciiMainCipherScrollPane = new RTextScrollPane(AsciiMainCipherArea);
        AsciiMainCipherScrollPane.setName("AsciiMainCipherScrollPane");
        AsciiMainCipherScrollPane.setHorizontalScrollBarPolicy(RTextScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        AsciiMainCipherScrollPane.setPreferredSize(new Dimension(0,0));
        AsciiMainCipherScrollPane.setBorder(null);

        return AsciiMainCipherScrollPane;
    }

    public JComponent AsciiMainControlPanel(){
        JComponent AsciiMainControlPanel = new JPanel(new GridBagLayout());
        AsciiMainControlPanel.setName("AsciiMainControlPanel");

        AsciiMainControlPanel.add(AsciiMainControlPlainPanel(), new GridBagConstraints(
                0,0,
                1,1,
                1,0.5,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        AsciiMainControlPanel.add(AsciiMainControlCipherPanel(), new GridBagConstraints(
                0,1,
                1,1,
                1,0.5,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return AsciiMainControlPanel;
    }

    public JComponent AsciiMainControlPlainPanel(){
        JComponent AsciiMainControlPlainPanel = new JPanel(new GridBagLayout());
        AsciiMainControlPlainPanel.setName("AsciiMainControlPlainPanel");
        AsciiMainControlPlainPanel.setPreferredSize(new Dimension(0,0));

        JLabel AsciiMainControlPlainSeparatorLabel = new JLabel("输入分割符:");
        AsciiMainControlPlainSeparatorLabel.setName("AsciiMainControlPlainSeparatorLabel");
        AsciiMainControlPlainSeparatorLabel.setPreferredSize(new Dimension(70,30));

        JTextField AsciiMainControlPlainSeparatorField = new JTextField("换行");
        AsciiMainControlPlainSeparatorField.setName("AsciiMainControlPlainSeparatorField");
        AsciiMainControlPlainSeparatorField.setPreferredSize(new Dimension(0,30));

        JLabel AsciiMainControlPlainSeparator2Label = new JLabel("输出分割符:");
        AsciiMainControlPlainSeparatorLabel.setName("AsciiMainControlPlainSeparator2Label");
        AsciiMainControlPlainSeparatorLabel.setPreferredSize(new Dimension(75,30));

        JTextField AsciiMainControlPlainSeparator2Field = new JTextField("换行");
        AsciiMainControlPlainSeparator2Field.setName("AsciiMainControlPlainSeparator2Field");
        AsciiMainControlPlainSeparator2Field.setPreferredSize(new Dimension(0,30));

        AsciiMainControlPlainPanel.add(AsciiMainControlPlainSeparatorLabel, new GridBagConstraints(
                0,0,
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,5,0,0),
                0,0
        ));

        AsciiMainControlPlainPanel.add(AsciiMainControlPlainSeparatorField, new GridBagConstraints(
                1,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        AsciiMainControlPlainPanel.add(AsciiMainControlSeparatorPanel(), new GridBagConstraints(
                0,1,
                2,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        AsciiMainControlPlainPanel.add(AsciiMainControlPlainSeparator2Label, new GridBagConstraints(
                0,2,
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,5,0,0),
                0,0
        ));

        AsciiMainControlPlainPanel.add(AsciiMainControlPlainSeparator2Field, new GridBagConstraints(
                1,2,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));


        AsciiMainControlPlainPanel.add(Helper.blackPanel(), new GridBagConstraints(
                0,3,
                2,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return AsciiMainControlPlainPanel;
    }


    public JComponent AsciiMainControlCipherPanel(){
        JComponent AsciiMainControlCipherPanel = new JPanel(new GridBagLayout());
        AsciiMainControlCipherPanel.setName("AsciiMainControlCipherPanel");
        AsciiMainControlCipherPanel.setPreferredSize(new Dimension(0,0));

        JLabel AsciiMainControlCipherSeparatorLabel = new JLabel("输入分割符:");
        AsciiMainControlCipherSeparatorLabel.setName("AsciiMainControlCipherSeparatorLabel");
        AsciiMainControlCipherSeparatorLabel.setPreferredSize(new Dimension(75,30));

        JTextField AsciiMainControlCipherSeparatorField = new JTextField("换行");
        AsciiMainControlCipherSeparatorField.setName("AsciiMainControlCipherSeparatorField");
        AsciiMainControlCipherSeparatorField.setPreferredSize(new Dimension(0,30));

        JLabel AsciiMainControlCipherSeparator2Label = new JLabel("输出分割符:");
        AsciiMainControlCipherSeparator2Label.setName("AsciiMainControlCipherSeparator2Label");
        AsciiMainControlCipherSeparator2Label.setPreferredSize(new Dimension(75,30));

        JTextField AsciiMainControlCipherSeparator2Field = new JTextField("换行");
        AsciiMainControlCipherSeparator2Field.setName("AsciiMainControlCipherSeparator2Field");
        AsciiMainControlCipherSeparator2Field.setPreferredSize(new Dimension(0,30));

        AsciiMainControlCipherPanel.add(AsciiMainControlCipherSeparatorLabel, new GridBagConstraints(
                0,0,
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,5,0,0),
                0,0
        ));

        AsciiMainControlCipherPanel.add(AsciiMainControlCipherSeparatorField, new GridBagConstraints(
                1,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        AsciiMainControlCipherPanel.add(AsciiMainControlSeparatorPanel(), new GridBagConstraints(
                0,1,
                2,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        AsciiMainControlCipherPanel.add(AsciiMainControlCipherSeparator2Label, new GridBagConstraints(
                0,2,
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,5,0,0),
                0,0
        ));

        AsciiMainControlCipherPanel.add(AsciiMainControlCipherSeparator2Field, new GridBagConstraints(
                1,2,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));


        AsciiMainControlCipherPanel.add(Helper.blackPanel(), new GridBagConstraints(
                0,3,
                2,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return AsciiMainControlCipherPanel;
    }

    public JComponent AsciiMainControlSeparatorPanel(){

        JComponent AsciiMainControlSeparatorPanel = new JPanel(new GridBagLayout());
        AsciiMainControlSeparatorPanel.setName("AsciiMainControlSeparatorPanel");
        AsciiMainControlSeparatorPanel.setPreferredSize(new Dimension(0,30));

        DiyJLabel AsciiMainControlNewlineLabel = new DiyJLabel("换行");
        AsciiMainControlNewlineLabel.setName("AsciiMainControlNewlineLabel");

        DiyJLabel AsciiMainControlCommaLabel = new DiyJLabel("逗号");
        AsciiMainControlCommaLabel.setName("AsciiMainControlCommaLabel");

        DiyJLabel AsciiMainControlSpaceLabel = new DiyJLabel("空格");
        AsciiMainControlSpaceLabel.setName("AsciiMainControlSpaceLabel");

        DiyJLabel AsciiMainControlTabLabel = new DiyJLabel("制表");
        AsciiMainControlTabLabel.setName("AsciiMainControlTabLabel");

        AsciiMainControlSeparatorPanel.add(AsciiMainControlNewlineLabel, new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        AsciiMainControlSeparatorPanel.add(AsciiMainControlCommaLabel, new GridBagConstraints(
                1,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        AsciiMainControlSeparatorPanel.add(AsciiMainControlSpaceLabel, new GridBagConstraints(
                2,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        AsciiMainControlSeparatorPanel.add(AsciiMainControlTabLabel, new GridBagConstraints(
                3,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        for(Component component : AsciiMainControlSeparatorPanel.getComponents()){
            component.setPreferredSize(new Dimension(0,25));
        }

        return AsciiMainControlSeparatorPanel;
    }


    public JComponent getAsciiMainPanel(String TagName){
        return MainPanelHashMap.containsKey(TagName)?(MainPanelHashMap.get(TagName)!=null?MainPanelHashMap.get(TagName):Helper.blackPanel()):Helper.blackPanel();
    }

    static class AsciiMainKeyListener implements KeyListener {

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
                RSyntaxTextArea outputArea = null;
                JTextField separatorField = null;
                JTextField separator2Field = null;
                JComponent rootPanel = (JComponent) eArea.getParent().getParent().getParent();
                if ("AsciiMainPlainArea".equals(eArea.getName())) {
                    outputArea = (RSyntaxTextArea) Helper.getComponent(rootPanel, "AsciiMainCipherArea"); assert outputArea != null;
                    separatorField = (JTextField) Helper.getComponent(rootPanel, "AsciiMainControlPlainSeparatorField"); assert separatorField!=null;
                    separator2Field= (JTextField) Helper.getComponent(rootPanel, "AsciiMainControlPlainSeparator2Field"); assert separator2Field!=null;
                    outputArea.setText(Ascii.encrypt(eArea.getText(), separatorField.getText(), separator2Field.getText()));
                    outputArea.updateUI();
                }

                if ("AsciiMainCipherArea".equals(eArea.getName())) {
                    outputArea = (RSyntaxTextArea) Helper.getComponent(rootPanel, "AsciiMainPlainArea"); assert outputArea != null;
                    separatorField = (JTextField) Helper.getComponent(rootPanel, "AsciiMainControlCipherSeparatorField"); assert separatorField!=null;
                    separator2Field= (JTextField) Helper.getComponent(rootPanel, "AsciiMainControlCipherSeparator2Field"); assert separator2Field!=null;
                    outputArea.setText(Ascii.decrypt(eArea.getText(), separatorField.getText(), separator2Field.getText()));
                    outputArea.updateUI();
                }
            }
        }
    }


}
