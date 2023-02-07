package Backend.Dice;

import GuiStuff.Settings;

import javax.accessibility.Accessible;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;
import java.util.Locale;

import static javax.swing.JOptionPane.getRootFrame;

public class DiceDialogManager extends JComponent implements Accessible {
    public static int showDialog() throws HeadlessException {

        final DiceDisplay pane = new DiceDisplay();

        DiceValueTracker ok = new DiceValueTracker(pane);
        JDialog dialog = createDialog("DICE", pane, ok);

        dialog.addComponentListener(new DisposeOnClose());

        dialog.show();

        return ok.getDiceVal();
    }

    private static Window getWindowForComponent(Component parentComponent)
            throws HeadlessException {
        if (parentComponent == null)
            return getRootFrame();
        if (parentComponent instanceof Frame || parentComponent instanceof Dialog)
            return (Window)parentComponent;
        return getWindowForComponent(parentComponent.getParent());
    }
    private static JDialog createDialog(String title, DiceDisplay chooserPane, ActionListener okListener) throws HeadlessException {

        Window window = getWindowForComponent(null);
        DiceDialog dialog;
        if (window instanceof Frame) {
            dialog = new DiceDialog((Frame)window, title, true, chooserPane,
                    okListener, null);
        } else {
            dialog = new DiceDialog((Dialog)window, title, true, chooserPane,
                    okListener, null);
        }
        dialog.getAccessibleContext().setAccessibleDescription(title);
        dialog.setSize(Settings.board_width+10,Settings.board_height/4);
        dialog.setLocation(-5,Settings.board_height/4);
        return dialog;
    }
}
class DisposeOnClose extends ComponentAdapter implements Serializable{
    public void componentHidden(ComponentEvent e) {
        Window w = (Window)e.getComponent();
        w.dispose();
    }
}
class DiceDialog extends JDialog {
    private int initialValue;
    private DiceDisplay chooserPane;
    private JButton cancelButton;

    public DiceDialog(Dialog owner, String title, boolean modal,
                      DiceDisplay chooserPane,
                      ActionListener okListener, ActionListener cancelListener)
            throws HeadlessException {
        super(owner, title, modal);
        initColorChooserDialog(chooserPane, okListener, cancelListener);
    }

    public DiceDialog(Frame owner, String title, boolean modal,
                      DiceDisplay chooserPane,
                      ActionListener okListener, ActionListener cancelListener)
            throws HeadlessException {
        super(owner, title, modal);
        initColorChooserDialog(chooserPane, okListener, cancelListener);
    }

    protected void initColorChooserDialog(DiceDisplay chooserPane,
                                          ActionListener okListener, ActionListener cancelListener) {
        //setResizable(false);

        this.chooserPane = chooserPane;

        Locale locale = getLocale();
        String okString = UIManager.getString("ColorChooser.okText", locale);
        String cancelString = UIManager.getString("ColorChooser.cancelText", locale);

        Container contentPane = getContentPane();
        contentPane.add(this.chooserPane);

        JButton okButton = new JButton(okString);
        getRootPane().setDefaultButton(okButton);
        okButton.getAccessibleContext().setAccessibleDescription(okString);
        okButton.setActionCommand("OK");
        okButton.setForeground(Settings.text_color);
        okButton.addActionListener(new ActionListener() {
            @SuppressWarnings("deprecation")
            public void actionPerformed(ActionEvent e) {
                hide();
            }
        });
        if (okListener != null) {
            okButton.addActionListener(okListener);
        }
        this.chooserPane.setOKButton(okButton);

        cancelButton = new JButton(cancelString);
        cancelButton.getAccessibleContext().setAccessibleDescription(cancelString);
        cancelButton.setForeground(Settings.text_color);
        KeyStroke cancelKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        InputMap inputMap = cancelButton.getInputMap(JComponent.
                WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = cancelButton.getActionMap();
        if (inputMap != null && actionMap != null) {
            inputMap.put(cancelKeyStroke, "cancel");
            //actionMap.put("cancel", cancelKeyAction);
        }
        // end esc handling

        cancelButton.setActionCommand("cancel");
        cancelButton.addActionListener(new ActionListener() {
            @SuppressWarnings("deprecation")
            public void actionPerformed(ActionEvent e) {
                hide();
            }
        });
        if (cancelListener != null) {
            cancelButton.addActionListener(cancelListener);
        }
        //buttonPane.add(cancelButton);

        if (JDialog.isDefaultLookAndFeelDecorated()) {
            boolean supportsWindowDecorations =
                    UIManager.getLookAndFeel().getSupportsWindowDecorations();
            if (supportsWindowDecorations) {
                getRootPane().setWindowDecorationStyle(JRootPane.COLOR_CHOOSER_DIALOG);
            }
        }
        pack();

        this.addWindowListener(new DiceDialog.Closer());

    }

    @SuppressWarnings("deprecation")
    public void show() {
        initialValue = chooserPane.getDiceValue();
        super.show();
    }

    @SuppressWarnings("serial") // JDK-implementation class
    class Closer extends WindowAdapter implements Serializable{
        @SuppressWarnings("deprecation")
        public void windowClosing(WindowEvent e) {
            cancelButton.doClick(0);
            Window w = e.getWindow();
            w.hide();
        }
    }
}
class DiceValueTracker implements ActionListener, Serializable {
    DiceDisplay chooser;
    Integer diceValue;
    int fallback;
    public DiceValueTracker(DiceDisplay c) {
        chooser = c;
        fallback = -1;
    }

    public void actionPerformed(ActionEvent e) {
        diceValue = chooser.getDiceValue();
    }

    public int getDiceVal() {
        if (diceValue == null){
            return fallback;
        }
        return chooser.getDiceValue();
    }
}
