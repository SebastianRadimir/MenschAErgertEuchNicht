package Backend;

import java.awt.*;

public class Figure {

    private boolean isHome;
    private boolean reachedEnd;
    private int position;
    private final Color figureColor;

    public Figure(int pos, Color c){
        position = pos;
        figureColor = c;
        isHome = true;
        reachedEnd = false;
    }

    public Color getColor(){
        return figureColor;
    }
    public int getFieldPos(){
        return position;
    }
    public boolean isRunning(){
        return !isHome;
    }
    public boolean inFinishLine(){
        return reachedEnd;
    }
}
