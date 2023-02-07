package Backend.Dice;

import GuiStuff.Settings;

import javax.swing.*;
import java.util.Random;

public class DiceDisplay extends JPanel {

    private int diceVal;

    public DiceDisplay(){
        setSize(Settings.board_width,Settings.board_height/10);
        diceVal = new Random().nextInt(1,7);
    }

    public int getDiceValue() {
        return diceVal;
    }
}
