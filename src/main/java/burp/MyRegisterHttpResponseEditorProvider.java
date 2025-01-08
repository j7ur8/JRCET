package burp;

import burp.api.montoya.core.ByteArray;
import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.http.message.MimeType;
import burp.api.montoya.http.message.responses.HttpResponse;
import burp.api.montoya.ui.Selection;
import burp.api.montoya.ui.editor.RawEditor;
import burp.api.montoya.ui.editor.extension.EditorCreationContext;
import burp.api.montoya.ui.editor.extension.ExtensionProvidedHttpResponseEditor;
import burp.api.montoya.ui.editor.extension.HttpResponseEditorProvider;
import jrcet.frame.Dencrypt.Unicode.Unicode;

import java.awt.*;
import java.nio.charset.StandardCharsets;

import static burp.MyExtender.BurpAPI;

public class MyRegisterHttpResponseEditorProvider implements HttpResponseEditorProvider {


    @Override
    public ExtensionProvidedHttpResponseEditor provideHttpResponseEditor(EditorCreationContext editorCreationContext) {
        return new MyExtensionHttpResponseEditor();
    }

    private static class MyExtensionHttpResponseEditor implements ExtensionProvidedHttpResponseEditor{

        HttpRequestResponse MyHttpRequestResponse;
//        DiyJTextAreaScrollPane MyExtenderEditor;
        RawEditor RepeaterUnicodeEditor = BurpAPI.userInterface().createRawEditor();
        MyExtensionHttpResponseEditor(){
            RepeaterUnicodeEditor.setEditable(false);

        }

        @Override
        public HttpResponse getResponse() {
            return HttpResponse.httpResponse(RepeaterUnicodeEditor.getContents());
        }

        @Override
        public void setRequestResponse(HttpRequestResponse httpRequestResponse) {
            MyHttpRequestResponse = httpRequestResponse;
            HttpResponse httpResponse = MyHttpRequestResponse.response();
            RepeaterUnicodeEditor.setContents(ByteArray.byteArray(Unicode.unicodeToString(httpResponse.toString()).getBytes(StandardCharsets.UTF_8)));
        }

        @Override
        public boolean isEnabledFor(HttpRequestResponse requestResponse) {
            HttpResponse httpResponse = requestResponse.response();
            return (httpResponse.inferredMimeType() == MimeType.JSON || httpResponse.statedMimeType() == MimeType.JSON);
        }

        @Override
        public String caption() {
            return "Unicode";
        }

        @Override
        public Component uiComponent() {
            return RepeaterUnicodeEditor.uiComponent();
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
