package jrcet;

import com.alibaba.fastjson.JSONArray;
import jrcet.help.Helper;
import org.xm.Similarity;

import javax.swing.*;

import java.util.Objects;

import static burp.MyExtender.API;
import static burp.api.montoya.collaborator.PayloadOption.WITHOUT_SERVER_LOCATION;

public class test {
    public static void main(String[] args) {
        // 创建 JFrame 对象
//        Similarity.charBasedSimilarity();
        System.out.println(Helper.base64UrlEncode2String("11"));
        System.out.println(Helper.base64Encode2String("11"));
    }

}
