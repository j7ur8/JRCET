package jrcet.frame.Asset;

import burp.api.montoya.http.HttpService;
import burp.api.montoya.http.message.requests.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jrcet.frame.Tools.Dencrypt.Url.Url;
import jrcet.help.Helper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Properties;

import static burp.MyExtender.API;
import static burp.api.montoya.http.message.params.HttpParameter.urlParameter;
import static jrcet.frame.Asset.AssetComponent.AssetComponentPanel;

public class Asset {

    public static String fofaSearchUrl = "";
    public static HttpRequest fofaSearchUrlRequest;
    public static String hunterSearchUrl = "";
    public static HttpRequest hunterSearchUrlRequest;
    public static Integer fofaTotalSearchNum = 0;
    public static Integer fofaTotalSearchPage = 0;
    public static Integer hunterTotalSearchNum = 0;
    public static Integer hunterTotalSearchPage = 0;
    public static HashMap<String, ArrayList<String[]>> resultMap = new HashMap<>();
    public static String page="1";

    public static String size="10";
    public static HashMap<String,String> searchMap = new HashMap<>();
    public static void search(String type, String text){

//        resultMap.put("1", new ArrayList<>());
        getTable().setRowCount(0);
        String fofaSearchString = "";
        String hunterSearchString = "";
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
        initParam();
        fofaSearch(fofaSearchString);
        hunterSearch(hunterSearchString);
    }

    public static void initParam(){
        fofaSearchUrl = "";
//        fofaSearchUrlRequest;
        hunterSearchUrl = "";
//        hunterSearchUrlRequest;
        fofaTotalSearchNum = 0;
        fofaTotalSearchPage = 0;
        hunterTotalSearchNum = 0;
        hunterTotalSearchPage = 0;
        resultMap = new HashMap<>();
        page="1";

        size="10";
    }

    public static void hunterSearch(String searchString){

        try{
            InputStream inputStream = Asset.class.getResourceAsStream("hunter.properties");
            Properties properties = new Properties();
            properties.load(inputStream);
            String key = properties.getProperty("key");

            hunterSearchUrl = java.lang.String.format("https://hunter.qianxin.com/openApi/search?api-key=%s&search=%s&page=%s&page_size=%s",key, Base64Url(Helper.base64Encode(searchString)),page,size);
            new searchWorker("hunter",hunterSearchUrl).execute();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void fofaSearch(String searchString){
        try{
            InputStream inputStream = Asset.class.getResourceAsStream("fofa.properties");
            Properties properties = new Properties();
            properties.load(inputStream);
            String email = properties.getProperty("email");
            String key = properties.getProperty("key");

            fofaSearchUrl = java.lang.String.format("https://fofa.info/api/v1/search/all?email=%s&key=%s&qbase64=%s&fields=base_protocol,protocol,domain,ip,port,title,icp,product,country_name,region,city,lastupdatetime&page=%s&size=%s",email,key,Helper.base64Encode(searchString),page,size);
            new searchWorker("fofa", fofaSearchUrl).execute();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String hunterParse(String type, String text){

        String s = "";
        switch (type){
            case "ip":
                s= java.lang.String.format("ip=%s",text);
                break;
            case "domain":
                s= java.lang.String.format("domain.suffix=%s",text);
                break;
            case "icp"://fofa不支持
                s= java.lang.String.format("icp.name=%s",text);
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
                s= java.lang.String.format("ip=%s",text);
                break;
            case "domain":
                s= java.lang.String.format("domain=%s",text);
                break;
            case "icp":
                s= java.lang.String.format("icp=%s",text);
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
        protected String doInBackground() {
//            API.logging().output().println(url);
            if(Objects.equals(type,"fofa")){
                fofaSearchUrlRequest = HttpRequest.httpRequestFromUrl(url);
                return new String(API.http().sendRequest(fofaSearchUrlRequest).response().body().getBytes(),StandardCharsets.UTF_8);
            }else if(Objects.equals(type,"hunter")){
                hunterSearchUrlRequest = HttpRequest.httpRequestFromUrl(url);
                return new String(API.http().sendRequest(hunterSearchUrlRequest).response().body().getBytes(),StandardCharsets.UTF_8);
            }
            return "error";
        }

        @Override
        protected void done() {
            try {
                // 获取耗时操作的结果并更新 UI 界面
                String result = get();
//                API.logging().output().println(result);
                SwingUtilities.invokeLater(() -> {
                    JSONObject resultJSON = JSON.parseObject(result);
                    if(Objects.equals(type, "fofa")){
//                        API.logging().output().println("执行fofa");
//                        Boolean error = resultJSON.getBoolean("error");
                        fofaTotalSearchNum = resultJSON.getInteger("size");
//                        Integer page = resultJSON.getInteger("page");
                        fofaTotalSearchPage = ((int)Math.ceil((double) fofaTotalSearchNum /Integer.parseInt(size)));
                        getFofaLabel().setText(String.format("FoFa: 当前%s/%s页，总计%s条; ", page, fofaTotalSearchPage, fofaTotalSearchNum));
                        updateTableFofa(resultJSON);

                    } else if (Objects.equals(type, "hunter")) {
//                        API.logging().output().println("执行hunter");
                        JSONObject data = resultJSON.getJSONObject("data");
//                        String message = resultJSON.getString("message");
//                        Integer rest_quota = resultJSON.getInteger("rest_quota");
                        hunterTotalSearchNum = data.getInteger("total");
//                        API.logging().output().println(hunterTotalSearchNum);
                        hunterTotalSearchPage = ((int)Math.ceil((double) hunterTotalSearchNum /Integer.parseInt(size)));
//                        API.logging().output().println(hunterTotalSearchPage);
//                        API.logging().output().println(getHunterLabel());
                        getHunterLabel().setText(String.format("Hunter: 当前%s/%s页，总计%s条; ", page, hunterTotalSearchPage, hunterTotalSearchNum));
                        updateTableHunter(data);
                    }
                    getPageLabel().setText(String.format("当前: %s/%s页;",page,fofaTotalSearchPage>hunterTotalSearchPage?fofaTotalSearchPage:hunterTotalSearchPage));
                });
            } catch (Exception e) {
                API.logging().error().println(e);
            }
        }
    }

    public static DefaultTableModel getTable(){
        JTable tTable = (JTable) Helper.getComponent(AssetComponentPanel,"AssetMainBodyResultTable");
        assert tTable != null;
        return (DefaultTableModel) tTable.getModel();
    }

    public static void updateTableHunter(JSONObject resultJSON){
        JSONArray arr = resultJSON.getJSONArray("arr");
        try{
            for (Object value : arr) {
                JSONObject rs = (JSONObject) value;
                JSONArray component = rs.getJSONArray("component");
                StringBuilder productBuilder = new StringBuilder();
                if(component!=null){
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
                String[] newRow = new String[]{protocol, url, domain, ip, port, title, icp, location, updatedtime, product, "hunter"};
                if(!resultMap.containsKey(page)){
                    resultMap.put(page,new ArrayList<>());
                }
                resultMap.get(page).add(newRow);
//                API.logging().output().println(newRow);
                getTable().addRow(newRow);
            }
        }catch (Exception e){
            API.logging().error().println(e);
        }

    }

    public static void updateTableFofa(JSONObject resultJSON){
        JSONArray results = resultJSON.getJSONArray("results");
        try{
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
                String[] newRow = new String[]{protocol, url, domain, ip, port, title, icp, location, updatedtime, product, "fofa"};
                if(!resultMap.containsKey(page)){
                    resultMap.put(page,new ArrayList<>());
                }
                resultMap.get(page).add(newRow);
//            API.logging().output().println(resultMap.get(page));
                getTable().addRow(newRow);
            }
        }catch (Exception e){
            API.logging().error().println(e);
        }

    }

    public static JLabel getFofaLabel(){
        JLabel tLabel = (JLabel) Helper.getComponent(AssetComponentPanel,"AssetMainBodyControlFofaLabel");
        assert tLabel != null;
        return tLabel;
    }

    public static JLabel getHunterLabel(){
        JLabel tLabel = (JLabel) Helper.getComponent(AssetComponentPanel,"AssetMainBodyControlHunterLabel");
        assert tLabel != null;
        return tLabel;
    }

    public static JLabel getPageLabel(){
        JLabel tLabel = (JLabel) Helper.getComponent(AssetComponentPanel,"AssetMainBodyControlPageLabel");
        assert tLabel != null;
        return tLabel;
    }

    public static String Base64Url(String bu){
        return bu.replace("+","-").replace("/","_");
    }

    public static void nextPage(){
        int pageNum = Integer.parseInt(page);
        if(resultMap.containsKey(Integer.toString(pageNum+1))){
            pageNum+=1;
            getTable().setRowCount(0);
            setTableFromExist(pageNum);
            return;
        }

        pageNum+=1;
        if(pageNum<=(fofaTotalSearchPage>hunterTotalSearchPage?fofaTotalSearchPage:hunterTotalSearchPage)){
            getTable().setRowCount(0);
        }else{
            return;
        }
        if(pageNum<=fofaTotalSearchPage){
            fofaSearchUrlRequest = fofaSearchUrlRequest.withUpdatedParameters(urlParameter("page",Integer.toString(pageNum)));
//            API.logging().output().println(fofaSearchUrlRequest.url());
            new searchWorker("fofa", fofaSearchUrlRequest.url()).execute();
        }

        if(pageNum<=hunterTotalSearchPage){
            hunterSearchUrlRequest = hunterSearchUrlRequest.withUpdatedParameters(urlParameter("page",Integer.toString(pageNum)));
//            API.logging().output().println(hunterSearchUrlRequest.url());
            new searchWorker("hunter", hunterSearchUrlRequest.url()).execute();
        }
        page = Integer.toString(pageNum);
    }

    public static void lastPage(){
//        API.logging().output().println("lasdtpage");
        int pageNum = Integer.parseInt(page)-1;
        if(resultMap.containsKey(Integer.toString(pageNum))){
            getTable().setRowCount(0);
            setTableFromExist(pageNum);
        }
    }

    private static void setTableFromExist(int pageNum) {
        for(Object[] o: resultMap.get(Integer.toString(pageNum))){
            getTable().addRow(o);
        }
        getPageLabel().setText(String.format("当前: %s/%s页;",pageNum,fofaTotalSearchPage>hunterTotalSearchPage?fofaTotalSearchPage:hunterTotalSearchPage));
        if(pageNum<=fofaTotalSearchPage){
            getFofaLabel().setText(String.format("FoFa: 当前%s/%s页，总计%s条; ", pageNum, fofaTotalSearchPage, fofaTotalSearchNum));
        }
        if(pageNum<=hunterTotalSearchPage){
            getHunterLabel().setText(String.format("Hunter: 当前%s/%s页，总计%s条; ", pageNum, hunterTotalSearchPage, hunterTotalSearchNum));
        }
        page = Integer.toString(pageNum);
    }

}
