package jrcet.frame.tools.HText;

import jrcet.diycomponents.DiyJComponent;
import jrcet.diycomponents.DiyJLabel;
import jrcet.diycomponents.DiyJTabLabel;
import jrcet.frame.tools.HText.Alone.AloneComponent;
import jrcet.frame.tools.HText.Parsepy.ParsepyComponent;
import jrcet.frame.tools.HText.Sort.SortComponent;

import javax.swing.*;
import java.awt.*;

public class HTextComponent extends DiyJComponent {

    private final JComponent AloneComponentPanel = AloneComponentPanel();
    private final JComponent SortComponentPanel = SortComponentPanel();
    private final JComponent ParsepyComponentPanel = ParsepyComponentPanel();

    @Override
    public JComponent main() {
        JComponent HTextComponentPanel = new JPanel(new GridBagLayout());
        HTextComponentPanel.setName("HTextComponentPanel");

        HTextComponentPanel.add(HTextMenuTabPanel(),new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        HTextComponentPanel.add(AloneComponentPanel,new GridBagConstraints(
                0,1,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return HTextComponentPanel;
    }

    public JComponent HTextMenuTabPanel(){
        JPanel HTextMenuTabPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
        HTextMenuTabPanel.setName("HTextMenuTabPanel");

        DiyJTabLabel HTextMenuAloneLabel = new DiyJTabLabel("Alone",true);
        HTextMenuAloneLabel.setName("HTextMenuAloneLabel");
        HTextMenuAloneLabel.setPanel(AloneComponentPanel);
        HTextMenuTabPanel.add(HTextMenuAloneLabel);

        DiyJTabLabel HTextMenuSortLabel = new DiyJTabLabel("Sort");
        HTextMenuSortLabel.setName("HTextMenuSortLabel");
        HTextMenuSortLabel.setPanel(SortComponentPanel);
        HTextMenuTabPanel.add(HTextMenuSortLabel);

        DiyJTabLabel HTextMenuParsepyLabel = new DiyJTabLabel("Parsepy");
        HTextMenuParsepyLabel.setName("HTextMenuParsepyLabel");
        HTextMenuParsepyLabel.setPanel(ParsepyComponentPanel);
        HTextMenuTabPanel.add(HTextMenuParsepyLabel);

        for(Component label : HTextMenuTabPanel.getComponents()){
            label.setFont(new Font("微软雅黑", Font.PLAIN,12));
            label.setPreferredSize(new Dimension(50,20));
        }

        return HTextMenuTabPanel;
    }


    public JComponent AloneComponentPanel(){
        return new AloneComponent().main();
    }

    public JComponent SortComponentPanel(){
        return new SortComponent().main();
    }

    public JComponent ParsepyComponentPanel() {
        return new ParsepyComponent().main();
    }
}
