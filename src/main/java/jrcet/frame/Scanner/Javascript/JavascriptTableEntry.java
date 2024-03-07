package jrcet.frame.Scanner.Javascript;

public class JavascriptTableEntry {


    private String Number;
    private String URL;
    private boolean IDCard;
    private boolean AccessKey;
    private boolean Token;
    private boolean PrivateKey;
    private boolean APPID;
    private boolean APPKey;
    private boolean WebHook;
    private boolean Password;

    public Object[] getRow(){
        return new Object[]{Number,URL,IDCard,PrivateKey,Password,AccessKey,Token,APPID,APPKey,WebHook};
    }
    public void setNumber(String number){
        Number = number;
    }
    public String getNumber() {
        return Number;
    }

    public void setURL(String url){
        URL=url;
    }

    public String getURL(){
        return URL;
    }

    public void setIDCard(boolean idCard){
        IDCard = idCard;
    }

    public boolean getIDCard(){
        return IDCard;
    }

    public void setAccessKey(boolean accessKey) {
        AccessKey = accessKey;
    }
    public boolean getAccessKey(){
        return AccessKey;
    }

    public void setToken(boolean token) {
        Token = token;
    }
    public boolean getToken(){
        return Token;
    }
    public void setPrivateKey(boolean privateKey) {
        PrivateKey = privateKey;
    }
    public boolean getPrivateKey(){
        return PrivateKey;
    }
    public void setAPPID(boolean appid) {
        APPID = appid;
    }
    public boolean getAPPID(){
        return APPID;
    }

    public void setAPPKey(boolean appKey) {
        APPKey = appKey;
    }
    public boolean getAPPKey(){
        return APPKey;
    }

    public void setWebHook(boolean webHook) {
        WebHook = webHook;
    }
    public boolean getWebHook(){
        return WebHook;
    }

    public void setPassword(boolean password) {
        Password = password;
    }
    public boolean getPassword(){
        return Password;
    }
}
