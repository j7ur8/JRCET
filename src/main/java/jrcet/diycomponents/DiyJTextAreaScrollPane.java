package jrcet.diycomponents;

import jrcet.frame.Jrcet;
import jrcet.frame.Setting.Setting;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

public class DiyJTextAreaScrollPane extends JScrollPane {
    private final JTextArea textArea;
    private final JList<Integer> lineNumberList;
    private final Font font = new Font("微软雅黑", Font.PLAIN, 14);
    private final FontRenderContext frc = new FontRenderContext(new AffineTransform(), true, true);

    public DiyJTextAreaScrollPane(String name) {
        textArea = new JTextArea();
        textArea.setName(name);
        textArea.setFont(font);
        textArea.setTabSize(2);
        textArea.setLineWrap(true);
        textArea.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 2));

        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateLineNumbers();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateLineNumbers();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateLineNumbers();
            }
        });


        lineNumberList = new JList<>(new LineNumberListModel());
        lineNumberList.setFont(font);
        lineNumberList.setBackground(Color.WHITE);
        lineNumberList.setCellRenderer(new LineNumberListCellRenderer(textArea));
        lineNumberList.setBorder(
                BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 0, 1, Setting.gray),
                BorderFactory.createEmptyBorder(0, 1, 0, 2))
        );

        setName(name + "ScrollPane");
        setViewportView(textArea);
        setRowHeaderView(lineNumberList);

        setPreferredSize(new Dimension(0, 0));
        setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Setting.gray));
        updateLineNumbers();
    }

    public void setText(String text) {
        textArea.setText(text);
    }

    public void addKeyListener(KeyListener keyListener) {
        textArea.addKeyListener(keyListener);
    }

    private void updateLineNumbers() {
        int lines = textArea.getLineCount();
        ((LineNumberListModel)lineNumberList.getModel()).setSize(lines);
    }

    public static class LineNumberListModel extends AbstractListModel<Integer> {
        private int size;
        public void setSize(int newSize) {
            int oldSize = size;
            this.size = newSize;
            fireContentsChanged(this, 0, Math.max(oldSize, newSize) - 1);
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

    public class LineNumberListCellRenderer extends JLabel implements ListCellRenderer<Integer> {

        private final JTextArea textArea;

        public LineNumberListCellRenderer(JTextArea textArea) {
            this.textArea = textArea;
            setFont(font);
            setHorizontalAlignment(SwingConstants.LEADING);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends Integer> list, Integer value, int index, boolean isSelected, boolean cellHasFocus) {
            try {
//                System.out.println("进入CellRenderer");
                setHorizontalAlignment(SwingConstants.RIGHT);
                setVerticalAlignment(SwingConstants.TOP);
                int lineStartOffset = textArea.getLineStartOffset(value - 1);
                int lineEndOffset = textArea.getLineEndOffset(value - 1);
                String line = textArea.getText(lineStartOffset, lineEndOffset - lineStartOffset);

                int lineWidth = (int) font.getStringBounds(line, frc).getWidth();
                int lineHeight = (int) font.getStringBounds(line, frc).getHeight()+1;
                int headerWidth = (int) font.getStringBounds(String.valueOf(value), frc).getWidth();

                double textAreaWidth = textArea.getSize().getWidth();
//                System.out.println("第"+value+"行，区域宽度："+textAreaWidth+"，文本宽度："+lineWidth);
                if (textAreaWidth != 0.0 && lineWidth > textAreaWidth) {
//                    System.out.println("进入LineWrap判断");
                    int lineRows = (int) (lineWidth / textAreaWidth + (lineWidth % textAreaWidth != 0 ? 1 : 0));
                    lineHeight = (lineHeight)*lineRows;
//                    System.out.println("行高："+lineHeight);
                }

                setPreferredSize(new Dimension((int) (headerWidth * 1.13), lineHeight));
                setText(String.valueOf(value));
            } catch (Exception e) {

                e.printStackTrace();
            }
            return this;
        }
    }

    public static void main(String[] args) {
        JFrame JrcetFrame = new JFrame("J7ur8's Remote Code Execute Tools");

        //setContentPane需放在前面，不然需要更改界面尺寸才会显示。
        Jrcet jrcet = new Jrcet();
        JrcetFrame.setContentPane(new DiyJTextAreaScrollPane("test"));

        JrcetFrame.setResizable(true);
        JrcetFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JrcetFrame.setSize(1200, 1000);
        JrcetFrame.setVisible(true);
    }
}
