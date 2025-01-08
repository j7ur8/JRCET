package burp;

import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;
import jrcet.frame.Intruder.*;
import jrcet.frame.Jrcet;

import java.sql.SQLException;

public class MyExtender implements BurpExtension{

    public static MontoyaApi BurpAPI;

    public MyExtender(){
    }

    @Override
    public void initialize(MontoyaApi api) {

        MyExtender.BurpAPI =api;
        BurpAPI.extension().setName("JRCET");
        BurpAPI.userInterface().registerSuiteTab("JRCET",new Jrcet().component());

        BurpAPI.intruder().registerPayloadProcessor(new CaptchaPayloadProcessor());
        BurpAPI.intruder().registerPayloadProcessor(new AesPayloadProcessor());
        BurpAPI.intruder().registerPayloadProcessor(new AsciiPayloadProcessor());
        BurpAPI.intruder().registerPayloadProcessor(new BasePayloadProcessor());
        BurpAPI.intruder().registerPayloadProcessor(new DesPayloadProcessor());
        BurpAPI.intruder().registerPayloadProcessor(new HexPayloadProcessor());
        BurpAPI.intruder().registerPayloadProcessor(new JwtPayloadProcessor());
        BurpAPI.intruder().registerPayloadProcessor(new RsaPayloadProcessor());
        BurpAPI.intruder().registerPayloadProcessor(new UnicodePayloadProcessor());
        BurpAPI.intruder().registerPayloadProcessor(new UrlPayloadProcessor());

        BurpAPI.userInterface().registerContextMenuItemsProvider(new MyContextMenuItemsProvider());
        BurpAPI.userInterface().registerHttpResponseEditorProvider(new MyRegisterHttpResponseEditorProvider());
        try {
            BurpAPI.http().registerHttpHandler(new MyRegisterHttpHandler());
        } catch (Exception e) {
            BurpAPI.logging().error().println(e);
        }

    }


}