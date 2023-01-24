package jrcet.frame.Tools.Captcha;

import burp.BurpExtender;
import jrcet.diycomponents.DiyJTextArea.ui.rsyntaxtextarea.RSyntaxTextArea;
import jrcet.help.Helper;
import jrcet.help.d4ocr.OCREngine;
import jrcet.help.d4ocr.network.HttpClient;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

import static jrcet.frame.Tools.Captcha.CaptchaComponent.CaptchaComponentPanel;

public class Captcha {


    public static String identifyCaptcha() {
        try {

            JTextField urlField = (JTextField) Helper.getComponent(CaptchaComponentPanel, "CaptchaMainCaptchaRequestMenuUrlField");
            assert urlField != null;
            JTextField ruleField = (JTextField) Helper.getComponent(CaptchaComponentPanel, "CaptchaMainCaptchaResponseMenuRuleFiled");
            assert ruleField != null;
            RSyntaxTextArea requestArea = (RSyntaxTextArea) Helper.getComponent(CaptchaComponentPanel, "CaptchaMainCaptchaRequestArea");
            assert requestArea != null;

            String url = urlField.getText();
            String rule = ruleField.getText();
            String requestPacket = requestArea.getText();

            byte[] response;
            BufferedImage image;

            HttpClient httpClient = new HttpClient(url, requestPacket);
            response = httpClient.doRequest();

            String responseText = new String(response);

            String imageText = Helper.matchByRegular(responseText, rule);
            imageText = Helper.isBase64(imageText) ? imageText : Helper.base64Encode(imageText);

            ByteArrayInputStream in = new ByteArrayInputStream(Helper.base64Decode(imageText));

            image = ImageIO.read(in);
            OCREngine engine = OCREngine.instance();
            return engine.recognize(image);

        } catch (Exception e) {
            BurpExtender.stdout.println(e);
        }

        return "ErrorOrz";
    }

    public static class getCaptchaThread extends Thread{
        public String url;
        public String raw;
        public String rule;
        public RSyntaxTextArea responseArea;
        public getCaptchaThread(String url, String raw,RSyntaxTextArea responseArea){
            this.url = url;
            this.raw = raw;
            this.responseArea = responseArea;
        }



        public void run() {
            byte[] response = new byte[0];
            try {
                HttpClient httpClient = new HttpClient(url, raw);
                response = httpClient.doRequest();
            } catch (Exception ee) {
                BurpExtender.stdout.println(ee);
            }
            String responseText = new String(response);
            responseArea.setText(responseText);
        }
    }
}
