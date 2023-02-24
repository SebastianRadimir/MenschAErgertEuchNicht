package Backend;

import GuiStuff.PlayerList;
import GuiStuff.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import static GuiStuff.Settings.*;

public class Game extends JPanel {

    private final DiceGUI d;
    private final Board board;
    private int currentPlayerIndex;
    private PlayerList pl;
    public Game(Board board){
        this.setLayout(null);
        currentPlayerIndex = 0;
        d = new DiceGUI();
        d.enableDice(3);
        this.board = board;
        pl = new PlayerList(this.board.players, board_height/(this.board.players.length+1),board_width/6);
        pl.setPlayerToGreen(board.players[currentPlayerIndex].getPlayerName());
        this.add(pl);
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
                Point b = MouseInfo.getPointerInfo().getLocation();
                int xpos = (int) b.getX();
                int ypos = (int) b.getY();
                Field f = board.getField(xpos, ypos);
                if (f!=null){

                    if (d.getValue()==6 && board.course[currentPlayerIndex*fieldPerPerson].equals(f) && !f.isOccupied() && board.players[currentPlayerIndex].getHomeFigure() != null) {
                        f.setFigure(board.players[currentPlayerIndex].getHomeFigure());

                        getGame().remove(pl);

                        pl = new PlayerList(board.players, board_height/(board.players.length+1),board_width/6);
                        pl.setPlayerToGreen(board.players[currentPlayerIndex].getPlayerName());
                        getGame().add(pl);
                        d.reset();

                        repaint();
                        updateUI();
                    }else {
                        if (d.getValue() != -1 && f.isOccupied()) {
                            moveFigure(f,d.getValue());
                            d.reset();
                            repaint();
                            updateUI();
                        }
                    }
                    return;
                }

                if (((Settings.board_width-(Settings.buttonSize*2))<xpos && xpos<Settings.board_width-Settings.buttonSize) && ((Settings.board_height-Settings.buttonSize)<ypos && ypos<Settings.board_height)) {
                    d.roll();
                    repaint();
                    updateUI();
                }
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

    private Game getGame(){
        return this;
    }
    public int nextPlayer(){
        currentPlayerIndex = (currentPlayerIndex+1)%board.playerAmount;
        pl.setPlayerToGreen(board.players[currentPlayerIndex].getPlayerName());
        return currentPlayerIndex;
    }

    public void moveFigure(Field selectedField, int amount){
        if (!selectedField.isOccupied()){
            return;
        }
        if (!selectedField.getFigure().isRunning()) {
            board.course[((selectedField.getIndex() + amount) % (playerAmount*fieldPerPerson))].setFigure(selectedField.clearField());
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

        d.paintComponent(g);


    }

}
