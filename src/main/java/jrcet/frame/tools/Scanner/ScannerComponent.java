package jrcet.frame.tools.Scanner;

import jrcet.diycomponents.DiyJComponent;
import jrcet.diycomponents.DiyJTabLabel;
import jrcet.frame.tools.Scanner.Fastjson.FastjsonComponent;

import javax.swing.*;
import java.awt.*;

public class ScannerComponent extends DiyJComponent {

    private final JComponent FastjsonComponentPanel = FastjsonComponentPanel();


    public static JComponent ScannerComponentPanel = null;

    public JComponent main(){

        ScannerComponentPanel = new JPanel(new GridBagLayout());
        ScannerComponentPanel.setName("ScannerComponentPanel");
        ScannerComponentPanel.setBackground(Color.WHITE);

        ScannerComponentPanel.add(ScannerMenuTabPanel(),new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        ScannerComponentPanel.add(FastjsonComponentPanel,new GridBagConstraints(
                0,1,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return ScannerComponentPanel;
    }

    public JComponent ScannerMenuTabPanel(){

        JPanel ScannerMenuPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        ScannerMenuPanel.setName("ScannerMenuPanel");

        //Fastjson
        DiyJTabLabel ScannerMenuAESLabel = new DiyJTabLabel("Fastjson", true);
        ScannerMenuAESLabel.setPanel(FastjsonComponentPanel);
        ScannerMenuAESLabel.setName("ScannerMenuFastjsonLabel");
        ScannerMenuPanel.add(ScannerMenuAESLabel);


        //设置 Tab 的按钮属性（高宽等）
        for(Component label : ScannerMenuPanel.getComponents()){
            label.setFont(new Font("微软雅黑", Font.PLAIN,12));
            label.setPreferredSize(new Dimension(50,20));
        }

        return ScannerMenuPanel;
    }


    public JComponent FastjsonComponentPanel(){
//        return new FastjsonComponent().main();
        return  new JPanel();
    }


}
