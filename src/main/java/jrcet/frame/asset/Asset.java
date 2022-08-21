package jrcet.frame.asset;

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

import static jrcet.frame.asset.AssetComponent.AAssetComponentPanel;


public class Asset {

    public static String lastClipboardText = "lastClipboardText ";
    public static String AssetMode = "Global";
    public static int page = 0;
    public static int dataNumber = 6;

    public static void registerHotKey(){

        Provider provider = Provider.getCurrentProvider(false);
        HotKeyListener listener = hotKey -> {
            String targetText = Helper.getSysClipboardText();
            targetText=targetText==null?"clipboard is null":targetText;
            analyseText(targetText);
        };

        provider.register(KeyStroke.getKeyStroke("control 1"), listener);
    }

    public static void  initResultUnitPanel(JComponent AssetMainResultUnitPanel, String[][] result){


        for(int i=0; i<result.length; i++){
            int row = i/3;
            int column = i%3;

            AssetMainResultUnitPanel.add(createUnitPanel(result[i]), new GridBagConstraints(
                    column+0,row+0,
                    1,1,
                    1,1,
                    GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH,
                    new Insets(1,1,1,1),
                    0,0
            ));
        }
    }

    public static JComponent createUnitPanel(String[] result){
        JComponent unitPanel = new JPanel(new GridBagLayout());
        unitPanel.setPreferredSize(new Dimension(0,0));
        unitPanel.setBackground(Color.WHITE);
        unitPanel.setBorder(BorderFactory.createMatteBorder(1,1,1,1,new Color(203,208,209)));

        JComponent tmp;
        String[] columns = new String[]{"Ip","Domain","Url","Port","Service","Belong","vendor","Vul","Project","Source"};
        for(int i=0; i<columns.length; i++ ){
            tmp = createUnitPropertyPanel(columns[i],result[i]);
            unitPanel.add(tmp, new GridBagConstraints(
//                    i%2,i/2+1,
                    0,i,
                    1,1,
                    1,0,
                    GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH,
                    new Insets(4,0,0,4),
                    0,0
            ));
        }

        DiyJButton updateButton = new DiyJButton("Update Asset");
        updateButton.setName("AssetMainResultUpdateButton");
        updateButton.setPreferredSize(new Dimension(0,30));

        unitPanel.add(updateButton, new GridBagConstraints(
//                    i%2,i/2+1,
                0,10,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(4,0,0,0),
                0,0
        ));

        return unitPanel;
    }

    public static JComponent createUnitPropertyPanel(String name, String value){
        JPanel createUnitPropertyPanel = new JPanel(new GridBagLayout());
        createUnitPropertyPanel.setName("NAssetMainAdd"+name+"Panel");
        createUnitPropertyPanel.setPreferredSize(new Dimension(0,30));
        createUnitPropertyPanel.setBackground(Color.WHITE);

        String labelName = "NAssetMainAdd"+name+"Label";
        String fieldName = "NAssetMainAdd"+name+"Field";

        JLabel jLabel = new JLabel(name+": ",SwingConstants.RIGHT);
        jLabel.setName(labelName);
        jLabel.setPreferredSize(new Dimension(60,0));

        JTextField jTextField = new JTextField();
        jTextField.setText(value);
        jTextField.setName(fieldName);
        jTextField.setPreferredSize(new Dimension(0,0));

        createUnitPropertyPanel.add(jLabel, new GridBagConstraints(
                0,0,
                1,1,
                0,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0)
        );
        createUnitPropertyPanel.add(jTextField, new GridBagConstraints(
                1,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));
        return createUnitPropertyPanel;
    }

    public static JFrame addFrame(){
        JFrame JrcetFrame = new JFrame("Add Asset");

        AAssetComponentPanel=new AAssetComponent().main();
        JrcetFrame.setContentPane(AAssetComponentPanel);

        JrcetFrame.setResizable(true);
        JrcetFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JrcetFrame.setSize(1000, 600);

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
        JFrame AAssetFrame = null;
        if(AAssetComponentPanel == null){
            AAssetFrame = addFrame();
        }

        AAssetFrame = AAssetFrame==null?(JFrame) SwingUtilities.getWindowAncestor(AAssetComponentPanel):AAssetFrame;

        if(Objects.equals(s, "")) {
            AAssetFrame.setVisible(true);
        }
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


        if(AAssetFrame.isVisible() && ((ip==null && domain==null && port==null && url==null) || Objects.equals(s, lastClipboardText))){
            System.out.println("未命中");
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
    }

    public static String addAsset(){
        JTextField ipField = (JTextField) Helper.getComponent(AAssetComponentPanel,"NAssetMainAddIpField"); assert ipField!=null;
        JTextField domainField = (JTextField) Helper.getComponent(AAssetComponentPanel,"NAssetMainAddDomainField"); assert domainField!=null;
        JTextField urlField = (JTextField) Helper.getComponent(AAssetComponentPanel,"NAssetMainAddUrlField"); assert urlField!=null;
        JTextField portField = (JTextField) Helper.getComponent(AAssetComponentPanel,"NAssetMainAddPortField"); assert portField!=null;
        JTextField serviceField = (JTextField) Helper.getComponent(AAssetComponentPanel,"NAssetMainAddServiceField"); assert serviceField!=null;
        JTextField belongField = (JTextField) Helper.getComponent(AAssetComponentPanel,"NAssetMainAddServiceField"); assert belongField!=null;
        JTextField vendorField = (JTextField) Helper.getComponent(AAssetComponentPanel,"NAssetMainAddServiceField"); assert vendorField!=null;
        JTextField vulField = (JTextField) Helper.getComponent(AAssetComponentPanel,"NAssetMainAddVulField"); assert vulField!=null;
        JTextField projectField = (JTextField) Helper.getComponent(AAssetComponentPanel,"NAssetMainAddProjectField"); assert projectField!=null;
        JTextField sourceField = (JTextField) Helper.getComponent(AAssetComponentPanel,"NAssetMainAddSourceField"); assert sourceField!=null;

        String uuid, ip,url,domain,port,service,vul,project,source,sql,belong,vendor;
        uuid = UUID.randomUUID().toString();
        ip = ipField.getText();
        domain = domainField.getText().toLowerCase(Locale.ROOT);
        url = urlField.getText();
        port = portField.getText();
        service = serviceField.getText();
        vul = vulField.getText();
        project = projectField.getText();
        source = sourceField.getText();
        belong = belongField.getText();
        vendor = vendorField.getText();

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
                put("asset",   "select uuid from asset where ip='"+ip+"' and port='"+port+"'");
                put("service", "select id from service where name='"+service+"'");
                put("project", "select id from project where name='"+project+"'");
                put("source",  "select id from source where name='"+source+"'");
                put("port",     "select id from port where name='"+port+"'");
                put("ip",     "select id from port where name='"+ip+"'");
            }
        };

        HashMap<String, String> insertSqlMap = new HashMap<String, String>(){
            {
                put("asset",   "insert into asset (uuid, ip, domain, url, port, service,belong,vendor, vul, project, source ) values('"+ uuid+"','"+ ip+"','"+ domain+"','"+ url+"','"+ port+"','"+ service+"','"+belong+"','"+vendor+"','"+ vul+"','"+ project+"','"+ source + "')");
                put("service", "insert into service (name) values('"+service+"')");
                put("belong", "insert into belong (name) values('"+belong+"')");
                put("vendor", "insert into vendor (name) values('"+vendor+"')");
                put("project", "insert into project (name) values('"+project+"')");
                put("source", "insert into source (name) values('"+source+"')");
                put("port", "insert into port (name) values('"+port+"')");
                put("ip", "insert into ip (name) values('"+ip+"')");
            }
        };

        HashMap<String, String> updateSqlMap = new HashMap<String, String>(){
            {
                put("service", "update service set utime='"+Helper.getTime()+"' where name='"+service+"'");
                put("project", "update project set utime='"+Helper.getTime()+"' where name='"+project+"'");
                put("belong", "update belong set utime='"+Helper.getTime()+"' where name='"+belong+"'");
                put("vendor", "update vendor set utime='"+Helper.getTime()+"' where name='"+vendor+"'");
                put("source", "update source set utime='"+Helper.getTime()+"' where name='"+source+"'");
                put("port", "update port set utime='"+Helper.getTime()+"' where name='"+port+"'");
                put("ip", "update ip set utime='"+Helper.getTime()+"' where name='"+ip+"'");
            }
        };

        int resultCount;
        PreparedStatement statement;
        for(String key : new String[]{"asset","service","belong","vendor","project","source","port","ip"}){
//            System.out.println(key);
            sql = selectSqlMap.get(key);
            try{
                Helper.mysqlInstance=Helper.mysqlInstance.isValid(2)?Helper.mysqlInstance:Helper.getJDBC();
                assert Helper.mysqlInstance != null;
                Helper.mysqlInstance=Helper.mysqlInstance.isValid(2)?Helper.mysqlInstance:Helper.getJDBC();
                assert Helper.mysqlInstance != null;
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
        belongField.setText("");
        vendorField.setText("");
        vulField.setText("");
        projectField.setText("");
        sourceField.setText("");
        return "查询成功";
    }

    public static String[][] searchAsset(String searchText,int page, int dataNumber){

        String[][] result = new String[dataNumber][10];
        String startNumber = String.valueOf(page*dataNumber);
        String sql = "select ip,domain,url,port,service,belong,vendor,vul,project,source from asset where ip like '" +
                "%" + searchText + "%' or domain like '" +
                "%" + searchText + "%' or url like '" +
                "%" + searchText + "%' or port like '" +
                "%" + searchText + "%' or service like '" +
                "%" + searchText + "%' or belong like '" +
                "%" + searchText + "%' or vendor like '" +
                "%" + searchText + "%' or vul like '" +
                "%" + searchText + "%' or project like '" +
                "%" + searchText + "%' order by utime desc limit "+startNumber+", "+dataNumber;

        try {
            Helper.mysqlInstance=Helper.mysqlInstance.isValid(2)?Helper.mysqlInstance:Helper.getJDBC();
            assert Helper.mysqlInstance != null;
            PreparedStatement statement = Helper.mysqlInstance.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rSet = statement.executeQuery();
            int n=0;
            while (rSet.next()) {
                result[n][0]=rSet.getString("ip");
                result[n][1]=rSet.getString("domain");
                result[n][2]=rSet.getString("url");
                result[n][3]=rSet.getString("port");
                result[n][4]=rSet.getString("service");
                result[n][5]=rSet.getString("belong");
                result[n][6]=rSet.getString("vendor");
                result[n][7]=rSet.getString("vul");
                result[n][8]=rSet.getString("project");
                result[n][9]=rSet.getString("source");
                n++;
//                System.out.println(rSet.getString("uuid") + "," + rSet.getString("ip") + "," + rSet.getString("domain"));
            }
        }catch (Exception e){
            e.printStackTrace();
            return new String[][]{};
        }
        return result;
    }

    public static String[][] searchFromAsset(int page, int dataNumber){
        String[][] result = new String[dataNumber][10];
        int startNumber = page*dataNumber;
        String sql = "select ip,domain,url,port,service,belong,vendor,vul,project,source from asset order by utime desc limit "+startNumber+", "+dataNumber;
        try {
            Helper.mysqlInstance=Helper.mysqlInstance.isValid(2)?Helper.mysqlInstance:Helper.getJDBC();
            assert Helper.mysqlInstance != null;
            PreparedStatement statement = Helper.mysqlInstance.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rSet = statement.executeQuery();
            int n=0;
            while (rSet.next()) {
                result[n][0]=rSet.getString("ip");
                result[n][1]=rSet.getString("domain");
                result[n][2]=rSet.getString("url");
                result[n][3]=rSet.getString("port");
                result[n][4]=rSet.getString("service");
                result[n][5]=rSet.getString("belong");
                result[n][6]=rSet.getString("vendor");
                result[n][7]=rSet.getString("vul");
                result[n][8]=rSet.getString("project");
                result[n][9]=rSet.getString("source");
                n++;
//                System.out.println(rSet.getString("uuid") + "," + rSet.getString("ip") + "," + rSet.getString("domain"));
            }
        }catch (Exception e){
            e.printStackTrace();
            return new String[][]{};
        }
        return result;
    }

    public static void updateUniteAsset(JComponent unitPanel){
        JTextField ipField,domainField,urlField,portField,serviceField,vulField,projectField,sourceField,belongField,vendorField;
        String ip,domain,url,port,service,vul,project,source,utime,belong,vendor;

        Component[] components = unitPanel.getComponents();
        ipField = ((JTextField)((JPanel)components[0]).getComponent(1));
        domainField =((JTextField)((JPanel)components[1]).getComponent(1)) ;
        urlField = ((JTextField)((JPanel)components[2]).getComponent(1));
        portField = ((JTextField)((JPanel)components[3]).getComponent(1));
        serviceField = ((JTextField)((JPanel)components[4]).getComponent(1));
        belongField = ((JTextField)((JPanel)components[5]).getComponent(1));
        vendorField = ((JTextField)((JPanel)components[6]).getComponent(1));
        vulField = ((JTextField)((JPanel)components[7]).getComponent(1));
        projectField = ((JTextField)((JPanel)components[8]).getComponent(1));
        sourceField = ((JTextField)((JPanel)components[9]).getComponent(1));

        ip = ipField.getText();
        domain = domainField.getText();
        url = urlField.getText();
        port = portField.getText();
        service = serviceField.getText();
        belong = belongField.getText();
        vendor = vendorField.getText();
        vul = vulField.getText();
        project = projectField.getText();
        source = sourceField.getText();
        utime = Helper.getTime();

        HashMap<String, String> insertSqlMap = new HashMap<String, String>(){
            {
               put("service", "insert into service (name) values('"+service+"')");
                put("belong", "insert into belong (name) values('"+belong+"')");
                put("vendor", "insert into vendor (name) values('"+vendor+"')");
                put("project", "insert into project (name) values('"+project+"')");
                put("source", "insert into source (name) values('"+source+"')");
            }
        };

        HashMap<String, String> updateSqlMap = new HashMap<String, String>(){
            {
                put("asset","update asset set " +
                        "domain='"+domain+"',"+
                        "url='"+url+"',"+
                        "service='"+service+"',"+
                        "belong='"+belong+"',"+
                        "vendor='"+vendor+"',"+
                        "vul='"+vul+"',"+
                        "project='"+project+"',"+
                        "source='"+source+"',"+
                        "utime='"+utime+"' "+
                        "where ip='"+ip+"' "+
                        "and port='"+port+"'");
                put("port", "update port set utime='"+ utime+"' where name='"+port+"'");
                put("project", "update project set utime='"+ utime+"' where name='"+project+"'");
                put("belong", "update belong set utime='"+ utime+"' where name='"+belong+"'");
                put("vendor", "update vendor set utime='"+ utime+"' where name='"+vendor+"'");
                put("service", "update service set utime='"+ utime+"' where name='"+service+"'");
                put("source", "update source set utime='"+ utime+"' where name='"+source+"'");
            }
        };

        try {
            int affectRows = 0;
            PreparedStatement statement;
            Helper.mysqlInstance = Helper.mysqlInstance.isValid(2) ? Helper.mysqlInstance : Helper.getJDBC();
            assert Helper.mysqlInstance != null;
            statement = Helper.mysqlInstance.prepareStatement(updateSqlMap.get("asset"));
            statement.executeUpdate();

            for(String table: new String[]{"service","belong","project","vendor","source"}){
                Helper.mysqlInstance=Helper.mysqlInstance.isValid(2)?Helper.mysqlInstance:Helper.getJDBC();
                assert Helper.mysqlInstance != null;
                statement=Helper.mysqlInstance.prepareStatement(updateSqlMap.get(table));
                affectRows = statement.executeUpdate();
               if(affectRows==0){
                   Helper.mysqlInstance=Helper.mysqlInstance.isValid(2)?Helper.mysqlInstance:Helper.getJDBC();
                   assert Helper.mysqlInstance != null;
                   statement=Helper.mysqlInstance.prepareStatement(insertSqlMap.get(table));
                   statement.executeUpdate();
               }
            }
        } catch (Exception e){
            e.printStackTrace();
        }


        addFrame();
        AAssetComponentPanel.updateUI();

//        domainField.setText(domain);
//        urlField.setText(url);
//        serviceField.setText(service);
//        vulField.setText(vul);
//        projectField.setText(project);
//        sourceField.setText(source);
//
//        unitPanel.updateUI();
    }

    public static JComponent NAssetMainHistoryPanel(){
        JPanel NAssetMainHistoryPanel = new JPanel(new GridBagLayout());
        NAssetMainHistoryPanel.setName("NAssetMainHistoryPanel");
        NAssetMainHistoryPanel.setPreferredSize(new Dimension(0,0));
        NAssetMainHistoryPanel.setBackground(Color.WHITE);

        String[] names = new String[]{"Ip","Port","Project","Service","belong","vendor","Source"};

        JComponent tmp;
        for(int i=0; i<names.length; i++){
            tmp = createHistoryPanel(names[i]);
            NAssetMainHistoryPanel.add(tmp, new GridBagConstraints(
                    0,i,
                    1,1,
                    1,0,
                    GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH,
                    new Insets(5,30,0,30),
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

        try{
            Helper.mysqlInstance=Helper.mysqlInstance.isValid(2)?Helper.mysqlInstance:Helper.getJDBC();
            assert Helper.mysqlInstance != null;
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
