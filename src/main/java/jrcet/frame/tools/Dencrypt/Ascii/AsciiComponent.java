package jrcet.frame.tools.Dencrypt.Ascii;

import jrcet.diycomponents.DiyJAddLabel;
import jrcet.diycomponents.DiyJComponent;
import jrcet.diycomponents.DiyJTextArea.ui.rsyntaxtextarea.RSyntaxTextArea;
import jrcet.diycomponents.DiyJTextArea.ui.rsyntaxtextarea.SyntaxConstants;
import jrcet.diycomponents.DiyJTextArea.ui.rtextarea.RTextScrollPane;
import jrcet.frame.setting.Setting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;


public class AsciiComponent extends DiyJComponent {

    public static HashMap<String, JComponent> MainPanelHashMap = new HashMap<>();

    public static JComponent AsciiComponentPanel = null;

    public AsciiComponent(){
        MainPanelHashMap.put("1", AsciiMainPanel());
    }

    public JComponent main(){
        AsciiComponentPanel = new JPanel(new GridBagLayout());
        AsciiComponentPanel.setName("AsciiComponentPanel");
        AsciiComponentPanel.setBackground(Color.WHITE);

        AsciiComponentPanel.add(AsciiTagBlackPanel(), new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        AsciiComponentPanel.add(AsciiTagTabPanel(),new GridBagConstraints(
                1,0,
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        AsciiComponentPanel.add(getAsciiMainPanel("1"),new GridBagConstraints(
                0,1,
                2,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(5,5,5,5),
                0,0
        ));

        return AsciiComponentPanel;
    }

    public JComponent AsciiTagBlackPanel(){
        JComponent AsciiTagBlackPanel = new JPanel();
        AsciiTagBlackPanel.setName("AsciiTagBlackPanel");
        AsciiTagBlackPanel.setOpaque(false);
        AsciiTagBlackPanel.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Setting.class4DefaultDiyJTabBorderColor));
        return AsciiTagBlackPanel;
    }

    public JComponent AsciiTagTabPanel(){
        JPanel AsciiTagTabPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,0,0));
        AsciiTagTabPanel.setName("AsciiTagTabPanel");
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

        AsciiMainPanel.add(AsciiMainInputScrollPane(), new GridBagConstraints(
                1,0,
                1,1,
                0.6,0.5,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        AsciiMainPanel.add(AsciiMainOutputScrollPane(), new GridBagConstraints(
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

    public JComponent AsciiMainInputScrollPane(){

        RSyntaxTextArea AsciiMainCiphertextArea = new RSyntaxTextArea();
        AsciiMainCiphertextArea.setName("AsciiMainCiphertextArea");
        AsciiMainCiphertextArea.setCodeFoldingEnabled(true);
        AsciiMainCiphertextArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        AsciiMainCiphertextArea.addKeyListener(new AsciiMainKeyListener());

        RTextScrollPane AsciiMainCiphertextScrollPane = new RTextScrollPane(AsciiMainCiphertextArea);
        AsciiMainCiphertextScrollPane.setName("AsciiMainCiphertextScrollPane");
        AsciiMainCiphertextScrollPane.setPreferredSize(new Dimension(0,0));
        AsciiMainCiphertextScrollPane.setBorder(null);

        return AsciiMainCiphertextScrollPane;

    }

    public JComponent AsciiMainOutputScrollPane(){

        //setCodeFoldingEnabled需要在setSyntaxEditingStyle前面
        RSyntaxTextArea AsciiMainPlaintextArea = new RSyntaxTextArea();
        AsciiMainPlaintextArea.setName("AsciiMainPlaintextArea");
        AsciiMainPlaintextArea.setLineWrap(true);
        AsciiMainPlaintextArea.setCodeFoldingEnabled(true);
        AsciiMainPlaintextArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        AsciiMainPlaintextArea.addKeyListener(new AsciiMainKeyListener());

        RTextScrollPane AsciiMainPlaintextScrollPane = new RTextScrollPane(AsciiMainPlaintextArea);
        AsciiMainPlaintextScrollPane.setName("AsciiMainPlaintextScrollPane");
        AsciiMainPlaintextScrollPane.setHorizontalScrollBarPolicy(RTextScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        AsciiMainPlaintextScrollPane.setPreferredSize(new Dimension(0,0));
        AsciiMainPlaintextScrollPane.setBorder(null);

        return AsciiMainPlaintextScrollPane;
    }

    public JComponent getAsciiMainPanel(String TagName){
        return MainPanelHashMap.containsKey(TagName)?(MainPanelHashMap.get(TagName)!=null?MainPanelHashMap.get(TagName):AsciiBlackPanel()):AsciiBlackPanel();
    }

    public JComponent AsciiBlackPanel(){
        JPanel AsciiBlackPanel = new JPanel();
        AsciiBlackPanel.setName("AsciiBlackPanel");
        AsciiBlackPanel.setOpaque(true);
        AsciiBlackPanel.setBackground(Color.PINK);

        return AsciiBlackPanel;
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

        }
    }


}
