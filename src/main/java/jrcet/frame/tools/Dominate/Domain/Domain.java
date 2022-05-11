package jrcet.frame.tools.Dominate.Domain;

import jrcet.lib.Helper;
import jrcet.diycomponents.DiyJTextArea.ui.rsyntaxtextarea.RSyntaxTextArea;
import jrcet.diycomponents.DiyJTextArea.ui.rsyntaxtextarea.SyntaxConstants;
import jrcet.diycomponents.DiyJTextArea.ui.rtextarea.RTextScrollPane;
import jrcet.diycomponents.TableColumnAdjuster;
import sun.swing.table.DefaultTableCellHeaderRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
            ipRadiate(sIP, radiateDataMap);
        }else{
            domainRadiate(sDomain, radiateDataMap);
        }
        return radiateDataMap;
    }

    public static void ipRadiate(String ip, HashMap<String, Object> radiateDataMap){

        radiateDataMap.put("IP/Domain", new ArrayList<>(Collections.singletonList(ip)));

        String ipDomainRegex = "<li><span class=\\\"date\\\">([0-9\\-]{25})</span><a href=\\\"/(.*)/\\\"";
        String ipDomainUrl = "https://site.ip138.com/"+ip+"/";

        putIpDomain(matchResponse(ipDomainUrl, ipDomainRegex), radiateDataMap);
    }

    public static void domainRadiate(String domain, HashMap<String, Object> radiateDataMap){

        radiateDataMap.put("IP/Domain", new ArrayList<>(Collections.singletonList(domain)));

//        String ipDomainRegex = "<span class=\\\"date\\\">([0-9\\-]{25})</span>\r\n<a href=\\\"/(.*)/\\\"";
////        String ipDomainUrl = "https://site.ip138.com/"+domain+"/";
//        String ipDomainUrl = "http://42.192.228.137:8999/"+domain+"/";
//        putIpDomain(matchResponse(ipDomainUrl, ipDomainRegex), radiateDataMap);

//        String beiAnRegex = "<td><span>(.*?)</span></td>|<td><a href=\\\".*?target=\\\"_blank\\\">(.*?)</a>|<span>(.*?)</span>";
//        String beiAnUrl = "https://icplishi.com/"+domain+"/";
//
//        putBeiAn(matchResponse(beiAnUrl, beiAnRegex), radiateDataMap);

//        String whoisRegex = "<p>   (.*?)</p>";
//        String whoisUrl = "https://site.ip138.com/"+domain+"/whois.htm";
//        putWhois(matchResponse(whoisUrl, whoisRegex), radiateDataMap);

//        String subdomainRegex = "rel=\\\"nofollow\\\">(.*?)</a></td>";
//        String subdomainUrl = "https://chaziyu.com/"+domain;
//        putSubdomain(matchResponse(subdomainUrl, subdomainRegex), radiateDataMap);
//        addSubdomain(domain, radiateDataMap);

    }

    public static void putIpDomain(Matcher matcher, HashMap<String, Object> radiateDataMap){

        ArrayList<String[]> ipList = new ArrayList<>();

        while (matcher.find()) {
            String[] tmpArray = new String[matcher.groupCount()];
            for(int i=0; i< matcher.groupCount(); i++){
                tmpArray[i]=matcher.group(i+1);
            }
            ipList.add(tmpArray);
        }
        radiateDataMap.put("Domain/IPList", ipList);
        radiateDataMap.put("Domain/IPList-Title", new ArrayList<>(Arrays.asList("日期","IP、域名")));
    }

    public static void putBeiAn(Matcher matcher, HashMap<String, Object> radiateDataMap){

        ArrayList<String[]> ipList = new ArrayList<>();

        String[] tmpArray = new String[5];
        for(int i=0; matcher.find() && i<=tmpArray.length; i++){
            if(i<2) {
                tmpArray[i] = matcher.group(1);
            }else if(i<4) {
                tmpArray[i] = matcher.group(2);
            } else if(i<5) {
                tmpArray[i] = matcher.group(3);
            } else {
                tmpArray[i-1] += " "+matcher.group(3);
            }
        }
        ipList.add(tmpArray);
        radiateDataMap.put("BeianList", ipList);
        radiateDataMap.put("BeianList-Title", new ArrayList<>(Arrays.asList("网站首页","备案类型","备案主体","备案号","备案时间")));
    }

    public static void putWhois(Matcher matcher, HashMap<String, Object> radiateDataMap){


        ArrayList<String[]> ipList = new ArrayList<>();

        String[] tmpArray = new String[]{""};
        while (matcher.find()){
            tmpArray[0] += matcher.group(1)+"\n";
        }
        ipList.add(tmpArray);
        radiateDataMap.put("WhoisList", ipList);
        radiateDataMap.put("WhoisList-Title", new ArrayList<>(Collections.singletonList("Whois")));
    }

    public static void putSubdomain(Matcher matcher, HashMap<String, Object> radiateDataMap){

        ArrayList<String[]> ipList = new ArrayList<>();

        String[] tmpArray = new String[]{""};
        while (matcher.find()) {
            tmpArray[0] += matcher.group(1)+"\n";
        }

        ipList.add(tmpArray);

        radiateDataMap.put("SubdomainList", ipList);
        radiateDataMap.put("SubdomainList-Title", new ArrayList<>(Collections.singletonList("域名")));
    }

    public static void addSubdomain(String domain, HashMap<String, Object> radiateDataMap){

        String tUrl = "https://chaziyu.com/ipchaxun.do?domain="+domain+"&page=";
        ArrayList<String []> ipList = (ArrayList<String[]>) radiateDataMap.get("SubdomainList");

        for(int i=2;;i++){

            Matcher matcher = matchResponse(tUrl+i,"\\[(.*?)]");
            if(matcher.find()){
                String tResponse = matcher.group(0);
                if(tResponse.contains("error domain")){
                    ipList.remove(0);
                    ipList.add(new String[]{"仅支持查询根域名"});
                    break;
                }else{
                    String[] subdomainString = (matcher.find()?(matcher.group(1)==null?"":matcher.group(1)):"出错").replace("\"","").split(",");
                    System.out.printf(String.valueOf(subdomainString));
                }
            }

//            for(Object o: tResponseArray){
//                ipList.get(0)[0] += o +"\n";
//            }
        }
    }

    public static void updatePanel(JComponent rootPanel, HashMap<String, Object> radiateDataMap){

        Object tArrayList = null;
        JScrollPane nTableScrollPane = null;
        ArrayList<String> titleArrayList = null;
        JComponent tComponentPanel = null;
        for(String key: radiateDataMap.keySet()){
            tArrayList = radiateDataMap.get(key);
            switch (key){
                case "IP/Domain":
                    break;
                case "Domain/IPList":
                    titleArrayList = (ArrayList<String>) radiateDataMap.get(key+"-Title");
                    nTableScrollPane = createTable(tArrayList, titleArrayList);
                    tComponentPanel = Helper.getComponent(rootPanel,"DomainMainIPDomainPanel");
                    assert tComponentPanel != null;
                    tComponentPanel.removeAll();
                    tComponentPanel.add(nTableScrollPane, new GridBagConstraints(
                            0,0,
                            1,1,
                            1,1,
                            GridBagConstraints.CENTER,
                            GridBagConstraints.BOTH,
                            new Insets(0,0,0,0),
                            0,0
                    ));
                    break;
                case "BeianList":
                    titleArrayList = (ArrayList<String>) radiateDataMap.get(key+"-Title");
                    nTableScrollPane = createTable(tArrayList, titleArrayList);
                    tComponentPanel = Helper.getComponent(rootPanel,"DomainMainBeianPanel");
                    assert tComponentPanel != null;
                    tComponentPanel.removeAll();
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
                case "WhoisList":
                    titleArrayList = (ArrayList<String>) radiateDataMap.get(key+"-Title");
                    nTableScrollPane = createText(tArrayList, titleArrayList);
                    tComponentPanel = Helper.getComponent(rootPanel, "DomainMainWhoisPanel");
                    assert tComponentPanel!=null;
                    tComponentPanel.removeAll();
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
                case "SubdomainList":
                    titleArrayList = (ArrayList<String>) radiateDataMap.get(key+"-Title");
                    nTableScrollPane = createText(tArrayList, titleArrayList);
                    tComponentPanel = Helper.getComponent(rootPanel, "DomainMainSubdomainPanel");
                    assert tComponentPanel!=null;
                    tComponentPanel.removeAll();
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

    public static RTextScrollPane createText(Object tArrayList, ArrayList<String> titleArrayList){
        StringBuilder textBuilder = new StringBuilder();

        for(String s : ((ArrayList<String[]>)tArrayList).get(0)){
            textBuilder.append(s);
        }

        RSyntaxTextArea nTextArea = new RSyntaxTextArea(textBuilder.toString());
        nTextArea.setLineWrap(true);
        nTextArea.setCodeFoldingEnabled(true);
        nTextArea.setCaretPosition(0);
        nTextArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_YAML);

        RTextScrollPane nTextAreaScrollPane = new RTextScrollPane(nTextArea);
        nTextAreaScrollPane.setHorizontalScrollBarPolicy(RTextScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        nTextAreaScrollPane.setBorder(null);
        nTextAreaScrollPane.setPreferredSize(new Dimension(0,0));

        return nTextAreaScrollPane;
    }

    public static JScrollPane createTable(Object tArrayList, ArrayList<String> titleArrayList){

        JTable nTable = new JTable( ((ArrayList<String[]>)tArrayList).toArray(new Object[0][]) , titleArrayList.toArray());
        nTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        //标题自适应宽度
        new TableColumnAdjuster(nTable).adjustColumns();
        //表头居中
        DefaultTableCellHeaderRenderer hr = new DefaultTableCellHeaderRenderer();
        hr.setHorizontalAlignment(JLabel.CENTER);
        nTable.getTableHeader().setDefaultRenderer(hr);
        //表格内容剧中
        DefaultTableCellRenderer cr = new DefaultTableCellRenderer();

        cr.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        nTable.setDefaultRenderer(Object.class, cr);


        JScrollPane nTableScrollPane = new JScrollPane(nTable);
        nTableScrollPane.setPreferredSize(new Dimension(0,0));
        nTableScrollPane.setBorder(null);

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

    public static Matcher matchResponse(String url, String regex){

        String tResponse = getResponse(url);
        ArrayList<String[]> ipList = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        return pattern.matcher(tResponse);
    }
}

