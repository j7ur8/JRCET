package burp.lib;

import java.io.*;

public class test {

    public static void main(String[] args) throws IOException, InterruptedException {

        BufferedWriter out = new BufferedWriter(new FileWriter("runoob.txt"));
        out.write("asdf");
    }

    public static String fileRead(String filename){
        FileInputStream fis = null;

        StringBuilder buffer=new StringBuilder();
        try {

            fis = new FileInputStream(filename);
            int ch =fis.read();
            while( ch != -1) {
                buffer.append((char)ch);
                ch=fis.read();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }
    public static class StreamGobble extends Thread  {


        public static Process p;

        @Override
        public void run() {
            try {
                System.out.println("start");
                Process p = Runtime.getRuntime().exec("nohup phantomjs /Users/j7ur8/Documents/GitHub/jsEncrypter/script/jsEncrypter_base64.js &>/dev/null 2>&1 &");
                StreamGobble.p = p;
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }

    }
}
