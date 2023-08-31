package jrcet.diycomponents;


import jrcet.help.Helper;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static burp.MyExtender.API;
import static jrcet.Main.centerInScreen;

public class DiyJTextAreaScrollPane extends JScrollPane {
    private final JTextArea textArea;
    private String textEncoding = "UTF-8";
    private byte[] textBytes = new byte[]{};
    private final JList<Integer> lineNumberList;
    private final Font font = new Font("微软雅黑", Font.PLAIN, 14);
    private final FontRenderContext frc = new FontRenderContext(new AffineTransform(), true, true);

    public DiyJTextAreaScrollPane(String name) {
        super(new JTextArea());
        textArea = (JTextArea) getViewport().getComponent(0);
        textArea.setName(name);
        textArea.setFont(font);
        textArea.setTabSize(2);
        textArea.setLineWrap(true);
        textArea.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 2));
        textArea.setForeground(new Color(34,34,34));
        // 为文本区域添加鼠标监听器，当右键被单击时显示弹出菜单
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem changeEncodingItem  = new JMenuItem("Change Encoding");
        changeEncodingItem.setName(name+"EncodingMenuItem");
        changeEncodingItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(Objects.equals(textEncoding, "UTF-8")){
                    textBytes=textArea.getText().getBytes(StandardCharsets.UTF_8);
                    try {
                        textArea.setText(new String(textBytes, "GBK"));
                        textEncoding="GBK";
                    } catch (UnsupportedEncodingException ex) {
                        API.logging().error().println("Unsupport Encoding at DiyJTextAreaScrollPane.java");
                    }
                } else if(Objects.equals(textEncoding, "GBK")){
                    textArea.setText(new String(textBytes,StandardCharsets.UTF_8));
                    textEncoding="UTF-8";
                }
            }
        });
        popupMenu.add(changeEncodingItem);
        textArea.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {

                if (e.getModifiersEx() == InputEvent.META_DOWN_MASK) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

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
                BorderFactory.createMatteBorder(0, 0, 0, 1, Helper.gray),
                BorderFactory.createEmptyBorder(0, 1, 0, 2))
        );

        setName(name + "ScrollPane");
        setRowHeaderView(lineNumberList);

        setPreferredSize(new Dimension(0, 0));
        setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Helper.gray));
        updateLineNumbers();
    }

    public void setText(String text) {
        textBytes = text.getBytes(StandardCharsets.ISO_8859_1);
        textArea.setText(text);
    }

    public String getText(){
        return textArea.getText();
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
//            setBorder(BorderFactory.createEmptyBorder(0,2,0,0));
            setForeground(new Color(120,120,120));
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

                setPreferredSize(new Dimension((int) (headerWidth * 1.13)+3, lineHeight));
                setText(String.valueOf(value));
            } catch (Exception e) {

                e.printStackTrace();
            }
            return this;
        }
    }



    public static void main(String[] args) {
        JFrame RScriptFrame = new JFrame("RScript");

        //setContentPane需放在前面，不然需要更改界面尺寸才会显示。
        RScriptFrame.setContentPane(new JTextPane());

//        JrcetFrame.setResizable(true);
//        RScriptFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        RScriptFrame.setSize(600, 500);
        centerInScreen(RScriptFrame);
        RScriptFrame.setVisible(true);
    }
}
