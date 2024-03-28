package jrcet.listener;

import burp.api.montoya.http.message.HttpHeader;
import burp.api.montoya.http.message.params.ParsedHttpParameter;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.ui.contextmenu.ContextMenuEvent;
import com.alibaba.fastjson.JSON;


import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static burp.MyExtender.BurpAPI;
import static jrcet.Main.centerInScreen;
import static jrcet.frame.Tools.RScript.RScript.*;
import static help.Helper.getEncoding;

public class RScriptContextMenuItemActionListener implements ActionListener {

    private static final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    private final ContextMenuEvent event;
    private final Map<String,String> URLParameterMap = new HashMap<>();
    private final Map<String,String> CookieParameterMap = new HashMap<>();
    private final Map<String,String> BodyParameterMap = new HashMap<>();
    private final Map<String,String> HeaderMap = new HashMap<>();


    public RScriptContextMenuItemActionListener(ContextMenuEvent event) {
        this.event = event;

        if(RScriptFrame==null)RScriptFrame = new JFrame("RScript");
        if(RScriptPanel==null)RScriptPanel=new JPanel(new GridBagLayout());
        if(RScriptButton==null){
//            API.logging().output().println("asdf");
            RScriptButton=new JButton("Copy");
            RScriptButton.addMouseListener(new MouseListener(){

                @Override
                public void mouseClicked(MouseEvent e) {
//                    API.logging().output().println(1);
                }

                @Override
                public void mousePressed(MouseEvent e) {
//                    API.logging().output().println(2);
                }

                @Override
                public void mouseReleased(MouseEvent e) {
//                    API.logging().output().println(((JComponent)e.getSource()).getParent().getComponents().length);
//                    String text = new String(rawEditor.getContents().getBytes(),StandardCharsets.UTF_8);
                    String text = RScriptEditor.getText();
//                    API.logging().output().println(text);
                    StringSelection selection = new StringSelection(text);
                    clipboard.setContents(selection,null);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
//                    API.logging().output().println(3);
                }

                @Override
                public void mouseExited(MouseEvent e) {
//                    API.logging().output().println(4);
                }
            });
        }
        RScriptPanel.add(RScriptButton, new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(5,5,5,5),
                0,0
        ));
        RScriptPanel.add(RScriptEditor,new GridBagConstraints(
                0,1,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(5,5,5,5),
                0,0
        ));
        RScriptFrame.setContentPane(RScriptPanel);
        RScriptFrame.setSize(600, 500);
        RScriptFrame.setResizable(false);
        centerInScreen(RScriptFrame);
        RScriptFrame.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            HttpRequest httpRequest = event.messageEditorRequestResponse().orElseThrow().requestResponse().request();

            List<HttpHeader> headers = httpRequest.headers();
//            headers.remove(0);
            for(HttpHeader header:headers){
                if(Objects.equals(header.name(), "Cookie"))continue;
                HeaderMap.put(header.name(),header.value());
            }
            List<ParsedHttpParameter> parameters = httpRequest.parameters();
            for(ParsedHttpParameter parameter:parameters){
                switch (parameter.type()) {
                    case URL -> URLParameterMap.put(parameter.name(), parameter.value());
                    case COOKIE -> CookieParameterMap.put(parameter.name(), parameter.value());
                    case BODY -> {
                        BodyParameterMap.put(parameter.name(), parameter.value());
                        BurpAPI.logging().output().println(parameter.value());
                        BurpAPI.logging().output().println(parameter.value().getBytes());
                        BurpAPI.logging().output().println(getEncoding(parameter.value()));
                    }
                }
            }
            String method = httpRequest.method().toLowerCase();
            String url = httpRequest.url().split("\\?")[0];
            String urlParameter = JSON.toJSONString(URLParameterMap).replace("\",\"","\",\n\t\"").replace("{\"","{\n\t\"").replace("\"}","\"\n}");
            String bodyParameter = JSON.toJSONString(BodyParameterMap).replace("\",\"","\",\n\t\"").replace("{\"","{\n\t\"").replace("\"}","\"\n}");
            String headerParameter = JSON.toJSONString(HeaderMap).replace("\",\"","\",\n\t\"").replace("{\"","{\n\t\"").replace("\"}","\"\n}");
            String cookieParameter = JSON.toJSONString(CookieParameterMap).replace("\",\"","\",\n\t\"").replace("{\"","{\n\t\"").replace("\"}","\"\n}");

            String requestCodeModel = "import requests\n\nsession = requests.Session()\n\nparamsGet = %s\nparamsPost = %s\nheaders = %s\ncookies = %s\nresponse = session.%s(\"%s\", data=paramsPost, params=paramsGet, headers=headers, cookies=cookies)\n";
            String pythonRequest = String.format(
                    requestCodeModel,
                    urlParameter,
                    bodyParameter,
                    headerParameter,
                    cookieParameter,
                    method,
                    url);



            RScriptEditor.setText(pythonRequest);
            RScriptFrame.setVisible(true);


        }catch (Exception ee){
            BurpAPI.logging().error().println(ee);
        }

    }
}
