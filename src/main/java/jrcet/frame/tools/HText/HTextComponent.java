package jrcet.frame.tools.HText;

import jrcet.diycomponents.DiyJComponent;
import jrcet.diycomponents.DiyJTabLabel;
import jrcet.frame.tools.HText.Alone.AloneComponent;
import jrcet.frame.tools.HText.Case.CaseComponent;
import jrcet.frame.tools.HText.Format.FormatComponent;
import jrcet.frame.tools.HText.IPUnit.IPUnitComponent;
import jrcet.frame.tools.HText.Len.LenComponent;
import jrcet.frame.tools.HText.Parsepy.ParsepyComponent;
import jrcet.frame.tools.HText.Regex.RegexComponent;
import jrcet.frame.tools.HText.Sort.SortComponent;

import javax.swing.*;
import java.awt.*;

public class HTextComponent extends DiyJComponent {

    private final JComponent AloneComponentPanel = AloneComponentPanel();
    private final JComponent SortComponentPanel = SortComponentPanel();
    private final JComponent ParsepyComponentPanel = ParsepyComponentPanel();
    private final JComponent FormattingComponentPanel = FormattingComponentPanel();
    private final JComponent CaseComponentPanel = CaseComponentPanel();
    private final JComponent IPUnitComponentPanel = IPUnitComponentPanel();
    private final JComponent RegexComponentPanel = RegexComponentPanel();
    private final JComponent LenComponentPanel = LenComponentPanel();
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

        HTextComponentPanel.add(LenComponentPanel,new GridBagConstraints(
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

        DiyJTabLabel HTextMenuAloneLabel = new DiyJTabLabel("Alone");
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

        DiyJTabLabel HTextMenuCaseLabel = new DiyJTabLabel("Case");
        HTextMenuCaseLabel.setName("HTextMenuSortLabel");
        HTextMenuCaseLabel.setPanel(CaseComponentPanel);
        HTextMenuTabPanel.add(HTextMenuCaseLabel);

        DiyJTabLabel HTextMenuFormattingLabel = new DiyJTabLabel("Format");
        HTextMenuFormattingLabel.setName("HTextMenuFormattingLabel");
        HTextMenuFormattingLabel.setPanel(FormattingComponentPanel);
        HTextMenuTabPanel.add(HTextMenuFormattingLabel);

        DiyJTabLabel HTextMenuIPUnitLabel = new DiyJTabLabel("IPUnit");
        HTextMenuIPUnitLabel.setName("HTextMenuIPUnitLabel");
        HTextMenuIPUnitLabel.setPanel(IPUnitComponentPanel);
        HTextMenuTabPanel.add(HTextMenuIPUnitLabel);

        DiyJTabLabel HTextMenuRegexLabel = new DiyJTabLabel("Regex");
        HTextMenuRegexLabel.setName("HTextMenuRegexLabel");
        HTextMenuRegexLabel.setPanel(RegexComponentPanel);
        HTextMenuTabPanel.add(HTextMenuRegexLabel);

        DiyJTabLabel HTextMenuLenLabel = new DiyJTabLabel("Len", true);
        HTextMenuLenLabel.setName("IPUnitMenuRegexLabel");
        HTextMenuLenLabel.setPanel(LenComponentPanel);
        HTextMenuTabPanel.add(HTextMenuLenLabel);

        for(Component label : HTextMenuTabPanel.getComponents()){
            label.setFont(new Font("????????????", Font.PLAIN,12));
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

    public JComponent FormattingComponentPanel() {
        return new FormatComponent().main();
    }

    public JComponent CaseComponentPanel() { return new CaseComponent().main(); }

    public JComponent IPUnitComponentPanel() { return new IPUnitComponent().main(); }

    public JComponent RegexComponentPanel() { return new RegexComponent().main(); }

    public JComponent LenComponentPanel() { return new LenComponent().main(); }
}
