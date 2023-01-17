package Backend;

import javax.swing.*;
import java.awt.*;

import static GuiStuff.Settings.*;

public class Game extends JPanel {

    private Board board;

    private int[] courseLineArrX;
    private int[] courseLineArrY;


    public Game(Board board){

        this.board = board;


    }



    @Override
    public void paintComponent(Graphics g) {

        g.setColor(board_bg_color);
        g.fillRect(0, 0, board_width + 10, board_height + 10);

        this.board.draw(g);



    }

}
