package Backend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import static GuiStuff.Settings.*;

public class Game extends JPanel {

    private final Board board;
    private int currentPlayerIndex;

    public Game(Board board){
        currentPlayerIndex = 0;

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
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                PointerInfo a = MouseInfo.getPointerInfo();
                Point b = a.getLocation();
                int xpos = (int) b.getX();
                int ypos = (int) b.getY();
                Field f = board.getField(xpos, ypos);
                if (f==null){
                    return;
                }
                f.setFigure(board.players[currentPlayerIndex].getFigures()[0]);

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

    public int nextPlayer(){
        currentPlayerIndex = (currentPlayerIndex+1)%board.playerAmount;
        return currentPlayerIndex;
    }

    public void moveFigure(Field selectedField, int amount){
        if (!selectedField.isOccupied()){
            return;
        }
        if (!selectedField.getFigure().isRunning()) {
            board.course[(selectedField.getIndex() + amount) % (playerAmount*fieldPerPerson)].setFigure(selectedField.clearField());
        }
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
