package burp;

import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.ui.Selection;
import burp.api.montoya.ui.editor.HttpRequestEditor;
import burp.api.montoya.ui.editor.extension.EditorMode;
import burp.api.montoya.ui.editor.extension.ExtensionHttpRequestEditor;
import burp.api.montoya.ui.editor.extension.ExtensionHttpRequestEditorProvider;

import javax.swing.*;
import java.awt.*;

import static burp.MyExtender.API;

public class MyRegisterHttpRequestEditorProvider implements ExtensionHttpRequestEditorProvider {

    @Override
    public ExtensionHttpRequestEditor provideHttpRequestEditor(HttpRequestResponse httpRequestResponse, EditorMode editorMode) {
        return new MyExtensionHttpRequestEditor();
    }

    private static class MyExtensionHttpRequestEditor implements ExtensionHttpRequestEditor{

        HttpRequestResponse MyHttpRequestResponse;
        JPanel MainPanel;
        HttpRequestEditor MyHttpRequestEditor;
        MyExtensionHttpRequestEditor(){
            this.MainPanel = new JPanel();
            MyHttpRequestEditor = API.userInterface().createHttpRequestEditor();
            this.MainPanel.add(MyHttpRequestEditor.uiComponent());
        }
        @Override
        public HttpRequest getHttpRequest() {
            return this.MyHttpRequestResponse.httpRequest().addHeader("111","222");
        }

        @Override
        public void setHttpRequestResponse(HttpRequestResponse httpRequestResponse) {
            this.MyHttpRequestResponse = httpRequestResponse;
        }

        @Override
        public boolean isEnabledFor(HttpRequestResponse httpRequestResponse) {
            HttpRequest httprequest = httpRequestResponse.httpRequest();
            return httprequest.url().contains("com");
        }

        @Override
        public String caption() {
            return "Test";
        }

        @Override
        public Component uiComponent() {
            return this.MainPanel;
        }

        @Override
        public Selection selectedData() {
            return null;
        }

        @Override
        public boolean isModified() {
            return false;
        }
    }
}
