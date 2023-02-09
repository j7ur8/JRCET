package jrcet.diycomponents;

import jrcet.frame.Setting.Setting;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.KeyListener;
import java.util.Set;

public class DiyJTextAreaScrollPane extends JScrollPane{
    private final JTextArea textArea;
    private final LineNumListModel listModelLineNum;

    public void setText(String text){
        this.textArea.setText(text);
    }

    public void addKeyListener(KeyListener keyListener){
        this.textArea.addKeyListener(keyListener);
    }
    public DiyJTextAreaScrollPane(String name){

        textArea = new JTextArea();
        textArea.setName(name);
//        textArea.setLineWrap(true);
//        textArea.setFont(new Font("微软雅黑",Font.PLAIN,14));
        textArea.setBorder(BorderFactory.createEmptyBorder(0,2,0,2));
        textArea.setTabSize(2);

        listModelLineNum = new LineNumListModel();
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

        LineNumListCellRenderer lineNumListCellRenderer = new LineNumListCellRenderer();
        lineNumListCellRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        lineNumListCellRenderer.setFont(textArea.getFont());

        JList<Integer> listLineNum = new JList<>(listModelLineNum);
        listLineNum.setFixedCellWidth(30);
        listLineNum.setBackground(Color.WHITE);

        listLineNum.setCellRenderer(lineNumListCellRenderer);
        listLineNum.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0,0,0,1, Setting.gray),BorderFactory.createEmptyBorder(0,0,0,2)));
        listLineNum.setFont(textArea.getFont());


        setName(name+"ScrollPane");
        setViewportView(textArea);
        setRowHeaderView(listLineNum);
        setPreferredSize(new Dimension(0,0));
        setBorder(BorderFactory.createMatteBorder(0,0,1,1,Setting.gray));

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

    public static class LineNumListCellRenderer extends JLabel implements ListCellRenderer<Integer> {

        @Override
        public Component getListCellRendererComponent(JList<? extends Integer> list, Integer value, int index, boolean isSelected, boolean cellHasFocus) {
            setText("" + value);
            return this;
        }

    }

}
