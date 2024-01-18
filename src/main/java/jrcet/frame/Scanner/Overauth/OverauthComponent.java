package jrcet.frame.Scanner.Overauth;

import burp.api.montoya.ui.editor.HttpRequestEditor;
import burp.api.montoya.ui.editor.HttpResponseEditor;
import jrcet.diycomponents.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import static burp.MyExtender.BurpAPI;

public class OverauthComponent extends DiyJComponent {

    public static JComponent OverauthComponentPanel = null;
    public static HttpRequestEditor OverauthAuthHighauthRequestEditor = BurpAPI.userInterface().createHttpRequestEditor();
    public static HttpResponseEditor OverauthAuthHighauthResponseEditor = BurpAPI.userInterface().createHttpResponseEditor();

    public static HttpRequestEditor OverauthAuthLowauthRequestEditor = BurpAPI.userInterface().createHttpRequestEditor();
    public static HttpResponseEditor OverauthAuthLowauthResponseEditor = BurpAPI.userInterface().createHttpResponseEditor();

    public static HttpRequestEditor OverauthAuthUnauthRequestEditor = BurpAPI.userInterface().createHttpRequestEditor();
    public static HttpResponseEditor OverauthAuthUnauthResponseEditor = BurpAPI.userInterface().createHttpResponseEditor();


    @Override
    public JComponent component() {

        OverauthComponentPanel = new JPanel(new GridBagLayout());
        OverauthComponentPanel.add(OverauthMenuPanel(),new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        OverauthComponentPanel.add(OverauthMenu1Panel(),new GridBagConstraints(
                0,1,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        OverauthComponentPanel.add(OverauthVariablePanel(),new GridBagConstraints(
                0,2,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return OverauthComponentPanel;
    }


    private JComponent OverauthVariablePanel(){
        DiyVariablePanel OverauthVariablePanel = new DiyVariablePanel(
                OverauthLoggerPanel(), "OverauthLoggerPanel",
                OverauthViewPanel(), "OverauthViewPanel", DiyVariablePanel.View.VERTICAL
        );
        OverauthVariablePanel.setName("OverauthVariablePanel");
        OverauthVariablePanel.setBackground(Color.WHITE);
        OverauthVariablePanel.setPreferredSize(new Dimension(0,0));

        return OverauthVariablePanel;
    }

    private JComponent OverauthLoggerPanel(){
        JPanel OverauthLoggerPanel = new JPanel(new GridBagLayout());
        OverauthLoggerPanel.setName("OverauthLoggerPanel");
        OverauthLoggerPanel.setBackground(Color.WHITE);

        Object[][] data = {};
        String[] columnNames = {"#","Tool","Method","Host","Path","Code","Length","requestTime","responseTime","OverAuth","UnAuth"};
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        DiyJLogTable OverauthLoggerTable = new DiyJLogTable(model);

        OverauthLoggerTable.setName("OverauthLoggerTable");
        OverauthLoggerTable.setColumnPreferredWidth(0, 50);
        OverauthLoggerTable.setColumnPreferredWidth(1, 75);
        OverauthLoggerTable.setColumnPreferredWidth(2, 75);
        OverauthLoggerTable.setColumnPreferredWidth(3, 200);
        OverauthLoggerTable.setColumnPreferredWidth(4, 300);
        OverauthLoggerTable.setColumnPreferredWidth(5, 75);
        OverauthLoggerTable.setColumnPreferredWidth(6, 75);
        OverauthLoggerTable.setColumnPreferredWidth(7, 150);
        OverauthLoggerTable.setColumnPreferredWidth(8, 150);
        OverauthLoggerTable.setColumnPreferredWidth(9, 100);
        OverauthLoggerTable.setColumnPreferredWidth(10, 100);

        JScrollPane OverauthLoggerTableScrollPane = new JScrollPane(OverauthLoggerTable);
        OverauthLoggerTableScrollPane.setName("OverauthLoggerTableScrollPane");
        OverauthLoggerTableScrollPane.setPreferredSize(new Dimension(0,0));


        DiyJList OverauthLoggerList = new DiyJList();
        OverauthLoggerList.setName("OverauthLoggerList");
        OverauthLoggerList.setPreferredSize(new Dimension(0,0));
        OverauthLoggerList.setTitle("水平越权");

        OverauthLoggerPanel.add(OverauthLoggerTableScrollPane,new GridBagConstraints(
                0,0,
                1,1,
                0.8,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        OverauthLoggerPanel.add(OverauthLoggerList,new GridBagConstraints(
                1,0,
                1,1,
                0.1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));



        return OverauthLoggerPanel;
    }

    private JComponent OverauthViewPanel(){
        JPanel OverauthAuthPanel = new JPanel(new GridBagLayout());
        OverauthAuthPanel.setName("OverauthAuthPanel");
        OverauthAuthPanel.setBackground(Color.WHITE);


        JTabbedPane jTabbedPane = new JTabbedPane(JTabbedPane.TOP,JTabbedPane.WRAP_TAB_LAYOUT);
        jTabbedPane.add("高权限",OverauthAuthHighauthPanel());
        jTabbedPane.add("低权限",OverauthAuthLowauthPanel());
        jTabbedPane.add("未授权",OverauthAuthUnauthPanel());

        OverauthAuthPanel.add(jTabbedPane,new GridBagConstraints(
                0,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));


        return OverauthAuthPanel;
    }

    private  JComponent OverauthMenuPanel(){
        JPanel OverauthMenuPanel = new JPanel(new GridBagLayout());
        OverauthMenuPanel.setName("OverauthMenuPanel");
        OverauthMenuPanel.setPreferredSize(new Dimension(0,30));

        DiyJButton OverauthMenuCheckButton = new DiyJButton("OFF");
        OverauthMenuCheckButton.setBackground(Color.RED);
        OverauthMenuCheckButton.setName("OverauthMenuCheckButton");

        DiyJButton OverauthMenuClearButton = new DiyJButton("Clear");
        OverauthMenuClearButton.setName("OverauthMenuClearButton");

        OverauthMenuPanel.add(OverauthMenuCheckButton,new GridBagConstraints(
                0,0,
                1,1,
                0.9,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));
        OverauthMenuPanel.add(OverauthMenuClearButton,new GridBagConstraints(
                1,0,
                1,1,
                0.1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return OverauthMenuPanel;
    }

    private JComponent OverauthMenu1Panel(){
        JPanel OverauthMenu1Panel = new JPanel(new GridBagLayout());
        OverauthMenu1Panel.setBackground(Color.WHITE);
        OverauthMenu1Panel.setName("OverauthMenu1Panel");
        OverauthMenu1Panel.setPreferredSize(new Dimension(0,30));

        DiyJTextField  OverauthMenuHostField= new DiyJTextField("目标：");
        OverauthMenuHostField.setPreferredSize(new Dimension(0,0));
        OverauthMenuHostField.setName("OverauthMenuHostField");

        DiyJTextField  OverauthMenuHighauthField= new DiyJTextField("高权限：");
        OverauthMenuHighauthField.setPreferredSize(new Dimension(0,0));
        OverauthMenuHighauthField.setName("OverauthMenuHighauthField");

        DiyJTextField  OverauthMenuLowauthField= new DiyJTextField("低权限：");
        OverauthMenuLowauthField.setPreferredSize(new Dimension(0,0));
        OverauthMenuLowauthField.setName("OverauthMenuLowauthField");
        OverauthMenu1Panel.add(OverauthMenuHostField,new GridBagConstraints(
                0,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));
        OverauthMenu1Panel.add(OverauthMenuHighauthField,new GridBagConstraints(
                1,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));
        OverauthMenu1Panel.add(OverauthMenuLowauthField,new GridBagConstraints(
                2,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return OverauthMenu1Panel;

    }

    private JComponent OverauthAuthHighauthPanel(){
        JComponent OverauthAuthHighauthPanel = new DiyVariablePanel(
                OverauthAuthHighauthRequestEditor.uiComponent(), "HighRequest",
                OverauthAuthHighauthResponseEditor.uiComponent(), "HighResponse",
                DiyVariablePanel.View.HORIZONTAL
        );
        OverauthAuthHighauthPanel.setName("OverauthAuthHighauthPanel");
        OverauthAuthHighauthPanel.setBackground(Color.WHITE);
        OverauthAuthHighauthPanel.setPreferredSize(new Dimension(0,0));

        return OverauthAuthHighauthPanel;
    }

    private JComponent OverauthAuthLowauthPanel(){
        JComponent OverauthAuthLowauthPanel = new DiyVariablePanel(
                OverauthAuthLowauthRequestEditor.uiComponent(), "LowRequest",
                OverauthAuthLowauthResponseEditor.uiComponent(), "LowResponse",
                DiyVariablePanel.View.HORIZONTAL
        );
        OverauthAuthLowauthPanel.setName("OverauthAuthLowauthPanel");
        OverauthAuthLowauthPanel.setBackground(Color.WHITE);
        OverauthAuthLowauthPanel.setPreferredSize(new Dimension(0,0));

        return OverauthAuthLowauthPanel;
    }


    private JComponent OverauthAuthUnauthPanel(){
        JComponent OverauthAuthUnauthPanel = new DiyVariablePanel(
                OverauthAuthUnauthRequestEditor.uiComponent(), "LowRequest",
                OverauthAuthUnauthResponseEditor.uiComponent(), "LowResponse",
                DiyVariablePanel.View.HORIZONTAL
        );
        OverauthAuthUnauthPanel.setName("OverauthAuthHighauthPanel");
        OverauthAuthUnauthPanel.setBackground(Color.WHITE);
        OverauthAuthUnauthPanel.setPreferredSize(new Dimension(0,0));

        return OverauthAuthUnauthPanel;
    }

}
