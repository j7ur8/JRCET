package jrcet.frame.tools.Scanner.Fastjson;

import burp.*;
import jrcet.diycomponents.DiyJComponent;
import jrcet.diycomponents.DiyJTextArea.ui.rsyntaxtextarea.RSyntaxTextArea;
import jrcet.diycomponents.DiyJTextArea.ui.rtextarea.RTextArea;
import jrcet.diycomponents.DiyJTextArea.ui.rtextarea.RTextScrollPane;
import jrcet.frame.setting.Setting;
import jrcet.listeners.DiyFastjsonJTableListSelectionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.net.URL;

public class FastjsonComponent extends DiyJComponent {

    public static IMessageEditor FastjsonPackageRequestEditor = BurpExtender.callbacks.createMessageEditor(new CustomIMessageEditorController(),false);
    public static IMessageEditor FastjsonPackageResponseEditor = BurpExtender.callbacks.createMessageEditor(new CustomIMessageEditorController(),false);
    public static JComponent FastjsonComponentPanel = null;
    @Override
    public JComponent main() {
        FastjsonComponentPanel = new JPanel(new GridBagLayout());
        FastjsonComponentPanel.setName("FastjsonComponentPanel");
        FastjsonComponentPanel.setBackground(Color.WHITE);

        FastjsonComponentPanel.add(FastjsonMainPanel(),new GridBagConstraints(
                0,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return FastjsonComponentPanel;
    }

    public JComponent FastjsonMainPanel(){
        JComponent FastjsonMainPanel = new JPanel(new GridBagLayout());
        FastjsonMainPanel.setName("FastjsonMainPanel");
        FastjsonMainPanel.setBackground(Color.WHITE);

        FastjsonMainPanel.add(FastjsonInfoPanel(),new GridBagConstraints(
                0,0,
                1,1,
                1,0.4,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        FastjsonMainPanel.add(FastjsonPackagePanel(),new GridBagConstraints(
                0,1,
                1,1,
                1,0.6,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return FastjsonMainPanel;
    }

    public JComponent FastjsonInfoPanel(){
        JComponent FastjsonInfoPanel = new JPanel(new GridBagLayout());
        FastjsonInfoPanel.setPreferredSize(new Dimension(0,0));
        FastjsonInfoPanel.setName("FastjsonInfoPanel");
        FastjsonInfoPanel.setBackground(Color.YELLOW);

        String[] title = new String[]{"URL", "Method", "Status", "Payload", "Result", "Time"};
        String[][] data = new String[][]{};
//        String[][] data = new String[][]{{"1","2","3","4","1123"},{"1","2","3","4","1123"},{"1","2","3","4","1123"},{"1","2","3","4","1123"},{"1","2","3","4","1123"},{"1","2","3","4","1123"},{"1","2","3","4","1123"},{"1","2","3","4","1123"},{"1","2","3","4","1123"},{"1","2","3","4","1123"},{"1","2","3","4","1123"},{"1","2","3","4","1123"},{"1","2","3","4","1123"},{"1","2","3","4","1123"},{"1","2","3","4","1123"},{"1","2","3","4","1123"},{"1","2","3","4","1123"},{"1","2","3","4","1123"},{"1","2","3","4","1123"},{"1","2","3","4","1123"},{"1","2","3","4","1123"},{"1","2","3","4","1123"},{"1","2","3","4","1123"},{"1","2","3","4","1123"},{"1","2","3","4","1123"},{"1","2","3","4","1123"},{"1","2","3","4","1123"},{"1","2","3","4","1123"},{"1","2","3","4","1123"},{"1","2","3","4","1123"},{"1","2","3","4","1123"},{"1","2","3","4","1123"},{"1","2","3","4","1123"},{"1","2","3","4","1123"},{"1","2","3","4","1123"},{"1","2","3","4","1123"},{"1","2","3","4","1123"},{"1","2","3","4","1123"},{"1","2","3","4","1123"},{"1","2","3","4","1123"},{"1","2","3","4","1123"},{"1","2","3","4","1123"},{"1","2","3","4","1123"},{"1","2","3","4","1123"},{"1","2","3","4","1123"},{"1","2","3","4","1123"},{"1","2","3","4","1123"},{"1","2","3","4","1123"},{"1","2","3","4","5"},{"1","2","3","4","5"},{"1","2","3","4","5"},{"1","2","3","4","5"},{"1","2","3","4","5"},{"1","2","3","4","5"},{"1","2","3","4","5"},{"1","2","3","4","5"},{"1","2","3","4","5"},{"1","2","3","4","5"},{"1","2","3","4","5"},{"1","2","3","4","5"},{"1","2","3","4","5"},{"1","2","3","4","5"},{"1","2","3","4","5"},{"1","2","3","4","5"},{"1","2","3","4","5"},{"1","2","3","4","5"},{"1","2","3","4","5"},{"1","2","3","4","5"},{"1","2","3","4","5"},{"1","2","3","4","5"},{"1","2","3","4","yes"}};
        JTable FastjsonInfoTable = new JTable(new DefaultTableModel(data,title)){
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

//        DefaultTableModel cc = (DefaultTableModel) FastjsonInfoTable.getModel();
//        cc.addRow(new Object[]{"c","c","c","c","c"});
        FastjsonInfoTable.setName("FastjsonInfoTable");
        FastjsonInfoTable.getSelectionModel().addListSelectionListener(new DiyFastjsonJTableListSelectionListener());
        FastjsonInfoTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane FastjsonInfoTableScrollPane = new JScrollPane(FastjsonInfoTable);
        FastjsonInfoTableScrollPane.setName("FastjsonInfoTableScrollPane");
        FastjsonInfoTableScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        FastjsonInfoTableScrollPane.setPreferredSize(new Dimension(0,0));

        FastjsonInfoPanel.add(FastjsonInfoTableScrollPane,new GridBagConstraints(
                0,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return FastjsonInfoTableScrollPane;
    }

    public JComponent FastjsonPackagePanel(){
        JComponent FastjsonPackagePanel = new JPanel(new GridBagLayout());
        FastjsonPackagePanel.setName("FastjsonPackagePanel");
        FastjsonPackagePanel.setBackground(Color.ORANGE);

//        RSyntaxTextArea FastjsonPackageRequestArea = new RSyntaxTextArea();
//        FastjsonPackageRequestArea.setName("FastjsonPackageRequestArea");
//        FastjsonPackageRequestArea.setCodeFoldingEnabled(true);
//        FastjsonPackageRequestArea.setLineWrap(true);
//        FastjsonPackageRequestArea.setPreferredSize(new Dimension(0,0));
//        FastjsonPackageRequestArea.setSyntaxEditingStyle(RSyntaxTextArea.SYNTAX_STYLE_NONE);
//
//        RTextScrollPane FastjsonPackageRequestScrollPane = new RTextScrollPane(FastjsonPackageRequestArea);
//        FastjsonPackageRequestScrollPane.setName("FastjsonPackageRequestScrollPane");
//        FastjsonPackageRequestScrollPane.setPreferredSize(new Dimension(0,0));
//        FastjsonPackageRequestScrollPane.setHorizontalScrollBarPolicy(RTextScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//
//        RSyntaxTextArea FastjsonPackageResponseArea = new RSyntaxTextArea();
//        FastjsonPackageResponseArea.setName("FastjsonPackageResponseArea");
//        FastjsonPackageResponseArea.setCodeFoldingEnabled(true);
//        FastjsonPackageResponseArea.setLineWrap(true);
//        FastjsonPackageResponseArea.setPreferredSize(new Dimension(0,0));
//        FastjsonPackageResponseArea.setSyntaxEditingStyle(RSyntaxTextArea.SYNTAX_STYLE_NONE);
//
//        RTextScrollPane FastjsonPackageResponseScrollPane = new RTextScrollPane(FastjsonPackageResponseArea);
//        FastjsonPackageResponseScrollPane.setName("FastjsonPackageResponseScrollPane");
//        FastjsonPackageResponseScrollPane.setPreferredSize(new Dimension(0,0));
//        FastjsonPackageResponseScrollPane.setHorizontalScrollBarPolicy(RTextScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//        FastjsonPackageRequestScrollPane.setBorder(BorderFactory.createMatteBorder(1,0,1,0, Setting.gray));
        Component FastjsonPackageRequestPanel = FastjsonPackageRequestEditor.getComponent();
        FastjsonPackageRequestPanel.setName("FastjsonPackageRequestPanel");
        FastjsonPackageRequestPanel.setPreferredSize(new Dimension(0,0));
        FastjsonPackagePanel.add(FastjsonPackageRequestPanel, new GridBagConstraints(
                0,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));


        Component FastjsonPackageResponsePanel = FastjsonPackageResponseEditor.getComponent();
        FastjsonPackageResponsePanel.setName("FastjsonPackageResponsePanel");
        FastjsonPackageResponsePanel.setPreferredSize(new Dimension(0,0));
        FastjsonPackagePanel.add(FastjsonPackageResponsePanel, new GridBagConstraints(
                1,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));


        return FastjsonPackagePanel;
    }


}
