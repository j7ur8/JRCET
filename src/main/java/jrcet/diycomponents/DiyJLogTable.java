package jrcet.diycomponents;

import burp.api.montoya.http.message.params.HttpParameter;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.http.message.responses.HttpResponse;
import jrcet.frame.Scanner.Fastjson.FastjsonTableEntry;
import jrcet.frame.Scanner.Overauth.OverauthTableEntry;
import jrcet.frame.Scanner.Springboot.SpringbootTableEntry;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


import static jrcet.frame.Scanner.Fastjson.Fastjson.*;
import static jrcet.frame.Scanner.Fastjson.FastjsonComponent.*;
import static jrcet.frame.Scanner.Overauth.Overauth.*;
import static jrcet.frame.Scanner.Overauth.OverauthComponent.*;
import static jrcet.frame.Scanner.Springboot.Springboot.*;
import static jrcet.frame.Scanner.Springboot.SpringbootComponent.*;


public class DiyJLogTable extends JTable {

    private TableModel tableModel = null;

    public DiyJLogTable(TableModel model){
        super(model);

        this.tableModel = model;

        addPopupMenu();

        getSelectionModel().addListSelectionListener(this::listSelectionAction);

        setDefaultRenderer(Object.class, setCellRenderer());

        setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }


    private TableCellRenderer setCellRenderer(){
        return new DefaultTableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                c.setEnabled(true);
                c.setForeground(Color.BLACK);
                ((DefaultTableCellRenderer) c).setHorizontalAlignment(SwingConstants.CENTER);

                switch (table.getName()){
                    case "OverauthLoggerTable" -> {
                        if(OverauthLoggerTableEntryMap.get((String) getValueAt(row,0)).getRemoved()){
                            c.setEnabled(false);
                        } else if( (column==9 || column==10 || column==11) && getValueAt(row,column)!=""){
                            c.setForeground(Color.RED);
                        }
                    }
                    case "FastjsonLoggerTable" -> {
                        if(FastjsonLoggerTableEntryMap.get((String) getValueAt(row,0)).getRemoved()){
                            c.setEnabled(false);
                        } else if( column==10  && getValueAt(row,column)!=""){
                            c.setForeground(Color.RED);
                        }
                    }

                    case "SpringbootLoggerTable" -> {
                        if(SpringbootLoggerTableEntryMap.get((String) getValueAt(row,0)).getRemoved()){
                            c.setEnabled(false);
                        } else if((column==10)  && getValueAt(row,column)!=""){
                            c.setForeground(Color.RED);
                        }else if( column==5 && getValueAt(row, column).equals("404") ){
                            c.setEnabled(false);
                        }
                    }
                }

                return c;
            }
        };
    }

    private void addPopupMenu(){
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


    public int getRowByValue(Object value) {
        for (int i = tableModel.getRowCount() - 1; i >= 0; --i) {
            if (tableModel.getValueAt(i, 0).equals(value)) {
                return i;
            }
        }
        return -1;
    }


    public void removeFromScopeAction(){
        int row = getSelectedRow();
        String serialNumber = "";
        switch (getName()){
            case "OverauthLoggerTable" -> {
                String rowNumber = getOverAuthSerialNumber(row);
                OverauthCheckUrlList.remove(OverauthLoggerTableEntryMap.get(rowNumber).getHighAuthRequest().url());
                OverauthLoggerTableEntryMap.get(rowNumber).setRemoved(true);
                OverauthAuthHighauthRequestEditor.setRequest(null);
                OverauthAuthHighauthResponseEditor.setResponse(null);
                OverauthAuthLowauthRequestEditor.setRequest(null);
                OverauthAuthLowauthResponseEditor.setResponse(null);
                OverauthAuthUnauthRequestEditor.setRequest(null);
                OverauthAuthUnauthResponseEditor.setResponse(null);
            }
            case "FastjsonLoggerTable" -> {
                serialNumber = getFastjsonSerialNumber(row);
                FastjsonCheckUrlList.remove(FastjsonLoggerTableEntryMap.get(serialNumber).getRawRequest().url());
                FastjsonLoggerTableEntryMap.get(serialNumber).setRemoved(true);
                FastjsonRawRequestEditor.setRequest(null);
                FastjsonRawResponseEditor.setResponse(null);
                FastjsonVulRequestEditor.setRequest(null);
                FastjsonVulResponseEditor.setResponse(null);
            }
            case "SpringbootLoggerTable" -> {
                serialNumber = getSpringbootSerialNumber(row);
                SpringbootCheckedUrlList.remove(SpringbootLoggerTableEntryMap.get(serialNumber) .getRawRequest().url());
                SpringbootLoggerTableEntryMap.get(serialNumber).setRemoved(true);
                SpringbootRawRequestEditor.setRequest(null);
                SpringbootRawResponseEditor.setResponse(null);
            }
        }
    }

    private void listSelectionAction(ListSelectionEvent e){
        if(!e.getValueIsAdjusting()){
            return;
        }

        switch (getName()){
            case "OverauthLoggerTable" -> {
                OverauthTableEntry overauthTableEntry = OverauthLoggerTableEntryMap.get(getOverAuthSerialNumber(getSelectedRow()));

                HttpRequest highAuthRequest = overauthTableEntry.getHighAuthRequest();
                HttpResponse highAuthResponse = overauthTableEntry.getHighAuthResponse();

                HttpRequest lowAuthRequest = overauthTableEntry.getLowAuthRequest();
                HttpResponse lowAuthResponse = overauthTableEntry.getLowAuthResponse();

                HttpRequest unAuthRequest = overauthTableEntry.getUnAuthRequest();
                HttpResponse unAuthResponse = overauthTableEntry.getUnAuthResponse();

                OverauthAuthHighauthRequestEditor.setRequest(highAuthRequest);
                OverauthAuthHighauthResponseEditor.setResponse(highAuthResponse);
                OverauthAuthLowauthRequestEditor.setRequest(lowAuthRequest);
                OverauthAuthLowauthResponseEditor.setResponse(lowAuthResponse);
                OverauthAuthUnauthRequestEditor.setRequest(unAuthRequest);
                OverauthAuthUnauthResponseEditor.setResponse(unAuthResponse);

                DiyJList diyJList = getOverauthLoggerList();
                diyJList.removeAllString();
                for(HttpParameter httpParameter: overauthTableEntry.getHorizontalOverAuthParameters()){
                    diyJList.addString(httpParameter.name()+" = "+httpParameter.value());
                }
            }
            case "FastjsonLoggerTable" -> {
                FastjsonTableEntry fastjsonTableEntry = FastjsonLoggerTableEntryMap.get(getFastjsonSerialNumber(getSelectedRow()));
                FastjsonRawRequestEditor.setRequest(fastjsonTableEntry.getRawRequest());
                FastjsonRawResponseEditor.setResponse(fastjsonTableEntry.getRawResponse());
                FastjsonVulRequestEditor.setRequest(fastjsonTableEntry.getFastjsonRequest());
                FastjsonVulResponseEditor.setResponse(fastjsonTableEntry.getFastjsonResponse());
            }
            case "SpringbootLoggerTable" -> {
                SpringbootTableEntry springbootTableEntry = SpringbootLoggerTableEntryMap.get(getSpringbootSerialNumber(getSelectedRow()));

                SpringbootRawRequestEditor.setRequest(springbootTableEntry.getRawRequest());
                SpringbootRawResponseEditor.setResponse(springbootTableEntry.getRawResponse());
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
