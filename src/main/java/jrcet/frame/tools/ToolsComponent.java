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

    private final JComponent RScriptComponentPanel = RScriptComponentPanel();
    private final JComponent JSEncryptComponentPanel = JSEncryptComponentPanel();
    private final JComponent SoLibraryComponentPanel = SoLibraryComponentPanel();
    private final JComponent DencryptComponent = DencryptComponent();

    /*
        toolsMenuPanel和toolsMenuBorderPanel会先被调用。
        必须其调用的Rscript、..、Dencrypt的后面。
     */

    @Override
    public JComponent main(){
        JPanel ToolsComponentPanel = new JPanel(new GridBagLayout());
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

        ToolsComponentPanel.add(DencryptComponent,new GridBagConstraints(
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

    /*
        RScript需要接受burp传入的ihttp数据，需要设置为全局变量。

        return toolsMenuPanel
    */

    public static DiyJTabLabel ToolsMenuRScriptLabel ;

    private JComponent ToolsMenuTabPanel(){
        JPanel ToolsMenuTabPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
        ToolsMenuTabPanel.setName("ToolsMenuTabPanel");
        ToolsMenuTabPanel.setOpaque(false);
        ToolsMenuTabPanel.setBackground(Color.WHITE);

        DiyJTabLabel ToolsMenuDencryptLabel = new DiyJTabLabel("Dencrypt", Setting.class1DefaultDiyJTabBorderColor,Setting.class1ClickedDiyJTabBorderColor,true);
        ToolsMenuDencryptLabel.setName("ToolsMenuDencryptLabel");
        ToolsMenuDencryptLabel.setPanel(DencryptComponent);
        ToolsMenuTabPanel.add(ToolsMenuDencryptLabel);

        ToolsMenuRScriptLabel = new DiyJTabLabel("RScript",Setting.class1DefaultDiyJTabBorderColor,Setting.class1ClickedDiyJTabBorderColor);
        ToolsMenuRScriptLabel.setName("ToolsMenuRScriptLabel");
        ToolsMenuRScriptLabel.setPanel(RScriptComponentPanel);
        ToolsMenuTabPanel.add(ToolsMenuRScriptLabel);

        DiyJTabLabel ToolsMenuJSEncryptLabel = new DiyJTabLabel("JSEncrypt",Setting.class1DefaultDiyJTabBorderColor,Setting.class1ClickedDiyJTabBorderColor);
        ToolsMenuJSEncryptLabel.setName("ToolsMenuJSEncryptLabel");
        ToolsMenuJSEncryptLabel.setPanel(JSEncryptComponentPanel);
        ToolsMenuTabPanel.add(ToolsMenuJSEncryptLabel);

        DiyJTabLabel ToolsMenuSOLibraryLabel = new DiyJTabLabel("SOLibrary",Setting.class1DefaultDiyJTabBorderColor,Setting.class1ClickedDiyJTabBorderColor);
        ToolsMenuSOLibraryLabel.setName("ToolsMenuSOLibraryLabel");
        ToolsMenuSOLibraryLabel.setPanel(SoLibraryComponentPanel);
        ToolsMenuTabPanel.add(ToolsMenuSOLibraryLabel);

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


    private JComponent DencryptComponent(){
        DiyJComponent DencryptComponentInstance = new DencryptComponent();
        JComponent DencryptComponentPanel = DencryptComponentInstance.main();
        return DencryptComponentPanel;
    }

    private JComponent RScriptComponentPanel(){
        DiyJComponent RScriptComponentInstance = new RScriptComponent();
        JComponent RScriptComponentPanel = RScriptComponentInstance.main();
        return RScriptComponentPanel;
    }

    private JComponent JSEncryptComponentPanel(){
        DiyJComponent JSEncryptComponentInstance = new JSEncryptComponent();
        JComponent JSEncryptComponentPanel = JSEncryptComponentInstance.main();
        return JSEncryptComponentPanel;
    }

    private JComponent SoLibraryComponentPanel(){
        DiyJComponent SoLibraryComponentInstance = new SoLibraryComponent();
        JComponent SoLibraryComponentPanel = SoLibraryComponentInstance.main();
        return SoLibraryComponentPanel;
    }

}