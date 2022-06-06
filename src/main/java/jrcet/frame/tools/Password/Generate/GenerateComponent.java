package jrcet.frame.tools.Password.Generate;

import jrcet.diycomponents.DiyJAddLabel;
import jrcet.diycomponents.DiyJCheckBox;
import jrcet.diycomponents.DiyJComponent;
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

public class GenerateComponent extends DiyJComponent {

    public static HashMap<String, JComponent> MainPanelHashMap = new HashMap<>();
    public static HashMap<String, GridBagConstraints> ComponentConstraintHashMap = new HashMap<>();
    
    public static JComponent GenerateComponentPanel = null;

    @Override
    public JComponent main() {
        new Generate();//不然JCheckBox会报错
        GenerateComponentPanel = new JPanel(new GridBagLayout());
        GenerateComponentPanel.setName("GenerateComponentPanel");
        GenerateComponentPanel.setBackground(Color.WHITE);

        GenerateComponentPanel.add(GenerateTagTabPanel(), new GridBagConstraints(
                0, 0,
                1, 1,
                0, 0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0),
                0, 0
        ));

        MainPanelHashMap.put("1", GenerateMainPanel());
        Helper.setConstraints(ComponentConstraintHashMap, GenerateComponentPanel, getGenerateMainPanel("1"), new GridBagConstraints(
                0, 1,
                1, 1,
                1, 0.7,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0),
                0, 0
        ));

        return GenerateComponentPanel;
    }

    public JComponent GenerateTagTabPanel(){

        JPanel GenerateTagTabPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,0,0));
        GenerateTagTabPanel.setName("GenerateTagTabPanel");
        GenerateTagTabPanel.setBackground(Color.WHITE);

        DiyJAddLabel GenerateTagTabSticker1Label = new DiyJAddLabel("1",true);
        GenerateTagTabSticker1Label.setName("GenerateTagTabSticker1Label");
        GenerateTagTabSticker1Label.setPanel(getGenerateMainPanel("1"));
        GenerateTagTabPanel.add(GenerateTagTabSticker1Label);
        DiyJAddLabel GenerateTabAddLabel = new DiyJAddLabel("···");
        GenerateTabAddLabel.setName("GenerateTabAddLabel");
        GenerateTagTabPanel.add(GenerateTabAddLabel);

        return GenerateTagTabPanel;
    }

    public JComponent GenerateMainPanel(){
        JComponent GenerateMainPanel = new JPanel(new GridBagLayout());
        GenerateMainPanel.setName("GenerateMainPanel");
        GenerateMainPanel.setBackground(Color.WHITE);

        GenerateMainPanel.add(GenerateBlackPanel(),new GridBagConstraints(
                0,0,
                1,2,
                0.2,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        GenerateMainPanel.add(GenerateMainInputPanel(),new GridBagConstraints(
                1, 0,
                1, 1,
                0.6, 0.05,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0),
                0, 0
        ));

        GenerateMainPanel.add(GenerateMainOutputPanel(),new GridBagConstraints(
                1, 1,
                1, 1,
                0.6, 0.95,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0),
                0, 0
        ));

        GenerateMainPanel.add(GenerateMainMenuPanel(),new GridBagConstraints(
                2,0,
                1,2,
                0.2,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return GenerateMainPanel;

    }

    public JComponent GenerateMainOutputPanel(){
        JComponent GenerateMainOutputPanel = new JPanel(new GridBagLayout());
        GenerateMainOutputPanel.setName("GenerateMainOutputPanel");
        GenerateMainOutputPanel.setBackground(Color.WHITE);

        RSyntaxTextArea GenerateMainOutputArea = new RSyntaxTextArea();
        GenerateMainOutputArea.setName("GenerateMainOutputArea");
        GenerateMainOutputArea.setCodeFoldingEnabled(true);
        GenerateMainOutputArea.setLineWrap(true);
        GenerateMainOutputArea.setText("");
        GenerateMainOutputArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);

        RTextScrollPane GenerateMainOutputAreaScrollPane = new RTextScrollPane(GenerateMainOutputArea);
        GenerateMainOutputAreaScrollPane.setHorizontalScrollBarPolicy(RTextScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        GenerateMainOutputAreaScrollPane.setName("GenerateMainOutputAreaScrollPane");
        GenerateMainOutputAreaScrollPane.setPreferredSize(new Dimension(0,0));
        GenerateMainOutputAreaScrollPane.setBorder(null);

        GenerateMainOutputPanel.add(GenerateMainOutputAreaScrollPane, new GridBagConstraints(
                0,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return GenerateMainOutputPanel;
    }


    public JComponent GenerateMainInputPanel(){
        JComponent GenerateMainMenuPanel = new JPanel(new GridBagLayout());
        GenerateMainMenuPanel.setName("GenerateMainMenuPanel");
        GenerateMainMenuPanel.setBackground(Color.WHITE);

        JTextField GenerateMainMenuNameField = new JTextField();
        GenerateMainMenuNameField.setName("GenerateMainMenuNameField");
        GenerateMainMenuNameField.setPreferredSize(new Dimension(0,0));
        GenerateMainMenuNameField.setHorizontalAlignment(JTextField.CENTER);
        GenerateMainMenuNameField.setFont(new Font("微软雅黑", Font.PLAIN,20));
        GenerateMainMenuNameField.addKeyListener(new GenerateMainMenuNameKeyListener());

        GenerateMainMenuPanel.add(GenerateMainMenuNameField, new GridBagConstraints(
                0,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return GenerateMainMenuPanel;
    }



    public JComponent GenerateMainMenuPanel(){
        JPanel GenerateMainMenuPanel = new JPanel(new GridBagLayout());
        GenerateMainMenuPanel.setName("GenerateMainBorderPanel");

        JLabel GenerateMainMenuTitleLabel = new JLabel("模式选择");
        GenerateMainMenuTitleLabel.setHorizontalAlignment(JLabel.CENTER);
        GenerateMainMenuTitleLabel.setFont(new Font("微软雅黑",Font.PLAIN, 16));
        GenerateMainMenuTitleLabel.setBorder(null);
        GenerateMainMenuTitleLabel.setPreferredSize(new Dimension(0,0));

        GenerateMainMenuPanel.add(GenerateMainMenuTitleLabel, new GridBagConstraints(
                0,0,
                1,1,
                1,0.05,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        GenerateMainMenuPanel.add(GenerateMainMenuModePanel(), new GridBagConstraints(
                0,1,
                1,1,
                1,0.95,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));


        return GenerateMainMenuPanel;
    }

    public JComponent GenerateMainMenuModePanel(){
        JComponent GenerateMainMenuModePanel = new JPanel(new GridBagLayout());
        GenerateMainMenuModePanel.setName("GenerateMainMenuModePanel");
        GenerateMainMenuModePanel.setBackground(Color.PINK);

        GenerateMainMenuModePanel.add(GenerateMainMenuModeSpecialcharPanel(),new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        GenerateMainMenuModePanel.add(GenerateMainMenuModeSuffixPanel(),new GridBagConstraints(
                0,1,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));


        GenerateMainMenuModePanel.add(Helper.blackPanel(), new GridBagConstraints(
                0,2,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return GenerateMainMenuModePanel;
    }

    public JComponent GenerateMainMenuModeSpecialcharPanel(){
        JComponent GenerateMainMenuModeSpecialcharPanel = new JPanel(new GridBagLayout());
        GenerateMainMenuModeSpecialcharPanel.setName("GenerateMainMenuModeSpecialcharPanel");
        GenerateMainMenuModeSpecialcharPanel.setPreferredSize(new Dimension(0,200));
        GenerateMainMenuModeSpecialcharPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.gray), "SpecialChar"));

        DiyJCheckBox GenerateMainMenuModeSpecialcharBox = new DiyJCheckBox("SpecialChar");
        GenerateMainMenuModeSpecialcharBox.setName("GenerateMainMenuModeSpecialcharBox");
        GenerateMainMenuModeSpecialcharBox.setPreferredSize(new Dimension(0,30));

        RSyntaxTextArea GenerateMainMenuModeSpecialcharArea = new RSyntaxTextArea("");
        GenerateMainMenuModeSpecialcharArea.setCodeFoldingEnabled(true);
        GenerateMainMenuModeSpecialcharArea.setLineWrap(true);
        GenerateMainMenuModeSpecialcharArea.setText("");
        GenerateMainMenuModeSpecialcharArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE);
        GenerateMainMenuModeSpecialcharArea.setName("GenerateMainMenuModeSpecialcharArea");

        RTextScrollPane GenerateMainMenuModeSpecialcharPane = new RTextScrollPane(GenerateMainMenuModeSpecialcharArea);
        GenerateMainMenuModeSpecialcharPane.setHorizontalScrollBarPolicy(RTextScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        GenerateMainMenuModeSpecialcharPane.setName("GenerateMainMenuModeSpecialcharPane");
        GenerateMainMenuModeSpecialcharPane.setPreferredSize(new Dimension(0,0));
        GenerateMainMenuModeSpecialcharPane.setBorder(null);

        GenerateMainMenuModeSpecialcharPanel.add(GenerateMainMenuModeSpecialcharBox, new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        GenerateMainMenuModeSpecialcharPanel.add(GenerateMainMenuModeSpecialcharPane, new GridBagConstraints(
                0,1,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return GenerateMainMenuModeSpecialcharPanel;
    }

    public JComponent GenerateMainMenuModeSuffixPanel(){
        JComponent GenerateMainMenuModeSuffixPanel = new JPanel(new GridBagLayout());
        GenerateMainMenuModeSuffixPanel.setName("GenerateMainMenuModeSuffixPanel");
        GenerateMainMenuModeSuffixPanel.setPreferredSize(new Dimension(0,100));
        GenerateMainMenuModeSuffixPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.gray), "Suffix"));

        DiyJCheckBox GenerateMainMenuModeDigital4Box = new DiyJCheckBox("digital4");
        GenerateMainMenuModeDigital4Box.setName("GenerateMainMenuModeDigital4Box");

        DiyJCheckBox GenerateMainMenuModeDigital6Box = new DiyJCheckBox("digital6");
        GenerateMainMenuModeDigital6Box.setName("GenerateMainMenuModeDigital6Box");

        DiyJCheckBox GenerateMainMenuModeDigital8Box = new DiyJCheckBox("digital8");
        GenerateMainMenuModeDigital8Box.setName("GenerateMainMenuModeDigital8Box");

        JTextField GenerateMainMenuModeDateField = new JTextField("1950-2022");
        GenerateMainMenuModeDateField.setHorizontalAlignment(JTextField.CENTER);
        GenerateMainMenuModeDateField.setName("GenerateMainMenuModeDateField");

        GenerateMainMenuModeSuffixPanel.add(GenerateMainMenuModeDigital4Box,new GridBagConstraints(
                0,0,
                1,1,
                0.5,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        GenerateMainMenuModeSuffixPanel.add(GenerateMainMenuModeDigital6Box,new GridBagConstraints(
                1,0,
                1,1,
                0.5,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        GenerateMainMenuModeSuffixPanel.add(GenerateMainMenuModeDigital8Box,new GridBagConstraints(
                2,0,
                1,1,
                0.5,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        GenerateMainMenuModeSuffixPanel.add(GenerateMainMenuModeDateField,new GridBagConstraints(
                0,1,
                3,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        GenerateMainMenuModeDigital4Box.setSelected(true);
        return GenerateMainMenuModeSuffixPanel;
    }



    public JComponent GenerateBlackPanel(){
        JPanel GenerateBlackPanel = new JPanel();
        GenerateBlackPanel.setName("GenerateBlackPanel");

        return GenerateBlackPanel;
    }


    public JComponent getGenerateMainPanel(String TagName){
        return MainPanelHashMap.containsKey(TagName)?(MainPanelHashMap.get(TagName)!=null?MainPanelHashMap.get(TagName):GenerateBlackPanel()):GenerateBlackPanel();
    }


    static class GenerateMainMenuNameKeyListener  implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {
            JTextField eField = (JTextField) e.getSource();
            JComponent rootPanel = (JComponent) eField.getParent().getParent().getParent();
            RSyntaxTextArea outputArea = null;
            if( (e.getModifiers()== InputEvent.CTRL_MASK || e.getModifiers() == InputEvent.META_MASK) && e.getKeyCode()==71) {
                JTextArea eTextArea = (JTextArea) Helper.getComponent(rootPanel,"GenerateMainOutputArea");
                Generate.genPassword(eField.getText());
                assert eTextArea != null;
                eTextArea.setText(Generate.result);
            }
        }
    }
}
