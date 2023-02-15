package Backend;

import java.awt.*;

import static GuiStuff.Settings.*;

public class Player {

    private final Figure[] figures;
    private final Color playerColor;
    private final String playerName;
    private final int playerIndex;
    private final House home;
    private final int maX;
    private final int maY;
    private final int miX;
    private final int miY;


    public Player(int playerIndex, int figureAmount, Color playerColor, String playerName){
        this.playerIndex = playerIndex;
        figures = new Figure[figureAmount];

        this.playerColor = playerColor;
        this.playerName = playerName;

        Field[] homeFields = new Field[figureAmount];
        //double angle = (PI2/playerAmount*(playerIndex+0.5));
        double angle = PI2/(playerAmount*fieldPerPerson);
        angle = angle * (((playerIndex*fieldPerPerson)+2)+0.5);
        double minDist = (circleSize/8.0);
        double dists = ((circleSize)-minDist)/figureAmount;
        for (int i = 0; i < figureAmount; i++) {
            figures[i] = new Figure(playerColor);

            double pn = (dists*(figureAmount-i))+minDist;
            homeFields[i] = new Field(i, (int)((Math.cos(angle)*pn) + boardCenterX), (int)((Math.sin(angle)*pn) + boardCenterY));
        }
        maX = (int)(Math.cos(angle)*(dists+minDist)) + boardCenterX;
        maY = (int)(Math.sin(angle)*(dists+minDist)) + boardCenterY;

        miX = (int)(Math.cos(angle)*getBoardShape(angle)) + boardCenterX;
        miY = (int)(Math.sin(angle)*getBoardShape(angle)) + boardCenterY;

        home = new House(this, homeFields);

    }

    public House getHome(){
        return this.home;

    }

    public Figure[] getFigures() {
        return figures;
    }

    public Color getColor() {
        return playerColor;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public void draw(Graphics g, int mpx, int mpy){

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(fieldSize/3.0f));
        g.setColor(board_line_color);
        g.drawLine(maX,maY,miX,miY);

        this.home.draw(g, mpx, mpy);

    }
}
