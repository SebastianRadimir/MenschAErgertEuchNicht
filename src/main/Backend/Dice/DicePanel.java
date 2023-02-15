package Backend.Dice;

import javax.swing.*;
import java.awt.*;

public class DicePanel extends JPanel {

    DiceSide ds;
    public DicePanel(DiceSide ds){
        this.ds = ds;
    }

    public DicePanel(){
        this(Backend.Dice.DiceSide.getRandomDiceSide());
    }
    public int getPoints(){
        return ds.getPoints();
    }
    public void draw(Graphics g, int xMin, int yMin, int xSize, int ySize){
        ds.draw(g, xMin, yMin, xSize, ySize);
    }
}
