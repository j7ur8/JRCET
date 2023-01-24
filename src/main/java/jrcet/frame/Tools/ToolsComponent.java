package jrcet.frame.Tools;

import jrcet.diycomponents.DiyJComponent;
import jrcet.diycomponents.DiyJTabLabel;
import jrcet.frame.Setting.Setting;
import jrcet.frame.Tools.Captcha.CaptchaComponent;
import jrcet.frame.Tools.Dencrypt.DencryptComponent;
import jrcet.frame.Tools.HText.HTextComponent;
import jrcet.frame.Tools.Password.PasswordComponent;
import jrcet.frame.Tools.RScript.RScriptComponent;

import javax.swing.*;
import java.awt.*;

public class ToolsComponent extends DiyJComponent {

    private final JComponent RScriptComponentPanel = RScriptComponentPanel();
    private final JComponent DencryptComponentPanel = DencryptComponentPanel();

    private final JComponent HTextComponentPanel = HTextComponentPanel();
    private final JComponent PasswordComponentPanel = PasswordComponentPanel();

    private final JComponent CaptchaComponentPanel = CaptchaComponentPanel();

    public static JComponent ToolsComponentPanel = null;

    @Override
    public JComponent main(){
        ToolsComponentPanel = new JPanel(new GridBagLayout());
        ToolsComponentPanel.setName("ToolsComponentPanel");
        ToolsComponentPanel.setBackground(Color.WHITE);

        ToolsComponentPanel.add(ToolsMenuTabPanel(), new GridBagConstraints(
                0,0,
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        ToolsComponentPanel.add(ToolsMenuTabBorderPanel(),new GridBagConstraints(
                1,0,
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

        JPanel ToolsMenuTabPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
        ToolsMenuTabPanel.setName("ToolsMenuTabPanel");
        ToolsMenuTabPanel.setOpaque(false);
        ToolsMenuTabPanel.setBackground(Color.WHITE);


        DiyJTabLabel ToolsMenuPasswordLabel = new DiyJTabLabel("Password");
        ToolsMenuPasswordLabel.setName("ToolsMenuPasswordLabel");
        ToolsMenuPasswordLabel.setPanel(PasswordComponentPanel);
        ToolsMenuTabPanel.add(ToolsMenuPasswordLabel);

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

        DiyJTabLabel ToolsMenuCaptchaLabel = new DiyJTabLabel("Captcha", true);
        ToolsMenuCaptchaLabel.setName("ToolsMenuCaptchaLabel");
        ToolsMenuCaptchaLabel.setPanel(CaptchaComponentPanel);
        ToolsMenuTabPanel.add(ToolsMenuCaptchaLabel);

        return ToolsMenuTabPanel;
    }

    /*
        补足toolsMenuPanel的下边框，无其他实际作用。
     */
    private JComponent ToolsMenuTabBorderPanel(){
        JPanel ToolsMenuTabBorderPanel = new JPanel();
        ToolsMenuTabBorderPanel.setName("ToolsMenuTabBorderPanel");
        ToolsMenuTabBorderPanel.setOpaque(false);
        ToolsMenuTabBorderPanel.setBackground(Color.WHITE);
        ToolsMenuTabBorderPanel.setBorder(BorderFactory.createMatteBorder(0,0,1,0,new Color(203,208,209)));
        return ToolsMenuTabBorderPanel;
    }

    private JComponent ToolsShowPanel(){
        JComponent showPanel = new JPanel();
        switch (Setting.ToolsShowPanel){
            case "Password":
                showPanel = PasswordComponentPanel;
                break;
            case "Dencrypt":
                showPanel = DencryptComponentPanel;
                break;
            case "RScript":
                showPanel = RScriptComponentPanel;
                break;
            case "HText":
                showPanel = HTextComponentPanel;
                break;
            case "Captcha":
                showPanel = CaptchaComponentPanel;
                break;
        }
        return showPanel;
    }

    private JComponent DencryptComponentPanel(){
        DiyJComponent DencryptComponentInstance = new DencryptComponent();
        JComponent DencryptComponentPanel = DencryptComponentInstance.main();
        return DencryptComponentPanel;
    }

    private JComponent RScriptComponentPanel(){
        DiyJComponent RScriptComponentInstance = new RScriptComponent();
        JComponent RScriptComponentPanel = RScriptComponentInstance.main();
        return RScriptComponentPanel;
    }


    private JComponent HTextComponentPanel(){
        DiyJComponent HTextComponentPanelInstance = new HTextComponent();
        JComponent HTextComponentPanel = HTextComponentPanelInstance.main();
        return HTextComponentPanel;
    }
    private JComponent PasswordComponentPanel(){
        return new PasswordComponent().main();
    }

    private JComponent CaptchaComponentPanel(){
        return  new CaptchaComponent().main();
    }
}