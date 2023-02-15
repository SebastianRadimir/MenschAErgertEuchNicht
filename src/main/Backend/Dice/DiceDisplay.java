package Backend.Dice;

import GuiStuff.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class DiceDisplay extends JPanel {

    private int diceVal;
    private
    DicePanel selectedDD = null;
    private boolean toggleDiceColor;
    private JButton acceptButton;

    private DicePanel[] dss;
    private double scrollDecelerate;
    private double initSpeed;
    private double offset;
    private int endTimer, index;
    private Timer t;
    private DiceDialog ddl;

    public DiceDisplay(){
        toggleDiceColor = false;
        setSize(Settings.board_width,Settings.board_height/10);

        diceVal = new Random().nextInt(1,7);
        scrollDecelerate = (new Random().nextInt(985,990))/1000.0;
        initSpeed = new Random().nextInt(100,500);

        int diceAmount = 8+((int)(initSpeed));

        dss = new DicePanel[diceAmount];
        for (int i = 0; i < diceAmount; i++) {
            dss[i]=new DicePanel();
        }

        t = new Timer(10, new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                runAnimation();
                repaint();
            }
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
            t = new Timer(100, new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    selectAnimation();
                    repaint();
                }
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
            g.fillRect(0, 0, this.getWidth(), this.getHeight());

            int ySize = this.getHeight();

            int i = 0;
            for (DicePanel dp : dss) {
                dp.draw(g, (i * ySize) - (int) (offset), 0, ySize, ySize);
                i++;
            }

            g.setColor(Settings.dice_selection_color);
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(6));
            g2.drawLine(ySize * 4, 0, ySize * 4, ySize);
        }else {

            g.setColor(Settings.board_bg_color);
            int xSize = this.getWidth();
            int ySize = this.getHeight();
            g.fillRect(0,0,this.getWidth(),ySize);

            selectedDD.draw(g, (index * ySize) - (int) (offset), 0, ySize, ySize);

            g.setColor(Settings.dice_selection_color);
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(6));
            g2.drawLine(ySize * 4, 0, ySize * 4, ySize);

        }
    }

    public void addDialog(DiceDialog diceDialog) {
        this.ddl = diceDialog;
    }
}
