package jrcet.frame.Tools.Captcha;

import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.http.message.responses.HttpResponse;
import help.Helper;
import help.d4ocr.OCREngine;


import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static burp.MyExtender.BurpAPI;
import static jrcet.frame.Tools.Captcha.CaptchaComponent.*;

public class Captcha {

    public static String DEFAULT_CODERULE="(([A-Za-z0-9+\\/]{4})*([A-Za-z0-9+\\/]{4}|[A-Za-z0-9+\\/]{3}=|[A-Za-z0-9+\\/]{2}==))";
    public static String DEFAULT_TOKENRULE = "([a-z0-9]{32})";

    public static String identifyCaptchaForIntruder() {
        try {

            String imageRule = getImageRuleField().getText();
            String tokenRule = getTokenRuleField().getText();

            HttpResponse httpResponse = BurpAPI.http().sendRequest(CaptchaRequestEditor.getRequest()).response();

            byte[] responseBytes = httpResponse.body().getBytes();
            String responseText = new String(httpResponse.body().getBytes(), StandardCharsets.UTF_8);

            String imageText = Helper.aiFindBase64(imageRule,responseText);

            byte[] imageByte = Helper.isBase64(imageText) ? Helper.base64Decode2Byte(imageText) : responseBytes;

            ByteArrayInputStream in = new ByteArrayInputStream(imageByte);

            return (Objects.equals(tokenRule, "") ? "" : Helper.matchByRegular(responseText, tokenRule)) +
                    getExtStrField().getText()+
                    OCREngine.instance().recognize(ImageIO.read(in));

        } catch (Exception e) {
            BurpAPI.logging().output().println(e.getMessage());
        }

        return "ErrorOrz";
    }

    public static void identifyCaptcha(){

        try{

            byte[] responseBytes = CaptchaResponseEditor.getResponse().body().getBytes();
            String responseText = new String(CaptchaResponseEditor.getResponse().body().getBytes(), StandardCharsets.UTF_8);

//            String imageText = Helper.matchByRegular(responseText, getImageRuleField().getText());
            String imageText = Helper.aiFindBase64(getImageRuleField().getText(),responseText);
//            BurpAPI.logging().output().println(imageText);
            byte[] imageByte = Helper.isBase64(imageText) ? Helper.base64Decode2Byte(imageText) : responseBytes;

            ByteArrayInputStream in = new ByteArrayInputStream(imageByte);
            BufferedImage image = null;
            try {
                image = ImageIO.read(in);
            } catch (Exception e) {
                BurpAPI.logging().error().println(e);
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
        }catch (Exception ee){
            BurpAPI.logging().error().println(ee);
        }
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
            CaptchaResponseEditor.setResponse(BurpAPI.http().sendRequest(httpRequest).response());
            return null;
        }
    }

    public static void reset(){
        (getImageRuleField()).setText(Captcha.DEFAULT_CODERULE);
        (getTokenRuleField()).setText(Captcha.DEFAULT_TOKENRULE);
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
