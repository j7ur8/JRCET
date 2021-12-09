package ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.net.MalformedURLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class test{

    private JFrame frame;
    private JPanel buttonPanel;
    private GridBagConstraints constraints;

    public test() throws MalformedURLException{

        frame = new JFrame();

        buttonPanel = new JPanel();
        frame.add(buttonPanel);

        buttonPanel.setLayout(new GridBagLayout());
        constraints = new GridBagConstraints();

        constraints.insets = new Insets(5, 5, 3, 5);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;


        String[] text = {"some Text","this text is longer" };

        for (int i = 0; i < text.length; i++) {
            JButton button= new JButton(text[i], new ImageIcon(new File("icon.png").toURI().toURL()));
            button.setAlignmentX(SwingConstants.WEST);
            button.setIconTextGap(30);
            button.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 10));


            buttonPanel.add(button, constraints);
            constraints.gridy++;
        }

        frame.pack();
        frame.setVisible(true);


    }

    public static void main(String[] args){
        try {
            new test();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}