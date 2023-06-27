package jrcet.frame.Tools;

import jrcet.diycomponents.DiyJComponent;
import jrcet.diycomponents.DiyJTabLabel;
import jrcet.frame.Setting.Setting;
import jrcet.frame.Tools.Captcha.CaptchaComponent;
import jrcet.frame.Tools.Dencrypt.DencryptComponent;
import jrcet.frame.Tools.HText.HTextComponent;
import jrcet.frame.Tools.Password.PasswordComponent;

import javax.swing.*;
import java.awt.*;

public class ToolsComponent extends DiyJComponent {

    private final JComponent RScriptComponentPanel = RScriptComponentPanel();
    private final JComponent DencryptComponentPanel = DencryptComponentPanel();

    private final JComponent HTextComponentPanel = HTextComponentPanel();

    private final JComponent CaptchaComponentPanel = CaptchaComponentPanel();

    private final JComponent PasswordComponentPanel = PasswordComponentPanel();
    public static JComponent ToolsComponentPanel = null;

    @Override
    public JComponent main(){
        ToolsComponentPanel = new JPanel(new GridBagLayout());
        ToolsComponentPanel.setName("ToolsComponentPanel");
        ToolsComponentPanel.setBackground(Color.WHITE);

        ToolsComponentPanel.add(ToolsMenuTabPanel(), new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        ToolsComponentPanel.add(ToolsShowPanel(),new GridBagConstraints(
                0,1,
                2,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return ToolsComponentPanel;
    }

    private JComponent ToolsMenuTabPanel(){

        JPanel ToolsMenuTabPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
        ToolsMenuTabPanel.setName("ToolsMenuTabPanel");

        DiyJTabLabel ToolsMenuDencryptLabel = new DiyJTabLabel("Dencrypt");
        ToolsMenuDencryptLabel.setName("ToolsMenuDencryptLabel");
        ToolsMenuDencryptLabel.setPanel(DencryptComponentPanel);
        ToolsMenuTabPanel.add(ToolsMenuDencryptLabel);

        DiyJTabLabel ToolsMenuRScriptLabel = new DiyJTabLabel("RScript");
        ToolsMenuRScriptLabel.setName("ToolsMenuRScriptLabel");
        ToolsMenuRScriptLabel.setPanel(RScriptComponentPanel);
        ToolsMenuTabPanel.add(ToolsMenuRScriptLabel);

        DiyJTabLabel ToolsMenuHTextLabel = new DiyJTabLabel("HText");
        ToolsMenuHTextLabel.setName("ToolsMenuHTextLabel");
        ToolsMenuHTextLabel.setPanel(HTextComponentPanel);
        ToolsMenuTabPanel.add(ToolsMenuHTextLabel);

        DiyJTabLabel ToolsMenuCaptchaLabel = new DiyJTabLabel("Captcha");
        ToolsMenuCaptchaLabel.setName("ToolsMenuCaptchaLabel");
        ToolsMenuCaptchaLabel.setPanel(CaptchaComponentPanel);
        ToolsMenuTabPanel.add(ToolsMenuCaptchaLabel);

        DiyJTabLabel ToolsMenuPasswordLabel = new DiyJTabLabel("Password", true);
        ToolsMenuPasswordLabel.setName("ToolsMenuPasswordLabel");
        ToolsMenuPasswordLabel.setPanel(PasswordComponentPanel);
        ToolsMenuTabPanel.add(ToolsMenuPasswordLabel);

        return ToolsMenuTabPanel;
    }

    private JComponent ToolsShowPanel(){

        switch (Setting.ToolsShowPanel){
            case "Dencrypt":
                return DencryptComponentPanel;
            case "RScript":
                return  RScriptComponentPanel;
            case "HText":
                return HTextComponentPanel;
            case "Captcha":
                return  CaptchaComponentPanel;
            case "Password":
                return PasswordComponentPanel;
        }
        return new JPanel();
    }

    private JComponent DencryptComponentPanel(){
        DiyJComponent DencryptComponentInstance = new DencryptComponent();
        JComponent DencryptComponentPanel = DencryptComponentInstance.main();
        return DencryptComponentPanel;
    }

    private JComponent RScriptComponentPanel(){
        return new JPanel();
    }


    private JComponent HTextComponentPanel(){
        DiyJComponent HTextComponentPanelInstance = new HTextComponent();
        JComponent HTextComponentPanel = HTextComponentPanelInstance.main();
        return HTextComponentPanel;
    }

    private JComponent CaptchaComponentPanel(){
        return  new CaptchaComponent().main();
    }

    private JComponent PasswordComponentPanel() {
        return new PasswordComponent().main();
    }
}