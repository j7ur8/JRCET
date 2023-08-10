package jrcet.diycomponents;

import jrcet.frame.Dencrypt.Hex.HexComponent;
import jrcet.frame.Dencrypt.Jwt.JwtComponent;
import jrcet.frame.HText.Case.CaseComponent;
import jrcet.frame.HText.Format.FormatComponent;
import jrcet.frame.HText.IPUnit.IPUnitComponent;
import jrcet.frame.HText.Len.LenComponent;
import jrcet.frame.HText.Regex.RegexComponent;
import jrcet.help.Helper;
import jrcet.frame.Dencrypt.Aes.AesComponent;
import jrcet.frame.Dencrypt.Ascii.AsciiComponent;
import jrcet.frame.Dencrypt.Base.BaseComponent;
import jrcet.frame.Dencrypt.Rsa.RsaComponent;
import jrcet.frame.Dencrypt.Unicode.UnicodeComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Objects;

import static jrcet.frame.Setting.Setting.orange;
import static jrcet.frame.Setting.Setting.gray;

public class DiyJAddLabel extends JLabel implements MouseListener {

    private JComponent mapPanel = null;

    public DiyJAddLabel(String labelName) {
        setText(labelName);
        setBackground(Color.WHITE);
        setHorizontalAlignment(JLabel.CENTER);
        setPreferredSize(new Dimension(50,15));
        setFont(new Font("微软雅黑", Font.PLAIN,12));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(1,0,0,0),
                BorderFactory.createMatteBorder(0,0,1,0, gray)
        ));

        addMouseListener(this);
    }

    public DiyJAddLabel(String labelName, boolean flag){
        this(labelName);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(2,0,0,0),
                BorderFactory.createMatteBorder(0,0,2,0, orange)
        ));
    }

    public void setPanel(JComponent targetPanel){
        mapPanel = targetPanel;
    }

    public JComponent getPanel(){

        return mapPanel!=null?mapPanel:new JPanel();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {


    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}