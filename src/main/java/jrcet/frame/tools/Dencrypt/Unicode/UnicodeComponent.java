package jrcet.frame.tools.Dencrypt.Unicode;

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

public class UnicodeComponent extends DiyJComponent {

    public static HashMap<String, GridBagConstraints> ComponentConstraintHashMap = new HashMap<>();
    public static HashMap<String, JComponent> MainPanelHashMap = new HashMap<>();

    public static JComponent UnicodeComponentPanel = null;

    public JComponent main(){

        UnicodeComponentPanel = new JPanel(new GridBagLayout());
        UnicodeComponentPanel.setName("UnicodeComponentPanel");
        UnicodeComponentPanel.setBackground(Color.WHITE);


        UnicodeComponentPanel.add(UnicodeTagTabPanel(),new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        MainPanelHashMap.put("1", UnicodeMainPanel());
        Helper.setConstraints(ComponentConstraintHashMap,UnicodeComponentPanel,getUnicodeMainPanel("1"),new GridBagConstraints(
                0,1,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(5,5,5,5),
                0,0
        ));

        return UnicodeComponentPanel;
    }


    public JComponent UnicodeTagTabPanel(){

        JPanel UnicodeTagTabPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,0,0));
        UnicodeTagTabPanel.setName("UnicodeTagTabPanel");
        UnicodeTagTabPanel.setBackground(Color.WHITE);

        DiyJAddLabel UnicodeTagTabSticker1Label = new DiyJAddLabel("1",true);
        UnicodeTagTabSticker1Label.setName("UnicodeTagTabSticker1Label");
        UnicodeTagTabSticker1Label.setPanel(getUnicodeMainPanel("1"));
        UnicodeTagTabPanel.add(UnicodeTagTabSticker1Label);

        DiyJAddLabel UnicodeTabAddLabel = new DiyJAddLabel("···");
        UnicodeTabAddLabel.setName("UnicodeTabAddLabel");
        UnicodeTagTabPanel.add(UnicodeTabAddLabel);

        return UnicodeTagTabPanel;
    }

    public JComponent UnicodeMainPanel(){

        JComponent UnicodeMainPanel = new JPanel(new GridBagLayout());
        UnicodeMainPanel.setName("UnicodeMainPanel");
        UnicodeMainPanel.setPreferredSize(new Dimension(0,0));

        UnicodeMainPanel.add(UnicodeMainBorderPanel(),new GridBagConstraints(
                0,0,
                1,2,
                0.2,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        UnicodeMainPanel.add(UnicodeMainStringScrollPane(), new GridBagConstraints(
                1,0,
                1,1,
                0.6,0.5,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        UnicodeMainPanel.add(UnicodeMainUnicodeScrollPane(), new GridBagConstraints(
                1,1,
                1,1,
                0.6,0.5,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        UnicodeMainPanel.add(UnicodeMainBorderPanel(),new GridBagConstraints(
                2,0,
                1,2,
                0.2,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return UnicodeMainPanel;
    }

    public JComponent UnicodeMainBorderPanel(){
        JComponent UnicodeMainBorderPanel = new JPanel();
        UnicodeMainBorderPanel.setName("UnicodeMainBorderPanel");

        return  UnicodeMainBorderPanel;
    }

    public JComponent UnicodeMainStringScrollPane(){

        RSyntaxTextArea UnicodeMainStringArea = new RSyntaxTextArea();
        UnicodeMainStringArea.setName("UnicodeMainStringArea");
        UnicodeMainStringArea.setCodeFoldingEnabled(true);
        UnicodeMainStringArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        UnicodeMainStringArea.addKeyListener(new UnicodeMainKeyListener());

        RTextScrollPane UnicodeMainStringScrollPane = new RTextScrollPane(UnicodeMainStringArea);
        UnicodeMainStringScrollPane.setName("UnicodeMainStringScrollPane");
        UnicodeMainStringScrollPane.setPreferredSize(new Dimension(0,0));
        UnicodeMainStringScrollPane.setBorder(BorderFactory.createMatteBorder(1,0,1,0, Setting.class2DefaultDiyJTabBorderColor));

        return UnicodeMainStringScrollPane;

    }

    public JComponent UnicodeMainUnicodeScrollPane(){

        //setCodeFoldingEnabled需要在setSyntaxEditingStyle前面
        RSyntaxTextArea UnicodeMainUnicodeArea = new RSyntaxTextArea();
        UnicodeMainUnicodeArea.setName("UnicodeMainUnicodeArea");
        UnicodeMainUnicodeArea.setLineWrap(true);
        UnicodeMainUnicodeArea.setCodeFoldingEnabled(true);
        UnicodeMainUnicodeArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        UnicodeMainUnicodeArea.addKeyListener(new UnicodeMainKeyListener());

        RTextScrollPane UnicodeMainUnicodeScrollPane = new RTextScrollPane(UnicodeMainUnicodeArea);
        UnicodeMainUnicodeScrollPane.setName("UnicodeMainPlaintextScrollPane");
        UnicodeMainUnicodeScrollPane.setHorizontalScrollBarPolicy(RTextScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        UnicodeMainUnicodeScrollPane.setPreferredSize(new Dimension(0,0));
        UnicodeMainUnicodeScrollPane.setBorder(null);

        return UnicodeMainUnicodeScrollPane;
    }

    public JComponent getUnicodeMainPanel(String TagName){
        return MainPanelHashMap.containsKey(TagName)?(MainPanelHashMap.get(TagName)!=null?MainPanelHashMap.get(TagName):UnicodeBlackPanel()):UnicodeBlackPanel();
    }

    public JComponent UnicodeBlackPanel(){
        JPanel UnicodeBlackPanel = new JPanel();
        UnicodeBlackPanel.setName("UnicodeBlackPanel");
        UnicodeBlackPanel.setOpaque(true);
        UnicodeBlackPanel.setBackground(Color.PINK);

        return UnicodeBlackPanel;
    }

    static class UnicodeMainKeyListener implements KeyListener {

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
                String tText = "未成功";
                switch (eArea.getName()){
                    case "UnicodeMainStringArea":
                        outputArea = (RSyntaxTextArea) Helper.getComponent((JComponent) eArea.getParent().getParent().getParent(), "UnicodeMainUnicodeArea" );
                        tText = Unicode.stringToUnicode(eArea.getText());
                        break;
                    case "UnicodeMainUnicodeArea":
                        outputArea = (RSyntaxTextArea) Helper.getComponent((JComponent) eArea.getParent().getParent().getParent(), "UnicodeMainStringArea" );
                        tText=Unicode.unicodeToString(eArea.getText());
                        break;
                }
                assert outputArea != null;
                outputArea.setText(tText);
            }
        }
    }


}
