package burp;

import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;
import jrcet.frame.Dencrypt.Hex.Hex;
import jrcet.frame.Dencrypt.Jwt.Jwt;
import jrcet.frame.Intruder.*;
import jrcet.frame.Jrcet;

public class MyExtender implements BurpExtension{

    public static MontoyaApi API;

    public MyExtender(){
    }

    @Override
    public void initialize(MontoyaApi api) {

        MyExtender.API =api;
        API.extension().setName("JRCET");
        API.userInterface().registerSuiteTab("JRCET",new Jrcet().component());

        API.intruder().registerPayloadProcessor(new CaptchaPayloadProcessor());
        API.intruder().registerPayloadProcessor(new AesPayloadProcessor());
        API.intruder().registerPayloadProcessor(new AsciiPayloadProcessor());
        API.intruder().registerPayloadProcessor(new BasePayloadProcessor());
        API.intruder().registerPayloadProcessor(new DesPayloadProcessor());
        API.intruder().registerPayloadProcessor(new HexPayloadProcessor());
        API.intruder().registerPayloadProcessor(new JwtPayloadProcessor());
        API.intruder().registerPayloadProcessor(new RsaPayloadProcessor());
        API.intruder().registerPayloadProcessor(new UnicodePayloadProcessor());
        API.intruder().registerPayloadProcessor(new UrlPayloadProcessor());


                API.userInterface().registerContextMenuItemsProvider(new MyContextMenuItemsProvider());
        API.userInterface().registerHttpResponseEditorProvider(new MyRegisterHttpResponseEditorProvider());
        API.http().registerHttpHandler(new MyRegisterHttpHandler());

    }


}