package jrcet.frame.Scanner.Springboot;

import burp.api.montoya.ui.editor.HttpRequestEditor;
import burp.api.montoya.ui.editor.HttpResponseEditor;
import jrcet.diycomponents.DiyJButton;
import jrcet.diycomponents.DiyJComponent;
import jrcet.diycomponents.DiyJLogTable;
import jrcet.diycomponents.DiyVariablePanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


import static burp.MyExtender.BurpAPI;


public class SpringbootComponent extends DiyJComponent {

    public static JComponent SpringbootComponentPanel = null;
    public static HttpRequestEditor SpringbootRawRequestEditor = BurpAPI.userInterface().createHttpRequestEditor();
    public static HttpResponseEditor SpringbootRawResponseEditor = BurpAPI.userInterface().createHttpResponseEditor();

    @Override
    public JComponent component() {

        SpringbootComponentPanel = new JPanel(new GridBagLayout());
        SpringbootComponentPanel.add(SpringbootMenuPanel(),new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        SpringbootComponentPanel.add(SpringbootVariablePanel(),new GridBagConstraints(
                0,1,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return SpringbootComponentPanel;
    }


    private JComponent SpringbootVariablePanel(){
        DiyVariablePanel SpringbootVariablePanel = new DiyVariablePanel(
                SpringbootLoggerPanel(), "SpringbootLoggerPanel",
                SpringbootViewPanel(), "SpringbootViewPanel", DiyVariablePanel.View.VERTICAL
        );
        SpringbootVariablePanel.setName("SpringbootVariablePanel");
        SpringbootVariablePanel.setBackground(Color.WHITE);
        SpringbootVariablePanel.setPreferredSize(new Dimension(0,0));

        return SpringbootVariablePanel;
    }

    private JComponent SpringbootLoggerPanel(){
        JPanel SpringbootLoggerPanel = new JPanel(new GridBagLayout());
        SpringbootLoggerPanel.setName("SpringbootLoggerPanel");
        SpringbootLoggerPanel.setBackground(Color.WHITE);

        DefaultTableModel model = new DefaultTableModel(new Object[][]{}, new String[]{"#","Tool","Method","Host","Path","Code","Length","requestTime","responseTime","Type","Vul"});
        DiyJLogTable SpringbootLoggerTable = new DiyJLogTable(model);

        SpringbootLoggerTable.setName("SpringbootLoggerTable");
        SpringbootLoggerTable.setColumnPreferredWidth(0, 50);
        SpringbootLoggerTable.setColumnPreferredWidth(1, 75);
        SpringbootLoggerTable.setColumnPreferredWidth(2, 75);
        SpringbootLoggerTable.setColumnPreferredWidth(3, 200);
        SpringbootLoggerTable.setColumnPreferredWidth(4, 300);
        SpringbootLoggerTable.setColumnPreferredWidth(5, 75);
        SpringbootLoggerTable.setColumnPreferredWidth(6, 75);
        SpringbootLoggerTable.setColumnPreferredWidth(7, 150);
        SpringbootLoggerTable.setColumnPreferredWidth(8, 150);
        SpringbootLoggerTable.setColumnPreferredWidth(9, 100);
        SpringbootLoggerTable.setColumnPreferredWidth(10, 100);

        JScrollPane SpringbootLoggerTableScrollPane = new JScrollPane(SpringbootLoggerTable);
        SpringbootLoggerTableScrollPane.setName("SpringbootLoggerTableScrollPane");
        SpringbootLoggerTableScrollPane.setPreferredSize(new Dimension(0,0));

        SpringbootLoggerPanel.add(SpringbootLoggerTableScrollPane,new GridBagConstraints(
                0,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return SpringbootLoggerPanel;
    }

    private JComponent SpringbootViewPanel(){

        JPanel SpringbootViewPanel = new JPanel(new GridBagLayout());
        SpringbootViewPanel.setName("SpringbootViewPanel");
        SpringbootViewPanel.setBackground(Color.WHITE);


        JTabbedPane jTabbedPane = new JTabbedPane(JTabbedPane.TOP,JTabbedPane.WRAP_TAB_LAYOUT);
        jTabbedPane.add("Raw",SpringbootViewRawPanel());


        SpringbootViewPanel.add(jTabbedPane,new GridBagConstraints(
                0,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));


        return SpringbootViewPanel;
    }

    public JComponent SpringbootViewRawPanel(){
        DiyVariablePanel SpringbootViewRawPanel = new DiyVariablePanel(
                SpringbootRawRequestEditor.uiComponent(),"SpringbootRawRequestEditor",
                SpringbootRawResponseEditor.uiComponent(), "SpringbootResponseEditor",
                DiyVariablePanel.View.HORIZONTAL
        );

        SpringbootViewRawPanel.setName("SpringbootViewRawPanel");
        SpringbootViewRawPanel.setPreferredSize(new Dimension(0,0));

        return SpringbootViewRawPanel;
    }


    private  JComponent SpringbootMenuPanel(){
        JPanel SpringbootMenuPanel = new JPanel(new GridBagLayout());
        SpringbootMenuPanel.setName("SpringbootMenuPanel");
        SpringbootMenuPanel.setPreferredSize(new Dimension(0,30));

        DiyJButton SpringbootMenuCheckButton = new DiyJButton("OFF");
        SpringbootMenuCheckButton.setName("SpringbootMenuCheckButton");
        SpringbootMenuCheckButton.setBackground(Color.RED);

        DiyJButton SpringbootMenuClearButton = new DiyJButton("Clear");
        SpringbootMenuClearButton.setName("SpringbootMenuClearButton");

        SpringbootMenuPanel.add(SpringbootMenuCheckButton,new GridBagConstraints(
                0,0,
                1,1,
                0.9,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        SpringbootMenuPanel.add(SpringbootMenuClearButton,new GridBagConstraints(
                1,0,
                1,1,
                0.1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return SpringbootMenuPanel;
    }


}
