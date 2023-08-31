package jrcet.frame.Tools.Password;

import jrcet.diycomponents.DiyJComponent;
import jrcet.diycomponents.DiyJTextAreaScrollPane;
import jrcet.help.Helper;

import javax.swing.*;
import java.awt.*;

public class PasswordComponent extends DiyJComponent {

    public static JComponent PasswordComponentPanel = null;

    @Override
    public JComponent component() {
        PasswordComponentPanel = new JPanel(new GridBagLayout());
        PasswordComponentPanel.setName("CaptchaComponentPanel");
        PasswordComponentPanel.setBackground(Color.WHITE);
        PasswordComponentPanel.setBorder(BorderFactory.createMatteBorder(1,0,0,0, Helper.gray));

        PasswordComponentPanel.add(PasswordMainPanel(), new GridBagConstraints(
                0,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));


        return PasswordComponentPanel;
    }

    private JComponent PasswordMainPanel(){
        JComponent PasswordMainPanel = new JPanel(new GridBagLayout());
        PasswordMainPanel.setBackground(Color.WHITE);
        PasswordMainPanel.setName("PasswordMainPanel");


        PasswordMainPanel.add(PasswordMainTopPanel(), new GridBagConstraints(
                0,0,
                3,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        PasswordMainPanel.add(Helper.blackPanel(Color.WHITE), new GridBagConstraints(
                0,1,
                1,1,
                0.15,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));
        PasswordMainPanel.add(PasswordCentralPanel(), new GridBagConstraints(
                1,1,
                1,1,
                0.6,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        PasswordMainPanel.add(PasswordRightPanel(), new GridBagConstraints(
                2,1,
                1,1,
                0.25,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return PasswordMainPanel;
    }

    private JComponent PasswordMainTopPanel(){
        JComponent PasswordMainTopPanel = new JPanel(new GridBagLayout());
        PasswordMainTopPanel.setName("PasswordMainTopPanel");
        PasswordMainTopPanel.setPreferredSize(new Dimension(0,30));
        PasswordMainTopPanel.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Helper.gray));
        PasswordMainTopPanel.setBackground(Color.WHITE);

        JTextField PasswordMainTopNameField = new JTextField("");
        PasswordMainTopNameField.addKeyListener(new PasswordMainTopNameFieldKeyListener());
        PasswordMainTopNameField.setName("PasswordMainTopNameField");
        PasswordMainTopNameField.setPreferredSize(new Dimension(0,0));


        PasswordMainTopPanel.add(Helper.blackPanel(Color.WHITE), new GridBagConstraints(
                0,0,
                1,1,
                0.3,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        PasswordMainTopPanel.add(PasswordMainTopNameField, new GridBagConstraints(
                1,0,
                1,1,
                0.4,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        PasswordMainTopPanel.add(Helper.blackPanel(Color.WHITE), new GridBagConstraints(
                2,0,
                1,1,
                0.3,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return PasswordMainTopPanel;
    }

    private JComponent PasswordCentralPanel(){

        JComponent PasswordCentralPanel = new JPanel(new GridBagLayout());
        PasswordCentralPanel.setName("PasswordCentralPanel");
        PasswordCentralPanel.setPreferredSize(new Dimension(0,0));
        PasswordCentralPanel.setBackground(Color.WHITE);
        PasswordCentralPanel.setBorder(BorderFactory.createMatteBorder(0,1,0,0, Helper.gray));

        DiyJTextAreaScrollPane PasswordCentralDictAreaScrollPane = new DiyJTextAreaScrollPane("PasswordCentralDictArea");
        PasswordCentralDictAreaScrollPane.setPreferredSize(new Dimension(0,0));
        PasswordCentralDictAreaScrollPane.setName("PasswordCentralDictAreaScrollPane");

        PasswordCentralPanel.add(PasswordCentralDictAreaScrollPane, new GridBagConstraints(
                0,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return PasswordCentralPanel;

    }

    private JComponent PasswordRightPanel(){

        JComponent PasswordRightPanel = new JPanel(new GridBagLayout());
        PasswordRightPanel.setName("PasswordRightPanel");
        PasswordRightPanel.setPreferredSize(new Dimension(0,0));
        PasswordRightPanel.setBackground(Color.WHITE);

        JPanel PasswordRightRulesPanel = new JPanel(new GridBagLayout());
        PasswordRightRulesPanel.setName("PasswordRightRulesPanel");
        PasswordRightRulesPanel.setBackground(Color.WHITE);
        PasswordRightRulesPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1,0,0,0,Color.gray),"密码规则"));

        //规则1
        JCheckBox PasswordRightRulesLengthBox = new JCheckBox("密码长度");
        PasswordRightRulesLengthBox.setName("PasswordRightRulesLengthBox");
        PasswordRightRulesLengthBox.setSelected(true);
        JTextField PasswordRightRulesLengthField = new JTextField("8-31");
        PasswordRightRulesLengthField.setName("PasswordRightRulesLengthField");

        //规则
        JCheckBox PasswordRightRulesComplexity1Box = new JCheckBox("必须包含大写字母");
        PasswordRightRulesComplexity1Box.setName("PasswordRightRulesComplexity1Box");
        //规则
        JCheckBox PasswordRightRulesComplexity2Box = new JCheckBox("必须包含数字符号");
        PasswordRightRulesComplexity2Box.setName("PasswordRightRulesComplexity2Box");

        //规则
        JCheckBox PasswordRightRulesComplexity3Box = new JCheckBox("必须包含特殊符号");
        PasswordRightRulesComplexity3Box.setName("PasswordRightRulesComplexity3Box");

        //规则
        JCheckBox PasswordRightRulesAccount1Box = new JCheckBox("不能包含账号信息（大小写敏感）");
        PasswordRightRulesAccount1Box.setName("PasswordRightRulesAccount1Box");

        //规则
        JCheckBox PasswordRightRulesAccount2Box = new JCheckBox("不能包含账号信息（大小写不敏感）");
        PasswordRightRulesAccount2Box.setName("PasswordRightRulesAccount2Box");

        //规则
        JCheckBox PasswordRightRulesSequence1Box = new JCheckBox("不能包含连续字符（横向）");
        PasswordRightRulesSequence1Box.setName("PasswordRightRulesSequence1Box");

        //规则
        JCheckBox PasswordRightRulesSequence2Box = new JCheckBox("不能包含连续字符（斜向）");
        PasswordRightRulesSequence2Box.setName("PasswordRightRulesSequence2Box");

        PasswordRightRulesPanel.add(PasswordRightRulesLengthBox, new GridBagConstraints(
                0,0,
                1,1,
                0,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(5,0,0,0),
                0,0
        ));


        PasswordRightRulesPanel.add(PasswordRightRulesLengthField, new GridBagConstraints(
                1,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(5,0,0,0),
                0,0
        ));

        PasswordRightRulesPanel.add(PasswordRightRulesComplexity1Box, new GridBagConstraints(
                0,1,
                2,1,
                0,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(5,0,0,0),
                0,0
        ));

        PasswordRightRulesPanel.add(PasswordRightRulesComplexity2Box, new GridBagConstraints(
                0,2,
                2,1,
                0,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(5,0,0,0),
                0,0
        ));

        PasswordRightRulesPanel.add(PasswordRightRulesComplexity3Box, new GridBagConstraints(
                0,3,
                2,1,
                0,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(5,0,0,0),
                0,0
        ));

        PasswordRightRulesPanel.add(PasswordRightRulesAccount1Box, new GridBagConstraints(
                0,4,
                2,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(5,0,0,0),
                0,0
        ));

        PasswordRightRulesPanel.add(PasswordRightRulesAccount2Box, new GridBagConstraints(
                0,5,
                2,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(5,0,0,0),
                0,0
        ));

        PasswordRightRulesPanel.add(PasswordRightRulesSequence1Box, new GridBagConstraints(
                0,6,
                2,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(5,0,0,0),
                0,0
        ));


        PasswordRightRulesPanel.add(PasswordRightRulesSequence2Box, new GridBagConstraints(
                0,7,
                2,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(5,0,0,0),
                0,0
        ));

        //密码信息panel
        JPanel PasswordRightInformationPanel = new JPanel(new GridBagLayout());
        PasswordRightInformationPanel.setName("PasswordRightInformationPanel");
        PasswordRightInformationPanel.setBackground(Color.WHITE);
        PasswordRightInformationPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1,0,0,0,Color.gray),"密码信息"));

        JLabel PasswordRightAccountnameLabel = new JLabel("账户名：");
        PasswordRightAccountnameLabel.setName("PasswordRightAccountnameLabel");
        JTextField PasswordRightAccountnameField = new JTextField("");
        PasswordRightAccountnameField.setName("PasswordRightAccountnameField");

        //出生年月日
        JLabel PasswordRightBirthdayLabel = new JLabel("出生日：");
        PasswordRightBirthdayLabel.setName("PasswordRightBirthdayLabel");
        JTextField PasswordRightBirthdayField = new JTextField("");
        PasswordRightBirthdayField.setName("PasswordRightBirthdayField");

        //信息
        JLabel PasswordRightPhoneLabel = new JLabel("手机号：");
        PasswordRightPhoneLabel.setName("PasswordRightPhoneLabel");
        JTextField PasswordRightPhoneField = new JTextField("");
        PasswordRightPhoneField.setName("PasswordRightPhoneField");

        //身份证
        JLabel PasswordRightIdcardLabel = new JLabel("身份证：");
        PasswordRightIdcardLabel.setName("PasswordRightIdcardLabel");
        JTextField PasswordRightIdcardField = new JTextField("");
        PasswordRightIdcardField.setName("PasswordRightIdcardField");

        PasswordRightInformationPanel.add(PasswordRightAccountnameLabel, new GridBagConstraints(
                0,0,
                1,1,
                0,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(5,5,0,0),
                0,0
        ));

        PasswordRightInformationPanel.add(PasswordRightAccountnameField, new GridBagConstraints(
                1,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(5,0,0,0),
                0,0
        ));

        PasswordRightInformationPanel.add(PasswordRightBirthdayLabel, new GridBagConstraints(
                0,1,
                1,1,
                0,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(5,5,0,0),
                0,0
        ));

        PasswordRightInformationPanel.add(PasswordRightBirthdayField, new GridBagConstraints(
                1,1,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(5,0,0,0),
                0,0
        ));

        PasswordRightInformationPanel.add(PasswordRightPhoneLabel, new GridBagConstraints(
                0,2,
                1,1,
                0,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(5,5,0,0),
                0,0
        ));

        PasswordRightInformationPanel.add(PasswordRightPhoneField, new GridBagConstraints(
                1,2,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(5,0,0,0),
                0,0
        ));

        PasswordRightInformationPanel.add(PasswordRightIdcardLabel, new GridBagConstraints(
                0,3,
                1,1,
                0,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(5,5,0,0),
                0,0
        ));

        PasswordRightInformationPanel.add(PasswordRightIdcardField, new GridBagConstraints(
                1,3,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(5,0,0,0),
                0,0
        ));

        //保存地址panel
        JPanel PasswordRightSavePanel = new JPanel(new GridBagLayout());
        PasswordRightSavePanel.setName("PasswordRightSavePanel");
        PasswordRightSavePanel.setBackground(Color.WHITE);
        PasswordRightSavePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1,0,0,0,Color.gray),"保存地址"));

        JCheckBox PasswordRightSaveifLabel = new JCheckBox("是否自动保存");
        PasswordRightSaveifLabel.setName("PasswordRightSaveifLabel");
        //单文件地址
        JLabel PasswordRightSaveoneLabel = new JLabel("单用户：");
        PasswordRightSaveoneLabel.setName("PasswordRightSaveoneLabel");
        JTextField PasswordRightSaveoneField = new JTextField("/Users/j7ur8/Documents/GitHub/JDict/burpsuite");
        PasswordRightSaveoneField.setName("PasswordRightSaveoneField");

        //多文件地址
        JLabel PasswordRightSaveallLabel = new JLabel("多用户：");
        PasswordRightSaveallLabel.setName("PasswordRightSaveallLabel");
        JTextField PasswordRightSaveallField = new JTextField("/Users/j7ur8/Documents/GitHub/JDict/burpsuite");
        PasswordRightSaveallField.setName("PasswordRightSaveallField");

        PasswordRightSavePanel.add(PasswordRightSaveifLabel, new GridBagConstraints(
                0,0,
                2,1,
                0,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(5,5,0,0),
                0,0
        ));

        PasswordRightSavePanel.add(PasswordRightSaveoneLabel, new GridBagConstraints(
                0,1,
                1,1,
                0,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(5,5,0,0),
                0,0
        ));

        PasswordRightSavePanel.add(PasswordRightSaveoneField, new GridBagConstraints(
                1,1,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(5,0,0,0),
                0,0
        ));

        PasswordRightSavePanel.add(PasswordRightSaveallLabel, new GridBagConstraints(
                0,2,
                1,1,
                0,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(5,5,0,0),
                0,0
        ));

        PasswordRightSavePanel.add(PasswordRightSaveallField, new GridBagConstraints(
                1,2,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(5,0,0,0),
                0,0
        ));


        //主panel
        PasswordRightPanel.add(PasswordRightRulesPanel, new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(10,0,0,0),
                0,0
        ));

        PasswordRightPanel.add(PasswordRightInformationPanel, new GridBagConstraints(
                0,1,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(10,0,0,0),
                0,0
        ));

        PasswordRightPanel.add(PasswordRightSavePanel, new GridBagConstraints(
                0,2,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(10,0,0,0),
                0,0
        ));

        PasswordRightPanel.add(Helper.blackPanel(Color.WHITE), new GridBagConstraints(
                0,PasswordRightPanel.getComponents().length,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(10,0,0,0),
                0,0
        ));

        return PasswordRightPanel;

    }

}
