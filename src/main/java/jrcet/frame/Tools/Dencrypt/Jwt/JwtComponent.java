package jrcet.frame.Tools.Dencrypt.Jwt;

import jrcet.diycomponents.DiyJTextAreaScrollPane;
import jrcet.help.Helper;
import jrcet.diycomponents.DiyJAddLabel;
import jrcet.diycomponents.DiyJComponent;
import jrcet.frame.Setting.Setting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;



public class JwtComponent extends DiyJComponent {

    public static HashMap<String, GridBagConstraints> ComponentConstraintHashMap = new HashMap<>();
    public static HashMap<String, JComponent> MainPanelHashMap = new HashMap<>();

    public static JComponent JwtComponentPanel = null;

    public JComponent main(){

        JwtComponentPanel = new JPanel(new GridBagLayout());
        JwtComponentPanel.setName("JwtComponentPanel");
        JwtComponentPanel.setBackground(Color.WHITE);


        JwtComponentPanel.add(JwtTagTabPanel(),new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        MainPanelHashMap.put("1", JwtMainPanel());
        Helper.setConstraints(ComponentConstraintHashMap,JwtComponentPanel,getJwtMainPanel("1"),new GridBagConstraints(
                0,1,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(5,5,5,5),
                0,0
        ));

        return JwtComponentPanel;
    }


    public JComponent JwtTagTabPanel(){

        JPanel JwtTagTabPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,0,0));
        JwtTagTabPanel.setName("JwtTagTabPanel");
        JwtTagTabPanel.setBackground(Color.WHITE);

        DiyJAddLabel JwtTagTabSticker1Label = new DiyJAddLabel("1",true);
        JwtTagTabSticker1Label.setName("JwtTagTabSticker1Label");
        JwtTagTabSticker1Label.setPanel(getJwtMainPanel("1"));
        JwtTagTabPanel.add(JwtTagTabSticker1Label);

        DiyJAddLabel JwtTabAddLabel = new DiyJAddLabel("···");
        JwtTabAddLabel.setName("JwtTabAddLabel");
        JwtTagTabPanel.add(JwtTabAddLabel);

        return JwtTagTabPanel;
    }

    public JComponent JwtMainPanel(){

        JComponent JwtMainPanel = new JPanel(new GridBagLayout());
        JwtMainPanel.setName("JwtMainPanel");
        JwtMainPanel.setPreferredSize(new Dimension(0,0));

        JwtMainPanel.add(Helper.blackPanel(),new GridBagConstraints(
                0,0,
                1,2,
                0.2,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        JwtMainPanel.add(JwtMainTokenScrollPane(), new GridBagConstraints(
                1,0,
                2,1,
                0.6,0.2,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        JwtMainPanel.add(JwtMainHeaderScrollPane(), new GridBagConstraints(
                1,1,
                1,1,
                0.3,0.8,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        JwtMainPanel.add(JwtMainDataScrollPane(), new GridBagConstraints(
                2,1,
                1,1,
                0.3,0.8,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        JwtMainPanel.add(JwtMainControlPanel(),new GridBagConstraints(
                3,0,
                1,2,
                0.2,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return JwtMainPanel;
    }


    public JComponent JwtMainTokenScrollPane(){

        DiyJTextAreaScrollPane JwtMainTokenAreaScrollPane = new DiyJTextAreaScrollPane("JwtMainTokenArea");
        JwtMainTokenAreaScrollPane.addKeyListener(new JwtMainKeyListener());

        return JwtMainTokenAreaScrollPane;
    }

    public JComponent JwtMainHeaderScrollPane(){

        DiyJTextAreaScrollPane JwtMainHeaderAreaScrollPane = new DiyJTextAreaScrollPane("JwtMainHeaderArea");
        JwtMainHeaderAreaScrollPane.addKeyListener(new JwtMainKeyListener());

        return JwtMainHeaderAreaScrollPane;
    }

    public JComponent JwtMainDataScrollPane(){

        DiyJTextAreaScrollPane JwtMainDataAreaScrollPane = new DiyJTextAreaScrollPane("JwtMainDataArea");
        JwtMainDataAreaScrollPane.addKeyListener(new JwtMainKeyListener());
        JwtMainDataAreaScrollPane.addKeyListener(new JwtMainKeyListener());

        return JwtMainDataAreaScrollPane;
    }

    public JComponent JwtMainControlPanel(){
        JComponent JwtMainControlPanel = new JPanel(new GridBagLayout());
        JwtMainControlPanel.setName("JwtMainControlPanel");

        JwtMainControlPanel.add(JwtMainControlModePanel(),new GridBagConstraints(
                0,JwtMainControlPanel.getComponentCount(),
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        JwtMainControlPanel.add(Helper.blackPanel(),new GridBagConstraints(
                0,JwtMainControlPanel.getComponentCount(),
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        JwtMainControlPanel.add(Helper.blackPanel(),new GridBagConstraints(
                0,JwtMainControlPanel.getComponentCount(),
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return JwtMainControlPanel;
    }

    public JComponent JwtMainControlHS256Panel(){
        JComponent JwtMainControlModePanel = new JPanel(new GridBagLayout());
        JwtMainControlModePanel.setName("JwtMainControlModePanel");
        JwtMainControlModePanel.setPreferredSize(new Dimension(0,80));
        JwtMainControlModePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1,0,0,0,Color.BLACK),"HS256"));

        JTextField JwtMainControlSecretField = new JTextField();
        JwtMainControlSecretField.setName("JwtMainControlSecretField");
        JwtMainControlSecretField.setPreferredSize(new Dimension(0,40));
        JwtMainControlSecretField.addKeyListener(new JwtMainKeyListener());

        JCheckBox JwtMainControlModeBox = new JCheckBox("Base64 Encoded");
        JwtMainControlModeBox.setName("JwtMainControlModeBox");
        JwtMainControlModeBox.setPreferredSize(new Dimension(0,30));

        JwtMainControlModePanel.add(JwtMainControlSecretField, new GridBagConstraints(
                0,JwtMainControlModePanel.getComponentCount(),
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        JwtMainControlModePanel.add(JwtMainControlModeBox,new GridBagConstraints(
                0,JwtMainControlModePanel.getComponentCount(),
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));


        return JwtMainControlModePanel;
    }

    public JComponent JwtMainControlModePanel(){
        JComponent JwtMainControlModePanel = new JPanel(new GridBagLayout());
        JwtMainControlModePanel.setName("JwtMainControlModePanel");
        JwtMainControlModePanel.setPreferredSize(new Dimension(0,110));

        HashMap<String, JComponent> jComponentHashMap = new HashMap<String, JComponent>(){
            {
                put("HS256",JwtMainControlHS256Panel());
            }
        };

        String[] labelNames = new String[]{"HS256","HS384","HS512","RS256","RS384","RS512","ES256","ES384","ES512","PS256","PS384","PS512"};

        Helper.set4DiyJChangeLabel(JwtMainControlModePanel, labelNames, jComponentHashMap);

        return JwtMainControlModePanel;
    }

    public JComponent getJwtMainPanel(String TagName){
        return MainPanelHashMap.containsKey(TagName)?(MainPanelHashMap.get(TagName)!=null?MainPanelHashMap.get(TagName):Helper.blackPanel()):Helper.blackPanel();
    }



    static class JwtMainKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e){

        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {
//
//            JTextComponent eTextComponent = (JTextComponent) e.getSource();
//            String eText = eTextComponent.getText();
//            String eTextComponentName = eTextComponent.getName();
//            JComponent rootPanel = (JComponent) eTextComponent.getParent().getParent().getParent();
//            switch (eTextComponentName){
//                case "JwtMainTokenArea":
//                    ArrayList<String> arrayList = Jwt.parseJwt(eText);
//                    if(arrayList!=null){
//                        JTextComponent headerTextArea = (JTextComponent) Helper.getComponent(rootPanel,"JwtMainHeaderArea");assert headerTextArea !=null;
//                        JTextComponent dataTextArea = (JTextComponent) Helper.getComponent(rootPanel,"JwtMainDataArea"); assert dataTextArea !=null;
//                        headerTextArea.setText(arrayList.get(0));
//                        dataTextArea.setText(arrayList.get(1));
//                    }
//                    break;
//                case "JwtMainControlSecretField":
//                case "JwtMainHeaderArea":
//                case "JwtMainDataArea":
//                    String mode = Jwt.JwtModeType;
//                    JTextComponent tokenTextArea = (JTextComponent) Helper.getComponent(rootPanel,"JwtMainTokenArea");assert tokenTextArea !=null;
//                    if(mode ==null){
//                        tokenTextArea.setText("请选择模式");
//                        break;
//                    }
//                    JTextField secretTextField = (JTextField)  Helper.getComponent(rootPanel, "JwtMainControlSecretField"); assert secretTextField!=null;
//                    JTextComponent headerTextArea = (JTextComponent) Helper.getComponent(rootPanel,"JwtMainHeaderArea");assert headerTextArea !=null;
//                    JTextComponent dataTextArea = (JTextComponent) Helper.getComponent(rootPanel,"JwtMainDataArea"); assert dataTextArea !=null;
//                    String headerText = headerTextArea.getText();
//                    String dataText = dataTextArea.getText();
//                    String secret = secretTextField.getText();
//
//                    tokenTextArea.setText(Jwt.generateJwt(headerText,dataText,secret, mode));
//                    break;
//            }

        }
    }


}
