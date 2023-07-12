package jrcet.frame.Tools.Captcha;

import burp.api.montoya.http.HttpService;
import burp.api.montoya.http.message.requests.HttpRequest;
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

public class Captcha {

    public static byte[] responseText="".getBytes();

    public static String identifyCaptcha() {
        try {

            JTextField urlField = (JTextField) Helper.getComponent(CaptchaComponentPanel, "CaptchaMainCaptchaRequestMenuUrlField");
            assert urlField != null;
            JTextField ruleField = (JTextField) Helper.getComponent(CaptchaComponentPanel, "CaptchaMainCaptchaResponseMenuRuleFiled");
            assert ruleField != null;
            JTextField rule1Field = (JTextField) Helper.getComponent(CaptchaComponentPanel, "CaptchaMainCaptchaResponseMenuRule1Filed");
            assert rule1Field != null;
            JTextField StrField = (JTextField) Helper.getComponent(CaptchaComponentPanel, "CaptchaMainCaptchaResponseMenuStrField");
            assert StrField != null;
            JTextArea requestArea = (JTextArea) Helper.getComponent(CaptchaComponentPanel, "CaptchaMainCaptchaRequestArea");
            assert requestArea != null;

            String url = urlField.getText();
            String rule = ruleField.getText();
            String requestPacket = requestArea.getText();

            byte[] response;
            BufferedImage image;

            HttpRequest httpRequest = HttpRequest.httpRequest(requestPacket).withService(HttpService.httpService(url));
            response = API.http().sendRequest(httpRequest).response().body().getBytes();

            String token="";
            String rule1 = rule1Field.getText();

            if(!Objects.equals(rule1, "")){
                token=Helper.matchByRegular(new String(response,StandardCharsets.UTF_8),rule1);
            }

            String imageText = Helper.matchByRegular(new String(response,StandardCharsets.UTF_8), rule);
            imageText = Helper.isBase64(imageText) ? imageText : Helper.base64Encode(response);

            ByteArrayInputStream in = new ByteArrayInputStream(Helper.base64Decode(imageText));

            image = ImageIO.read(in);
            OCREngine engine = OCREngine.instance();
            String res = engine.recognize(image);

            String Str = StrField.getText();
            if(!Objects.equals(Str, "")){
                return token+Str+res;
            }
            return res;

        } catch (Exception e) {
            API.logging().output().println(e.getMessage());
        }

        return "ErrorOrz";
    }

    public static class getCaptchaThread extends Thread{
        public String url;
        public String raw;
        public JTextArea responseArea;
        public getCaptchaThread(String url, String raw, JTextArea responseArea){
            this.url = url;
            this.raw = raw;
            this.responseArea = responseArea;
        }

        public void run() {
            byte[] response= "".getBytes();
            try {
                HttpRequest httpRequest = HttpRequest.httpRequest(raw).withService(HttpService.httpService(url));

                response = API.http().sendRequest(httpRequest).response().body().getBytes();
                responseText= response;
            } catch (Exception e) {
                API.logging().output().println(e.getMessage());
            }

            responseArea.setText(new String(response,StandardCharsets.UTF_8));
        }
    }
}
