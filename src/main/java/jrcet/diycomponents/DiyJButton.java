package jrcet.diycomponents;

import jrcet.frame.asset.Asset;
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
import java.util.Objects;

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
        String eButtonName = eButton.getText();
        JComponent rootPanel;
        JComponent tPanel;
        JComponent nPanel;
        DiyJLabel tLabel;
        switch (eButtonName) {
            case "Copy":
                writeRScript(eButton);
                break;
            case "Aes":
            case "Rsa":
            case "Base":
            case "Ascii":
            case "Unicode":
                nPanel = getNewIntruderModulePanel(eButtonName);
                tPanel = Helper.getComponent(IntruderComponentPanel, "IntruderMainPanel");assert tPanel!=null;
                IntruderModuleComponentList.add(nPanel);
                tLabel = (DiyJLabel) Helper.getComponent(IntruderComponentPanel, "IntruderMainControlShowPanel"); assert tLabel!=null;
                String tLabelText = tLabel.getText();
                if(Objects.equals(tLabelText, "")){
                    tLabel.setText(tLabelText+eButtonName);
                }else{
                    tLabel.setText(tLabelText+"->"+eButtonName);
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
            case "Save":
                rootPanel = (JComponent) eButton.getParent().getParent();
                JTextField tTextField = (JTextField) Helper.getComponent(rootPanel, "AssetMainControlUrlField");assert tTextField!=null;
                Asset.saveAsset(tTextField.getText());
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
        }
        return null;
    }


}
