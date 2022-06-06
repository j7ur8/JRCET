package jrcet.help;

import java.util.Map;

public class ShutdownThread extends Thread{

    private final Map<Object, Object> services;
    public ShutdownThread(){
         services = Services.getServices();
    }
    public void run() {
//        BufferedWriter out = null;
//        try {
//            out = new BufferedWriter(new FileWriter("/Users/j7ur8/Desktop/success"));
//            out.write("asdfasd");
//            out.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        for (Object oj : services.values()){
            Process process = (Process) oj;
            process.destroy();
//            System.out.println(oj);
        }
    }
}
