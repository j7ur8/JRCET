package burp;

import burp.api.montoya.http.MimeType;
import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.http.message.responses.HttpResponse;
import burp.api.montoya.ui.Selection;
import burp.api.montoya.ui.editor.HttpResponseEditor;
import burp.api.montoya.ui.editor.RawEditor;
import burp.api.montoya.ui.editor.extension.EditorMode;
import burp.api.montoya.ui.editor.extension.ExtensionHttpResponseEditor;
import burp.api.montoya.ui.editor.extension.ExtensionHttpResponseEditorProvider;
import jrcet.diycomponents.DiyJTextAreaScrollPane;
import jrcet.frame.Tools.Dencrypt.Unicode.Unicode;

import javax.swing.*;
import java.awt.*;
import java.nio.charset.StandardCharsets;

import static burp.MyExtender.API;

public class MyRegisterHttpResponseEditorProvider implements ExtensionHttpResponseEditorProvider {
    @Override
    public ExtensionHttpResponseEditor provideHttpResponseEditor(HttpRequestResponse httpRequestResponse, EditorMode editorMode) {
        return new MyExtensionHttpResponseEditor();
    }

    private static class MyExtensionHttpResponseEditor implements ExtensionHttpResponseEditor{

        HttpRequestResponse MyHttpRequestResponse;
        RawEditor MyExtenderEditor;
        MyExtensionHttpResponseEditor(){
            MyExtenderEditor = API.userInterface().createRawEditor();
        }

        @Override
        public void setHttpRequestResponse(HttpRequestResponse requestResponse) {
            MyHttpRequestResponse = requestResponse;
            HttpResponse httpResponse = MyHttpRequestResponse.httpResponse();
            httpResponse = httpResponse.withBody(Unicode.unicodeToString(new String(httpResponse.body(), StandardCharsets.UTF_8)));
            MyExtenderEditor.setContents(httpResponse.asBytes());
        }

        @Override
        public boolean isEnabledFor(HttpRequestResponse requestResponse) {
            HttpResponse httpResponse = requestResponse.httpResponse();
            return (httpResponse.inferredMimeType() == MimeType.JSON || httpResponse.statedMimeType() == MimeType.JSON);
        }

        @Override
        public String caption() {
            return "Extender";
        }

        @Override
        public Component uiComponent() {
            return MyExtenderEditor.uiComponent();
        }

        @Override
        public Selection selectedData() {
            return null;
        }

        @Override
        public boolean isModified() {
            return false;
        }

        @Override
        public HttpResponse getHttpResponse() {
            return HttpResponse.httpResponse(MyExtenderEditor.getContents());
        }
    }
}
