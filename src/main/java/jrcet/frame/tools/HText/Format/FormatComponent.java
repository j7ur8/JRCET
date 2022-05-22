package jrcet.frame.tools.HText.Format;

import jrcet.diycomponents.*;
import jrcet.diycomponents.DiyJTextArea.ui.rsyntaxtextarea.RSyntaxTextArea;
import jrcet.diycomponents.DiyJTextArea.ui.rsyntaxtextarea.SyntaxConstants;
import jrcet.diycomponents.DiyJTextArea.ui.rtextarea.RTextScrollPane;
import jrcet.lib.Helper;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public class FormatComponent extends DiyJComponent {


    public static HashMap<String, GridBagConstraints> ComponentConstraintHashMap = new HashMap<>();
    public static HashMap<String, JComponent> MainPanelHashMap = new HashMap<>();

    public static JComponent FormatComponentPanel = null;

    @Override
    public JComponent main() {

        Format.initFormat();
        FormatComponentPanel = new JPanel(new GridBagLayout());
        FormatComponentPanel.setBackground(Color.WHITE);
        FormatComponentPanel.setName("FormatComponentPanel");

        FormatComponentPanel.add(FormatTagTabPanel(),new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        MainPanelHashMap.put("1", FormatMainPanel());
        Helper.setConstraints(ComponentConstraintHashMap, FormatComponentPanel, getFormatMainPanel("1"),new GridBagConstraints(
                0,1,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return FormatComponentPanel;
    }

    public JComponent FormatTagTabPanel(){

        JPanel FormatTagTabPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,0,0));
        FormatTagTabPanel.setName("FormatTagTabPanel");
        FormatTagTabPanel.setBackground(Color.WHITE);
        FormatTagTabPanel.setBorder(BorderFactory.createMatteBorder(0,0,0,0,new Color(203,208,209)));

        DiyJAddLabel FormatTagTabSticker1Label = new DiyJAddLabel("1",true);
        FormatTagTabSticker1Label.setName("FormatTagTabSticker1Label");
        FormatTagTabSticker1Label.setPanel(getFormatMainPanel("1"));
        FormatTagTabPanel.add(FormatTagTabSticker1Label);

        DiyJAddLabel FormatTabAddLabel = new DiyJAddLabel("···");
        FormatTabAddLabel.setName("FormatTabAddLabel");
        FormatTagTabPanel.add(FormatTabAddLabel);

        return FormatTagTabPanel;
    }

    public JComponent FormatMainPanel(){
        JPanel FormatMainPanel = new JPanel(new GridBagLayout());
        FormatMainPanel.setName("FormatMainPanel");
        FormatMainPanel.setBackground(Color.WHITE);
        FormatMainPanel.setPreferredSize(new Dimension(0,0));

        FormatMainPanel.add(Helper.blackPanel(),new GridBagConstraints(
                0,0,
                1,1,
                0.2,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        FormatMainPanel.add(FormatMainInputAreaScrollPane(),new GridBagConstraints(
                1,0,
                1,1,
                0.6,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        FormatMainPanel.add(FormatMainControlPanel(),new GridBagConstraints(
                2,0,
                1,1,
                0.2,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return FormatMainPanel;
    }

    public JComponent FormatMainInputAreaScrollPane(){
        RSyntaxTextArea FormatMainInputArea = new RSyntaxTextArea();
        FormatMainInputArea.setName("FormatMainInputArea");
        FormatMainInputArea.setCodeFoldingEnabled(true);
        FormatMainInputArea.setLineWrap(true);
        FormatMainInputArea.setText("#请输入文件路径或\\n分割的字符串...");
        FormatMainInputArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        FormatMainInputArea.addKeyListener(new FormatMainInputAreaKeyListener());

        RTextScrollPane FormatMainInputAreaScrollPane = new RTextScrollPane(FormatMainInputArea);
        FormatMainInputAreaScrollPane.setHorizontalScrollBarPolicy(RTextScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        FormatMainInputAreaScrollPane.setName("FormatMainInputAreaScrollPane");
        FormatMainInputAreaScrollPane.setPreferredSize(new Dimension(0,0));
        FormatMainInputAreaScrollPane.setBorder(null);

        return  FormatMainInputAreaScrollPane;
    }

    public JComponent FormatMainControlPanel(){
        JComponent FormatMainControlPanel = new JPanel(new GridBagLayout());
        FormatMainControlPanel.setName("FormatMainControlPanel");

        DiyJComboBox<String> FormatMainControlModeBox = new DiyJComboBox<>(new String[]{"Change Separator","Add AllFix","Add Suffix","Add Prefix","Remove AllFix","Remove Suffix","Remove Prefix"});
        FormatMainControlModeBox.setName("FormatMainControlModeBox");
        FormatMainControlModeBox.setPreferredSize(new Dimension(0,30));

        FormatMainControlPanel.add(FormatMainControlModeBox, new GridBagConstraints(
                0,FormatMainControlPanel.getComponentCount(),
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(20,0,10,0),
                0,0
        ));

        FormatMainControlPanel.add(FormatMainControlSeparatorPanel(), new GridBagConstraints(
                0,FormatMainControlPanel.getComponentCount(),
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        FormatMainControlPanel.add(FormatMainControlFixPanel(), new GridBagConstraints(
                0,FormatMainControlPanel.getComponentCount(),
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        FormatMainControlPanel.add(Helper.blackPanel(), new GridBagConstraints(
                0,FormatMainControlPanel.getComponentCount(),
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return FormatMainControlPanel;
    }

    public JComponent FormatMainControlSeparatorPanel(){
        JComponent FormatMainControlSeparatorPanel = new JPanel(new GridBagLayout());
        FormatMainControlSeparatorPanel.setName("FormatMainControlSeparatorPanel");
        FormatMainControlSeparatorPanel.setPreferredSize(new Dimension(0,70));
        FormatMainControlSeparatorPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1,0,0,0,Color.GRAY),"Change Separator"));

        JTextField FormatMainControlSeparatorSourceField = new JTextField("");
        FormatMainControlSeparatorSourceField.getDocument().putProperty("owner", FormatMainControlSeparatorSourceField);
        FormatMainControlSeparatorSourceField.getDocument().addDocumentListener(new FormatDocumentListener());
        FormatMainControlSeparatorSourceField.setName("FormatMainControlSeparatorSourceField");
        FormatMainControlSeparatorSourceField.setPreferredSize(new Dimension(0,30));

        DiyJLabel FormatMainControlSeparatorArrowLabel = new DiyJLabel("->");
        FormatMainControlSeparatorArrowLabel.setName("FormatMainControlArrowLabel");
        FormatMainControlSeparatorArrowLabel.setBackground(null);
        FormatMainControlSeparatorArrowLabel.setOpaque(true);
        FormatMainControlSeparatorArrowLabel.setPreferredSize(new Dimension(0,20));

        JTextField FormatMainControlSeparatorDestinationField = new JTextField("");
        FormatMainControlSeparatorDestinationField.getDocument().putProperty("owner", FormatMainControlSeparatorDestinationField);
        FormatMainControlSeparatorDestinationField.getDocument().addDocumentListener(new FormatDocumentListener());
        FormatMainControlSeparatorDestinationField.setName("FormatMainControlSeparatorDestinationField");
        FormatMainControlSeparatorDestinationField.setPreferredSize(new Dimension(0, 30));

        FormatMainControlSeparatorPanel.add(FormatMainControlSeparatorSourceField, new GridBagConstraints(
                0,0,
                1,1,
                0.45,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        FormatMainControlSeparatorPanel.add(FormatMainControlSeparatorArrowLabel, new GridBagConstraints(
                1,0,
                1,1,
                0.1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,2,0),
                0,0
        ));

        FormatMainControlSeparatorPanel.add(FormatMainControlSeparatorDestinationField, new GridBagConstraints(
                2,0,
                1,1,
                0.45,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return FormatMainControlSeparatorPanel;

    }

    public JComponent FormatMainControlFixPanel() {
        JComponent FormatMainControlFixPanel = new JPanel(new GridBagLayout());
        FormatMainControlFixPanel.setName("FormatMainControlFixPanel");
        FormatMainControlFixPanel.setPreferredSize(new Dimension(0, 70));
        FormatMainControlFixPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY), "Prefix Or Suffix"));

        JTextField FormatMainControlFixValueField = new JTextField();
        FormatMainControlFixValueField.getDocument().putProperty("owner", FormatMainControlFixValueField);
        FormatMainControlFixValueField.getDocument().addDocumentListener(new FormatDocumentListener());
        FormatMainControlFixValueField.setName("FormatMainControlFixValueField");
        FormatMainControlFixValueField.setPreferredSize(new Dimension(0, 30));

        FormatMainControlFixPanel.add(FormatMainControlFixValueField, new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return FormatMainControlFixPanel;
    }

        public JComponent getFormatMainPanel(String TagName){
        return MainPanelHashMap.containsKey(TagName)?(MainPanelHashMap.get(TagName)!=null?MainPanelHashMap.get(TagName): Helper.blackPanel()):Helper.blackPanel();
    }

    static class FormatMainInputAreaKeyListener implements KeyListener {

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
                if ("FormatMainInputArea".equals(eArea.getName())) {
                    eArea.setText(Format.formatting(eArea.getText()));
                }
            }
        }
    }

    static class FormatDocumentListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            updateFormatProperty(e);

        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            updateFormatProperty(e);
        }

        private void updateFormatProperty(DocumentEvent e) {
            Document eDocument = e.getDocument();
            String eName = ((JTextField) eDocument.getProperty("owner")).getName();
            try{
                switch (eName){
                    case "FormatMainControlSeparatorSourceField":
                        Format.FormatSourceSeparator = eDocument.getText(0,eDocument.getLength());
                        break;
                    case "FormatMainControlSeparatorDestinationField":
                        Format.FormatDestinationSeparator = eDocument.getText(0,eDocument.getLength());
                        break;
                    case "FormatMainControlFixValueField":
                        Format.FormatFixValue = eDocument.getText(0,eDocument.getLength());
                        break;
                }
            }catch (Exception ignore){}
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
        }
    }

}
