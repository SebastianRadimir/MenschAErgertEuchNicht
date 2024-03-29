package Backend;

import java.awt.*;

import static GuiStuff.Settings.*;

public class Player {

    private final Figure[] figures;
    private final Color playerColor;
    private final String playerName;
    private final House home;
    private final int maX;
    private final int maY;
    private final int miX;
    private final int miY;
    private final int minMx;
    private final int minMy;


    public Player(int playerIndex, int figureAmount, Color playerColor, String playerName){
        figures = new Figure[figureAmount];

        this.playerColor = playerColor;
        this.playerName = playerName;

        Field[] homeFields = new Field[figureAmount];
        double angle = PI2/(playerAmount*fieldPerPerson);
        angle = angle * ((playerIndex*fieldPerPerson)+2);
        double minDist = (circleSize/8.0);
        double dists = ((circleSize)-minDist)/figureAmount;
        for (int i = 0; i < figureAmount; i++) {
            figures[i] = new Figure(playerColor);

            double pn = (dists*(figureAmount-i))+minDist;
            homeFields[i] = new Field(i, (int)((Math.cos(angle)*pn) + boardCenterX), (int)((Math.sin(angle)*pn) + boardCenterY));
        }
        maX = homeFields[0].getX();
        maY = homeFields[0].getY();

        miX = (int)(Math.cos(angle)*getBoardShape(angle)) + boardCenterX;
        miY = (int)(Math.sin(angle)*getBoardShape(angle)) + boardCenterY;

        minMx = homeFields[figureAmount-1].getX();
        minMy = homeFields[figureAmount-1].getY();

        home = new House(this, homeFields);

    }

    public House getHome(){
        return this.home;

    }

    public int getFinishedFigAmount(){

        int homeFigs = 0;
        for (int i = 0; i < figureAmount; i++) {
            if (figures[i].inFinishLine()){
                homeFigs++;
            }
        }
        return homeFigs;
    }
    public int getHomeFigAmount(){

        int homeFigs = 0;
        for (int i = 0; i < figureAmount; i++) {
            if (figures[i].isHome()){
                homeFigs++;
            }
        }
        return homeFigs;
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

    public void draw(Graphics g, int mpx, int mpy){

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(fieldSize/3.0f));
        g.setColor(board_line_color);
        g.drawLine(maX,maY,miX,miY);
        g.drawLine(maX,maY,minMx,minMy);

        this.home.draw(g, mpx, mpy);

    }

    public Figure getNextHomeFigure() {
        for (Figure figure : figures) {
            if (figure.isHome()) {
                return figure;
            }
        }
        return null;
    }
}
