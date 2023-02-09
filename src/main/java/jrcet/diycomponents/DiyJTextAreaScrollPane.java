package jrcet.diycomponents;

import jrcet.frame.Setting.Setting;
import sun.font.FontDesignMetrics;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.Objects;
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
        textArea.setLineWrap(true);
        textArea.setFont(new Font("微软雅黑",Font.PLAIN,14));
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

        LineNumListCellRenderer lineNumListCellRenderer = new LineNumListCellRenderer(textArea);
        lineNumListCellRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        lineNumListCellRenderer.setFont(textArea.getFont());

        JList<Integer> listLineNum = new JList<>(listModelLineNum);
        listLineNum.setFixedCellWidth(30);
        listLineNum.setBackground(Color.WHITE);

        listLineNum.setCellRenderer(lineNumListCellRenderer);
        listLineNum.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0,0,0,1, Setting.gray),BorderFactory.createEmptyBorder(0,0,0,2)));
        listLineNum.setFont(textArea.getFont());

        setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        setName(name+"ScrollPane");
        setViewportView(textArea);
        setRowHeaderView(listLineNum);
        setPreferredSize(new Dimension(0,0));
        setBorder(BorderFactory.createMatteBorder(0,0,1,1,Setting.gray));
        setFirstLine();
    }

    private void setFirstLine(){
        setText(" ");
        setText("");
        setText("");
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

        private final JTextArea textArea;
        public LineNumListCellRenderer(JTextArea textArea){
            this.textArea=textArea;
        }
        @Override
        public Component getListCellRendererComponent(JList<? extends Integer> list, Integer value, int index, boolean isSelected, boolean cellHasFocus) {
            try {

                int lineRows = 1;
                Font font = textArea.getFont();
                AffineTransform affinetransform = new AffineTransform();
                FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
                int lineStartOffset = textArea.getLineStartOffset(value-1);
                int lineEndOffset = textArea.getLineEndOffset(value-1);
                String line = textArea.getText(lineStartOffset,lineEndOffset-lineStartOffset);

                int lineWidth = (int) (font.getStringBounds(line, frc).getWidth());
                int lineHeight = (int) (font.getStringBounds(line, frc).getHeight());
                double textAreaWidth = textArea.getSize().getWidth();
                if(textAreaWidth!=0.0&&lineWidth>textAreaWidth){
                    lineRows = (int)(lineWidth / textAreaWidth + (lineWidth % textAreaWidth != 0 ? 1 : 0));
                    int cellHeight = lineHeight*lineRows;
                    setVerticalAlignment(SwingConstants.TOP);
                    setPreferredSize(new Dimension(30,cellHeight));
                }
                setText(""+value);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return this;
        }
    }
}
