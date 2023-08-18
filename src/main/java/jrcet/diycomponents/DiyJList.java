package jrcet.diycomponents;

import jrcet.frame.Jrcet;
import jrcet.frame.Setting.Setting;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class DiyJList extends JScrollPane {


    private final JList<String> Jlist;

    private final DefaultListModel<String> JlistModel;
    public DiyJList(){
        super(new JList<String>());

        setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        Jlist = (JList<String>) getViewport().getView();
        JlistModel = new DefaultListModel<>();
        Jlist.setModel(JlistModel);
    }


    public void addString(String element){
        JlistModel.addElement(element);
    }

    public void addString(Integer pos, String element){
        JlistModel.add(pos, element);
    }

    public void delString(String element){
        JlistModel.removeElement(element);
    }

    public void delString(Integer pos){
        JlistModel.remove(pos);
    }

    public void removeAllString(){
        JlistModel.removeAllElements();
    }

    public void setTitle(String title){
        Jlist.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1,0,0,0, Setting.gray),title,TitledBorder.CENTER,TitledBorder.CENTER));
    }

    public JList<?> getJlist() {
        return Jlist;
    }

    public static void main(String[] args) {

        JFrame JrcetFrame = new JFrame("J7ur8's Remote Code Execute Tools");

        //setContentPane需放在前面，不然需要更改界面尺寸才会显示。
        DiyJList s = new DiyJList();

        s.addString("c");
        s.addString("d");
        JPanel a = new JPanel();
        a.setSize(new Dimension(100,100));

        a.add(s);
        JrcetFrame.setContentPane(a);
        JrcetFrame.setResizable(true);
        JrcetFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JrcetFrame.setSize(1200, 1000);

        JrcetFrame.setVisible(true);
        System.out.println(new DiyJList().getJlist());
    }
}
