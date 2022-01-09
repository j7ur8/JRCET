package jrcet.diycomponents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DiyJLabel extends JLabel  {



    public DiyJLabel(String labelName){
        setLabelProperty(labelName);
    }




    private void setLabelProperty(String labelName){
        setOpaque(true);
        setText(labelName);
        setBackground(Color.WHITE);
        setHorizontalAlignment(JLabel.CENTER);
        setFont(new Font("微软雅黑",0,14));
        setPreferredSize(new Dimension(80,27));
    }





}
