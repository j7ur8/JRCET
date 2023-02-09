package jrcet;

import jrcet.diycomponents.DiyJTextAreaScrollPane;
import jrcet.frame.Setting.Setting;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class test {
    private final JScrollPane scrollPaneTextArea;
    private final JTextArea textArea;
    private final LineNumListModel listModelLineNum;

    public static class LineNumListCellRenderer extends JLabel implements ListCellRenderer<Integer> {

        @Override
        public Component getListCellRendererComponent(JList<? extends Integer> list, Integer value, int index, boolean isSelected, boolean cellHasFocus) {
            setText("" + value);
            return this;
        }

    }

    test(){
        textArea = new JTextArea();
//        textArea.setLineWrap(true);
        textArea.setFont(new Font("微软雅黑",Font.PLAIN,14));
        textArea.setTabSize(2);

        listModelLineNum = new LineNumListModel();

        LineNumListCellRenderer lineNumListCellRenderer = new LineNumListCellRenderer();
        lineNumListCellRenderer.setHorizontalAlignment(SwingConstants.RIGHT);

        JList<Integer> listLineNum = new JList<>(listModelLineNum);
        listLineNum.setFixedCellWidth(30);
        listLineNum.setBackground(Color.WHITE);
        listLineNum.setCellRenderer(lineNumListCellRenderer);
        listLineNum.setBorder(BorderFactory.createMatteBorder(0,0,0,1, Setting.gray));


        scrollPaneTextArea = new JScrollPane();
//        scrollPaneTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPaneTextArea.setViewportView(textArea);
        scrollPaneTextArea.setRowHeaderView(listLineNum);

        textArea.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void removeUpdate(DocumentEvent e) {
                int lines = textArea.getLineCount();
                listModelLineNum.setSize(lines);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                int lines = textArea.getLineCount();
                listModelLineNum.setSize(lines);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                int lines = textArea.getLineCount();
                listModelLineNum.setSize(lines);
            }
        });

        listLineNum.setFont(textArea.getFont());
        lineNumListCellRenderer.setFont(textArea.getFont());
    }

    public static class LineNumListModel extends AbstractListModel<Integer> {
        private int size;
        public void setSize(int newSize) {
            int oldSize = size;
            this.size = newSize;
            if (oldSize > newSize) {
                fireIntervalRemoved(this, newSize, oldSize - 1);
            } else if (oldSize < newSize) {
                fireIntervalAdded(this, oldSize, newSize - 1);
            }
        }
        @Override
        public int getSize() {
            return size;
        }
        @Override
        public Integer getElementAt(int index) {
            return index + 1;
        }
    }


    public static void main(String[] args) {
//        test a = new test();
        JFrame JrcetFrame = new JFrame("J7ur8's Remote Code Execute Tools");

        DiyJTextAreaScrollPane diyJTextAreaScrollPane = new DiyJTextAreaScrollPane("testArea");
        //setContentPane需放在前面，不然需要更改界面尺寸才会显示。
        JrcetFrame.setContentPane(diyJTextAreaScrollPane);

        JrcetFrame.setResizable(true);
        JrcetFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JrcetFrame.setSize(1200, 1000);
        JrcetFrame.setVisible(true);
    }

}
