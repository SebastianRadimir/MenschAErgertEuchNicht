package GuiStuff;


import java.awt.*;

import static java.lang.Math.PI;
import static java.lang.Math.min;

public class Settings {

    //public static int board_width = 1280;
    //public static int board_height = 1280;
    public static int circlePrecision = 360;
    public static double PI2 = PI*2.0;
    public static int fieldPerPerson = 10;
    public static int board_width = 1000;
    public static int board_height = 1000;
    public static int boardCenterX = board_width/2;
    public static int boardCenterY = board_height/2;
    public static int circleSize = min(boardCenterY/2,boardCenterX/2);
    public static int fieldSize = 30;
    public static Color board_bg_color = new Color(36,70,103);
    public static Color board_line_color = new Color(115, 95, 71);
    public static Color board_tile_color = new Color(206, 171, 96);

    public static void reload(){

        boardCenterX = board_width/2;
        boardCenterY = board_height/2;
        circleSize = min(boardCenterY/2,boardCenterX/2);

    }
}
