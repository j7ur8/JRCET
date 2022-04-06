package jrcet.frame.tools.Dencrypt.Base;

/**
 * 实现Base页面组件的类
 */

import jrcet.diycomponents.DiyJAddLabel;
import jrcet.diycomponents.DiyJButton;
import jrcet.diycomponents.DiyJComponent;
import jrcet.diycomponents.DiyJLabel;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;

public class BaseComponent extends DiyJComponent {

    public final JComponent BaseMainPanel = BaseMainPanel();
    public final JComponent DefaultBaseMainPanel = BaseMainPanel;

    public BaseComponent(){

    }

    public JComponent main(){

        JPanel BaseComponentPanel = new JPanel(new GridBagLayout());
        BaseComponentPanel.setName("BaseComponentPanel");
        BaseComponentPanel.setOpaque(false);
        BaseComponentPanel.setPreferredSize(new Dimension(0,0));

        BaseComponentPanel.add(BaseTagTabPanel(),new GridBagConstraints(
                0,0,
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        BaseComponentPanel.add(DefaultBaseMainPanel,new GridBagConstraints(
                0,1,
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return BaseComponentPanel;
    }

    public JComponent BaseTagTabPanel(){
        JPanel BaseTagTabPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,0,0));
        BaseTagTabPanel.setName("BaseTagTabPanel");
        BaseTagTabPanel.setOpaque(false);
        BaseTagTabPanel.setBorder(BorderFactory.createMatteBorder(0,0,0,0,new Color(203,208,209)));

        DiyJAddLabel BaseTagDefaultLabel = new DiyJAddLabel("1",true);
        BaseTagDefaultLabel.setName("BaseTagDefaultLabel");
        BaseTagTabPanel.add(BaseTagDefaultLabel);
        BaseTagDefaultLabel.setMapPanel(BaseMainPanel);

        DiyJAddLabel BaseTabAddLabel = new DiyJAddLabel("···",false);
        BaseTabAddLabel.setName("BaseTabAddLabel");
        BaseTagTabPanel.add(BaseTabAddLabel);

        return BaseTagTabPanel;
    }

    public JComponent BaseMainPanel(){
        JComponent BaseMainPanel = new JPanel(new GridBagLayout());
        BaseMainPanel.setName("BaseMainPanel");
        BaseMainPanel.setOpaque(false);
        BaseMainPanel.setBackground(Color.WHITE);

        JScrollPane BaseMainScrollPane = new JScrollPane(BaseMainPanel,VERTICAL_SCROLLBAR_AS_NEEDED,HORIZONTAL_SCROLLBAR_NEVER);
        BaseMainScrollPane.setName("BaseMainScrollPane");
        BaseMainScrollPane.setBorder(null);
        BaseMainScrollPane.setPreferredSize(new Dimension(0,0));

        BaseMainPanel.add(BaseMainContentPanel(), new GridBagConstraints(
                0, BaseMainPanel.getComponentCount(),
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(10,10,10,10),
                0,0
        ));

        BaseMainPanel.add(BaseMainBlackPanel(), new GridBagConstraints(
                0, BaseMainPanel.getComponentCount(),
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return BaseMainScrollPane;
    }

    public JComponent BaseMainContentPanel(){
        JComponent BaseMainContentPanel = new JPanel(new GridBagLayout());
        BaseMainContentPanel.setName("BaseMainContentPanel");
        BaseMainContentPanel.setOpaque(true);
        BaseMainContentPanel.setBackground(Color.WHITE);
        BaseMainContentPanel.setPreferredSize(new Dimension(0,200));

        BaseMainContentPanel.add(BaseMainContentBlackPanel(),new GridBagConstraints(
                0,0,
                1,1,
                0.1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        BaseMainContentPanel.add(BaseMainContentTextareaPanel(),new GridBagConstraints(
                1,0,
                1,1,
                0.45,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(5,5,5,5),
                0,0
        ));

        BaseMainContentPanel.add(BaseMainContentMenuPanel(),new GridBagConstraints(
                2,0,
                1,1,
                0,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        BaseMainContentPanel.add( BaseMainContentStatusPanel(),new GridBagConstraints(
                3,0,
                1,1,
                0.10,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        BaseMainContentPanel.add(BaseMainContentBlackPanel(),new GridBagConstraints(
                4,0,
                1,1,
                0.1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return BaseMainContentPanel;
    }

    private JComponent BaseMainContentTextareaPanel(){
        JTextArea BaseMainContentTextAreaPanel = new JTextArea();
        BaseMainContentTextAreaPanel.setName("BaseMainContentTextAreaPanel");
        BaseMainContentTextAreaPanel.setLineWrap(true);
        BaseMainContentTextAreaPanel.setWrapStyleWord(true);
        BaseMainContentTextAreaPanel.setBorder(BorderFactory.createMatteBorder(1,1,1,1,new Color(144,144,160)));

        JScrollPane BaseMainContentTextAreaScrollPanel = new JScrollPane(BaseMainContentTextAreaPanel);
        BaseMainContentTextAreaScrollPanel.setName("BaseMainContentTextAreaScrollPanel");
        BaseMainContentTextAreaScrollPanel.setBorder(null);
        BaseMainContentTextAreaScrollPanel.setPreferredSize(new Dimension(0,0));

        return BaseMainContentTextAreaScrollPanel;
    }


    private JComponent BaseMainContentMenuPanel(){

        JPanel BaseMainContentMenuPanel = new JPanel(new GridBagLayout());
        BaseMainContentMenuPanel.setName("BaseMainContentMenuPanel");
        BaseMainContentMenuPanel.setPreferredSize(new Dimension(270,0));
        BaseMainContentMenuPanel.setOpaque(false);

        JPanel BaseMainContentMenuEncodePanel = new JPanel(new FlowLayout(FlowLayout.LEFT,0,25));
        BaseMainContentMenuEncodePanel.setName("BaseMainContentMenuEncodePanel");
        BaseMainContentMenuEncodePanel.setOpaque(false);
        BaseMainContentMenuEncodePanel.setPreferredSize(new Dimension(0,0));
        BaseMainContentMenuAddButton(BaseMainContentMenuEncodePanel,"Encode: ");

        BaseMainContentMenuPanel.add(BaseMainContentMenuEncodePanel,new GridBagConstraints(
                0,0,
                1,1,
                1,0.5,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,10,0,0),
                0,0
        ));

        JPanel BaseMainContentMenuDecodePanel = new JPanel(new FlowLayout(FlowLayout.LEFT,0,25));
        BaseMainContentMenuDecodePanel.setName("BaseMainContentMenuDecodePanel");
        BaseMainContentMenuDecodePanel.setOpaque(false);
        BaseMainContentMenuDecodePanel.setPreferredSize(new Dimension(0,0));
        BaseMainContentMenuAddButton(BaseMainContentMenuDecodePanel,"Decode: ");
        BaseMainContentMenuPanel.add(BaseMainContentMenuDecodePanel,new GridBagConstraints(
                0,1,
                1,1,
                1,0.5,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,10,0,0),
                0,0
        ));

        return BaseMainContentMenuPanel;
    }

    //Pause、Continue按钮的设置
    private JComponent BaseMainContentStatusPanel(){

        JPanel BaseMainContentStatusPanel = new JPanel(new GridBagLayout());
        BaseMainContentStatusPanel.setName("BaseMainContentStatusPanel");
        BaseMainContentStatusPanel.setOpaque(false);
        BaseMainContentStatusPanel.setPreferredSize(new Dimension(0,0));

        DiyJButton BaseMainContentStatusPauseButton = new DiyJButton("Pause");
        BaseMainContentStatusPauseButton.setName("BaseMainContentStatusPauseButton");
        BaseMainContentStatusPanel.add(BaseMainContentStatusPauseButton);

        return BaseMainContentStatusPanel;
    }

    //设置按钮的格式
    private void BaseMainContentMenuAddButton(JPanel buttonListPanel, String type) {

        DiyJButton[][] buttonArray=new DiyJButton[2][7];
        DiyJLabel label = new DiyJLabel(type);
        int iType = Objects.equals(type, "Encode: ") ?0:1;
        label.setOpaque(false);
        label.setPreferredSize(new Dimension(100,50));
        buttonListPanel.add(label);

        String[] baseFamily ={"B16","B32","B58","B64","B85","B91","B92"};

        for(int i=0;i<baseFamily.length;i++){
            DiyJButton iButton = new DiyJButton(baseFamily[i],true);
            buttonListPanel.add(iButton);
            buttonArray[iType][i]=iButton;
        }
    }

    //BaseMainContentPanel的BlackPanel
    private JComponent BaseMainContentBlackPanel(){
        JPanel BaseMainContentBlackPanel = new JPanel();
        BaseMainContentBlackPanel.setName("BaseMainContentBlackPanel");
        BaseMainContentBlackPanel.setPreferredSize(new Dimension(0,0));
        BaseMainContentBlackPanel.setOpaque(false);
        return BaseMainContentBlackPanel;
    }

    //BasePanel的BlackPanel
    private JComponent BaseMainBlackPanel(){
        JPanel BaseMainBlackPanel = new JPanel();
        BaseMainBlackPanel.setName("BaseMainBlackPanel");
        BaseMainBlackPanel.setOpaque(false);
        BaseMainBlackPanel.setBackground(Color.WHITE);
        BaseMainBlackPanel.setPreferredSize(new Dimension(0,0));
        return BaseMainBlackPanel;
    }

}
