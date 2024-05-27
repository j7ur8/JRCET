package jrcet.frame.Scanner.Fastjson;

import burp.api.montoya.ui.editor.HttpRequestEditor;
import burp.api.montoya.ui.editor.HttpResponseEditor;
import diycomponents.DiyJButton;
import diycomponents.DiyJComponent;
import diycomponents.DiyJLogTable;
import diycomponents.DiyVariablePanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import static burp.MyExtender.BurpAPI;

public class FastjsonComponent extends DiyJComponent {

    public static JComponent FastjsonComponentPanel = null;
    public static HttpRequestEditor FastjsonRawRequestEditor = BurpAPI.userInterface().createHttpRequestEditor();
    public static HttpResponseEditor FastjsonRawResponseEditor = BurpAPI.userInterface().createHttpResponseEditor();

    public static HttpRequestEditor FastjsonVulRequestEditor = BurpAPI.userInterface().createHttpRequestEditor();
    public static HttpResponseEditor FastjsonVulResponseEditor = BurpAPI.userInterface().createHttpResponseEditor();


    @Override
    public JComponent component() {

        FastjsonComponentPanel = new JPanel(new GridBagLayout());
        FastjsonComponentPanel.add(FastjsonMenuPanel(),new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        FastjsonComponentPanel.add(FastjsonVariablePanel(),new GridBagConstraints(
                0,1,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return FastjsonComponentPanel;
    }


    private JComponent FastjsonVariablePanel(){
        DiyVariablePanel FastjsonVariablePanel = new DiyVariablePanel(
                FastjsonLoggerPanel(), "FastjsonLoggerPanel",
                FastjsonViewPanel(), "FastjsonViewPanel", DiyVariablePanel.View.VERTICAL
        );
        FastjsonVariablePanel.setName("FastjsonVariablePanel");
        FastjsonVariablePanel.setBackground(Color.WHITE);
        FastjsonVariablePanel.setPreferredSize(new Dimension(0,0));

        return FastjsonVariablePanel;
    }

    private JComponent FastjsonLoggerPanel(){
        JPanel FastjsonLoggerPanel = new JPanel(new GridBagLayout());
        FastjsonLoggerPanel.setName("FastjsonLoggerPanel");
        FastjsonLoggerPanel.setBackground(Color.WHITE);

        Object[][] data = {};
        String[] columnNames = {"#","Tool","Method","Host","Path","Code","Length","requestTime","responseTime","DnsLog","FastJson"};

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        DiyJLogTable FastjsonLoggerTable = new DiyJLogTable(model);

        FastjsonLoggerTable.setName("FastjsonLoggerTable");
        FastjsonLoggerTable.setColumnPreferredWidth(0, 50);
        FastjsonLoggerTable.setColumnPreferredWidth(1, 75);
        FastjsonLoggerTable.setColumnPreferredWidth(2, 75);
        FastjsonLoggerTable.setColumnPreferredWidth(3, 200);
        FastjsonLoggerTable.setColumnPreferredWidth(4, 300);
        FastjsonLoggerTable.setColumnPreferredWidth(5, 75);
        FastjsonLoggerTable.setColumnPreferredWidth(6, 75);
        FastjsonLoggerTable.setColumnPreferredWidth(7, 150);
        FastjsonLoggerTable.setColumnPreferredWidth(8, 150);
        FastjsonLoggerTable.setColumnPreferredWidth(9, 350);
        FastjsonLoggerTable.setColumnPreferredWidth(10, 100);


        JScrollPane FastjsonLoggerTableScrollPane = new JScrollPane(FastjsonLoggerTable);
        FastjsonLoggerTableScrollPane.setName("FastjsonLoggerTableScrollPane");
        FastjsonLoggerTableScrollPane.setPreferredSize(new Dimension(0,0));

        FastjsonLoggerPanel.add(FastjsonLoggerTableScrollPane,new GridBagConstraints(
                0,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return FastjsonLoggerPanel;
    }

    private JComponent FastjsonViewPanel(){

        JPanel FastjsonViewPanel = new JPanel(new GridBagLayout());
        FastjsonViewPanel.setName("FastjsonViewPanel");
        FastjsonViewPanel.setBackground(Color.WHITE);


        JTabbedPane jTabbedPane = new JTabbedPane(JTabbedPane.TOP,JTabbedPane.WRAP_TAB_LAYOUT);
        jTabbedPane.add("Raw",FastjsonViewRawPanel());

        jTabbedPane.add("FastJson",FastjsonViewVulPanel());
        jTabbedPane.setSelectedIndex(1);
        FastjsonViewPanel.add(jTabbedPane,new GridBagConstraints(
                0,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));


        return FastjsonViewPanel;

    }

    public JComponent FastjsonViewRawPanel(){
        DiyVariablePanel FastjsonViewRawPanel = new DiyVariablePanel(
                FastjsonRawRequestEditor.uiComponent(),"FastjsonRawRequestEditor",
                FastjsonRawResponseEditor.uiComponent(), "FastjsonResponseEditor",
                DiyVariablePanel.View.HORIZONTAL
        );

        FastjsonViewRawPanel.setName("FastjsonViewRawPanel");
        FastjsonViewRawPanel.setPreferredSize(new Dimension(0,0));

        return FastjsonViewRawPanel;
    }

    public JComponent FastjsonViewVulPanel(){
        DiyVariablePanel FastjsonViewVulPanel = new DiyVariablePanel(
                FastjsonVulRequestEditor.uiComponent(),"FastjsonVulRequestEditor",
                FastjsonVulResponseEditor.uiComponent(), "FastjsonVulResponseEditor",
                DiyVariablePanel.View.HORIZONTAL
        );

        FastjsonViewVulPanel.setName("FastjsonViewVulPanel");
        FastjsonViewVulPanel.setPreferredSize(new Dimension(0,0));

        return FastjsonViewVulPanel;
    }

    private  JComponent FastjsonMenuPanel(){
        JPanel FastjsonMenuPanel = new JPanel(new GridBagLayout());
        FastjsonMenuPanel.setName("FastjsonMenuPanel");
        FastjsonMenuPanel.setPreferredSize(new Dimension(0,30));

        DiyJButton FastjsonMenuCheckButton = new DiyJButton("OFF");
        FastjsonMenuCheckButton.setName("FastjsonMenuCheckButton");
        FastjsonMenuCheckButton.setBackground(Color.RED);

        DiyJButton FastjsonMenuClearButton = new DiyJButton("Clear");
        FastjsonMenuClearButton.setName("FastjsonMenuClearButton");

        DiyJButton FastjsonMenuShowAllButton = new DiyJButton("ShowAll");
        FastjsonMenuShowAllButton.setName("FastjsonMenuShowAllButton");

        FastjsonMenuPanel.add(FastjsonMenuCheckButton,new GridBagConstraints(
                0,0,
                1,1,
                0.9,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        FastjsonMenuPanel.add(FastjsonMenuClearButton,new GridBagConstraints(
                1,0,
                1,1,
                0.1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return FastjsonMenuPanel;
    }

}
