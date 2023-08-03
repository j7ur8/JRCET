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
import static jrcet.frame.Tools.Captcha.CaptchaComponent.*;

public class Captcha {


    public static String identifyCaptcha() {
        try {

            String imageRule = getImageRuleField().getText();
            String tokenRule = getTokenRuleField().getText();

            String response = new String(API.http().sendRequest(CaptchaRequestEditor.getRequest()).response().body().getBytes(),StandardCharsets.UTF_8);

            String imageText = Helper.matchByRegular(response, imageRule);
            imageText = Helper.isBase64(imageText) ? imageText : Helper.base64Encode(imageText);
            ByteArrayInputStream in = new ByteArrayInputStream(Helper.base64Decode(imageText));

            return (Objects.equals(tokenRule, "")?"":Helper.matchByRegular(response,tokenRule)) +
                    getExtStrField().getText()+
                    OCREngine.instance().recognize(ImageIO.read(in));

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

    public static JTextField getExtStrField(){
        return (JTextField) Helper.getComponent(CaptchaComponentPanel, "CaptchaMenuResponseExtField");
    }
}
