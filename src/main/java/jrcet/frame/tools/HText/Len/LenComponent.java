package jrcet.frame.tools.HText.Len;

import jrcet.diycomponents.*;
import jrcet.diycomponents.DiyJTextArea.ui.rsyntaxtextarea.RSyntaxTextArea;
import jrcet.diycomponents.DiyJTextArea.ui.rsyntaxtextarea.SyntaxConstants;
import jrcet.diycomponents.DiyJTextArea.ui.rtextarea.RTextScrollPane;
import jrcet.help.Helper;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class LenComponent extends DiyJComponent {


    public static HashMap<String, GridBagConstraints> ComponentConstraintHashMap = new HashMap<>();
    public static HashMap<String, JComponent> MainPanelHashMap = new HashMap<>();

    public static JComponent LenComponentPanel = null;

    @Override
    public JComponent main() {

        LenComponentPanel = new JPanel(new GridBagLayout());
        LenComponentPanel.setBackground(Color.WHITE);
        LenComponentPanel.setName("LenComponentPanel");

        LenComponentPanel.add(LenTagTabPanel(),new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        MainPanelHashMap.put("1", LenMainPanel());
        Helper.setConstraints(ComponentConstraintHashMap, LenComponentPanel, getLenMainPanel("1"),new GridBagConstraints(
                0,1,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return LenComponentPanel;
    }

    public JComponent LenTagTabPanel(){

        JPanel LenTagTabPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,0,0));
        LenTagTabPanel.setName("LenTagTabPanel");
        LenTagTabPanel.setBackground(Color.WHITE);
        LenTagTabPanel.setBorder(BorderFactory.createMatteBorder(0,0,0,0,new Color(203,208,209)));

        DiyJAddLabel LenTagTabSticker1Label = new DiyJAddLabel("1",true);
        LenTagTabSticker1Label.setName("LenTagTabSticker1Label");
        LenTagTabSticker1Label.setPanel(getLenMainPanel("1"));
        LenTagTabPanel.add(LenTagTabSticker1Label);

        DiyJAddLabel LenTabAddLabel = new DiyJAddLabel("??????");
        LenTabAddLabel.setName("LenTabAddLabel");
        LenTagTabPanel.add(LenTabAddLabel);

        return LenTagTabPanel;
    }

    public JComponent LenMainPanel(){
        JPanel LenMainPanel = new JPanel(new GridBagLayout());
        LenMainPanel.setName("LenMainPanel");
        LenMainPanel.setBackground(Color.WHITE);
        LenMainPanel.setPreferredSize(new Dimension(0,0));

        LenMainPanel.add(Helper.blackPanel(),new GridBagConstraints(
                0,0,
                1,1,
                0.2,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        LenMainPanel.add(LenMainInputAreaScrollPane(),new GridBagConstraints(
                1,0,
                1,1,
                0.6,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        LenMainPanel.add(LenMainControlPanel(),new GridBagConstraints(
                2,0,
                1,1,
                0.2,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return LenMainPanel;
    }

    public JComponent LenMainInputAreaScrollPane(){
        RSyntaxTextArea LenMainInputArea = new RSyntaxTextArea();
        LenMainInputArea.setName("LenMainInputArea");
        LenMainInputArea.setCodeFoldingEnabled(true);
        LenMainInputArea.setLineWrap(true);
        LenMainInputArea.setText("#????????????????????????\\n??????????????????...");
        LenMainInputArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        LenMainInputArea.addKeyListener(new LenMainInputAreaKeyListener());

        RTextScrollPane LenMainInputAreaScrollPane = new RTextScrollPane(LenMainInputArea);
        LenMainInputAreaScrollPane.setHorizontalScrollBarPolicy(RTextScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        LenMainInputAreaScrollPane.setName("LenMainInputAreaScrollPane");
        LenMainInputAreaScrollPane.setPreferredSize(new Dimension(0,0));
        LenMainInputAreaScrollPane.setBorder(null);

        return  LenMainInputAreaScrollPane;
    }

    public JComponent LenMainControlPanel(){
        JComponent LenMainControlPanel = new JPanel(new GridBagLayout());
        LenMainControlPanel.setName("LenMainControlPanel");

        JTextField LenMainControlLenField = new JTextField("??????????????????????????????");
        LenMainControlLenField.setName("LenMainControlLenField");
        LenMainControlLenField.setPreferredSize(new Dimension(0,30));
        LenMainControlLenField.addFocusListener(new LenMainControlLenFieldFocusListener());

        JList<String> LenMainControlModeList = new JList<>(new String[]{"??????","??????","??????","??????","??????"});
        LenMainControlModeList.setName("LenMainControlModeList");
        LenMainControlModeList.setName("LenMainControlModeList");
        LenMainControlModeList.setPreferredSize(new Dimension(0,110));
        LenMainControlModeList.setFont(new Font("????????????", Font.PLAIN,16));
        LenMainControlModeList.setCellRenderer(new LenMainControlModeListCellRenderer());
        LenMainControlModeList.addListSelectionListener(new LenMainControlModeListListener());

        LenMainControlPanel.add(LenMainControlLenField, new GridBagConstraints(
                0,LenMainControlPanel.getComponentCount(),
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(20,0,10,0),
                0,0
        ));

        LenMainControlPanel.add(LenMainControlModeList, new GridBagConstraints(
                0,LenMainControlPanel.getComponentCount(),
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(20,0,10,0),
                0,0
        ));

        LenMainControlPanel.add(Helper.blackPanel(), new GridBagConstraints(
                0,LenMainControlPanel.getComponentCount(),
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return LenMainControlPanel;
    }



    public JComponent getLenMainPanel(String TagName){
        return MainPanelHashMap.containsKey(TagName)?(MainPanelHashMap.get(TagName)!=null?MainPanelHashMap.get(TagName): Helper.blackPanel()):Helper.blackPanel();
    }

    static class LenMainInputAreaKeyListener implements KeyListener {

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
                JTextField eTextField = (JTextField) Helper.getComponent(rootPanel, "LenMainControlLenField"); assert eTextField!=null;
                eTextArea.setText(Len.handle(eTextArea.getText(), eTextField.getText()));
            }
        }
    }

    static class LenMainControlLenFieldFocusListener implements FocusListener {

        public String hintText = "??????????????????????????????";

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
                eTextField.setText(hintText);
            }
        }
    }

    static class LenMainControlModeListListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            if(!e.getValueIsAdjusting()) return;
            JList<String> eJList = (JList<String>) e.getSource();
            JTextField tTextField = (JTextField) Helper.getComponent((JComponent) eJList.getParent(),"LenMainControlLenField");assert tTextField != null;
            tTextField.setText(eJList.getSelectedValue());
        }
    }

    static class LenMainControlModeListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel tLabel = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (isSelected) {
                tLabel.setBackground(new Color(254, 152, 165));
                tLabel.setBorder(null);
            }
            return tLabel;
        }
    }
}
