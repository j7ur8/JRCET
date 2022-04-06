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

import static jrcet.frame.tools.JSEncrypt.JSEncrypt.JSEncryptPayload;

public class JSEncryptComponent extends DiyJComponent {

    public static String JSEncryptPhantomjsLocation = "/Users/j7ur8/Documents/local/bin/phantomjs";
    public static String JSEncryptScriptLocation = "/Users/j7ur8/Documents/GitHub/jsEncrypter/script/jsEncrypter_base64.js";

    @Override
    public JComponent main() {

        JPanel JSEncryptComponentPanel = new JPanel(new GridBagLayout());
        JSEncryptComponentPanel.setName("JSEncryptComponentPanel");
        JSEncryptComponentPanel.setOpaque(false);
        JSEncryptComponentPanel.setBackground(Color.ORANGE);

        JSEncryptComponentPanel.add(JSEncryptMenuPanel(),new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        JSEncryptComponentPanel.add(JSEncryptMainPanel(),new GridBagConstraints(
                0,1,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return JSEncryptComponentPanel;
    }

    private JComponent JSEncryptMenuPanel(){
        JPanel JSEncryptMenuPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,5,5));
        JSEncryptMenuPanel.setOpaque(false);
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

        return JSEncryptMenuPanel;
    }

    private JComponent JSEncryptMainPanel() {

        JPanel JSEncryptMainPanel = new JPanel(new GridBagLayout());
        JSEncryptMainPanel.setName("JSEncryptMainPanel");
        JSEncryptMainPanel.setPreferredSize(new Dimension(0,0));
        JSEncryptMainPanel.setOpaque(false);
        JSEncryptMainPanel.setBackground(Color.PINK);

        JSEncryptMainPanel.add(JSEncryptMainScriptEditorScrollPanel(), new GridBagConstraints(
                0,0,
                1,1,
                0.6,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        JSEncryptMainPanel.add(JSEncryptMainPayloadScrollPanel(), new GridBagConstraints(
                1,0,
                1,1,
                0.2,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        JSEncryptMainPanel.add(JSEncryptMainResultScrollPanel(), new GridBagConstraints(
                2,0,
                1,1,
                0.2,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return JSEncryptMainPanel;
    }

    private JComponent JSEncryptMainScriptEditorScrollPanel(){

        RSyntaxTextArea JSEncryptMainScriptEditor = new RSyntaxTextArea();
        JSEncryptMainScriptEditor.setName("JSEncryptMainScriptEditor");
        JSEncryptMainScriptEditor.setCodeFoldingEnabled(true);
        JSEncryptMainScriptEditor.setText((Helper.isFile(JSEncryptScriptLocation)?Helper.readFile(JSEncryptScriptLocation):"File Not Found"));
        JSEncryptMainScriptEditor.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);

        RTextScrollPane JSEncryptMainScriptEditorScrollPanel = new RTextScrollPane(JSEncryptMainScriptEditor);
        JSEncryptMainScriptEditorScrollPanel.setName("JSEncryptMainScriptEditorScrollPanel");
        JSEncryptMainScriptEditorScrollPanel.setPreferredSize(new Dimension(0,0));

        JSEncryptMainScriptEditor.addKeyListener(new KeyListener(){
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
                        Helper.writeFile(jScriptContent, JSEncryptScriptLocation);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        return  JSEncryptMainScriptEditorScrollPanel;
    }

    public JComponent JSEncryptMainPayloadScrollPanel(){

        StringBuilder JSEncryptMainPayload = new StringBuilder();
        for (String payload : JSEncryptPayload) {
            JSEncryptMainPayload.append(payload).append(System.lineSeparator());
        }

        JTextArea JSEncryptMainPayloadEditor = new JTextArea();
        JSEncryptMainPayloadEditor.setLineWrap(true);
        JSEncryptMainPayloadEditor.setWrapStyleWord(true);
        JSEncryptMainPayloadEditor.setName("JSEncryptMainPayloadEditor");
        JSEncryptMainPayloadEditor.setText(JSEncryptMainPayload.toString());
        JSEncryptMainPayloadEditor.setPreferredSize(new Dimension(0,0));
        JSEncryptMainPayloadEditor.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(203, 208, 209)));

        JScrollPane JSEncryptMainPayloadEditorScrollPanel = new JScrollPane(JSEncryptMainPayloadEditor);
        JSEncryptMainPayloadEditorScrollPanel.setBorder(null);
        JSEncryptMainPayloadEditorScrollPanel.setName("JSEncryptMainPayloadEditorScrollPanel");
        JSEncryptMainPayloadEditorScrollPanel.setRowHeaderView(new TextLineNumber(JSEncryptMainPayloadEditor,2));

        return JSEncryptMainPayloadEditorScrollPanel;
    }

    private JComponent JSEncryptMainResultScrollPanel(){
        
        JTextArea JSEncryptMainResultEditor = new JTextArea();
        JSEncryptMainResultEditor.setLineWrap(true);
        JSEncryptMainResultEditor.setWrapStyleWord(true);
        JSEncryptMainResultEditor.setName("JSEncryptMainResultEditor");
        JSEncryptMainResultEditor.setPreferredSize(new Dimension(0,0));

        JScrollPane JSEncryptMainResultScrollPanel = new JScrollPane(JSEncryptMainResultEditor);
        JSEncryptMainResultScrollPanel.setName("JSEncryptMainResultScrollPanel");
        JSEncryptMainResultScrollPanel.setBorder(null);
        JSEncryptMainResultScrollPanel.setRowHeaderView(new TextLineNumber(JSEncryptMainResultEditor));

        return  JSEncryptMainResultScrollPanel;
    }

}