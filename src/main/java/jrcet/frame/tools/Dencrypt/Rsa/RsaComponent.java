package jrcet.frame.tools.Dencrypt.Rsa;

import jrcet.help.Helper;
import jrcet.diycomponents.DiyJAddLabel;
import jrcet.diycomponents.DiyJComponent;
import jrcet.diycomponents.DiyJLabel;
import jrcet.diycomponents.DiyJTextArea.ui.rsyntaxtextarea.RSyntaxTextArea;
import jrcet.diycomponents.DiyJTextArea.ui.rsyntaxtextarea.SyntaxConstants;
import jrcet.diycomponents.DiyJTextArea.ui.rtextarea.RTextScrollPane;
import jrcet.frame.setting.Setting;

import javax.swing.*;

import java.awt.*;
import java.util.HashMap;


public class RsaComponent extends DiyJComponent {

    public static HashMap<String, JComponent> MainPanelHashMap = new HashMap<>();
    public static HashMap<String, GridBagConstraints> ComponentConstraintHashMap = new HashMap<>();

    public static JComponent RsaComponentPanel = null;

    public JComponent main(){

        RsaComponentPanel = new JPanel(new GridBagLayout());
        RsaComponentPanel.setName("RsaComponentPanel");
        RsaComponentPanel.setBackground(Color.WHITE);
        RsaComponentPanel.setPreferredSize(new Dimension(0,0));

        RsaComponentPanel.add(RsaTagBlackPanel(), new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        RsaComponentPanel.add(RsaTagTabPanel(),new GridBagConstraints(
                1,0,
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        MainPanelHashMap.put("1", RsaMainPanel());
        Helper.setConstraints(ComponentConstraintHashMap, RsaComponentPanel, getRsaMainPanel("1"), new GridBagConstraints(
                0,1,
                2,1,
                1,0.7,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(5,5,5,5),
                0,0
        ));

        return RsaComponentPanel;
    }

    public JComponent RsaTagBlackPanel(){
        JComponent RsaTagBlackPanel = new JPanel();
        RsaTagBlackPanel.setName("RsaTagBlackPanel");
        RsaTagBlackPanel.setOpaque(false);
        RsaTagBlackPanel.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Setting.gray));
        return RsaTagBlackPanel;
    }

    public JComponent RsaTagTabPanel(){
        JPanel RsaTagTabPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,0,0));
        RsaTagTabPanel.setName("RsaTagTabPanel");
        RsaTagTabPanel.setBorder(BorderFactory.createMatteBorder(0,0,0,0,new Color(203,208,209)));

        DiyJAddLabel RsaTagTabSticker1Label = new DiyJAddLabel("1",true);
        RsaTagTabSticker1Label.setName("RsaTagTabSticker1Label");
        RsaTagTabSticker1Label.setPanel(getRsaMainPanel("1"));
        RsaTagTabPanel.add(RsaTagTabSticker1Label);
        DiyJAddLabel RsaTabAddLabel = new DiyJAddLabel("···");
        RsaTabAddLabel.setName("RsaTabAddLabel");
        RsaTagTabPanel.add(RsaTabAddLabel);

        return RsaTagTabPanel;
    }

    public JComponent RsaMainPanel(){

        JComponent RsaMainPanel = new JPanel(new GridBagLayout());
        RsaMainPanel.setName("RsaMainPanel");
        RsaMainPanel.setPreferredSize(new Dimension(0,0));

        RsaMainPanel.add(RsaMainPlaintextScrollPane(), new GridBagConstraints(
                0,0,
                1,1,
                0.9,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        RsaMainPanel.add(RsaMainCiphertextScrollPane(), new GridBagConstraints(
                0,1,
                1,1,
                0.9,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        RsaMainPanel.add(RsaMainControlPanel(), new GridBagConstraints(
                1,0,
                1,2,
                0.127,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return RsaMainPanel;
    }

    public JComponent RsaMainPlaintextScrollPane(){

        RSyntaxTextArea RsaMainPlaintextArea = new RSyntaxTextArea();
        RsaMainPlaintextArea.setName("RsaMainPlaintextArea");
        RsaMainPlaintextArea.setCodeFoldingEnabled(true);
        RsaMainPlaintextArea.setLineWrap(true);
        RsaMainPlaintextArea.setText("#请输入...");
        RsaMainPlaintextArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);

        RTextScrollPane RsaMainPlaintextScrollPane = new RTextScrollPane(RsaMainPlaintextArea);
        RsaMainPlaintextScrollPane.setHorizontalScrollBarPolicy(RTextScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        RsaMainPlaintextScrollPane.setName("RsaMainPlaintextScrollPane");
        RsaMainPlaintextScrollPane.setPreferredSize(new Dimension(0,0));
        RsaMainPlaintextScrollPane.setBorder(BorderFactory.createMatteBorder(0,0,1,0,Setting.gray));

        return RsaMainPlaintextScrollPane;

    }

    public JComponent RsaMainCiphertextScrollPane(){

        //setCodeFoldingEnabled需要在setSyntaxEditingStyle前面
        RSyntaxTextArea RsaMainCiphertextArea = new RSyntaxTextArea();
        RsaMainCiphertextArea.setName("RsaMainCiphertextArea");
        RsaMainCiphertextArea.setCodeFoldingEnabled(true);
        RsaMainCiphertextArea.setLineWrap(true);
        RsaMainCiphertextArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);

        RTextScrollPane RsaMainCiphertextScrollPane = new RTextScrollPane(RsaMainCiphertextArea);
        RsaMainCiphertextScrollPane.setHorizontalScrollBarPolicy(RTextScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        RsaMainCiphertextScrollPane.setName("RsaMainCiphertextScrollPane");
        RsaMainCiphertextScrollPane.setBorder(null);
        RsaMainCiphertextScrollPane.setPreferredSize(new Dimension(0,0));

        return RsaMainCiphertextScrollPane;
    }

    public JComponent RsaMainControlPanel(){

        JComponent RsaMainControlPanel = new JPanel(new GridBagLayout());
        RsaMainControlPanel.setName("RsaMainControllerPanel");
        RsaMainControlPanel.setBackground(Color.WHITE);

        RsaMainControlPanel.add(RsaMainControlPublicScrollPane(),new GridBagConstraints(
                0,0,
                1,1,
                1,0.25,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        RsaMainControlPanel.add(RsaMainControlPrivateScrollPane(),new GridBagConstraints(
                0,1,
                1,1,
                1,0.25,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        RsaMainControlPanel.add(RsaMainControlActionPanel(),new GridBagConstraints(
                0,2,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        RsaMainControlPanel.add(RsaMainControlBlackPanel(), new GridBagConstraints(
                0,3,
                1,1,
                1,0.5,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return RsaMainControlPanel;
    }

    public JComponent RsaMainControlPublicScrollPane(){
        RSyntaxTextArea RsaMainControlPublicArea = new RSyntaxTextArea();
        RsaMainControlPublicArea.setName("RsaMainControlPublicArea");
        RsaMainControlPublicArea.setCodeFoldingEnabled(true);
        RsaMainControlPublicArea.setLineWrap(true);
        RsaMainControlPublicArea.setText("#Public Key");
        RsaMainControlPublicArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE);

        RTextScrollPane RsaMainControlPublicScrollPane = new RTextScrollPane(RsaMainControlPublicArea);
        RsaMainControlPublicScrollPane.setHorizontalScrollBarPolicy(RTextScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        RsaMainControlPublicScrollPane.setName("RsaMainControlPublicScrollPane");
        RsaMainControlPublicScrollPane.setLineNumbersEnabled(false);
        RsaMainControlPublicScrollPane.setPreferredSize(new Dimension(0,0));
        RsaMainControlPublicScrollPane.setBorder(BorderFactory.createMatteBorder(0,0,1,0,Setting.gray));

        return RsaMainControlPublicScrollPane;
    }

    public JComponent RsaMainControlPrivateScrollPane(){
        RSyntaxTextArea RsaMainControlPrivateArea = new RSyntaxTextArea();
        RsaMainControlPrivateArea.setName("RsaMainControlPrivateArea");
        RsaMainControlPrivateArea.setCodeFoldingEnabled(true);
        RsaMainControlPrivateArea.setLineWrap(true);
        RsaMainControlPrivateArea.setText("#Private Key");
        RsaMainControlPrivateArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE);

        RTextScrollPane RsaMainControlPrivateScrollPane = new RTextScrollPane(RsaMainControlPrivateArea);
        RsaMainControlPrivateScrollPane.setHorizontalScrollBarPolicy(RTextScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        RsaMainControlPrivateScrollPane.setName("RsaMainControlPrivateScrollPane");
        RsaMainControlPrivateScrollPane.setLineNumbersEnabled(false);
        RsaMainControlPrivateScrollPane.setPreferredSize(new Dimension(0,0));
        RsaMainControlPrivateScrollPane.setBorder(null);

        return RsaMainControlPrivateScrollPane;
    }

    public JComponent RsaMainControlActionPanel() {
        JComponent RsaMainControlActionPanel = new JPanel(new GridBagLayout());
        RsaMainControlActionPanel.setName("RsaMainControlActionPanel");
        RsaMainControlActionPanel.setBackground(Color.WHITE);

        DiyJLabel RsaMainControlEncryptLabel = new DiyJLabel("Encrypt");
        RsaMainControlEncryptLabel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
        RsaMainControlEncryptLabel.setName("RsaMainControlEncryptLabel");
        RsaMainControlActionPanel.add(RsaMainControlEncryptLabel, new GridBagConstraints(
                0, 0,
                1, 1,
                0.5, 1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0),
                0, 0
        ));

        DiyJLabel RsaMainControlDecryptLabel = new DiyJLabel("Decrypt");
        RsaMainControlDecryptLabel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
        RsaMainControlDecryptLabel.setName("RsaMainControlDecryptLabel");
        RsaMainControlActionPanel.add(RsaMainControlDecryptLabel, new GridBagConstraints(
                1, 0,
                1, 1,
                0.5, 1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0),
                0, 0
        ));

        return  RsaMainControlActionPanel;
    }

    public JComponent RsaMainControlBlackPanel(){
        JComponent AseMainControlBlackPanel = new JPanel();
        AseMainControlBlackPanel.setBackground(Color.WHITE);
        AseMainControlBlackPanel.setName("AseMainControlBlackPanel");

        return AseMainControlBlackPanel;
    }


    public JComponent RsaBlackPanel(){
        JPanel RsaBlackPanel = new JPanel();
        RsaBlackPanel.setName("RsaBlackPanel");
        RsaBlackPanel.setOpaque(true);
        RsaBlackPanel.setBackground(Color.PINK);

        return RsaBlackPanel;
    }

    public JComponent getRsaMainPanel(String TagName){
        return MainPanelHashMap.containsKey(TagName)?(MainPanelHashMap.get(TagName)!=null?MainPanelHashMap.get(TagName):RsaBlackPanel()):RsaBlackPanel();
    }

}
