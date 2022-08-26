package jrcet.frame.asset;



import jrcet.diycomponents.DiyJButton;
import jrcet.diycomponents.DiyJComponent;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
public class AAssetComponent extends DiyJComponent {

    public JComponent main(){

        JComponent AAssetComponentPanel = new JPanel(new GridBagLayout());
        AAssetComponentPanel.setName("AAssetComponentPanel");
        AAssetComponentPanel.setBackground(Color.WHITE);

        AAssetComponentPanel.add(NAssetMainAddPanel(), new GridBagConstraints(
                0,0,
                1,1,
                1,0.5,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(20,30,0,30),
                0,0
        ));

        AAssetComponentPanel.add(Asset.NAssetMainHistoryPanel(), new GridBagConstraints(
                0,1,
                1,1,
                1,0.5,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(10,30,30,30),
                0,0
        ));

        return AAssetComponentPanel;
    }


    public JComponent NAssetMainAddPanel(){
        JPanel NAssetMainAddPanel = new JPanel(new GridBagLayout());
        NAssetMainAddPanel.setName("NAssetMainAddPanel");
        NAssetMainAddPanel.setBackground(Color.WHITE);
        NAssetMainAddPanel.setPreferredSize(new Dimension(0,0));

        NAssetMainAddPanel.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createMatteBorder(1,0,0,0,Color.gray),
                        "Add Panel",
                        TitledBorder.LEFT,
                        TitledBorder.DEFAULT_POSITION
                )
        );


        JComponent tmp;
        String[] columns = new String[]{"Ip","Domain","Url","Port","Service","Belong","Vendor","Vul","Project","Source"};
        for(int i=0; i<columns.length; i++ ){
            tmp = createUnitPanel(columns[i]);
            NAssetMainAddPanel.add(tmp, new GridBagConstraints(
                    i%2,i/2,
                    1,1,
                    1,1,
                    GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH,
                    new Insets(5,0,0,0),
                    0,0
            ));
        }

        DiyJButton NAssetMainAddButton = new DiyJButton("Add Asset");
        NAssetMainAddButton.setName("NAssetMainAAddButton");
//        NAssetMainAddButton.setPreferredSize(new Dimension(0,0));

        DiyJButton NAssetMainAddweButton = new DiyJButton("Add Asset Without Exit");
        NAssetMainAddweButton.setName("NAssetMainAddweButton");
//        NAssetMainAddweButton.setPreferredSize(new Dimension(0,0));

        NAssetMainAddPanel.add(NAssetMainAddweButton, new GridBagConstraints(
                0,5,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(10,15,0,1),
                0,0
        ));

        NAssetMainAddPanel.add(NAssetMainAddButton, new GridBagConstraints(
                1,5,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(10,1,0,15),
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