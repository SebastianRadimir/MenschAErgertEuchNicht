package Backend;

import GuiStuff.ArrowPanel;
import GuiStuff.PlayerList;
import GuiStuff.Settings;
import GuiStuff.Winscreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import static GuiStuff.Settings.*;

public class Game extends JPanel {

    private Timer t;
    private final DiceGUI d;
    private final Board board;
    private int currentPlayerIndex;
    private PlayerList pl;
    private ArrowPanel nextPlayerBtn;
    private Winscreen ws;
    public Game(Board board){
        ws = null;
        this.setLayout(null);
        currentPlayerIndex = 0;
        d = new DiceGUI();
        d.enableDice(3);
        this.board = board;
        pl = new PlayerList(this.board.players, board_height/(this.board.players.length*2),board_width/7);
        pl.setLocation(board_width-(board_width/7),0);
        pl.setPlayerToGreen(board.players[currentPlayerIndex].getPlayerName());
        this.add(pl);
        nextPlayerBtn = new ArrowPanel(0.9, board.players[currentPlayerIndex].getColor(), board_bg_color, false);
        this.add(nextPlayerBtn);
        nextPlayerBtn.setSize(buttonSize, buttonSize);
        nextPlayerBtn.setLocation(Settings.board_width-Settings.buttonSize, board_height-Settings.buttonSize);
        nextPlayerBtn.setVisible(true);
        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                rep();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                rep();
            }
        });
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {
                if (ws != null){
                    return;
                }
                Point b = MouseInfo.getPointerInfo().getLocation();
                int xpos = (int) b.getX();
                int ypos = (int) b.getY();
                Field f = board.getField(xpos, ypos);

                if (f!=null) {
                    if (board.players[currentPlayerIndex].getHomeFigAmount() >= 1 && !board.course[currentPlayerIndex * fieldPerPerson].equals(f) && board.course[currentPlayerIndex * fieldPerPerson].isOccupied() && board.course[currentPlayerIndex * fieldPerPerson].getFigure().isSamePlayer(board.players[currentPlayerIndex])){
                        return;
                    }
                    // figuren zu hause und eine sechs gewürfelt und startfeld gedrückt wurde
                    if (board.players[currentPlayerIndex].getHomeFigAmount() >= 1 && d.getValue() == 6 && board.course[currentPlayerIndex * fieldPerPerson].equals(f)) {
                        if (f.isOccupied()) {
                            // figuren zu hause und eine sechs gewürfelt und startfeld vom gleichen spieler besetzt und startfeld gedrückt wurde
                            if (f.getFigure().isSamePlayer(board.players[currentPlayerIndex])) {
                                moveFigure(f, d.getValue());
                                return;
                            } else {
                                // figuren zu hause und eine sechs gewürfelt und startfeld vom anderen spieler besetzt
                                f.getFigure().kill();
                                pl.updateFiguresAtHome(board.getPlayerByFigure(f.getFigure()));
                            }
                        }
                        f.setFigure(board.players[currentPlayerIndex].getNextHomeFigure());
                        pl.updateFiguresAtHome(board.players[currentPlayerIndex]);
                        d.reset();
                        rep();
                        return;
                    }
                    // keine figuren zu hause
                    if (board.players[currentPlayerIndex].getHomeFigAmount() == 0 || d.getValue() != 6){
                        moveFigure(f, d.getValue());
                    }
                }
                if (((Settings.board_width-(Settings.buttonSize*2))<xpos && xpos<Settings.board_width-Settings.buttonSize) && ((Settings.board_height-Settings.buttonSize)<ypos && ypos<Settings.board_height)) {
                    d.roll();
                    rep();
                    return;
                }
                if (!d.canRoll() && ((Settings.board_width-Settings.buttonSize)<xpos && xpos<Settings.board_width) && ((Settings.board_height-Settings.buttonSize)<ypos && ypos<Settings.board_height)) {

                    nextPlayer();

                    d.enableDice(figureAmount == board.players[currentPlayerIndex].getHomeFigAmount()?3: 1);
                    nextPlayerBtn.setColor(board.players[currentPlayerIndex].getColor());
                    nextPlayerBtn.repaint();
                    d.reset();
                    rep();
                }}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
    }

    public int nextPlayer(){
        currentPlayerIndex = (currentPlayerIndex+1)%board.playerAmount;
        pl.setPlayerToGreen(board.players[currentPlayerIndex].getPlayerName());
        return currentPlayerIndex;
    }

    public void moveFigure(Field selectedField, int amount){
        if (selectedField.getFigure() == null){
            return;
        }

        int selectedFieldIndex = ((selectedField.getIndex() + amount) % (playerAmount*fieldPerPerson));

        if (!selectedField.getFigure().isSamePlayer(board.players[currentPlayerIndex])){
            return;
        }
        if (!selectedField.getFigure().isHome() && amount>0) {
            // wenn eine runde für die figur gemacht wurde...
            if (selectedField.getFigure().getSteps()+amount>=(playerAmount*fieldPerPerson)){
                int homeDepth = (selectedField.getFigure().getSteps()+amount)-(playerAmount*fieldPerPerson);

                // heim ist out of bounds
                if (homeDepth>figureAmount){
                    return;
                }
                House h = board.players[currentPlayerIndex].getHome();
                // wenn das feld zu hause frei ist
                if (!h.getRoom(homeDepth).isOccupied()){
                    Figure f = selectedField.clearField();
                    f.setReachedEnd(true);
                    h.getRoom(homeDepth).setFigure(f);
                    d.reset();
                    rep();
                    if (board.players[currentPlayerIndex].getFinishedFigAmount() == figureAmount){
                        setCurrentWinner();
                    }
                }
                return;
            }

            Field fieldInQuestion = board.course[selectedFieldIndex];
            if (fieldInQuestion.isOccupied()){
                fieldInQuestion.getFigure().kill();
                pl.updateFiguresAtHome(board.getPlayerByFigure(fieldInQuestion.getFigure()));
            }
            selectedField.getFigure().addSteps(amount);

            fieldInQuestion.setFigure(selectedField.clearField());
            pl.updateFiguresAtHome(board.players[currentPlayerIndex]);
            d.reset();
            rep();
        }
    }

    private void setCurrentWinner(){
        ws = new Winscreen(board.players[currentPlayerIndex]);
        pl.setVisible(false);
        nextPlayerBtn.setVisible(false);
        rep();
        t = new Timer(16, ae -> {
                    rep();
                });
        t.start();
    }
    private void rep(){
        this.repaint();
        this.updateUI();
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

        if (ws != null){
            ws.paintWinner(g);
        }

    }

}
