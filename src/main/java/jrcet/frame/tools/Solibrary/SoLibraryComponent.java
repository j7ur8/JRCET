package jrcet.frame.tools.Solibrary;

import jrcet.diycomponents.DiyJButton;
import jrcet.diycomponents.DiyJComponent;

import javax.swing.*;
import java.awt.*;

public class SoLibraryComponent extends DiyJComponent {

    public static String databasePath="";
    public static JPanel SoLibraryComponentPanel = null;

    @Override
    public JComponent main() {

        SoLibraryComponentPanel = new JPanel(new GridBagLayout());
        SoLibraryComponentPanel.setName("SoLibraryComponentPanel");
        SoLibraryComponentPanel.setOpaque(false);
        SoLibraryComponentPanel.setBackground(Color.YELLOW);

        SoLibraryComponentPanel.add(SoLibraryMenuTabPanel(),new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));


        SoLibraryComponentPanel.add(SoLibraryResultScrollPanel(),new GridBagConstraints(
                0,1,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));


        return SoLibraryComponentPanel;
    }

    private JComponent SoLibraryMenuTabPanel(){

        JPanel SoLibraryMenuTabPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,5,5));
        SoLibraryMenuTabPanel.setName("SoLibraryMenuTabPanel");
        SoLibraryMenuTabPanel.setBackground(Color.WHITE);
        SoLibraryMenuTabPanel.setPreferredSize(new Dimension(0,30));

        JLabel SoLibraryMenuTabDatabaseLabel = new JLabel("Database:");
        SoLibraryMenuTabDatabaseLabel.setName("SoLibraryMenuTabDatabaseLabel");
        SoLibraryMenuTabPanel.add(SoLibraryMenuTabDatabaseLabel);

        JTextField SoLibraryMenuTabDatabaseJTextField = new JTextField();
        SoLibraryMenuTabDatabaseJTextField.setText(databasePath);
        SoLibraryMenuTabDatabaseJTextField.setPreferredSize(new Dimension(300,20));
        SoLibraryMenuTabPanel.add(SoLibraryMenuTabDatabaseJTextField);

        DiyJButton SoLibraryMenuTabSetDBButton = new DiyJButton("SetDatabase");
        SoLibraryMenuTabSetDBButton.setPreferredSize(new Dimension(120,20));
        SoLibraryMenuTabPanel.add(SoLibraryMenuTabSetDBButton);

        JLabel SoLibraryMenuTabNameLabel = new JLabel("姓名:");
        SoLibraryMenuTabPanel.add(SoLibraryMenuTabNameLabel);

        JTextField SoLibraryMenuTabNameJTextField = new JTextField();
        SoLibraryMenuTabNameJTextField.setPreferredSize(new Dimension(100,20));
        SoLibraryMenuTabPanel.add(SoLibraryMenuTabNameJTextField);

        JLabel SoLibraryMenuTabCardLabel = new JLabel("身份证:");
        SoLibraryMenuTabPanel.add(SoLibraryMenuTabCardLabel);

        JTextField SoLibraryMenuTabCardLabelJTextField = new JTextField();
        SoLibraryMenuTabCardLabelJTextField.setPreferredSize(new Dimension(160,20));
        SoLibraryMenuTabPanel.add(SoLibraryMenuTabCardLabelJTextField);

        JLabel SoLibraryMenuTabPhoneLabel = new JLabel("手机:");
        SoLibraryMenuTabPanel.add(SoLibraryMenuTabPhoneLabel);

        JTextField SoLibraryMenuTabPhoneJTextField = new JTextField();
        SoLibraryMenuTabPhoneJTextField.setPreferredSize(new Dimension(100,20));
        SoLibraryMenuTabPanel.add(SoLibraryMenuTabPhoneJTextField);

        DiyJButton SoLibraryMenuTabSearchButton = new DiyJButton("Search");
        SoLibraryMenuTabSearchButton.setPreferredSize(new Dimension(120,20));
        SoLibraryMenuTabPanel.add(SoLibraryMenuTabSearchButton);

        return SoLibraryMenuTabPanel;
    }

    private JComponent SoLibraryResultScrollPanel(){

        JPanel SoLibraryResultPanel=new JPanel(new GridBagLayout());
        SoLibraryResultPanel.setName("SoLibraryResultPanel");
        SoLibraryResultPanel.setOpaque(false);
        SoLibraryResultPanel.setBackground(Color.WHITE);

        JScrollPane searchResultScrollPanel = new JScrollPane(SoLibraryResultPanel);
        searchResultScrollPanel.setPreferredSize(new Dimension(0,0));

        return searchResultScrollPanel;
    }

}
