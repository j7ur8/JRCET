package jrcet.frame.Asset;

import jrcet.diycomponents.DiyJComponent;
import jrcet.diycomponents.DiyJTextField;
import jrcet.listener.AssetSearchKeyListener;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class AssetComponent extends DiyJComponent {

    public static JComponent AssetComponentPanel = null;
    @Override
    public JComponent main() {

        AssetComponentPanel = new JPanel(new GridBagLayout());

        AssetComponentPanel.setName("AssetComponentPanel");
        AssetComponentPanel.setBackground(Color.WHITE);

        AssetComponentPanel.add(AssetMainPanel(), new GridBagConstraints(
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

    private JComponent AssetMainPanel(){
        JComponent AssetMainPanel = new JPanel(new GridBagLayout());
        AssetMainPanel.setName("AssetMainPanel");
        AssetMainPanel.setBackground(Color.WHITE);

        AssetMainPanel.add(AssetMainSearchPanel(), new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(5,0,5,0),
                0,0
        ));

        AssetMainPanel.add(AssetMainBodyPanel(), new GridBagConstraints(
                0,1,
                1,1,
                1,0.8,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));


        return AssetMainPanel;
    }

    private JComponent AssetMainSearchPanel() {
        JComponent AssetMainSearchPanel = new JPanel(new GridBagLayout());
        AssetMainSearchPanel.setName("AssetMainSearchPanel");
        AssetMainSearchPanel.setBackground(Color.WHITE);

        DiyJTextField AssetMainSearchIpField = new DiyJTextField("IP");
        AssetMainSearchIpField.setName("AssetMainSearchIpField");
        AssetMainSearchIpField.setPreferredSize(new Dimension(0,30));
        AssetMainSearchIpField.getBodyField().addKeyListener(new AssetSearchKeyListener());

        DiyJTextField AssetMainSearchDomainField = new DiyJTextField("Domain");
        AssetMainSearchDomainField.setName("AssetMainSearchDomainField");
        AssetMainSearchDomainField.setPreferredSize(new Dimension(0,30));
        AssetMainSearchDomainField.getBodyField().addKeyListener(new AssetSearchKeyListener());

        DiyJTextField AssetMainSearchIcpField = new DiyJTextField("ICP");
        AssetMainSearchIcpField.setName("AssetMainSearchIcpField");
        AssetMainSearchIcpField.setPreferredSize(new Dimension(0,30));
        AssetMainSearchIcpField.getBodyField().addKeyListener(new AssetSearchKeyListener());

        DiyJTextField AssetMainSearchBodyField = new DiyJTextField("Body");
        AssetMainSearchBodyField.setName("AssetMainSearchBodyField");
        AssetMainSearchBodyField.setPreferredSize(new Dimension(0,30));
        AssetMainSearchBodyField.getBodyField().addKeyListener(new AssetSearchKeyListener());

        DiyJTextField AssetMainSearchTitleField = new DiyJTextField("Title");
        AssetMainSearchTitleField.setName("AssetMainSearchTitleField");
        AssetMainSearchTitleField.setPreferredSize(new Dimension(0,30));
        AssetMainSearchTitleField.getBodyField().addKeyListener(new AssetSearchKeyListener());

        AssetMainSearchPanel.add(AssetMainSearchIpField,new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,5,0,5),
                0,0
        ));

        AssetMainSearchPanel.add(AssetMainSearchDomainField,new GridBagConstraints(
                1,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,5,0,5),
                0,0
        ));

        AssetMainSearchPanel.add(AssetMainSearchIcpField,new GridBagConstraints(
                2,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,5,0,5),
                0,0
        ));

        AssetMainSearchPanel.add(AssetMainSearchBodyField,new GridBagConstraints(
                3,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,5,0,5),
                0,0
        ));

        AssetMainSearchPanel.add(AssetMainSearchTitleField,new GridBagConstraints(
                4,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,5,0,5),
                0,0
        ));
        return AssetMainSearchPanel;
    }

    private JComponent AssetMainBodyPanel() {
        JComponent AssetMainBodyPanel = new JPanel(new GridBagLayout());
        AssetMainBodyPanel.setName("AssetMainBodyPanel");
        AssetMainBodyPanel.setBackground(Color.WHITE);


        TableCellRenderer renderer = new DefaultTableCellRenderer() {

            private static final long serialVersionUID = 1L;

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                ((DefaultTableCellRenderer) c).setHorizontalAlignment(SwingConstants.CENTER);
                return c;
            }
        };
//        table.setDefaultRenderer(Object.class, renderer);

        Object[][] data = {};
        String[] columnNames = {"protocol","url","domain","ip","port","title","icp","location","updatedtime","product","source"};

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable AssetMainBodyResultTable = new JTable(model);
        AssetMainBodyResultTable.setName("AssetMainBodyResultTable");
        AssetMainBodyResultTable.setDefaultRenderer(Object.class,renderer);
        AssetMainBodyResultTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        AssetMainBodyResultTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        AssetMainBodyResultTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        AssetMainBodyResultTable.getColumnModel().getColumn(2).setPreferredWidth(120);
        AssetMainBodyResultTable.getColumnModel().getColumn(3).setPreferredWidth(120);
        AssetMainBodyResultTable.getColumnModel().getColumn(4).setPreferredWidth(50);
        AssetMainBodyResultTable.getColumnModel().getColumn(5).setPreferredWidth(200);
        AssetMainBodyResultTable.getColumnModel().getColumn(6).setPreferredWidth(170);
        AssetMainBodyResultTable.getColumnModel().getColumn(7).setPreferredWidth(150);
        AssetMainBodyResultTable.getColumnModel().getColumn(8).setPreferredWidth(150);
        AssetMainBodyResultTable.getColumnModel().getColumn(9).setPreferredWidth(300);

        JScrollPane AssetMainBodyResultTableScrollPane = new JScrollPane(AssetMainBodyResultTable);
        AssetMainBodyResultTableScrollPane.setName("AssetMainBodyResultTableScrollPane");
//        AssetMainBodyResultTable.setDefaultEditor(Object.class, null);
        AssetMainBodyPanel.add(AssetMainBodyResultTableScrollPane,new GridBagConstraints(
                0,0,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,5,0,5),
                0,0
        ));
        return AssetMainBodyPanel;
    }
}
