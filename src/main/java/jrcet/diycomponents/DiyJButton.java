package jrcet.diycomponents;

import burp.Services;
import jrcet.frame.tools.JSEncrypt.JSEncryptComponent;
import jrcet.frame.tools.JSEncrypt.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DiyJButton extends JButton implements MouseListener, ClipboardOwner {


    public DiyJButton(String text) {
        setPreferredSize(new Dimension(160,30));
        addMouseListener(this);
        setFocusPainted(false);
        setFont(new Font("微软雅黑", Font.PLAIN,14));
        setOpaque(true);
        setBackground(Color.WHITE);
        setText(text);

        this.addActionListener( (e)->{
            DiyJButton targetButton = (DiyJButton)e.getSource();

            switch (targetButton.getText()){
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
                case "JSTest":
                    jSTestJSEncrypt(targetButton);
                    break;
                case "SetPhantomJS":
                    SetPhantomJS(targetButton);
                case "DisConnect":
                    try {
                        jsDisConnect(targetButton);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    break;
            }

        });
    }

    private void SetPhantomJS(DiyJButton targetButton) {
        JPanel parentPanel = (JPanel) targetButton.getParent();
        JLabel targetLabel = (JLabel) parentPanel.getComponent(11);
    }

    private void jsDisConnect(DiyJButton targetButton) throws InterruptedException {
        Utils.phantomjsProcess.destroy();
        JPanel parentPanel = (JPanel) targetButton.getParent();
        JLabel targetLabel = (JLabel) parentPanel.getComponent(11);
        if(Utils.sendTestConnect()){
            targetLabel.setText("True");
        }else{
            targetLabel.setText("False");
        }

    }

    private void writeRScript(DiyJButton targetButton){
        JPanel targetPanel = (JPanel) targetButton.getParent().getParent();
        JScrollPane targetJScrollPane = (JScrollPane) targetPanel.getComponent(1);
        JTextArea targetJTextArea = (JTextArea) targetJScrollPane.getViewport().getComponent(0);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(targetJTextArea.getText()), this);
    }

    private void conTestJSEncrypt(DiyJButton targetButton) throws InterruptedException {
        Thread phantomjsThread = new Utils.StreamGobble(JSEncryptComponent.phantomjsLocation,JSEncryptComponent.jScriptLocation);
        phantomjsThread.start();
        Thread.sleep(1000);
        Utils.phantomjsProcess = Utils.StreamGobble.p;
        Services.setService("phantomjs",Utils.phantomjsProcess);
        JPanel parentPanel = (JPanel) targetButton.getParent();
        JLabel targetLabel = (JLabel) parentPanel.getComponent(11);
        if(Utils.sendTestConnect()){
            targetLabel.setText("True");
        }else{
            targetLabel.setText("False");
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
                String newPayload = Utils.sendPayload(payload);
                newPayload += System.lineSeparator();
                tmp += newPayload;
            }

            // 如果是Windows，先UTF-8编码在显示，解决Windows上乱码问题
            if(System.getProperty("os.name").toLowerCase().contains("win")){
                tmp = Utils.transformCharset(tmp,"UTF-8");
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
}
