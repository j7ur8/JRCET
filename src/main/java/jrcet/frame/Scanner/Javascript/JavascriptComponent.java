package jrcet.frame.Scanner.Javascript;

import burp.api.montoya.ui.editor.HttpResponseEditor;
import burp.api.montoya.ui.editor.RawEditor;
import jrcet.diycomponents.DiyJButton;
import jrcet.diycomponents.DiyJComponent;
import jrcet.diycomponents.DiyJLogTable;
import jrcet.diycomponents.DiyVariablePanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

import static burp.MyExtender.BurpAPI;

public class JavascriptComponent extends DiyJComponent {

    public static JComponent JavascriptComponentPanel = null;

    public static RawEditor JavascriptResultEditor = BurpAPI.userInterface().createRawEditor();

    @Override
    public JComponent component() {

        JavascriptComponentPanel = new JPanel(new GridBagLayout());

        JavascriptComponentPanel.add(JavascriptMenuPanel(),new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        JavascriptComponentPanel.add(JavascriptVariablePanel(),new GridBagConstraints(
                0,1,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return JavascriptComponentPanel;
    }

    private  JComponent JavascriptMenuPanel(){
        JPanel JavascriptMenuPanel = new JPanel(new GridBagLayout());
        JavascriptMenuPanel.setName("JavascriptMenuPanel");
        JavascriptMenuPanel.setPreferredSize(new Dimension(0,30));

        DiyJButton JavascriptMenuCheckButton = new DiyJButton("OFF");
        JavascriptMenuCheckButton.setName("JavascriptMenuCheckButton");
        JavascriptMenuCheckButton.setBackground(Color.RED);

        DiyJButton JavascriptMenuClearButton = new DiyJButton("Clear");
        JavascriptMenuClearButton.setName("JavascriptMenuClearButton");

        JavascriptMenuPanel.add(JavascriptMenuCheckButton,new GridBagConstraints(
                0,0,
                1,1,
                0.9,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        JavascriptMenuPanel.add(JavascriptMenuClearButton,new GridBagConstraints(
                1,0,
                1,1,
                0.1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return JavascriptMenuPanel;
    }

    private JComponent JavascriptVariablePanel(){
        DiyVariablePanel JavascriptVariablePanel = new DiyVariablePanel(
                JavascriptLoggerPanel(), "JavascriptLoggerPanel",
                JavascriptViewPanel(), "JavascriptViewPanel", DiyVariablePanel.View.VERTICAL
        );
        JavascriptVariablePanel.setName("JavascriptVariablePanel");
        JavascriptVariablePanel.setBackground(Color.WHITE);
        JavascriptVariablePanel.setPreferredSize(new Dimension(0,0));

        return JavascriptVariablePanel;
    }

    private JComponent JavascriptViewPanel(){
        JavascriptResultEditor.setEditable(false);
        JPanel JavascriptViewPanel = new JPanel(new GridBagLayout());
        JavascriptViewPanel.setPreferredSize(new Dimension(0,0));
        JavascriptViewPanel.add(JavascriptResultEditor.uiComponent(), new GridBagConstraints(
                0,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));
        
        return JavascriptViewPanel;
    }
    
    private JComponent JavascriptLoggerPanel(){
        JPanel JavascriptLoggerPanel = new JPanel(new GridBagLayout());
        JavascriptLoggerPanel.setName("JavascriptLoggerPanel");
        JavascriptLoggerPanel.setBackground(Color.WHITE);

        Object[][] data = {};
        String[] columnNames = {"#","URL","IDCard","PrivateKey","Password","AccessKey","Token","APPID","APPKey","WebHook"};

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        DiyJLogTable JavascriptLoggerTable = new DiyJLogTable(model);

        JavascriptLoggerTable.setName("JavascriptLoggerTable");
        JavascriptLoggerTable.setColumnPreferredWidth(0, 50);
        JavascriptLoggerTable.setColumnPreferredWidth(1, 275);
        JavascriptLoggerTable.setColumnPreferredWidth(2, 75);
        JavascriptLoggerTable.setColumnPreferredWidth(3, 75);
        JavascriptLoggerTable.setColumnPreferredWidth(4, 75);
        JavascriptLoggerTable.setColumnPreferredWidth(5, 75);
        JavascriptLoggerTable.setColumnPreferredWidth(6, 75);
        JavascriptLoggerTable.setColumnPreferredWidth(7, 75);
        JavascriptLoggerTable.setColumnPreferredWidth(8, 75);
        JavascriptLoggerTable.setColumnPreferredWidth(9, 75);


        JScrollPane JavascriptLoggerTableScrollPane = new JScrollPane(JavascriptLoggerTable);
        JavascriptLoggerTableScrollPane.setName("JavascriptLoggerTableScrollPane");
        JavascriptLoggerTableScrollPane.setPreferredSize(new Dimension(0,0));

        JavascriptLoggerPanel.add(JavascriptLoggerTableScrollPane,new GridBagConstraints(
                0,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return JavascriptLoggerPanel;
    }
    
}
