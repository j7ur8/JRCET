package jrcet.frame.asset;

import jrcet.lib.Helper;

import javax.swing.*;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Asset {

    public static String AssetRootPath = "/Users/j7ur8/Desktop/Asset";
    public static String AssetDictionary1 = "";
    public static String AssetDictionary2 = "";

    public static String AssetPinter = "";
    public static String AssetBelong = "";


    public static void saveAsset(String url){
        String pinterPath = AssetRootPath+File.separator+"资产指纹"+File.separator+AssetPinter;
        if(!new File(pinterPath).exists()){
            try {
                Files.createDirectories(Paths.get(pinterPath));
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        String belongPath = AssetRootPath+File.separator+AssetBelong+File.separator+AssetPinter;
        if(!new File(belongPath).exists()){
            try {
                Files.createDirectories(Paths.get(belongPath));
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        String pinterUrlPath1 = pinterPath+File.separator+"all.txt";
        String pinterUrlPath2 = pinterPath+File.separator+AssetBelong+".txt";
        String belongUrlPath = belongPath+File.separator+"url.txt";

        url = url+"\n";
        Helper.appendWriteFile(pinterUrlPath1, url);
        Helper.appendWriteFile(pinterUrlPath2, url);
        try{
            Files.createSymbolicLink(FileSystems.getDefault().getPath(belongUrlPath),FileSystems.getDefault().getPath(pinterUrlPath2));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

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
