package Backend.Dice;

import GuiStuff.Settings;

import javax.swing.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class DiceDisplay extends JPanel {

    private int diceVal;
    private JButton acceptButton;

    public DiceDisplay(){
        setSize(Settings.board_width,Settings.board_height/10);

        startAnimation();

    }

    public int getDiceValue() {
        return diceVal;
    }
    public void setOKButton(JButton jb){
        acceptButton = jb;
    }
    private void startAnimation(){




        // thread that will close the dialog automatically after 5 seconds
        new Thread(() -> emulateFinal()).start();

    }

    private void emulateFinal(){

        try {
            TimeUnit.SECONDS.sleep(5);
        }catch (InterruptedException ignored){
        }

        diceVal = new Random().nextInt(1,7);
        acceptButton.doClick();
    }
}
