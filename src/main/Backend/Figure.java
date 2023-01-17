package Backend;

import java.awt.*;

public class Figure{

    private boolean isHome;
    private boolean reachedEnd;
    private int position;
    private final Color figureColor;

    public Figure(int index, Color c){
        position = index;
        figureColor = c;
        isHome = true;
        reachedEnd = false;
    }

    public Color getColor(){
        return figureColor;
    }
    public int getFieldIndex(){
        return position;
    }
    public boolean isRunning(){
        return !isHome && !reachedEnd;
    }
    public boolean inFinishLine(){
        return reachedEnd;
    }

}
