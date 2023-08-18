package jrcet.frame.Scanner.Springboot;

import burp.api.montoya.ui.editor.HttpRequestEditor;
import burp.api.montoya.ui.editor.HttpResponseEditor;
import jrcet.diycomponents.DiyJComponent;
import jrcet.diycomponents.DiyJLogTable;
import jrcet.diycomponents.DiyVariablePanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


import static burp.MyExtender.API;


public class SpringbootComponent extends DiyJComponent {

    public static JComponent SpringbootComponentPanel = null;
    public static HttpRequestEditor SpringbootRawRequestEditor = API.userInterface().createHttpRequestEditor();
    public static HttpResponseEditor SpringbootRawResponseEditor = API.userInterface().createHttpResponseEditor();
//
//    public static HttpRequestEditor SpringbootSwaggerRequestEditor = API.userInterface().createHttpRequestEditor();
//    public static HttpResponseEditor SpringbootSwaggerResponseEditor = API.userInterface().createHttpResponseEditor();
//
//    public static HttpRequestEditor SpringbootDruidRequestEditor = API.userInterface().createHttpRequestEditor();
//    public static HttpResponseEditor SpringbootDruidResponseEditor = API.userInterface().createHttpResponseEditor();
//
//    public static HttpRequestEditor SpringbootActuatorRequestEditor = API.userInterface().createHttpRequestEditor();
//    public static HttpResponseEditor SpringbootActuatorResponseEditor = API.userInterface().createHttpResponseEditor();
//
//    public static HttpRequestEditor SpringbootDocRequestEditor = API.userInterface().createHttpRequestEditor();
//    public static HttpResponseEditor SpringbootDocResponseEditor = API.userInterface().createHttpResponseEditor();


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

        Object[][] data = {};
        String[] columnNames = {"#","Tool","Method","Host","Path","Length","requestTime","responseTime","Type","Vul"};

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        DiyJLogTable SpringbootLoggerTable = new DiyJLogTable(model);

        SpringbootLoggerTable.setName("SpringbootLoggerTable");
        SpringbootLoggerTable.setColumnPreferredWidth(0, 50);
        SpringbootLoggerTable.setColumnPreferredWidth(1, 75);
        SpringbootLoggerTable.setColumnPreferredWidth(2, 75);
        SpringbootLoggerTable.setColumnPreferredWidth(3, 200);
        SpringbootLoggerTable.setColumnPreferredWidth(4, 300);
        SpringbootLoggerTable.setColumnPreferredWidth(5, 75);
        SpringbootLoggerTable.setColumnPreferredWidth(6, 150);
        SpringbootLoggerTable.setColumnPreferredWidth(7, 150);
        SpringbootLoggerTable.setColumnPreferredWidth(8, 100);
        SpringbootLoggerTable.setColumnPreferredWidth(9, 100);
//        SpringbootLoggerTable.setColumnPreferredWidth(10, 100);
//        SpringbootLoggerTable.setColumnPreferredWidth(11, 100);


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

//    public JComponent SpringbootViewSwaggerPanel(){
//        DiyVariablePanel SpringbootViewSwaggerPanel = new DiyVariablePanel(
//                SpringbootSwaggerRequestEditor.uiComponent(),"SpringbootSwaggerRequestEditor",
//                SpringbootSwaggerResponseEditor.uiComponent(), "SpringbootSwaggerResponseEditor",
//                DiyVariablePanel.View.HORIZONTAL
//        );
//
//        SpringbootViewSwaggerPanel.setName("SpringbootViewSwaggerPanel");
//        SpringbootViewSwaggerPanel.setPreferredSize(new Dimension(0,0));
//
//        return SpringbootViewSwaggerPanel;
//    }

//    public JComponent SpringbootViewDruidPanel(){
//        DiyVariablePanel SpringbootViewDruidPanel = new DiyVariablePanel(
//                SpringbootDruidRequestEditor.uiComponent(),"SpringbootDruidRequestEditor",
//                SpringbootDruidResponseEditor.uiComponent(), "SpringbootDruidResponseEditor",
//                DiyVariablePanel.View.HORIZONTAL
//        );
//
//        SpringbootViewDruidPanel.setName("SpringbootViewDruidPanel");
//        SpringbootViewDruidPanel.setPreferredSize(new Dimension(0,0));
//
//        return SpringbootViewDruidPanel;
//    }
//
//    public JComponent SpringbootViewActuatorPanel(){
//        DiyVariablePanel SpringbootViewActuatorPanel = new DiyVariablePanel(
//                SpringbootActuatorRequestEditor.uiComponent(),"SpringbootActuatorRequestEditor",
//                SpringbootActuatorResponseEditor.uiComponent(), "SpringbootActuatorResponseEditor",
//                DiyVariablePanel.View.HORIZONTAL
//        );
//
//        SpringbootViewActuatorPanel.setName("SpringbootViewActuatorPanel");
//        SpringbootViewActuatorPanel.setPreferredSize(new Dimension(0,0));
//
//        return SpringbootViewActuatorPanel;
//    }
//
//    public JComponent SpringbootViewDocPanel(){
//        DiyVariablePanel SpringbootViewDocPanel = new DiyVariablePanel(
//                SpringbootDocRequestEditor.uiComponent(),"SpringbootDocRequestEditor",
//                SpringbootDocResponseEditor.uiComponent(), "SpringbootDocResponseEditor",
//                DiyVariablePanel.View.HORIZONTAL
//        );
//
//        SpringbootViewDocPanel.setName("SpringbootViewVulPanel");
//        SpringbootViewDocPanel.setPreferredSize(new Dimension(0,0));
//
//        return SpringbootViewDocPanel;
//    }

    private  JComponent SpringbootMenuPanel(){
        JPanel SpringbootMenuPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
        SpringbootMenuPanel.setName("SpringbootMenuPanel");
        SpringbootMenuPanel.setPreferredSize(new Dimension(0,30));

        JCheckBox SpringbootMenuWorkBox = new JCheckBox("开启Springboot检测");
        SpringbootMenuWorkBox.setPreferredSize(new Dimension(200,30));
        SpringbootMenuWorkBox.setName("SpringbootMenuWorkBox");

        SpringbootMenuPanel.add(SpringbootMenuWorkBox);

        return SpringbootMenuPanel;
    }




}
