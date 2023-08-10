package jrcet.frame.Scanner.Overauth;

import jrcet.help.Helper;

import javax.swing.*;
import java.util.HashMap;

import static jrcet.frame.Scanner.Overauth.OverauthComponent.OverauthComponentPanel;

public class Overauth {

    private static final HashMap<String, Integer> ColumnMap = new HashMap<>(){
        {
            put("#", 0);
            put("Tool", 1);
            put("Method", 2);
            put("Host", 3);
            put("Path", 4);
            put("Length", 5);
            put("requestTime", 6);
            put("responseTime", 7);
            put("OverAuth", 8);
            put("UnAuth", 9);
            put("FlatAuth", 10);
        }
    };
    public static void setOverauthLoggerTableValueAt(String value, Integer rowIndex, String columnName){
        getOverauthLoggerTable().getModel().setValueAt(value, rowIndex, ColumnMap.get(columnName));
    }

    public static JTable getOverauthLoggerTable(){
        return (JTable) Helper.getComponent(OverauthComponentPanel,"OverauthLoggerTable");
    }

    private static JTextField getField(String filedName){
        return (JTextField) Helper.getComponent(OverauthComponentPanel,filedName);
    }

    public static JTextField getOverauthMenuHighauthField(){
        return getField("OverauthMenuHighauthField");
    }

    public static JTextField getOverauthMenuHostField(){
        return getField("OverauthMenuHostField");
    }

    public static JTextField getOverauthMenuLowauthField(){
        return getField("OverauthMenuLowauthField");
    }

    public static String getOverAuthRequestNumber(int row){
        return (String) getOverauthLoggerTable().getValueAt(row,0);
    }
}
