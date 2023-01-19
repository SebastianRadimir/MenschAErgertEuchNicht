package Backend;

import java.awt.*;

import static GuiStuff.Settings.*;

public class Board {
    private final Player[] players;
    private final Field[] course;
    private int currentPlayerIndex;
    private final int playerAmount;
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

            double pn = Math.max((Math.sin(ni*playerAmount)+1.2)*(circleSize/2.0),(Math.sqrt(playerAmount)*(circleSize/4.0)));
            courseLineArrX[i] = (int)((Math.cos(ni)*pn) + boardCenterX);
            courseLineArrY[i] = (int)((Math.sin(ni)*pn) + boardCenterY);

        }

        course = new Field[playerAmount*fieldPerPerson];

        double div = PI2/(playerAmount*fieldPerPerson);
        for (int i = 3; i < (playerAmount*fieldPerPerson)+3; i++) {
            double ni = div*(i+0.5);

            double pn = Math.max((Math.sin(ni*playerAmount)+1.2)*(circleSize/2.0),(Math.sqrt(playerAmount)*(circleSize/4.0)));
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

        for (int i = 0; i < playerAmount; i++) {
            players[i].draw(g);
        }

        g.setColor(board_line_color);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(fieldSize/3.0f));
        g.drawPolygon(courseLineArrX, courseLineArrY,circlePrecision);

        for (int i = 0; i < playerAmount*fieldPerPerson; i++) {
            course[i].draw(g);
        }

        g2.setStroke(new BasicStroke(fieldSize/5.0f));
        for (int i = 0; i < playerAmount; i++) {
            g.setColor(players[i].getColor());
            int x = course[i*fieldPerPerson].getX();
            int y = course[i*fieldPerPerson].getY();
            g.drawOval(x - (fieldSize / 2),y - (fieldSize / 2), fieldSize, fieldSize);
        }

    }
}
