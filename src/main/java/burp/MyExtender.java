package burp;

import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;
import jrcet.frame.Jrcet;

public class MyExtender implements BurpExtension{

    public static MontoyaApi api;

    public MyExtender(){
    }

    @Override
    public void initialize(MontoyaApi api) {
        api.misc().setExtensionName("JRCET");

        api.userInterface().registerSuiteTab("JRCET",new Jrcet().main());

        api.scanner().registerScanCheck(new MyScanCheck());

        api.intruder().registerPayloadProcessor(new MyPayloadProcessor());

        api.userInterface().createHttpRequestEditor()
        MyExtender.api =api;
    }


}