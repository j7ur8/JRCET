package burp.lib;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Helper {

    public static String readFile(String filename) {

        StringBuilder content = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(filename), StandardCharsets.UTF_8));
            String line = "";
            while ((line = in.readLine()) != null) {
                content.append(line).append("\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    public static void writeFile(String Content,String filename) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(filename));
        out.write(Content);
        out.close();
    }

    public static boolean isFile(String filename){
        File file = new File(filename);

        return file.isFile();
    }

    /*
    根据匹配的字符串检测进程，并返回pid
     */
//    public static String[] detectProcess(String keyValue) throws IOException {
//        Process a=Runtime.getRuntime().exec("ps aux|grep "+keyValue+" |awk '{print $2}'");
//
//    }
}
