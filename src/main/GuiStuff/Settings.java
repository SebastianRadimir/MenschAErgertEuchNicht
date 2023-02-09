package GuiStuff;


import java.awt.*;

import static java.lang.Math.PI;
import static java.lang.Math.min;

public class Settings {

    public static int playerAmount = 4;
    public static int figureAmount = 4;
    public static int circlePrecision = 360;
    public static double PI2 = PI*2.0;
    public static int fieldPerPerson = 10;
    public static int board_width = 1000;
    public static int board_height = 1000;
    public static int boardCenterX = board_width/2;
    public static int boardCenterY = board_height/2;
    public static int circleSize = boardCenterY/2;
    public static int fieldSize = 10;
    public static Color board_bg_color = new Color(36,70,103);
    public static Color board_line_color = new Color(115, 95, 71);
    public static Color board_tile_color = new Color(206, 171, 96);
    public static Color highlight_color = new Color(42, 187, 220);
    public static Color text_color = new Color(0, 0, 0);
    public static Color dice_BG_color = new Color(255, 255, 255);
    public static Color dice_selection_color = new Color(255, 0, 0);

    public static double getBoardShape(double angle){
        return Math.max((Math.sin(angle*playerAmount)+1.2)*(circleSize/2.0),(Math.sqrt(playerAmount)*(circleSize/4.0))); // Board als Blume
    }

    public static void reload(){

        boardCenterX = board_width/2;
        boardCenterY = board_height/2;
        circleSize = (int)min(boardCenterY*0.65,boardCenterX*0.65);
        fieldSize = (int)(((Math.pow(playerAmount,(-Math.E/4.0))/0.02))+8.0);

    }
}
