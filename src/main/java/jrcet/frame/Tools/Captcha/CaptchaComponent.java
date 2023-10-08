package jrcet.frame.Tools.Captcha;

import burp.api.montoya.ui.editor.HttpRequestEditor;
import burp.api.montoya.ui.editor.HttpResponseEditor;
import jrcet.diycomponents.*;
import jrcet.help.Helper;


import javax.swing.*;
import java.awt.*;

import static burp.MyExtender.BurpAPI;

public class CaptchaComponent extends DiyJComponent {

    public static JComponent CaptchaComponentPanel = null;

    public static HttpRequestEditor CaptchaRequestEditor = BurpAPI.userInterface().createHttpRequestEditor();

    public static HttpResponseEditor CaptchaResponseEditor = BurpAPI.userInterface().createHttpResponseEditor();

    public JComponent component(){
        CaptchaComponentPanel = new JPanel(new GridBagLayout());
        CaptchaComponentPanel.setName("CaptchaComponentPanel");
        CaptchaComponentPanel.setBackground(Color.WHITE);
        CaptchaComponentPanel.setBorder(BorderFactory.createMatteBorder(1,0,0,0, Helper.gray));

        CaptchaComponentPanel.add(CaptchaMenuPanel(), new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        CaptchaComponentPanel.add(CaptchaViewPanel(), new GridBagConstraints(
                0,1,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return CaptchaComponentPanel;
    }

    public JComponent CaptchaMenuPanel(){
        JComponent CaptchaMenuPanel = new JPanel(new GridBagLayout());
        CaptchaMenuPanel.setName("CaptchaMenuPanel");
        CaptchaMenuPanel.setBackground(Color.WHITE);

        CaptchaMenuPanel.add(CaptchaMenuRequestPanel(), new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,5,0,5),
                0,0
        ));

        CaptchaMenuPanel.add(CaptchaMenuResponsePanel(), new GridBagConstraints(
                1,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,5,0,5),
                0,0
        ));

        CaptchaMenuPanel.add(CaptchaMenuResultPanel(),new GridBagConstraints(
                0,1,
                2,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,5),
                0,0
        ));

        return CaptchaMenuPanel;
    }


    private JComponent CaptchaMenuRequestPanel(){
        JComponent CaptchaMenuRequestPanel = new JPanel(new GridBagLayout());
        CaptchaMenuRequestPanel.setName("CaptchaMenuRequestPanel");
        CaptchaMenuRequestPanel.setPreferredSize(new Dimension(0,35));
        CaptchaMenuRequestPanel.setBackground(Color.WHITE);


        DiyJTextField CaptchaMenuRequestUrlField = new DiyJTextField("URL");
        CaptchaMenuRequestUrlField.setName("CaptchaMenuRequestUrlField");
        CaptchaMenuRequestUrlField.setPreferredSize(new Dimension(0,0));

        CaptchaMenuRequestPanel.add(CaptchaMenuRequestUrlField, new GridBagConstraints(
                0,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        DiyJButton CaptchaMenuRequestUrlButton = new DiyJButton("获取");
        CaptchaMenuRequestUrlButton.setName("CaptchaMenuRequestUrlButton");
        CaptchaMenuRequestUrlButton.setPreferredSize(new Dimension(80,0));
        CaptchaMenuRequestPanel.add(CaptchaMenuRequestUrlButton, new GridBagConstraints(
                1,0,
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        DiyJButton CaptchaMenuRequestIdentifyButton = new DiyJButton("识别");
        CaptchaMenuRequestIdentifyButton.setPreferredSize(new Dimension(80,0));
        CaptchaMenuRequestIdentifyButton.setName("CaptchaMenuRequestIdentifyButton");
        CaptchaMenuRequestPanel.add(CaptchaMenuRequestIdentifyButton, new GridBagConstraints(
                2,0,
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return CaptchaMenuRequestPanel;
    }

    private JComponent CaptchaMenuResponsePanel(){
        //CaptchaCaptchaResponseMenuPanel
        JComponent CaptchaMenuResponsePanel = new JPanel(new GridBagLayout());
        CaptchaMenuResponsePanel.setPreferredSize(new Dimension(0,35));
        CaptchaMenuResponsePanel.setName("CaptchaMenuResponsePanel");
        CaptchaMenuResponsePanel.setBackground(Color.WHITE);

        DiyJTextField CaptchaMenuResponseImageField = new DiyJTextField("ImageRule");
        CaptchaMenuResponseImageField.setText("(([A-Za-z0-9+\\/]{4})*([A-Za-z0-9+\\/]{4}|[A-Za-z0-9+\\/]{3}=|[A-Za-z0-9+\\/]{2}==))");
        CaptchaMenuResponseImageField.setName("CaptchaMenuResponseImageField");
        CaptchaMenuResponseImageField.setPreferredSize(new Dimension(0,0));

        CaptchaMenuResponsePanel.add(CaptchaMenuResponseImageField, new GridBagConstraints(
                0,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));


        DiyJTextField CaptchaMenuResponseTokenField = new DiyJTextField("TokenRule");
        CaptchaMenuResponseTokenField.setName("CaptchaMenuResponseTokenField");
        CaptchaMenuResponseTokenField.setPreferredSize(new Dimension(0,0));
        CaptchaMenuResponsePanel.add(CaptchaMenuResponseTokenField, new GridBagConstraints(
                1,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        DiyJTextField CaptchaMenuResponseExtField = new DiyJTextField("ExtStr");
        CaptchaMenuResponseExtField.setName("CaptchaMenuResponseExtField");
        CaptchaMenuResponseExtField.setPreferredSize(new Dimension(0,0));
        CaptchaMenuResponsePanel.add(CaptchaMenuResponseExtField, new GridBagConstraints(
                2,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));


        return CaptchaMenuResponsePanel;
    }

    private JComponent CaptchaMenuResultPanel(){

        JComponent CaptchaMenuResultPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        CaptchaMenuResultPanel.setName("CaptchaMenuResultPanel");
        CaptchaMenuResultPanel.setPreferredSize(new Dimension(0,30));

        return CaptchaMenuResultPanel;
    }

    private JComponent CaptchaViewPanel(){
        DiyVariablePanel CaptchaViewPanel = new DiyVariablePanel(
                CaptchaRequestEditor.uiComponent(), "CaptchaRequestEditor",
                CaptchaResponseEditor.uiComponent(), "CaptchaResponseEditor",
                DiyVariablePanel.View.HORIZONTAL
        );

        CaptchaViewPanel.setPreferredSize(new Dimension(0,0));

        CaptchaViewPanel.setName("CaptchaViewPanel");
        return CaptchaViewPanel;
    }


}
