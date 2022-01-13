package jrcet.frame.tools;

import jrcet.diycomponents.DiyJComponent;
import jrcet.diycomponents.DiyJTabLabel;
import jrcet.frame.setting.Setting;
import jrcet.frame.tools.Dencrypt.DencryptComponent;
import jrcet.frame.tools.JSEncrypt.JSEncryptComponent;
import jrcet.frame.tools.RScript.RScriptComponent;
import jrcet.frame.tools.Solibrary.SoLibraryComponent;

import javax.swing.*;
import java.awt.*;

public class ToolsComponent extends DiyJComponent {

    private final JComponent RScript = RScript();
    private final JComponent JSEncrypt = JSEncrypt();
    private final JComponent SoLibrary = SoLibrary();
    private final JComponent Dencrypt = Dencrypt();
    public ToolsComponent(){}

    @Override
    public JComponent main(){
        JPanel toolsPanel = new JPanel(new GridBagLayout());
        toolsPanel.setOpaque(true);
        toolsPanel.setBackground(Color.WHITE);

        GridBagConstraints toolsMenuPanelProperty = new GridBagConstraints(
                0,0,
                1,1,
                0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        );
        toolsPanel.add(toolsMenuPanel(), toolsMenuPanelProperty);

        GridBagConstraints toolsMenuBorderPanelProperty = new GridBagConstraints(
                1,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        );
        toolsPanel.add(toolsMenuBorderPanel(),toolsMenuBorderPanelProperty);

        GridBagConstraints defaultToolPanelProperty = new GridBagConstraints(
                0,1,
                2,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        );
        toolsPanel.add(RScript,defaultToolPanelProperty);

        return toolsPanel;
    }

    public static DiyJTabLabel toolsMenuRScriptLabel = new DiyJTabLabel("RScript",Setting.class1DefaultDiyJTabBorderColor,Setting.class1ClickedDiyJTabBorderColor,true);
    private JComponent toolsMenuPanel(){
        JPanel toolsMenuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
        toolsMenuPanel.setOpaque(false);
        toolsMenuPanel.setBackground(Color.WHITE);

        DiyJTabLabel toolsMenuDencryptLabel = new DiyJTabLabel("Dencrypt", Setting.class1DefaultDiyJTabBorderColor,Setting.class1ClickedDiyJTabBorderColor);
        toolsMenuDencryptLabel.setMapPanel(Dencrypt);
        toolsMenuPanel.add(toolsMenuDencryptLabel);

        toolsMenuRScriptLabel.setMapPanel(RScript);
        toolsMenuPanel.add(toolsMenuRScriptLabel);

        DiyJTabLabel toolsMenuJSEncryptLabel = new DiyJTabLabel("JSEncrypt",Setting.class1DefaultDiyJTabBorderColor,Setting.class1ClickedDiyJTabBorderColor);
        toolsMenuJSEncryptLabel.setMapPanel(JSEncrypt);
        toolsMenuPanel.add(toolsMenuJSEncryptLabel);

        DiyJTabLabel toolsMenuJSEncryptLabelSoLibraryLabel = new DiyJTabLabel("SoLibrary",Setting.class1DefaultDiyJTabBorderColor,Setting.class1ClickedDiyJTabBorderColor);
        toolsMenuJSEncryptLabelSoLibraryLabel.setMapPanel(SoLibrary);
        toolsMenuPanel.add(toolsMenuJSEncryptLabelSoLibraryLabel);

        return toolsMenuPanel;
    }

    private JComponent toolsMenuBorderPanel(){
        JPanel toolsMenuBorderPanel = new JPanel();
        toolsMenuBorderPanel.setOpaque(false);
        toolsMenuBorderPanel.setBackground(Color.WHITE);
        toolsMenuBorderPanel.setBorder(BorderFactory.createMatteBorder(0,0,1,0,new Color(203,208,209)));
        return toolsMenuBorderPanel;
    }

    private JComponent Dencrypt(){
        DiyJComponent DencryptInstance = new DencryptComponent();
        JComponent DecryptPanel = DencryptInstance.main();
        return DecryptPanel;
    }

    private JComponent RScript(){
        DiyJComponent RScriptPanelInstance = new RScriptComponent();
        JComponent RScriptPanel = RScriptPanelInstance.main();
        return RScriptPanel;
    }

    private JComponent JSEncrypt(){
        DiyJComponent JSEncryptInstance = new JSEncryptComponent();
        JComponent JSEncryptPanel = JSEncryptInstance.main();
        return JSEncryptPanel;
    }

    private JComponent SoLibrary(){
        DiyJComponent SoLibraryInstance = new SoLibraryComponent();
        JComponent SoLibraryPanel = SoLibraryInstance.main();
        return SoLibraryPanel;
    }

}