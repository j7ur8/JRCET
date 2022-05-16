package jrcet.frame.tools.Intruder;

import jrcet.diycomponents.*;
import jrcet.diycomponents.DiyJTextArea.ui.rsyntaxtextarea.RSyntaxTextArea;
import jrcet.diycomponents.DiyJTextArea.ui.rsyntaxtextarea.SyntaxConstants;
import jrcet.diycomponents.DiyJTextArea.ui.rtextarea.RTextScrollPane;
import jrcet.lib.Helper;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class IntruderComponent extends DiyJComponent {

    public static JComponent IntruderComponentPanel = null;
    public static ArrayList<JComponent> IntruderModuleComponentList = new ArrayList<>();


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

        IntruderMainPanel.add(IntruderMainControlPanel(), new GridBagConstraints(
                0, IntruderMainPanel.getComponentCount(),
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,100,0,100),
                0,0
        ));

//        IntruderMainPanel.add(IntruderMainAesPanel(), new GridBagConstraints(
//                0, IntruderMainPanel.getComponentCount(),
//                1,1,
//                1,0,
//                GridBagConstraints.CENTER,
//                GridBagConstraints.BOTH,
//                new Insets(0,100,0,100),
//                0,0
//        ));
//
//        IntruderMainPanel.add(IntruderMainRsaPanel(), new GridBagConstraints(
//                0,IntruderMainPanel.getComponentCount(),
//                1,1,
//                1,0,
//                GridBagConstraints.CENTER,
//                GridBagConstraints.BOTH,
//                new Insets(0,100,0,100),
//                0,0
//        ));
//
//        IntruderMainPanel.add(IntruderMainBasePanel(), new GridBagConstraints(
//                0,IntruderMainPanel.getComponentCount(),
//                1,1,
//                1,0,
//                GridBagConstraints.CENTER,
//                GridBagConstraints.BOTH,
//                new Insets(0,100,0,100),
//                0,0
//        ));
//
//        IntruderMainPanel.add(IntruderMainAsciiPanel(), new GridBagConstraints(
//                0,IntruderMainPanel.getComponentCount(),
//                1,1,
//                1,0,
//                GridBagConstraints.CENTER,
//                GridBagConstraints.BOTH,
//                new Insets(0,100,0,100),
//                0,0
//        ));
//
//        IntruderMainPanel.add(IntruderMainUnicodePanel(), new GridBagConstraints(
//                0,IntruderMainPanel.getComponentCount(),
//                1,1,
//                1,0,
//                GridBagConstraints.CENTER,
//                GridBagConstraints.BOTH,
//                new Insets(0,100,0,100),
//                0,0
//        ));
        IntruderMainPanel.add(Helper.blackPanel(), new GridBagConstraints(
                0,IntruderMainPanel.getComponentCount(),
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

    public JComponent IntruderMainControlPanel(){

        JComponent IntruderMainControlPanel = new JPanel(new GridBagLayout());
        IntruderMainControlPanel.setName("IntruderMainControlPanel");
        IntruderMainControlPanel.setBackground(Color.WHITE);
        IntruderMainControlPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1,0,0,0,Color.gray),"Control Center",TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));
        IntruderMainControlPanel.setPreferredSize(new Dimension(0,150));

        DiyJLabel IntruderMainControlShowLabel = new DiyJLabel("");
        IntruderMainControlShowLabel.setName("IntruderMainControlShowPanel");

        DiyJButton IntruderMainControlAesButton = new DiyJButton(("Aes"));
        IntruderMainControlAesButton.setName("IntruderMainControlAesButton");
        DiyJButton IntruderMainControlRsaButton = new DiyJButton(("Rsa"));
        IntruderMainControlRsaButton.setName("IntruderMainControlRsaButton");
        DiyJButton IntruderMainControlBaseButton = new DiyJButton(("Base"));
        IntruderMainControlBaseButton.setName("IntruderMainControlBaseButton");
        DiyJButton IntruderMainControlAsciiButton = new DiyJButton(("Ascii"));
        IntruderMainControlAsciiButton.setName("IntruderMainControlAsciiButton");
        DiyJButton IntruderMainControlUnicodeButton = new DiyJButton(("Unicode"));
        IntruderMainControlUnicodeButton.setName("IntruderMainControlUnicodeButton");
        DiyJButton IntruderMainControlClearButton = new DiyJButton(("Clear All"));
        IntruderMainControlClearButton.setName("IntruderMainControlClearButton");

        IntruderMainControlPanel.add(IntruderMainControlShowLabel, new GridBagConstraints(
                0,0,
                5,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        IntruderMainControlPanel.add(IntruderMainControlAesButton, new GridBagConstraints(
                IntruderMainControlPanel.getComponentCount()-1,1,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));
        IntruderMainControlPanel.add(IntruderMainControlRsaButton, new GridBagConstraints(
                IntruderMainControlPanel.getComponentCount()-1,1,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));
        IntruderMainControlPanel.add(IntruderMainControlBaseButton, new GridBagConstraints(
                IntruderMainControlPanel.getComponentCount()-1,1,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));
        IntruderMainControlPanel.add(IntruderMainControlAsciiButton, new GridBagConstraints(
                IntruderMainControlPanel.getComponentCount()-1,1,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));
        IntruderMainControlPanel.add(IntruderMainControlUnicodeButton, new GridBagConstraints(
                IntruderMainControlPanel.getComponentCount()-1,1,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        IntruderMainControlPanel.add(IntruderMainControlClearButton, new GridBagConstraints(
                0,2,
                5,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));


        return IntruderMainControlPanel;
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

        IntruderMainAesPanel.add(Helper.blackPanel() ,new GridBagConstraints(
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

        IntruderMainAesPanel.add(Helper.blackPanel(),new GridBagConstraints(
                0,4,
                3,1,
                0,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        IntruderMainAesPanel.add(Helper.blackPanel(),new GridBagConstraints(
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

        RSyntaxTextArea IntruderMainRsaPublicArea = new RSyntaxTextArea();
        IntruderMainRsaPublicArea.setName("IntruderMainRsaPublicArea");
        IntruderMainRsaPublicArea.setCodeFoldingEnabled(true);
        IntruderMainRsaPublicArea.setLineWrap(true);
        IntruderMainRsaPublicArea.setText("#Public Key");
        IntruderMainRsaPublicArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE);

        RTextScrollPane IntruderMainRsaPublicScrollPane = new RTextScrollPane(IntruderMainRsaPublicArea);
        IntruderMainRsaPublicScrollPane.setHorizontalScrollBarPolicy(RTextScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        IntruderMainRsaPublicScrollPane.setName("IntruderMainRsaPublicScrollPane");
        IntruderMainRsaPublicScrollPane.setPreferredSize(new Dimension(0,0));
        IntruderMainRsaPublicScrollPane.setBorder(BorderFactory.createMatteBorder(1,1,1,1, new Color(210,210,210)));

        RSyntaxTextArea IntruderMainRsaPrivateArea = new RSyntaxTextArea();
        IntruderMainRsaPrivateArea.setName("IntruderMainRsaPrivateArea");
        IntruderMainRsaPrivateArea.setCodeFoldingEnabled(true);
        IntruderMainRsaPrivateArea.setText("#Private Key");
        IntruderMainRsaPrivateArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE);

        RTextScrollPane IntruderMainRsaPrivateScrollPane = new RTextScrollPane(IntruderMainRsaPrivateArea);
        IntruderMainRsaPrivateScrollPane.setHorizontalScrollBarPolicy(RTextScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        IntruderMainRsaPrivateScrollPane.setName("IntruderMainRsaPrivateScrollPane");
        IntruderMainRsaPrivateScrollPane.setPreferredSize(new Dimension(0,0));
        IntruderMainRsaPrivateScrollPane.setBorder(BorderFactory.createMatteBorder(1,1,1,1, new Color(210,210,210)));

        DiyJComboBox<String> IntruderMainRsaTypeBox = new DiyJComboBox<>(new String[]{"Encrypt","Decrypt"});
        IntruderMainRsaTypeBox.setName("IntruderMainRsaTypeBox");
        IntruderMainRsaTypeBox.setPreferredSize(new Dimension(120,30));

        IntruderMainRsaPanel.add(IntruderMainRsaPublicScrollPane, new GridBagConstraints(
                0,0,
                1,3,
                0.5,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(5,10,10,5),
                0,0
        ));

        IntruderMainRsaPanel.add(IntruderMainRsaPrivateScrollPane, new GridBagConstraints(
                1,0,
                1,3,
                0.5,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(5,5,10,0),
                0,0
        ));

        IntruderMainRsaPanel.add(Helper.blackPanel(),new GridBagConstraints(
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

        IntruderMainRsaPanel.add(Helper.blackPanel(),new GridBagConstraints(
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

        IntruderMainBasePanel.add(Helper.blackPanel(),new GridBagConstraints(
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

        IntruderMainBasePanel.add(Helper.blackPanel(),new GridBagConstraints(
                0,2,
                2,1,
                0,0.5,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        IntruderMainBasePanel.add(Helper.blackPanel(),new GridBagConstraints(
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
        IntruderMainAsciiPanel.setPreferredSize(new Dimension(0, 100));

        DiyJComboBox<String> IntruderMainAsciiTypeBox = new DiyJComboBox<>(new String[]{"Encrypt","Decrypt"});
        IntruderMainAsciiTypeBox.setName("IntruderMainAsciiTypeBox");
        IntruderMainAsciiTypeBox.setPreferredSize(new Dimension(120,30));

        IntruderMainAsciiPanel.add(Helper.blackPanel(),new GridBagConstraints(
                0,0,
                1,1,
                0,0.5,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        IntruderMainAsciiPanel.add(IntruderMainAsciiTypeBox,new GridBagConstraints(
                0,1,
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,50,0,0),
                0,0
        ));

        IntruderMainAsciiPanel.add(Helper.blackPanel(),new GridBagConstraints(
                0,2,
                1,1,
                0,0.5,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        IntruderMainAsciiPanel.add(Helper.blackPanel(),new GridBagConstraints(
                1,0,
                1,1,
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

        IntruderMainUnicodePanel.add(Helper.blackPanel(),new GridBagConstraints(
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

        IntruderMainUnicodePanel.add(Helper.blackPanel(),new GridBagConstraints(
                0,2,
                1,1,
                0,0.5,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        IntruderMainUnicodePanel.add(Helper.blackPanel(),new GridBagConstraints(
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
