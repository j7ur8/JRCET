package jrcet.frame.Scanner.Fastjson;

import burp.api.montoya.ui.editor.HttpRequestEditor;
import burp.api.montoya.ui.editor.HttpResponseEditor;
import jrcet.diycomponents.DiyJComponent;
import jrcet.diycomponents.DiyVariablePanel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static burp.MyExtender.API;
import static burp.MyRegisterHttpHandler.*;
import static jrcet.frame.Scanner.Fastjson.Fastjson.getFastjsonRequestNumber;

public class FastjsonComponent extends DiyJComponent {

    public static JComponent FastjsonComponentPanel = null;
    public static HttpRequestEditor FastjsonRawRequestEditor = API.userInterface().createHttpRequestEditor();
    public static HttpResponseEditor FastjsonRawResponseEditor = API.userInterface().createHttpResponseEditor();

    public static HttpRequestEditor FastjsonVulRequestEditor = API.userInterface().createHttpRequestEditor();
    public static HttpResponseEditor FastjsonVulResponseEditor = API.userInterface().createHttpResponseEditor();


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

        TableCellRenderer renderer = new DefaultTableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setEnabled(true);
                c.setForeground(Color.BLACK);
                ((DefaultTableCellRenderer) c).setHorizontalAlignment(SwingConstants.CENTER);
                if(FastjsonEntryMap.get((String) table.getValueAt(row,0)).getRemoved()){
                    c.setEnabled(false);
                } else if( (column==8 || column==9 || column==10) && table.getValueAt(row,column)!=""){
                    c.setForeground(Color.RED);
                }
                return c;
            }
        };

        Object[][] data = {};
        String[] columnNames = {"#","Tool","Method","Host","Path","Length","requestTime","responseTime","Dnslog","FastJson"};

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable FastjsonLoggerTable = new JTable(model){
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        FastjsonLoggerTable.setName("FastjsonLoggerTable");
        FastjsonLoggerTable.setDefaultRenderer(Object.class,renderer);
        FastjsonLoggerTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        FastjsonLoggerTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        FastjsonLoggerTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        FastjsonLoggerTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        FastjsonLoggerTable.getColumnModel().getColumn(3).setPreferredWidth(200);
        FastjsonLoggerTable.getColumnModel().getColumn(4).setPreferredWidth(300);
        FastjsonLoggerTable.getColumnModel().getColumn(5).setPreferredWidth(100);
        FastjsonLoggerTable.getColumnModel().getColumn(6).setPreferredWidth(150);
        FastjsonLoggerTable.getColumnModel().getColumn(7).setPreferredWidth(150);
        FastjsonLoggerTable.getColumnModel().getColumn(8).setPreferredWidth(100);
        FastjsonLoggerTable.getColumnModel().getColumn(9).setPreferredWidth(100);


        FastjsonLoggerTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = FastjsonLoggerTable.getSelectedRow();
            if (!e.getValueIsAdjusting() && selectedRow!=-1) {
                FastjsonTableEntry FastjsonTableEntry = FastjsonEntryMap.get(getFastjsonRequestNumber(selectedRow));
                FastjsonRawRequestEditor.setRequest(FastjsonTableEntry.getRawRequest());
                FastjsonRawResponseEditor.setResponse(FastjsonTableEntry.getSimplifyRawResponse());
                FastjsonVulRequestEditor.setRequest(FastjsonTableEntry.getFastjsonRequest());
                FastjsonVulResponseEditor.setResponse(FastjsonTableEntry.getSimplifyFastjsonResponse());
            }
        });

        JPopupMenu FastjsonLoggerTablePopupMenu = new JPopupMenu();

        JMenuItem delMenItem = new JMenuItem();
        delMenItem.setText("Remove From Scope");
        delMenItem.addActionListener(evt -> {
            int row = FastjsonLoggerTable.getSelectedRow();
            try{
                FastjsonCheckUrlList.remove(FastjsonEntryMap.get(getFastjsonRequestNumber(row)) .getRawRequest().url());
                FastjsonEntryMap.get(getFastjsonRequestNumber(row)).setRemoved(true);
                FastjsonRawRequestEditor.setRequest(null);
                FastjsonRawResponseEditor.setResponse(null);
                FastjsonVulRequestEditor.setRequest(null);
                FastjsonVulResponseEditor.setResponse(null);
            }catch (Exception e){
                API.logging().output().println(e);
            }

        });
        FastjsonLoggerTablePopupMenu.add(delMenItem);

        FastjsonLoggerTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(e.getButton()==3){
                    int focusedRowIndex = FastjsonLoggerTable.rowAtPoint(e.getPoint());
                    if (focusedRowIndex == -1) {
                        return;
                    }
                    //将表格所选项设为当前右键点击的行
                    FastjsonLoggerTable.setRowSelectionInterval(focusedRowIndex, focusedRowIndex);
                    FastjsonLoggerTablePopupMenu.show(FastjsonLoggerTable, e.getX(), e.getY());
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

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
        JPanel FastjsonMenuPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,0,0));
        FastjsonMenuPanel.setName("FastjsonMenuPanel");
        FastjsonMenuPanel.setPreferredSize(new Dimension(0,30));

        JCheckBox FastjsonMenuWorkBox = new JCheckBox("开启Fastjson检测");
        FastjsonMenuWorkBox.setName("FastjsonMenuWorkBox");

        FastjsonMenuPanel.add(FastjsonMenuWorkBox);

        return FastjsonMenuPanel;
    }




}
