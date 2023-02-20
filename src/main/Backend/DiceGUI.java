package Backend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DiceGUI extends JPanel {

    private int canRoll;
    private int value = -1;

    public DiceGUI(){
        canRoll = 1;
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                roll();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });



    }

    public void enableDice(int rollAmount){
        canRoll = rollAmount;
    }

    public boolean canRoll(){
        return canRoll>=1;
    }

    public void roll(){
        if (canRoll>=1) {
            // set value to dice.show() thingy
            // value = ...;
            canRoll =  value == 6?1: canRoll - 1; // if (value == 6 ) => 1 mal noch else => canRoll-1 mal rollen
        }

    }
    @Override
    public void paintComponent(Graphics g){

        if (canRoll>=1){

            // draw a dice side or someting lol


        }

    }
    public int getValue(){
        return value;
    }
}
