package jrcet.frame.tools;

import jrcet.diycomponents.DiyJComponent;
import jrcet.diycomponents.DiyJTabLabel;
import jrcet.frame.tools.Dencrypt.DencryptComponent;
import jrcet.frame.tools.Dominate.DominateComponent;
import jrcet.frame.tools.HText.HTextComponent;
import jrcet.frame.tools.Intruder.IntruderComponent;
import jrcet.frame.tools.Password.PasswordComponent;
import jrcet.frame.tools.RScript.RScriptComponent;
import jrcet.frame.tools.Scanner.ScannerComponent;

import javax.swing.*;
import java.awt.*;

public class ToolsComponent extends DiyJComponent {

    private final JComponent RScriptComponentPanel = RScriptComponentPanel();
    private final JComponent DencryptComponentPanel = DencryptComponentPanel();

    private final JComponent ScannerComponentPanel = ScannerComponentPanel();
    private final JComponent IntruderComponentPanel = IntruderComponentPanel();
    private final JComponent HTextComponentPanel = HTextComponentPanel();
    private final JComponent DominateComponentPanel = DominateComponentPanel();
    private final JComponent PasswordComponentPanel = PasswordComponentPanel();

    public static JComponent ToolsComponentPanel = null;

    @Override
    public JComponent main(){
        ToolsComponentPanel = new JPanel(new GridBagLayout());
        ToolsComponentPanel.setName("ToolsComponentPanel");
        ToolsComponentPanel.setOpaque(true);
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

        ToolsComponentPanel.add(ScannerComponentPanel,new GridBagConstraints(
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

        DiyJTabLabel ToolsMenuIntruderLabel = new DiyJTabLabel("Intruder");
        ToolsMenuIntruderLabel.setName("ToolsMenuIntruderLabel");
        ToolsMenuIntruderLabel.setPanel(IntruderComponentPanel);
        ToolsMenuTabPanel.add(ToolsMenuIntruderLabel);

        DiyJTabLabel ToolsMenuScannerLabel = new DiyJTabLabel("Scanner", true);
        ToolsMenuScannerLabel.setName("ToolsMenuScannerLabel");
        ToolsMenuScannerLabel.setPanel(ScannerComponentPanel);
        ToolsMenuTabPanel.add(ToolsMenuScannerLabel);

        DiyJTabLabel ToolsMenuDominateLabel = new DiyJTabLabel("Dominate");
        ToolsMenuDominateLabel.setName("ToolsMenuDominateLabel");
        ToolsMenuDominateLabel.setPanel(DominateComponentPanel);
        ToolsMenuTabPanel.add(ToolsMenuDominateLabel);

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


    private JComponent IntruderComponentPanel(){
        DiyJComponent IntruderComponentInstance = new IntruderComponent();
        JComponent IntruderComponentPanel = IntruderComponentInstance.main();
        return IntruderComponentPanel;
    }

    private JComponent HTextComponentPanel(){
        DiyJComponent HTextComponentPanelInstance = new HTextComponent();
        JComponent HTextComponentPanel = HTextComponentPanelInstance.main();
        return HTextComponentPanel;
    }

    private JComponent DominateComponentPanel(){
        return  new DominateComponent().main();
    }

    private JComponent PasswordComponentPanel(){
        return new PasswordComponent().main();
    }

    private JComponent ScannerComponentPanel() { return new ScannerComponent().main(); }
}