package ui;

import java.io.File;
import java.util.Scanner;

class test{

    public static void main(String[] args) {
        //列目录
        lml();
    }

    public static void lml(){
        File file = new File("/Users/j7ur8/Desktop/Asset");
        File[] files = file.listFiles();
        if (null != files) {
            for (int i = 0; i < files.length; i++) {
                if(files[i].isDirectory()){
                    System.out.println(files[i].getName());
                }
            }
        }
    }
}