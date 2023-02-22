package Backend;

import java.awt.*;

public class Figure{

    private boolean isHome;
    private boolean reachedEnd;
    private final Color figureColor;

    public Figure(Color c){
        figureColor = c;
        isHome = true;
        reachedEnd = false;
    }

    public void setHome(boolean home) {
        isHome = home;
    }

    public void setReachedEnd(boolean reachedEnd) {
        this.reachedEnd = reachedEnd;
    }

    public Color getColor(){
        return figureColor;
    }
    public boolean isRunning(){
        return !isHome && !reachedEnd;
    }

    /***
     * returns a boolean, of the figure,
     * only true if figure is still at home
     */
    public boolean isHome(){
        return isHome && !reachedEnd;
    }
    public boolean inFinishLine(){
        return reachedEnd;
    }

}
