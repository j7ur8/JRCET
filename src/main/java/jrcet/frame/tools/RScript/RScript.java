package jrcet.frame.tools.RScript;

import burp.IExtensionHelpers;
import burp.IHttpRequestResponse;
import burp.IRequestInfo;
import burp.lib.Json;
import burp.lib.TextLineNumber;
import jrcet.diycomponents.DiyJButton;
import jrcet.diycomponents.DiyJComponent;

import javax.swing.*;
import java.awt.*;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.List;

public class RScript extends DiyJComponent {
    private final static String[] PYTHON_ESCAPE = new String[256];
    private final static String SESSION_VAR = "session";
    private enum BodyType {JSON, DATA};
    private IExtensionHelpers helpers;
    static {
        for (int i = 0x00; i <= 0xFF; i++) PYTHON_ESCAPE[i] = String.format("\\x%02x", i);
        for (int i = 0x20; i < 0x80; i++) PYTHON_ESCAPE[i] = String.valueOf((char)i);
        PYTHON_ESCAPE['\n'] = "\\n";
        PYTHON_ESCAPE['\r'] = "\\r";
        PYTHON_ESCAPE['\t'] = "\\t";
        PYTHON_ESCAPE['"'] = "\\\"";
        PYTHON_ESCAPE['\\'] = "\\\\";
    }


    @Override
    public JComponent main() {
        JPanel RScriptPanel = new JPanel(new GridBagLayout());
        RScriptPanel.setOpaque(true);
        RScriptPanel.setBackground(Color.WHITE);

        JTextArea RScriptJTextArea = new JTextArea();
        RScriptJTextArea.setLineWrap(true);
        RScriptJTextArea.setWrapStyleWord(true);

        JScrollPane RScriptJTextAreaScroll = new JScrollPane(RScriptJTextArea);
        RScriptJTextAreaScroll.setBorder(null);
        TextLineNumber tln = new TextLineNumber(RScriptJTextArea);
        RScriptJTextAreaScroll.setRowHeaderView( tln );
        GridBagConstraints RScriptJTextAreaScrollProperty = new GridBagConstraints();
        RScriptJTextAreaScrollProperty.gridx=0;RScriptJTextAreaScrollProperty.gridy=1;
        RScriptJTextAreaScrollProperty.weightx=100;RScriptJTextAreaScrollProperty.weighty=100;
        RScriptJTextAreaScrollProperty.fill=GridBagConstraints.BOTH;
        RScriptPanel.add(RScriptJTextAreaScroll,RScriptJTextAreaScrollProperty);

        JPanel RScriptMenuPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,5,5));
        RScriptMenuPanel.setOpaque(true);
        RScriptMenuPanel.setBackground(Color.WHITE);
        RScriptMenuPanel.setPreferredSize(new Dimension(0,40));
        RScriptMenuPanel.setBorder(BorderFactory.createMatteBorder(0,0,1,0,new Color(203,208,209)));
        RScriptMenuPanel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        DiyJButton RScriptMenuSCopyButton = new DiyJButton("Copy With Session");
        DiyJButton RScriptMenuCopyButton = new DiyJButton("Copy");
        RScriptMenuPanel.add(RScriptMenuCopyButton);
        RScriptMenuPanel.add(RScriptMenuSCopyButton);

        GridBagConstraints RScriptMenuPanelProperty = new GridBagConstraints();
        RScriptMenuPanelProperty.gridx=0;RScriptMenuPanelProperty.gridy=0;
        RScriptMenuPanelProperty.weighty=0;RScriptMenuPanelProperty.weightx=1;
        RScriptMenuPanelProperty.fill=GridBagConstraints.BOTH;
        RScriptPanel.add(RScriptMenuPanel,RScriptMenuPanelProperty);

        return RScriptPanel;
    }

    public String initScript(IHttpRequestResponse[] messages, IExtensionHelpers helpers){
        this.helpers=helpers;
        int i = 0;
        StringBuilder py = new StringBuilder("import requests");
        String requestsMethodPrefix =
                "\n" + "requests" + ".";

        for (IHttpRequestResponse message : messages) {
            IRequestInfo ri = helpers.analyzeRequest(message);
            byte[] req = message.getRequest();
            String prefix = "burp" + i++ + "_";
            py.append("\n\n").append(prefix).append("url = \"");
            py.append(escapeQuotes(ri.getUrl().toString()));
            py.append('"');
            List<String> headers = ri.getHeaders();
            boolean cookiesExist = processCookies(prefix, py, headers);
            py.append('\n').append(prefix).append("headers = {");
            processHeaders(py, headers);
            py.append('}');
            RScript.BodyType bodyType = processBody(prefix, py, req, ri);
            py.append(requestsMethodPrefix);
            py.append(ri.getMethod().toLowerCase());
            py.append('(').append(prefix).append("url, headers=");
            py.append(prefix).append("headers");
            if (cookiesExist) py.append(", cookies=").append(prefix).append("cookies");
            if (bodyType != null) {
                String kind = bodyType.toString().toLowerCase();
                py.append(", ").append(kind).append('=').append(prefix).append(kind);
            }
            py.append(')');
        }

        return String.valueOf(py);
    }

    private static boolean processCookies(String prefix, StringBuilder py,
                                          List<String> headers) {
        ListIterator<String> iter = headers.listIterator();
        boolean cookiesExist = false;
        while (iter.hasNext()) {
            String header = iter.next();
            if (!header.toLowerCase().startsWith("cookie:")) continue;
            iter.remove();
            for (String cookie : header.substring(8).split("; ?")) {
                if (cookiesExist) {
                    py.append(", \"");
                } else {
                    cookiesExist = true;
                    py.append('\n').append(prefix).append("cookies = {\"");
                }
                String[] parts = cookie.split("=", 2);
                py.append(escapeQuotes(parts[0]));
                py.append("\": \"");
                py.append(escapeQuotes(parts[1]));
                py.append('"');
            }
        }
        if (cookiesExist) py.append('}');
        return cookiesExist;
    }

    private static final Collection<String> IGNORE_HEADERS = Arrays.asList("host:", "content-length:");

    private static void processHeaders(StringBuilder py, List<String> headers) {
        boolean firstHeader = true;
        header_loop:
        for (String header : headers) {
            String lowerCaseHeader = header.toLowerCase();
            for (String headerToIgnore : IGNORE_HEADERS) {
                if (lowerCaseHeader.startsWith(headerToIgnore)) continue header_loop;
            }
            header = escapeQuotes(header);
            int colonPos = header.indexOf(':');
            if (colonPos == -1) continue;
            if (firstHeader) {
                firstHeader = false;
                py.append('"');
            } else {
                py.append(", \"");
            }
            py.append(header, 0, colonPos);
            py.append("\": \"");
            py.append(header, colonPos + 2, header.length());
            py.append('"');
        }
    }

    private RScript.BodyType processBody(String prefix, StringBuilder py,
                                              byte[] req, IRequestInfo ri) {
        int bo = ri.getBodyOffset();
        if (bo >= req.length - 2) return null;
        py.append('\n').append(prefix);
        byte contentType = ri.getContentType();
        if (contentType == IRequestInfo.CONTENT_TYPE_JSON) {
            try {
                Json root = Json.read(byteSliceToString(req, bo, req.length));
                py.append("json=");
                escapeJson(root, py);
                return RScript.BodyType.JSON;
            } catch (Exception e) {
                // not valid JSON, treat it like any other kind of data
            }
        }
        py.append("data = ");
        if (contentType == IRequestInfo.CONTENT_TYPE_URL_ENCODED) {
            py.append('{');
            boolean firstKey = true;
            int keyStart = bo, keyEnd = -1;
            for (int pos = bo; pos < req.length; pos++) {
                byte b = req[pos];
                if (keyEnd == -1) {
                    if (b == (byte)'=') {
                        if (pos == req.length - 1) {
                            if (!firstKey) py.append(", ");
                            escapeUrlEncodedBytes(req, py, keyStart, pos);
                            py.append(": ''");
                        } else {
                            keyEnd = pos;
                        }
                    }
                } else if (b == (byte)'&' || pos == req.length - 1) {
                    if (firstKey) firstKey = false; else py.append(", ");
                    escapeUrlEncodedBytes(req, py, keyStart, keyEnd);
                    py.append(": ");
                    escapeUrlEncodedBytes(req, py, keyEnd + 1,
                            pos == req.length - 1 ? req.length : pos);
                    keyEnd = -1;
                    keyStart = pos + 1;
                }
            }
            py.append('}');
        } else {
            escapeBytes(req, py, bo, req.length);
        }
        return RScript.BodyType.DATA;
    }

    private static String escapeQuotes(String value) {
        return value.replace("\\", "\\\\").replace("\"", "\\\"")
                .replace("\n", "\\n").replace("\r", "\\r");
    }

    private void escapeUrlEncodedBytes(byte[] input, StringBuilder output,
                                       int start, int end) {
        if (end > start) {
            byte[] dec = helpers.urlDecode(Arrays.copyOfRange(input, start, end));
            escapeBytes(dec, output, 0, dec.length);
        } else {
            output.append("''");
        }
    }

    private static final String PYTHON_TRUE = "True", PYTHON_FALSE = "False", PYTHON_NULL = "None";

    private static void escapeJson(Json node, StringBuilder output) {
        if (node.isObject()) {
            output.append('{');
            Map<String, Json> tm = new TreeMap(String.CASE_INSENSITIVE_ORDER);
            tm.putAll(node.asJsonMap());
            final Iterator<Map.Entry<String, Json>> iter = tm.entrySet().iterator();
            if (iter.hasNext()) {
                appendIteratedEntry(iter, output);
                while (iter.hasNext()) {
                    output.append(", ");
                    appendIteratedEntry(iter, output);
                }
            }
            output.append('}');
        } else if (node.isArray()) {
            output.append('[');
            final Iterator<Json> iter = node.asJsonList().iterator();
            if (iter.hasNext()) {
                escapeJson(iter.next(), output);
                while (iter.hasNext()) {
                    output.append(", ");
                    escapeJson(iter.next(), output);
                }
            }
            output.append(']');
        } else if (node.isString()) {
            escapeString(node.asString(), output);
        } else if (node.isBoolean()) {
            output.append(node.asBoolean() ? PYTHON_TRUE : PYTHON_FALSE);
        } else if (node.isNull()) {
            output.append(PYTHON_NULL);
        } else if (node.isNumber()) {
            output.append(node.asString());
        }
    }

    private static void appendIteratedEntry(Iterator<Map.Entry<String, Json>> iter, StringBuilder output) {
        final Map.Entry<String, Json> e = iter.next();
        escapeString(e.getKey(), output);
        output.append(": ");
        escapeJson(e.getValue(), output);
    }

    private static String byteSliceToString(byte[] input, int from, int till) {
        try {
            return new String(input, from, till - from, "ISO-8859-1");
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("All JVMs must support ISO-8859-1");
        }
    }

    private static void escapeString(String input, StringBuilder output) {
        output.append('"');
        int length = input.length();
        for (int pos = 0; pos < length; pos++) {
            output.append(PYTHON_ESCAPE[input.charAt(pos) & 0xFF]);
        }
        output.append('"');
    }

    private static void escapeBytes(byte[] input, StringBuilder output,
                                    int start, int end) {
        output.append('"');
        for (int pos = start; pos < end; pos++) {
            output.append(PYTHON_ESCAPE[input[pos] & 0xFF]);
        }
        output.append('"');
    }

}
