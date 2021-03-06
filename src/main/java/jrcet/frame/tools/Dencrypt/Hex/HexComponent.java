package jrcet.frame.tools.Dencrypt.Hex;

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

public class HexComponent extends DiyJComponent {

    public static HashMap<String, GridBagConstraints> ComponentConstraintHashMap = new HashMap<>();
    public static HashMap<String, JComponent> MainPanelHashMap = new HashMap<>();

    public static JComponent HexComponentPanel = null;

    public JComponent main(){

        HexComponentPanel = new JPanel(new GridBagLayout());
        HexComponentPanel.setName("HexComponentPanel");
        HexComponentPanel.setBackground(Color.WHITE);

        HexComponentPanel.add(HexTagTabPanel(),new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        MainPanelHashMap.put("1", HexMainPanel());
        Helper.setConstraints(ComponentConstraintHashMap, HexComponentPanel, getHexMainPanel("1"),new GridBagConstraints(
                0,1,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(5,5,5,5),
                0,0
        ));

        return HexComponentPanel;
    }

    public JComponent HexTagTabPanel(){

        JPanel HexTagTabPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,0,0));
        HexTagTabPanel.setName("HexTagTabPanel");
        HexTagTabPanel.setBackground(Color.WHITE);
        HexTagTabPanel.setBorder(BorderFactory.createMatteBorder(0,0,0,0,new Color(203,208,209)));

        DiyJAddLabel HexTagTabSticker1Label = new DiyJAddLabel("1",true);
        HexTagTabSticker1Label.setName("HexTagTabSticker1Label");
        HexTagTabSticker1Label.setPanel(getHexMainPanel("1"));
        HexTagTabPanel.add(HexTagTabSticker1Label);

        DiyJAddLabel HexTabAddLabel = new DiyJAddLabel("??????");
        HexTabAddLabel.setName("HexTabAddLabel");
        HexTagTabPanel.add(HexTabAddLabel);

        return HexTagTabPanel;
    }

    public JComponent HexMainPanel(){

        JComponent HexMainPanel = new JPanel(new GridBagLayout());
        HexMainPanel.setName("HexMainPanel");
        HexMainPanel.setPreferredSize(new Dimension(0,0));

        HexMainPanel.add(Helper.blackPanel(),new GridBagConstraints(
                0,0,
                1,2,
                0.2,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        HexMainPanel.add(HexMainPlainScrollPane(), new GridBagConstraints(
                1,0,
                1,1,
                0.6,0.5,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        HexMainPanel.add(HexMainCipherScrollPane(), new GridBagConstraints(
                1,1,
                1,1,
                0.6,0.5,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        HexMainPanel.add(HexMainControlPanel(), new GridBagConstraints(
                2,0,
                1,2,
                0.2,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return HexMainPanel;
    }


    public JComponent HexMainPlainScrollPane(){

        RSyntaxTextArea HexMainPlainArea = new RSyntaxTextArea();
        HexMainPlainArea.setName("HexMainPlainArea");
        HexMainPlainArea.setText("#?????????????????????????????????");
        HexMainPlainArea.setLineWrap(true);
        HexMainPlainArea.setCodeFoldingEnabled(true);
        HexMainPlainArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        HexMainPlainArea.addKeyListener(new HexMainKeyListener());

        RTextScrollPane HexMainPlainScrollPane = new RTextScrollPane(HexMainPlainArea);
        HexMainPlainScrollPane.setName("HexMainPlainScrollPane");
        HexMainPlainScrollPane.setHorizontalScrollBarPolicy(RTextScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        HexMainPlainScrollPane.setPreferredSize(new Dimension(0,0));
        HexMainPlainScrollPane.setBorder(BorderFactory.createMatteBorder(1,0,1,0, Setting.gray));

        return HexMainPlainScrollPane;

    }

    public JComponent HexMainCipherScrollPane(){

        //setCodeFoldingEnabled?????????setSyntaxEditingStyle??????
        RSyntaxTextArea HexMainCipherArea = new RSyntaxTextArea();
        HexMainCipherArea.setText("#?????????????????????????????????");
        HexMainCipherArea.setName("HexMainCipherArea");
        HexMainCipherArea.setLineWrap(true);
        HexMainCipherArea.setCodeFoldingEnabled(true);
        HexMainCipherArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        HexMainCipherArea.addKeyListener(new HexMainKeyListener());

        RTextScrollPane HexMainCipherScrollPane = new RTextScrollPane(HexMainCipherArea);
        HexMainCipherScrollPane.setName("HexMainCipherScrollPane");
        HexMainCipherScrollPane.setHorizontalScrollBarPolicy(RTextScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        HexMainCipherScrollPane.setPreferredSize(new Dimension(0,0));
        HexMainCipherScrollPane.setBorder(null);

        return HexMainCipherScrollPane;
    }

    public JComponent HexMainControlPanel(){
        JComponent HexMainControlPanel = new JPanel(new GridBagLayout());
        HexMainControlPanel.setName("HexMainControlPanel");

        HexMainControlPanel.add(HexMainControlPlainPanel(), new GridBagConstraints(
                0,0,
                1,1,
                1,0.5,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        HexMainControlPanel.add(HexMainControlCipherPanel(), new GridBagConstraints(
                0,1,
                1,1,
                1,0.5,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return HexMainControlPanel;
    }

    public JComponent HexMainControlPlainPanel(){
        JComponent HexMainControlPlainPanel = new JPanel(new GridBagLayout());
        HexMainControlPlainPanel.setName("HexMainControlPlainPanel");
        HexMainControlPlainPanel.setPreferredSize(new Dimension(0,0));

        JLabel HexMainControlPlainSeparatorLabel = new JLabel("???????????????:");
        HexMainControlPlainSeparatorLabel.setName("HexMainControlPlainSeparatorLabel");
        HexMainControlPlainSeparatorLabel.setPreferredSize(new Dimension(70,30));

        JTextField HexMainControlPlainSeparatorField = new JTextField("??????");
        HexMainControlPlainSeparatorField.setName("HexMainControlPlainSeparatorField");
        HexMainControlPlainSeparatorField.setPreferredSize(new Dimension(0,30));

        JLabel HexMainControlPlainSeparator2Label = new JLabel("???????????????:");
        HexMainControlPlainSeparatorLabel.setName("HexMainControlPlainSeparator2Label");
        HexMainControlPlainSeparatorLabel.setPreferredSize(new Dimension(75,30));

        JTextField HexMainControlPlainSeparator2Field = new JTextField("??????");
        HexMainControlPlainSeparator2Field.setName("HexMainControlPlainSeparator2Field");
        HexMainControlPlainSeparator2Field.setPreferredSize(new Dimension(0,30));

        HexMainControlPlainPanel.add(HexMainControlPlainSeparatorLabel, new GridBagConstraints(
                0,0,
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,5,0,0),
                0,0
        ));

        HexMainControlPlainPanel.add(HexMainControlPlainSeparatorField, new GridBagConstraints(
                1,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        HexMainControlPlainPanel.add(HexMainControlSeparatorPanel(), new GridBagConstraints(
                0,1,
                2,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        HexMainControlPlainPanel.add(HexMainControlPlainSeparator2Label, new GridBagConstraints(
                0,2,
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,5,0,0),
                0,0
        ));

        HexMainControlPlainPanel.add(HexMainControlPlainSeparator2Field, new GridBagConstraints(
                1,2,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));


        HexMainControlPlainPanel.add(Helper.blackPanel(), new GridBagConstraints(
                0,3,
                2,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return HexMainControlPlainPanel;
    }


    public JComponent HexMainControlCipherPanel(){
        JComponent HexMainControlCipherPanel = new JPanel(new GridBagLayout());
        HexMainControlCipherPanel.setName("HexMainControlCipherPanel");
        HexMainControlCipherPanel.setPreferredSize(new Dimension(0,0));

        JLabel HexMainControlCipherSeparatorLabel = new JLabel("???????????????:");
        HexMainControlCipherSeparatorLabel.setName("HexMainControlCipherSeparatorLabel");
        HexMainControlCipherSeparatorLabel.setPreferredSize(new Dimension(75,30));

        JTextField HexMainControlCipherSeparatorField = new JTextField("??????");
        HexMainControlCipherSeparatorField.setName("HexMainControlCipherSeparatorField");
        HexMainControlCipherSeparatorField.setPreferredSize(new Dimension(0,30));

        JLabel HexMainControlCipherSeparator2Label = new JLabel("???????????????:");
        HexMainControlCipherSeparator2Label.setName("HexMainControlCipherSeparator2Label");
        HexMainControlCipherSeparator2Label.setPreferredSize(new Dimension(75,30));

        JTextField HexMainControlCipherSeparator2Field = new JTextField("??????");
        HexMainControlCipherSeparator2Field.setName("HexMainControlCipherSeparator2Field");
        HexMainControlCipherSeparator2Field.setPreferredSize(new Dimension(0,30));

        HexMainControlCipherPanel.add(HexMainControlCipherSeparatorLabel, new GridBagConstraints(
                0,0,
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,5,0,0),
                0,0
        ));

        HexMainControlCipherPanel.add(HexMainControlCipherSeparatorField, new GridBagConstraints(
                1,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        HexMainControlCipherPanel.add(HexMainControlSeparatorPanel(), new GridBagConstraints(
                0,1,
                2,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        HexMainControlCipherPanel.add(HexMainControlCipherSeparator2Label, new GridBagConstraints(
                0,2,
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,5,0,0),
                0,0
        ));

        HexMainControlCipherPanel.add(HexMainControlCipherSeparator2Field, new GridBagConstraints(
                1,2,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));


        HexMainControlCipherPanel.add(Helper.blackPanel(), new GridBagConstraints(
                0,3,
                2,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return HexMainControlCipherPanel;
    }

    public JComponent HexMainControlSeparatorPanel(){

        JComponent HexMainControlSeparatorPanel = new JPanel(new GridBagLayout());
        HexMainControlSeparatorPanel.setName("HexMainControlSeparatorPanel");
        HexMainControlSeparatorPanel.setPreferredSize(new Dimension(0,30));

        DiyJLabel HexMainControlNewlineLabel = new DiyJLabel("??????");
        HexMainControlNewlineLabel.setName("HexMainControlNewlineLabel");

        DiyJLabel HexMainControlCommaLabel = new DiyJLabel("??????");
        HexMainControlCommaLabel.setName("HexMainControlCommaLabel");

        DiyJLabel HexMainControlSpaceLabel = new DiyJLabel("??????");
        HexMainControlSpaceLabel.setName("HexMainControlSpaceLabel");

        DiyJLabel HexMainControlTabLabel = new DiyJLabel("??????");
        HexMainControlTabLabel.setName("HexMainControlTabLabel");

        HexMainControlSeparatorPanel.add(HexMainControlNewlineLabel, new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        HexMainControlSeparatorPanel.add(HexMainControlCommaLabel, new GridBagConstraints(
                1,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        HexMainControlSeparatorPanel.add(HexMainControlSpaceLabel, new GridBagConstraints(
                2,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        HexMainControlSeparatorPanel.add(HexMainControlTabLabel, new GridBagConstraints(
                3,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        for(Component component : HexMainControlSeparatorPanel.getComponents()){
            component.setPreferredSize(new Dimension(0,25));
        }

        return HexMainControlSeparatorPanel;
    }


    public JComponent getHexMainPanel(String TagName){
        return MainPanelHashMap.containsKey(TagName)?(MainPanelHashMap.get(TagName)!=null?MainPanelHashMap.get(TagName):Helper.blackPanel()):Helper.blackPanel();
    }

    static class HexMainKeyListener implements KeyListener {

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
                if ("HexMainPlainArea".equals(eArea.getName())) {
                    outputArea = (RSyntaxTextArea) Helper.getComponent(rootPanel, "HexMainCipherArea"); assert outputArea != null;
                    separatorField = (JTextField) Helper.getComponent(rootPanel, "HexMainControlPlainSeparatorField"); assert separatorField!=null;
                    separator2Field= (JTextField) Helper.getComponent(rootPanel, "HexMainControlPlainSeparator2Field"); assert separator2Field!=null;
                    outputArea.setText(Hex.encrypt(eArea.getText(), separatorField.getText(), separator2Field.getText()));
                    outputArea.updateUI();
                }

                if ("HexMainCipherArea".equals(eArea.getName())) {
                    outputArea = (RSyntaxTextArea) Helper.getComponent(rootPanel, "HexMainPlainArea"); assert outputArea != null;
                    separatorField = (JTextField) Helper.getComponent(rootPanel, "HexMainControlCipherSeparatorField"); assert separatorField!=null;
                    separator2Field= (JTextField) Helper.getComponent(rootPanel, "HexMainControlCipherSeparator2Field"); assert separator2Field!=null;
                    outputArea.setText(Hex.decrypt(eArea.getText(), separatorField.getText(), separator2Field.getText()));
                    outputArea.updateUI();
                }
            }
        }
    }


}
