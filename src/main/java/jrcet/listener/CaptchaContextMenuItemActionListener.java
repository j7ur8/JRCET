package jrcet.listener;

import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.ui.contextmenu.ContextMenuEvent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static jrcet.frame.Tools.Captcha.Captcha.getUrlField;
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
