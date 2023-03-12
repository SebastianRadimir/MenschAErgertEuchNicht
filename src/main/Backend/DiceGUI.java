package Backend;

import Backend.Dice.DiceDialogManager;
import Backend.Dice.DicePanel;
import Backend.Dice.DiceSide;
import GuiStuff.Settings;

import javax.swing.*;
import java.awt.*;

public class DiceGUI extends JPanel {

    private int canRoll;
    private final DicePanel dp;
    private int value = -1;

    public DiceGUI(){
        dp = new DicePanel(DiceSide.getRandomDiceSide());
        canRoll = 1;
    }

    public void enableDice(int rollAmount){
        canRoll = rollAmount;
    }

    public boolean canRoll(){
        return canRoll>=1;
    }

    public void roll(){
        //dp.setSide(DiceSide.getRandomDiceSide());
        if (canRoll()) {
            value = DiceDialogManager.showDialog();
            canRoll =  value == 6?1: canRoll - 1;
            dp.setSide(DiceSide.getSideByVal(value));
        }

    }
    @Override
    public void paintComponent(Graphics g){
        dp.draw(g, Settings.board_width-(Settings.buttonSize*2),Settings.board_height-Settings.buttonSize,Settings.buttonSize,Settings.buttonSize);
    }
    public int getValue(){
        return value;
    }

    public void reset() {
        value = -1;
    }
}
