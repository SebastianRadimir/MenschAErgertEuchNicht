package Backend;

import java.awt.*;

import static GuiStuff.Settings.*;

public class Player {

    private Figure[] figures;
    private Color playerColor;
    private String playerName;
    private int playerIndex;
    private House home;

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

    public void draw(Graphics g){

        this.home.draw(g);



    }
}
