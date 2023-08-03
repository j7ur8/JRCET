package jrcet.frame.Tools.Captcha;

import burp.api.montoya.ui.editor.HttpRequestEditor;
import burp.api.montoya.ui.editor.HttpResponseEditor;
import com.coreyd97.BurpExtenderUtilities.VariableViewPanel;
import jrcet.diycomponents.DiyJButton;
import jrcet.diycomponents.DiyJComponent;


import jrcet.diycomponents.DiyJTextAreaScrollPane;
import jrcet.diycomponents.DiyJTextField;
import jrcet.frame.Setting.Setting;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

import static burp.MyExtender.API;

public class CaptchaComponent extends DiyJComponent {

    public static JComponent CaptchaComponentPanel = null;

    public static HttpRequestEditor CaptchaRequestEditor = API.userInterface().createHttpRequestEditor();

    public static HttpResponseEditor CaptchaResponseEditor = API.userInterface().createHttpResponseEditor();

    public JComponent main(){
        CaptchaComponentPanel = new JPanel(new GridBagLayout());
        CaptchaComponentPanel.setName("CaptchaComponentPanel");
        CaptchaComponentPanel.setBackground(Color.WHITE);
        CaptchaComponentPanel.setBorder(BorderFactory.createMatteBorder(1,0,0,0, Setting.gray));

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
        VariableViewPanel CaptchaViewPanel = new VariableViewPanel(null, null,
                CaptchaRequestEditor.uiComponent(), "LowRequest",
                CaptchaResponseEditor.uiComponent(), "LowResponse",
                VariableViewPanel.View.HORIZONTAL);

        CaptchaViewPanel.setPreferredSize(new Dimension(0,0));

        CaptchaViewPanel.setName("CaptchaViewPanel");
        return CaptchaViewPanel;
    }


}
