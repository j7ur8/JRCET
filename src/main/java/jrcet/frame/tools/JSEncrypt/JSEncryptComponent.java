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
        JSEncryptMenuPanel.setName("JSEncryptMenuPanel");
        JSEncryptMenuPanel.setPreferredSize(new Dimension(0,30));
        JSEncryptMenuPanel.setBorder(BorderFactory.createMatteBorder(0,0,1,0,new Color(203,208,209)));
        JSEncryptMenuPanel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        JLabel JSEncryptMenuHostLabel = new JLabel("Host: ");
        JSEncryptMenuPanel.add(JSEncryptMenuHostLabel);
        JSEncryptMenuHostLabel.setName("JSEncryptMenuHostLabel");

        JTextField JSEncryptMenuHostField = new JTextField("127.0.0.1");
        JSEncryptMenuHostField.setName("JSEncryptMenuHostField");
        JSEncryptMenuHostField.setPreferredSize(new Dimension(100,20));
        JSEncryptMenuPanel.add(JSEncryptMenuHostField);

        JLabel JSEncryptMenuHostPortLabel = new JLabel("Port: ");
        JSEncryptMenuPanel.add(JSEncryptMenuHostPortLabel);
        JSEncryptMenuHostPortLabel.setName("JSEncryptMenuHostPortLabel");

        JTextField JSEncryptMenuPortField = new JTextField("1664");
        JSEncryptMenuPortField.setName("JSEncryptMenuPortField");
        JSEncryptMenuPortField.setPreferredSize(new Dimension(50,20));
        JSEncryptMenuPanel.add(JSEncryptMenuPortField);

        JLabel JSEncryptMenuTimeoutLabel = new JLabel("Timeout: ");
        JSEncryptMenuPanel.add(JSEncryptMenuTimeoutLabel);
        JSEncryptMenuTimeoutLabel.setName("JSEncryptMenuTimeoutLabel");

        JTextField JSEncryptMenuTimeoutField = new JTextField("5000");
        JSEncryptMenuTimeoutField.setName("JSEncryptMenuTimeoutField");
        JSEncryptMenuTimeoutField.setPreferredSize(new Dimension(50,20));
        JSEncryptMenuPanel.add(JSEncryptMenuTimeoutField);

        JLabel JSEncryptMenuPhantomjsLabel = new JLabel("Phantomjs: ");
        JSEncryptMenuPanel.add(JSEncryptMenuPhantomjsLabel);
        JSEncryptMenuPhantomjsLabel.setName("JSEncryptMenuPhantomjsLabel");

        JTextField JSEncryptMenuPhantomjsField = new JTextField("/Users/j7ur8/Documents/local/bin/phantomjs");
        JSEncryptMenuPhantomjsField.setName("JSEncryptMenuPhantomjsField");
        JSEncryptMenuPhantomjsField.setPreferredSize(new Dimension(100,20));
        JSEncryptMenuPanel.add(JSEncryptMenuPhantomjsField);

        JLabel JSEncryptMenuJScriptLabel = new JLabel("JScript: ");
        JSEncryptMenuPanel.add(JSEncryptMenuJScriptLabel);
        JSEncryptMenuJScriptLabel.setName("JSEncryptMenuJScriptLabel");

        JTextField JSEncryptMenuJScriptField = new JTextField("/Users/j7ur8/Documents/GitHub/jsEncrypter/script/jsEncrypter_base64.js");
        JSEncryptMenuJScriptField.setName("JSEncryptMenuJScriptField");
        JSEncryptMenuJScriptField.setPreferredSize(new Dimension(100,20));
        JSEncryptMenuPanel.add(JSEncryptMenuJScriptField);

        DiyJButton JSEncryptMenuSetPhantomjsButton = new DiyJButton("SetPath");
        JSEncryptMenuSetPhantomjsButton.setName("JSEncryptMenuSetPhantomjsButton");
        JSEncryptMenuSetPhantomjsButton.setPreferredSize(new Dimension(120,20));
        JSEncryptMenuPanel.add(JSEncryptMenuSetPhantomjsButton);

        JLabel JSEncryptMenuFlagLabel = new JLabel("   IsConnected: ");
        JSEncryptMenuFlagLabel.setName("JSEncryptMenuFlagLabel");
        JSEncryptMenuPanel.add(JSEncryptMenuFlagLabel);

        JLabel JSEncryptMenuStatusLabel = new JLabel("False");
        JSEncryptMenuStatusLabel.setForeground(Color.WHITE);
        JSEncryptMenuStatusLabel.setName("JSEncryptMenuStatusLabel");
        JSEncryptMenuStatusLabel.setPreferredSize(new Dimension(50,20));
        JSEncryptMenuPanel.add(JSEncryptMenuStatusLabel);

        DiyJButton JSEncryptMenuConnectButton = new DiyJButton("Connect");
        JSEncryptMenuConnectButton.setName("JSEncryptMenuConnectButton");
        JSEncryptMenuConnectButton.setPreferredSize(new Dimension(120,20));
        JSEncryptMenuPanel.add(JSEncryptMenuConnectButton);


        DiyJButton JSEncryptMenuTestButton = new DiyJButton("JSTest");
        JSEncryptMenuTestButton.setName("JSEncryptMenuTestButton");
        JSEncryptMenuTestButton.setPreferredSize(new Dimension(120,20));
        JSEncryptMenuPanel.add(JSEncryptMenuTestButton);

        DiyJButton JSEncryptMenuBreakButton = new DiyJButton("Break");
        JSEncryptMenuBreakButton.setName("JSEncryptMenuBreakButton");
        JSEncryptMenuBreakButton.setPreferredSize(new Dimension(120,20));
        JSEncryptMenuPanel.add(JSEncryptMenuBreakButton);

        return JSEncryptMenuPanel;
    }

    private JComponent JSEncryptMainPanel() {

        JPanel JSEncryptMainPanel = new JPanel(new GridBagLayout());
        JSEncryptMainPanel.setName("JSEncryptMainPanel");
        JSEncryptMainPanel.setPreferredSize(new Dimension(0,0));
        JSEncryptMainPanel.setOpaque(false);

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
                if((e.getModifiers()== InputEvent.CTRL_MASK || e.getModifiers() == InputEvent.META_MASK) && e.getKeyCode()==83){
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