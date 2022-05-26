package jrcet.frame.asset;

import jrcet.diycomponents.DiyJComponent;
import jrcet.diycomponents.DiyJTextArea.ui.rsyntaxtextarea.RSyntaxTextArea;
import jrcet.diycomponents.DiyJTextArea.ui.rsyntaxtextarea.SyntaxConstants;
import jrcet.diycomponents.DiyJTextArea.ui.rtextarea.RTextScrollPane;
import jrcet.frame.tools.HText.Alone.AloneComponent;
import jrcet.lib.Helper;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

public class AssetComponent extends DiyJComponent {

    public static JComponent AssetComponentPanel = null;

    @Override
    public JComponent main() {

        AssetComponentPanel = new JPanel(new GridBagLayout());
        AssetComponentPanel.setBackground(Color.WHITE);
        AssetComponentPanel.setName("AssetComponentPanel");

        AssetComponentPanel.add(AssetMainPanel(),new GridBagConstraints(
                0,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return AssetComponentPanel;
    }

    public JComponent AssetMainPanel(){
        JPanel AssetMainPanel = new JPanel(new GridBagLayout());
        AssetMainPanel.setName("AssetMainPanel");
        AssetMainPanel.setPreferredSize(new Dimension(0,0));

        AssetMainPanel.add(AssetMainShowPanel(),new GridBagConstraints(
                0,0,
                1,1,
                0.7,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        AssetMainPanel.add(AssetMainControlPanel(),new GridBagConstraints(
                1,0,
                1,1,
                0.30,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return AssetMainPanel;
    }

    public JComponent AssetMainShowPanel(){
        JComponent AssetMainShowPanel = new JPanel(new GridBagLayout());
        AssetMainShowPanel.setName("AssetMainShowPanel");
        AssetMainShowPanel.setBackground(Color.WHITE);

        JList<String> AssetMainShowDictionary1List = new JList<>(Asset.getDictionary(Asset.AssetRootPath));
        AssetMainShowDictionary1List.setName("AssetMainShowDictionary1List");
        AssetMainShowDictionary1List.setFont(new Font("微软雅黑",Font.PLAIN, 15));
        AssetMainShowDictionary1List.setPreferredSize(new Dimension(0,0));
        AssetMainShowDictionary1List.addListSelectionListener(new AssetMainShowListSelectionListener());

        JList<String> AssetMainShowDictionary2List = new JList<>();
        AssetMainShowDictionary2List.setName("AssetMainShowDictionary2List");
        AssetMainShowDictionary2List.setFont(new Font("微软雅黑",Font.PLAIN, 15));
        AssetMainShowDictionary2List.setPreferredSize(new Dimension(0,0));
        AssetMainShowDictionary2List.addListSelectionListener(new AssetMainShowListSelectionListener());

        JList<String> AssetMainShowFileList = new JList<>();
        AssetMainShowFileList.setName("AssetMainShowFileList");
        AssetMainShowFileList.setFont(new Font("微软雅黑",Font.PLAIN, 15));
        AssetMainShowFileList.setPreferredSize(new Dimension(0,0));
        AssetMainShowFileList.addListSelectionListener(new AssetMainShowListSelectionListener());


        RSyntaxTextArea AssetMainShowFileArea = new RSyntaxTextArea();
        AssetMainShowFileArea.setName("AssetMainShowFileArea");
        AssetMainShowFileArea.setCodeFoldingEnabled(true);
        AssetMainShowFileArea.setLineWrap(true);
        AssetMainShowFileArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE);
        RTextScrollPane AssetMainShowFileAreaScrollPane = new RTextScrollPane(AssetMainShowFileArea);
        AssetMainShowFileAreaScrollPane.setHorizontalScrollBarPolicy(RTextScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        AssetMainShowFileAreaScrollPane.setName("AloneMainInputAreaScrollPane");
        AssetMainShowFileAreaScrollPane.setPreferredSize(new Dimension(0,0));
        AssetMainShowFileAreaScrollPane.setBorder(null);


        AssetMainShowPanel.add(AssetMainShowDictionary1List,new GridBagConstraints(
                0,0,
                1,1,
                0.15,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        AssetMainShowPanel.add(AssetMainShowDictionary2List,new GridBagConstraints(
                1,0,
                1,1,
                0.15,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        AssetMainShowPanel.add(AssetMainShowFileList,new GridBagConstraints(
                2,0,
                1,1,
                0.15,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        AssetMainShowPanel.add(AssetMainShowFileAreaScrollPane,new GridBagConstraints(
                3,0,
                1,1,
                0.55,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return AssetMainShowPanel;
    }

    public JComponent AssetMainControlPanel(){
        JComponent AssetMainControlPanel = new JPanel(new GridBagLayout());
        AssetMainControlPanel.setName("AssetMainControlPanel");



        return AssetMainControlPanel;
    }


    static class AssetMainShowListSelectionListener implements ListSelectionListener{

        @Override
        public void valueChanged(ListSelectionEvent e) {

            if(!e.getValueIsAdjusting()) return;
            JList<String> eList = (JList<String>) e.getSource();
            String eListName = eList.getName();
            String eFileName = eList.getSelectedValue();
            JComponent rootPanel = (JComponent) eList.getParent();
            JList<String> tList2 = (JList<String>) Helper.getComponent(rootPanel, "AssetMainShowFileList"); assert tList2 != null;
            JList<String> tList1 = null;
            String[] tFileNames = null;
            RSyntaxTextArea tTextArea = (RSyntaxTextArea) Helper.getComponent(rootPanel, "AssetMainShowFileArea"); assert tTextArea!=null;;
            switch (eListName){
                case "AssetMainShowDictionary1List":
                    tList1 = (JList<String>) Helper.getComponent(rootPanel, "AssetMainShowDictionary2List");assert tList1 != null;
                    Asset.AssetDictionary1 = eFileName;
                    tFileNames = Asset.getDictionary(
                            Asset.AssetRootPath+File.separator+
                                    eFileName
                    );
                    tList1.setListData(tFileNames);
                    tList2.setListData(new String[]{});
                    tTextArea.setText("");
                    break;
                case "AssetMainShowDictionary2List":
                    Asset.AssetDictionary2 = eFileName;
                    tFileNames = Asset.getFile(
                            Asset.AssetRootPath+File.separator+
                                    Asset.AssetDictionary1+File.separator+
                                    eFileName
                    );
                    tList2.setListData(tFileNames);
                    break;
                case "AssetMainShowFileList":
                    tTextArea.setText(Helper.readFile(
                            Asset.AssetRootPath+File.separator
                                    +Asset.AssetDictionary1+File.separator
                                    +Asset.AssetDictionary2+File.separator
                                    +eFileName
                    ));
                    break;
            }
//            rootPanel.updateUI();
        }
    }
}
