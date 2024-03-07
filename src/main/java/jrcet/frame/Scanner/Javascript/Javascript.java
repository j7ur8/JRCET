package jrcet.frame.Scanner.Javascript;

import burp.api.montoya.core.Annotations;
import burp.api.montoya.http.handler.HttpResponseReceived;
import jrcet.diycomponents.DiyJLogTable;
import jrcet.help.Helper;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static burp.MyExtender.BurpAPI;
import static jrcet.frame.Scanner.Fastjson.FastjsonComponent.FastjsonComponentPanel;
import static jrcet.frame.Scanner.Javascript.JavascriptComponent.JavascriptComponentPanel;

public class Javascript {

    public static boolean JavascriptCheck = false;
    public static final String JAVASCRIPT = "JAVASCRIPT";
    private static final ReentrantLock JavascriptTableSerialNumberLock = new ReentrantLock();
    public static ArrayList<String> JavascriptCheckedUrlList = new ArrayList<>();
    public static String JavascriptLoggerTableSerialNumber = "0";

    public static HashMap<String,String> JavascriptResultMap = new HashMap<>();
    public static final HashMap<String, String> JavascriptCheckMap = new HashMap<>() {
        {
            put("IDCard", "\\b([1-9]\\d{5}(19|20)\\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx])\\b");
            put("PrivateKey", "-----\\s*?BEGIN[ A-Z0-9_-]*?PRIVATE KEY\\s*?-----[a-zA-Z0-9\\/\\n\\r=+]*-----\\s*?END[ A-Z0-9_-]*? PRIVATE KEY\\s*?-----");
            put("Password", "(?i)(?:admin_?pass|password|[a-z]{3,15}_?password|user_?pass|user_?pwd|admin_?pwd)\\\\?['\"]*\\s*[:=]\\s*\\\\?['\"]{0,}[a-z0-9!@#$%&*]{5,20}\\\\?['\"]{0,}");
            put("AccessKey_Aliyun", "\\bLTAI[A-Za-z\\d]{12,30}\\b");
            put("AccessKey_QCloud", "\\bAKID[A-Za-z\\d]{13,40}\\b");
            put("AccessKey_JDCloud", "\\bJDC_[0-9A-Z]{25,40}\\b");
            put("AccessKey_AWS", "\\b(?:A3T[A-Z0-9]|AKIA|AGPA|AIDA|AROA|AIPA|ANPA|ANVA|ASIA)[A-Z0-9]{16}\\b");
            put("AccessKey_VolcanoEngine", "\\b(?:AKLT|AKTP)[a-zA-Z0-9]{35,50}\\b");
            put("AccessKey_Kingsoft", "\\bAKLT[a-zA-Z0-9-_]{16,28}\\b");
            put("AccessKey_GCP", "\\bAIza[0-9A-Za-z_\\-]{35}\\b");
            put("Token_Bear", "\\b[Bb]earer\\s+[a-zA-Z0-9\\-=._+/\\\\]{20,500}\\b");
            put("Token_Basic", "\\b[Bb]asic\\s+[A-Za-z0-9+/]{18,}={0,2}\\b");
            put("Token_Auth", "\\b[Aa]uthorization\\b\\s*[:=]\\s*['\\\"]?\\b(?:[Tt]oken\\s+)?[a-zA-Z0-9\\-_+/\\.]{20,500}\\b");
            put("Token_JWT", "eyJ[A-Za-z0-9_/+\\-]{10,}={0,2}\\.[A-Za-z0-9_/+\\-\\\\]{15,}={0,2}\\.[A-Za-z0-9_/+\\-\\\\]{10,}={0,2}");
            put("Token_GrafanaCloudAPI", "\\bglc_[A-Za-z0-9\\-_+/]{32,200}={0,2}\\b");
            put("Token_GrafanaServiceAccount", "\\bglsa_[A-Za-z0-9]{32}_[A-Fa-f0-9]{8}\\b");
            put("Token_Gitlab", "\\b(glpat-[a-zA-Z0-9\\-=_]{20,22})\\b");
            put("Token_Github", "\\b((?:ghp|gho|ghu|ghs|ghr|github_pat)_[a-zA-Z0-9_]{36,255})\\b");
            put("APPKey_QCloudAPI", "\\bAPID[a-zA-Z0-9]{32,42}\\b");
            put("APPKey_Default", "\\b(?:VUE|APP|REACT)_[A-Z_0-9]{1,15}_(?:KEY|PASS|PASSWORD|TOKEN|APIKEY)['\\\"]*[:=]['\\\"]*(?:[A-Za-z0-9_\\-]{15,50}|[a-z0-9/+]{50,100}==?)['\\\"]*");
            put("APPKey_Grafana", "\\beyJrIjoi[a-zA-Z0-9\\-_+/]{50,100}={0,2}\\b");
            put("APPID_WechatAPP", "\\b(wx[a-z0-9]{15,18})\\b");
            put("APPID_WechatCorp", "\\b(ww[a-z0-9]{15,18})\\b");
            put("APPID_Wechat", "\\b(gh_[a-z0-9]{11,13})\\b");
            put("WebHook_WechatCorp", "\\bhttps://qyapi.weixin.qq.com/cgi-bin/webhook/send\\?key=[a-zA-Z0-9\\-]{25,50}\\b");
            put("WebHook_Dingtalk", "\\bhttps://oapi.dingtalk.com/robot/send\\?access_token=[a-z0-9]{50,80}\\b");
            put("WebHook_Feishu", "\\bhttps://open.feishu.cn/open-apis/bot/v2/hook/[a-z0-9\\-]{25,50}\\b");
            put("WebHook_Slack", "\\bhttps://hooks.slack.com/services/[a-zA-Z0-9\\-_]{6,12}/[a-zA-Z0-9\\-_]{6,12}/[a-zA-Z0-9\\-_]{15,24}\\b");
        }
    };

    public static void javascriptCheckResponse(HttpResponseReceived responseReceived){
        String responseBody = responseReceived.bodyToString();
        String requestsNotes = responseReceived.annotations().notes();
        String requestUrl = requestsNotes.split("¥¥")[1];
//        BurpAPI.logging().output().println(requestUrl);
        StringBuilder tmpResult = new StringBuilder();
        JavascriptTableEntry javascriptTableEntry = new JavascriptTableEntry();
        javascriptTableEntry.setURL(requestUrl);
//        BurpAPI.logging().output().println(responseBody);
        for(Map.Entry<String,String> entry : JavascriptCheckMap.entrySet()){
            String key = entry.getKey();
            String regex = entry.getValue();
            Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
            Matcher matcher = pattern.matcher(responseBody);
            while (matcher.find()) {
                switch (key.split("_")[0]){
                    case "IDCard"-> javascriptTableEntry.setIDCard(true);
                    case "PrivateKey"-> javascriptTableEntry.setPrivateKey(true);
                    case "Password"-> javascriptTableEntry.setPassword(true);
                    case "AccessKey"-> javascriptTableEntry.setAccessKey(true);
                    case "Token"-> javascriptTableEntry.setToken(true);
                    case "APPID"-> javascriptTableEntry.setAPPID(true);
                    case "APPKey"-> javascriptTableEntry.setAPPKey(true);
                    case "WebHook"-> javascriptTableEntry.setWebHook(true);
                }
                tmpResult.append(key).append("=>").append(matcher.group(0)).append("\n");
//                BurpAPI.logging().output().println(tmpResult);
            }
            JavascriptResultMap.put(requestUrl,tmpResult.toString());
        }
//        BurpAPI.logging().output().println(JavascriptResultMap.get(requestUrl));
        if(tmpResult.toString().equals("")){
            JavascriptResultMap.remove(requestUrl);
        }else{
//            BurpAPI.logging().output().println("ccc");
//            BurpAPI.logging().output().println(getJavascriptLoggerTable());
            javascriptTableEntry.setNumber(getJavascriptLoggerTableSerialNumber());
            ((DefaultTableModel)getJavascriptLoggerTable().getModel()).addRow(javascriptTableEntry.getRow());
            JavascriptCheckedUrlList.add(requestUrl);
        }
    }

    public static void main(String[] args) {
        String string = "412829199903243616\n"
                + "412829199903243611\n"
                + "412829199903243612\n" +
                "APID1111111111111111111111111111111111111111";

        String url="http://www.baidu.com/a.js";
        StringBuilder tmpResult = new StringBuilder();
        for(Map.Entry<String,String> entry : JavascriptCheckMap.entrySet()){
            String key = entry.getKey();
            String regex = entry.getValue();
            Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
            Matcher matcher = pattern.matcher(string);
            while (matcher.find()) {
                tmpResult.append(key).append("=>").append(matcher.group(0)).append("\n");
            }
//            System.out.println(tmpResult);
            JavascriptResultMap.put(url, tmpResult.toString());
        }
        System.out.println("IDCard".split("_")[0]);
    }

    public static void clearJavascriptTable(){
        ((DefaultTableModel)getJavascriptLoggerTable().getModel()).setRowCount(0);
        JavascriptCheckedUrlList.clear();
        JavascriptLoggerTableSerialNumber="0";
    }

    public static DiyJLogTable getJavascriptLoggerTable(){
        return (DiyJLogTable) Helper.getComponent(JavascriptComponentPanel, "JavascriptLoggerTable");
    }

    private static String getJavascriptLoggerTableSerialNumber() {
        JavascriptTableSerialNumberLock.lock();
        JavascriptLoggerTableSerialNumber = Integer.toString(Integer.parseInt(JavascriptLoggerTableSerialNumber)+1);
        JavascriptTableSerialNumberLock.unlock();

        return JavascriptLoggerTableSerialNumber;
    }
}
