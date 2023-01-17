package Backend;

import java.awt.*;

public class Player {

    private Figure[] figures;
    private Color playerColor;
    private String playerName;
    private int playerIndex;


    public Player(int playerIndex, int figureAmount, Color playerColor, String playerName){
        this.playerIndex = playerIndex;
        figures = new Figure[figureAmount];

        this.playerColor = playerColor;
        this.playerName = playerName;

        for (int i = 0; i < figureAmount; i++) {
            figures[i] = new Figure(0, playerColor);
        }

    }

}
