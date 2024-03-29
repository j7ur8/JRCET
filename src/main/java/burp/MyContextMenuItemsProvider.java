package burp;

import burp.api.montoya.ui.contextmenu.ContextMenuEvent;
import burp.api.montoya.ui.contextmenu.ContextMenuItemsProvider;
import jrcet.listener.CaptchaContextMenuItemActionListener;
import jrcet.listener.PureRequestContextMenuItemActionListener;
import jrcet.listener.RScriptContextMenuItemActionListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MyContextMenuItemsProvider implements ContextMenuItemsProvider {


    @Override
    public List<Component> provideMenuItems(ContextMenuEvent event) {

        List<Component> jMenuItemList= new ArrayList<>();

        //设置发送验证码请求包的JMenuItem和相关操作
        JMenuItem captchaItem = new JMenuItem("Captcha");
        captchaItem.addActionListener(new CaptchaContextMenuItemActionListener(event));

        JMenuItem rscriptItem = new JMenuItem("RScript");
        rscriptItem.addActionListener(new RScriptContextMenuItemActionListener(event));

        JMenuItem pureRequestItem = new JMenuItem("PureRequest");
        pureRequestItem.addActionListener(new PureRequestContextMenuItemActionListener(event));

        jMenuItemList.add(captchaItem);

        jMenuItemList.add(rscriptItem);
        jMenuItemList.add(pureRequestItem);
        return jMenuItemList;
    }

}
