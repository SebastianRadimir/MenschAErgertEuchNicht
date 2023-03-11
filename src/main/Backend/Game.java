package Backend;

import GuiStuff.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import static Backend.Dice.DiceSide.drawBackground;
import static GuiStuff.Settings.*;

public class Game extends JPanel {

    private Timer t;
    private final DiceGUI d;
    private static Board board = null;
    private static int currentPlayerIndex;
    private final PlayerList pl;
    private final ArrowPanel nextPlayerBtn;
    private Winscreen ws;
    private final RuleWindow rw;
    private Figure runner;
    private double movePIndex = 0;
    private double endMoveIndex = 0;
    private Point[] travelPath;
    public Game(Board board,JFrame parent){
        ws = null;
        travelPath = null;
        runner = null;
        rw = new RuleWindow();
        rw.setVisible(false);
        this.setLayout(null);
        currentPlayerIndex = 0;
        d = new DiceGUI();
        d.enableDice(3);
        Game.board = board;
        pl = new PlayerList(Game.board.players, board_height/(Game.board.players.length*2),board_width/7);
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
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {
                if (runner != null){
                    return;
                }
                Point b = MouseInfo.getPointerInfo().getLocation();
                int xpos = (int) b.getX();
                int ypos = (int) b.getY();
                if (ws != null){
                    if (ws.passedEnough()){

                        if (boardCenterY+(int)(buttonSize*1.5)>ypos && boardCenterY+((int)(buttonSize*0.75))<ypos){
                            parent.dispose();
                            new GuiStuff.WelcomeGUI();
                            return;
                        }

                        if (boardCenterY+(buttonSize*2.25)<ypos && boardCenterY+(buttonSize*3)>ypos){
                            System.exit(0);
                            return;
                        }

                    }
                    return;
                }
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

                House h = currentPlayer().getHome();
                f = h.getField(xpos, ypos);
                if (f != null){
                    if (h.movePlayerInHome(f,d.getValue())){
                        pl.updateFiguresAtHome(board.players[currentPlayerIndex]);
                        d.reset();
                        rep();
                        return;
                    }
                }

                if (xpos<Settings.buttonSize && ypos<Settings.buttonSize && xpos>0 && ypos>0){
                    if (rw.isActive()){
                        rw.toFront();
                    }
                    rw.setVisible(true);
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

    public static Player currentPlayer(){
        return board.players[currentPlayerIndex];
    }
    public void nextPlayer(){
        currentPlayerIndex = (currentPlayerIndex+1)%board.playerAmount;
        pl.setPlayerToGreen(board.players[currentPlayerIndex].getPlayerName());
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
            Field fieldInQuestion = board.course[selectedFieldIndex];
            // wenn eine runde für die figur gemacht wurde...
            if (selectedField.getFigure().getSteps()+amount>=(playerAmount*fieldPerPerson)){
                int homeDepth = (selectedField.getFigure().getSteps()+amount)-(playerAmount*fieldPerPerson);

                // heim ist out of bounds
                if (homeDepth>=figureAmount){
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

            if (fieldInQuestion.isOccupied()){
                fieldInQuestion.getFigure().kill();
                pl.updateFiguresAtHome(board.getPlayerByFigure(fieldInQuestion.getFigure()));
            }
            selectedField.getFigure().addSteps(amount);

            runner = selectedField.clearField();
            movePIndex = 0;

            Point from = new Point(selectedField.getX(),selectedField.getY());
            Point to = new Point(fieldInQuestion.getX(),fieldInQuestion.getY());

            endMoveIndex = 16*amount;
            double xStep = (to.x-from.x)/endMoveIndex;
            double yStep = (to.y-from.y)/endMoveIndex;

            travelPath = new Point[(int)endMoveIndex];
            for (int i = 0; i < endMoveIndex; i++) {
                travelPath[i] = new Point((int)((xStep*i)+from.x),(int)((yStep*i)+from.y));
            }

            t = new Timer(16, ae -> {
                startRunning(fieldInQuestion);
                repaint();
            });
            t.start();

        }
    }

    private void startRunning(Field fieldInQuestion){

        movePIndex+=1;

        if (movePIndex>=endMoveIndex-1) {
            movePIndex = 0;
            endMoveIndex = 0;
            t.stop();
            fieldInQuestion.setFigure(runner);
            runner = null;
            travelPath = null;
            t=null;
            pl.updateFiguresAtHome(board.players[currentPlayerIndex]);
            d.reset();
            rep();
        }

    }

    private void setCurrentWinner(){
        ws = new Winscreen(board.players[currentPlayerIndex]);
        pl.setVisible(false);
        rw.setVisible(false);
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

        board.draw(g, xpos, ypos);

        if (runner != null && travelPath != null){

            g.setColor(runner.getColor());
            Point p = travelPath[(int)movePIndex];
            double dynamicSize = (Math.sin((movePIndex*Math.PI)*(1.0/endMoveIndex))*3)+1;
            double fs = (fieldSize/2.0)*dynamicSize;

            g.fillOval((int)(p.x-fs),(int)(p.y-fs),(int)(fieldSize*dynamicSize), (int)(fieldSize*dynamicSize));

        }

        d.paintComponent(g);

        if (ws != null){
            ws.paintWinner(g);
            if (ws.passedEnough()){

                if (boardCenterY+(int)(buttonSize*1.5)>ypos && boardCenterY+((int)(buttonSize*0.75))<ypos){
                    g.setColor(board_bg_color.darker().darker());
                }else {
                    g.setColor(board_bg_color.darker());
                }

                g.setFont(new Font(null, Font.PLAIN, buttonSize));

                String options = "Nochmal Spielen";
                int ls = options.length();

                g.drawString(options, (int)(boardCenterX-((ls/4.0)*buttonSize)), boardCenterY+((int)(buttonSize*1.5)));


                if (boardCenterY+(buttonSize*2.25)<ypos && boardCenterY+(buttonSize*3)>ypos){
                    g.setColor(board_bg_color.darker().darker());
                }else {
                    g.setColor(board_bg_color.darker());
                }
                options = "Beenden";
                ls = options.length();

                g.drawString(options, (int)(boardCenterX-((ls/4.0)*buttonSize)), boardCenterY+(buttonSize*3));

            }
        }else {

            Color ps = Settings.dice_BG_color;
            Settings.dice_BG_color = board_bg_color.darker();
            drawBackground(g,0,0,buttonSize,buttonSize);
            Settings.dice_BG_color = ps;

            g.setColor(board_bg_color.brighter());
            double spl = buttonSize/6.0;
            for (int i = 0; i < 7; i++) {
                if (i%2==1){
                    g.fillRect((int)(spl*1.25), (int)spl*i, (int)(buttonSize-(spl*2)), (int)(spl/2.0));
                    g.fillOval((int)(spl/2.0), (int)spl*i,(int)(spl/2.0), (int)(spl/2.0));
                }
            }
        }
    }
}
