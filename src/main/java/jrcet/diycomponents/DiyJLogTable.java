package jrcet.diycomponents;


import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.http.message.responses.HttpResponse;
import jrcet.frame.Scanner.Fastjson.FastjsonTableEntry;
import jrcet.frame.Scanner.Overauth.OverauthTableEntry;
import jrcet.frame.Scanner.Springboot.SpringbootTableEntry;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


import static burp.MyRegisterHttpHandler.*;
import static jrcet.frame.Scanner.Fastjson.Fastjson.getFastjsonRequestNumber;
import static jrcet.frame.Scanner.Fastjson.FastjsonComponent.*;
import static jrcet.frame.Scanner.Overauth.Overauth.getOverAuthRequestNumber;
import static jrcet.frame.Scanner.Overauth.OverauthComponent.*;
import static jrcet.frame.Scanner.Springboot.Springboot.getSpringbootRequestNumber;
import static jrcet.frame.Scanner.Springboot.SpringbootComponent.*;


public class DiyJLogTable extends JTable {

    public DiyJLogTable(TableModel model){
        super(model);

        addPopupMenu();

        getSelectionModel().addListSelectionListener(e-> listSelectionAction());

        setDefaultRenderer(Object.class,setCellRenderer());

        setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }

    public TableCellRenderer setCellRenderer(){
        return new DefaultTableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                c.setEnabled(true);
                c.setForeground(Color.BLACK);
                ((DefaultTableCellRenderer) c).setHorizontalAlignment(SwingConstants.CENTER);

                switch (table.getName()){
                    case "OverauthLoggerTable" -> {
                        if(AuthCheckEntryMap.get((String) getValueAt(row,0)).Removed){
                            c.setEnabled(false);
                        } else if( (column==8 || column==9 || column==10) && getValueAt(row,column)!=""){
                            c.setForeground(Color.RED);
                        }
                    }
                    case "FastjsonLoggerTable" -> {
                        if(FastjsonEntryMap.get((String) getValueAt(row,0)).getRemoved()){
                            c.setEnabled(false);
                        } else if(  column==9  && getValueAt(row,column)!=""){
                            c.setForeground(Color.RED);
                        }
                    }

                    case "SpringbootLoggerTable" -> {
                        if(SpringbootEntryMap.get((String) getValueAt(row,0)).getRemoved()){
                            c.setEnabled(false);
                        } else if((column==9)  && getValueAt(row,column)!=""){
                            c.setForeground(Color.RED);
                        }
                    }
                }

                return c;
            }
        };
    }

    public void addPopupMenu(){
        JPopupMenu DiyJLogTablePopupMenu = new JPopupMenu();

        JMenuItem delMenItem = new JMenuItem();
        delMenItem.setText("Remove From Scope");
        delMenItem.addActionListener(evt -> removeFromScopeAction());

        DiyJLogTablePopupMenu.add(delMenItem);

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(e.getButton()==3){
                    int focusedRowIndex = rowAtPoint(e.getPoint());
                    if (focusedRowIndex == -1) {
                        return;
                    }
                    //将表格所选项设为当前右键点击的行
                    setRowSelectionInterval(focusedRowIndex, focusedRowIndex);
                    DiyJLogTablePopupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    public void setColumnPreferredWidth(int columnIndex, int width){
        getColumnModel().getColumn(columnIndex).setPreferredWidth(width);
    }

    public void removeFromScopeAction(){
        int row = getSelectedRow();
        String requestNumber = "";
        switch (getName()){
            case "OverauthLoggerTable" -> {
                String rowNumber = getOverAuthRequestNumber(row);
                AuthCheckUrlList.remove(AuthCheckEntryMap.get(rowNumber).HighAuthRequest.url());
                AuthCheckEntryMap.get(rowNumber).Removed=true;
                OverauthAuthHighauthRequestEditor.setRequest(null);
                OverauthAuthHighauthResponseEditor.setResponse(null);
                OverauthAuthLowauthRequestEditor.setRequest(null);
                OverauthAuthLowauthResponseEditor.setResponse(null);
                OverauthAuthUnauthRequestEditor.setRequest(null);
                OverauthAuthUnauthResponseEditor.setResponse(null);
            }
            case "FastjsonLoggerTable" -> {
                requestNumber = getFastjsonRequestNumber(row);
                FastjsonCheckUrlList.remove(FastjsonEntryMap.get(requestNumber) .getRawRequest().url());
                FastjsonEntryMap.get(requestNumber).setRemoved(true);
                FastjsonRawRequestEditor.setRequest(null);
                FastjsonRawResponseEditor.setResponse(null);
                FastjsonVulRequestEditor.setRequest(null);
                FastjsonVulResponseEditor.setResponse(null);
            }
            case "SpringbootLoggerTable" -> {
                requestNumber = getSpringbootRequestNumber(row);
                SpringbootCheckUrlList.remove(SpringbootEntryMap.get(requestNumber) .getRawRequest().url());
                SpringbootEntryMap.get(requestNumber).setRemoved(true);
                SpringbootRawRequestEditor.setRequest(null);
                SpringbootRawResponseEditor.setResponse(null);
            }
        }
    }

    public void listSelectionAction(){
        switch (getName()){
            case "OverauthLoggerTable" -> {
                OverauthTableEntry overauthTableEntry = AuthCheckEntryMap.get(getOverAuthRequestNumber(getSelectedRow()));
                OverauthAuthHighauthRequestEditor.setRequest(overauthTableEntry.HighAuthRequest);
                OverauthAuthHighauthResponseEditor.setResponse(overauthTableEntry.simplifyHighAuthResponse);
                OverauthAuthLowauthRequestEditor.setRequest(overauthTableEntry.LowAuthRequest);
                OverauthAuthLowauthResponseEditor.setResponse(overauthTableEntry.simplifyLowAuthResponse);
                OverauthAuthUnauthRequestEditor.setRequest(overauthTableEntry.UnAuthRequest);
                OverauthAuthUnauthResponseEditor.setResponse(overauthTableEntry.simplifyUnAuthResponse);
            }
            case "FastjsonLoggerTable" -> {
                FastjsonTableEntry fastjsonTableEntry = FastjsonEntryMap.get(getFastjsonRequestNumber(getSelectedRow()));
                FastjsonRawRequestEditor.setRequest(fastjsonTableEntry.getRawRequest());
                FastjsonRawResponseEditor.setResponse(fastjsonTableEntry.getSimplifyRawResponse());
                FastjsonVulRequestEditor.setRequest(fastjsonTableEntry.getFastjsonRequest());
                FastjsonVulResponseEditor.setResponse(fastjsonTableEntry.getSimplifyFastjsonResponse());
            }
            case "SpringbootLoggerTable" -> {
                SpringbootTableEntry springbootTableEntry = SpringbootEntryMap.get(getSpringbootRequestNumber(getSelectedRow()));

                SpringbootRawRequestEditor.setRequest(springbootTableEntry.getRawRequest());
                SpringbootRawResponseEditor.setResponse(springbootTableEntry.getSimplifyRawResponse());
            }
        }
    }

    public static void main(String[] args) {
        Object[][] data = {{"1","2","3"},{"11","22","33"}};
        String[] columnNames = {"#","Tool","Method"};

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        DiyJLogTable logTable = new DiyJLogTable(model);

        JFrame JrcetFrame = new JFrame("J7ur8's Remote Code Execute Tools");
        JPanel a = new JPanel(new GridBagLayout());
        a.setPreferredSize(new Dimension(1200,1000));

        a.add(logTable,new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        JrcetFrame.setContentPane(a);

        JrcetFrame.setResizable(true);
        JrcetFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JrcetFrame.setSize(1200, 1000);
        JrcetFrame.setVisible(true);

    }


}
