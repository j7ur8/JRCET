package jrcet.frame.tools.Intruder;

import jrcet.diycomponents.DiyJComboBox;
import jrcet.diycomponents.DiyJComponent;
import jrcet.diycomponents.DiyJLabel;

import javax.swing.*;
import java.awt.*;

public class IntruderComponent extends DiyJComponent {

    public static JComponent IntruderComponentPanel = null;

    @Override
    public JComponent main() {

        IntruderComponentPanel = new JPanel(new GridBagLayout());
        IntruderComponentPanel.setName("IntruderComponentPanel");
        IntruderComponentPanel.setBackground(Color.WHITE);

        IntruderComponentPanel.add(IntruderMainPanel(),new GridBagConstraints(
                0,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return IntruderComponentPanel;
    }

    public JComponent IntruderMainPanel(){

        JComponent IntruderMainPanel = new JPanel(new GridBagLayout());
        IntruderMainPanel.setName("IntruderMainPanel");
        IntruderMainPanel.setBackground(Color.WHITE);

        IntruderMainPanel.add(IntruderMainAesPanel(), new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,100,0,100),
                0,0
        ));


        return IntruderMainPanel;
    }

    public JComponent IntruderMainAesPanel(){

        JComponent IntruderMainAesPanel = new JPanel(new GridBagLayout());
        IntruderMainAesPanel.setName("IntruderMainAesPanel");
        IntruderMainAesPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1,0,0,0,Color.gray),"Aes"));
        IntruderMainAesPanel.setPreferredSize(new Dimension(0,200));

        DiyJLabel IntruderMainAesIvLabel = new DiyJLabel(" Iv:");
        IntruderMainAesIvLabel.setName("IntruderMainAesIvLabel");
        IntruderMainAesIvLabel.setPreferredSize(new Dimension(50,30));
        JTextField IntruderMainAesIvField = new JTextField();
        IntruderMainAesIvField.setName("IntruderMainAesIvField");
        IntruderMainAesIvField.setPreferredSize(new Dimension(200,30));
        DiyJComboBox<String>IntruderMainAesIvBox = new DiyJComboBox<>(new String[]{"Raw", "Base64", "Hex"});
        IntruderMainAesIvBox.setName("IntruderMainAesIvBox");
        IntruderMainAesIvBox.setPreferredSize(new Dimension(100,30));

        DiyJLabel IntruderMainAesKeyLabel = new DiyJLabel("Key: ");
        IntruderMainAesKeyLabel.setName("IntruderMainAesKeyLabel");
        IntruderMainAesKeyLabel.setPreferredSize(new Dimension(50,30));
        JTextField IntruderMainAesKeyField = new JTextField();
        IntruderMainAesKeyField.setName("IntruderMainAesKeyField");
        IntruderMainAesKeyField.setPreferredSize(new Dimension(200,30));
        DiyJComboBox<String> IntruderMainAesKeyBox = new DiyJComboBox<>(new String[]{"Raw", "Base64", "Hex"});
        IntruderMainAesKeyBox.setName("IntruderMainAesKeyBox");
        IntruderMainAesKeyBox.setPreferredSize(new Dimension(100,30));

        DiyJLabel IntruderMainAesModeLabel = new DiyJLabel(("Mode: "));
        IntruderMainAesModeLabel.setName("IntruderMainAesModeLabel");
        IntruderMainAesModeLabel.setPreferredSize(new Dimension(50,30));
        DiyJComboBox<String> IntruderMainAesModeBox = new DiyJComboBox<>(new String[]{"AES/ECB/NoPadding", "AES/ECB/PKCS5Padding", "AES/CBC/NoPadding", "AES/CBC/PKCS5Padding"});
        IntruderMainAesModeBox.setName("IntruderMainAesModeBox");
        IntruderMainAesModeBox.setPreferredSize(new Dimension(0,30));
        DiyJComboBox<String> IntruderMainAesTypeBox = new DiyJComboBox<>(new String[]{"Decrypt","Encrypt"});
        IntruderMainAesTypeBox.setName("IntruderMainAesTypeBox");
        IntruderMainAesTypeBox.setPreferredSize(new Dimension(0,30));



        IntruderMainAesPanel.add(IntruderMainAesIvLabel, new GridBagConstraints(
                0,0,
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        IntruderMainAesPanel.add(IntruderMainAesIvField, new GridBagConstraints(
                1,0,
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        IntruderMainAesPanel.add(IntruderMainAesIvBox, new GridBagConstraints(
                2,0,
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        IntruderMainAesPanel.add(IntruderMainAesKeyLabel, new GridBagConstraints(
                0,1,
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        IntruderMainAesPanel.add(IntruderMainAesKeyField, new GridBagConstraints(
                1,1,
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        IntruderMainAesPanel.add(IntruderMainAesKeyBox, new GridBagConstraints(
                2,1,
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        IntruderMainAesPanel.add(IntruderMainAesModeLabel, new GridBagConstraints(
                0,3,
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        IntruderMainAesPanel.add(IntruderMainAesModeBox, new GridBagConstraints(
                1,3,
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));
        IntruderMainAesPanel.add(IntruderMainAesTypeBox, new GridBagConstraints(
                2,3,
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return  IntruderMainAesPanel;
    }

}
