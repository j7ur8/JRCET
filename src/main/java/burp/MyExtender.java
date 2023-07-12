package burp;

import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;
import jrcet.frame.Jrcet;

public class MyExtender implements BurpExtension{

    public static MontoyaApi API;

    public MyExtender(){
    }

    @Override
    public void initialize(MontoyaApi api) {

        MyExtender.API =api;
        API.extension().setName("JRCET");
//        API.misc().setExtensionName("JRCET");
        API.userInterface().registerSuiteTab("JRCET",new Jrcet().main());

        API.scanner().registerScanCheck(new MyScanCheck());

        API.intruder().registerPayloadProcessor(new MyPayloadProcessor());

        API.userInterface().registerContextMenuItemsProvider(new MyContextMenuItemsProvider());
//        API.userInterface().registerHttpRequestEditorProvider(new MyRegisterHttpRequestEditorProvider());
        API.userInterface().registerHttpResponseEditorProvider(new MyRegisterHttpResponseEditorProvider());

    }


}