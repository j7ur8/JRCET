package jrcet.frame.Scanner.Overauth;

import burp.api.montoya.ui.editor.HttpRequestEditor;
import burp.api.montoya.ui.editor.HttpResponseEditor;
import jrcet.diycomponents.DiyJComponent;
import jrcet.diycomponents.DiyJTabLabel;
import jrcet.diycomponents.DiyJTextField;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static burp.MyExtender.API;
import static burp.MyRegisterHttpHandler.*;

public class OverauthComponent extends DiyJComponent {

    public static JComponent OverauthComponent = null;
    public static HttpRequestEditor OverauthAuthHighauthRequestEditor = API.userInterface().createHttpRequestEditor();
    public static HttpResponseEditor OverauthAuthHighauthResponseEditor = API.userInterface().createHttpResponseEditor();

    public static HttpRequestEditor OverauthAuthLowauthRequestEditor = API.userInterface().createHttpRequestEditor();
    public static HttpResponseEditor OverauthAuthLowauthResponseEditor = API.userInterface().createHttpResponseEditor();

    public static HttpRequestEditor OverauthAuthUnauthRequestEditor = API.userInterface().createHttpRequestEditor();
    public static HttpResponseEditor OverauthAuthUnauthResponseEditor = API.userInterface().createHttpResponseEditor();


    private final JComponent OverauthAuthHighauthPanel = OverauthAuthHighauthPanel();

    private final JComponent OverauthAuthLowauthPanel = OverauthAuthLowauthPanel();

    private final JComponent OverauthAuthUnauthPanel = OverauthAuthUnauthPanel();

    @Override
    public JComponent main() {
        OverauthComponent = new JPanel(new GridBagLayout());
        OverauthComponent.setName("OverauthComponent");
        OverauthComponent.setBackground(Color.BLUE);

        OverauthComponent.add(OverauthLoggerPanel(),new GridBagConstraints(
                0,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        OverauthComponent.add(OverauthMenuPanel(),new GridBagConstraints(
                0,1,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));


        OverauthComponent.add(OverauthAuthPanel(),new GridBagConstraints(
                0,2,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return OverauthComponent;
    }

    private JComponent OverauthLoggerPanel(){
        JPanel OverauthLoggerPanel = new JPanel(new GridBagLayout());
        OverauthLoggerPanel.setName("OverauthLoggerPanel");
        OverauthLoggerPanel.setBackground(Color.WHITE);

        TableCellRenderer renderer = new DefaultTableCellRenderer() {

            private static final long serialVersionUID = 1L;

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                ((DefaultTableCellRenderer) c).setHorizontalAlignment(SwingConstants.CENTER);

                c.setEnabled(true);
                c.setForeground(Color.BLACK);
                if(RequestMap.get((String) table.getValueAt(row,0)).Removed){
                    c.setEnabled(false);
                } else if( (column==8 || column==9 || column==10) && table.getValueAt(row,column)!=""){
                        c.setForeground(Color.RED);
                }
                return c;
            }
        };

        Object[][] data = {};
        String[] columnNames = {"#","Tool","Method","Host","Path","Length","requestTime","responseTime","OverAuth","UnAuth","FlatAuth"};

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable OverauthLoggerTable = new JTable(model){
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };


        OverauthLoggerTable.setName("OverauthLoggerTable");
        OverauthLoggerTable.setDefaultRenderer(Object.class,renderer);
        OverauthLoggerTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        OverauthLoggerTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        OverauthLoggerTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        OverauthLoggerTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        OverauthLoggerTable.getColumnModel().getColumn(3).setPreferredWidth(200);
        OverauthLoggerTable.getColumnModel().getColumn(4).setPreferredWidth(300);
        OverauthLoggerTable.getColumnModel().getColumn(5).setPreferredWidth(100);
        OverauthLoggerTable.getColumnModel().getColumn(6).setPreferredWidth(150);
        OverauthLoggerTable.getColumnModel().getColumn(7).setPreferredWidth(150);
        OverauthLoggerTable.getColumnModel().getColumn(8).setPreferredWidth(100);
        OverauthLoggerTable.getColumnModel().getColumn(9).setPreferredWidth(100);
        OverauthLoggerTable.getColumnModel().getColumn(10).setPreferredWidth(200);


        OverauthLoggerTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = OverauthLoggerTable.getSelectedRow();
            if (!e.getValueIsAdjusting() && selectedRow!=-1) {
                OverauthTableEntry overauthTableEntry = RequestMap.get(getRequestNumber(selectedRow));
                OverauthAuthHighauthRequestEditor.setRequest(overauthTableEntry.HighAuthRequest);
                OverauthAuthHighauthResponseEditor.setResponse(overauthTableEntry.simplifyHighAuthResponse);
                OverauthAuthLowauthRequestEditor.setRequest(overauthTableEntry.LowAuthRequest);
                OverauthAuthLowauthResponseEditor.setResponse(overauthTableEntry.simplifyLowAuthResponse);
                OverauthAuthUnauthRequestEditor.setRequest(overauthTableEntry.UnAuthRequest);
                OverauthAuthUnauthResponseEditor.setResponse(overauthTableEntry.simplifyUnAuthResponse);
            }
        });

        JPopupMenu OverauthLoggerTablePopupMenu = new JPopupMenu();

        JMenuItem delMenItem = new JMenuItem();
        delMenItem.setText("Remove From Scope");
        delMenItem.addActionListener(evt -> {
            int row = OverauthLoggerTable.getSelectedRow();
            try{
                UrlList.remove(RequestMap.get(getRequestNumber(row)).HighAuthRequest.url());
                RequestMap.get(getRequestNumber(row)).Removed=true;
                OverauthAuthHighauthRequestEditor.setRequest(null);
                OverauthAuthHighauthResponseEditor.setResponse(null);
                OverauthAuthLowauthRequestEditor.setRequest(null);
                OverauthAuthLowauthResponseEditor.setResponse(null);
                OverauthAuthUnauthRequestEditor.setRequest(null);
                OverauthAuthUnauthResponseEditor.setResponse(null);
            }catch (Exception e){
                API.logging().output().println(e);
            }

        });
        OverauthLoggerTablePopupMenu.add(delMenItem);

        OverauthLoggerTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(e.getButton()==3){
                    int focusedRowIndex = OverauthLoggerTable.rowAtPoint(e.getPoint());
                    if (focusedRowIndex == -1) {
                        return;
                    }
                    //将表格所选项设为当前右键点击的行
                    OverauthLoggerTable.setRowSelectionInterval(focusedRowIndex, focusedRowIndex);
                    OverauthLoggerTablePopupMenu.show(OverauthLoggerTable, e.getX(), e.getY());
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        JScrollPane OverauthLoggerTableScrollPane = new JScrollPane(OverauthLoggerTable);
        OverauthLoggerTableScrollPane.setName("AssetMainBodyResultTableScrollPane");
        OverauthLoggerTableScrollPane.setPreferredSize(new Dimension(0,0));

        OverauthLoggerPanel.add(OverauthLoggerTableScrollPane,new GridBagConstraints(
                0,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));


        return OverauthLoggerPanel;
    }

    private JComponent OverauthAuthPanel(){
        JPanel OverauthAuthPanel = new JPanel(new GridBagLayout());
        OverauthAuthPanel.setName("OverauthAuthPanel");
        OverauthAuthPanel.setBackground(Color.WHITE);


        OverauthAuthPanel.add(OverauthAuthMenuPanel(),new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        OverauthAuthPanel.add(OverauthAuthHighauthPanel,new GridBagConstraints(
                0,1,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));


        return OverauthAuthPanel;
    }

    private  JComponent OverauthAuthMenuPanel(){
        JPanel OverauthAuthMenuPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,0,0));
        OverauthAuthMenuPanel.setName("OverauthAuthMenuPanel");

        DiyJTabLabel OverauthAuthMenuHighauthLabel = new DiyJTabLabel("高权限",true);
        OverauthAuthMenuHighauthLabel.setName("OverauthAuthMenuHighauthLabel");
        OverauthAuthMenuHighauthLabel.setPanel(OverauthAuthHighauthPanel);

        DiyJTabLabel OverauthAuthMenuLowauthLabel = new DiyJTabLabel("低权限");
        OverauthAuthMenuLowauthLabel.setName("OverauthAuthMenuHighauthLabel");
        OverauthAuthMenuLowauthLabel.setPanel(OverauthAuthLowauthPanel);

        DiyJTabLabel OverauthAuthMenuUnauthLabel = new DiyJTabLabel("未授权");
        OverauthAuthMenuUnauthLabel.setName("OverauthAuthMenuHighauthLabel");
        OverauthAuthMenuUnauthLabel.setPanel(OverauthAuthUnauthPanel);

        OverauthAuthMenuPanel.add(OverauthAuthMenuHighauthLabel);
        OverauthAuthMenuPanel.add(OverauthAuthMenuLowauthLabel);
        OverauthAuthMenuPanel.add(OverauthAuthMenuUnauthLabel);

        return OverauthAuthMenuPanel;

    }

    private JComponent OverauthAuthHighauthPanel(){
        JComponent OverauthAuthHighauthPanel = new JPanel(new GridBagLayout());
        OverauthAuthHighauthPanel.setName("OverauthAuthHighauthPanel");
        OverauthAuthHighauthPanel.setBackground(Color.WHITE);

        Component OverauthAuthHighauthRequestPanel = OverauthAuthHighauthRequestEditor.uiComponent();
        OverauthAuthHighauthRequestPanel.setPreferredSize(new Dimension(0,0));

        Component OverauthAuthHighauthResponsePanel = OverauthAuthHighauthResponseEditor.uiComponent();
        OverauthAuthHighauthResponsePanel.setPreferredSize(new Dimension(0,0));


        OverauthAuthHighauthPanel.add(OverauthAuthHighauthRequestPanel,new GridBagConstraints(
                0,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        OverauthAuthHighauthPanel.add(OverauthAuthHighauthResponsePanel,new GridBagConstraints(
                1,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return OverauthAuthHighauthPanel;
    }

    private JComponent OverauthAuthLowauthPanel(){
        JComponent OverauthAuthLowauthPanel = new JPanel(new GridBagLayout());
        OverauthAuthLowauthPanel.setName("OverauthAuthLowauthPanel");
        OverauthAuthLowauthPanel.setBackground(Color.WHITE);

        Component OverauthAuthHighauthRequestPanel = OverauthAuthLowauthRequestEditor.uiComponent();
        OverauthAuthHighauthRequestPanel.setPreferredSize(new Dimension(0,0));

        Component OverauthAuthHighauthResponsePanel = OverauthAuthLowauthResponseEditor.uiComponent();
        OverauthAuthHighauthResponsePanel.setPreferredSize(new Dimension(0,0));


        OverauthAuthLowauthPanel.add(OverauthAuthHighauthRequestPanel,new GridBagConstraints(
                0,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        OverauthAuthLowauthPanel.add(OverauthAuthHighauthResponsePanel,new GridBagConstraints(
                1,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return OverauthAuthLowauthPanel;
    }


    private JComponent OverauthAuthUnauthPanel(){
        JComponent OverauthAuthUnauthPanel = new JPanel(new GridBagLayout());
        OverauthAuthUnauthPanel.setName("OverauthAuthHighauthPanel");
        OverauthAuthUnauthPanel.setBackground(Color.WHITE);

        Component OverauthAuthHighauthRequestPanel = OverauthAuthUnauthRequestEditor.uiComponent();
        OverauthAuthHighauthRequestPanel.setPreferredSize(new Dimension(0,0));

        Component OverauthAuthHighauthResponsePanel = OverauthAuthUnauthResponseEditor.uiComponent();
        OverauthAuthHighauthResponsePanel.setPreferredSize(new Dimension(0,0));


        OverauthAuthUnauthPanel.add(OverauthAuthHighauthRequestPanel,new GridBagConstraints(
                0,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        OverauthAuthUnauthPanel.add(OverauthAuthHighauthResponsePanel,new GridBagConstraints(
                1,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return OverauthAuthUnauthPanel;
    }

    private JComponent OverauthMenuPanel(){
        JPanel OverauthMenuPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
        OverauthMenuPanel.setName("OverauthMenuPanel");
        OverauthMenuPanel.setBackground(Color.WHITE);

        DiyJTextField  OverauthMenuHostField= new DiyJTextField("目标：");
        OverauthMenuHostField.setName("OverauthMenuHostField");
        OverauthMenuHostField.setPreferredSize(new Dimension(300,25));

        DiyJTextField  OverauthMenuHighauthField= new DiyJTextField("高权限：");
        OverauthMenuHighauthField.setName("OverauthMenuHighauthField");
        OverauthMenuHighauthField.setPreferredSize(new Dimension(400,25));

        DiyJTextField  OverauthMenuLowauthField= new DiyJTextField("低权限：");
        OverauthMenuLowauthField.setName("OverauthMenuLowauthField");
        OverauthMenuLowauthField.setPreferredSize(new Dimension(400,25));

        OverauthMenuPanel.add(OverauthMenuHostField);
        OverauthMenuPanel.add(OverauthMenuHighauthField);
        OverauthMenuPanel.add(OverauthMenuLowauthField);

        return OverauthMenuPanel;
    }

}