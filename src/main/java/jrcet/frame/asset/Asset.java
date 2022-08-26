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
import static jrcet.frame.asset.AssetComponent.AAssetFrame;


public class Asset {
    public static Integer UPDATE_SUCCESS = 1;
    public static Integer UPDATE_FAIL = 0;
    public static Integer UPDATE_ERROR = 2;

    public static Integer INSERT_SUCCESS = 1;
    public static Integer INSERT_FAIL = 0;
    public static Integer INSERT_ERROR = 2;

    public static Integer SELECT_SUCCESS = 1;
    public static Integer SELECT_FAIL = 0;
    public static Integer SELECT_ERROR = 2;

    public static Integer ASSET_UUID = 0;
    public static Integer ASSET_IP = 1;
    public static Integer ASSET_DOMAIN = 2;
    public static Integer ASSET_URL = 3;
    public static Integer ASSET_PORT = 4;
    public static Integer ASSET_SERVICE = 5;
    public static Integer ASSET_BELONG = 6;
    public static Integer ASSET_VENDOR = 7;
    public static Integer ASSET_VUL = 8;
    public static Integer ASSET_PROJECT = 9;
    public static Integer ASSET_SOURCE = 10;
    public static Integer ASSET_ALL_UPDATE = 11;
    public static Integer ASSET_ALL_INSERT = 12;
    public static Integer ASSET_ALL_SELECT = 13;

    public static HashMap<Integer, String> insertSqlMap = new HashMap<Integer, String>(){
        {
            put(ASSET_ALL_SELECT, "insert into asset (uuid, ip, domain, url, port, service, belong, vendor, vul, project, source ) values('"+UUID.randomUUID()+"','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')");
            put(ASSET_ALL_INSERT, "insert into asset (uuid, ip, domain, url, port, service, belong, vendor, vul, project, source ) values('"+UUID.randomUUID()+"','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')");
            put(ASSET_SERVICE, "insert into service (name) values('%s')");
            put(ASSET_BELONG, "insert into belong (name) values('%s')");
            put(ASSET_VENDOR, "insert into vendor (name) values('%s')");
            put(ASSET_PROJECT, "insert into project (name) values('%s')");
            put(ASSET_SOURCE, "insert into source (name) values('%s')");
            put(ASSET_PORT, "insert into port (name) values('%s')");
            put(ASSET_IP, "insert into ip (name) values('%s')");
        }
    };

    public static HashMap<Integer, String> updateSqlMap = new HashMap<Integer, String>(){
        {
            put(ASSET_IP, "update ip set utime='"+ Helper.getTime()+"' where name='%s'");
            put(ASSET_PORT, "update port set utime='"+ Helper.getTime()+"' where name='%s'");
            put(ASSET_SERVICE, "update service set utime='"+ Helper.getTime()+"' where name='%s'");
            put(ASSET_BELONG, "update belong set utime='"+ Helper.getTime()+"' where name='%s'");
            put(ASSET_VENDOR, "update vendor set utime='"+ Helper.getTime()+"' where name='%s'");
            put(ASSET_PROJECT, "update project set utime='"+ Helper.getTime()+"' where name='%s'");
            put(ASSET_SOURCE, "update source set utime='"+ Helper.getTime()+"' where name='%s'");
            put(ASSET_ALL_UPDATE,"update asset set " +
                    "domain='%s',url='%s',service='%s',belong='%s',vendor='%s',vul='%s',project='%s',source='%s'," +
                    "utime='"+Helper.getTime()+"' where ip='%s' and port='%s'");
        }
    };

    public static HashMap<Integer, String> selectSqlMap = new HashMap<Integer, String>(){
        {
            put(ASSET_ALL_SELECT,  "select uuid from asset where ip='%s' and port='%s'");
            put(ASSET_SERVICE, "select id from service where name='%s'");
            put(ASSET_PROJECT, "select id from project where name='%s'");
            put(ASSET_SOURCE,  "select id from source where name='%s'");
            put(ASSET_PORT,    "select id from port where name='%s'");
            put(ASSET_IP,      "select id from ip where name='%s'");
            put(ASSET_BELONG,  "select id from belong where name ='%s'");
            put(ASSET_VENDOR,  "select id from vendor where name ='%s'");
        }
    };

    public static String lastClipboardText = "lastClipboardText ";
    public static String AssetMode = "Global";
    public static int page = 0;
    public static int dataNumber = 6;

    public static Integer updateDynamic(HashMap<Integer,Object> columns,Integer mode){
        String sql;
        try{
            if(mode<11){
                String name= (String) columns.get(mode);
                if(Objects.equals(name, "")){
                    return UPDATE_SUCCESS;
                }
                sql = String.format(updateSqlMap.get(mode), name);
            }else if(mode.equals(ASSET_ALL_UPDATE)){
                sql = String.format(updateSqlMap.get(mode), (String [])columns.get(mode));
            }else{
                return UPDATE_ERROR;
            }
            PreparedStatement statement = Objects.requireNonNull(Helper.getConnector()).prepareStatement(sql);
            if(statement.executeUpdate()>0){
                return UPDATE_SUCCESS;
            }
            return UPDATE_FAIL;
        }catch (Exception e){
            e.printStackTrace();
            return UPDATE_ERROR;
        }

    }

    public static Integer insertDynamic(HashMap<Integer,Object> columns,Integer mode) {
        String sql;
        try{
            if(mode<11){
                sql = String.format(insertSqlMap.get(mode), columns.get(mode));
            }else if(mode.equals(ASSET_ALL_INSERT)){
                sql = String.format(insertSqlMap.get(mode), (String [])columns.get(mode));
            }else{
                return INSERT_ERROR;
            }
            PreparedStatement statement = Objects.requireNonNull(Helper.getConnector()).prepareStatement(sql);
            if(statement.executeUpdate()>0){
                return INSERT_SUCCESS;
            }
            return INSERT_FAIL;
        }catch (Exception e){
            e.printStackTrace();
            return INSERT_ERROR;
        }
    }

    public static Integer selectDynamic(HashMap<Integer,Object> columns,Integer mode) {
        String sql;
        try{
            if(mode<11){
                sql = String.format(selectSqlMap.get(mode), columns.get(mode));
            }else if(mode.equals(ASSET_ALL_SELECT)){
                sql = String.format(selectSqlMap.get(mode), (String [])columns.get(mode));
            }else{
                return SELECT_ERROR;
            }
            PreparedStatement statement = Objects.requireNonNull(Helper.getConnector()).prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rSet = statement.executeQuery();
            rSet.last();
            if(rSet.getRow()>0){
                return SELECT_SUCCESS;
            }
            return SELECT_FAIL;
        }catch (Exception e){
            e.printStackTrace();
            return SELECT_ERROR;
        }
    }

    public static void updateUniteAsset(JComponent unitePanel){

        HashMap<Integer,Object> unitContent = getUnitContent(unitePanel);

        try {
            if(!Objects.equals(updateDynamic(unitContent, ASSET_ALL_UPDATE), UPDATE_SUCCESS)){
                return;
            }
            for(Integer mode: new Integer[]{ASSET_BELONG,ASSET_IP,ASSET_PORT,ASSET_SERVICE,ASSET_PROJECT,ASSET_VENDOR,ASSET_SOURCE}){
                if(!Objects.equals(updateDynamic(unitContent, mode), UPDATE_SUCCESS) ){
                    insertDynamic(unitContent, mode);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

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
                    column, row,
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

    public static JFrame getAAssetFrame(){
        JFrame AAssetFrame = new JFrame("Add Asset");

//        AAssetComponentPanel=new AAssetComponent().main();
//        AAssetFrame.setContentPane(AAssetComponentPanel);

        AAssetFrame.setResizable(true);
        AAssetFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        AAssetFrame.setSize(1000, 600);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = ( screenSize.width - AAssetFrame.getWidth())/2;
        int y = ( screenSize.height - AAssetFrame.getHeight())/2;
        AAssetFrame.setLocation(x,  y);

        AAssetFrame.setVisible(false);

        return  AAssetFrame;
    }

    public static void analyseText(String s){

        String ip = null,domain = null, port=null, url=null;
        JTextField portField,ipField,domainField,urlField;

        if(AAssetComponentPanel == null){
            AAssetFrame = getAAssetFrame();
            AAssetComponentPanel = new AAssetComponent().main();
            AAssetFrame.setContentPane(AAssetComponentPanel);
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

    public static HashMap<Integer,Object> getNAssetContent(JComponent nAssetPanel){
        JTextField ipField = (JTextField) Helper.getComponent(nAssetPanel,"NAssetMainAddIpField"); assert ipField!=null;
        JTextField domainField = (JTextField) Helper.getComponent(nAssetPanel,"NAssetMainAddDomainField"); assert domainField!=null;
        JTextField urlField = (JTextField) Helper.getComponent(nAssetPanel,"NAssetMainAddUrlField"); assert urlField!=null;
        JTextField portField = (JTextField) Helper.getComponent(nAssetPanel,"NAssetMainAddPortField"); assert portField!=null;
        JTextField serviceField = (JTextField) Helper.getComponent(nAssetPanel,"NAssetMainAddServiceField"); assert serviceField!=null;
        JTextField belongField = (JTextField) Helper.getComponent(nAssetPanel,"NAssetMainAddBelongField"); assert belongField!=null;
        JTextField vendorField = (JTextField) Helper.getComponent(nAssetPanel,"NAssetMainAddVendorField"); assert vendorField!=null;
        JTextField vulField = (JTextField) Helper.getComponent(nAssetPanel,"NAssetMainAddVulField"); assert vulField!=null;
        JTextField projectField = (JTextField) Helper.getComponent(nAssetPanel,"NAssetMainAddProjectField"); assert projectField!=null;
        JTextField sourceField = (JTextField) Helper.getComponent(nAssetPanel,"NAssetMainAddSourceField"); assert sourceField!=null;

        return new HashMap<Integer,Object>(){
            {
                put(ASSET_UUID,UUID.randomUUID().toString());
                put(ASSET_IP,ipField.getText());
                put(ASSET_DOMAIN,domainField.getText());
                put(ASSET_URL,urlField.getText());
                put(ASSET_PORT,portField.getText());
                put(ASSET_SERVICE,serviceField.getText());
                put(ASSET_VUL,vulField.getText());
                put(ASSET_PROJECT,projectField.getText());
                put(ASSET_SOURCE,sourceField.getText());
                put(ASSET_BELONG,belongField.getText());
                put(ASSET_VENDOR,vendorField.getText());
                put(ASSET_ALL_SELECT,new String[]{ipField.getText(),portField.getText()});
                put(ASSET_ALL_INSERT,new String[]{ipField.getText(), domainField.getText(), urlField.getText(), portField.getText(), serviceField.getText(), belongField.getText(), vendorField.getText(), vulField.getText(), projectField.getText(), sourceField.getText()});
            }
        };
    }

    public static boolean checkInput(String input){
        return !input.contains("'");
    }

    public static String addAsset(JComponent nAssetPanel){

        HashMap<Integer, Object> nAssetContent = getNAssetContent(nAssetPanel);

        for(Object v:nAssetContent.values()){
            if(v instanceof String && !checkInput((String)v)){
                return "输入不能含有单引号";
            }
        }

        if(Objects.equals(nAssetContent.get(ASSET_IP), "") && !Helper.isIpAddress((String) nAssetContent.get(ASSET_IP)) && Objects.equals(nAssetContent.get(ASSET_PORT),"")){
            return "IP和端口不能为空 或 IP错误";
        }

        if(!Objects.equals(nAssetContent.get(ASSET_DOMAIN), "") && !Helper.isDomain((String) nAssetContent.get(ASSET_DOMAIN))){
            return "Domain不合法";
        }

        if(!Objects.equals(nAssetContent.get(ASSET_URL), "") && !Helper.isUrl((String) nAssetContent.get(ASSET_URL))){
            return "Url不合法";
        }

        if(!Helper.isNumeric((String) nAssetContent.get(ASSET_PORT)) && Integer.parseInt((String) nAssetContent.get(ASSET_PORT))>0 && Integer.parseInt((String) nAssetContent.get(ASSET_PORT))<65535){
            return "Port不合法";
        }

        for(Integer mode : new Integer[]{ASSET_ALL_SELECT,ASSET_SERVICE,ASSET_BELONG,ASSET_VENDOR,ASSET_PROJECT,ASSET_SOURCE,ASSET_PORT,ASSET_IP}){

            try{
                if(!Objects.equals(selectDynamic(nAssetContent, mode), SELECT_SUCCESS)){
                    insertDynamic(nAssetContent,mode);
                }else if(Objects.equals(mode, ASSET_ALL_SELECT)){
                        return "重复资产";
                }else{
                    updateDynamic(nAssetContent,mode);
                }
            }catch (Exception e){
                e.printStackTrace();
                return "系统错误";
            }
        }

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
            Helper.dbConnector =Helper.dbConnector.isValid(2)?Helper.dbConnector :Helper.getConnector();
            assert Helper.dbConnector != null;
            PreparedStatement statement = Helper.dbConnector.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
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
            }
        }catch (Exception e){
            e.printStackTrace();
            return new String[][]{};
        }
        return result;
    }

    public static String[][] getAsset(int page, int dataNumber){
        String[][] result = new String[dataNumber][10];
        int startNumber = page*dataNumber;
        String sql = "select ip,domain,url,port,service,belong,vendor,vul,project,source from asset order by utime desc limit "+startNumber+", "+dataNumber;
        try {
            Helper.dbConnector =Helper.dbConnector.isValid(2)?Helper.dbConnector :Helper.getConnector();
            assert Helper.dbConnector != null;
            PreparedStatement statement = Helper.dbConnector.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
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

    public static HashMap<Integer,Object> getUnitContent(JComponent unitPanel){
        JTextField ipField,domainField,urlField,portField,serviceField,vulField,projectField,sourceField,belongField,vendorField;

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

        return new HashMap<Integer, Object>(){
            {
                put(ASSET_IP,ipField.getText());
                put(ASSET_DOMAIN,domainField.getText());
                put(ASSET_URL,urlField.getText());
                put(ASSET_PORT,portField.getText());
                put(ASSET_SERVICE,serviceField.getText());
                put(ASSET_BELONG,belongField.getText());
                put(ASSET_VENDOR,vendorField.getText());
                put(ASSET_VUL,vulField.getText());
                put(ASSET_PROJECT,projectField.getText());
                put(ASSET_SOURCE,sourceField.getText());
                put(ASSET_ALL_UPDATE,new String[]{
                        domainField.getText(),
                        urlField.getText(),
                        serviceField.getText(),
                        belongField.getText(),
                        vendorField.getText(),
                        vulField.getText(),
                        projectField.getText(),
                        sourceField.getText(),
                        ipField.getText(),
                        portField.getText()
                });
                put(ASSET_ALL_INSERT,new String[]{
                        ipField.getText(),
                        domainField.getText(),
                        urlField.getText(),
                        portField.getText(),
                        serviceField.getText(),
                        belongField.getText(),
                        vendorField.getText(),
                        vulField.getText(),
                        projectField.getText(),
                        sourceField.getText()
                });
            }
        };
    }

    public static JComponent NAssetMainHistoryPanel(){
        JPanel NAssetMainHistoryPanel = new JPanel(new GridBagLayout());
        NAssetMainHistoryPanel.setName("NAssetMainHistoryPanel");
        NAssetMainHistoryPanel.setPreferredSize(new Dimension(0,0));
        NAssetMainHistoryPanel.setBackground(Color.WHITE);

        String[] names = new String[]{"Ip","Port","Project","Service","Belong","Vendor","Source"};

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
            Helper.dbConnector =Helper.dbConnector.isValid(2)?Helper.dbConnector :Helper.getConnector();
            assert Helper.dbConnector != null;
            PreparedStatement statement=Helper.dbConnector.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
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

    public static void refreshAddPanel(){
//        AAssetFrame.removeAll();
        AAssetComponentPanel = new AAssetComponent().main();
        AAssetFrame.setContentPane(AAssetComponentPanel);
//        AAssetFrame.repaint();
        AAssetFrame.repaint();
        AAssetFrame.revalidate();
    }

}
