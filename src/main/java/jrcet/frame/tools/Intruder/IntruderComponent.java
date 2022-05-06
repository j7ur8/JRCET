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

        IntruderComponentPanel.add(IntruderMainPane(),new GridBagConstraints(
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

    public JComponent IntruderMainPane(){

        JComponent IntruderMainPanel = new JPanel(new GridBagLayout());
        IntruderMainPanel.setName("IntruderMainPanel");
        IntruderMainPanel.setBackground(Color.WHITE);

        IntruderMainPanel.add(IntruderMainAesPanel(),new GridBagConstraints(
                0,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));


        JScrollPane IntruderMainPane = new JScrollPane(IntruderMainPanel);
        IntruderMainPane.setName("IntruderMainPane");

        return IntruderMainPane;
    }

    public JComponent IntruderMainAesPanel(){
        JComponent IntruderMainAesPanel = new JPanel(new GridBagLayout());
        IntruderMainAesPanel.setName("IntruderMainAesPanel");

        IntruderMainAesPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1,0,0,0,Color.gray),"Aes"));

//        DiyJLabel AesMainControlIvLabel = new DiyJLabel(" Iv:");
//        AesMainControlIvLabel.setName("AesMainControlIvLabel");
//        AesMainControlIvLabel.setPreferredSize(new Dimension(50,30));
//        JTextField AesMainControlIvField = new JTextField();
//        AesMainControlIvField.setName("AesMainControlIvField");
//        AesMainControlIvField.setPreferredSize(new Dimension(50,30));
//        DiyJComboBox<String> AesMainControlIvBox = new DiyJComboBox<>(new String[]{"Raw", "Base64", "Hex"});
//        AesMainControlIvBox.setName("AesMainControlIvBox");
//        AesMainControlIvBox.setPreferredSize(new Dimension(50,30));
//
//        DiyJLabel AesMainControlKeyLabel = new DiyJLabel("Key: ");
//        AesMainControlKeyLabel.setName("AesMainControlKeyLabel");
//        AesMainControlKeyLabel.setPreferredSize(new Dimension(50,30));
//        JTextField AesMainControlKeyField = new JTextField();
//        AesMainControlKeyField.setName("AesMainControlKeyField");
//        AesMainControlKeyField.setPreferredSize(new Dimension(50,30));
//        DiyJComboBox<String> AesMainControlKeyBox = new DiyJComboBox<>(new String[]{"Raw", "Base64", "Hex"});
//        AesMainControlKeyBox.setName("AesMainControlKeyBox");
//        AesMainControlKeyBox.setPreferredSize(new Dimension(50,30));


        return  IntruderMainAesPanel;
    }


    public JComponent IntruderMainHeadPanel(String separatorName){

        JComponent IntruderMainHeadPanel = new JPanel(new GridBagLayout());
        IntruderMainHeadPanel.setName("IntruderMainHeadPanel");
        IntruderMainHeadPanel.setBackground(Color.WHITE);

        DiyJLabel IntruderMainHeadLabel = new DiyJLabel(separatorName);
        IntruderMainHeadLabel.setName("IntruderMainHead"+separatorName+"Label");
        IntruderMainHeadLabel.setPreferredSize(new Dimension(40,10));

        IntruderMainHeadPanel.add(IntruderMainHeadSeparatorLine(),new GridBagConstraints(
                0,0,
                1,1,
                0.1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        IntruderMainHeadPanel.add(IntruderMainHeadLabel,new GridBagConstraints(
                1,0,
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        IntruderMainHeadPanel.add(IntruderMainHeadSeparatorLine(),new GridBagConstraints(
                2,0,
                1,1,
                0.9,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return IntruderMainHeadPanel;
    }

    public JComponent IntruderMainHeadSeparatorLine(){
        JSeparator IntruderMainHeadSeparator = new JSeparator();
        IntruderMainHeadSeparator.setBackground(Color.MAGENTA);
        IntruderMainHeadSeparator.setName("IntruderMainHeadSeparator");
        return  IntruderMainHeadSeparator;
    }

    public JComponent IntruderMainBorderPanel(){
        JComponent IntruderMainBorderPanel = new JPanel();
        IntruderMainBorderPanel.setName("IntruderMainBorderPanel");

        return IntruderMainBorderPanel;
    }

}
