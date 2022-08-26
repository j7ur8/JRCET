package jrcet;

import java.util.HashMap;

public class test{
    public static Integer ASSET_IP = 0;
    public static Integer ASSET_DOMAIN = 1;
    public static Integer ASSET_URL = 2;
    public static Integer ASSET_PORT = 3;
    public static Integer ASSET_SERVICE = 4;
    public static Integer ASSET_BELONG = 5;
    public static Integer ASSET_VENDOR = 6;
    public static Integer ASSET_VUL = 7;
    public static Integer ASSET_PROJECT = 8;
    public static Integer ASSET_SOURCE = 9;
    public static Integer ASSET_ALL = 10;
    public static HashMap<String, String> aa=new HashMap<String,String>(){
        {
            put("a","c:%s:%s");
        }
    };

    public static void main(String[] args) {
        String sql = "%s:%s:%s:%s:%s";
//        String[] aa=new String[]{"a","b","C","D","E"};
        String aa = String.format(
                "update asset set domain='%s',url='%s',service='%s',belong='%s',vendor='%s',vul='%s',project='%s',source='%s',utime='2022-08-22 09:49:05' where ip='%s' and port='%s'",
                new String[]{"1","2","3","4","5","6","7","8","9","10"});
        System.out.println(aa);
//        System.out.printf(sql,  aa);
    }

}