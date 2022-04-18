package jrcet.frame.tools.Dencrypt.Unicode;

import burp.lib.Helper;
import jrcet.Main;
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

    public static HashMap<String, JComponent> MainPanelHashMap = new HashMap<>();

    public UnicodeComponent(){
        MainPanelHashMap.put("1", UnicodeMainPanel());
    }

    public JComponent main(){
        JComponent UnicodeComponentPanel = new JPanel(new GridBagLayout());
        UnicodeComponentPanel.setName("UnicodeComponentPanel");
        UnicodeComponentPanel.setBackground(Color.WHITE);
        UnicodeComponentPanel.setPreferredSize(new Dimension(0,0));

        UnicodeComponentPanel.add(UnicodeTagBlackPanel(), new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        UnicodeComponentPanel.add(UnicodeTagTabPanel(),new GridBagConstraints(
                1,0,
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        UnicodeComponentPanel.add(getUnicodeMainPanel("1"),new GridBagConstraints(
                0,1,
                2,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(5,5,5,5),
                0,0
        ));

        return UnicodeComponentPanel;
    }

    public JComponent UnicodeTagBlackPanel(){
        JComponent UnicodeTagBlackPanel = new JPanel();
        UnicodeTagBlackPanel.setName("UnicodeTagBlackPanel");
        UnicodeTagBlackPanel.setOpaque(false);
        UnicodeTagBlackPanel.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Setting.class4DefaultDiyJTabBorderColor));
        return UnicodeTagBlackPanel;
    }

    public JComponent UnicodeTagTabPanel(){
        JPanel UnicodeTagTabPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,0,0));
        UnicodeTagTabPanel.setName("UnicodeTagTabPanel");
        UnicodeTagTabPanel.setBorder(BorderFactory.createMatteBorder(0,0,0,0,new Color(203,208,209)));

        DiyJAddLabel UnicodeTagTabSticker1Label = new DiyJAddLabel("1",true);
        UnicodeTagTabSticker1Label.setName("UnicodeTagTabSticker1Label");
        UnicodeTagTabSticker1Label.setPanel(getUnicodeMainPanel("1"));
        UnicodeTagTabPanel.add(UnicodeTagTabSticker1Label);

        DiyJAddLabel UnicodeTabAddLabel = new DiyJAddLabel("···");
        UnicodeTabAddLabel.setName("UnicodeTabAddLabel");
        UnicodeTagTabPanel.add(UnicodeTabAddLabel);

        return UnicodeTagTabPanel;
    }

    public static JComponent UnicodeMainPanel(){

        JComponent UnicodeMainPanel = new JPanel(new GridBagLayout());
        UnicodeMainPanel.setName("UnicodeMainPanel");
        UnicodeMainPanel.setPreferredSize(new Dimension(0,0));

        UnicodeMainPanel.add(UnicodeMainInputScrollPane(), new GridBagConstraints(
                0,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        UnicodeMainPanel.add(UnicodeMainOutputScrollPane(), new GridBagConstraints(
                0,1,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return UnicodeMainPanel;
    }

    public static JComponent UnicodeMainInputScrollPane(){

        RSyntaxTextArea UnicodeMainCiphertextArea = new RSyntaxTextArea();
        UnicodeMainCiphertextArea.setName("UnicodeMainCiphertextArea");
        UnicodeMainCiphertextArea.setCodeFoldingEnabled(true);
        UnicodeMainCiphertextArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        UnicodeMainCiphertextArea.addKeyListener(new UnicodeMainKeyListener());

        RTextScrollPane UnicodeMainCiphertextScrollPane = new RTextScrollPane(UnicodeMainCiphertextArea);
        UnicodeMainCiphertextScrollPane.setName("UnicodeMainCiphertextScrollPane");
        UnicodeMainCiphertextScrollPane.setPreferredSize(new Dimension(0,0));
        UnicodeMainCiphertextScrollPane.setBorder(null);

        return UnicodeMainCiphertextScrollPane;

    }

    public static JComponent UnicodeMainOutputScrollPane(){

        //setCodeFoldingEnabled需要在setSyntaxEditingStyle前面
        RSyntaxTextArea UnicodeMainPlaintextArea = new RSyntaxTextArea();
        UnicodeMainPlaintextArea.setName("UnicodeMainPlaintextArea");
        UnicodeMainPlaintextArea.setCodeFoldingEnabled(true);
        UnicodeMainPlaintextArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        UnicodeMainPlaintextArea.addKeyListener(new UnicodeMainKeyListener());

        RTextScrollPane UnicodeMainPlaintextScrollPane = new RTextScrollPane(UnicodeMainPlaintextArea);
        UnicodeMainPlaintextScrollPane.setName("UnicodeMainPlaintextScrollPane");
        UnicodeMainPlaintextScrollPane.setPreferredSize(new Dimension(0,0));
        UnicodeMainPlaintextScrollPane.setBorder(null);

        return UnicodeMainPlaintextScrollPane;
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

    public static JComponent getNewMainPanel(){
        return UnicodeMainPanel();
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
                switch (eArea.getName()){
                    case "UnicodeMainCiphertextArea":
                        outputArea = (RSyntaxTextArea) Helper.getComponent(Main.JrcetPanel, "UnicodeMainPlaintextArea" );
                        outputArea.setText(Unicode.unicodeToString(eArea.getText()));
                        break;
                    case "UnicodeMainPlaintextArea":
                        outputArea = (RSyntaxTextArea) Helper.getComponent(Main.JrcetPanel, "UnicodeMainCiphertextArea" );
                        outputArea.setText(Unicode.stringToUnicode(eArea.getText()));
                        break;
                }
                assert outputArea != null;
                outputArea.updateUI();
            }
        }
    }


}
