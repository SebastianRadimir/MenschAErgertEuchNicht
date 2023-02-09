package Backend.Dice;

import GuiStuff.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class DiceDisplay extends JPanel {

    private int diceVal;
    private JButton acceptButton;

    private DicePanel[] dss;
    private double scrollDecelerate;
    private double initSpeed;
    private double offset;
    private Timer t;
    private DiceDialog ddl;

    public DiceDisplay(){
        setSize(Settings.board_width,Settings.board_height/10);

        diceVal = new Random().nextInt(1,7);
        scrollDecelerate = (new Random().nextInt(97,99))/100.0;
        initSpeed = new Random().nextInt(1,200);

        int diceAmount = 8+((int)(initSpeed*scrollDecelerate));

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
        if (initSpeed<=0.1){

            int index = ((int)(offset)/this.getHeight())+4;
            if (index<dss.length) {
                diceVal = dss[index].getPoints();
            }
            emulateFinal();
        }
    }

    private void emulateFinal(){
        t.stop();

        t=null;
        ddl.addWindowListener(new DiceDialog.Closer());
        acceptButton.doClick();
    }

    @Override
    public void paintComponent(Graphics g){

        g.setColor(Settings.board_bg_color);

        g.fillRect(0,0,this.getWidth(),this.getHeight());

        int ySize = this.getHeight();

        int i = 0;
        for (DicePanel dp:dss) {
            dp.draw(g, (i*ySize)-(int)(offset), 0, ySize, ySize);
            i++;
        }

        g.setColor(Settings.dice_selection_color);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(6));
        g2.drawLine(ySize*4, 0, ySize*4, ySize);

    }

    public void addDialog(DiceDialog diceDialog) {
        this.ddl = diceDialog;
    }
}
