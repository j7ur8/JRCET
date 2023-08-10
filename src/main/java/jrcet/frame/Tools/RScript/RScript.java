package jrcet.frame.Tools.RScript;

import jrcet.diycomponents.DiyJTextAreaScrollPane;
import jrcet.help.Helper;


import javax.swing.*;



public class RScript {
    public static JFrame RScriptFrame =null;

    public static DiyJTextAreaScrollPane RScriptEditor = Helper.createDiyJTextAreaScrollPane("RScriptArea");

    public static JPanel RScriptPanel = null;

    public static JButton RScriptButton = null;

    public RScript() {
    }
}
