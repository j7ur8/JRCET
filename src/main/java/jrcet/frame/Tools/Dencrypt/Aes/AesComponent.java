package jrcet.frame.Tools.Dencrypt.Aes;

import jrcet.diycomponents.*;
import jrcet.help.Helper;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;




public class AesComponent extends DiyJComponent {

    public static HashMap<String, GridBagConstraints> ComponentConstraintHashMap = new HashMap<>();
    public static HashMap<String, JComponent> MainPanelHashMap = new HashMap<>();
    public static JComponent AesComponentPanel = null;


    public JComponent main(){

        AesComponentPanel = new JPanel(new GridBagLayout());
        AesComponentPanel.setName("AesComponentPanel");
        AesComponentPanel.setBackground(Color.WHITE);

        AesComponentPanel.add(AesTagTabPanel(),new GridBagConstraints(
                0,0,
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        MainPanelHashMap.put("1", AesMainPanel());
        Helper.setConstraints(ComponentConstraintHashMap,AesComponentPanel,getAesMainPanel("1"),new GridBagConstraints(
                0,1,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(5,5,5,5),
                0,0
        ));

        return AesComponentPanel;
    }

    public JComponent AesTagTabPanel(){

        JPanel AesTagTabPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,0,0));
        AesTagTabPanel.setBackground(Color.WHITE);
        AesTagTabPanel.setName("AesTagTabPanel");

        DiyJAddLabel AesTagTabSticker1Label = new DiyJAddLabel("1",true);
        AesTagTabSticker1Label.setName("AesTagTabSticker1Label");
        AesTagTabSticker1Label.setPanel(getAesMainPanel("1"));
        AesTagTabPanel.add(AesTagTabSticker1Label);
        DiyJAddLabel AesTabAddLabel = new DiyJAddLabel("···");
        AesTabAddLabel.setName("AesTabAddLabel");
        AesTagTabPanel.add(AesTabAddLabel);

        return AesTagTabPanel;
    }

    public JComponent AesMainPanel(){

        JComponent AesMainPanel = new JPanel(new GridBagLayout());
        AesMainPanel.setName("AesMainPanel");
        AesMainPanel.setPreferredSize(new Dimension(0,0));

        AesMainPanel.add(AesMainPlaintextScrollPane(), new GridBagConstraints(
                0,0,
                1,1,
                0.9,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        AesMainPanel.add(AesMainCipherJScrollPane(), new GridBagConstraints(
                0,1,
                1,1,
                0.9,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        AesMainPanel.add(AesMainControlPanel(), new GridBagConstraints(
                1,0,
                1,2,
                0.1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return AesMainPanel;
    }

    public JComponent AesMainPlaintextScrollPane(){


        DiyJTextAreaScrollPane AesMainPlaintextAreaScrollPane = new DiyJTextAreaScrollPane("AesMainPlaintextArea");

        AesMainPlaintextAreaScrollPane.setText("#请输入...\n#iv和key支持Raw、Base64、Hex三种方式");


        return AesMainPlaintextAreaScrollPane;
    }

    public JComponent AesMainCipherJScrollPane(){

        return new DiyJTextAreaScrollPane("AesMainCiphertextArea");
    }

    public JComponent AesMainControlPanel(){

        JComponent AesMainControlPanel = new JPanel(new GridBagLayout());
        AesMainControlPanel.setName("AesMainControllerPanel");
        AesMainControlPanel.setBackground(Color.WHITE);

        AesMainControlPanel.add(AesMainControlModelPanel(), new GridBagConstraints(
                0,0,
                1,1,
                1,0.3,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        AesMainControlPanel.add(AesMainControlActionPanel(), new GridBagConstraints(
                0,1,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        AesMainControlPanel.add(AesMainControlIvPanel(), new GridBagConstraints(
                0,2,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        AesMainControlPanel.add(AesMainControlKeyPanel(), new GridBagConstraints(
                0,3,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));



        AesMainControlPanel.add(AseMainControlBlackPanel(), new GridBagConstraints(
                0,4,
                1,1,
                1,0.7,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return AesMainControlPanel;
    }

    public JComponent AesMainControlModelPanel(){
        JComponent AesMainControlModelPanel = new JPanel(new GridBagLayout());
        AesMainControlModelPanel.setName("AesMainControlModelPanel");
        AesMainControlModelPanel.setBackground(Color.WHITE);

        JList<String> AesMainControlModeList = new JList<>(new String[]{
                "AES/ECB/PKCS5Padding",
                "AES/ECB/NoPadding",
                "AES/CBC/PKCS5Padding",
                "AES/CBC/NoPadding"
        });
        AesMainControlModeList.setName("AesMainControlModeList");
        AesMainControlModeList.setFont(new Font("微软雅黑",Font.PLAIN,14));
        AesMainControlModeList.setBorder(BorderFactory.createEmptyBorder(10,20, 10, 0));
        AesMainControlModelPanel.add(AesMainControlModeList,new GridBagConstraints(
                0,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return AesMainControlModelPanel;
    }

    public JComponent AesMainControlActionPanel(){
        JComponent AesMainControlActionPanel = new JPanel(new GridBagLayout());
        AesMainControlActionPanel.setName("AesMainControlActionPanel");
        AesMainControlActionPanel.setBackground(Color.WHITE);

        DiyJLabel AesMainControlEncryptLabel = new DiyJLabel("Encrypt");
        AesMainControlEncryptLabel.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.gray));
        AesMainControlEncryptLabel.setName("AesMainControlEncryptLabel");
        AesMainControlActionPanel.add(AesMainControlEncryptLabel,new GridBagConstraints(
                0,0,
                1,1,
                0.5,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        DiyJLabel AesMainControlDecryptLabel = new DiyJLabel("Decrypt");
        AesMainControlDecryptLabel.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.gray));
        AesMainControlDecryptLabel.setName("AesMainControlDecryptLabel");
        AesMainControlActionPanel.add(AesMainControlDecryptLabel,new GridBagConstraints(
                1,0,
                1,1,
                0.5,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));


        return AesMainControlActionPanel;
    }

    public JComponent AesMainControlIvPanel(){

        JComponent AesMainControlIvPanel = new JPanel(new GridBagLayout());
        AesMainControlIvPanel.setName("AesMainControlIvPanel");
        AesMainControlIvPanel.setBackground(Color.WHITE);
        AesMainControlIvPanel.setPreferredSize(new Dimension(0,40));
        AesMainControlIvPanel.setBorder(BorderFactory.createEmptyBorder(5,0,0,5));

        DiyJLabel AesMainControlIvLabel = new DiyJLabel(" Iv:");
        AesMainControlIvLabel.setName("AesMainControlIvLabel");
        AesMainControlIvLabel.setPreferredSize(new Dimension(50,30));
        AesMainControlIvPanel.add(AesMainControlIvLabel,new GridBagConstraints(
                0,0,
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        JTextField AesMainControlIvField = new JTextField();
        AesMainControlIvField.setName("AesMainControlIvField");
        AesMainControlIvField.setPreferredSize(new Dimension(50,30));
        AesMainControlIvPanel.add(AesMainControlIvField,new GridBagConstraints(
                1,0,
                1,1,
                0.8,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        DiyJComboBox<String> AesMainControlIvBox = new DiyJComboBox<>(new String[]{"Raw", "Base64", "Hex"});
        AesMainControlIvBox.setName("AesMainControlIvBox");
        AesMainControlIvBox.setPreferredSize(new Dimension(50,30));
        AesMainControlIvPanel.add(AesMainControlIvBox,new GridBagConstraints(
                2,0,
                1,1,
                0.2,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return AesMainControlIvPanel;
    }

    public JComponent AesMainControlKeyPanel(){
        JComponent AesMainControlKeyPanel = new JPanel(new GridBagLayout());
        AesMainControlKeyPanel.setName("AesMainControlKeyPanel");
        AesMainControlKeyPanel.setBackground(Color.WHITE);
        AesMainControlKeyPanel.setPreferredSize(new Dimension(0,40));
        AesMainControlKeyPanel.setBorder(BorderFactory.createEmptyBorder(5,0,0,5));

        DiyJLabel AesMainControlKeyLabel = new DiyJLabel("Key: ");
        AesMainControlKeyLabel.setName("AesMainControlKeyLabel");
        AesMainControlKeyLabel.setPreferredSize(new Dimension(50,30));
        AesMainControlKeyPanel.add(AesMainControlKeyLabel,new GridBagConstraints(
                0,0,
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        JTextField AesMainControlKeyField = new JTextField();
        AesMainControlKeyField.setName("AesMainControlKeyField");
        AesMainControlKeyField.setPreferredSize(new Dimension(50,30));
        AesMainControlKeyPanel.add(AesMainControlKeyField,new GridBagConstraints(
                1,0,
                1,1,
                0.8,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        DiyJComboBox<String> AesMainControlKeyBox = new DiyJComboBox<>(new String[]{"Raw", "Base64", "Hex"});
        AesMainControlKeyBox.setName("AesMainControlKeyBox");
        AesMainControlKeyBox.setPreferredSize(new Dimension(50,30));
        AesMainControlKeyPanel.add(AesMainControlKeyBox,new GridBagConstraints(
                2,0,
                1,1,
                0.2,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return AesMainControlKeyPanel;
    }


    public JComponent AseMainControlBlackPanel(){
        JComponent AseMainControlBlackPanel = new JPanel();
        AseMainControlBlackPanel.setBackground(Color.WHITE);
        AseMainControlBlackPanel.setName("AseMainControlBlackPanel");

        return AseMainControlBlackPanel;
    }


    public JComponent getAesMainPanel(String TagName){
        return MainPanelHashMap.containsKey(TagName)?(MainPanelHashMap.get(TagName)!=null?MainPanelHashMap.get(TagName):AesBlackPanel()):AesBlackPanel();
    }

    public JComponent AesBlackPanel(){
        JPanel AesBlackPanel = new JPanel();
        AesBlackPanel.setName("AesBlackPanel");
        AesBlackPanel.setOpaque(true);
        AesBlackPanel.setBackground(Color.PINK);

        return AesBlackPanel;
    }

}
