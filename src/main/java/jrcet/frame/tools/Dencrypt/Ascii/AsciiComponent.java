package jrcet.frame.tools.Dencrypt.Ascii;

import jrcet.lib.Helper;
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

        AsciiMainPanel.add(AsciiMainBorderPanel(),new GridBagConstraints(
                0,0,
                1,2,
                0.2,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        AsciiMainPanel.add(AsciiMainStringScrollPane(), new GridBagConstraints(
                1,0,
                1,1,
                0.6,0.5,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        AsciiMainPanel.add(AsciiMainAsciiScrollPane(), new GridBagConstraints(
                1,1,
                1,1,
                0.6,0.5,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        AsciiMainPanel.add(AsciiMainBorderPanel(),new GridBagConstraints(
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

    public JComponent AsciiMainBorderPanel(){
        JComponent AsciiMainBorderPanel = new JPanel();
        AsciiMainBorderPanel.setName("AsciiMainBorderPanel");

        return  AsciiMainBorderPanel;
    }

    public JComponent AsciiMainStringScrollPane(){

        RSyntaxTextArea AsciiMainStringArea = new RSyntaxTextArea();
        AsciiMainStringArea.setName("AsciiMainStringArea");
        AsciiMainStringArea.setText("#单个字符直接得到结果，多个字符默认提取第一个字符作为Ascii结果的分割符。如` abc`的结果为`97 98 99`");
        AsciiMainStringArea.setLineWrap(true);
        AsciiMainStringArea.setCodeFoldingEnabled(true);
        AsciiMainStringArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        AsciiMainStringArea.addKeyListener(new AsciiMainKeyListener());

        RTextScrollPane AsciiMainStringScrollPane = new RTextScrollPane(AsciiMainStringArea);
        AsciiMainStringScrollPane.setName("AsciiMainStringScrollPane");
        AsciiMainStringScrollPane.setHorizontalScrollBarPolicy(RTextScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        AsciiMainStringScrollPane.setPreferredSize(new Dimension(0,0));
        AsciiMainStringScrollPane.setBorder(BorderFactory.createMatteBorder(1,0,1,0, Setting.class2DefaultDiyJTabBorderColor));

        return AsciiMainStringScrollPane;

    }

    public JComponent AsciiMainAsciiScrollPane(){

        //setCodeFoldingEnabled需要在setSyntaxEditingStyle前面
        RSyntaxTextArea AsciiMainAsciiArea = new RSyntaxTextArea();
        AsciiMainAsciiArea.setText("#默认提取首次出现在相邻数字间的字符作为结果的分割符。如`103 103 103`,则结果为`g g g`");
        AsciiMainAsciiArea.setName("AsciiMainAsciiArea");
        AsciiMainAsciiArea.setLineWrap(true);
        AsciiMainAsciiArea.setCodeFoldingEnabled(true);
        AsciiMainAsciiArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        AsciiMainAsciiArea.addKeyListener(new AsciiMainKeyListener());

        RTextScrollPane AsciiMainAsciiScrollPane = new RTextScrollPane(AsciiMainAsciiArea);
        AsciiMainAsciiScrollPane.setName("AsciiMainAsciiScrollPane");
        AsciiMainAsciiScrollPane.setHorizontalScrollBarPolicy(RTextScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        AsciiMainAsciiScrollPane.setPreferredSize(new Dimension(0,0));
        AsciiMainAsciiScrollPane.setBorder(null);

        return AsciiMainAsciiScrollPane;
    }

    public JComponent AsciiBlackPanel(){
        JPanel AsciiBlackPanel = new JPanel();
        AsciiBlackPanel.setName("AsciiBlackPanel");
        AsciiBlackPanel.setOpaque(true);
        AsciiBlackPanel.setBackground(Color.PINK);

        return AsciiBlackPanel;
    }

    public JComponent getAsciiMainPanel(String TagName){
        return MainPanelHashMap.containsKey(TagName)?(MainPanelHashMap.get(TagName)!=null?MainPanelHashMap.get(TagName):AsciiBlackPanel()):AsciiBlackPanel();
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
                if ("AsciiMainStringArea".equals(eArea.getName())) {
                    outputArea = (RSyntaxTextArea) Helper.getComponent((JComponent) eArea.getParent().getParent().getParent(), "AsciiMainAsciiArea");
                    assert outputArea != null;
                    outputArea.setText(Ascii.char2Ascii(eArea.getText()));
                    outputArea.updateUI();
                }

                if ("AsciiMainAsciiArea".equals(eArea.getName())) {
                    outputArea = (RSyntaxTextArea) Helper.getComponent((JComponent) eArea.getParent().getParent().getParent(), "AsciiMainStringArea");
                    assert outputArea != null;
                    outputArea.setText(Ascii.ascii2Char(eArea.getText()));
                    outputArea.updateUI();
                }
            }
        }
    }


}
