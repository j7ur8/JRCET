package jrcet.frame.tools.Dominate.Domain;

import burp.lib.Helper;
import jrcet.diycomponents.TableColumnAdjuster;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static jrcet.frame.tools.Dominate.Domain.DomainComponent.DomainComponentPanel;

public class Domain {

    public static HashMap<String, Object> radiate(String text) {

        HashMap<String, Object> radiateDataMap = new HashMap<>();
        String sIP = null;
        String sDomain = null;

        try{
            URL sUrl = new URL(text);
            if(Helper.isIpAddress(sUrl.getHost())){
                sIP = sUrl.getHost();
            }else{
                sDomain = sUrl.getHost();
            }
        }catch (Exception e){
            if(Helper.isIpAddress(text)){
                sIP = text;
            }else if(Helper.isDomain(text)){
                sDomain = text;
            }else{
                radiateDataMap.put("Error", new ArrayList<>(Collections.singletonList("输入错误")) );
            }
        }

        if(sIP != null){
            radiateDataMap=ipRadiate(sIP);
        }else{
            radiateDataMap=domainRadiate(sDomain);
        }

        return radiateDataMap;
    }

    public static HashMap<String, Object> ipRadiate(String ip){

        HashMap<String, Object> radiateDataMap = new HashMap<>();
        String url = "https://site.ip138.com/"+ip+"/";
        String tResponse = getResponse(url);
        String regex = "<li><span class=\\\"date\\\">([0-9\\-]{25})</span><a href=\\\"/(.*)/\\\"";
        Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(tResponse);

        ArrayList<String[]> domainList = new ArrayList<>();

        while (matcher.find()) {
            String[] tmpArray = new String[matcher.groupCount()];
            for(int i=0; i< matcher.groupCount(); i++){
                tmpArray[i]=matcher.group(i+1);
            }
            domainList.add(tmpArray);
        }
        radiateDataMap.put("IP", new ArrayList<>(Collections.singletonList(ip)));
        radiateDataMap.put("Domain", domainList);

        return radiateDataMap;
    }

    public static HashMap<String, Object> domainRadiate(String domain){

        HashMap<String, Object> radiateDataMap = new HashMap<>();

        return radiateDataMap;
    }

    public static void fillComponent(HashMap<String, Object> radiateDataMap){

        Object tArrayList = null;
        ArrayList<String> titleArrayList = null;
        for(String key: radiateDataMap.keySet()){
            tArrayList = radiateDataMap.get(key);
            switch (key){
                case "IP":
                    break;
                case "Domain":
                    titleArrayList = new ArrayList<>(Arrays.asList("日期","域名"));

                    JScrollPane nTableScrollPane = createTable(tArrayList, titleArrayList);

                    JComponent tComponentPanel = Helper.getComponent(DomainComponentPanel,"DomainMainIPDomainPanel");
                    assert tComponentPanel != null;
                    tComponentPanel.add(nTableScrollPane, new GridBagConstraints(
                            0,0,
                            1,1,
                            1,1,
                            GridBagConstraints.CENTER,
                            GridBagConstraints.BOTH,
                            new Insets(0,0,0,0),
                            0,0
                    ));
                    tComponentPanel.updateUI();
                    break;
            }
        }
    }

    public static JScrollPane createTable(Object tArrayList, ArrayList<String> titleArrayList){

        JTable nTable = new JTable( ((ArrayList<String[]>)tArrayList).toArray(new Object[0][]) , titleArrayList.toArray());
        nTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        new TableColumnAdjuster(nTable).adjustColumns();

        JScrollPane nTableScrollPane = new JScrollPane(nTable);
//        nTableScrollPane.setHorizontalScrollBarPolicy(JScrollPane.);

        return nTableScrollPane;
    }

    public static String getResponse(String url){
        String result = null;
        try{
            URL eURL = new URL(url);
            HttpURLConnection eConnection = (HttpURLConnection) eURL.openConnection();
            eConnection.setRequestMethod("GET");
            eConnection.setConnectTimeout(15000);
            eConnection.setReadTimeout(60000);
            eConnection.setDoOutput(true);
            eConnection.setDoInput(true);
            eConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.51 Safari/537.36");
            eConnection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
            eConnection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
            eConnection.setRequestProperty("Connection", "close");

            if (eConnection.getResponseCode() == 200) {
                InputStream is = eConnection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                StringBuilder sbf = new StringBuilder();
                String temp = null;
                while ((temp = br.readLine()) != null) {
                    sbf.append(temp);
                    sbf.append("\r\n");
                }
                result = sbf.toString();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}

