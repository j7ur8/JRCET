package burp.lib;

import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
//import org.xerial.sqlite-jdbc;

public class test {

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException, SQLException {
//        new SQLi
        String [] tables ={
            "aa"
        };

        Class.forName("org.sqlite.JDBC");
        String db = "/Users/j7ur8/Desktop/SoLibra.db";
        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + db);
        Statement state = conn.createStatement();
        String data,sql,name,idCard,phone;
        ResultSet rs;
        for (String table : tables){
            try{
                sql = "SELECT 姓名,身份证号,手机 FROM '"+table+"'";
                rs = state.executeQuery(sql);
                while (rs.next()) {
                    name = rs.getString(1).replace(",","");
                    idCard = rs.getString(2).replace(",","");
                    phone = rs.getString(3).replace(",","");
                    data = name+","+idCard+","+phone+","+table+"\n";
//                  System.out.print(data);

                        BufferedWriter bw = new BufferedWriter(
                            new OutputStreamWriter(new FileOutputStream("/Users/j7ur8/Desktop/aa",true), StandardCharsets.UTF_8));
                        bw.write(data);
                        bw.flush();
                        bw.close();

                }
                rs.close();
            }catch(Exception e){
                System.out.println(table);
            }
        }
        conn.close();
    }

    public static void centerInScreen(Window win) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = ( screenSize.width - win.getWidth())/2;
        int y = ( screenSize.height - win.getHeight())/2;
        win.setLocation(x,  y);
    }

}
