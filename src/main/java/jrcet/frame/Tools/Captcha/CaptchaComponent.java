package jrcet.frame.Tools.Captcha;

import jrcet.diycomponents.DiyJButton;
import jrcet.diycomponents.DiyJComponent;
import jrcet.diycomponents.DiyJTextArea.ui.rsyntaxtextarea.RSyntaxTextArea;
import jrcet.diycomponents.DiyJTextArea.ui.rsyntaxtextarea.SyntaxConstants;
import jrcet.diycomponents.DiyJTextArea.ui.rtextarea.RTextScrollPane;
import jrcet.frame.Setting.Setting;

import javax.swing.*;
import java.awt.*;

public class CaptchaComponent extends DiyJComponent {

    public static JComponent CaptchaComponentPanel = null;

    public JComponent main(){

        CaptchaComponentPanel = new JPanel(new GridBagLayout());
        CaptchaComponentPanel.setName("CaptchaComponentPanel");
        CaptchaComponentPanel.setBackground(Color.WHITE);

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
        RSyntaxTextArea CaptchaMainCaptchaRequestArea = new RSyntaxTextArea();
        CaptchaMainCaptchaRequestArea.setName("CaptchaMainCaptchaRequestArea");
        CaptchaMainCaptchaRequestArea.setCodeFoldingEnabled(true);
        CaptchaMainCaptchaRequestArea.setLineWrap(true);
        CaptchaMainCaptchaRequestArea.setText("#请输入请求数据包...");
        CaptchaMainCaptchaRequestArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE);

        RTextScrollPane CaptchaMainCaptchaRequestAreaScrollPane = new RTextScrollPane(CaptchaMainCaptchaRequestArea);
        CaptchaMainCaptchaRequestAreaScrollPane.setHorizontalScrollBarPolicy(RTextScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        CaptchaMainCaptchaRequestAreaScrollPane.setName("CaptchaMainCaptchaRequestAreaScrollPane");
        CaptchaMainCaptchaRequestAreaScrollPane.setPreferredSize(new Dimension(0,0));
        CaptchaMainCaptchaRequestAreaScrollPane.setBorder(BorderFactory.createMatteBorder(1,0,1,0, Setting.gray));

        //CaptchaMainCaptchaResponseMenuPanel
        JComponent CaptchaMainCaptchaResponseMenuPanel = new JPanel(new GridBagLayout());
        CaptchaMainCaptchaResponseMenuPanel.setPreferredSize(new Dimension(0,35));
        CaptchaMainCaptchaResponseMenuPanel.setName("CaptchaMainCaptchaResponseMenuPanel");
        CaptchaMainCaptchaResponseMenuPanel.setBackground(Color.WHITE);

        JLabel CaptchaMainCaptchaResponseMenuRuleLabel = new JLabel("Rule: ");
        CaptchaMainCaptchaResponseMenuRuleLabel.setName("CaptchaMainCaptchaResponseMenuRuleLabel");
        JTextField CaptchaMainCaptchaResponseMenuRuleFiled = new JTextField("");
        CaptchaMainCaptchaResponseMenuRuleFiled.setName("CaptchaMainCaptchaResponseMenuRuleFiled");

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


        //CaptchaMainCaptchaResponseAreaScrollPane
        RSyntaxTextArea CaptchaMainCaptchaResponseArea = new RSyntaxTextArea();
        CaptchaMainCaptchaResponseArea.setName("CaptchaMainCaptchaResponseArea");
        CaptchaMainCaptchaResponseArea.setCodeFoldingEnabled(true);
        CaptchaMainCaptchaResponseArea.setLineWrap(true);
        CaptchaMainCaptchaResponseArea.setText("#返回包数据...");
        CaptchaMainCaptchaResponseArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE);

        RTextScrollPane CaptchaMainCaptchaResponseAreaScrollPane = new RTextScrollPane(CaptchaMainCaptchaResponseArea);
        CaptchaMainCaptchaResponseAreaScrollPane.setHorizontalScrollBarPolicy(RTextScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        CaptchaMainCaptchaResponseAreaScrollPane.setName("CaptchaMainResponseAreaScrollPane");
        CaptchaMainCaptchaResponseAreaScrollPane.setPreferredSize(new Dimension(0,0));
        CaptchaMainCaptchaResponseAreaScrollPane.setBorder(BorderFactory.createMatteBorder(1,0,1,0, Setting.gray));

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


        JComponent CaptchaMainCaptchaImageMenuResultPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,2,2));
        CaptchaMainCaptchaImageMenuResultPanel.setName("CaptchaMainCaptchaImageMenuResultPanel");
//        CaptchaMainCaptchaImageMenuResultPanel.setBackground(Color.GRAY);

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

        RSyntaxTextArea CaptchaMainCaptchaImageArea = new RSyntaxTextArea();
        CaptchaMainCaptchaImageArea.setName("CaptchaMainCaptchaImageArea");
        CaptchaMainCaptchaImageArea.setCodeFoldingEnabled(true);
        CaptchaMainCaptchaImageArea.setLineWrap(true);
        CaptchaMainCaptchaImageArea.setText("#图片信息");
        CaptchaMainCaptchaImageArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE);

        RTextScrollPane CaptchaMainCaptchaImageAreaScrollPane = new RTextScrollPane(CaptchaMainCaptchaImageArea);
        CaptchaMainCaptchaImageAreaScrollPane.setHorizontalScrollBarPolicy(RTextScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        CaptchaMainCaptchaImageAreaScrollPane.setName("CaptchaMainCaptchaImageAreaScrollPane");
        CaptchaMainCaptchaImageAreaScrollPane.setPreferredSize(new Dimension(0,0));
        CaptchaMainCaptchaImageAreaScrollPane.setBorder(BorderFactory.createMatteBorder(1,0,0,0, Setting.gray));

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
