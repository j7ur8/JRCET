package jrcet.frame.HText;

import diycomponents.DiyJComponent;
import jrcet.frame.HText.Alone.AloneComponent;
import jrcet.frame.HText.Case.CaseComponent;
import jrcet.frame.HText.Format.FormatComponent;
import jrcet.frame.HText.IPUnit.IPUnitComponent;
import jrcet.frame.HText.Len.LenComponent;
import jrcet.frame.HText.Parsepy.ParsepyComponent;
import jrcet.frame.HText.Regex.RegexComponent;
import jrcet.frame.HText.Sort.SortComponent;

import javax.swing.*;
import java.awt.*;

public class HTextComponent extends DiyJComponent {


    public JComponent HTextComponentPanel = null;
    @Override
    public JComponent component() {
        HTextComponentPanel = new JPanel(new GridBagLayout());
        HTextComponentPanel.setName("HTextComponentPanel");

        HTextComponentPanel.add(HTextTabbedPanel(),new GridBagConstraints(
                0,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return HTextComponentPanel;
    }

    public JComponent HTextTabbedPanel(){
        JTabbedPane HTextTabbedPanel = new JTabbedPane(JTabbedPane.LEFT,JTabbedPane.SCROLL_TAB_LAYOUT);
        HTextTabbedPanel.setName("HTextTabbedPanel");
        HTextTabbedPanel.add("Alone",new AloneComponent().component());
        HTextTabbedPanel.add("Sort",new SortComponent().component());
        HTextTabbedPanel.add("Parsepy",new ParsepyComponent().component());
        HTextTabbedPanel.add("Case",new CaseComponent().component());
        HTextTabbedPanel.add("Format",new FormatComponent().component());
        HTextTabbedPanel.add("IPUnit",new IPUnitComponent().component());
        HTextTabbedPanel.add("Regex",new RegexComponent().component());
        HTextTabbedPanel.add("Len",new LenComponent().component());

        return HTextTabbedPanel;
    }

}
