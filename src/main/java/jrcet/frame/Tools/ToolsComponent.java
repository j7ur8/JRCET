package jrcet.frame.Tools;

import diycomponents.DiyJComponent;
import jrcet.frame.Tools.Captcha.CaptchaComponent;
import jrcet.frame.Tools.Password.PasswordComponent;

import javax.swing.*;
import java.awt.*;

public class ToolsComponent extends DiyJComponent {

    public static JComponent ToolsComponentPanel = null;

    @Override
    public JComponent component(){
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

        ToolsTabbedPane.add("Captcha", new CaptchaComponent().component());
        ToolsTabbedPane.add("Password", new PasswordComponent().component());

        return ToolsTabbedPane;
    }


}