package jrcet.frame.Intruder;

import jrcet.diycomponents.*;
import jrcet.help.Helper;




import javax.swing.*;
import java.awt.*;

public class IntruderComponent extends DiyJComponent {

    public static JComponent IntruderComponentPanel = null;


    @Override
    public JComponent component() {

        IntruderComponentPanel = new JPanel(new GridBagLayout());
        IntruderComponentPanel.setName("IntruderComponentPanel");
        IntruderComponentPanel.setBackground(Color.WHITE);

        IntruderComponentPanel.add(IntruderMainPanel(),new GridBagConstraints(
                0,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(30,0,0,0),
                0,0
        ));

        return IntruderComponentPanel;
    }

    public JComponent IntruderMainPanel(){

        JComponent IntruderMainPanel = new JPanel(new GridBagLayout());
        IntruderMainPanel.setName("IntruderMainPanel");
        IntruderMainPanel.setBackground(Color.WHITE);

        IntruderMainPanel.add(IntruderMainAesPanel(), new GridBagConstraints(
                0, IntruderMainPanel.getComponentCount(),
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        IntruderMainPanel.add(IntruderMainRsaPanel(), new GridBagConstraints(
                0, IntruderMainPanel.getComponentCount(),
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        IntruderMainPanel.add(IntruderMainBasePanel(), new GridBagConstraints(
                0, IntruderMainPanel.getComponentCount(),
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        IntruderMainPanel.add(IntruderMainAsciiPanel(), new GridBagConstraints(
                0, IntruderMainPanel.getComponentCount(),
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        IntruderMainPanel.add(IntruderMainUnicodePanel(), new GridBagConstraints(
                0, IntruderMainPanel.getComponentCount(),
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));


        JScrollPane IntruderMainScrollPane = new JScrollPane(IntruderMainPanel);
        IntruderMainScrollPane.setName("IntruderMainScrollPane");
        IntruderMainScrollPane.setPreferredSize(new Dimension(0,0));
        IntruderMainScrollPane.setBorder(null);

        return IntruderMainScrollPane;
    }


    public JComponent IntruderMainAesPanel(){

        JComponent IntruderMainAesPanel = new JPanel(new GridBagLayout());
        IntruderMainAesPanel.setBackground(Color.WHITE);
        IntruderMainAesPanel.setName("IntruderMainAesPanel");
        IntruderMainAesPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1,0,0,0,Color.gray),"Aes"));
        IntruderMainAesPanel.setPreferredSize(new Dimension(0,150));

        DiyJLabel IntruderMainAesIvLabel = new DiyJLabel(" Iv:");
        IntruderMainAesIvLabel.setName("IntruderMainAesIvLabel");
        IntruderMainAesIvLabel.setPreferredSize(new Dimension(50,30));
        JTextField IntruderMainAesIvField = new JTextField();
        IntruderMainAesIvField.setName("IntruderMainAesIvField");
        IntruderMainAesIvField.setPreferredSize(new Dimension(200,30));
        DiyJComboBox<String>IntruderMainAesIvBox = new DiyJComboBox<>(new String[]{"Raw", "Base64", "Hex"});
        IntruderMainAesIvBox.setName("IntruderMainAesIvBox");
        IntruderMainAesIvBox.setPreferredSize(new Dimension(120,30));

        DiyJLabel IntruderMainAesKeyLabel = new DiyJLabel("Key: ");
        IntruderMainAesKeyLabel.setName("IntruderMainAesKeyLabel");
        IntruderMainAesKeyLabel.setPreferredSize(new Dimension(50,30));
        JTextField IntruderMainAesKeyField = new JTextField();
        IntruderMainAesKeyField.setName("IntruderMainAesKeyField");
        IntruderMainAesKeyField.setPreferredSize(new Dimension(200,30));
        DiyJComboBox<String> IntruderMainAesKeyBox = new DiyJComboBox<>(new String[]{"Raw", "Base64", "Hex"});
        IntruderMainAesKeyBox.setName("IntruderMainAesKeyBox");
        IntruderMainAesKeyBox.setPreferredSize(new Dimension(120,30));

        DiyJLabel IntruderMainAesModeLabel = new DiyJLabel(("Mode: "));
        IntruderMainAesModeLabel.setName("IntruderMainAesModeLabel");
        IntruderMainAesModeLabel.setPreferredSize(new Dimension(50,30));
        DiyJComboBox<String> IntruderMainAesModeBox = new DiyJComboBox<>(new String[]{"AES/ECB/NoPadding", "AES/ECB/PKCS5Padding", "AES/CBC/NoPadding", "AES/CBC/PKCS5Padding"});
        IntruderMainAesModeBox.setName("IntruderMainAesModeBox");
        IntruderMainAesModeBox.setPreferredSize(new Dimension(200,30));
        DiyJComboBox<String> IntruderMainAesTypeBox = new DiyJComboBox<>(new String[]{"Encrypt","Decrypt"});
        IntruderMainAesTypeBox.setName("IntruderMainAesTypeBox");
        IntruderMainAesTypeBox.setPreferredSize(new Dimension(120,30));

        IntruderMainAesPanel.add(Helper.blackPanel(Color.WHITE) ,new GridBagConstraints(
                0,0,
                3,1,
                0,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        IntruderMainAesPanel.add(IntruderMainAesIvLabel, new GridBagConstraints(
                0,1,
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,50,0,0),
                0,0
        ));

        IntruderMainAesPanel.add(IntruderMainAesIvField, new GridBagConstraints(
                1,1,
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        IntruderMainAesPanel.add(IntruderMainAesIvBox, new GridBagConstraints(
                2,1,
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        IntruderMainAesPanel.add(IntruderMainAesKeyLabel, new GridBagConstraints(
                0,2,
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,50,0,0),
                0,0
        ));

        IntruderMainAesPanel.add(IntruderMainAesKeyField, new GridBagConstraints(
                1,2,
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        IntruderMainAesPanel.add(IntruderMainAesKeyBox, new GridBagConstraints(
                2,2,
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
                new Insets(0,50,0,0),
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

        IntruderMainAesPanel.add(Helper.blackPanel(Color.WHITE),new GridBagConstraints(
                0,4,
                3,1,
                0,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        IntruderMainAesPanel.add(Helper.blackPanel(Color.WHITE),new GridBagConstraints(
                3,0,
                1,5,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        for(Component i : IntruderMainAesPanel.getComponents()){
            i.setFont(new Font("微软雅黑", Font.PLAIN,14));;
        }
        return  IntruderMainAesPanel;
    }

    public JComponent IntruderMainRsaPanel(){

        JComponent IntruderMainRsaPanel = new JPanel(new GridBagLayout());
        IntruderMainRsaPanel.setName("IntruderMainRsaPanel");
        IntruderMainRsaPanel.setBackground(Color.WHITE);
        IntruderMainRsaPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1,0,0,0,Color.gray),"Rsa"));
        IntruderMainRsaPanel.setPreferredSize(new Dimension(0,200));

        DiyJTextAreaScrollPane IntruderMainRsaPublicAreaScrollPane = new DiyJTextAreaScrollPane("IntruderMainRsaPublicArea");


        DiyJTextAreaScrollPane IntruderMainRsaPrivateAreaScrollPane = new DiyJTextAreaScrollPane("IntruderMainRsaPrivateArea");


        DiyJComboBox<String> IntruderMainRsaTypeBox = new DiyJComboBox<>(new String[]{"Encrypt","Decrypt"});
        IntruderMainRsaTypeBox.setName("IntruderMainRsaTypeBox");
        IntruderMainRsaTypeBox.setPreferredSize(new Dimension(120,30));

        IntruderMainRsaPanel.add(IntruderMainRsaPublicAreaScrollPane, new GridBagConstraints(
                0,0,
                1,3,
                0.5,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(5,10,10,5),
                0,0
        ));

        IntruderMainRsaPanel.add(IntruderMainRsaPrivateAreaScrollPane, new GridBagConstraints(
                1,0,
                1,3,
                0.5,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(5,5,10,0),
                0,0
        ));

        IntruderMainRsaPanel.add(Helper.blackPanel(Color.WHITE),new GridBagConstraints(
                2,0,
                1,1,
                0,0.5,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        IntruderMainRsaPanel.add(IntruderMainRsaTypeBox, new GridBagConstraints(
                2,1,
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        IntruderMainRsaPanel.add(Helper.blackPanel(Color.WHITE),new GridBagConstraints(
                2,2,
                1,1,
                0,0.5,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return IntruderMainRsaPanel;
    }

    public JComponent IntruderMainBasePanel() {

        JComponent IntruderMainBasePanel = new JPanel(new GridBagLayout());
        IntruderMainBasePanel.setName("IntruderMainBasePanel");
        IntruderMainBasePanel.setBackground(Color.WHITE);
        IntruderMainBasePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.gray), "Base"));
        IntruderMainBasePanel.setPreferredSize(new Dimension(0, 100));

        DiyJComboBox<String> IntruderMainBaseModeBox = new DiyJComboBox<>(new String[]{"Base64","Base32"});
        IntruderMainBaseModeBox.setName("IntruderMainBaseModeBox");
        IntruderMainBaseModeBox.setPreferredSize(new Dimension(120,30));

        DiyJComboBox<String> IntruderMainBaseTypeBox = new DiyJComboBox<>(new String[]{"Encrypt","Decrypt"});
        IntruderMainBaseTypeBox.setName("IntruderMainBaseTypeBox");
        IntruderMainBaseTypeBox.setPreferredSize(new Dimension(120,30));

        IntruderMainBasePanel.add(Helper.blackPanel(Color.WHITE),new GridBagConstraints(
                0,0,
                2,1,
                0,0.5,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        IntruderMainBasePanel.add(IntruderMainBaseModeBox,new GridBagConstraints(
                0,1,
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,50,0,0),
                0,0
        ));

        IntruderMainBasePanel.add(IntruderMainBaseTypeBox,new GridBagConstraints(
                1,1,
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        IntruderMainBasePanel.add(Helper.blackPanel(Color.WHITE),new GridBagConstraints(
                0,2,
                2,1,
                0,0.5,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        IntruderMainBasePanel.add(Helper.blackPanel(Color.WHITE),new GridBagConstraints(
                2,0,
                1,3,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return IntruderMainBasePanel;
    }

    public JComponent IntruderMainAsciiPanel() {

        JComponent IntruderMainAsciiPanel = new JPanel(new GridBagLayout());
        IntruderMainAsciiPanel.setName("IntruderMainAsciiPanel");
        IntruderMainAsciiPanel.setBackground(Color.WHITE);
        IntruderMainAsciiPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.gray), "Ascii"));
        IntruderMainAsciiPanel.setPreferredSize(new Dimension(0, 150));

        JLabel IntruderMainAsciiSeparator1Label = new JLabel("输入分割符: ");
        IntruderMainAsciiSeparator1Label.setPreferredSize(new Dimension(75,30));
        IntruderMainAsciiSeparator1Label.setName("IntruderMainAsciiSeparator1Label");

        JTextField IntruderMainAsciiSeparator1Field = new JTextField("");
        IntruderMainAsciiSeparator1Field.setName("IntruderMainAsciiSeparator1Field");
        IntruderMainAsciiSeparator1Field.setPreferredSize(new Dimension(200,30));

        JLabel IntruderMainAsciiSeparator2Label = new JLabel("输出分割符: ");
        IntruderMainAsciiSeparator2Label.setPreferredSize(new Dimension(75,30));
        IntruderMainAsciiSeparator2Label.setName("IntruderMainAsciiSeparator2Label");

        JTextField IntruderMainAsciiSeparator2Field = new JTextField("");
        IntruderMainAsciiSeparator2Field.setName("IntruderMainAsciiSeparator2Field");
        IntruderMainAsciiSeparator2Field.setPreferredSize(new Dimension(200,30));

        DiyJComboBox<String> IntruderMainAsciiTypeBox = new DiyJComboBox<>(new String[]{"Encrypt","Decrypt"});
        IntruderMainAsciiTypeBox.setName("IntruderMainAsciiTypeBox");
        IntruderMainAsciiTypeBox.setPreferredSize(new Dimension(120,30));


        IntruderMainAsciiPanel.add(Helper.blackPanel(Color.WHITE), new GridBagConstraints(
                0,0,
                3,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        IntruderMainAsciiPanel.add(IntruderMainAsciiSeparator1Label, new GridBagConstraints(
                0,1,
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,25,0,0),
                0,0
        ));

        IntruderMainAsciiPanel.add(IntruderMainAsciiSeparator1Field, new GridBagConstraints(
                1,1,
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        IntruderMainAsciiPanel.add(IntruderMainAsciiSeparator2Label, new GridBagConstraints(
                0,2,
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,25,0,0),
                0,0
        ));

        IntruderMainAsciiPanel.add(IntruderMainAsciiSeparator2Field, new GridBagConstraints(
                1,2,
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        IntruderMainAsciiPanel.add(IntruderMainAsciiTypeBox,new GridBagConstraints(
                1,3,
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        IntruderMainAsciiPanel.add(Helper.blackPanel(Color.WHITE), new GridBagConstraints(
                2,1,
                1,3,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        IntruderMainAsciiPanel.add(Helper.blackPanel(Color.WHITE), new GridBagConstraints(
                0,4,
                3,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return IntruderMainAsciiPanel;
    }

    public JComponent IntruderMainUnicodePanel() {

        JComponent IntruderMainUnicodePanel = new JPanel(new GridBagLayout());
        IntruderMainUnicodePanel.setBackground(Color.WHITE);
        IntruderMainUnicodePanel.setName("IntruderMainUnicodePanel");
        IntruderMainUnicodePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.gray), "Unicode"));
        IntruderMainUnicodePanel.setPreferredSize(new Dimension(0, 100));

        DiyJComboBox<String> IntruderMainUnicodeTypeBox = new DiyJComboBox<>(new String[]{"Encrypt","Decrypt"});
        IntruderMainUnicodeTypeBox.setName("IntruderMainUnicodeTypeBox");
        IntruderMainUnicodeTypeBox.setPreferredSize(new Dimension(120,30));

        IntruderMainUnicodePanel.add(Helper.blackPanel(Color.WHITE),new GridBagConstraints(
                0,0,
                1,1,
                0,0.5,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        IntruderMainUnicodePanel.add(IntruderMainUnicodeTypeBox,new GridBagConstraints(
                0,1,
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,50,0,0),
                0,0
        ));

        IntruderMainUnicodePanel.add(Helper.blackPanel(Color.WHITE),new GridBagConstraints(
                0,2,
                1,1,
                0,0.5,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        IntruderMainUnicodePanel.add(Helper.blackPanel(Color.WHITE),new GridBagConstraints(
                1,0,
                1,3,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return IntruderMainUnicodePanel;
    }


}
