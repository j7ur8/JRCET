package jrcet.frame.asset;

import jrcet.diycomponents.DiyJButton;
import jrcet.diycomponents.DiyJComponent;
import jrcet.diycomponents.DiyJTextArea.ui.rsyntaxtextarea.RSyntaxTextArea;
import jrcet.frame.tools.Dencrypt.Unicode.Unicode;
import jrcet.help.Helper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Objects;

public class AssetComponent extends DiyJComponent {


    public static JComponent AssetComponentPanel = null;

    public JComponent main(){

        AssetComponentPanel = new JPanel(new GridBagLayout());
        AssetComponentPanel.setName("AssetComponentPanel");
        AssetComponentPanel.setBackground(Color.WHITE);

        AssetComponentPanel.add(AssetMainPanel(), new GridBagConstraints(
                0,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(10,0,0,0),
                0,0
        ));

        return AssetComponentPanel;
    }


    public JComponent AssetMainPanel(){

        JComponent AssetMainPanel = new JPanel(new GridBagLayout());
        AssetMainPanel.setName("AssetMainPanel");
        AssetMainPanel.setPreferredSize(new Dimension(0,0));
        AssetMainPanel.setBackground(Color.WHITE);

        AssetMainPanel.add(AssetMainQueryPanel(), new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        AssetMainPanel.add(AssetMainResultPanel(), new GridBagConstraints(
                0,1,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return AssetMainPanel;
    }

    public JComponent AssetMainQueryPanel(){
        JPanel AssetMainQueryPanel = new JPanel(new GridBagLayout());
        AssetMainQueryPanel.setPreferredSize(new Dimension(0,40));
        AssetMainQueryPanel.setName("AssetMainQueryPanel");
        AssetMainQueryPanel.setBackground(Color.WHITE);

        JTextField AssetMainQueryInputField = new JTextField();
        AssetMainQueryInputField.setName("AssetMainQueryInputField");
        AssetMainQueryInputField.setPreferredSize(new Dimension(0,0));
        AssetMainQueryInputField.addKeyListener(new AssetMainQueryInputKeyListener());

        AssetMainQueryPanel.add(Helper.blackPanel(Color.WHITE), new GridBagConstraints(
                0,0,
                1,1,
                0.2,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        AssetMainQueryPanel.add(AssetMainQueryInputField, new GridBagConstraints(
                1,0,
                1,1,
                0.6,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        AssetMainQueryPanel.add(AssetMainQueryMenuPanel(), new GridBagConstraints(
                2,0,
                1,1,
                0.2,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return AssetMainQueryPanel;
    }

    public JComponent AssetMainQueryMenuPanel(){
        JComponent AssetMainQueryMenuPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,0,5));
        AssetMainQueryMenuPanel.setBackground(Color.WHITE);
        AssetMainQueryMenuPanel.setName("AssetMainQueryMenuPanel");
        AssetMainQueryMenuPanel.setPreferredSize(new Dimension(0,0));

        DiyJButton AssetMainQueryMenuLastButton = new DiyJButton("Last");
        AssetMainQueryMenuLastButton.setName("AssetMainQueryMenuLastButton");
        AssetMainQueryMenuLastButton.setPreferredSize(new Dimension(80,30));

        DiyJButton AssetMainQueryMenuNextButton = new DiyJButton("Next");
        AssetMainQueryMenuNextButton.setName("AssetMainQueryMenuNextButton");
        AssetMainQueryMenuNextButton.setPreferredSize(new Dimension(80,30));

        DiyJButton AssetMainQueryMenuRefreshButton = new DiyJButton("Refresh");
        AssetMainQueryMenuRefreshButton.setName("AssetMainQueryMenuRefreshButton");
        AssetMainQueryMenuRefreshButton.setPreferredSize(new Dimension(80,30));
//        AssetMainQueryMenuRefreshButton.setMargin();
        AssetMainQueryMenuPanel.add(AssetMainQueryMenuLastButton);
        AssetMainQueryMenuPanel.add(AssetMainQueryMenuNextButton);
        AssetMainQueryMenuPanel.add(AssetMainQueryMenuRefreshButton);

        return AssetMainQueryMenuPanel;
    }

    public JComponent AssetMainResultPanel(){
        JPanel AssetMainResultPanel = new JPanel(new GridBagLayout());
        AssetMainResultPanel.setName("AssetMainResultPanel");
        AssetMainResultPanel.setPreferredSize(new Dimension(0,0));
        AssetMainResultPanel.setBackground(Color.WHITE);

        AssetMainResultPanel.add(Helper.blackPanel(Color.WHITE), new GridBagConstraints(
                0,0,
                1,1,
                0.15,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        AssetMainResultPanel.add(AssetMainResultUnitPanel(), new GridBagConstraints(
                1,0,
                1,1,
                0.7,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        AssetMainResultPanel.add(Helper.blackPanel(Color.WHITE), new GridBagConstraints(
                2,0,
                1,1,
                0.15,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return AssetMainResultPanel;
    }

    public JComponent AssetMainResultUnitPanel(){
        JPanel AssetMainResultUnitPanel = new JPanel(new GridBagLayout());
        AssetMainResultUnitPanel.setName("AssetMainResultUnitPanel");
        AssetMainResultUnitPanel.setBackground(Color.WHITE);
        AssetMainResultUnitPanel.setPreferredSize(new Dimension(0,0));

        Asset.initResultUnitPanel(AssetMainResultUnitPanel,Asset.searchFromAsset(0,12));
        
        return AssetMainResultUnitPanel;
    }


    static class AssetMainQueryInputKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e){
        }

        @Override
        public void keyPressed(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {

            if( (e.getModifiers()== InputEvent.CTRL_MASK || e.getModifiers() == InputEvent.META_MASK) && e.getKeyCode()==71){
                JTextField eField = (JTextField) e.getSource();
                String queryText = eField.getText();

                if(Objects.equals(queryText, "")){
                    return;
                }

                Asset.page=0;
                String[][] result = Asset.searchAsset(queryText,Asset.page,Asset.dataNumber);
                JComponent tPanel = Helper.getComponent(AssetComponentPanel,"AssetMainResultUnitPanel");assert tPanel!=null;
                tPanel.removeAll();
                Asset.initResultUnitPanel(tPanel, result);
                tPanel.updateUI();
                Asset.AssetMode="Search";
            }
        }
    }



}
