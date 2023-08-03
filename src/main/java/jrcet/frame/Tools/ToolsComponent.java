package jrcet.frame.Tools;

import jrcet.diycomponents.DiyJComponent;
import jrcet.frame.Tools.Captcha.CaptchaComponent;
import jrcet.frame.Dencrypt.DencryptComponent;
import jrcet.frame.HText.HTextComponent;
import jrcet.frame.Tools.Password.PasswordComponent;

import javax.swing.*;
import java.awt.*;

public class ToolsComponent extends DiyJComponent {

    public static JComponent ToolsComponentPanel = null;

    @Override
    public JComponent main(){
        ToolsComponentPanel = new JPanel(new GridBagLayout());
        ToolsComponentPanel.setName("ToolsComponentPanel");
        ToolsComponentPanel.setBackground(Color.WHITE);

        ToolsComponentPanel.add(ToolsTabbedPane(), new GridBagConstraints(
                0,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,5,0),
                0,0
        ));

        return ToolsComponentPanel;
    }

    private JComponent ToolsTabbedPane(){

        JTabbedPane ToolsTabbedPane = new JTabbedPane(JTabbedPane.LEFT,JTabbedPane.SCROLL_TAB_LAYOUT);
        ToolsTabbedPane.setName("ToolsTabbedPane");

        ToolsTabbedPane.add("RScript", new JPanel());

        ToolsTabbedPane.add("Captcha", new CaptchaComponent().main());
        ToolsTabbedPane.add("Password", new PasswordComponent().main());

        ToolsTabbedPane.setSelectedIndex(1);
        return ToolsTabbedPane;
    }


}