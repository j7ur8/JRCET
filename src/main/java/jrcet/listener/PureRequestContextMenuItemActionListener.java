package jrcet.listener;

import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.ui.contextmenu.ContextMenuEvent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static jrcet.frame.Tools.Captcha.Captcha.getUrlField;
import static jrcet.frame.Tools.Captcha.CaptchaComponent.CaptchaRequestEditor;

public class PureRequestContextMenuItemActionListener implements ActionListener {

    private final String[] unusefulHeaderList = new String[]{
            "Sec-Fetch-Dest","Sec-Fetch-Mode","Sec-Fetch-Site","sec-ch-ua-mobile","sec-ch-ua","If-Modified-Since","If-None-Match","sec-ch-ua-platform","Origin","Referer"
    };
    private final ContextMenuEvent event;

    public PureRequestContextMenuItemActionListener(ContextMenuEvent event) {
        this.event = event;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        HttpRequest httpRequest = event.messageEditorRequestResponse().orElseThrow().requestResponse().request();
        for(String header: unusefulHeaderList){
            httpRequest = httpRequest.withRemovedHeader(header);
        }

        event.messageEditorRequestResponse().orElseThrow().setRequest(httpRequest);
    }
}

