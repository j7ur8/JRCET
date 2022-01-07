package burp.lib;

import org.sqlite.SQLiteConnection;

import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;

public class test {

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException, SQLException {


        Class.forName("org.sqlite.JDBC");
//        SQLiteConnection cnn = new SQLiteConnection("Data Source=Da/Users/j7ur8/Desktop/SoLibra.db");

    }

    public static void centerInScreen(Window win) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = ( screenSize.width - win.getWidth())/2;
        int y = ( screenSize.height - win.getHeight())/2;
        win.setLocation(x,  y);
    }

}
