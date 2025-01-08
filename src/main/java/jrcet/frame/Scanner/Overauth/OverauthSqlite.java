package jrcet.frame.Scanner.Overauth;

import help.Helper;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import static burp.MyExtender.BurpAPI;

public class OverauthSqlite {


    private static String DatabaseUrl = "jdbc:sqlite:/tmp/overauth.db";

    private static Connection connection = null;

    private static Integer status = 0;  // write 2 read 1

//    private static PreparedStatement pstmt = null;

    private static  ResultSet rs = null;

    private static ArrayList<PreparedStatement> PSTMTList = new ArrayList<>();

    public static PreparedStatement getPstmt(String sql) throws SQLException {
        return getConnection().prepareStatement(sql);
    }

    public static Connection getConnection() throws SQLException {
        if(connection != null){
            return connection;
        }
        connection = DriverManager.getConnection(DatabaseUrl);
        return connection;
    }


//    public static void close(){
//        if (pstmt != null) {
//            try {
//                pstmt.close();
//            } catch (SQLException e) {
//                BurpAPI.logging().error().println("pstmtError");
//                BurpAPI.logging().error().println(e);
//            }
//        }
//        if(rs !=null){
//            try {
//                rs.close();
//            } catch (SQLException e) {
//                BurpAPI.logging().error().println("rsError");
//                BurpAPI.logging().error().println(e);
//            }
//        }
//
//    }
    public static void close(PreparedStatement pstmt, ResultSet rs ){
        if(rs !=null){
            try {
                rs.close();
            } catch (SQLException e) {
                BurpAPI.logging().error().println(e);
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                BurpAPI.logging().error().println(e);
            }
        }

    }

    public static ResultSet uniqQuery(PreparedStatement pstmt) throws SQLException {
        return pstmt.executeQuery();
    }

    public static void uniqUpdate(PreparedStatement pstmt) throws SQLException {
        pstmt.executeUpdate();
    }

}
