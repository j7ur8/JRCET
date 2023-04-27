package jrcet.diycomponents;

import burp.MyExtender;
import jrcet.frame.Tools.Captcha.Captcha;
import jrcet.frame.Intruder.IntruderComponent;
import jrcet.help.Helper;
import jrcet.help.d4ocr.OCREngine;



import javax.imageio.ImageIO;
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
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;


import static jrcet.frame.Intruder.IntruderComponent.IntruderComponentPanel;
import static jrcet.frame.Intruder.Intruder.IntruderModuleComponentList;

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

    private void writeRScript(DiyJButton targetButton){
        JPanel targetPanel = (JPanel) targetButton.getParent().getParent();
        JScrollPane targetJScrollPane = (JScrollPane) targetPanel.getComponent(1);
        JTextArea targetJTextArea = (JTextArea) targetJScrollPane.getViewport().getComponent(0);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(targetJTextArea.getText()), this);
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
        DiyJButton eButton = (DiyJButton) e.getSource();
        String eButtonText = eButton.getText();
        String eButtonName = eButton.getName();
        JComponent rootPanel;
        JComponent tPanel;
        JComponent nPanel;
        JTextField tField = null;
        JLabel tLabel;
        String text;
        String[][] result;
        switch (eButtonName) {
            case "CaptchaMainCaptchaRequestMenuUrlButton":
                rootPanel = (JComponent) eButton.getParent().getParent();
                JTextArea sJTextArea = (JTextArea) Helper.getComponent(rootPanel, "CaptchaMainCaptchaRequestArea");assert sJTextArea != null;
                JTextField urlField = (JTextField) Helper.getComponent(rootPanel, "CaptchaMainCaptchaRequestMenuUrlField");assert urlField != null;
                JTextArea responseArea = (JTextArea) Helper.getComponent(rootPanel, "CaptchaMainCaptchaResponseArea");assert responseArea!=null;

                String raw = sJTextArea.getText();
                String url = urlField.getText();

                Thread thread = new Captcha.getCaptchaThread(url,raw,responseArea);
                thread.start();
                break;
            case "CaptchaMainCaptchaImageMenuIdentifyButton":
                rootPanel = (JComponent) eButton.getParent().getParent();
                JTextField ruleField = (JTextField)  Helper.getComponent(rootPanel, "CaptchaMainCaptchaResponseMenuRuleFiled");assert ruleField != null;
                JTextField rule1Field = (JTextField)  Helper.getComponent(rootPanel, "CaptchaMainCaptchaResponseMenuRule1Filed");assert rule1Field != null;
                JTextArea imageArea = (JTextArea) Helper.getComponent(rootPanel, "CaptchaMainCaptchaImageArea");assert imageArea!=null;
                JTextArea responseArea1 = (JTextArea) Helper.getComponent(rootPanel, "CaptchaMainCaptchaResponseArea");assert responseArea1!=null;

                String token="";
                String rule1 = rule1Field.getText();
                if(!Objects.equals(rule1, "")){
                    token=Helper.matchByRegular(responseArea1.getText(),rule1);
                }
                String imageText = Helper.matchByRegular(responseArea1.getText(),ruleField.getText());

                imageText = Helper.isBase64(imageText)?imageText:Helper.base64Encode(Captcha.responseText);
                imageArea.setText(imageText);

                ByteArrayInputStream in = new ByteArrayInputStream(Helper.base64Decode(imageText));
                BufferedImage image = null;
                try {
                    image = ImageIO.read(in);
                } catch (IOException ee) {

                }
                OCREngine engine = OCREngine.instance();
                String res = engine.recognize(image);

                JLabel resultLabel = new JLabel(res);
                resultLabel.setFont(new Font("微软雅黑",Font.PLAIN,20));

                JLabel tokenLabel= new JLabel(token);
                tokenLabel.setFont(new Font("微软雅黑",Font.PLAIN,20));

                byte[] imageByte = Helper.base64Decode(imageText);
                ImageIcon imageIcon = Helper.byte2img(imageByte);
                JLabel imageLabel = new JLabel(imageIcon, JLabel.CENTER);
                imageLabel.setPreferredSize(new Dimension(160,30));

                JPanel CaptchaMainCaptchaImageMenuResultPanel = (JPanel) Helper.getComponent(rootPanel, "CaptchaMainCaptchaImageMenuResultPanel"); assert  CaptchaMainCaptchaImageMenuResultPanel!=null;
                CaptchaMainCaptchaImageMenuResultPanel.removeAll();
                CaptchaMainCaptchaImageMenuResultPanel.add(imageLabel);
                CaptchaMainCaptchaImageMenuResultPanel.add(resultLabel);
                CaptchaMainCaptchaImageMenuResultPanel.add(tokenLabel);
                CaptchaMainCaptchaImageMenuResultPanel.repaint();
                CaptchaMainCaptchaImageMenuResultPanel.revalidate();
                break;
            case "Copy":
                writeRScript(eButton);
                break;
            case "IntruderMainControlAesButton":
            case "IntruderMainControlDesButton":
            case "IntruderMainControlMd5Button":
            case "IntruderMainControlRsaButton":
            case "IntruderMainControlBaseButton":
            case "IntruderMainControlAsciiButton":
            case "IntruderMainControlUnicodeButton":
                nPanel = getNewIntruderModulePanel(eButtonText);
                tPanel = Helper.getComponent(IntruderComponentPanel, "IntruderMainPanel");assert tPanel!=null;
                IntruderModuleComponentList.add(nPanel);
                tLabel = (DiyJLabel) Helper.getComponent(IntruderComponentPanel, "IntruderMainControlShowPanel"); assert tLabel!=null;
                String tLabelText = tLabel.getText();
                if(Objects.equals(tLabelText, "")){
                    tLabel.setText(tLabelText+eButtonText);
                }else{
                    tLabel.setText(tLabelText+"->"+eButtonText);
                }
                tPanel.remove(tPanel.getComponents().length-1);
                tPanel.add(nPanel, new GridBagConstraints(
                        0,tPanel.getComponentCount(),
                        1,1,
                        1,0,
                        GridBagConstraints.CENTER,
                        GridBagConstraints.BOTH,
                        new Insets(0,100,0,100),
                        0,0
                ));
                tPanel.add(Helper.blackPanel(),new GridBagConstraints(
                        0,tPanel.getComponentCount(),
                        1,1,
                        1,1,
                        GridBagConstraints.CENTER,
                        GridBagConstraints.BOTH,
                        new Insets(0,0,0,0),
                        0,0
                ));
                tPanel.updateUI();
                break;
            case "IntruderMainControlClearButton":
                tPanel = Helper.getComponent(IntruderComponentPanel, "IntruderMainPanel");assert tPanel!=null;
                IntruderModuleComponentList = new ArrayList<>();
                tLabel = (DiyJLabel) Helper.getComponent(IntruderComponentPanel, "IntruderMainControlShowPanel"); assert tLabel!=null;
                tLabel.setText("");
                while (tPanel.getComponentCount()!=1){
                    tPanel.remove(1);
                }
                tPanel.add(Helper.blackPanel(),new GridBagConstraints(
                        0,tPanel.getComponentCount(),
                        1,1,
                        1,1,
                        GridBagConstraints.CENTER,
                        GridBagConstraints.BOTH,
                        new Insets(0,0,0,0),
                        0,0
                ));
                tPanel.updateUI();
                break;
        }

    }
    public JComponent getNewIntruderModulePanel(String moduleName){
        switch (moduleName){
            case "Aes":
                return new IntruderComponent().IntruderMainAesPanel();
            case "Base":
                return new IntruderComponent().IntruderMainBasePanel();
            case "Rsa":
                return new IntruderComponent().IntruderMainRsaPanel();
            case "Ascii":
                return new IntruderComponent().IntruderMainAsciiPanel();
            case "Unicode":
                return new IntruderComponent().IntruderMainUnicodePanel();
            case "Md5":
                JComponent acc = new JPanel();
                acc.setName("IntruderMainMd5Panel");
                return acc;
            case "Des":
                JComponent acc1 = new JPanel();
                acc1.setName("IntruderMainDesPanel");
                return acc1;
        }
        return null;
    }

}
