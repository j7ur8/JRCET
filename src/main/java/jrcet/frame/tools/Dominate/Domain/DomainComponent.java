package jrcet.frame.tools.Dominate.Domain;

import burp.lib.Helper;
import jrcet.diycomponents.DiyJAddLabel;
import jrcet.diycomponents.DiyJComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

public class DomainComponent extends DiyJComponent {

    public static HashMap<String, GridBagConstraints> ComponentConstraintHashMap = new HashMap<>();
    public static HashMap<String, JComponent> MainPanelHashMap = new HashMap<>();
    public static JComponent DomainComponentPanel = null;


    @Override
    public JComponent main() {

        DomainComponentPanel = new JPanel(new GridBagLayout());
        DomainComponentPanel.setName("DomainComponentPanel");
        DomainComponentPanel.setBackground(Color.WHITE);

        DomainComponentPanel.add(DomainTagTabPanel(),new GridBagConstraints(
                0,0,
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        MainPanelHashMap.put("1", DomainMainPanel());
        Helper.setConstraints(ComponentConstraintHashMap,DomainComponentPanel,getMainPanel("1"),new GridBagConstraints(
                0,1,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(5,5,5,5),
                0,0
        ));

        return  DomainComponentPanel;
    }

    public JComponent DomainTagTabPanel(){

        JComponent DomainTagTabPanel = new JPanel(new FlowLayout());
        DomainTagTabPanel.setBackground(Color.WHITE);
        DomainTagTabPanel.setName("DomainTagTabPanel");

        DiyJAddLabel DomainTagTabSticker1Label = new DiyJAddLabel("1",true);
        DomainTagTabSticker1Label.setName("DomainTagTabSticker1Label");
        DomainTagTabSticker1Label.setPanel(getMainPanel("1"));
        DomainTagTabPanel.add(DomainTagTabSticker1Label);

        DiyJAddLabel DomainTabAddLabel = new DiyJAddLabel("···");
        DomainTabAddLabel.setName("AesTabAddLabel");
        DomainTagTabPanel.add(DomainTabAddLabel);

        return DomainTagTabPanel;
    }

    public JComponent DomainMainPanel(){
        JComponent DomainMainPanel = new JPanel(new GridBagLayout());
        DomainMainPanel.setName("DomainMainPanel");
        DomainMainPanel.setBackground(Color.WHITE);

        DomainMainPanel.add(DomainMainSubdomainPanel(),new GridBagConstraints(
                0,0,
                1,2,
                0.3,0.5,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        DomainMainPanel.add(DomainMainSearchPanel(),new GridBagConstraints(
                1,0,
                1,1,
                0.4,0.05,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        DomainMainPanel.add(DomainMainIPDomainPanel(),new GridBagConstraints(
                1,1,
                1,1,
                0.4,0.45,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        DomainMainPanel.add(DomainMainWhoisPanel(),new GridBagConstraints(
                2,0,
                1,2,
                0.3,0.5,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        DomainMainPanel.add(DomainMainBeianPanel(),new GridBagConstraints(
                0,2,
                3,1,
                1,0.5,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));
        return DomainMainPanel;
    }

    public JComponent DomainMainSearchPanel(){
        JComponent DomainMainSearchPanel = new JPanel(new GridBagLayout());
        DomainMainSearchPanel.setName("DomainMainSearchPanel");
        DomainMainSearchPanel.setPreferredSize(new Dimension(0,0));
        DomainMainSearchPanel.setBackground(Color.WHITE);

        JTextField DomainMainSearchUrlField = new JTextField("");
        DomainMainSearchUrlField.setHorizontalAlignment(JTextField.CENTER);
        DomainMainSearchUrlField.setFont(new Font("微软雅黑", Font.PLAIN,20));
        DomainMainSearchUrlField.addKeyListener(new DomainMainSearchKeyListener());
        DomainMainSearchUrlField.setPreferredSize(new Dimension(0,0));

        DomainMainSearchPanel.add(DomainMainSearchUrlField, new GridBagConstraints(
                0,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));
        return DomainMainSearchPanel;
    }

    public JComponent DomainMainIPDomainPanel(){
        JComponent DomainMainIPDomainPanel = new JPanel(new GridBagLayout());
        DomainMainIPDomainPanel.setName("DomainMainIPDomainPanel");

        return DomainMainIPDomainPanel;
    }

    public JComponent DomainMainSubdomainPanel(){
        JComponent DomainMainSubdomainPanel = new JPanel(new GridBagLayout());
        DomainMainSubdomainPanel.setName("DomainMainSubdomainPanel");

        return DomainMainSubdomainPanel;
    }

    public JComponent DomainMainBeianPanel(){
        JComponent DomainMainBeianPanel = new JPanel(new GridBagLayout());
        DomainMainBeianPanel.setName("DomainMainBeianPanel");

        return DomainMainBeianPanel;
    }

    public JComponent DomainMainWhoisPanel(){
        JComponent DomainMainWhoisPanel = new JPanel(new GridBagLayout());
        DomainMainWhoisPanel.setName("DomainMainWhoisPanel");

        return DomainMainWhoisPanel;
    }

    public JComponent getMainPanel(String TagName ){
        return MainPanelHashMap.containsKey(TagName)?(MainPanelHashMap.get(TagName)!=null?MainPanelHashMap.get(TagName):DomainBlackPanel()):DomainBlackPanel();
    }

    public JComponent DomainBlackPanel(){
        JPanel DomainBlackPanel = new JPanel();
        DomainBlackPanel.setName("DomainBlackPanel");
        DomainBlackPanel.setOpaque(true);

        return DomainBlackPanel;
    }

    static class DomainMainSearchKeyListener implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {
            if( (e.getModifiers()== InputEvent.CTRL_MASK || e.getModifiers() == InputEvent.META_MASK) && e.getKeyCode()==71) {
                JComponent rootPanel = (JComponent) ((JTextField)e.getSource()).getParent().getParent();
                JTextField eField = (JTextField) e.getSource();
                HashMap<String, Object> radiateDataMap = Domain.radiate(eField.getText());
                Domain.updatePanel(rootPanel, radiateDataMap);
            }
        }
    }
}
