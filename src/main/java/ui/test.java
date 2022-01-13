package ui;

import java.awt.*;

import javax.swing.*;

public class test {

    protected void initUI() {
        JFrame frame = new JFrame(test.class.getSimpleName());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(50,0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBackground(Color.ORANGE);

        JTextArea textField = new JTextArea();
        JLabel label = new JLabel("A cool long nice label that will stretch.");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBorder(BorderFactory.createLineBorder(Color.GREEN));

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;// Fill the "cell" in both direction
        gbc.weightx = 1.0;// Allocate extra-width to the label
        gbc.weighty = 1.0;// Allocate extra-height to the label
        gbc.gridwidth = GridBagConstraints.REMAINDER;// The label takes all the available width of the "row"
        panel.add(label, gbc);

        gbc.weighty = 0; // Don't stretch TF vertically
        gbc.fill = GridBagConstraints.BOTH; // Fill horizontally
        gbc.gridwidth = GridBagConstraints.RELATIVE;
        panel.add(textField, gbc);
        gbc.weightx = 0; // No extra horizontal space is given to the button
        gbc.fill = GridBagConstraints.NONE; // No fill for the button
        panel.add(buttonPanel, gbc);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new test().initUI();
            }
        });
    }
}

