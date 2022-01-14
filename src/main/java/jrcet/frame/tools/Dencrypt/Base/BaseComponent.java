package jrcet.frame.tools.Dencrypt.Base;

import jrcet.diycomponents.DiyJButton;
import jrcet.diycomponents.DiyJComponent;
import jrcet.diycomponents.DiyJLabel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;

public class BaseComponent extends DiyJComponent {

    public Integer hashmapLength=0; //此处有bug
    public JComponent defaultBaseFunctionInstancePanel;
    public ArrayList<tmpBaseFunctionInstance> baseInstanceList=new ArrayList<>();
    public JComponent blackInstance = blackInstance();
    public JPanel BasePanel = new JPanel(new GridBagLayout());

    {
        baseInstanceList.add(new tmpBaseFunctionInstance());
        defaultBaseFunctionInstancePanel = baseInstanceList.get(0).main();
    }

    public JComponent main(){

        BasePanel.setOpaque(false);
        BasePanel.setBackground(Color.WHITE);

        JScrollPane BaseScrollPane = new JScrollPane(BasePanel,VERTICAL_SCROLLBAR_AS_NEEDED,HORIZONTAL_SCROLLBAR_NEVER);
        BaseScrollPane.setBorder(null);
        BaseScrollPane.setPreferredSize(new Dimension(0,0));
        BaseScrollPane.setName("Base-1");

        BasePanel.add(defaultBaseFunctionInstancePanel, new GridBagConstraints(
                0,BasePanel.getComponentCount(),
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(10,10,10,10),
                0,0
        ));

        BasePanel.add(blackInstance, new GridBagConstraints(
                0,BasePanel.getComponentCount(),
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));

        return BaseScrollPane;

    }

    public void addTmpBaseFunctionPanel(){
        BasePanel.remove(BasePanel.getComponents().length-1);
        baseInstanceList.add(new tmpBaseFunctionInstance());
        BasePanel.add(baseInstanceList.get(hashmapLength).main(),new GridBagConstraints(
                0,BasePanel.getComponentCount(),
                1,1,
                1,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(10,10,10,10),
                0,0
        ));
        BasePanel.add(blackInstance, new GridBagConstraints(
                0,BasePanel.getComponentCount(),
                1,1,
                1,1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0,0,0,0),
                0,0
        ));
    }

     public class tmpBaseFunctionInstance {
        public final JComponent instancePanel = new JPanel(new GridBagLayout());
        public DiyJButton[][] buttonArray=new DiyJButton[2][7];
        public JTextArea dataArea = new JTextArea();

        public JComponent main(){
            instancePanel.setOpaque(true);
            instancePanel.setBackground(Color.WHITE);
            instancePanel.setPreferredSize(new Dimension(0,200));

            JPanel leftBlackPanel = new JPanel();
            leftBlackPanel.setOpaque(false);
            leftBlackPanel.setPreferredSize(new Dimension(0,0));
            instancePanel.add(leftBlackPanel,new GridBagConstraints(
                    0,0,
                    1,1,
                    0.1,1,
                    GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH,
                    new Insets(0,0,0,0),
                    0,0
            ));

            dataArea.setLineWrap(true);
            dataArea.setWrapStyleWord(true);
            dataArea.setBorder(BorderFactory.createMatteBorder(1,1,1,1,new Color(144,144,160)));
            JScrollPane dataAreaScroll = new JScrollPane(dataArea);
            dataAreaScroll.setBorder(null);
            dataAreaScroll.setPreferredSize(new Dimension(0,0));

            instancePanel.add(dataAreaScroll,new GridBagConstraints(
                    1,0,
                    1,1,
                    0.45,1,
                    GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH,
                    new Insets(5,5,5,5),
                    0,0
            ));

            JPanel menuPanel = new JPanel(new GridBagLayout());
            menuPanel.setPreferredSize(new Dimension(270,0));
            menuPanel.setOpaque(false);
            instancePanel.add(menuPanel,new GridBagConstraints(
                    2,0,
                    1,1,
                    0,1,
                    GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH,
                    new Insets(0,0,0,0),
                    0,0
            ));

            JPanel menuEncodePanel = new JPanel(new FlowLayout(FlowLayout.LEFT,0,25));
            menuEncodePanel.setOpaque(false);
            menuEncodePanel.setPreferredSize(new Dimension(0,0));
            menuPanel.add(menuEncodePanel,new GridBagConstraints(
                    0,0,
                    1,1,
                    1,0.5,
                    GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH,
                    new Insets(0,10,0,0),
                    0,0
            ));

            buttonList(menuEncodePanel,"Encode: ");

            JPanel menuDecodePanel = new JPanel(new FlowLayout(FlowLayout.LEFT,0,25));
            menuDecodePanel.setOpaque(false);
            menuDecodePanel.setPreferredSize(new Dimension(0,0));
            menuPanel.add(menuDecodePanel,new GridBagConstraints(
                    0,1,
                    1,1,
                    1,0.5,
                    GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH,
                    new Insets(0,10,0,0),
                    0,0
            ));

            buttonList(menuDecodePanel,"Decode: ");

            JPanel menuDecodeStatusPanel = new JPanel(new GridBagLayout());
            menuDecodeStatusPanel.setOpaque(false);
            menuDecodeStatusPanel.setPreferredSize(new Dimension(0,0));
            instancePanel.add(menuDecodeStatusPanel,new GridBagConstraints(
                    3,0,
                    1,1,
                    0.10,1,
                    GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH,
                    new Insets(0,0,0,0),
                    0,0
            ));

            DiyJButton pauseButton = new DiyJButton("Pause");
            menuDecodeStatusPanel.add(pauseButton);

            JPanel rightBlackPanel = new JPanel();
            rightBlackPanel.setPreferredSize(new Dimension(0,0));
            rightBlackPanel.setOpaque(false);
            instancePanel.add(rightBlackPanel,new GridBagConstraints(
                    4,0,
                    1,1,
                    0.1,0,
                    GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH,
                    new Insets(0,0,0,0),
                    0,0
            ));
            reSetName(instancePanel);
            hashmapLength++;
            return instancePanel;
        }

        private void reSetName(JComponent target){
            Component[] components = target.getComponents();
            if(components.length!=0)
                for(Component i : components){
                    reSetName((JComponent) i);
            }
            target.setName(String.valueOf(hashmapLength));

        }

        private void buttonList(JPanel buttonListPanel, String type) {
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
    }

    private JComponent blackInstance(){
        JPanel instancePanel = new JPanel();
        instancePanel.setOpaque(false);
        instancePanel.setBackground(Color.WHITE);
        instancePanel.setPreferredSize(new Dimension(0,0));
        return instancePanel;
    }

}
