package Backend;

import Backend.Dice.DiceDialogManager;
import Backend.Dice.DicePanel;
import Backend.Dice.DiceSide;
import GuiStuff.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DiceGUI extends JPanel {

    private int canRoll;
    private DicePanel dp;
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
        if (canRoll>=1) {
            value = DiceDialogManager.showDialog();
            dp = new DicePanel(DiceSide.getRandomDiceSide());
            canRoll =  value == 6?1: canRoll - 1; // if (value == 6 ) => 1 mal noch else => canRoll-1 mal rollen
            dp.setSide(DiceSide.getSideByVal(value));
        }

    }
    @Override
    public void paintComponent(Graphics g){

            dp.draw(g, Settings.board_width-Settings.buttonSize,Settings.board_height-Settings.buttonSize,Settings.buttonSize,Settings.buttonSize);


    }
    public int getValue(){
        return value;
    }
}
