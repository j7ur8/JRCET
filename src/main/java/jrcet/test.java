package jrcet;

import com.alibaba.fastjson.JSONArray;

import javax.swing.*;

import java.util.Objects;

import static burp.MyExtender.API;

public class test {
    public static void main(String[] args) {
        // 创建 JFrame 对象
        String a="[[\"tcp\",\"ftp\",\"\",\"42.192.228.137\",\"21\",\"\",\"\",\"vsftpd\",\"中国\",\"\",\"\",\"2023-06-26 16:00:00\"],[\"udp\",\"ntp\",\"\",\"42.192.228.137\",\"123\",\"\",\"\",\"NTP服务器\",\"中国\",\"\",\"\",\"2023-06-26 07:00:00\"],[\"udp\",\"portmap\",\"\",\"42.192.228.137\",\"111\",\"\",\"\",\"\",\"中国\",\"\",\"\",\"2023-06-24 23:00:00\"],[\"tcp\",\"https\",\"\",\"42.192.228.137\",\"443\",\"\",\"\",\"APACHE-HTTP_Server\",\"中国\",\"\",\"\",\"2023-06-24 21:00:00\"],[\"tcp\",\"http\",\"\",\"42.192.228.137\",\"12345\",\"\",\"\",\"APACHE-HTTP_Server\",\"中国\",\"\",\"\",\"2023-06-17 17:00:00\"],[\"tcp\",\"http\",\"\",\"42.192.228.137\",\"12345\",\"\",\"\",\"APACHE-HTTP_Server\",\"中国\",\"\",\"\",\"2023-06-17 17:00:00\"],[\"tcp\",\"ssh\",\"\",\"42.192.228.137\",\"22\",\"\",\"\",\"ubuntu-系统,OpenSSH\",\"中国\",\"\",\"\",\"2023-06-16 14:00:00\"],[\"tcp\",\"ssh\",\"j7ur8.com\",\"42.192.228.137\",\"22\",\"\",\"\",\"ubuntu-系统,OpenSSH\",\"中国\",\"\",\"\",\"2023-06-16 14:00:00\"],[\"tcp\",\"https\",\"j7ur8.com\",\"42.192.228.137\",\"443\",\"\",\"\",\"APACHE-HTTP_Server\",\"中国\",\"\",\"\",\"2023-06-15 21:00:00\"],[\"tcp\",\"tls\",\"\",\"42.192.228.137\",\"60001\",\"\",\"\",\"\",\"中国\",\"\",\"\",\"2023-06-15 18:00:00\"]]";
        JSONArray results = JSONArray.parseArray(a);
        for(int i=0; i<results.size(); i++){
            JSONArray rs = (JSONArray) results.get(0);
                            String url="";
            String protocol = rs.getString(0)+"/"+rs.getString(1);
            String domain = rs.getString(2);
            String ip = rs.getString(3);
            String port = rs.getString(4);
                            if(Objects.equals(rs.getString(1), "http") || Objects.equals(rs.getString(1), "https")){
                                if (!Objects.equals(domain, "")){
                                    url=rs.getString(1)+"://"+domain+":"+port+"/";
                                }else{
                                    url=rs.getString(1)+"://"+ip+":"+port+"/";
                                }
                            }
            String title = rs.getString(5);
            String icp = rs.getString(6);
            String product = rs.getString(7);
            String location = rs.getString(8)+"/"+rs.getString(9)+"/"+rs.getString(10);
            String updatedtime = rs.getString(11);
            System.out.println(protocol+"---"+product+"---"+domain+"---"+ip+"---"+port+"---"+title+"---"+icp+"---"+product+"---"+location+"---"+updatedtime+url);
//                            defaultTableModel.addRow(new Object[]{protocol,url,domain,ip,port,title,icp,location,updatedtime,product,"fofa"});
        }
    }

}
