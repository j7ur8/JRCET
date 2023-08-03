package jrcet.frame.Dencrypt.Rsa;

import jrcet.diycomponents.DiyJTextAreaScrollPane;
import jrcet.help.Helper;
import jrcet.diycomponents.DiyJAddLabel;
import jrcet.diycomponents.DiyJComponent;
import jrcet.diycomponents.DiyJLabel;
import jrcet.frame.Setting.Setting;

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
                0.7,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        RsaMainPanel.add(RsaMainCipherJScrollPane(), new GridBagConstraints(
                0,1,
                1,1,
                0.7,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        RsaMainPanel.add(RsaMainControlPanel(), new GridBagConstraints(
                1,0,
                1,2,
                0.3,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return RsaMainPanel;
    }

    public JComponent RsaMainPlaintextScrollPane(){

        DiyJTextAreaScrollPane RsaMainPlaintextAreaScrollPane = new DiyJTextAreaScrollPane("RsaMainPlaintextArea");

        return RsaMainPlaintextAreaScrollPane;
    }

    public JComponent RsaMainCipherJScrollPane(){

        DiyJTextAreaScrollPane RsaMainCiphertextAreaScrollPane = new DiyJTextAreaScrollPane("RsaMainCiphertextArea");

        return RsaMainCiphertextAreaScrollPane;
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
                1,0.5,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));


        return RsaMainControlPanel;
    }

    public JComponent RsaMainControlPublicScrollPane(){

        DiyJTextAreaScrollPane RsaMainControlPublicAreaScrollPane = new DiyJTextAreaScrollPane("RsaMainControlPublicArea");

        RsaMainControlPublicAreaScrollPane.setText("#Public Key");

        return  RsaMainControlPublicAreaScrollPane;
    }

    public JComponent RsaMainControlPrivateScrollPane(){
        DiyJTextAreaScrollPane RsaMainControlPrivateAreaScrollPane = new DiyJTextAreaScrollPane("RsaMainControlPrivateArea");
        RsaMainControlPrivateAreaScrollPane.setPreferredSize(new Dimension(0,0));
        RsaMainControlPrivateAreaScrollPane.setText("Private key");

        return RsaMainControlPrivateAreaScrollPane;
    }

    public JComponent RsaMainControlActionPanel() {
        JComponent RsaMainControlActionPanel = new JPanel(new GridBagLayout());
        RsaMainControlActionPanel.setName("RsaMainControlActionPanel");
        RsaMainControlActionPanel.setPreferredSize(new Dimension(0,0));
        RsaMainControlActionPanel.setBackground(Color.WHITE);

        DiyJLabel RsaMainControlEncryptLabel = new DiyJLabel("Encrypt");
        RsaMainControlEncryptLabel.setPreferredSize(new Dimension(0,27));
        RsaMainControlEncryptLabel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
        RsaMainControlEncryptLabel.setName("RsaMainControlEncryptLabel");
        RsaMainControlActionPanel.add(RsaMainControlEncryptLabel, new GridBagConstraints(
                0, 0,
                1, 1,
                0.5, 0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0),
                0, 0
        ));

        DiyJLabel RsaMainControlDecryptLabel = new DiyJLabel("Decrypt");
        RsaMainControlDecryptLabel.setPreferredSize(new Dimension(0,27));
        RsaMainControlDecryptLabel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
        RsaMainControlDecryptLabel.setName("RsaMainControlDecryptLabel");
        RsaMainControlActionPanel.add(RsaMainControlDecryptLabel, new GridBagConstraints(
                1, 0,
                1, 1,
                0.5, 0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0),
                0, 0
        ));

        RsaMainControlActionPanel.add(Helper.blackPanel(Color.white), new GridBagConstraints(
                0,1,
                2,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));
        return  RsaMainControlActionPanel;
    }


    public JComponent getRsaMainPanel(String TagName){
        return MainPanelHashMap.containsKey(TagName)?(MainPanelHashMap.get(TagName)!=null?MainPanelHashMap.get(TagName):Helper.blackPanel()):Helper.blackPanel();
    }

}
