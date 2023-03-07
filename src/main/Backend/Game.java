package Backend;

import GuiStuff.ArrowPanel;
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
    private ArrowPanel nextPlayerBtn;
    public Game(Board board){
        this.setLayout(null);
        currentPlayerIndex = 0;
        d = new DiceGUI();
        d.enableDice(3);
        this.board = board;
        pl = new PlayerList(this.board.players, board_height/(this.board.players.length+1),board_width/6);
        pl.setPlayerToGreen(board.players[currentPlayerIndex].getPlayerName());
        this.add(pl);
        nextPlayerBtn = new ArrowPanel(0.5, board.players[currentPlayerIndex].getColor(), board_bg_color);
        this.add(nextPlayerBtn);
        nextPlayerBtn.setSize(buttonSize, buttonSize);
        nextPlayerBtn.setLocation(Settings.board_width-Settings.buttonSize, board_height-Settings.buttonSize);
        nextPlayerBtn.setVisible(true);
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

                if (f!=null){// wenn auf ein feld gedrückt wurde:
                    if (board.players[currentPlayerIndex].getHomeFigAmount() >= 1){// wenn es noch mindestens eine figur zu hause gibt:
                        if (board.course[currentPlayerIndex*fieldPerPerson].equals(f)){// wenn der spieler sein startfeld gedrückt hat:
                            if (f.isOccupied()) {//  und es eine figur auf diesen feld da ist:
                                // bewege die figur und setze den würfel zu etwas invalides
                                moveFigure(f, d.getValue());
                            }else if (d.getValue()==6){// und eine sechsa gewürfelt wurde
                                f.setFigure(board.players[currentPlayerIndex].getNextHomeFigure());
                                pl.updateFiguresAtHome(board.players[currentPlayerIndex]);
                                d.reset();
                                repaint();
                                updateUI();
                                return;
                            }
                            // wenn das start feld nicht gedrückt wwurde und das start feld lehr ist:
                        }else if (!board.course[currentPlayerIndex*fieldPerPerson].isOccupied()){
                            moveFigure(f, d.getValue());
                            return;
                        }
                    }else { // wenn keine figuren zu hause sind:
                        moveFigure(f, d.getValue());
                        return;
                    }
                }

                if (((Settings.board_width-(Settings.buttonSize*2))<xpos && xpos<Settings.board_width-Settings.buttonSize) && ((Settings.board_height-Settings.buttonSize)<ypos && ypos<Settings.board_height)) {
                    d.roll();
                    repaint();
                    updateUI();
                    return;
                }
                if (!d.canRoll() && ((Settings.board_width-Settings.buttonSize)<xpos && xpos<Settings.board_width) && ((Settings.board_height-Settings.buttonSize)<ypos && ypos<Settings.board_height)) {

                    nextPlayer();

                    d.enableDice(figureAmount == board.players[currentPlayerIndex].getHomeFigAmount()?3: 1);
                    nextPlayerBtn.setColor(board.players[currentPlayerIndex].getColor());
                    nextPlayerBtn.repaint();
                    d.reset();
                    repaint();
                    updateUI();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
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
        if (!selectedField.isOccupied() || !selectedField.getFigure().isCorrectPlayer(board.players[currentPlayerIndex])){
            return;
        }
        if (!selectedField.getFigure().isHome() && amount>=0) {// hier muss noch das mit den nahc hause felder eingebaut werden!
            board.course[((selectedField.getIndex() + amount) % (playerAmount*fieldPerPerson))].setFigure(selectedField.clearField());
            pl.updateFiguresAtHome(board.players[currentPlayerIndex]);
            d.reset();
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
