package jrcet.frame.asset;



import jrcet.diycomponents.DiyJButton;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
public class AAssetComponent {

    public static JComponent AAssetComponentPanel;
//
//    public static void main(String[] args) {
//        Asset.registerHotKey();
//    }

    public JComponent AAssetComponentPanel(){
        AAssetComponentPanel = new JPanel(new GridBagLayout());
        AAssetComponentPanel.setName("AAssetComponentPanel");
        AAssetComponentPanel.setBackground(Color.WHITE);

        AAssetComponentPanel.add(NAssetMainAddPanel(), new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(30,30,0,30),
                0,0
        ));

        AAssetComponentPanel.add(Asset.NAssetMainHistoryPanel(), new GridBagConstraints(
                0,1,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,30,30,30),
                0,0
        ));

        return AAssetComponentPanel;
    }


    public JComponent NAssetMainAddPanel(){
        JPanel NAssetMainAddPanel = new JPanel(new GridBagLayout());
        NAssetMainAddPanel.setName("NAssetMainAddPanel");
        NAssetMainAddPanel.setBackground(Color.WHITE);
        NAssetMainAddPanel.setPreferredSize(new Dimension(0,250));

        NAssetMainAddPanel.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createMatteBorder(1,0,0,0,Color.gray),
                        "Add Panel",
                        TitledBorder.LEFT,
                        TitledBorder.DEFAULT_POSITION
                )
        );


        JLabel NAssetMainAddResultLabel = new JLabel("");
        NAssetMainAddResultLabel.setName("NAssetMainAddResultLabel");
        NAssetMainAddResultLabel.setFont(new Font("微软雅黑",Font.PLAIN,13));
        NAssetMainAddResultLabel.setHorizontalAlignment(JLabel.CENTER);
        NAssetMainAddResultLabel.setForeground(Color.RED);
        NAssetMainAddResultLabel.setPreferredSize(new Dimension(0,20));


        NAssetMainAddPanel.add(NAssetMainAddResultLabel, new GridBagConstraints(
                0,0,
                2,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,15,0,15),
                0,0
        ));


        JComponent tmp;
        String[] columns = new String[]{"Ip","Domain","Url","Port","Service","Vul","Project","Source"};
        for(int i=0; i<columns.length; i++ ){
            tmp = createUnitPanel(columns[i]);
            NAssetMainAddPanel.add(tmp, new GridBagConstraints(
                    i%2,i/2+1,
                    1,1,
                    1,0,
                    GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH,
                    new Insets(10,0,0,0),
                    0,0
            ));
        }

        DiyJButton NAssetMainAddButton = new DiyJButton("Add Asset");
        NAssetMainAddButton.setName("NAssetMainAAddButton");
        NAssetMainAddButton.setPreferredSize(new Dimension(0,30));

        DiyJButton NAssetMainAddweButton = new DiyJButton("Add Asset Without Exit");
        NAssetMainAddweButton.setName("NAssetMainAddweButton");
        NAssetMainAddweButton.setPreferredSize(new Dimension(0,30));

        NAssetMainAddPanel.add(NAssetMainAddweButton, new GridBagConstraints(
                0,5,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(15,15,0,1),
                0,0
        ));

        NAssetMainAddPanel.add(NAssetMainAddButton, new GridBagConstraints(
                1,5,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(15,1,0,15),
                0,0
        ));

        return NAssetMainAddPanel;
    }

    public JComponent createUnitPanel(String name){
        JPanel unitPanel = new JPanel(new GridBagLayout());
        unitPanel.setName("NAssetMainAdd"+name+"Panel");
        unitPanel.setPreferredSize(new Dimension(0,30));
        unitPanel.setBackground(Color.WHITE);

        String labelName = "NAssetMainAdd"+name+"Label";
        String fieldName = "NAssetMainAdd"+name+"Field";

        JLabel jLabel = new JLabel(name+": ",SwingConstants.RIGHT);
        jLabel.setName(labelName);
        jLabel.setPreferredSize(new Dimension(60,0));

        JTextField jTextField = new JTextField();
        jTextField.setName(fieldName);
        jTextField.setPreferredSize(new Dimension(0,0));

        unitPanel.add(jLabel, new GridBagConstraints(
                0,0,
                1,1,
                0,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0)
        );
        unitPanel.add(jTextField, new GridBagConstraints(
                1,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));
        return unitPanel;
    }


}