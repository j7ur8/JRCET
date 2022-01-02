package jrcet.frame.tools.JSEncrypt;

import burp.lib.Helper;
import burp.lib.TextLineNumber;
import jrcet.diycomponents.DiyJButton;
import jrcet.diycomponents.DiyJComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import static jrcet.frame.tools.JSEncrypt.Utils.testPayload;

public class JSEncryptComponent extends DiyJComponent {
    public static String phantomjsLocation = "/Users/j7ur8/Documents/local/bin/phantomjs";
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
        JSEncryptMenuPanel.setPreferredSize(new Dimension(0,40));
        JSEncryptMenuPanel.setBorder(BorderFactory.createMatteBorder(0,0,1,0,new Color(203,208,209)));
        JSEncryptMenuPanel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        JLabel JSEncryptMenuHostLabel = new JLabel("Host: ");
        JSEncryptMenuPanel.add(JSEncryptMenuHostLabel);
        JTextField JSEncryptMenuHostTextField = new JTextField("127.0.0.1");
        JSEncryptMenuHostTextField.setPreferredSize(new Dimension(100,30));
        JSEncryptMenuPanel.add(JSEncryptMenuHostTextField);

        JLabel JSEncryptMenuHostPortLabel = new JLabel("Port: ");
        JSEncryptMenuPanel.add(JSEncryptMenuHostPortLabel);
        JTextField JSEncryptMenuHostPortTextField = new JTextField("1664");
        JSEncryptMenuHostPortTextField.setPreferredSize(new Dimension(50,30));
        JSEncryptMenuPanel.add(JSEncryptMenuHostPortTextField);

        JLabel JSEncryptMenuTimeoutLabel = new JLabel("Timeout: ");
        JSEncryptMenuPanel.add(JSEncryptMenuTimeoutLabel);
        JTextField JSEncryptMenuTimeoutField = new JTextField("5000");
        JSEncryptMenuTimeoutField.setPreferredSize(new Dimension(50,30));
        JSEncryptMenuPanel.add(JSEncryptMenuTimeoutField);

        JLabel JSEncryptMenuPhantomjsLabel = new JLabel("Phantomjs: ");
        JSEncryptMenuPanel.add(JSEncryptMenuPhantomjsLabel);
        JTextField JSEncryptMenuPhantomjsField = new JTextField("/Users/j7ur8/Documents/local/bin/phantomjs");
        JSEncryptMenuPhantomjsField.setPreferredSize(new Dimension(100,30));
        JSEncryptMenuPanel.add(JSEncryptMenuPhantomjsField);

        JLabel JSEncryptMenuJScriptLabel = new JLabel("JScript: ");
        JSEncryptMenuPanel.add(JSEncryptMenuJScriptLabel);
        JTextField JSEncryptMenuJScriptField = new JTextField("/Users/j7ur8/Documents/GitHub/jsEncrypter/script/jsEncrypter_base64.js");
        JSEncryptMenuJScriptField.setPreferredSize(new Dimension(100,30));
        JSEncryptMenuPanel.add(JSEncryptMenuJScriptField);

        JLabel JSEncryptMenuConnectedFlagLabel = new JLabel("IsConnected: ");
        JSEncryptMenuPanel.add(JSEncryptMenuConnectedFlagLabel);
        JLabel JSEncryptMenuConnectedFlagLabelValue = new JLabel("False");
        JSEncryptMenuConnectedFlagLabelValue.setPreferredSize(new Dimension(50,30));
        JSEncryptMenuPanel.add(JSEncryptMenuConnectedFlagLabelValue);

        DiyJButton JSEncryptMenuConnectButton = new DiyJButton("Connection");
        JSEncryptMenuConnectButton.setPreferredSize(new Dimension(120,30));
        JSEncryptMenuPanel.add(JSEncryptMenuConnectButton);

        DiyJButton JSEncryptMenuConnectTestButton = new DiyJButton("JSTest");
        JSEncryptMenuConnectTestButton.setPreferredSize(new Dimension(120,30));
        JSEncryptMenuPanel.add(JSEncryptMenuConnectTestButton);

        DiyJButton JSEncryptMenuDisConnectButton = new DiyJButton("DisConnect");
        JSEncryptMenuDisConnectButton.setPreferredSize(new Dimension(120,30));
        JSEncryptMenuPanel.add(JSEncryptMenuDisConnectButton);
//        JSEncryptMenuConnectButton

        GridBagConstraints JSEncryptMenuPanelProperty = new GridBagConstraints();
        JSEncryptMenuPanelProperty.gridx=0;JSEncryptMenuPanelProperty.gridy=0;
        JSEncryptMenuPanelProperty.weighty=0;JSEncryptMenuPanelProperty.weightx=1;
        JSEncryptMenuPanelProperty.fill=GridBagConstraints.BOTH;
        JSEncryptPanel.add(JSEncryptMenuPanel,JSEncryptMenuPanelProperty);

        GridBagConstraints centerViewPanelProperty = new GridBagConstraints();
        centerViewPanelProperty.gridx=0;centerViewPanelProperty.gridy=1;
        centerViewPanelProperty.weighty=1;centerViewPanelProperty.weightx=1;
        centerViewPanelProperty.fill=GridBagConstraints.BOTH;
        JSEncryptPanel.add(centerViewPanel,centerViewPanelProperty);

        return JSEncryptPanel;
    }

    private static JComponent centerViewPanel() {
        JPanel centerViewPanel = new JPanel(new GridBagLayout());
        centerViewPanel.setOpaque(true);
        centerViewPanel.setBackground(Color.PINK);

        JTextArea JSEncryptEditorJTextArea = new JTextArea();
        JSEncryptEditorJTextArea.setName("adsf");
        JSEncryptEditorJTextArea.setLineWrap(true);
        JSEncryptEditorJTextArea.setWrapStyleWord(true);
        JSEncryptEditorJTextArea.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(203, 208, 209)));
        JScrollPane JSEncryptJTextAreaScroll = new JScrollPane(JSEncryptEditorJTextArea);
        JSEncryptJTextAreaScroll.setBorder(null);
        TextLineNumber tln1 = new TextLineNumber(JSEncryptEditorJTextArea,2);
        JSEncryptJTextAreaScroll.setRowHeaderView(tln1);
        GridBagConstraints JSEncryptJTextAreaScrollProperty = new GridBagConstraints();
        JSEncryptJTextAreaScrollProperty.gridx = 0;
        JSEncryptJTextAreaScrollProperty.gridy = 0;
        JSEncryptJTextAreaScrollProperty.weightx = 0.7;
        JSEncryptJTextAreaScrollProperty.weighty = 1;
        JSEncryptJTextAreaScrollProperty.gridheight = 1;
        JSEncryptJTextAreaScrollProperty.fill = GridBagConstraints.BOTH;
        centerViewPanel.add(JSEncryptJTextAreaScroll, JSEncryptJTextAreaScrollProperty);
        JSEncryptEditorJTextArea.setText(Helper.readFile(jScriptLocation));
        JSEncryptEditorJTextArea.addKeyListener(new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

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
        JScrollPane JSEncryptOriginPayloadJTTextAreaScroll = new JScrollPane(JSEncryptOriginPayloadJTextArea);
        JSEncryptOriginPayloadJTTextAreaScroll.setBorder(null);
        TextLineNumber tln2 = new TextLineNumber(JSEncryptOriginPayloadJTextArea,2);
        JSEncryptOriginPayloadJTextArea.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(203, 208, 209)));
        JSEncryptOriginPayloadJTTextAreaScroll.setRowHeaderView(tln2);
        GridBagConstraints JSEncryptOriginPayloadJTTextAreaScrollProperty = new GridBagConstraints();
        JSEncryptOriginPayloadJTTextAreaScrollProperty.gridx = 1;
        JSEncryptOriginPayloadJTTextAreaScrollProperty.gridy = 0;
        JSEncryptOriginPayloadJTTextAreaScrollProperty.weighty = 1;
        JSEncryptOriginPayloadJTTextAreaScrollProperty.weightx = 0.15;
        JSEncryptOriginPayloadJTTextAreaScrollProperty.fill = GridBagConstraints.BOTH;
        centerViewPanel.add(JSEncryptOriginPayloadJTTextAreaScroll, JSEncryptOriginPayloadJTTextAreaScrollProperty);
        StringBuilder tmp = new StringBuilder();
        for (String payload : testPayload) {
            tmp.append(payload).append(System.lineSeparator());
        }
        JSEncryptOriginPayloadJTextArea.setText(tmp.toString());

        JTextArea JSEncryptResultJTextArea = new JTextArea();
        JSEncryptResultJTextArea.setLineWrap(true);
        JSEncryptResultJTextArea.setWrapStyleWord(true);
        JScrollPane JSEncryptResultJTextAreaScroll = new JScrollPane(JSEncryptResultJTextArea);
        JSEncryptResultJTextAreaScroll.setBorder(null);
        TextLineNumber tln3 = new TextLineNumber(JSEncryptResultJTextArea);
        JSEncryptResultJTextAreaScroll.setRowHeaderView(tln3);
        GridBagConstraints JSEncryptResultJTextAreaScrollProperty = new GridBagConstraints();
        JSEncryptResultJTextAreaScrollProperty.gridx = 2;
        JSEncryptResultJTextAreaScrollProperty.gridy = 0;
        JSEncryptResultJTextAreaScrollProperty.weighty = 1;
        JSEncryptResultJTextAreaScrollProperty.weightx = 0.15;
        JSEncryptResultJTextAreaScrollProperty.fill = GridBagConstraints.BOTH;
        centerViewPanel.add(JSEncryptResultJTextAreaScroll, JSEncryptResultJTextAreaScrollProperty);

        return centerViewPanel;
    }

}