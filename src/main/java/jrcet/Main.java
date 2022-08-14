package jrcet;

import jrcet.frame.Jrcet;
import jrcet.frame.asset.Asset;
import jrcet.frame.asset.AssetComponent;
import jrcet.frame.tools.Scanner.Fastjson.FastjsonComponent;
import jrcet.help.Helper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

import static jrcet.frame.asset.AssetComponent.AssetComponentPanel;

public class Main {

    public static ArrayList<String> JrcetComponentList =new ArrayList<>();
    public static JComponent JrcetComponentPanel=Jrcet.JrcetComponentPanel;

    public static void main(String[] args) {
        
        JFrame JrcetFrame = new JFrame("J7ur8's Remote Code Execute Tools");

        //setContentPane需放在前面，不然需要更改界面尺寸才会显示。

        JrcetFrame.setContentPane(new Jrcet().main());

        JrcetFrame.setResizable(true);
        JrcetFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JrcetFrame.setSize(1200, 1000);
        centerInScreen(JrcetFrame);
        JrcetFrame.setVisible(true);

        Asset.registerHotKey();
        Helper.mysqlInstance = Helper.getJDBC();
        JComponent AssetMainResultUnitPanel = Helper.getComponent(AssetComponentPanel,"AssetMainResultUnitPanel"); assert AssetMainResultUnitPanel!=null;
        Asset.initResultUnitPanel(AssetMainResultUnitPanel,Asset.searchFromAsset(0,Asset.dataNumber));
        AssetMainResultUnitPanel.updateUI();
//        Helper.travelComponent(Main.JrcetPanel);
//
//        for(String i :Helper.treeComponent(JrcetComponentList)){
//            System.out.println(i);
//        }
    }

    public static void centerInScreen(Window win) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = ( screenSize.width - win.getWidth())/2;
        int y = ( screenSize.height - win.getHeight())/2;
        win.setLocation(x,  y);
    }

}
