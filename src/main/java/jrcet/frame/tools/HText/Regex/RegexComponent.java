package jrcet.frame.tools.HText.Regex;

import jrcet.diycomponents.*;
import jrcet.diycomponents.DiyJTextArea.ui.rsyntaxtextarea.RSyntaxTextArea;
import jrcet.diycomponents.DiyJTextArea.ui.rsyntaxtextarea.SyntaxConstants;
import jrcet.diycomponents.DiyJTextArea.ui.rtextarea.RTextScrollPane;
import jrcet.lib.Helper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class RegexComponent extends DiyJComponent {


    public static HashMap<String, GridBagConstraints> ComponentConstraintHashMap = new HashMap<>();
    public static HashMap<String, JComponent> MainPanelHashMap = new HashMap<>();

    public static JComponent RegexComponentPanel = null;

    @Override
    public JComponent main() {

        RegexComponentPanel = new JPanel(new GridBagLayout());
        RegexComponentPanel.setBackground(Color.WHITE);
        RegexComponentPanel.setName("RegexComponentPanel");

        RegexComponentPanel.add(RegexTagTabPanel(),new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        MainPanelHashMap.put("1", RegexMainPanel());
        Helper.setConstraints(ComponentConstraintHashMap, RegexComponentPanel, getRegexMainPanel("1"),new GridBagConstraints(
                0,1,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return RegexComponentPanel;
    }

    public JComponent RegexTagTabPanel(){

        JPanel RegexTagTabPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,0,0));
        RegexTagTabPanel.setName("RegexTagTabPanel");
        RegexTagTabPanel.setBackground(Color.WHITE);
        RegexTagTabPanel.setBorder(BorderFactory.createMatteBorder(0,0,0,0,new Color(203,208,209)));

        DiyJAddLabel RegexTagTabSticker1Label = new DiyJAddLabel("1",true);
        RegexTagTabSticker1Label.setName("RegexTagTabSticker1Label");
        RegexTagTabSticker1Label.setPanel(getRegexMainPanel("1"));
        RegexTagTabPanel.add(RegexTagTabSticker1Label);

        DiyJAddLabel RegexTabAddLabel = new DiyJAddLabel("···");
        RegexTabAddLabel.setName("RegexTabAddLabel");
        RegexTagTabPanel.add(RegexTabAddLabel);

        return RegexTagTabPanel;
    }

    public JComponent RegexMainPanel(){
        JPanel RegexMainPanel = new JPanel(new GridBagLayout());
        RegexMainPanel.setName("RegexMainPanel");
        RegexMainPanel.setBackground(Color.WHITE);
        RegexMainPanel.setPreferredSize(new Dimension(0,0));

        RegexMainPanel.add(Helper.blackPanel(),new GridBagConstraints(
                0,0,
                1,1,
                0.2,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        RegexMainPanel.add(RegexMainInputAreaScrollPane(),new GridBagConstraints(
                1,0,
                1,1,
                0.6,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        RegexMainPanel.add(RegexMainControlPanel(),new GridBagConstraints(
                2,0,
                1,1,
                0.2,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return RegexMainPanel;
    }

    public JComponent RegexMainInputAreaScrollPane(){
        RSyntaxTextArea RegexMainInputArea = new RSyntaxTextArea();
        RegexMainInputArea.setName("RegexMainInputArea");
        RegexMainInputArea.setCodeFoldingEnabled(true);
        RegexMainInputArea.setLineWrap(true);
        RegexMainInputArea.setText("#请输入文件路径或\\n分割的字符串...");
        RegexMainInputArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        RegexMainInputArea.addKeyListener(new RegexMainInputAreaKeyListener());

        RTextScrollPane RegexMainInputAreaScrollPane = new RTextScrollPane(RegexMainInputArea);
        RegexMainInputAreaScrollPane.setHorizontalScrollBarPolicy(RTextScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        RegexMainInputAreaScrollPane.setName("RegexMainInputAreaScrollPane");
        RegexMainInputAreaScrollPane.setPreferredSize(new Dimension(0,0));
        RegexMainInputAreaScrollPane.setBorder(null);

        return  RegexMainInputAreaScrollPane;
    }

    public JComponent RegexMainControlPanel(){
        JComponent RegexMainControlPanel = new JPanel(new GridBagLayout());
        RegexMainControlPanel.setName("RegexMainControlPanel");

        JTextField RegexMainControlRegexField = new JTextField("请输入正则");
        RegexMainControlRegexField.setName("RegexMainControlRegexField");
        RegexMainControlRegexField.setForeground(Color.GRAY);
        RegexMainControlRegexField.setPreferredSize(new Dimension(0,30));
        RegexMainControlRegexField.addFocusListener(new RegexMainControlRegexFieldFocusListener());

        RegexMainControlPanel.add(RegexMainControlRegexField, new GridBagConstraints(
                0,RegexMainControlPanel.getComponentCount(),
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(20,0,10,0),
                0,0
        ));

        RegexMainControlPanel.add(Helper.blackPanel(), new GridBagConstraints(
                0,RegexMainControlPanel.getComponentCount(),
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return RegexMainControlPanel;
    }



    public JComponent getRegexMainPanel(String TagName){
        return MainPanelHashMap.containsKey(TagName)?(MainPanelHashMap.get(TagName)!=null?MainPanelHashMap.get(TagName): Helper.blackPanel()):Helper.blackPanel();
    }

    static class RegexMainInputAreaKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {


            if( (e.getModifiers()== InputEvent.CTRL_MASK || e.getModifiers() == InputEvent.META_MASK) && e.getKeyCode()==71){
                RSyntaxTextArea eTextArea = (RSyntaxTextArea) e.getSource();
                JComponent rootPanel = (JComponent) eTextArea.getParent().getParent().getParent();
                JTextField eTextField = (JTextField) Helper.getComponent(rootPanel, "RegexMainControlRegexField"); assert eTextField!=null;

                eTextArea.setText(Regex.handle(eTextArea.getText(), eTextField.getText()));
            }
        }
    }

    static class RegexMainControlRegexFieldFocusListener implements FocusListener {

        public String hintText = "请输入正则";

        @Override
        public void focusGained(FocusEvent e) {
            JTextField eTextField = (JTextField) e.getSource();
            String temp = eTextField.getText();

            if(temp.equals(hintText)) {
                eTextField.setText("");
                eTextField.setForeground(Color.BLACK);
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            JTextField eTextField = (JTextField) e.getSource();
            String temp = eTextField.getText();
            if(temp.equals("")) {
                eTextField.setForeground(Color.GRAY);
                eTextField.setText(hintText);
            }
        }
    }



}
