package jrcet.diycomponents;

import jrcet.frame.asset.Asset;
import jrcet.frame.asset.AssetComponent;
import jrcet.frame.tools.Intruder.IntruderComponent;
import jrcet.help.Helper;


import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static jrcet.frame.asset.AAssetComponent.AAssetComponentPanel;
import static jrcet.frame.asset.AssetComponent.AssetComponentPanel;
import static jrcet.frame.tools.Intruder.IntruderComponent.IntruderComponentPanel;
import static jrcet.frame.tools.Intruder.IntruderComponent.IntruderModuleComponentList;

public class DiyJButton extends JButton implements MouseListener, ClipboardOwner, ActionListener {

    public DiyJButton(String text) {
        setText(text);
        setOpaque(true);
        setFocusPainted(false);
        addMouseListener(this);
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(120,30));
        addActionListener(this);
        setFont(new Font("微软雅黑", Font.PLAIN,14));
    }




    private void writeRScript(DiyJButton targetButton){
        JPanel targetPanel = (JPanel) targetButton.getParent().getParent();
        JScrollPane targetJScrollPane = (JScrollPane) targetPanel.getComponent(1);
        JTextArea targetJTextArea = (JTextArea) targetJScrollPane.getViewport().getComponent(0);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(targetJTextArea.getText()), this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {


    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void lostOwnership(Clipboard clipboard, Transferable contents) {

    }

    @Override
    public void actionPerformed(ActionEvent e){
        DiyJButton eButton = (DiyJButton) e.getSource();
        String eButtonText = eButton.getText();
        String eButtonName = eButton.getName();
        JComponent rootPanel;
        JComponent tPanel;
        JComponent nPanel;
        JTextField tField = null;
        JLabel tLabel;
        String text;
        String[][] result;
        switch (eButtonName){
            case "NAssetMainHistoryPortButton":
                tField = (JTextField) Helper.getComponent(AAssetComponentPanel, "NAssetMainAddPortField"); assert tField != null;
                tField.setText(eButtonText);
                break;
            case "NAssetMainHistoryServiceButton":
                tField = (JTextField) Helper.getComponent(AAssetComponentPanel, "NAssetMainAddServiceField"); assert tField != null;
                tField.setText(eButtonText);
                break;
            case "NAssetMainHistoryProjectButton":
                tField = (JTextField) Helper.getComponent(AAssetComponentPanel, "NAssetMainAddProjectField"); assert tField != null;
                tField.setText(eButtonText);
                break;
            case "NAssetMainHistorySourceButton":
                tField = (JTextField) Helper.getComponent(AAssetComponentPanel, "NAssetMainAddSourceField"); assert tField != null;
                tField.setText(eButtonText);
                break;
            case "AssetMainQueryMenuRefreshButton":
                tPanel =  Helper.getComponent(AssetComponentPanel, "AssetMainResultUnitPanel"); assert tPanel!=null;
                tPanel.removeAll();
                Asset.initResultUnitPanel(tPanel, Asset.searchFromAsset(0,Asset.dataNumber));
                tPanel.updateUI();
                Asset.AssetMode="Global";
                Asset.page=0;
                break;
            case "AssetMainQueryMenuLastButton":
                if(Asset.page>0){
                    Asset.page-=1;
                }else{
                    break;
                }

                if(Objects.equals(Asset.AssetMode, "Search")){
                    tField = (JTextField) Helper.getComponent(AssetComponentPanel, "AssetMainQueryInputField"); assert tField!=null;
                    text = tField.getText();
                    if(Objects.equals(text, "")){
                        break;
                    }
                    result = Asset.searchAsset(text,Asset.page,Asset.dataNumber);
                }else{
                    result = Asset.searchFromAsset(Asset.page,Asset.dataNumber);
                }

                tPanel = Helper.getComponent(AssetComponentPanel,"AssetMainResultUnitPanel");assert tPanel!=null;
                tPanel.removeAll();
                Asset.initResultUnitPanel(tPanel, result);
                tPanel.updateUI();
                break;
            case "AssetMainQueryMenuNextButton":
                Asset.page++;

                if(Objects.equals(Asset.AssetMode, "Search")){
                    tField = (JTextField) Helper.getComponent(AssetComponentPanel, "AssetMainQueryInputField"); assert tField!=null;
                    text = tField.getText();
                    if(Objects.equals(text, "")){
                        Asset.page--;
                        break;
                    }
                    result = Asset.searchAsset(text,Asset.page,Asset.dataNumber);
                }else{
                    result = Asset.searchFromAsset(Asset.page,Asset.dataNumber);
                }

                if(Arrays.deepEquals(result, new String[Asset.dataNumber][8])){
                    Asset.page--;
                    break;
                }

                tPanel = Helper.getComponent(AssetComponentPanel,"AssetMainResultUnitPanel");assert tPanel!=null;
                tPanel.removeAll();
                Asset.initResultUnitPanel(tPanel, result);
                tPanel.updateUI();
                break;
            case "AssetMainResultUpdateButton":
                JComponent unitPanel = (JComponent) eButton.getParent();
                Asset.updateUniteAsset(unitPanel);
                break;
        }

        switch (eButtonText) {
            case "Add Asset":
                tLabel = (JLabel) Helper.getComponent(AAssetComponentPanel, "NAssetMainAddResultLabel"); assert tLabel!=null;
                tLabel.setText(Asset.addAsset());
//                System.out.println(test.addAsset());
                tPanel =  Helper.getComponent(AAssetComponentPanel, "AAssetComponentPanel"); assert tPanel !=null;
                tPanel.remove(tPanel.getComponentCount()-1);
                tPanel.add(Asset.NAssetMainHistoryPanel(), new GridBagConstraints(
                        0,1,
                        1,1,
                        1,1,
                        GridBagConstraints.CENTER,
                        GridBagConstraints.BOTH,
                        new Insets(0,30,30,30),
                        0,0
                ));
                tPanel.updateUI();
                JFrame AAssetFrame = (JFrame) SwingUtilities.getWindowAncestor(AAssetComponentPanel);

                updateAfterAdd();

                AAssetFrame.dispose();

                break;
            case "Add Asset Without Exit":
                tLabel = (JLabel) Helper.getComponent(AAssetComponentPanel, "NAssetMainAddResultLabel"); assert tLabel!=null;
                tLabel.setText(Asset.addAsset());
//                System.out.println(test.addAsset());
                tPanel =  Helper.getComponent(AAssetComponentPanel, "AAssetComponentPanel"); assert tPanel !=null;
                tPanel.remove(tPanel.getComponentCount()-1);
                tPanel.add(Asset.NAssetMainHistoryPanel(), new GridBagConstraints(
                        0,1,
                        1,1,
                        1,1,
                        GridBagConstraints.CENTER,
                        GridBagConstraints.BOTH,
                        new Insets(0,30,30,30),
                        0,0
                ));
                tPanel.updateUI();
                updateAfterAdd();
                break;
            case "Copy":
                writeRScript(eButton);
                break;
            case "Aes":
            case "Des":
            case "Md5":
            case "Rsa":
            case "Base":
            case "Ascii":
            case "Unicode":
                nPanel = getNewIntruderModulePanel(eButtonText);
                tPanel = Helper.getComponent(IntruderComponentPanel, "IntruderMainPanel");assert tPanel!=null;
                IntruderModuleComponentList.add(nPanel);
                tLabel = (DiyJLabel) Helper.getComponent(IntruderComponentPanel, "IntruderMainControlShowPanel"); assert tLabel!=null;
                String tLabelText = tLabel.getText();
                if(Objects.equals(tLabelText, "")){
                    tLabel.setText(tLabelText+eButtonText);
                }else{
                    tLabel.setText(tLabelText+"->"+eButtonText);
                }
                tPanel.remove(tPanel.getComponents().length-1);
                tPanel.add(nPanel, new GridBagConstraints(
                        0,tPanel.getComponentCount(),
                        1,1,
                        1,0,
                        GridBagConstraints.CENTER,
                        GridBagConstraints.BOTH,
                        new Insets(0,100,0,100),
                        0,0
                ));
                tPanel.add(Helper.blackPanel(),new GridBagConstraints(
                        0,tPanel.getComponentCount(),
                        1,1,
                        1,1,
                        GridBagConstraints.CENTER,
                        GridBagConstraints.BOTH,
                        new Insets(0,0,0,0),
                        0,0
                ));
                tPanel.updateUI();
                break;
            case "Clear All":
                tPanel = Helper.getComponent(IntruderComponentPanel, "IntruderMainPanel");assert tPanel!=null;
                IntruderModuleComponentList = new ArrayList<>();
                tLabel = (DiyJLabel) Helper.getComponent(IntruderComponentPanel, "IntruderMainControlShowPanel"); assert tLabel!=null;
                tLabel.setText("");
                while (tPanel.getComponentCount()!=1){
                    tPanel.remove(1);
                }
                tPanel.add(Helper.blackPanel(),new GridBagConstraints(
                        0,tPanel.getComponentCount(),
                        1,1,
                        1,1,
                        GridBagConstraints.CENTER,
                        GridBagConstraints.BOTH,
                        new Insets(0,0,0,0),
                        0,0
                ));
                tPanel.updateUI();
                break;

        }
    }

    private void updateAfterAdd() {
        JTextField tField;
        String text;
        String[][] result;
        JComponent tPanel;
        if(Objects.equals(Asset.AssetMode, "Search")){
            tField = (JTextField) Helper.getComponent(AssetComponentPanel,"AssetMainQueryInputField");assert tField!=null;
            text = tField.getText();
            if(Objects.equals(text, "")){
                return;
            }

            result = Asset.searchAsset(text,Asset.page,Asset.dataNumber);
            tPanel = Helper.getComponent(AssetComponentPanel,"AssetMainResultUnitPanel");assert tPanel!=null;
            tPanel.removeAll();
            Asset.initResultUnitPanel(tPanel, result);
            tPanel.updateUI();
        }else{
            tPanel =  Helper.getComponent(AssetComponentPanel, "AssetMainResultUnitPanel"); assert tPanel!=null;
            tPanel.removeAll();
            Asset.initResultUnitPanel(tPanel, Asset.searchFromAsset(Asset.page,Asset.dataNumber));
            tPanel.updateUI();
        }
    }

    public JComponent getNewIntruderModulePanel(String moduleName){
        switch (moduleName){
            case "Aes":
                return new IntruderComponent().IntruderMainAesPanel();
            case "Base":
                return new IntruderComponent().IntruderMainBasePanel();
            case "Rsa":
                return new IntruderComponent().IntruderMainRsaPanel();
            case "Ascii":
                return new IntruderComponent().IntruderMainAsciiPanel();
            case "Unicode":
                return new IntruderComponent().IntruderMainUnicodePanel();
            case "Md5":
                JComponent acc = new JPanel();
                acc.setName("IntruderMainMd5Panel");
                return acc;
            case "Des":
                JComponent acc1 = new JPanel();
                acc1.setName("IntruderMainDesPanel");
                return acc1;
        }
        return null;
    }

}
