package Backend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import static GuiStuff.Settings.*;

public class Game extends JPanel {

    private final Board board;

    public Game(Board board){

        this.board = board;

        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                rep();
            }
        });

    }

    private void rep(){
        this.repaint();
    }
    @Override
    public void paintComponent(Graphics g) {

        PointerInfo a = MouseInfo.getPointerInfo();
        Point b = a.getLocation();
        int xpos = (int) b.getX();
        int ypos = (int) b.getY();

        g.setColor(board_bg_color);
        g.fillRect(0, 0, board_width + 10, board_height + 10);

        this.board.draw(g, xpos, ypos);

    }

}
