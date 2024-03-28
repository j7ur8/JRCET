package jrcet.frame.Dencrypt.Unicode;

import diycomponents.*;

import javax.swing.*;
import java.awt.*;


public class UnicodeComponent extends DiyJComponent {

    public static JComponent UnicodeComponentPanel = null;

    public static DiyJTextAreaScrollPane UnicodePlainArea = new DiyJTextAreaScrollPane("UnicodePlainArea");
    public static DiyJTextAreaScrollPane UnicodeCipherArea = new DiyJTextAreaScrollPane("UnicodeCipherArea");

    public JComponent component(){

        UnicodeComponentPanel = new JPanel(new GridBagLayout());
        UnicodeComponentPanel.setName("UnicodeComponentPanel");
        UnicodeComponentPanel.setBackground(Color.WHITE);


        UnicodeComponentPanel.add(UnicodeFunctionPanel(),new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));
        UnicodeComponentPanel.add(UnicodeAreaPanel(),new GridBagConstraints(
                0,1,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));


        return UnicodeComponentPanel;
    }

    public JComponent UnicodeFunctionPanel(){
        JPanel UnicodeFunctionPanel = new JPanel(new GridBagLayout());
        UnicodeFunctionPanel.setName("UnicodeFunctionPanel");
        UnicodeFunctionPanel.setPreferredSize(new Dimension(0,30));

        DiyJButton UnicodeFunctionDecryptButton = new DiyJButton("Decrypt");
        UnicodeFunctionDecryptButton.setName("UnicodeFunctionDecryptButton");
        UnicodeFunctionPanel.add(UnicodeFunctionDecryptButton,new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        DiyJButton UnicodeFunctionEncryptButton = new DiyJButton("Encrypt");
        UnicodeFunctionEncryptButton.setName("UnicodeFunctionEncryptButton");
        UnicodeFunctionPanel.add(UnicodeFunctionEncryptButton,new GridBagConstraints(
                1,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return UnicodeFunctionPanel;
    }

    public JComponent UnicodeAreaPanel(){
        DiyVariablePanel UnicodeAreaPanel = new DiyVariablePanel(
                UnicodePlainArea,"UnicodePlainArea",
                UnicodeCipherArea,"UnicodeCipherArea",
                DiyVariablePanel.View.HORIZONTAL
        );
        UnicodeAreaPanel.setName("UnicodeAreaPanel");
        UnicodeAreaPanel.setPreferredSize(new Dimension(0,0));
        return UnicodeAreaPanel;
    }

}
