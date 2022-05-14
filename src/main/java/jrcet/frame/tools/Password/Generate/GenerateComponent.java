package jrcet.frame.tools.Password.Generate;

import jrcet.diycomponents.DiyJAddLabel;
import jrcet.diycomponents.DiyJComponent;
import jrcet.diycomponents.DiyJLabel;
import jrcet.diycomponents.DiyJTextArea.ui.rsyntaxtextarea.RSyntaxTextArea;
import jrcet.diycomponents.DiyJTextArea.ui.rsyntaxtextarea.SyntaxConstants;
import jrcet.diycomponents.DiyJTextArea.ui.rtextarea.RTextScrollPane;
import jrcet.frame.setting.Setting;
import jrcet.frame.tools.Dominate.Domain.Domain;
import jrcet.frame.tools.Dominate.Domain.DomainComponent;
import jrcet.frame.tools.HText.Parsepy.ParsepyComponent;
import jrcet.lib.Helper;

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

        GenerateMainPanel.add(GenerateMainBorderPanel(),new GridBagConstraints(
                0,0,
                1,2,
                0.2,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        GenerateMainPanel.add(GenerateMainMenuPanel(),new GridBagConstraints(
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

        GenerateMainPanel.add(GenerateMainRightPanel(),new GridBagConstraints(
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


    public JComponent GenerateMainMenuPanel(){
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

    public JComponent GenerateMainRightPanel(){
        JPanel GenerateMainRightPanel = new JPanel(new GridBagLayout());
        GenerateMainRightPanel.setName("GenerateMainRightPanel");
        GenerateMainRightPanel.setPreferredSize(new Dimension(0,0));
        GenerateMainRightPanel.setBorder(null);

        GenerateMainRightPanel.add(GenerateMainBorderPanel(),new GridBagConstraints(
                0,0,
                1,1,
                1,0.05,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));
        GenerateMainRightPanel.add(GenerateMainRightTabPanel(),new GridBagConstraints(
                0,1,
                1,1,
                1,0.95,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return GenerateMainRightPanel;
    }

    public JComponent GenerateMainRightTabPanel(){
        JPanel GenerateMainRightTabPanel = new JPanel(new GridBagLayout());
        GenerateMainRightTabPanel.setName("GenerateMainRightTabPanel");
        GenerateMainRightTabPanel.setBorder(null);

        DiyJLabel GenerateMainRightTabAllLabel = new DiyJLabel("All");
        GenerateMainRightTabAllLabel.setName("GenerateMainRightTabAllLabel");

        DiyJLabel GenerateMainRightTabDigital4Label = new DiyJLabel("Digital4");
        GenerateMainRightTabDigital4Label.setName("GenerateMainRightTabDigital4Label");

        DiyJLabel GenerateMainRightTabDigital6Label = new DiyJLabel("Digital6");
        GenerateMainRightTabDigital6Label.setName("GenerateMainRightTabDigital4Label");

        DiyJLabel GenerateMainRightTabDigital8Label = new DiyJLabel("Digital8");
        GenerateMainRightTabDigital8Label.setName("GenerateMainRightTabDigital4Label");

        DiyJLabel GenerateMainRightTabSDigital4Label = new DiyJLabel("SDigital4");
        GenerateMainRightTabSDigital4Label.setName("GenerateMainRightTabDigital4Label");

        DiyJLabel GenerateMainRightTabSDigital6Label = new DiyJLabel("SDigital6");
        GenerateMainRightTabSDigital6Label.setName("GenerateMainRightTabDigital4Label");

        DiyJLabel GenerateMainRightTabSDigital8Label = new DiyJLabel("SDigital6");
        GenerateMainRightTabSDigital8Label.setName("GenerateMainRightTabDigital4Label");

        GenerateMainRightTabPanel.add(GenerateMainRightTabAllLabel, new GridBagConstraints(
                0,GenerateMainRightTabPanel.getComponentCount(),
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        GenerateMainRightTabPanel.add(GenerateMainRightTabDigital4Label, new GridBagConstraints(
                0,GenerateMainRightTabPanel.getComponentCount(),
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        GenerateMainRightTabPanel.add(GenerateMainRightTabDigital6Label, new GridBagConstraints(
                0,GenerateMainRightTabPanel.getComponentCount(),
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        GenerateMainRightTabPanel.add(GenerateMainRightTabDigital8Label, new GridBagConstraints(
                0,GenerateMainRightTabPanel.getComponentCount(),
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        GenerateMainRightTabPanel.add(GenerateMainRightTabSDigital4Label, new GridBagConstraints(
                0,GenerateMainRightTabPanel.getComponentCount(),
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        GenerateMainRightTabPanel.add(GenerateMainRightTabSDigital6Label, new GridBagConstraints(
                0,GenerateMainRightTabPanel.getComponentCount(),
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        GenerateMainRightTabPanel.add(GenerateMainRightTabSDigital8Label, new GridBagConstraints(
                0,GenerateMainRightTabPanel.getComponentCount(),
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        GenerateMainRightTabPanel.add(GenerateMainBorderPanel(), new GridBagConstraints(
                1,0,
                1,GenerateMainRightTabPanel.getComponentCount(),
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        GenerateMainRightTabPanel.add(GenerateMainBorderPanel(), new GridBagConstraints(
                0,8,
                2,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        for(Component i : GenerateMainRightTabPanel.getComponents()){
            if(i.getClass().toString().contains("DiyJLabel")){
                i.setPreferredSize(new Dimension(70,25));
                ((DiyJLabel)i).setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1,0,0,0,new Color(237,237,237)), BorderFactory.createEmptyBorder(0,2,0,0)));
            }
        }
        GenerateMainRightTabAllLabel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1,0,0,0,new Color(237,237,237)), BorderFactory.createMatteBorder(0,2,0,0, Setting.class2ClickedDiyJTabBorderColor)));

        return GenerateMainRightTabPanel;
    }

    public JComponent GenerateMainBorderPanel(){
        JPanel GenerateMainBorderPanel = new JPanel();
        GenerateMainBorderPanel.setName("GenerateMainBorderPanel");

        return GenerateMainBorderPanel;
    }

    public JComponent GenerateBlackPanel(){
        JPanel GenerateBlackPanel = new JPanel();
        GenerateBlackPanel.setName("GenerateBlackPanel");
        GenerateBlackPanel.setOpaque(true);
        GenerateBlackPanel.setBackground(Color.PINK);

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
                eTextArea.setText(Generate.allPassword);
            }
        }
    }
}
