package jrcet.diycomponents;

import burp.MyExtender;
import jrcet.frame.Asset.Asset;
import jrcet.frame.Tools.Captcha.Captcha;
import jrcet.frame.Intruder.IntruderComponent;
import jrcet.frame.Tools.Captcha.CaptchaComponent;
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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;


import static burp.MyExtender.API;
import static jrcet.frame.Intruder.IntruderComponent.IntruderComponentPanel;
import static jrcet.frame.Intruder.Intruder.IntruderModuleComponentList;
import static jrcet.frame.Tools.Captcha.Captcha.getImageRuleField;
import static jrcet.frame.Tools.Captcha.Captcha.getTokenRuleField;
import static jrcet.frame.Tools.Captcha.CaptchaComponent.*;

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
        JLabel tLabel;
        switch (eButtonName) {
            case "CaptchaMenuRequestUrlButton" -> new Captcha.setCaptchaWorker(CaptchaRequestEditor.getRequest()).execute();
            case "CaptchaMenuImageIdentifyButton" -> {

                String responseText = new String(CaptchaResponseEditor.getResponse().body().getBytes(), StandardCharsets.UTF_8);
//                API.logging().output().println(responseText);

//                API.logging().output().println(getImageRuleField());
//                API.logging().output().println(getImageRuleField().getText());
                String imageText = Helper.matchByRegular(responseText, getImageRuleField().getText());
                imageText = Helper.isBase64(imageText) ? imageText : Helper.base64Encode(imageText);
//                API.logging().output().println(imageText);

                ByteArrayInputStream in = new ByteArrayInputStream(Helper.base64Decode(imageText));
                BufferedImage image = null;
                try {
                    image = ImageIO.read(in);
                } catch (IOException ignored) {

                }
                OCREngine engine = OCREngine.instance();
                String res = engine.recognize(image);
//                API.logging().output().println(res);

                JLabel resultLabel = new JLabel(res);
                resultLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));

                String token = "";
                String rule1 = getTokenRuleField().getText();
                if (!Objects.equals(rule1, "")) {
                    token = Helper.matchByRegular(responseText, rule1);
                }
                JLabel tokenLabel = new JLabel(token);
                tokenLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
                byte[] imageByte = Helper.base64Decode(imageText);
                ImageIcon imageIcon = Helper.byte2img(imageByte);
                JLabel imageLabel = new JLabel(imageIcon, JLabel.CENTER);
                imageLabel.setPreferredSize(new Dimension(160, 30));
                JPanel CaptchaMainCaptchaImageMenuResultPanel = (JPanel) Helper.getComponent(CaptchaComponentPanel, "CaptchaMenuImageResultPanel");
                assert CaptchaMainCaptchaImageMenuResultPanel != null;
                CaptchaMainCaptchaImageMenuResultPanel.removeAll();
                CaptchaMainCaptchaImageMenuResultPanel.add(imageLabel);
                CaptchaMainCaptchaImageMenuResultPanel.add(resultLabel);
                CaptchaMainCaptchaImageMenuResultPanel.add(tokenLabel);
                CaptchaMainCaptchaImageMenuResultPanel.repaint();
                CaptchaMainCaptchaImageMenuResultPanel.revalidate();
            }
            case "IntruderMainControlAesButton", "IntruderMainControlDesButton", "IntruderMainControlMd5Button", "IntruderMainControlRsaButton", "IntruderMainControlBaseButton", "IntruderMainControlAsciiButton", "IntruderMainControlUnicodeButton" -> {
                nPanel = getNewIntruderModulePanel(eButtonText);
                tPanel = Helper.getComponent(IntruderComponentPanel, "IntruderMainPanel");
                assert tPanel != null;
                IntruderModuleComponentList.add(nPanel);
                tLabel = (DiyJLabel) Helper.getComponent(IntruderComponentPanel, "IntruderMainControlShowPanel");
                assert tLabel != null;
                String tLabelText = tLabel.getText();
                if (Objects.equals(tLabelText, "")) {
                    tLabel.setText(tLabelText + eButtonText);
                } else {
                    tLabel.setText(tLabelText + "->" + eButtonText);
                }
                tPanel.remove(tPanel.getComponents().length - 1);
                tPanel.add(nPanel, new GridBagConstraints(
                        0, tPanel.getComponentCount(),
                        1, 1,
                        1, 0,
                        GridBagConstraints.CENTER,
                        GridBagConstraints.BOTH,
                        new Insets(0, 100, 0, 100),
                        0, 0
                ));
                tPanel.add(Helper.blackPanel(), new GridBagConstraints(
                        0, tPanel.getComponentCount(),
                        1, 1,
                        1, 1,
                        GridBagConstraints.CENTER,
                        GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 0),
                        0, 0
                ));
                tPanel.updateUI();
            }
            case "IntruderMainControlClearButton" -> {
                tPanel = Helper.getComponent(IntruderComponentPanel, "IntruderMainPanel");
                assert tPanel != null;
                IntruderModuleComponentList = new ArrayList<>();
                tLabel = (DiyJLabel) Helper.getComponent(IntruderComponentPanel, "IntruderMainControlShowPanel");
                assert tLabel != null;
                tLabel.setText("");
                while (tPanel.getComponentCount() != 1) {
                    tPanel.remove(1);
                }
                tPanel.add(Helper.blackPanel(), new GridBagConstraints(
                        0, tPanel.getComponentCount(),
                        1, 1,
                        1, 1,
                        GridBagConstraints.CENTER,
                        GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 0),
                        0, 0
                ));
                tPanel.updateUI();
            }
            case "AssetMainBodyControlLastButton" -> Asset.lastPage();
            case "AssetMainBodyControlNextButton" -> Asset.nextPage();
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
