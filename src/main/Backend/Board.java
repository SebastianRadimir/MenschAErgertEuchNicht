package Backend;

import GuiStuff.PerlinNoise;

import java.awt.*;
import java.util.Random;

import static GuiStuff.Settings.*;

public class Board {
    private final int randX;
    private final int randY;

    private Player[] players;
    private Field[] course;
    private int currentPlayerIndex;
    private final int playerAmount;
    private final double perc = 0.7;
    private final double amplification = 0.5;
    private final int[] courseLineArrX;
    private final int[] courseLineArrY;

    public Board(Player[] players){
        this.players = players;

        randX = new Random().nextInt();
        randY = new Random().nextInt();

        courseLineArrX = new int[circlePrecision];
        courseLineArrY = new int[circlePrecision];
        double circlePrecisionInterval = PI2/circlePrecision;
        for (int i = 0; i < circlePrecision; i++) {

            double ni = circlePrecisionInterval*i;

            double pn = PerlinNoise.noise((Math.cos(ni)/perc)+randX,(Math.sin(ni)/perc)+randY)*amplification;

            courseLineArrX[i] = (int)((circleSize*Math.cos(ni)*(pn+1)) + boardCenterX);
            courseLineArrY[i] = (int)((circleSize*Math.sin(ni)*(pn+1)) + boardCenterY);

        }

        currentPlayerIndex = 0;
        playerAmount = this.players.length;

        course = new Field[playerAmount*fieldPerPerson];

        double div = PI2/(playerAmount*fieldPerPerson);
        for (int i = 0; i < playerAmount*fieldPerPerson; i++) {
            double ni = div*i;

            double pn = PerlinNoise.noise((Math.cos(ni)/perc)+randX,(Math.sin(ni)/perc)+randY)*amplification;
            course[i] = new Field(i, (int)((circleSize*Math.cos(ni)*(pn+1)) + boardCenterX), (int)((circleSize*Math.sin(ni)*(pn+1)) + boardCenterY));
        }
    }

    public int nextPlayer(){
        currentPlayerIndex = (currentPlayerIndex+1)%this.playerAmount;
        return currentPlayerIndex;
    }

    public void moveFigure(Field selectedField, int amount){
        if (!selectedField.isOccupied()){
            return;
        }
        if (!selectedField.getFigure().isRunning()) {
            course[(selectedField.getFigure().getFieldIndex() + amount) % (playerAmount*fieldPerPerson)].setFigure(selectedField.clearField());
        }

    }

    public void draw(Graphics g){

        g.setColor(board_line_color);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(fieldSize/2));
        g.drawPolygon(courseLineArrX, courseLineArrY,circlePrecision);

        for (int i = 0; i < playerAmount*fieldPerPerson; i++) {
            course[i].draw(g);
        }


    }
}
