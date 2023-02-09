
import Backend.*;

import javax.swing.*;

import java.awt.*;
import java.util.Random;

import static GuiStuff.Settings.*;
public class Main {

    public static void main(String[] args) {

        JFrame gameJFame = new JFrame("Game");
        gameJFame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        gameJFame.setResizable(false);
        gameJFame.setUndecorated(true);
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

        board_width = size.width;
        board_height = size.height;
        fieldPerPerson = 10;  // max 20
        playerAmount = 4;  // max 20
        figureAmount = 4;  // max 25

        reload();
        gameJFame.setSize(board_width,board_height);

        gameJFame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //gameJFame.setLayout(null);

        Player[] ps = new Player[playerAmount];
        for (int i = 0; i < ps.length; i++) {
            ps[i] = new Player(i, figureAmount, new Color((int)(new Random().nextFloat()*255), (int)(new Random().nextFloat()*255), (int)(new Random().nextFloat()*255)), "Bruh");
        }

        Game g = new Game(new Board(ps));

        gameJFame.add(g);

        //gameJFame.setBackground(board_bg_color);

        gameJFame.setVisible(true);
    }
}
