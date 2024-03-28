//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package diycomponents;



import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

public class DiyVariablePanel extends JPanel {
    private final Component a;
    private final String aTitle;
    private final Component b;
    private final String bTitle;
    private Component wrapper;
    private View view;

    public DiyVariablePanel(Component a, String aTitle, Component b, String bTitle, View defaultView) {

        this.a = a;
        this.aTitle = aTitle;
        this.b = b;
        this.bTitle = bTitle;
        this.view=defaultView;
        this.setLayout(new BorderLayout());
        this.setView(this.view);
    }


    public View getView() {
        return this.view;
    }

    public void setView(View view) {
        if (view == null) {
            view = DiyVariablePanel.View.VERTICAL;
        }

        switch (view) {
            case HORIZONTAL, VERTICAL -> {
                this.wrapper = new JSplitPane();
                ((JSplitPane) this.wrapper).setLeftComponent(this.a);
                ((JSplitPane) this.wrapper).setRightComponent(this.b);
                if (view == View.HORIZONTAL) {
                    ((JSplitPane) this.wrapper).setOrientation(1);
                } else {
                    ((JSplitPane) this.wrapper).setOrientation(0);
                }
                ((JSplitPane) this.wrapper).setResizeWeight(0.5);
            }
            case TABS -> {
                this.wrapper = new JTabbedPane();
                ((JTabbedPane) this.wrapper).addTab(this.aTitle, this.a);
                ((JTabbedPane) this.wrapper).addTab(this.bTitle, this.b);
            }
        }

        this.removeAll();
        this.add(this.wrapper, "Center");
        this.revalidate();
        this.repaint();
        this.view = view;


    }

    public enum View {
        HORIZONTAL,
        VERTICAL,
        TABS;

        View() {
        }
    }
}
