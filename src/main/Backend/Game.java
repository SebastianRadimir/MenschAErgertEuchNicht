package Backend;

import GuiStuff.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import static Backend.Dice.DiceSide.drawBackground;
import static GuiStuff.Settings.*;

public class Game extends JPanel{

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
    private int endMoveIndex = 0;
    private Point[] travelPath;
    private Field predictedPathField = null;
    private final int animationSteps = 40;
    private final BufferedImage bgImage;
    public Game(Board board,JFrame parent){

        bgImage = getBGImage();
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
        if (bgImage != null) {
            nextPlayerBtn.setBackground(new Color(0, 0, 0, 0));
        }
        nextPlayerBtn.setVisible(true);
        parent.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (runner != null){
                    movePIndex = endMoveIndex-1;
                    return;
                }
                if (ws != null){
                    return;
                }
                if (e.getKeyChar() == ' '){
                    d.roll();
                    rep();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Point b = MouseInfo.getPointerInfo().getLocation();
                int xpos = (int) b.getX();
                int ypos = (int) b.getY();
                predict(board.getField(xpos, ypos));
                rep();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                Point b = MouseInfo.getPointerInfo().getLocation();
                int xpos = (int) b.getX();
                int ypos = (int) b.getY();
                predict(board.getField(xpos, ypos));
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
                    movePIndex = endMoveIndex-1;
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

    private BufferedImage getBGImage() {

        try {
            String name = "src/assets/woodenplank"+new Random().nextInt(1,4)+".jpg";

            return ImageIO.read(new File(name));
        } catch (IOException e) {
            return null;
        }
    }
    private void predict(Field f){
        predictedPathField = null;
        if (f != null && f.isOccupied() && d.getValue() >= 1 && f.getFigure().isSamePlayer(currentPlayer())){
            int selectedFieldIndex = ((f.getIndex() + d.getValue()) % (playerAmount*fieldPerPerson));
            if (f.getFigure().getSteps()+d.getValue()>=(playerAmount*fieldPerPerson)) {
                int homeDepth = (f.getFigure().getSteps() + d.getValue()) - (playerAmount * fieldPerPerson);

                if (homeDepth >= figureAmount) {
                    return;
                }
                predictedPathField = board.players[currentPlayerIndex].getHome().getRoom(homeDepth);
                return;
            }
            predictedPathField = board.course[selectedFieldIndex];
        }
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
                    if (currentPlayer().getFinishedFigAmount()>=figureAmount){
                        setCurrentWinner();
                    }
                }
                return;
            }
            selectedField.getFigure().addSteps(amount);

            runner = selectedField.clearField();
            movePIndex = 0;

            endMoveIndex = animationSteps*amount;
            travelPath = new Point[endMoveIndex];

            for (int i = 0; i < amount; i++) {

                int realIndex = ((selectedField.getIndex()+i) );
                Point from = new Point(board.course[realIndex % (playerAmount*fieldPerPerson)].getX(),board.course[realIndex % (playerAmount*fieldPerPerson)].getY());
                Point to = new Point(board.course[(realIndex+1) % (playerAmount*fieldPerPerson)].getX(),board.course[(realIndex+1) % (playerAmount*fieldPerPerson)].getY());

                double xStep = (to.x - from.x) / (double)animationSteps;
                double yStep = (to.y - from.y) / (double)animationSteps;

                for (int j = 0; j < animationSteps; j++) {
                    travelPath[(i*animationSteps)+j] = new Point((int) ((xStep * j) + from.x), (int) ((yStep * j) + from.y));
                }
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

        if (movePIndex>=(endMoveIndex-1)) {

            if (fieldInQuestion.isOccupied()){
                fieldInQuestion.getFigure().kill();
                pl.updateFiguresAtHome(board.getPlayerByFigure(fieldInQuestion.getFigure()));
            }

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
        board_bg_color = new Color(0,0,0,0);
        d.setVisible(false);
        d.setBackground(board_bg_color);
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

        g.setColor(board_bg_color2);

        g.fillRect(0, 0, board_width + 10, board_height + 10);
        if (bgImage != null) {
            g.drawImage(bgImage, 0, 0, this);
            g.setColor(board_bg_color2.brighter());
            g.fillRect((boardCenterX-boardCenterY)+10, 10, board_height-10, board_height-20);

            Graphics2D g2 = (Graphics2D) g;
            Stroke prevS = g2.getStroke();
            g2.setStroke(new BasicStroke(buttonSize/10));

            g.setColor(board_bg_color2.darker());
            g.drawRect((boardCenterX-boardCenterY)+10, 10, board_height-10, board_height-20);
            g2.setStroke(prevS);
        }

        board.draw(g, xpos, ypos);

        if (runner != null && travelPath != null && travelPath[(int)movePIndex] != null){

            g.setColor(runner.getColor());
            Point p = travelPath[(int)movePIndex];
            double dynamicSize = Math.abs(Math.sin((movePIndex*Math.PI)/(double)animationSteps)*1.5)+1;
            double fs = (fieldSize/2.0)*dynamicSize;

            g.fillOval((int)(p.x-fs),(int)(p.y-fs),(int)(fieldSize*dynamicSize), (int)(fieldSize*dynamicSize));

        }
        if (predictedPathField != null){
            predictedPathField.draw(g, predictedPathField.getX(),predictedPathField.getY());
        }

        if (ws != null){
            d.paintComponent(g);
            ws.paintWinner(g);
            if (ws.passedEnough()){

                if (boardCenterY+(int)(buttonSize*1.5)>ypos && boardCenterY+((int)(buttonSize*0.75))<ypos){
                    g.setColor(highlight_color);
                }else {
                    g.setColor(highlight_color.darker());
                }

                g.setFont(new Font(null, Font.PLAIN, buttonSize));

                String options = "Nochmal spielen";
                int ls = options.length();

                g.drawString(options, (int)(boardCenterX-((ls/4.0)*buttonSize)), boardCenterY+((int)(buttonSize*1.5)));


                if (boardCenterY+(buttonSize*2.25)<ypos && boardCenterY+(buttonSize*3)>ypos){
                    g.setColor(highlight_color);
                }else {
                    g.setColor(highlight_color.darker());
                }
                options = "Beenden";
                ls = options.length();

                g.drawString(options, (int)(boardCenterX-((ls/4.0)*buttonSize)), boardCenterY+(buttonSize*3));

            }
        }else {

            d.paintComponent(g);
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
