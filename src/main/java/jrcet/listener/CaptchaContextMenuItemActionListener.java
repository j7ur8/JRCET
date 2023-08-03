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
import static jrcet.frame.Tools.Captcha.Captcha.getUrlField;
import static jrcet.frame.Tools.Captcha.CaptchaComponent.CaptchaComponentPanel;
import static jrcet.frame.Tools.Captcha.CaptchaComponent.CaptchaRequestEditor;

public class CaptchaContextMenuItemActionListener implements ActionListener {

    private final ContextMenuEvent event;
    public CaptchaContextMenuItemActionListener(ContextMenuEvent event) {
        this.event = event;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        HttpRequest httpRequest = event.messageEditorRequestResponse().orElseThrow().requestResponse().request();
        getUrlField().setText(httpRequest.url());
        CaptchaRequestEditor.setRequest(httpRequest);
    }
}
