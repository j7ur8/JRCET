package jrcet.frame.tools.Dominate;

import jrcet.diycomponents.DiyJComponent;
import jrcet.diycomponents.DiyJTabLabel;
import jrcet.frame.tools.Dominate.Domain.DomainComponent;

import javax.swing.*;
import java.awt.*;

public class DominateComponent extends DiyJComponent {

    public final JComponent DomainComponentPanel = DomainComponentPanel();
    public static JComponent DominateComponentPanel = null;


    @Override
    public JComponent main() {

        DominateComponentPanel = new JPanel(new GridBagLayout());
        DominateComponentPanel.setName("DominateComponentPanel");
        DominateComponentPanel.setBackground(Color.WHITE);

        DominateComponentPanel.add(DominateMenuTabPanel(),new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        DominateComponentPanel.add(DomainComponentPanel,new GridBagConstraints(
                0,1,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return DominateComponentPanel;
    }

    public JComponent DominateMenuTabPanel(){

        JComponent DominateMenuTabPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
        DominateMenuTabPanel.setName("DominateMenuTabPanel");

        DiyJTabLabel DominateMenuDomainLabel = new DiyJTabLabel("Domain",true);
        DominateMenuDomainLabel.setName("DominateMenuDomainLabel");
        DominateMenuDomainLabel.setPanel(DomainComponentPanel);
        DominateMenuTabPanel.add(DominateMenuDomainLabel);

        for(Component label : DominateMenuTabPanel.getComponents()){
            label.setFont(new Font("微软雅黑", Font.PLAIN,12));
            label.setPreferredSize(new Dimension(50,20));
        }

        return DominateMenuTabPanel;
    }

    public JComponent DomainComponentPanel(){
        return new DomainComponent().main();
    }

}
