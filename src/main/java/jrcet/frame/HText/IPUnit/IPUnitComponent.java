package jrcet.frame.HText.IPUnit;

import jrcet.diycomponents.*;
import jrcet.help.Helper;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public class IPUnitComponent extends DiyJComponent {

    public static HashMap<String, GridBagConstraints> ComponentConstraintHashMap = new HashMap<>();
    public static HashMap<String, JComponent> MainPanelHashMap = new HashMap<>();

    public static JComponent IPUnitComponentPanel = null;

    @Override
    public JComponent main() {

        IPUnitComponentPanel = new JPanel(new GridBagLayout());
        IPUnitComponentPanel.setBackground(Color.WHITE);
        IPUnitComponentPanel.setName("IPUnitComponentPanel");

        IPUnitComponentPanel.add(IPUnitTagTabPanel(),new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        MainPanelHashMap.put("1", IPUnitMainPanel());
        Helper.setConstraints(ComponentConstraintHashMap, IPUnitComponentPanel, getIPUnitMainPanel("1"),new GridBagConstraints(
                0,1,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return IPUnitComponentPanel;
    }

    public JComponent IPUnitTagTabPanel(){

        JPanel IPUnitTagTabPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,0,0));
        IPUnitTagTabPanel.setName("IPUnitTagTabPanel");
        IPUnitTagTabPanel.setBackground(Color.WHITE);
        IPUnitTagTabPanel.setBorder(BorderFactory.createMatteBorder(0,0,0,0,new Color(203,208,209)));

        DiyJAddLabel IPUnitTagTabSticker1Label = new DiyJAddLabel("1",true);
        IPUnitTagTabSticker1Label.setName("IPUnitTagTabSticker1Label");
        IPUnitTagTabSticker1Label.setPanel(getIPUnitMainPanel("1"));
        IPUnitTagTabPanel.add(IPUnitTagTabSticker1Label);

        DiyJAddLabel IPUnitTabAddLabel = new DiyJAddLabel("···");
        IPUnitTabAddLabel.setName("IPUnitTabAddLabel");
        IPUnitTagTabPanel.add(IPUnitTabAddLabel);

        return IPUnitTagTabPanel;
    }

    public JComponent IPUnitMainPanel(){
        JPanel IPUnitMainPanel = new JPanel(new GridBagLayout());
        IPUnitMainPanel.setName("IPUnitMainPanel");
        IPUnitMainPanel.setBackground(Color.WHITE);
        IPUnitMainPanel.setPreferredSize(new Dimension(0,0));

        IPUnitMainPanel.add(Helper.blackPanel(),new GridBagConstraints(
                0,0,
                1,1,
                0.2,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        IPUnitMainPanel.add(IPUnitMainInputAreaScrollPane(),new GridBagConstraints(
                1,0,
                1,1,
                0.6,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        IPUnitMainPanel.add(IPUnitMainControlPanel(),new GridBagConstraints(
                2,0,
                1,1,
                0.2,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return IPUnitMainPanel;
    }

    public JComponent IPUnitMainInputAreaScrollPane(){
        DiyJTextAreaScrollPane IPUnitMainInputAreaScrollPane = new DiyJTextAreaScrollPane("IPUnitMainInputArea");

        IPUnitMainInputAreaScrollPane.setText("#请输入文件路径或\\n分割的字符串...");
        IPUnitMainInputAreaScrollPane.addKeyListener(new IPUnitMainInputAreaKeyListener());

        return  IPUnitMainInputAreaScrollPane;
    }

    public JComponent IPUnitMainControlPanel(){
        JComponent IPUnitMainControlPanel = new JPanel(new GridBagLayout());
        IPUnitMainControlPanel.setName("IPUnitMainControlPanel");

        JList<String> IPUnitMainControlModeList = new JList<>(new String[]{"打印IP段内的全部IP","判断IP是否属于IP段"});
        IPUnitMainControlModeList.setName("IPUnitMainControlModeList");
        IPUnitMainControlModeList.setName("IPUnitMainControlModeList");
        IPUnitMainControlModeList.setPreferredSize(new Dimension(0,50));
        IPUnitMainControlModeList.setFont(new Font("微软雅黑", Font.PLAIN,16));
        IPUnitMainControlModeList.setSelectedIndex(0);
        IPUnitMainControlModeList.setCellRenderer(new IPUnitMainControlModeListCellRenderer());
        IPUnitMainControlModeList.addListSelectionListener(new IPUnitMainControlModeListListener());

        IPUnitMainControlPanel.add(IPUnitMainControlModeList, new GridBagConstraints(
                0,IPUnitMainControlPanel.getComponentCount(),
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(20,0,10,0),
                0,0
        ));

        IPUnitMainControlPanel.add(Helper.blackPanel(), new GridBagConstraints(
                0,IPUnitMainControlPanel.getComponentCount(),
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return IPUnitMainControlPanel;
    }



    public JComponent getIPUnitMainPanel(String TagName){
        return MainPanelHashMap.containsKey(TagName)?(MainPanelHashMap.get(TagName)!=null?MainPanelHashMap.get(TagName): Helper.blackPanel()):Helper.blackPanel();
    }

    static class IPUnitMainInputAreaKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {

            if( (e.getModifiers()== InputEvent.CTRL_MASK || e.getModifiers() == InputEvent.META_MASK) && e.getKeyCode()==71){
                JTextArea eTextArea = (JTextArea) e.getSource();
                eTextArea.setText(IPUnit.handle(eTextArea.getText()));
            }
        }
    }

    static class IPUnitMainControlModeListListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            if(!e.getValueIsAdjusting()) return;
            JList<String> eJList = (JList<String>) e.getSource();
            IPUnit.IPUnitMode = eJList.getSelectedValue();
        }
    }

    static class IPUnitMainControlModeListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel tLabel = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (isSelected) {
                tLabel.setBackground(new Color(254,152,165));
                tLabel.setBorder(null);
            }
            return tLabel;
        }
    }


}
