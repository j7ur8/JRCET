package jrcet.help;

import javax.swing.*;
import java.awt.*;

import static jrcet.frame.Setting.Setting.gray;
import static jrcet.frame.Setting.Setting.orange;

public class Decorate {

    public static void setAddLabel(JLabel jLabel){
        jLabel.setBackground(Color.WHITE);
        jLabel.setHorizontalAlignment(JLabel.CENTER);
        jLabel.setPreferredSize(new Dimension(50,15));
        jLabel.setFont(new Font("微软雅黑", Font.PLAIN,12));
        jLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(1,0,0,0),
                BorderFactory.createMatteBorder(0,0,1,0, gray)
        ));
    }

    public static void setAddLabel(JLabel jLabel, boolean isDefault){
        setAddLabel(jLabel);
        if(isDefault){
            jLabel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createEmptyBorder(2,0,0,0),
                    BorderFactory.createMatteBorder(0,0,2,0, orange)
            ));
        }
    }



}
