package jrcet.frame.tools.JSEncrypt;

import burp.BurpExtender;
import burp.lib.HttpClient;

import java.io.IOException;

public class Utils {
    public static Process phantomjsProcess;

    static public String testPayload[] = {
            "123456","a123456","123456a","5201314",
            "111111","woaini1314","qq123456","123123",
            "000000","1qaz2wsx","1q2w3e4r","qwe123",
            "7758521","123qwe","a123123","123456aa",
            "woaini520","woaini","100200","1314520","中国"
    };

    public static String sendPayload(String payload){
        String encryptPayload = "";
        HttpClient hc = new HttpClient("http://127.0.0.1:1664");
        hc.setConnTimeout(5000);
        hc.setReadTimeout(5000);
        String data = "payload=" + payload;
        hc.setData(data);
        hc.sendPost();
        encryptPayload = hc.getRspData();
        return encryptPayload;
    }

    public static boolean sendTestConnect() {

        HttpClient hc = new HttpClient("http://127.0.0.1:1664");
        hc.setConnTimeout(5000);
        hc.setReadTimeout(5000);
        hc.sendGet();
        byte[] aa = hc.getRspData().getBytes();
        return hc.getStatusCode() == 200;
    }


    public static class StreamGobble extends Thread  {

        public static Process p;
        private final String code;

        public StreamGobble(String phantomjsLocation, String jScriptLocation){
            this.code = "nohup "+phantomjsLocation+" "+jScriptLocation+" &>/dev/null 2>&1 &";
//            this.code="start /b "+phantomjsLocation+" "+jScriptLocation;
        }

        @Override
        public void run() {
            try {
//                System.out.println("start");
                StreamGobble.p = Runtime.getRuntime().exec(this.code);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }

    }

    /**
     * 转换字符串编码
     */
    public static String transformCharset(String str,String charsetName){
        String newStr = null;
        try {
            newStr = new String(str.getBytes(), charsetName);
        }catch (Exception e){
            BurpExtender.stdout.println("[-] Utils.transformCharset erro: " + e.getMessage());
            newStr = str;
        }
        return newStr;
    }
}
