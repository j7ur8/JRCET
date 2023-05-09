package jrcet.listener;

import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.ui.contextmenu.ContextMenuEvent;
import jrcet.help.Helper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import static burp.MyExtender.API;
import static jrcet.frame.Tools.Captcha.CaptchaComponent.CaptchaComponentPanel;

public class CaptchaContextMenuItemActionListener implements ActionListener {

    private final ContextMenuEvent event;
    public CaptchaContextMenuItemActionListener(ContextMenuEvent event) {
        this.event = event;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        List<HttpRequestResponse> httpRequestResponseList = event.selectedRequestResponses();
        if(httpRequestResponseList.size()!=1){
            API.logging().error().println("httpRequestResponseList.size()!=1");
            return;
        }
        HttpRequest httpRequest = httpRequestResponseList.get(0).httpRequest();
        String urlString = httpRequest.url();
        String reqString = httpRequest.toString();

        try{
            JTextField urlField = (JTextField) Helper.getComponent(CaptchaComponentPanel, "CaptchaMainCaptchaRequestMenuUrlField");
            JTextArea requestArea = (JTextArea) Helper.getComponent(CaptchaComponentPanel, "CaptchaMainCaptchaRequestArea");
            assert urlField != null;
            urlField.setText(urlString);
            assert requestArea != null;
            requestArea.setText(reqString);
        }catch (Exception exception){
            API.logging().error().println(exception);
        }
    }
}
