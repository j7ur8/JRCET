package ui;

import java.io.*;

public class test {
    public static void main(String[] args) throws Exception {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("filename",true));
            out.write("aString2");
            out.close();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }
}
