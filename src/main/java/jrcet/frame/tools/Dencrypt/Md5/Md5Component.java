package jrcet.frame.tools.Dencrypt.Md5;

import jrcet.help.Helper;
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

public class Md5Component extends DiyJComponent {

    public static HashMap<String, GridBagConstraints> ComponentConstraintHashMap = new HashMap<>();
    public static HashMap<String, JComponent> MainPanelHashMap = new HashMap<>();

    public static JComponent Md5ComponentPanel = null;

    public JComponent main(){

        Md5ComponentPanel = new JPanel(new GridBagLayout());
        Md5ComponentPanel.setName("Md5ComponentPanel");
        Md5ComponentPanel.setBackground(Color.WHITE);


        Md5ComponentPanel.add(Md5TagTabPanel(),new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        MainPanelHashMap.put("1", Md5MainPanel());
        Helper.setConstraints(ComponentConstraintHashMap,Md5ComponentPanel,getMd5MainPanel("1"),new GridBagConstraints(
                0,1,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(5,5,5,5),
                0,0
        ));

        return Md5ComponentPanel;
    }


    public JComponent Md5TagTabPanel(){

        JPanel Md5TagTabPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,0,0));
        Md5TagTabPanel.setName("Md5TagTabPanel");
        Md5TagTabPanel.setBackground(Color.WHITE);

        DiyJAddLabel Md5TagTabSticker1Label = new DiyJAddLabel("1",true);
        Md5TagTabSticker1Label.setName("Md5TagTabSticker1Label");
        Md5TagTabSticker1Label.setPanel(getMd5MainPanel("1"));
        Md5TagTabPanel.add(Md5TagTabSticker1Label);

        DiyJAddLabel Md5TabAddLabel = new DiyJAddLabel("···");
        Md5TabAddLabel.setName("Md5TabAddLabel");
        Md5TagTabPanel.add(Md5TabAddLabel);

        return Md5TagTabPanel;
    }

    public JComponent Md5MainPanel(){

        JComponent Md5MainPanel = new JPanel(new GridBagLayout());
        Md5MainPanel.setName("Md5MainPanel");
        Md5MainPanel.setPreferredSize(new Dimension(0,0));

        Md5MainPanel.add(Md5MainBorderPanel(),new GridBagConstraints(
                0,0,
                1,2,
                0.2,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        Md5MainPanel.add(Md5MainStringScrollPane(), new GridBagConstraints(
                1,0,
                1,1,
                0.6,0.5,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        Md5MainPanel.add(Md5MainMd5ScrollPane(), new GridBagConstraints(
                1,1,
                1,1,
                0.6,0.5,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        Md5MainPanel.add(Md5MainBorderPanel(),new GridBagConstraints(
                2,0,
                1,2,
                0.2,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return Md5MainPanel;
    }

    public JComponent Md5MainBorderPanel(){
        JComponent Md5MainBorderPanel = new JPanel();
        Md5MainBorderPanel.setName("Md5MainBorderPanel");
        Md5MainBorderPanel.setPreferredSize(new Dimension(0,0));

        return  Md5MainBorderPanel;
    }

    public JComponent Md5MainStringScrollPane(){

        RSyntaxTextArea Md5MainStringArea = new RSyntaxTextArea();
        Md5MainStringArea.setName("Md5MainStringArea");
        Md5MainStringArea.setCodeFoldingEnabled(true);
        Md5MainStringArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        Md5MainStringArea.addKeyListener(new Md5MainKeyListener());

        RTextScrollPane Md5MainStringScrollPane = new RTextScrollPane(Md5MainStringArea);
        Md5MainStringScrollPane.setName("Md5MainStringScrollPane");
        Md5MainStringScrollPane.setPreferredSize(new Dimension(0,0));
        Md5MainStringScrollPane.setBorder(BorderFactory.createMatteBorder(1,0,1,0, Setting.gray));

        return Md5MainStringScrollPane;

    }

    public JComponent Md5MainMd5ScrollPane(){

        //setCodeFoldingEnabled需要在setSyntaxEditingStyle前面
        RSyntaxTextArea Md5MainMd5Area = new RSyntaxTextArea();
        Md5MainMd5Area.setName("Md5MainMd5Area");
        Md5MainMd5Area.setLineWrap(true);
        Md5MainMd5Area.setCodeFoldingEnabled(true);
        Md5MainMd5Area.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        Md5MainMd5Area.addKeyListener(new Md5MainKeyListener());

        RTextScrollPane Md5MainMd5ScrollPane = new RTextScrollPane(Md5MainMd5Area);
        Md5MainMd5ScrollPane.setName("Md5MainPlaintextScrollPane");
        Md5MainMd5ScrollPane.setHorizontalScrollBarPolicy(RTextScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        Md5MainMd5ScrollPane.setPreferredSize(new Dimension(0,0));
        Md5MainMd5ScrollPane.setBorder(null);

        return Md5MainMd5ScrollPane;
    }

    public JComponent getMd5MainPanel(String TagName){
        return MainPanelHashMap.containsKey(TagName)?(MainPanelHashMap.get(TagName)!=null?MainPanelHashMap.get(TagName):Md5BlackPanel()):Md5BlackPanel();
    }

    public JComponent Md5BlackPanel(){
        JPanel Md5BlackPanel = new JPanel();
        Md5BlackPanel.setName("Md5BlackPanel");
        Md5BlackPanel.setOpaque(true);
        Md5BlackPanel.setBackground(Color.PINK);

        return Md5BlackPanel;
    }

    static class Md5MainKeyListener implements KeyListener {

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
                if ("Md5MainStringArea".equals(eArea.getName())) {
                    outputArea = (RSyntaxTextArea) Helper.getComponent((JComponent) eArea.getParent().getParent().getParent(), "Md5MainMd5Area");
                    tText = Md5.stringToMd5(eArea.getText());
                }
                assert outputArea != null;
                outputArea.setText(tText);
            }
        }
    }


}
