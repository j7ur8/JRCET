package jrcet.frame.tools.Password;

import jrcet.diycomponents.DiyJComponent;
import jrcet.diycomponents.DiyJTabLabel;
import jrcet.frame.tools.Password.Generate.GenerateComponent;

import javax.swing.*;
import java.awt.*;

public class PasswordComponent extends DiyJComponent {

    public final static JComponent GenerateComponentPanel = GenerateComponentPanel();

    public static JComponent PasswordComponentPanel = null;

    @Override
    public JComponent main() {
        PasswordComponentPanel = new JPanel(new GridBagLayout());
        PasswordComponentPanel.setName("PasswordComponentPanel");
        PasswordComponentPanel.setBackground(Color.WHITE);

        PasswordComponentPanel.add(PasswordMenuTabPanel(),new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        PasswordComponentPanel.add(GenerateComponentPanel,new GridBagConstraints(
                0,1,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));
        return PasswordComponentPanel;
    }
    
    public JComponent PasswordMenuTabPanel(){
        JPanel PasswordMenuTabPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
        PasswordMenuTabPanel.setName("PasswordMenuTabPanel");

        DiyJTabLabel PasswordMenuGenerateLabel = new DiyJTabLabel("Generate",true);
        PasswordMenuGenerateLabel.setName("PasswordMenuGenerateLabel");
        PasswordMenuGenerateLabel.setPanel(GenerateComponentPanel);
        PasswordMenuTabPanel.add(PasswordMenuGenerateLabel);

        for(Component label : PasswordMenuTabPanel.getComponents()){
            label.setFont(new Font("微软雅黑", Font.PLAIN,12));
            label.setPreferredSize(new Dimension(60,20));
        }

        return PasswordMenuTabPanel;
    }

    public static JComponent GenerateComponentPanel(){
        return new GenerateComponent().main();
    }
}
