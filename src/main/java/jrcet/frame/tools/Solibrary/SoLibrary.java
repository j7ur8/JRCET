package jrcet.frame.tools.Solibrary;

import burp.BurpExtender;
import jrcet.diycomponents.TableColumnAdjuster;
import org.sqlite.SQLiteConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class SoLibrary {
    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            StackTraceElement[] a = e.getStackTrace();
            for (StackTraceElement stackTraceElement : a) {
                BurpExtender.stdout.println(
                        stackTraceElement.getClassName()+" "+
                                stackTraceElement.getFileName()+" "+
                                stackTraceElement.getLineNumber()+" "+
                                stackTraceElement.getMethodName());
            }
        }
    }

    public static void Search(String name,String card,String phone) {
        String sqlInit = "",sql;
        int flag = Objects.equals(name, "") ? 0:1;
        flag = Objects.equals(card, "") ? flag :flag+2;
        flag = Objects.equals(phone, "") ? flag: flag+4;

        switch (flag){
            case 0:
                break;
            case 1:
                sqlInit = "select * from '%s' where 姓名='"+name+"'";
                break;
            case 2:
                sqlInit = "select * from '%s' where 身份证号='"+card+"'";
                break;
            case 3:
                sqlInit = "select * from '%s' where 姓名='"+name+"' and 身份证号='"+card+"'";
                break;
            case 4:
                sqlInit = "select * from '%s' where 手机='"+phone+"'";
                break;
            case 5:
                sqlInit = "select * from '%s' where 姓名='"+name+"' and 手机='"+phone+"'";
                break;
            case 6:
                sqlInit = "select * from '%s' where 身份证号='"+card+"' and 手机='"+phone+"'";
                break;
            case 7:
                sqlInit = "select * from '%s' where 姓名='"+name+"' and 身份证号='"+card+"' and 手机='"+phone+"'";
                break;
        }
        if(Objects.equals(sqlInit,"")) return;
        try{
            sql = String.format(sqlInit,"users");
            String db = "/Users/j7ur8/Desktop/SoLibra.db";
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + db);
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery(sql);
            String tmpTable="";
            while(rs.next()){
                if(!Objects.equals(tmpTable, rs.getString(4))) {
                    tmpTable = rs.getString(4);
                }else{
                    continue;
                }
                sql = String.format(sqlInit,tmpTable);
                Statement tmpState = conn.createStatement();
                ResultSet tmpResult = tmpState.executeQuery(sql);
                ResultSetMetaData tmpColumnSet =tmpResult.getMetaData();
                int columnCount = tmpColumnSet.getColumnCount();
                ArrayList<Object> columnArray = new ArrayList<>();
                ArrayList<Object[]> valueArray = new ArrayList<>();
                for(int i=0;i<columnCount;i++){
                    columnArray.add(tmpColumnSet.getColumnName(i+1));
                }

                while(tmpResult.next()){
                    Object[] tmpValueArray = new String[columnCount];
                    for(int i=0;i<columnCount;i++){
                        tmpValueArray[i]=tmpResult.getString(i+1);
//                        System.out.println(tmpResult.getString(i+1));
                    }
                    valueArray.add(tmpValueArray);
                }
                tmpResult.close();
                addJTable(createJTable(columnArray,valueArray));
            }
            rs.close();
            conn.close();
        }catch (Exception e){
            StackTraceElement[] a = e.getStackTrace();
            for (StackTraceElement stackTraceElement : a) {
                BurpExtender.stdout.println(
                        stackTraceElement.getClassName()+" "+
                        stackTraceElement.getFileName()+" "+
                        stackTraceElement.getLineNumber()+" "+
                        stackTraceElement.getMethodName());
            }
        }

    }

    private static JTable createJTable(ArrayList<Object> columnsArray, ArrayList<Object[]> valueArray){
        Object[] columnNames = columnsArray.toArray();
        Object[][] rowData = valueArray.toArray(new Object[0][]);
        JTable newJTable = new JTable(rowData,columnNames);
        newJTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnAdjuster tca = new TableColumnAdjuster(newJTable);
        tca.adjustColumns();
        return newJTable;
    }

    private static void addJTable(JTable targetJTable){
        JScrollPane targetScrollPanel = (JScrollPane) SoLibraryComponent.searchResultScrollPanel;
        JPanel targetPanel = (JPanel) targetScrollPanel.getViewport().getView();
        int elementCount = targetPanel.getComponentCount();

        JScrollPane tmpScrollPane=new JScrollPane(targetJTable);

        GridBagConstraints tmpScrollPaneProperty = new GridBagConstraints();
        tmpScrollPaneProperty.gridx=0;tmpScrollPaneProperty.gridy=elementCount+1;
        tmpScrollPaneProperty.weightx=1;tmpScrollPaneProperty.weighty=0;
        tmpScrollPaneProperty.fill= GridBagConstraints.BOTH;
        tmpScrollPaneProperty.anchor=GridBagConstraints.NORTH;
        targetPanel.add(tmpScrollPane,tmpScrollPaneProperty);

        targetScrollPanel.validate();
        targetScrollPanel.repaint();
    }
}
