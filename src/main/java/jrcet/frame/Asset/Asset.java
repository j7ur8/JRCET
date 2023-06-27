package jrcet.frame.Asset;

import burp.api.montoya.http.message.requests.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jrcet.help.Helper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.Properties;

import static burp.MyExtender.API;
import static jrcet.frame.Asset.AssetComponent.AssetComponentPanel;

public class Asset {

    public static String fofaSearchString = "";
    public static String hunterSearchString = "";

    public static String page="1";
    public static String size="10";
    public static HashMap<String,String> searchMap = new HashMap<>();
    public static void search(String type, String text){

//        String fofaSearchString = "";
//        String hunterSearchString = "";
        switch (type){
            case "AssetMainSearchIpField":
                fofaSearchString = fofaParse("ip", text);
                hunterSearchString = hunterParse("ip",text);
                break;
            case "AssetMainSearchDomainField":
                fofaSearchString = fofaParse("domain", text);
                hunterSearchString = hunterParse("domain",text);
                break;
            case "AssetMainSearchIcpField":
                fofaSearchString = fofaParse("icp", text);
                hunterSearchString = hunterParse("icp",text);
                break;
            case "AssetMainSearchBodyField":
                fofaSearchString = fofaParse("body", text);
                hunterSearchString = hunterParse("body",text);
                break;
            case "AssetMainSearchTitleField":
                fofaSearchString = fofaParse("title", text);
                hunterSearchString = hunterParse("title",text);
                break;
        }

        fofaSearch(fofaSearchString);
        hunterSearch(hunterSearchString);
    }

    public static String hunterSearch(String searchString){

        try{
            InputStream inputStream = Asset.class.getResourceAsStream("hunter.properties");
            Properties properties = new Properties();
            properties.load(inputStream);
            String key = properties.getProperty("key");

            String url = String.format("https://hunter.qianxin.com/openApi/search?api-key=%s&search=%s&page=%s&page_size=%s",key,Helper.base64Encode(searchString),page,size);
            new searchWorker("hunter",url).execute();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static String fofaSearch(String searchString){
        try{
            InputStream inputStream = Asset.class.getResourceAsStream("fofa.properties");
            Properties properties = new Properties();
            properties.load(inputStream);
            String email = properties.getProperty("email");
            String key = properties.getProperty("key");

            String url = String.format("https://fofa.info/api/v1/search/all?email=%s&key=%s&qbase64=%s&fields=base_protocol,protocol,domain,ip,port,title,icp,product,country_name,region,city,lastupdatetime&page=%s&size=%s",email,key,Helper.base64Encode(searchString),page,size);
            new searchWorker("fofa", url).execute();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static String hunterParse(String type, String text){

        String s = "";
        switch (type){
            case "ip":
                s=String.format("ip=%s",text);
                break;
            case "domain":
                s=String.format("domain.suffix=%s",text);
                break;
            case "icp"://fofa不支持
                s=String.format("icp.name=%s",text);
                break;
            case "body":
                s="web.body="+text.
                        replace(" ","").
                        replace("&&","&&web.body=").
                        replace("||","||web.body=");
                break;
            case "title":
                s="web.title="+text.
                        replace(" ","").
                        replace("&&","&&web.title=").
                        replace("||","||web.title=");
                break;
        }
        return s;
    }

    public static String fofaParse(String type, String text){
        String s = "";
        switch (type){
            case "ip":
                s=String.format("ip=%s",text);
                break;
            case "domain":
                s=String.format("domain=%s",text);
                break;
            case "icp":
                s=String.format("icp=%s",text);
                break;
            case "body":
            case "title":
                s=type+"="+text.
                        replace(" ","").
                        replace("&&","&&"+type).
                        replace("||","||"+type);
                break;
        }

//        System.out.println(s);
        return s;
    }


    public static class searchWorker extends SwingWorker<String, Void> {
        public String url;
        public String type;

        public searchWorker(String type,String url){
            this.url=url;
            this.type=type;
        }
        @Override
        protected String doInBackground() throws Exception {
            // 在后台线程中执行耗时操作
            HttpRequest httpRequest = HttpRequest.httpRequestFromUrl(url);
//            API.logging().output().println(url);
            return new String(API.http().issueRequest(httpRequest).httpResponse().body(),StandardCharsets.UTF_8);
        }

        @Override
        protected void done() {
            try {
                // 获取耗时操作的结果并更新 UI 界面
                String result = get();
                SwingUtilities.invokeLater(() -> {
                    JSONObject resultJSON = JSON.parseObject(result);
//                    API.logging().output().println(resultJSON);
                    JTable tTable = (JTable) Helper.getComponent(AssetComponentPanel,"AssetMainBodyResultTable");
                    assert tTable != null;
                    DefaultTableModel defaultTableModel = (DefaultTableModel) tTable.getModel();
                    if(Objects.equals(type, "fofa")){
                        Boolean error = resultJSON.getBoolean("error");
                        Integer size = resultJSON.getInteger("size");
                        Integer page = resultJSON.getInteger("page");
                        JSONArray results = resultJSON.getJSONArray("results");
//                        API.logging().output().println(results);
                        for (Object o : results) {
                            JSONArray rs = (JSONArray) o;
                            String url = "";
                            String protocol = rs.getString(0) + "/" + rs.getString(1);
                            String domain = rs.getString(2);
                            String ip = rs.getString(3);
                            String port = rs.getString(4);
                            if (Objects.equals(rs.getString(1), "http") || Objects.equals(rs.getString(1), "https")) {
                                if (!Objects.equals(domain, "")) {
                                    url = rs.getString(1) + "://" + domain + ":" + port + "/";
                                } else {
                                    url = rs.getString(1) + "://" + ip + ":" + port + "/";
                                }
                            }
                            String title = rs.getString(5);
                            String icp = rs.getString(6);
                            String product = rs.getString(7);
                            String location = rs.getString(8) + "/" + rs.getString(9) + "/" + rs.getString(10);
                            String updatedtime = rs.getString(11);
//                            API.logging().output().println(protocol+"---"+product+"---"+domain+"---"+ip+"---"+port+"---"+title+"---"+icp+"---"+product+"---"+location+"---"+updatedtime);
                            defaultTableModel.addRow(new Object[]{protocol, url, domain, ip, port, title, icp, location, updatedtime, product, "fofa"});
                        }
                    } else if (Objects.equals(type, "hunter")) {
                        String message = resultJSON.getString("message");
                        JSONObject data = resultJSON.getJSONObject("data");
                        JSONArray arr = data.getJSONArray("arr");
                        Integer total = resultJSON.getInteger("total");
                        Integer rest_quota = resultJSON.getInteger("rest_quota");
                        for (Object value : arr) {
                            JSONObject rs = (JSONObject) value;
                            JSONArray component = rs.getJSONArray("component");
                            StringBuilder productBuilder = new StringBuilder();
                            for (Object o : component) {
                                JSONObject ct = (JSONObject) o;
                                if (ct.containsKey("name")) {
                                    productBuilder.append(ct.getString("name"));
                                }

                                if (ct.containsKey("version")) {
                                    productBuilder.append(":").append(ct.getString("version"));
                                }
                                productBuilder.append(";");
                            }
                            String protocol = rs.getString("base_protocol") + "/" + rs.getString("protocol");
                            String domain = rs.getString("domain");
                            String ip = rs.getString("ip");
                            String port = rs.getString("port");
                            String title = rs.getString("web_title");
                            String url = rs.getString("url");
                            String icp = rs.getString("company");
                            String product = productBuilder.toString();
                            String location = rs.getString("country") + "/" + rs.getString("province") + "/" + rs.getString("city");
                            String updatedtime = rs.getString("updated_at");
                            //更新table
                            defaultTableModel.addRow(new Object[]{protocol, url, domain, ip, port, title, icp, location, updatedtime, product, "hunter"});

                        }
                    }
                });
            } catch (Exception ex) {
//                API.logging().error().println(ex);
            }
        }
    }




}
