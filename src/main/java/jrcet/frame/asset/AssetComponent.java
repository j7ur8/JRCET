package jrcet.frame.asset;

import jrcet.diycomponents.DiyJButton;
import jrcet.diycomponents.DiyJComponent;
import jrcet.diycomponents.DiyJLabel;
import jrcet.diycomponents.DiyJTextArea.ui.rsyntaxtextarea.RSyntaxTextArea;
import jrcet.diycomponents.DiyJTextArea.ui.rsyntaxtextarea.SyntaxConstants;
import jrcet.diycomponents.DiyJTextArea.ui.rtextarea.RTextScrollPane;
import jrcet.lib.Helper;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
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
        AssetMainShowPanel.setBackground(new Color(250,250,250));
        AssetMainShowPanel.setPreferredSize(new Dimension(0,0));
        AssetMainShowPanel.setName("AssetMainShowPanel");

        JList<String> AssetMainShowDictionary1List = new JList<>(Asset.getDictionary(Asset.AssetRootPath));
        AssetMainShowDictionary1List.setName("AssetMainShowDictionary1List");
        AssetMainShowDictionary1List.setFont(new Font("微软雅黑",Font.PLAIN, 15));
        AssetMainShowDictionary1List.addListSelectionListener(new AssetMainShowListSelectionListener());
        AssetMainShowDictionary1List.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        AssetMainShowDictionary1List.setFixedCellHeight(25);
        JScrollPane AssetMainShowDictionary1ListScrollPane = new JScrollPane(AssetMainShowDictionary1List);
        AssetMainShowDictionary1ListScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        AssetMainShowDictionary1ListScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        AssetMainShowDictionary1ListScrollPane.setPreferredSize(new Dimension(0,0));
        AssetMainShowDictionary1ListScrollPane.setName("AssetMainShowDictionary1ListScrollPane");
        AssetMainShowDictionary1ListScrollPane.setBorder(BorderFactory.createMatteBorder(0,1,0,0,new Color(237,237,237)));

        JList<String> AssetMainShowDictionary2List = new JList<>();
        AssetMainShowDictionary2List.setName("AssetMainShowDictionary2List");
        AssetMainShowDictionary2List.setFont(new Font("微软雅黑",Font.PLAIN, 15));
        AssetMainShowDictionary2List.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        AssetMainShowDictionary2List.setFixedCellHeight(25);
        AssetMainShowDictionary2List.addListSelectionListener(new AssetMainShowListSelectionListener());
        JScrollPane AssetMainShowDictionary2ListScrollPane = new JScrollPane(AssetMainShowDictionary2List);
        AssetMainShowDictionary2ListScrollPane.setName("AssetMainShowDictionary2ListScrollPane");
        AssetMainShowDictionary2ListScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        AssetMainShowDictionary2ListScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        AssetMainShowDictionary2ListScrollPane.setPreferredSize(new Dimension(0,0));
        AssetMainShowDictionary2ListScrollPane.setBorder(null);

        JList<String> AssetMainShowFileList = new JList<>();
        AssetMainShowFileList.setName("AssetMainShowFileList");
        AssetMainShowFileList.setFont(new Font("微软雅黑",Font.PLAIN, 15));
        AssetMainShowFileList.setPreferredSize(new Dimension(0,0));
        AssetMainShowFileList.setFixedCellHeight(25);
        AssetMainShowFileList.addListSelectionListener(new AssetMainShowListSelectionListener());
        AssetMainShowFileList.addListSelectionListener(new AssetMainShowListSelectionListener());
        JScrollPane AssetMainShowFileListScrollPane = new JScrollPane(AssetMainShowFileList);
        AssetMainShowFileListScrollPane.setName("AssetMainShowFileListScrollPane");
        AssetMainShowFileListScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        AssetMainShowFileListScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        AssetMainShowFileListScrollPane.setPreferredSize(new Dimension(0,0));
        AssetMainShowFileListScrollPane.setBorder(null);

        RSyntaxTextArea AssetMainShowFileArea = new RSyntaxTextArea();
        AssetMainShowFileArea.setName("AssetMainShowFileArea");
        AssetMainShowFileArea.setCodeFoldingEnabled(true);
        AssetMainShowFileArea.setLineWrap(true);
        AssetMainShowFileArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE);
        RTextScrollPane AssetMainShowFileAreaScrollPane = new RTextScrollPane(AssetMainShowFileArea);
        AssetMainShowFileAreaScrollPane.setHorizontalScrollBarPolicy(RTextScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        AssetMainShowFileAreaScrollPane.setName("AloneMainInputAreaScrollPane");
        AssetMainShowFileAreaScrollPane.setPreferredSize(new Dimension(0,0));
        AssetMainShowFileAreaScrollPane.setBorder(BorderFactory.createMatteBorder(1,0,0,0, new Color(238,238,238)));


        AssetMainShowPanel.add(AssetMainShowDictionary1ListScrollPane,new GridBagConstraints(
                0,0,
                1,1,
                0.3,0.3,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,15,0,0),
                0,0
        ));

        AssetMainShowPanel.add(AssetMainShowDictionary2ListScrollPane,new GridBagConstraints(
                1,0,
                1,1,
                0.3,0.3,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        AssetMainShowPanel.add(AssetMainShowFileListScrollPane,new GridBagConstraints(
                2,0,
                1,1,
                0.3,0.3,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        AssetMainShowPanel.add(AssetMainShowFileAreaScrollPane,new GridBagConstraints(
                0,1,
                3,1,
                1,0.7,
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

        AssetMainControlPanel.add(AssetMainControlUrlPanel(),new GridBagConstraints(
                0,AssetMainControlPanel.getComponentCount(),
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        AssetMainControlPanel.add(AssetMainControlPinterPanel(),new GridBagConstraints(
                0,AssetMainControlPanel.getComponentCount(),
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        AssetMainControlPanel.add(AssetMainControlBelongPanel(),new GridBagConstraints(
                0,AssetMainControlPanel.getComponentCount(),
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        AssetMainControlPanel.add(AssetMainControlSavePanel(),new GridBagConstraints(
                0,AssetMainControlPanel.getComponentCount(),
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        AssetMainControlPanel.add(Helper.blackPanel(), new GridBagConstraints(
                0,AssetMainControlPanel.getComponentCount(),
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return AssetMainControlPanel;
    }

    public JComponent AssetMainControlSavePanel(){
        JComponent AssetMainControlSavePanel = new JPanel(new GridBagLayout());
        AssetMainControlSavePanel.setName("AssetMainControlSavePanel");
        AssetMainControlSavePanel.setPreferredSize(new Dimension(0,130));

        JTextField AssetMainControlPinterField = new JTextField();
        AssetMainControlPinterField.setName("AssetMainControlPinterField");
        AssetMainControlPinterField.setPreferredSize(new Dimension(0,40));

        JTextField AssetMainControlBelongField = new JTextField();
        AssetMainControlBelongField.setName("AssetMainControlPinterField");
        AssetMainControlBelongField.setPreferredSize(new Dimension(0,40));

        DiyJButton AssetMainControlSaveButton = new DiyJButton("Save");
        AssetMainControlSaveButton.setName("AssetMainControlSaveButton");
        AssetMainControlSaveButton.setPreferredSize(new Dimension(0,40));
        AssetMainControlSaveButton.setBackground(new Color(238,238,238));

        AssetMainControlSavePanel.add(AssetMainControlPinterField, new GridBagConstraints(
                0, 0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        AssetMainControlSavePanel.add(AssetMainControlBelongField, new GridBagConstraints(
                0, 1,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        AssetMainControlSavePanel.add(AssetMainControlSaveButton, new GridBagConstraints(
                0, 2,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));


        return AssetMainControlSavePanel;
    }

    public JComponent AssetMainControlPinterPanel(){
        String[] labelNames = new String[]{"Nginx","Tomcat","FastJson","Struts2","Shiro","Weblogic","JBoss"};
        JComponent AssetMainControlPinterPanel = new JPanel(new GridBagLayout());
        AssetMainControlPinterPanel.setName("AssetMainControlPinterPanel");
        AssetMainControlPinterPanel.setPreferredSize(new Dimension(0,(((labelNames.length-1)/4)+2)*40));
        AssetMainControlPinterPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1,0,0,0, Color.BLACK),"资产指纹"));

        setAssetButton(AssetMainControlPinterPanel, labelNames);

        return AssetMainControlPinterPanel;
    }

    public JComponent AssetMainControlBelongPanel(){
        String[] labelNames = new String[]{"中国电信","中国移动","中国联通","中国政府","中国通服"};
        JComponent AssetMainControlBelongPanel = new JPanel(new GridBagLayout());
        AssetMainControlBelongPanel.setName("AssetMainControlBelongPanel");
        AssetMainControlBelongPanel.setPreferredSize(new Dimension(0,(((labelNames.length-1)/4)+2)*45));
        AssetMainControlBelongPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1,0,0,0, Color.BLACK),"资产归属"));

        setAssetButton(AssetMainControlBelongPanel, labelNames);

        return AssetMainControlBelongPanel;
    }

    public JComponent AssetMainControlUrlPanel() {
        JComponent AssetMainControlUrlPanel = new JPanel(new GridBagLayout());
        AssetMainControlUrlPanel.setName("AssetMainControlUrlPanel");
        AssetMainControlUrlPanel.setPreferredSize(new Dimension(0,50));

        JTextField AssetMainControlUrlField = new JTextField();
        AssetMainControlUrlField.setName("AssetMainControlUrlField");
        AssetMainControlUrlField.setFont(new Font("微软雅黑",Font.PLAIN,17));
        AssetMainControlUrlField.setHorizontalAlignment(JTextField.CENTER);
        AssetMainControlUrlField.setPreferredSize(new Dimension(0,0));

        AssetMainControlUrlPanel.add(AssetMainControlUrlField ,new GridBagConstraints(
                0,AssetMainControlUrlField.getComponentCount(),
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));


        return AssetMainControlUrlPanel;
    }

    void setAssetButton(JComponent tPanel, String[] tStrings){
        for(int i=0;i<tStrings.length;i++){
            String s = tStrings[i];
            DiyJLabel tmpLabel = new DiyJLabel(s);
            tmpLabel.setPreferredSize(new Dimension(0,30));
            tmpLabel.setName(tPanel.getName().replace("Panel","Laben"+String.valueOf(i)+"Panel"));

            tPanel.add(tmpLabel, new GridBagConstraints(
                    i-((i/4)*4), i/4,
                    1,1,
                    0.25,0,
                    GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH,
                    new Insets(0,1,2,1),
                    0,0
            ));
        }

        tPanel.add(Helper.blackPanel(),new GridBagConstraints(
                tStrings.length%4, (tStrings.length-1)/4,
                4-tStrings.length%4,1,
                0.25*(4-tStrings.length%4),0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));
    }

    static class AssetMainShowListSelectionListener implements ListSelectionListener{

        @Override
        public void valueChanged(ListSelectionEvent e) {

            if(!e.getValueIsAdjusting()) return;
            JList<String> eList = (JList<String>) e.getSource();
            String eListName = eList.getName();
            String eFileName = eList.getSelectedValue();
            JComponent rootPanel = (JComponent) eList.getParent().getParent().getParent();
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
                                    +eFileName,3145728
                    ));
                    break;
            }
//            rootPanel.updateUI();
        }
    }
}
