package burp;

import burp.api.montoya.ui.contextmenu.ContextMenuEvent;
import burp.api.montoya.ui.contextmenu.ContextMenuItemsProvider;
import jrcet.listener.CaptchaContextMenuItemActionListener;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class MyContextMenuItemsProvider implements ContextMenuItemsProvider {



    @Override
    public List<JMenuItem> provideMenuItems(ContextMenuEvent event) {

        List<JMenuItem> jMenuItemList= new ArrayList<>();

        //设置发送验证码请求包的JMenuItem和相关操作
        JMenuItem captchaItem = new JMenuItem("Captcha");
        captchaItem.addActionListener(new CaptchaContextMenuItemActionListener(event));
        jMenuItemList.add(captchaItem);


        return jMenuItemList;
    }
}
