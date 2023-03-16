package Backend.Dice;

import GuiStuff.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class DiceDisplay extends JPanel {

    private int diceVal;
    private
    DicePanel selectedDD = null;
    private boolean toggleDiceColor;
    private JButton acceptButton;

    private final DicePanel[] dss;
    private final double scrollDecelerate;
    private double initSpeed;
    private double offset;
    private int endTimer, index;
    private Timer t;
    private DiceDialog ddl;
    private JDialog dialog;

    public DiceDisplay(){
        toggleDiceColor = false;
        setSize(Settings.board_width,Settings.board_height/10);

        diceVal = new Random().nextInt(1,7);
        scrollDecelerate = (new Random().nextInt(955,970))/1000.0;
        initSpeed = new Random().nextInt(100,500);

        int diceAmount = 8+((int)(initSpeed));

        dss = new DicePanel[diceAmount];
        for (int i = 0; i < diceAmount; i++) {
            dss[i]=new DicePanel();
        }

        t = new Timer(10, ae -> {
            runAnimation();
            repaint();
        });
        t.start();

    }

    public int getDiceValue() {
        return diceVal;
    }
    public void setOKButton(JButton jb){
        acceptButton = jb;
    }
    private void runAnimation(){
        offset+=(initSpeed*scrollDecelerate);
        initSpeed*=scrollDecelerate;
        if (initSpeed<=0.3){
            emulateFinal();
        }
    }

    private void emulateFinal(){
        t.stop();

        t=null;

        index = ((int)(offset)/this.getHeight())+4;
        if (index<dss.length) {
            selectedDD = dss[index];
            diceVal = selectedDD.getPoints();

        }

        if (selectedDD != null) {
            endTimer = 0;
            t = new Timer(100, ae -> {
                selectAnimation();
                repaint();
            });
            t.start();
        }else {
            JOptionPane.showMessageDialog(null, "Lets just say you rolled a "+ diceVal+".");
            closeALL();
        }
    }

    private void selectAnimation(){
        toggleDiceColor = true;
        if (endTimer>=10){
            repaint();
            t.stop();
            t=null;
            closeALL();
        }
        endTimer++;
    }
    private void closeALL(){
        ddl.addWindowListener(new DiceDialog.Closer());
        acceptButton.doClick();
    }

    @Override
    public void paintComponent(Graphics g){

        if (!toggleDiceColor) {
            g.setColor(Settings.board_bg_color);
            int xSize = this.getWidth();
            int val = this.getHeight();
            g.fillRect(0, 0, xSize, val);

            int i = 0;
            for (DicePanel dp : dss) {
                dp.draw(g, (i * val) - (int) (offset), 0, val, val);
                i++;
            }

            g.setColor(Settings.dice_selection_color);
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(6));
            g2.drawLine(val * 4, 0, val * 4, val);
        }else {

            g.setColor(Settings.board_bg_color);
            int val = this.getHeight();
            g.fillRect(0,0,this.getWidth(),val);

            selectedDD.draw(g, (index * val) - (int) (offset), 0, val, val);

            g.setColor(Settings.dice_selection_color);
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(6));
            g2.drawLine(val * 4, 0, val * 4, val);

        }
    }

    public void addDialog(DiceDialog diceDialog) {
        this.ddl = diceDialog;
    }

    public void setParent(JDialog dialog) {
        this.dialog = dialog;
        this.dialog.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                while (initSpeed>=0.3) {
                    runAnimation();
                }

            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }
}
