package jrcet.frame.tools.JSEncrypt;

import burp.lib.Helper;
import burp.lib.TextLineNumber;
import jrcet.diycomponents.DiyJButton;
import jrcet.diycomponents.DiyJComponent;
import jrcet.diycomponents.DiyJTextArea.ui.rsyntaxtextarea.RSyntaxTextArea;
import jrcet.diycomponents.DiyJTextArea.ui.rsyntaxtextarea.SyntaxConstants;
import jrcet.diycomponents.DiyJTextArea.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import static jrcet.frame.tools.JSEncrypt.Utils.testPayload;

public class JSEncryptComponent extends DiyJComponent {
    public static String phantomjsLocation = "/Users/j7ur8/Documents/local/bin/phantomjs";
//    public static String phantomjsLocation = "C:\\Users\\J7ur8\\Desktop\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe";
//    public static String jScriptLocation = "C:\\Users\\J7ur8\\Desktop\\GitHub\\jsEncrypter\\test\\TestScript\\Base64\\jsEncrypter_base64.js";
    public static String jScriptLocation = "/Users/j7ur8/Documents/GitHub/jsEncrypter/script/jsEncrypter_base64.js";

    static public JComponent centerViewPanel=centerViewPanel();

    @Override
    public JComponent main() {
        JPanel JSEncryptPanel = new JPanel(new GridBagLayout());
        JSEncryptPanel.setOpaque(true);
        JSEncryptPanel.setBackground(Color.ORANGE);

        JPanel JSEncryptMenuPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,5,5));
        JSEncryptMenuPanel.setOpaque(true);
        JSEncryptMenuPanel.setBackground(Color.WHITE);
        JSEncryptMenuPanel.setPreferredSize(new Dimension(0,30));
        JSEncryptMenuPanel.setBorder(BorderFactory.createMatteBorder(0,0,1,0,new Color(203,208,209)));
        JSEncryptMenuPanel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        JLabel JSEncryptMenuHostLabel = new JLabel("Host: ");
        JSEncryptMenuPanel.add(JSEncryptMenuHostLabel);
        JTextField JSEncryptMenuHostTextField = new JTextField("127.0.0.1");
        JSEncryptMenuHostTextField.setPreferredSize(new Dimension(100,20));
        JSEncryptMenuPanel.add(JSEncryptMenuHostTextField);

        JLabel JSEncryptMenuHostPortLabel = new JLabel("Port: ");
        JSEncryptMenuPanel.add(JSEncryptMenuHostPortLabel);
        JTextField JSEncryptMenuHostPortTextField = new JTextField("1664");
        JSEncryptMenuHostPortTextField.setPreferredSize(new Dimension(50,20));
        JSEncryptMenuPanel.add(JSEncryptMenuHostPortTextField);

        JLabel JSEncryptMenuTimeoutLabel = new JLabel("Timeout: ");
        JSEncryptMenuPanel.add(JSEncryptMenuTimeoutLabel);
        JTextField JSEncryptMenuTimeoutField = new JTextField("5000");
        JSEncryptMenuTimeoutField.setPreferredSize(new Dimension(50,20));
        JSEncryptMenuPanel.add(JSEncryptMenuTimeoutField);

        JLabel JSEncryptMenuPhantomjsLabel = new JLabel("Phantomjs: ");
        JSEncryptMenuPanel.add(JSEncryptMenuPhantomjsLabel);
        JTextField JSEncryptMenuPhantomjsField = new JTextField("/Users/j7ur8/Documents/local/bin/phantomjs");
        JSEncryptMenuPhantomjsField.setPreferredSize(new Dimension(100,20));
        JSEncryptMenuPanel.add(JSEncryptMenuPhantomjsField);

        JLabel JSEncryptMenuJScriptLabel = new JLabel("JScript: ");
        JSEncryptMenuPanel.add(JSEncryptMenuJScriptLabel);
        JTextField JSEncryptMenuJScriptField = new JTextField("/Users/j7ur8/Documents/GitHub/jsEncrypter/script/jsEncrypter_base64.js");
        JSEncryptMenuJScriptField.setPreferredSize(new Dimension(100,20));
        JSEncryptMenuPanel.add(JSEncryptMenuJScriptField);

        DiyJButton JSEncryptMenuSetPhantomjsButton = new DiyJButton("SetPath");
        JSEncryptMenuSetPhantomjsButton.setPreferredSize(new Dimension(120,20));
        JSEncryptMenuPanel.add(JSEncryptMenuSetPhantomjsButton);

        JLabel JSEncryptMenuConnectedFlagLabel = new JLabel("   IsConnected: ");
        JSEncryptMenuPanel.add(JSEncryptMenuConnectedFlagLabel);
        JLabel JSEncryptMenuConnectedFlagLabelValue = new JLabel("False");
        JSEncryptMenuConnectedFlagLabelValue.setForeground(Color.WHITE);
        JSEncryptMenuConnectedFlagLabelValue.setPreferredSize(new Dimension(50,20));
        JSEncryptMenuPanel.add(JSEncryptMenuConnectedFlagLabelValue);

        DiyJButton JSEncryptMenuConnectButton = new DiyJButton("Connection");
        JSEncryptMenuConnectButton.setPreferredSize(new Dimension(120,20));
        JSEncryptMenuPanel.add(JSEncryptMenuConnectButton);


        DiyJButton JSEncryptMenuConnectTestButton = new DiyJButton("JSTest");
        JSEncryptMenuConnectTestButton.setPreferredSize(new Dimension(120,20));
        JSEncryptMenuPanel.add(JSEncryptMenuConnectTestButton);

        DiyJButton JSEncryptMenuDisConnectButton = new DiyJButton("DisConnect");
        JSEncryptMenuDisConnectButton.setPreferredSize(new Dimension(120,20));
        JSEncryptMenuPanel.add(JSEncryptMenuDisConnectButton);

        GridBagConstraints JSEncryptMenuPanelProperty = new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        );
        JSEncryptPanel.add(JSEncryptMenuPanel,JSEncryptMenuPanelProperty);

        centerViewPanel.setPreferredSize(new Dimension(0,0));
        GridBagConstraints centerViewPanelProperty = new GridBagConstraints(
                0,1,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        );
        JSEncryptPanel.add(centerViewPanel,centerViewPanelProperty);

        return JSEncryptPanel;
    }

    private static JComponent centerViewPanel() {
        JPanel centerViewPanel = new JPanel(new GridBagLayout());
        centerViewPanel.setOpaque(true);
        centerViewPanel.setBackground(Color.PINK);

        RSyntaxTextArea JSEncryptEditorRSyntaxTextArea = new RSyntaxTextArea();
        JSEncryptEditorRSyntaxTextArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        JSEncryptEditorRSyntaxTextArea.setCodeFoldingEnabled(true);
        RTextScrollPane JSEncryptEditorRSyntaxTextAreaScroll = new RTextScrollPane(JSEncryptEditorRSyntaxTextArea);
        JSEncryptEditorRSyntaxTextAreaScroll.setPreferredSize(new Dimension(0,0));
        GridBagConstraints JSEncryptEditorRSyntaxTextAreaScrollProperty = new GridBagConstraints(
                0,0,
                1,1,
                0.6,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        );
        centerViewPanel.add(JSEncryptEditorRSyntaxTextAreaScroll, JSEncryptEditorRSyntaxTextAreaScrollProperty);

        JSEncryptEditorRSyntaxTextArea.setText(Helper.readFile(jScriptLocation));
        JSEncryptEditorRSyntaxTextArea.addKeyListener(new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getModifiers()== InputEvent.META_MASK && e.getKeyCode()==83){
                    JTextArea parent = (JTextArea) e.getSource();
                    String jScriptContent = parent.getText();
                    try {
                        Helper.writeFile(jScriptContent,jScriptLocation);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        JTextArea JSEncryptOriginPayloadJTextArea = new JTextArea();
        JSEncryptOriginPayloadJTextArea.setLineWrap(true);
        JSEncryptOriginPayloadJTextArea.setWrapStyleWord(true);
        JSEncryptOriginPayloadJTextArea.setPreferredSize(new Dimension(0,0));
        JScrollPane JSEncryptOriginPayloadJTTextAreaScroll = new JScrollPane(JSEncryptOriginPayloadJTextArea);
        JSEncryptOriginPayloadJTTextAreaScroll.setBorder(null);
        TextLineNumber tln2 = new TextLineNumber(JSEncryptOriginPayloadJTextArea,2);
        JSEncryptOriginPayloadJTextArea.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(203, 208, 209)));
        JSEncryptOriginPayloadJTTextAreaScroll.setRowHeaderView(tln2);
        GridBagConstraints JSEncryptOriginPayloadJTTextAreaScrollProperty = new GridBagConstraints(
                1,0,
                1,1,
                0.2,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        );
        centerViewPanel.add(JSEncryptOriginPayloadJTTextAreaScroll, JSEncryptOriginPayloadJTTextAreaScrollProperty);
        StringBuilder tmp = new StringBuilder();
        for (String payload : testPayload) {
            tmp.append(payload).append(System.lineSeparator());
        }
        JSEncryptOriginPayloadJTextArea.setText(tmp.toString());

        JTextArea JSEncryptResultJTextArea = new JTextArea();
        JSEncryptResultJTextArea.setLineWrap(true);
        JSEncryptResultJTextArea.setWrapStyleWord(true);
        JSEncryptResultJTextArea.setPreferredSize(new Dimension(0,0));
        JScrollPane JSEncryptResultJTextAreaScroll = new JScrollPane(JSEncryptResultJTextArea);
        JSEncryptResultJTextAreaScroll.setBorder(null);
        TextLineNumber tln3 = new TextLineNumber(JSEncryptResultJTextArea);
        JSEncryptResultJTextAreaScroll.setRowHeaderView(tln3);
        GridBagConstraints JSEncryptResultJTextAreaScrollProperty = new GridBagConstraints(
                2,0,
                1,1,
                0.2,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        );
        centerViewPanel.add(JSEncryptResultJTextAreaScroll, JSEncryptResultJTextAreaScrollProperty);

        return centerViewPanel;
    }

}