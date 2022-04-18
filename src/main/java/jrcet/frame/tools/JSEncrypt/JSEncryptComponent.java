package jrcet.frame.tools.JSEncrypt;

import burp.lib.Helper;
import jrcet.diycomponents.DiyJButton;
import jrcet.diycomponents.DiyJComponent;
import jrcet.diycomponents.DiyJTabLabel;
import jrcet.diycomponents.DiyJTextArea.ui.rsyntaxtextarea.RSyntaxTextArea;
import jrcet.diycomponents.DiyJTextArea.ui.rsyntaxtextarea.SyntaxConstants;
import jrcet.diycomponents.DiyJTextArea.ui.rtextarea.RTextScrollPane;
import jrcet.frame.setting.Setting;
import jrcet.frame.tools.Dencrypt.Base.Base;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class JSEncryptComponent extends DiyJComponent {

    public static String JSEncryptPhantomjsLocation = "/Users/j7ur8/Documents/local/bin/phantomjs";
    public static String JSEncryptScriptLocation = "/Users/j7ur8/Documents/GitHub/jsEncrypter/script/jsEncrypter_base64.js";

    @Override
    public JComponent main() {

        JComponent JSEncryptComponentPanel = new JPanel(new GridBagLayout());
        JSEncryptComponentPanel.setName("JSEncryptComponentPanel");
        JSEncryptComponentPanel.setBackground(Color.WHITE);

        JSEncryptComponentPanel.add(JSEncryptMenuTabBorderPanel(), new GridBagConstraints(
                0,0,
                1,1,
                0.5,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        JSEncryptComponentPanel.add(JSEncryptMenuTabPanel(),new GridBagConstraints(
                1,0,
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        JSEncryptComponentPanel.add(JSEncryptMenuTabBorderPanel(), new GridBagConstraints(
                2,0,
                1,1,
                0.5,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        JSEncryptComponentPanel.add(JSEncryptMainPanel(),new GridBagConstraints(
                0,1,
                3,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return JSEncryptComponentPanel;
    }

    private JComponent JSEncryptMenuTabPanel(){

        JComponent JSEncryptMenuTabPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
        JSEncryptMenuTabPanel.setName("JSEncryptMenuTabPanel");

        DiyJTabLabel JSEncryptMenuScriptLabel = new DiyJTabLabel("Script",true);
        JSEncryptMenuScriptLabel.setName("JSEncryptMenuScriptLabel");
        JSEncryptMenuTabPanel.add(JSEncryptMenuScriptLabel);


//        DiyJTabLabel JSEncryptMenuTestLabel = new DiyJTabLabel("Test");
//        JSEncryptMenuTestLabel.setName("JSEncryptMenuTestLabel");
//        JSEncryptMenuTabPanel.add(JSEncryptMenuTestLabel);

        for(Component label : JSEncryptMenuTabPanel.getComponents()){
            label.setFont(new Font("微软雅黑", Font.PLAIN,13));
            label.setPreferredSize(new Dimension(40,25));
        }

        return JSEncryptMenuTabPanel;
    }

    private JComponent JSEncryptMenuTabBorderPanel(){
        JComponent JSEncryptMenuTabBorderPanel = new JPanel();
        JSEncryptMenuTabBorderPanel.setName("JSEncryptMenuTabBorderPanel");
        JSEncryptMenuTabBorderPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(1,0,0,0),BorderFactory.createMatteBorder(0,0,1,0,Setting.class2DefaultDiyJTabBorderColor)));
        JSEncryptMenuTabBorderPanel.setBackground(Color.WHITE);

        return JSEncryptMenuTabBorderPanel;
    }

    private JComponent JSEncryptMainPanel() {

        JPanel JSEncryptMainPanel = new JPanel(new GridBagLayout());
        JSEncryptMainPanel.setName("JSEncryptMainPanel");

        JSEncryptMainPanel.add(JSEncryptMainScriptAreaScrollPanel(), new GridBagConstraints(
                0,0,
                1,1,
                0.9,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        JSEncryptMainPanel.add(JSEncryptMainControlPanel(), new GridBagConstraints(
                1,0,
                1,1,
                0.2,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return JSEncryptMainPanel;
    }


    private JComponent JSEncryptMainScriptAreaScrollPanel(){

        RSyntaxTextArea JSEncryptMainScriptArea = new RSyntaxTextArea();
        JSEncryptMainScriptArea.setName("JSEncryptMainScriptArea");
        JSEncryptMainScriptArea.setCodeFoldingEnabled(true);
        JSEncryptMainScriptArea.setLineWrap(true);
        JSEncryptMainScriptArea.setText((Helper.isFile(JSEncryptScriptLocation)?Helper.readFile(JSEncryptScriptLocation):"File Not Found"));
        JSEncryptMainScriptArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        JSEncryptMainScriptArea.setText(new String(Base.b64decoder.decode(Setting.PhantomScript), StandardCharsets.UTF_8));
        JSEncryptMainScriptArea.addKeyListener(new JSEncryptMainScriptAreaKeyListener());

        RTextScrollPane JSEncryptMainScriptAreaScrollPanel = new RTextScrollPane(JSEncryptMainScriptArea);
        JSEncryptMainScriptAreaScrollPanel.setHorizontalScrollBarPolicy(RTextScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JSEncryptMainScriptAreaScrollPanel.setName("JSEncryptMainScriptEditorScrollPanel");
        JSEncryptMainScriptAreaScrollPanel.setPreferredSize(new Dimension(0,0));
        JSEncryptMainScriptAreaScrollPanel.setBorder(null);

        return  JSEncryptMainScriptAreaScrollPanel;
    }

    private JComponent JSEncryptMainControlPanel(){

        JComponent JSEncryptMainControlPanel = new JPanel(new GridBagLayout());
        JSEncryptMainControlPanel.setName("JSEncryptMainControlPanel");

        JSEncryptMainControlPanel.add(JSEncryptMainControlEncryptPanel(),new GridBagConstraints(
                0,0,
                1,1,
                1,0.4,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        JSEncryptMainControlPanel.add(JSEncryptMainControlModePanel(),new GridBagConstraints(
                0,1,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        JSEncryptMainControlPanel.add(JSEncryptMainControlArgsPanel(),new GridBagConstraints(
                0,2,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        JSEncryptMainControlPanel.add(JSEncryptMainControlBlackPanel(),new GridBagConstraints(
                0,3,
                1,1,
                1,0.6,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return JSEncryptMainControlPanel;
    }

    private JComponent JSEncryptMainControlEncryptPanel(){
        JComponent JSEncryptMainControlEncryptPanel = new JPanel(new GridBagLayout());
        JSEncryptMainControlEncryptPanel.setName("JSEncryptMainControlEncryptPanel");

        JSEncryptMainControlEncryptPanel.add(JSEncryptMainPlaintextAreaScrollPanel(), new GridBagConstraints(
                0,0,
                1,1,
                1,0.5,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        JSEncryptMainControlEncryptPanel.add(JSEncryptMainCiphertextAreaScrollPanel(), new GridBagConstraints(
                0,1,
                1,1,
                1,0.5,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return JSEncryptMainControlEncryptPanel;
    }

    private JComponent JSEncryptMainControlModePanel(){
        JComponent JSEncryptMainControlModePanel = new JPanel(new GridBagLayout());
        JSEncryptMainControlModePanel.setName("JSEncryptMainControlModePanel");

        DiyJButton JSEncryptMainControlAesButton = new DiyJButton("Load AES Script");
        JSEncryptMainControlAesButton.setName("JSEncryptMainControlAesButton");
        JSEncryptMainControlAesButton.setPreferredSize(new Dimension(0,30));
        DiyJButton JSEncryptMainControlRsaButton = new DiyJButton("Load RSA Script");
        JSEncryptMainControlRsaButton.setPreferredSize(new Dimension(0,30));
        JSEncryptMainControlRsaButton.setName("JSEncryptMainControlRsaButton");
        DiyJButton JSEncryptMainControlMd5Button = new DiyJButton("Load MD5 Script");
        JSEncryptMainControlMd5Button.setPreferredSize(new Dimension(0,30));
        JSEncryptMainControlMd5Button.setName("JSEncryptMainControlMD5Button");
        DiyJButton JSEncryptMainControlSm3Button = new DiyJButton("Load SM3 Script");
        JSEncryptMainControlSm3Button.setPreferredSize(new Dimension(0,30));
        JSEncryptMainControlSm3Button.setName("JSEncryptMainControlSM3Button");

        JSEncryptMainControlModePanel.add(JSEncryptMainControlAesButton,new GridBagConstraints(
                0,0,
                1,1,
                0.5,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        JSEncryptMainControlModePanel.add(JSEncryptMainControlRsaButton,new GridBagConstraints(
                1,0,
                1,1,
                0.5,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        JSEncryptMainControlModePanel.add(JSEncryptMainControlMd5Button,new GridBagConstraints(
                0,1,
                1,1,
                0.5,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        JSEncryptMainControlModePanel.add(JSEncryptMainControlSm3Button,new GridBagConstraints(
                1,1,
                1,1,
                0.5,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));


        return JSEncryptMainControlModePanel;
    }

    private JComponent JSEncryptMainControlArgsPanel(){
        JComponent JSEncryptMainControlArgsPanel = new JPanel(new GridBagLayout());
        JSEncryptMainControlArgsPanel.setName("JSEncryptMainControlArgsPanel");

        JLabel JSEncryptMainControlIPAddressLabel = new JLabel(" IPAddress:");
        JSEncryptMainControlIPAddressLabel.setName("JSEncryptMainControlIPAddressLabel");
        JSEncryptMainControlIPAddressLabel.setPreferredSize(new Dimension(70,30));
        JTextField JSEncryptMainControlIPAddressField = new JTextField();
        JSEncryptMainControlIPAddressField.setName("JSEncryptMainControlIPAddressField");
        JSEncryptMainControlIPAddressField.setPreferredSize(new Dimension(0,30));

        JLabel JSEncryptMainControlPortLabel = new JLabel("Port:");
        JSEncryptMainControlPortLabel.setName("JSEncryptMainControlPortLabel");
        JSEncryptMainControlPortLabel.setPreferredSize(new Dimension(33,30));
        JTextField JSEncryptMainControlPortsField = new JTextField();
        JSEncryptMainControlPortsField.setName("JSEncryptMainControlPortsField");
        JSEncryptMainControlPortsField.setPreferredSize(new Dimension(0,30));

        JLabel JSEncryptMainControlPhantomjsLabel = new JLabel("Phantomjs:");
        JSEncryptMainControlPhantomjsLabel.setName("JSEncryptMainControlPhantomjsLabel");
        JSEncryptMainControlPhantomjsLabel.setPreferredSize(new Dimension(70,30));
        JTextField JSEncryptMainControlPhantomjsField = new JTextField();
        JSEncryptMainControlPhantomjsField.setName("JSEncryptMainControlPhantomjsField");
        JSEncryptMainControlPhantomjsField.setPreferredSize(new Dimension(0,30));

        DiyJButton JSEncryptMainControlTestButton = new DiyJButton("Test");
        JSEncryptMainControlTestButton.setPreferredSize(new Dimension(0,30));
        JSEncryptMainControlTestButton.setName("JSEncryptMainControlTestButton");

        JSEncryptMainControlArgsPanel.add(JSEncryptMainControlIPAddressLabel,new GridBagConstraints(
                0,0,
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        JSEncryptMainControlArgsPanel.add(JSEncryptMainControlIPAddressField,new GridBagConstraints(
                1,0,
                1,1,
                0.8,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        JSEncryptMainControlArgsPanel.add(JSEncryptMainControlPortLabel,new GridBagConstraints(
                2,0,
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        JSEncryptMainControlArgsPanel.add(JSEncryptMainControlPortsField,new GridBagConstraints(
                3,0,
                1,1,
                0.2,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));
        JSEncryptMainControlArgsPanel.add(JSEncryptMainControlPortsField,new GridBagConstraints(
                3,0,
                1,1,
                0.5,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));
        JSEncryptMainControlArgsPanel.add(JSEncryptMainControlPhantomjsLabel,new GridBagConstraints(
                0,1,
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));
        JSEncryptMainControlArgsPanel.add(JSEncryptMainControlPhantomjsField,new GridBagConstraints(
                1,1,
                3,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));
        JSEncryptMainControlArgsPanel.add(JSEncryptMainControlTestButton,new GridBagConstraints(
                0,2,
                4,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return JSEncryptMainControlArgsPanel;
    }
    private JComponent JSEncryptMainPlaintextAreaScrollPanel(){

        RSyntaxTextArea JSEncryptMainPlaintextArea = new RSyntaxTextArea();
        JSEncryptMainPlaintextArea.setName("JSEncryptMainPlaintextArea");
        JSEncryptMainPlaintextArea.setCodeFoldingEnabled(true);
        JSEncryptMainPlaintextArea.setLineWrap(true);
        JSEncryptMainPlaintextArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        JSEncryptMainPlaintextArea.addKeyListener(new JSEncryptMainScriptAreaKeyListener());

        RTextScrollPane JSEncryptMainPlaintextAreaScrollPanel = new RTextScrollPane(JSEncryptMainPlaintextArea);
        JSEncryptMainPlaintextAreaScrollPanel.setHorizontalScrollBarPolicy(RTextScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JSEncryptMainPlaintextAreaScrollPanel.setName("JSEncryptMainPlaintextAreaScrollPanel");
        JSEncryptMainPlaintextAreaScrollPanel.setPreferredSize(new Dimension(0,0));
        JSEncryptMainPlaintextAreaScrollPanel.setLineNumbersEnabled(false);
        JSEncryptMainPlaintextAreaScrollPanel.setBorder(BorderFactory.createMatteBorder(0,0,1,0,Setting.class2DefaultDiyJTabBorderColor));

        return JSEncryptMainPlaintextAreaScrollPanel;
    }


    private JComponent JSEncryptMainCiphertextAreaScrollPanel(){

        RSyntaxTextArea JSEncryptMainCiphertextArea = new RSyntaxTextArea();
        JSEncryptMainCiphertextArea.setName("JSEncryptMainCiphertextArea");
        JSEncryptMainCiphertextArea.setCodeFoldingEnabled(true);
        JSEncryptMainCiphertextArea.setLineWrap(true);
        JSEncryptMainCiphertextArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        JSEncryptMainCiphertextArea.addKeyListener(new JSEncryptMainScriptAreaKeyListener());

        RTextScrollPane JSEncryptMainCiphertextAreaScrollPanel = new RTextScrollPane(JSEncryptMainCiphertextArea);
        JSEncryptMainCiphertextAreaScrollPanel.setHorizontalScrollBarPolicy(RTextScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JSEncryptMainCiphertextAreaScrollPanel.setName("JSEncryptMainCiphertextAreaScrollPanel");
        JSEncryptMainCiphertextAreaScrollPanel.setPreferredSize(new Dimension(0,0));
        JSEncryptMainCiphertextAreaScrollPanel.setLineNumbersEnabled(false);
        JSEncryptMainCiphertextAreaScrollPanel.setBorder(BorderFactory.createMatteBorder(0,0,1,0,Setting.class2DefaultDiyJTabBorderColor));

        return JSEncryptMainCiphertextAreaScrollPanel;
    }


    private JComponent JSEncryptMainControlBlackPanel(){

        JComponent JSEncryptMainControlBlackPanel = new JPanel();
        JSEncryptMainControlBlackPanel.setName("JSEncryptMainControlBlackPanel");
        JSEncryptMainControlBlackPanel.setBackground(Color.BLACK);

        return JSEncryptMainControlBlackPanel;
    }

    static class JSEncryptMainScriptAreaKeyListener implements  KeyListener{
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
    }
}