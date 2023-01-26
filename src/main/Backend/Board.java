package Backend;

import java.awt.*;

import static GuiStuff.Settings.*;

public class Board {
    public final Player[] players;
    public final Field[] course;
    public final int playerAmount;
    private final int[] courseLineArrX;
    private final int[] courseLineArrY;
    public Board(Player[] players){
        this.players = players;

        courseLineArrX = new int[circlePrecision];
        courseLineArrY = new int[circlePrecision];
        double circlePrecisionInterval = PI2/circlePrecision;

        playerAmount = this.players.length;

        for (int i = 0; i < circlePrecision; i++) {

            double ni = circlePrecisionInterval*i;

            double pn = getBoardShape(ni);
            courseLineArrX[i] = (int)((Math.cos(ni)*pn) + boardCenterX);
            courseLineArrY[i] = (int)((Math.sin(ni)*pn) + boardCenterY);

        }

        course = new Field[playerAmount*fieldPerPerson];

        double div = PI2/(playerAmount*fieldPerPerson);
        for (int i = 3; i < (playerAmount*fieldPerPerson)+3; i++) {
            double ni = div*(i+0.5);

            double pn = getBoardShape(ni);
            course[i-3] = new Field(i-3, (int)((Math.cos(ni)*pn) + boardCenterX), (int)((Math.sin(ni)*pn) + boardCenterY));
        }
    }

    public Field getField(int xPos, int yPos){
        for (int i = 0; i < playerAmount*fieldPerPerson; i++) {

            Field f = course[i];

            int x = f.getX();
            int y = f.getY();

            if (Math.sqrt(((xPos - x)*(xPos - x)) + ((yPos - y) * (yPos - y))) <= (fieldSize/2.0)){
                return f;
            }
        }
        return null;
    }

    public void draw(Graphics g, int xpos, int ypos){

        for (int i = 0; i < playerAmount; i++) {
            players[i].draw(g, xpos,ypos);
        }

        g.setColor(board_line_color);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(fieldSize/3.0f));
        g.drawPolygon(courseLineArrX, courseLineArrY,circlePrecision);

        for (int i = 0; i < playerAmount*fieldPerPerson; i++) {
            course[i].draw(g, xpos,ypos);
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
