package GuiStuff;

import javax.swing.*;

import java.awt.*;

import static GuiStuff.Settings.*;
public class ExampleGUI {

    public static void main(String[] args) {

        JFrame gameJFame = new JFrame("Game");
        gameJFame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        gameJFame.setResizable(false);
        gameJFame.setUndecorated(true);
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

        board_width = size.width;
        board_height = size.height;

        gameJFame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gameJFame.setLayout(null);

        gameJFame.add(new Board(4,4));

        gameJFame.setBackground(board_bg_color);



        gameJFame.setVisible(true);
    }
}
