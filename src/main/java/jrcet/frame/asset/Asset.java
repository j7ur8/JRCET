package jrcet.frame.asset;

import jrcet.lib.Helper;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Asset {

    public static String AssetRootPath = "/Users/j7ur8/Desktop/Asset";
    public static String AssetDictionary1 = "";
    public static String AssetDictionary2 = "";

    public static String[] getDictionary(String filename){

        if(!Helper.isDict(filename)) return new String[]{};
        File file = new File(filename);
        File[] files = file.listFiles();
        ArrayList<String> fileList = new ArrayList<>();
        if (null != files) {
            for (File value : files) {
                if (value.isDirectory() && !value.getName().startsWith(".")) {
                    fileList.add(value.getName());
                }
            }
        }

        String[] fileNames = new String[fileList.size()];
        fileList.toArray(fileNames);

        return fileNames;
    }

    public static String[] getFile(String filename){

        if(!Helper.isDict(filename)) return new String[]{};
        File file = new File(filename);
        File[] files = file.listFiles();
        ArrayList<String> fileList = new ArrayList<>();
        if (null != files) {
            for (File value : files) {
                if (value.isFile() && !value.getName().startsWith(".")) {
                    fileList.add(value.getName());
                }
            }
        }

        String[] fileNames = new String[fileList.size()];
        fileList.toArray(fileNames);

        return fileNames;
    }
}
