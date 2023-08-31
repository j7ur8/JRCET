package jrcet.frame.Tools.Captcha;

import burp.api.montoya.http.message.requests.HttpRequest;
import jrcet.help.Helper;
import jrcet.help.d4ocr.OCREngine;


import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static burp.MyExtender.API;
import static jrcet.frame.Tools.Captcha.CaptchaComponent.*;

public class Captcha {


    public static String identifyCaptchaForIntruder() {
        try {

            String imageRule = getImageRuleField().getText();
            String tokenRule = getTokenRuleField().getText();

            String response = new String(API.http().sendRequest(CaptchaRequestEditor.getRequest()).response().body().getBytes(),StandardCharsets.UTF_8);

            String imageText = Helper.matchByRegular(response, imageRule);
            imageText = Helper.isBase64(imageText) ? imageText : Helper.base64Encode2String(imageText);
            ByteArrayInputStream in = new ByteArrayInputStream(Helper.base64Decode2Byte(imageText));

            return (Objects.equals(tokenRule, "") ? "" : Helper.matchByRegular(response,tokenRule)) +
                    getExtStrField().getText()+
                    OCREngine.instance().recognize(ImageIO.read(in));

        } catch (Exception e) {
            API.logging().output().println(e.getMessage());
        }

        return "ErrorOrz";
    }

    public static void identifyCaptcha(){
        String responseText = new String(CaptchaResponseEditor.getResponse().body().getBytes(), StandardCharsets.UTF_8);
//                API.logging().output().println(responseText);

//                API.logging().output().println(getImageRuleField());
//                API.logging().output().println(getImageRuleField().getText());
        String imageText = Helper.matchByRegular(responseText, getImageRuleField().getText());
        imageText = Helper.isBase64(imageText) ? imageText : Helper.base64Encode2String(imageText);
//                API.logging().output().println(imageText);

        ByteArrayInputStream in = new ByteArrayInputStream(Helper.base64Decode2Byte(imageText));
        BufferedImage image = null;
        try {
            image = ImageIO.read(in);
        } catch (IOException ignored) {

        }
        OCREngine engine = OCREngine.instance();
        String res = engine.recognize(image);
//                API.logging().output().println(res);

        JLabel resultLabel = new JLabel(res);
        resultLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));

        String token = "";
        String rule1 = getTokenRuleField().getText();
        if (!Objects.equals(rule1, "")) {
            token = Helper.matchByRegular(responseText, rule1);
        }
        JLabel tokenLabel = new JLabel(token);
        tokenLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        byte[] imageByte = Helper.base64Decode2Byte(imageText);
        ImageIcon imageIcon = Helper.byte2img(imageByte);
        JLabel imageLabel = new JLabel(imageIcon, JLabel.CENTER);
        imageLabel.setPreferredSize(new Dimension(160, 30));

        JPanel CaptchaMenuResultPanel = getCaptchaMenuResultPanel();
        CaptchaMenuResultPanel.removeAll();
        CaptchaMenuResultPanel.add(imageLabel);
        CaptchaMenuResultPanel.add(resultLabel);
        CaptchaMenuResultPanel.add(tokenLabel);
        CaptchaMenuResultPanel.repaint();
        CaptchaMenuResultPanel.revalidate();
    }

    public static JPanel getCaptchaMenuResultPanel() {
        return (JPanel) Helper.getComponent(CaptchaComponentPanel, "CaptchaMenuResultPanel");
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
