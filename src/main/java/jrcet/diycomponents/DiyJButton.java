package jrcet.diycomponents;

import burp.Services;
import burp.lib.Helper;
import jrcet.diycomponents.DiyJTextArea.ui.rtextarea.RTextArea;
import jrcet.diycomponents.DiyJTextArea.ui.rtextarea.RTextScrollPane;
import jrcet.frame.tools.JSEncrypt.JSEncryptComponent;
import jrcet.frame.tools.JSEncrypt.JSEncrypt;
import jrcet.frame.tools.Solibrary.SoLibrary;
import jrcet.frame.tools.Solibrary.SoLibraryComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class DiyJButton extends JButton implements MouseListener, ClipboardOwner, ActionListener {

    public DiyJButton(String text) {
        setText(text);
        setOpaque(true);
        setFocusPainted(false);
        addMouseListener(this);
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(120,30));
        addActionListener(this);
        setFont(new Font("微软雅黑", Font.PLAIN,14));
    }

    public DiyJButton(String text,boolean flag) {

        setMargin(new Insets(0,0,0,0));
        StringBuilder buildText= new StringBuilder();
        for(int x = 0; x<text.length()-1;x++){
            String ch = Character.toString(text.charAt(x));
            buildText.append(ch).append("<br>");
        }
        buildText.append(text.charAt(text.length()-1));
        buildText = new StringBuilder("<html>"+buildText+"</html>");
        setText(String.valueOf(buildText));
        setOpaque(false);
        setBorder(BorderFactory.createMatteBorder(1,1,1,1,new Color(144,144,160)));
        setFocusPainted(false);
        addMouseListener(this);
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(20,50));
        addActionListener(this);
        setVerticalTextPosition( SwingConstants.CENTER );
    }

    private void SetDatabase(DiyJButton targetButton) {
        JPanel parentPanel = (JPanel) targetButton.getParent();
        JTextField DatabaseTextField = (JTextField) parentPanel.getComponent(1);
        if(Helper.isFile(DatabaseTextField.getText())){
            SoLibraryComponent.databasePath=DatabaseTextField.getText();
        }else{
            DatabaseTextField.setText("文件路径错误");
            SoLibraryComponent.databasePath="";
        }

    }

    private void Search(DiyJButton targetButton){
        JPanel parentPanel = (JPanel) targetButton.getParent();
        JTextField nameField = (JTextField) parentPanel.getComponent(4);
        JTextField cardField = (JTextField) parentPanel.getComponent(6);
        JTextField phoneField = (JTextField) parentPanel.getComponent(8);
        ((JPanel)((JScrollPane)SoLibraryComponent.SoLibraryResultScrollPanel).getViewport().getView()).removeAll();
        SoLibrary.Search(nameField.getText(),cardField.getText(),phoneField.getText());

    }

    private void SetPath(DiyJButton targetButton) throws InterruptedException {
        JPanel parentPanel = (JPanel) targetButton.getParent();
        JTextField phantomjsTextField = (JTextField) parentPanel.getComponent(7);
        JTextField jsScriptTextField = (JTextField) parentPanel.getComponent(9);
        String phantomjsLocation = phantomjsTextField.getText();
        String jsScriptLocation = jsScriptTextField.getText();
        if (Helper.isFile(phantomjsLocation) && Helper.isFile(jsScriptLocation)){
            JSEncryptComponent.JSEncryptPhantomjsLocation =phantomjsLocation;
            JSEncryptComponent.JSEncryptScriptLocation =jsScriptLocation;

            JPanel buzhongyaodemingzi = (JPanel) parentPanel.getParent();
            JPanel buzhongyaodemingzi2 = (JPanel) buzhongyaodemingzi.getComponent(1);
            RTextArea targetJTextArea = ((RTextScrollPane) buzhongyaodemingzi2.getComponent(0)).getTextArea();
            targetJTextArea.setText(Helper.readFile(JSEncryptComponent.JSEncryptScriptLocation));
            if(JSEncrypt.sendTestConnect()){
                jsDisConnect(targetButton);
            }
        }

    }

    private void jsDisConnect(DiyJButton targetButton) throws InterruptedException {
         JSEncrypt.phantomjsProcess.destroy();
        JPanel parentPanel = (JPanel) targetButton.getParent();
        JLabel targetLabel = (JLabel) parentPanel.getComponent(12);
        if(JSEncrypt.sendTestConnect()){
            targetLabel.setText("True");
            targetLabel.setForeground(Color.GREEN);
        }else{
            targetLabel.setText("False");
            targetLabel.setForeground(Color.RED);
        }

    }

    private void loadRScript(String jsFilename){

    }

    private void writeRScript(DiyJButton targetButton){
        JPanel targetPanel = (JPanel) targetButton.getParent().getParent();
        JScrollPane targetJScrollPane = (JScrollPane) targetPanel.getComponent(1);
        JTextArea targetJTextArea = (JTextArea) targetJScrollPane.getViewport().getComponent(0);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(targetJTextArea.getText()), this);
    }

    private void conTestJSEncrypt(DiyJButton targetButton) throws InterruptedException {
        Thread phantomjsThread = new JSEncrypt.StreamGobble(JSEncryptComponent.JSEncryptPhantomjsLocation,JSEncryptComponent.JSEncryptScriptLocation);
        phantomjsThread.start();
        Thread.sleep(1000);
        JSEncrypt.phantomjsProcess = JSEncrypt.StreamGobble.p;
        Services.setService("phantomjs", JSEncrypt.phantomjsProcess);
        JPanel parentPanel = (JPanel) targetButton.getParent();
        JLabel targetLabel = (JLabel) parentPanel.getComponent(12);
        if(JSEncrypt.sendTestConnect()){
            targetLabel.setText("True");
            targetLabel.setForeground(Color.GREEN);
        }else{
            targetLabel.setText("False");
            targetLabel.setForeground(Color.RED);
        }
    }

    private void jSTestJSEncrypt(DiyJButton targetButton){
        JPanel parentPanel = (JPanel) targetButton.getParent().getParent().getComponent(1);
        JScrollPane payloadScrollPane = (JScrollPane) parentPanel.getComponent(1);
        JTextArea payloadJTextArea = (JTextArea) payloadScrollPane.getViewport().getComponent(0);

        JScrollPane targetScrollPane = (JScrollPane) parentPanel.getComponent(2);
        JTextArea targetJTextArea = (JTextArea) targetScrollPane.getViewport().getComponent(0);

        String testPayload=payloadJTextArea.getText();

        SwingUtilities.invokeLater(() -> {
            String[] payloads = testPayload.split("\r|\n");
            String tmp = "";
            for (String payload : payloads) {
                if(payload == null ||payload.equals("")){
                    continue;
                }
                String newPayload = JSEncrypt.sendPayload(payload);
                newPayload += System.lineSeparator();
                tmp += newPayload;
            }

            // 如果是Windows，先UTF-8编码在显示，解决Windows上乱码问题
            if(System.getProperty("os.name").toLowerCase().contains("win")){
                tmp = JSEncrypt.transformCharset(tmp,"UTF-8");
            }
            targetJTextArea.setText(tmp);
        });
    }

    @Override
    public void mouseClicked(MouseEvent e) {


    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void lostOwnership(Clipboard clipboard, Transferable contents) {

    }

    @Override
    public void actionPerformed(ActionEvent e){
        DiyJButton targetButton = (DiyJButton) e.getSource();
        String caseString = targetButton.getText().replaceAll("<[a-z/]{1,6}>","");
        switch (caseString) {
            case "Copy":
                writeRScript(targetButton);
                break;
            case "Connection":
                try {
                    conTestJSEncrypt(targetButton);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                break;
            case "JSTest": jSTestJSEncrypt(targetButton);
                break;
            case "SetPath":
                try {
                    SetPath(targetButton);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                break;
            case "DisConnect":
                try {
                    jsDisConnect(targetButton);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                break;
            case "Search":
                Search(targetButton);
                break;
            case "SetDatabase":
                SetDatabase(targetButton);
                break;
            case "Pause":
                Pause(targetButton);
                break;
            case "Continue":
                Continue(targetButton);
                break;
            case "B16":
                Base(targetButton,caseString);
                break;
            case "B32":
                Base(targetButton,caseString);
                break;
            case "B58":
                Base(targetButton,caseString);
                break;
            case "B64":
                Base(targetButton,caseString);
                break;
            case "B85":
                Base(targetButton,caseString);
                break;
            case "B91":
                Base(targetButton,caseString);
                break;
            case "B92":
                Base(targetButton,caseString);
            }
        }

    private void Base(JButton targetButton, String caseString) {

    }

    private void Continue(DiyJButton targetButton) {

    }

    private void Pause(DiyJButton targetButton) {

    }
}
