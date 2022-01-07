package jrcet.frame.tools.Solibrary;

import jrcet.diycomponents.DiyJButton;
import jrcet.diycomponents.DiyJComponent;
import sun.awt.image.GifImageDecoder;

import javax.swing.*;
import java.awt.*;

public class SoLibraryComponent extends DiyJComponent {

    public static String databasePath="/Users/j7ur8/Documents/DocumentArchive/SoLibra.db";
    public static JComponent searchMenuPanel=searchMenuPanel();
    public static JComponent searchResultScrollPanel=searchResultScrollPanel();

    @Override
    public JComponent main() {
        JPanel SoLibraryPanel = new JPanel(new GridBagLayout());
        SoLibraryPanel.setOpaque(true);
        SoLibraryPanel.setBackground(Color.YELLOW);

        GridBagConstraints searchMenuPanelProperty = new GridBagConstraints();
        searchMenuPanelProperty.gridx=0;searchMenuPanelProperty.gridy=0;
        searchMenuPanelProperty.weightx=1;searchMenuPanelProperty.weighty=0;
        searchMenuPanelProperty.fill=GridBagConstraints.BOTH;
        SoLibraryPanel.add(searchMenuPanel,searchMenuPanelProperty);

        GridBagConstraints searchResultPanelScrollProperty = new GridBagConstraints();
        searchResultPanelScrollProperty.gridx=0;searchMenuPanelProperty.gridy=1;
        searchResultPanelScrollProperty.weightx=1;searchResultPanelScrollProperty.weighty=1;
        searchResultPanelScrollProperty.fill=GridBagConstraints.BOTH;
        SoLibraryPanel.add(searchResultScrollPanel,searchResultPanelScrollProperty);


        return SoLibraryPanel;
    }

    private static JComponent searchMenuPanel(){
        JPanel searchMenu = new JPanel(new FlowLayout(FlowLayout.RIGHT,5,5));
        searchMenu.setPreferredSize(new Dimension(0,30));

        JLabel databaseLabel = new JLabel("Database:");
        searchMenu.add(databaseLabel);
        JTextField databaseJTextField = new JTextField();
        databaseJTextField.setText(databasePath);
        databaseJTextField.setPreferredSize(new Dimension(300,20));
        searchMenu.add(databaseJTextField);

        DiyJButton setDatabaseButton = new DiyJButton("SetDatabase");
        setDatabaseButton.setPreferredSize(new Dimension(120,20));
        searchMenu.add(setDatabaseButton);

        JLabel nameLabel = new JLabel("姓名:");
        searchMenu.add(nameLabel);
        JTextField nameJTextField = new JTextField();
        nameJTextField.setPreferredSize(new Dimension(100,20));
        searchMenu.add(nameJTextField);

        JLabel cardLabel = new JLabel("身份证:");
        searchMenu.add(cardLabel);
        JTextField cardJTextField = new JTextField();
        cardJTextField.setPreferredSize(new Dimension(160,20));
        searchMenu.add(cardJTextField);

        JLabel phoneLabel = new JLabel("手机:");
        searchMenu.add(phoneLabel);
        JTextField phoneJTextField = new JTextField();
        phoneJTextField.setPreferredSize(new Dimension(100,20));
        searchMenu.add(phoneJTextField);

        DiyJButton searchButton = new DiyJButton("Search");
        searchButton.setPreferredSize(new Dimension(120,20));
        searchMenu.add(searchButton);


        return searchMenu;
    }

    private static JComponent searchResultScrollPanel(){

        JPanel searchResultPanel=new JPanel(new GridBagLayout());

        searchResultPanel.setOpaque(true);
        searchResultPanel.setBackground(Color.WHITE);

        JScrollPane searchResultScrollPanel = new JScrollPane(searchResultPanel);
        searchResultScrollPanel.setPreferredSize(new Dimension(0,0));

        return searchResultScrollPanel;
    }

}
