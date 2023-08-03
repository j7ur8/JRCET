package jrcet.frame.Tools.Captcha;

import burp.api.montoya.http.HttpService;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.http.message.responses.HttpResponse;
import jrcet.diycomponents.DiyJTextField;
import jrcet.help.Helper;
import jrcet.help.d4ocr.OCREngine;


import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static burp.MyExtender.API;
import static jrcet.frame.Tools.Captcha.CaptchaComponent.CaptchaComponentPanel;
import static jrcet.frame.Tools.Captcha.CaptchaComponent.CaptchaResponseEditor;

public class Captcha {


    public static String identifyCaptcha() {
        try {
//            JTextField urlField = (JTextField) Helper.getComponent(CaptchaComponentPanel, "CaptchaMainCaptchaRequestMenuUrlField");
//            assert urlField != null;
            JTextField ruleField = (JTextField) Helper.getComponent(CaptchaComponentPanel, "CaptchaMainCaptchaResponseMenuRuleFiled");assert ruleField != null;

            JTextField rule1Field = (JTextField) Helper.getComponent(CaptchaComponentPanel, "CaptchaMainCaptchaResponseMenuRule1Filed");assert rule1Field != null;

            JTextField StrField = (JTextField) Helper.getComponent(CaptchaComponentPanel, "CaptchaMainCaptchaResponseMenuStrField");assert StrField != null;

            JTextArea requestArea = (JTextArea) Helper.getComponent(CaptchaComponentPanel, "CaptchaMainCaptchaRequestArea"); assert requestArea != null;

            String rule = ruleField.getText();
            String rule1 = rule1Field.getText();

            String response = new String(CaptchaResponseEditor.getResponse().body().getBytes(),StandardCharsets.UTF_8);

            String imageText = Helper.matchByRegular(response, rule);

            imageText = Helper.isBase64(imageText) ? imageText : Helper.base64Encode(imageText);

            ByteArrayInputStream in = new ByteArrayInputStream(Helper.base64Decode(imageText));

            String res = OCREngine.instance().recognize(ImageIO.read(in));

            String Str = StrField.getText();

            if(!Objects.equals(Str, "")){
                res =  Objects.equals(rule1, "")?"":Helper.matchByRegular(response,rule1)
                        +Str+res;
            }

            return res;

        } catch (Exception e) {
            API.logging().output().println(e.getMessage());
        }

        return "ErrorOrz";
    }


    public static class setCaptchaWorker extends SwingWorker<String, Void> {

        protected HttpRequest httpRequest;
        public setCaptchaWorker(HttpRequest httpRequest) {
            this.httpRequest = httpRequest;
        }

        @Override
        protected String doInBackground() {
            CaptchaResponseEditor.setResponse(API.http().sendRequest(httpRequest).response());
            return null;
        }
    }

    public static JTextField getUrlField(){
        return (JTextField) Helper.getComponent(CaptchaComponentPanel, "CaptchaMenuRequestUrlField");
    }
    public static JTextField getImageRuleField(){
        return (JTextField) Helper.getComponent(CaptchaComponentPanel, "CaptchaMenuResponseImageField");
    }
    public static JTextField getTokenRuleField(){
        return (JTextField) Helper.getComponent(CaptchaComponentPanel, "CaptchaMenuResponseTokenField");
    }
}
