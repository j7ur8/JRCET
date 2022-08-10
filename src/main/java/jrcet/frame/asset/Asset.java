package jrcet.frame.asset;

import com.tulskiy.keymaster.common.HotKey;
import com.tulskiy.keymaster.common.HotKeyListener;
import com.tulskiy.keymaster.common.Provider;
import jrcet.diycomponents.DiyJButton;
import jrcet.help.Helper;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

import static jrcet.frame.asset.AAssetComponent.AAssetComponentPanel;

public class Asset {

    public static String lastClipboardText = "lastClipboardText ";

    public static void registerHotKey(){

        Provider provider = Provider.getCurrentProvider(false);
        HotKeyListener listener = new HotKeyListener() {
            public void onHotKey(HotKey hotKey) {
                String targetText = Helper.getSysClipboardText();
                targetText=targetText==null?"clipboard is null":targetText;

                analyseText(targetText);
            }
        };

        HotKeyListener listener1 = new HotKeyListener() {
            public void onHotKey(HotKey hotKey) {
                JFrame AAssetFrame = (JFrame) AAssetComponentPanel.getParent();
                AAssetFrame.setVisible(false);
            }
        };
        provider.register(KeyStroke.getKeyStroke("control 1"), listener);
        provider.register(KeyStroke.getKeyStroke("control 2"), listener1);
    }


    public static JFrame addFrame(){
        JFrame JrcetFrame = new JFrame("Add Asset");

        JrcetFrame.setContentPane(new AAssetComponent().AAssetComponentPanel());

        JrcetFrame.setResizable(false);
        JrcetFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JrcetFrame.setSize(600, 500);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = ( screenSize.width - JrcetFrame.getWidth())/2;
        int y = ( screenSize.height - JrcetFrame.getHeight())/2;
        JrcetFrame.setLocation(x,  y);

        JrcetFrame.setVisible(false);

        return  JrcetFrame;
    }


    public static void analyseText(String s){

        String ip = null,domain = null, port=null, url=null;
        JTextField portField,ipField,domainField,urlField;

        if(Helper.isIpAddress(s)){ ip = s; }
        if(Helper.isNumeric(s) && Integer.parseInt(s)<65535 && Integer.parseInt(s) >0){ port = s; }
        if(Helper.isDomain(s)){ domain = s; }
        if(Helper.isUrl(s)){
            try{
                URL urlObject = new URL(s);
                url = String.valueOf(urlObject);
                ip = Helper.isIpAddress(urlObject.getHost())?urlObject.getHost():ip;
                domain = Helper.isDomain(urlObject.getHost())?urlObject.getHost():domain;
                port = (urlObject.getPort()==-1)?(Objects.equals(urlObject.getProtocol(), "http")?"80":"443"):String.valueOf(urlObject.getPort());
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        JFrame AAssetFrame = null;
        if(AAssetComponentPanel == null){
            AAssetFrame = addFrame();
        }

        AAssetFrame = AAssetFrame==null?(JFrame) SwingUtilities.getWindowAncestor(AAssetComponentPanel):AAssetFrame;

        if(AAssetFrame.isVisible() && ((ip==null && domain==null && port==null && url==null) || Objects.equals(s, lastClipboardText))){
            AAssetFrame.setVisible(false);
        }else {
            AAssetFrame.setVisible(true);
            AAssetFrame.setAlwaysOnTop(true);
            AAssetFrame.setAlwaysOnTop(false);
        }
        lastClipboardText=s;
        if(ip!=null){
            ipField = (JTextField) Helper.getComponent(AAssetComponentPanel, "NAssetMainAddIpField"); assert ipField != null;
            ipField.setText(ip);
        }

        if(domain!=null){
            domainField = (JTextField) Helper.getComponent(AAssetComponentPanel, "NAssetMainAddDomainField"); assert domainField != null;
            domainField.setText(domain);
        }

        if(port!=null){
            portField = (JTextField) Helper.getComponent(AAssetComponentPanel, "NAssetMainAddPortField"); assert portField != null;
            portField.setText(port);
        }

        if(url!=null){
            urlField = (JTextField) Helper.getComponent(AAssetComponentPanel, "NAssetMainAddUrlField"); assert urlField != null;
            urlField.setText(url);
        }

        System.out.println("未命中");
    }

    public static String addAsset(){
        JTextField ipField = (JTextField) Helper.getComponent(AAssetComponentPanel,"NAssetMainAddIpField"); assert ipField!=null;
        JTextField domainField = (JTextField) Helper.getComponent(AAssetComponentPanel,"NAssetMainAddDomainField"); assert domainField!=null;
        JTextField urlField = (JTextField) Helper.getComponent(AAssetComponentPanel,"NAssetMainAddUrlField"); assert urlField!=null;
        JTextField portField = (JTextField) Helper.getComponent(AAssetComponentPanel,"NAssetMainAddPortField"); assert portField!=null;
        JTextField serviceField = (JTextField) Helper.getComponent(AAssetComponentPanel,"NAssetMainAddServiceField"); assert serviceField!=null;
        JTextField vulField = (JTextField) Helper.getComponent(AAssetComponentPanel,"NAssetMainAddVulField"); assert vulField!=null;
        JTextField projectField = (JTextField) Helper.getComponent(AAssetComponentPanel,"NAssetMainAddProjectField"); assert projectField!=null;
        JTextField sourceField = (JTextField) Helper.getComponent(AAssetComponentPanel,"NAssetMainAddSourceField"); assert sourceField!=null;

        String uuid, ip,url,domain,port,service,vul,project,source,sql;
        uuid = UUID.randomUUID().toString();
        ip = ipField.getText();
        domain = domainField.getText().toLowerCase(Locale.ROOT);
        url = urlField.getText();
        port = portField.getText();
        service = serviceField.getText();
        vul = vulField.getText();
        project = projectField.getText();
        source = sourceField.getText();

        if(Objects.equals(ip, "")){
            return "IP不能为空";
        }

        if(!Objects.equals(domain, "") && !Helper.isDomain(domain)){
            return "Domain不合法";
        }

        if(!Helper.isIpAddress(ip)){
            return "IP不合法";
        }

        if(!Objects.equals(url, "") && !Helper.isUrl(url)){
            return "Url不合法";
        }

        if(Objects.equals(port,"")){
            return "端口不能为空";
        }

        if(!Helper.isNumeric(port) && Integer.parseInt(port)>0 && Integer.parseInt(port)<65535){
            return "Port不合法";
        }

        HashMap<String, String> selectSqlMap = new HashMap<String, String>(){
            {
                put("asset",   "select uuid from asset where ip='"+ip+"'");
                put("service", "select id from service where name='"+service+"'");
                put("project", "select id from project where name='"+project+"'");
                put("source",  "select id from source where name='"+source+"'");
                put("port",     "select id from port where name='"+port+"'");
            }
        };

        HashMap<String, String> insertSqlMap = new HashMap<String, String>(){
            {
                put("asset",   "insert into asset (uuid, ip, domain, url, port, service, vul, project, source ) values('"+ uuid+"','"+ ip+"','"+ domain+"','"+ url+"','"+ port+"','"+ service+"','"+ vul+"','"+ project+"','"+ source + "')");
                put("service", "insert into service (name) values('"+service+"')");
                put("project", "insert into project (name) values('"+project+"')");
                put("source", "insert into source (name) values('"+source+"')");
                put("port", "insert into port (name) values('"+port+"')");
            }
        };

        HashMap<String, String> updateSqlMap = new HashMap<String, String>(){
            {
                put("service", "update service set utime='"+Helper.getTime()+"' where name='"+service+"'");
                put("project", "update project set utime='"+Helper.getTime()+"' where name='"+project+"'");
                put("source", "update source set utime='"+Helper.getTime()+"' where name='"+source+"'");
                put("port", "update port set utime='"+Helper.getTime()+"' where name='"+port+"'");
            }
        };

        int resultCount;
        PreparedStatement statement;
        for(String key : new String[]{"asset","service","project","source","port"}){
            System.out.println(key);
            sql = selectSqlMap.get(key);
            try{
                statement=Helper.mysqlInstance.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rSet=statement.executeQuery();
                rSet.last();
                resultCount = rSet.getRow();

                if(resultCount == 0){
                    sql = insertSqlMap.get(key);
                    statement=Helper.mysqlInstance.prepareStatement(sql);
                    statement.executeUpdate();
                }else{
                    if(Objects.equals(key, "asset")){
                        return "重复资产";
                    }else{
                        sql = updateSqlMap.get(key);
                        statement=Helper.mysqlInstance.prepareStatement(sql);
                        statement.executeUpdate();
                    }

                }
            }catch (Exception e){
                e.printStackTrace();
                return "系统错误";
            }
        }


        ipField.setText("");
        domainField.setText("");
        urlField.setText("");
        portField.setText("");
        serviceField.setText("");
        vulField.setText("");
        projectField.setText("");
        sourceField.setText("");
        return "查询成功";
    }

    public static String searchAsset(){
        JTextField searchField = (JTextField) Helper.getComponent(AAssetComponentPanel, "NAssetMainSearchMenuSearchField"); assert searchField!=null;
        String searchText = searchField.getText();
        if(Objects.equals(searchText, "")){
            return "搜索内容不能为空";
        }

        String sql = "select uuid, ip ,domain from asset where ip like '" +
                "%" + searchText + "%' or domain like '" +
                "%" + searchText + "%' or url like '" +
                "%" + searchText + "%' or port like '" +
                "%" + searchText + "%' or service like '" +
                "%" + searchText + "%' or port like '" +
                "%" + searchText + "%' or project like '" +
                "%" + searchText + "%'";

//        System.out.println(sql);
        PreparedStatement statement;
        try{
            statement=Helper.mysqlInstance.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rSet=statement.executeQuery();
            while(rSet.next()){
                System.out.println(rSet.getString("uuid")+","+rSet.getString("ip")+","+ rSet.getString("domain"));
            }
        }catch (Exception e){
            e.printStackTrace();
            return "系统错误";
        }
        return "查询成功";
    }


    public static JComponent NAssetMainHistoryPanel(){
        JPanel NAssetMainHistoryPanel = new JPanel(new GridBagLayout());
        NAssetMainHistoryPanel.setName("NAssetMainHistoryPanel");
        NAssetMainHistoryPanel.setPreferredSize(new Dimension(0,0));
        NAssetMainHistoryPanel.setBackground(Color.WHITE);

        String[] names = new String[]{"Port","Project","Service","Source"};

        JComponent tmp;
        for(int i=0; i<names.length; i++){
            tmp = createHistoryPanel(names[i]);
            NAssetMainHistoryPanel.add(tmp, new GridBagConstraints(
                    0,i,
                    1,1,
                    1,0,
                    GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH,
                    new Insets(10,30,0,30),
                    0,0
            ));
        }

        return NAssetMainHistoryPanel;
    }

    public static JComponent createHistoryPanel(String name){
        JPanel historyPanel = new JPanel(new GridBagLayout());
        historyPanel.setName("NAssetMainHistory"+name+"Panel");
        historyPanel.setPreferredSize(new Dimension(0,30));
        historyPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(name+": ",SwingConstants.RIGHT);
        titleLabel.setName("NAssetMainHistory"+name+"Label");
        titleLabel.setPreferredSize(new Dimension(55,0));

        historyPanel.add(titleLabel, new GridBagConstraints(
                0,0,
                1,1,
                0,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        String[] result = new String[0];
        String sql = "select name from "+name.toLowerCase(Locale.ROOT)+" where name!='' order by utime desc limit 5";
//        System.out.println(sql);
        try{
            PreparedStatement statement=Helper.mysqlInstance.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rSet=statement.executeQuery();
            rSet.last();
            result = new String[rSet.getRow()];
            rSet.beforeFirst();
            int i=0;
            while (rSet.next()){
                result[i++]=rSet.getString(1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        DiyJButton tmpDiyJButton;
        int resultLength = result.length;
        for(int i=0; i<resultLength; i++){
            tmpDiyJButton = new DiyJButton(result[i]);
            tmpDiyJButton.setName("NAssetMainHistory"+name+"Button");
            tmpDiyJButton.setPreferredSize(new Dimension(0,0));

            historyPanel.add(tmpDiyJButton, new GridBagConstraints(
                    i+1,0,
                    1,1,
                    1.0/resultLength,1,
                    GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH,
                    new Insets(0,0,0,0),
                    0,0
            ));
        }

        return historyPanel;
    }

}
