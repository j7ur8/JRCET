package jrcet.listeners;

import burp.IHttpRequestResponse;
import jrcet.diycomponents.DiyJTextArea.ui.rsyntaxtextarea.RSyntaxTextArea;
import jrcet.frame.tools.Scanner.Fastjson.Fastjson;
import jrcet.frame.tools.Scanner.Fastjson.FastjsonComponent;
import jrcet.help.Helper;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static jrcet.frame.tools.Scanner.Fastjson.FastjsonComponent.FastjsonPackageRequestEditor;
import static jrcet.frame.tools.Scanner.Fastjson.FastjsonComponent.FastjsonPackageResponseEditor;

public class DiyFastjsonJTableListSelectionListener implements ListSelectionListener {
    @Override
    public void valueChanged(ListSelectionEvent e) {
        ListSelectionModel tListSelectionModel = (ListSelectionModel) e.getSource();
        if(tListSelectionModel.getValueIsAdjusting()){
            int minIndex = tListSelectionModel.getMinSelectionIndex();
            int maxIndex = tListSelectionModel.getMaxSelectionIndex();
            for (int i = minIndex; i <= maxIndex; i++) {
                if (tListSelectionModel.isSelectedIndex(i)) {
                    IHttpRequestResponse tHttpRequestResponse = Fastjson.FastjsonLogEntries.get(i).requestResponse();

                    FastjsonPackageRequestEditor.setMessage(tHttpRequestResponse.getRequest(), true);
                    FastjsonPackageResponseEditor.setMessage(tHttpRequestResponse.getResponse(), false);
                }
            }
        }
    }
}
