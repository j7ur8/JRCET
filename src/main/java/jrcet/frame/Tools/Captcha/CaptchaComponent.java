package jrcet.frame.Tools.Captcha;

import jrcet.diycomponents.DiyJButton;
import jrcet.diycomponents.DiyJComponent;


import jrcet.diycomponents.DiyJTextAreaScrollPane;
import jrcet.frame.Setting.Setting;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

public class CaptchaComponent extends DiyJComponent {

    public static JComponent CaptchaComponentPanel = null;

    public JComponent main(){

        CaptchaComponentPanel = new JPanel(new GridBagLayout());
        CaptchaComponentPanel.setName("CaptchaComponentPanel");
        CaptchaComponentPanel.setBackground(Color.WHITE);
        CaptchaComponentPanel.setBorder(BorderFactory.createMatteBorder(1,0,0,0, Setting.gray));

        CaptchaComponentPanel.add(CaptchaMainPanel(), new GridBagConstraints(
                0,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));


        return CaptchaComponentPanel;
    }

    public JComponent CaptchaMainPanel(){
        JComponent CaptchaMainPanel = new JPanel(new GridBagLayout());
        CaptchaMainPanel.setBackground(Color.WHITE);
        CaptchaMainPanel.setName("CaptchaMainPanel");

        CaptchaMainPanel.add(CaptchaMainCaptchaPanel(), new GridBagConstraints(
                0,0,
                1,1,
                0.75,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return CaptchaMainPanel;
    }

    public JComponent CaptchaMainCaptchaPanel(){
        JComponent CaptchaMainCaptchaPanel = new JPanel(new GridBagLayout());
        CaptchaMainCaptchaPanel.setName("CaptchaMainCaptchaPanel");
        CaptchaMainCaptchaPanel.setBackground(Color.WHITE);

        //CaptchaMainCaptchaRequestMenuPanel
        JComponent CaptchaMainCaptchaRequestMenuPanel = new JPanel(new GridBagLayout());
        CaptchaMainCaptchaRequestMenuPanel.setName("CaptchaMainCaptchaRequestMenuPanel");
        CaptchaMainCaptchaRequestMenuPanel.setPreferredSize(new Dimension(0,35));
        CaptchaMainCaptchaRequestMenuPanel.setBackground(Color.WHITE);

        JLabel CaptchaMainCaptchaRequestMenuUrlLabel = new JLabel("URL: ");
        CaptchaMainCaptchaRequestMenuUrlLabel.setName("CaptchaMainCaptchaRequestMenuUrlLabel");
        JTextField CaptchaMainCaptchaRequestMenuUrlField = new JTextField();
        CaptchaMainCaptchaRequestMenuUrlField.setName("CaptchaMainCaptchaRequestMenuUrlField");
        DiyJButton CaptchaMainCaptchaRequestMenuUrlButton = new DiyJButton("获取");
        CaptchaMainCaptchaRequestMenuUrlButton.setName("CaptchaMainCaptchaRequestMenuUrlButton");
        CaptchaMainCaptchaRequestMenuUrlButton.setPreferredSize(new Dimension(0,80));


        CaptchaMainCaptchaRequestMenuPanel.add(CaptchaMainCaptchaRequestMenuUrlLabel, new GridBagConstraints(
                0,0,
                1,1,
                0,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));
        CaptchaMainCaptchaRequestMenuPanel.add(CaptchaMainCaptchaRequestMenuUrlField, new GridBagConstraints(
                1,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));
        CaptchaMainCaptchaRequestMenuPanel.add(CaptchaMainCaptchaRequestMenuUrlButton, new GridBagConstraints(
                2,0,
                1,1,
                0,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        //CaptchaMainCaptchaRequestAreaScrollPane
        DiyJTextAreaScrollPane CaptchaMainCaptchaRequestAreaScrollPane = new DiyJTextAreaScrollPane("CaptchaMainCaptchaRequestArea");

        CaptchaMainCaptchaRequestAreaScrollPane.setText("#请输入请求数据包...");


        //CaptchaMainCaptchaResponseMenuPanel
        JComponent CaptchaMainCaptchaResponseMenuPanel = new JPanel(new GridBagLayout());
        CaptchaMainCaptchaResponseMenuPanel.setPreferredSize(new Dimension(0,35));
        CaptchaMainCaptchaResponseMenuPanel.setName("CaptchaMainCaptchaResponseMenuPanel");
        CaptchaMainCaptchaResponseMenuPanel.setBackground(Color.WHITE);

        JLabel CaptchaMainCaptchaResponseMenuRuleLabel = new JLabel("Rule: ");
        CaptchaMainCaptchaResponseMenuRuleLabel.setName("CaptchaMainCaptchaResponseMenuRuleLabel");
        JTextField CaptchaMainCaptchaResponseMenuRuleFiled = new JTextField("");
        CaptchaMainCaptchaResponseMenuRuleFiled.setName("CaptchaMainCaptchaResponseMenuRuleFiled");
        CaptchaMainCaptchaResponseMenuRuleFiled.setPreferredSize(new Dimension(0,0));

        JLabel CaptchaMainCaptchaResponseMenuRule1Label = new JLabel("Rule1: ");
        CaptchaMainCaptchaResponseMenuRule1Label.setName("CaptchaMainCaptchaResponseMenuRule1Label");
        JTextField CaptchaMainCaptchaResponseMenuRule1Filed = new JTextField("");
        CaptchaMainCaptchaResponseMenuRule1Filed.setName("CaptchaMainCaptchaResponseMenuRule1Filed");
        CaptchaMainCaptchaResponseMenuRule1Filed.setPreferredSize(new Dimension(0,0));

        JLabel CaptchaMainCaptchaResponseMenuStrLabel = new JLabel("Str: ");
        CaptchaMainCaptchaResponseMenuStrLabel.setName("CaptchaMainCaptchaResponseMenuStrLabel");
        JTextField CaptchaMainCaptchaResponseMenuStrField = new JTextField("");
        CaptchaMainCaptchaResponseMenuStrField.setName("CaptchaMainCaptchaResponseMenuStrField");
        CaptchaMainCaptchaResponseMenuStrField.setPreferredSize(new Dimension(0,0));

        CaptchaMainCaptchaResponseMenuPanel.add(CaptchaMainCaptchaResponseMenuRuleLabel, new GridBagConstraints(
                0,0,
                1,1,
                0,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        CaptchaMainCaptchaResponseMenuPanel.add(CaptchaMainCaptchaResponseMenuRuleFiled, new GridBagConstraints(
                1,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        CaptchaMainCaptchaResponseMenuPanel.add(CaptchaMainCaptchaResponseMenuRule1Label, new GridBagConstraints(
                2,0,
                1,1,
                0,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        CaptchaMainCaptchaResponseMenuPanel.add(CaptchaMainCaptchaResponseMenuRule1Filed, new GridBagConstraints(
                3,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        CaptchaMainCaptchaResponseMenuPanel.add(CaptchaMainCaptchaResponseMenuStrLabel, new GridBagConstraints(
                4,0,
                1,1,
                0,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));
        CaptchaMainCaptchaResponseMenuPanel.add(CaptchaMainCaptchaResponseMenuStrField, new GridBagConstraints(
                5,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        //CaptchaMainCaptchaResponseAreaScrollPane
        DiyJTextAreaScrollPane CaptchaMainCaptchaResponseAreaScrollPane = new DiyJTextAreaScrollPane("CaptchaMainCaptchaResponseArea");

        CaptchaMainCaptchaPanel.add(CaptchaMainCaptchaRequestMenuPanel, new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,5,0,5),
                0,0
        ));

        CaptchaMainCaptchaPanel.add(CaptchaMainCaptchaResponseMenuPanel, new GridBagConstraints(
                1,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,5,0,5),
                0,0
        ));

        CaptchaMainCaptchaPanel.add(CaptchaMainCaptchaRequestAreaScrollPane, new GridBagConstraints(
                0,1,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));
        CaptchaMainCaptchaPanel.add(CaptchaMainCaptchaResponseAreaScrollPane, new GridBagConstraints(
                1,1,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        JComponent CaptchaMainCaptchaImageMenuPanel = new JPanel(new GridBagLayout());
        CaptchaMainCaptchaImageMenuPanel.setName("CaptchaMainCaptchaImageMenuPanel");
        CaptchaMainCaptchaImageMenuPanel.setBackground(Color.WHITE);
        CaptchaMainCaptchaImageMenuPanel.setPreferredSize(new Dimension(0,35));

        JLabel CaptchaMainCaptchaImageMenuImageLabel = new JLabel("Image: ");
        CaptchaMainCaptchaImageMenuImageLabel.setName("CaptchaMainCaptchaImageMenuImageLabel");
        DiyJButton CaptchaMainCaptchaImageMenuIdentifyButton = new DiyJButton("Identify");
        CaptchaMainCaptchaImageMenuIdentifyButton.setPreferredSize(new Dimension(120,0));
        CaptchaMainCaptchaImageMenuIdentifyButton.setName("CaptchaMainCaptchaImageMenuIdentifyButton");

        JComponent CaptchaMainCaptchaImageMenuResultPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,20,3));
        CaptchaMainCaptchaImageMenuResultPanel.setName("CaptchaMainCaptchaImageMenuResultPanel");

        CaptchaMainCaptchaImageMenuPanel.add(CaptchaMainCaptchaImageMenuImageLabel, new GridBagConstraints(
                0,0,
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,5,0,0),
                0,0
        ));
        CaptchaMainCaptchaImageMenuPanel.add(CaptchaMainCaptchaImageMenuResultPanel,new GridBagConstraints(
                1,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        CaptchaMainCaptchaImageMenuPanel.add(CaptchaMainCaptchaImageMenuIdentifyButton,new GridBagConstraints(
                2,0,
                1,1,
                0,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,5),
                0,0
        ));

        DiyJTextAreaScrollPane CaptchaMainCaptchaImageAreaScrollPane = new DiyJTextAreaScrollPane("CaptchaMainCaptchaImageArea");

        CaptchaMainCaptchaPanel.add(CaptchaMainCaptchaImageMenuPanel,new GridBagConstraints(
                0,2,
                2,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,5),
                0,0
        ));

        CaptchaMainCaptchaPanel.add(CaptchaMainCaptchaImageAreaScrollPane,new GridBagConstraints(
                0,3,
                2,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,5),
                0,0
        ));

        return CaptchaMainCaptchaPanel;
    }


}
