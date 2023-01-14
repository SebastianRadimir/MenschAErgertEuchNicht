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



        gameJFame.setVisible(true);
    }
}
