package Backend;

import java.awt.*;

import static GuiStuff.Settings.*;

public class Board {
    private Player[] players;
    private Field[] course;
    private int currentPlayerIndex;
    private final int playerAmount;
    //private final double perc = 0.7;
    //private final double amplification = 0.5;
    private final int[] courseLineArrX;
    private final int[] courseLineArrY;

    public Board(Player[] players){
        this.players = players;

        courseLineArrX = new int[circlePrecision];
        courseLineArrY = new int[circlePrecision];
        double circlePrecisionInterval = PI2/circlePrecision;

        currentPlayerIndex = 0;
        playerAmount = this.players.length;

        for (int i = 0; i < circlePrecision; i++) {

            double ni = circlePrecisionInterval*i;

            //double pn = PerlinNoise.noise((Math.cos(ni)/perc)+randX,(Math.sin(ni)/perc)+randY)*amplification;
            double pn = (Math.sin(ni*playerAmount)+1.2)*(circleSize/2.0);
            courseLineArrX[i] = (int)((Math.cos(ni)*pn) + boardCenterX);
            courseLineArrY[i] = (int)((Math.sin(ni)*pn) + boardCenterY);

        }

        course = new Field[playerAmount*fieldPerPerson];

        double div = PI2/(playerAmount*fieldPerPerson);
        for (int i = 3; i < (playerAmount*fieldPerPerson)+3; i++) {
            double ni = div*(i+0.5);

            //double pn = PerlinNoise.noise((Math.cos(ni)/perc)+randX,(Math.sin(ni)/perc)+randY)*amplification;
            double pn = (Math.sin(ni*playerAmount)+1.2)*(circleSize/2.0);
            course[i-3] = new Field(i-3, (int)((Math.cos(ni)*pn) + boardCenterX), (int)((Math.sin(ni)*pn) + boardCenterY));
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
            course[(selectedField.getIndex() + amount) % (playerAmount*fieldPerPerson)].setFigure(selectedField.clearField());
        }

    }

    public void draw(Graphics g){

        g.setColor(board_line_color);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(fieldSize/3.0f));
        g.drawPolygon(courseLineArrX, courseLineArrY,circlePrecision);

        for (int i = 0; i < playerAmount*fieldPerPerson; i++) {
            course[i].draw(g);
        }

        for (int i = 0; i < playerAmount; i++) {
            players[i].draw(g);
        }

    }
}
