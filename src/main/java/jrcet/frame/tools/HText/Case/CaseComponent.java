package jrcet.frame.tools.HText.Case;

import jrcet.diycomponents.*;
import jrcet.diycomponents.DiyJTextArea.ui.rsyntaxtextarea.RSyntaxTextArea;
import jrcet.diycomponents.DiyJTextArea.ui.rsyntaxtextarea.SyntaxConstants;
import jrcet.diycomponents.DiyJTextArea.ui.rtextarea.RTextScrollPane;
import jrcet.help.Helper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Locale;

public class CaseComponent extends DiyJComponent {


    public static HashMap<String, GridBagConstraints> ComponentConstraintHashMap = new HashMap<>();
    public static HashMap<String, JComponent> MainPanelHashMap = new HashMap<>();

    public static JComponent CaseComponentPanel = null;

    @Override
    public JComponent main() {

        Case.initCase();
        CaseComponentPanel = new JPanel(new GridBagLayout());
        CaseComponentPanel.setBackground(Color.WHITE);
        CaseComponentPanel.setName("CaseComponentPanel");

        CaseComponentPanel.add(CaseTagTabPanel(),new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        MainPanelHashMap.put("1", CaseMainPanel());
        Helper.setConstraints(ComponentConstraintHashMap, CaseComponentPanel, getCaseMainPanel("1"),new GridBagConstraints(
                0,1,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return CaseComponentPanel;
    }

    public JComponent CaseTagTabPanel(){

        JPanel CaseTagTabPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,0,0));
        CaseTagTabPanel.setName("CaseTagTabPanel");
        CaseTagTabPanel.setBackground(Color.WHITE);
        CaseTagTabPanel.setBorder(BorderFactory.createMatteBorder(0,0,0,0,new Color(203,208,209)));

        DiyJAddLabel CaseTagTabSticker1Label = new DiyJAddLabel("1",true);
        CaseTagTabSticker1Label.setName("CaseTagTabSticker1Label");
        CaseTagTabSticker1Label.setPanel(getCaseMainPanel("1"));
        CaseTagTabPanel.add(CaseTagTabSticker1Label);

        DiyJAddLabel CaseTabAddLabel = new DiyJAddLabel("···");
        CaseTabAddLabel.setName("CaseTabAddLabel");
        CaseTagTabPanel.add(CaseTabAddLabel);

        return CaseTagTabPanel;
    }

    public JComponent CaseMainPanel(){
        JPanel CaseMainPanel = new JPanel(new GridBagLayout());
        CaseMainPanel.setName("CaseMainPanel");
        CaseMainPanel.setBackground(Color.WHITE);
        CaseMainPanel.setPreferredSize(new Dimension(0,0));

        CaseMainPanel.add(Helper.blackPanel(),new GridBagConstraints(
                0,0,
                1,2,
                0.2,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        CaseMainPanel.add(CaseMainPlainAreaScrollPane(),new GridBagConstraints(
                1,0,
                1,1,
                0.6,0.5,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        CaseMainPanel.add(CaseMainCipherAreaScrollPane(),new GridBagConstraints(
                1,1,
                1,1,
                0.6,0.5,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        CaseMainPanel.add(Helper.blackPanel(),new GridBagConstraints(
                2,0,
                1,2,
                0.2,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return CaseMainPanel;
    }

    public JComponent CaseMainPlainAreaScrollPane(){
        RSyntaxTextArea CaseMainPlainArea = new RSyntaxTextArea();
        CaseMainPlainArea.setName("CaseMainPlainArea");
        CaseMainPlainArea.setCodeFoldingEnabled(true);
        CaseMainPlainArea.setLineWrap(true);
        CaseMainPlainArea.setText("#请输入文件路径或\\n分割的字符串...");
        CaseMainPlainArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        CaseMainPlainArea.addKeyListener(new CaseMainPlainAreaKeyListener());

        RTextScrollPane CaseMainPlainAreaScrollPane = new RTextScrollPane(CaseMainPlainArea);
        CaseMainPlainAreaScrollPane.setHorizontalScrollBarPolicy(RTextScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        CaseMainPlainAreaScrollPane.setName("CaseMainPlainAreaScrollPane");
        CaseMainPlainAreaScrollPane.setPreferredSize(new Dimension(0,0));
        CaseMainPlainAreaScrollPane.setBorder(null);

        return  CaseMainPlainAreaScrollPane;
    }

    public JComponent CaseMainCipherAreaScrollPane(){
        RSyntaxTextArea CaseMainCipherArea = new RSyntaxTextArea();
        CaseMainCipherArea.setName("CaseMainCipherArea");
        CaseMainCipherArea.setCodeFoldingEnabled(true);
        CaseMainCipherArea.setLineWrap(true);
        CaseMainCipherArea.setText("#请输入文件路径或\\n分割的字符串...");
        CaseMainCipherArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        CaseMainCipherArea.addKeyListener(new CaseMainPlainAreaKeyListener());

        RTextScrollPane CaseMainCipherAreaScrollPane = new RTextScrollPane(CaseMainCipherArea);
        CaseMainCipherAreaScrollPane.setHorizontalScrollBarPolicy(RTextScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        CaseMainCipherAreaScrollPane.setName("CaseMainPlainAreaScrollPane");
        CaseMainCipherAreaScrollPane.setPreferredSize(new Dimension(0,0));
        CaseMainCipherAreaScrollPane.setBorder(null);

        return  CaseMainCipherAreaScrollPane;
    }

    public JComponent getCaseMainPanel(String TagName){
        return MainPanelHashMap.containsKey(TagName)?(MainPanelHashMap.get(TagName)!=null?MainPanelHashMap.get(TagName): Helper.blackPanel()):Helper.blackPanel();
    }

    static class CaseMainPlainAreaKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {

            if( (e.getModifiers()== InputEvent.CTRL_MASK || e.getModifiers() == InputEvent.META_MASK) && e.getKeyCode()==71){
                JTextArea eArea = (JTextArea) e.getSource();
                String eAreaName = eArea.getName();
                JComponent rootPanel = (JComponent) eArea.getParent().getParent().getParent();
                JTextArea tArea = null;
                switch (eAreaName) {
                    case "CaseMainPlainArea":
                        tArea = (JTextArea) Helper.getComponent(rootPanel, "CaseMainCipherArea"); assert tArea!=null;
                        tArea.setText(eArea.getText().toUpperCase(Locale.ROOT));
                        break;
                    case "CaseMainCipherArea":
                        tArea = (JTextArea) Helper.getComponent(rootPanel, "CaseMainPlainArea"); assert tArea!=null;
                        tArea.setText(eArea.getText().toLowerCase(Locale.ROOT));
                        break;
                }
            }
        }
    }


}
