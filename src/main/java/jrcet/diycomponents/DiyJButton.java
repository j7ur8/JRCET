package jrcet.diycomponents;

import burp.lib.Helper;
import jrcet.frame.tools.Solibrary.SoLibrary;
import jrcet.frame.tools.Solibrary.SoLibraryComponent;

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

import static jrcet.frame.tools.Solibrary.SoLibraryComponent.SoLibraryComponentPanel;

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


    private void SetDatabase(DiyJButton targetButton) {
        JPanel parentPanel = (JPanel) targetButton.getParent();
        JTextField DatabaseTextField = (JTextField) parentPanel.getComponent(1);
        if(Helper.isFile(DatabaseTextField.getText())){
            SoLibraryComponent.databasePath=DatabaseTextField.getText();
        }else{
            DatabaseTextField.setText("文件路径错误");
            SoLibraryComponent.databasePath="";
        }
    }

    private void Search(DiyJButton targetButton){
        JPanel parentPanel = (JPanel) targetButton.getParent();
        JTextField nameField = (JTextField) parentPanel.getComponent(4);
        JTextField cardField = (JTextField) parentPanel.getComponent(6);
        JTextField phoneField = (JTextField) parentPanel.getComponent(8);
        JComponent ePanel = Helper.getComponent(SoLibraryComponentPanel,"SoLibraryResultPanel");
        assert ePanel != null;
        ePanel.removeAll();
        SoLibrary.Search(nameField.getText(),cardField.getText(),phoneField.getText());
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
        String eButtonName = eButton.getText().replaceAll("<[a-z/]{1,6}>","");
        switch (eButtonName) {
            case "Copy":
                writeRScript(eButton);
                break;
            case "Search":
                Search(eButton);
                break;
            case "SetDatabase":
                SetDatabase(eButton);
                break;
        }
    }




}
