package GuiStuff;

import javax.swing.*;
import java.awt.*;

import static GuiStuff.Settings.*;
public class ExampleGUI {

    public static void main(String[] args) {



        JFrame gameJFame = new JFrame("Game");

        gameJFame.setResizable(false);
        gameJFame.setSize(board_width, board_height);

        gameJFame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gameJFame.setLayout(new BorderLayout());

        gameJFame.add(new Board(),BorderLayout.CENTER);

        gameJFame.setBackground(new Color(board_bg_color));

        //int i=0;
        //Letter[] letters = new Letter[6];
        //for (char e:"ABCDEF".toCharArray()) {
        //    letters[i] = Letter.charToLetter(e);
        //    gameJFame.add(new JLabel(letters[i].getIcon()));
        //    i++;
        //}


        gameJFame.setVisible(true);
    }
}
