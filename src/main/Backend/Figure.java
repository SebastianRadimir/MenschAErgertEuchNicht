package Backend;

import java.awt.*;

public class Figure{

    private boolean isHome;
    private boolean reachedEnd;
    private final Color figureColor;
    private int steps;

    public Figure(Color c){
        figureColor = c;
        isHome = true;
        reachedEnd = false;
        steps = 0;
    }

    public boolean isSamePlayer(Player p){
        return p.getColor().equals(figureColor);
    }

    public void setHome(boolean home) {
        isHome = home;
    }

    public int getSteps(){
        return this.steps;
    }
    public void addSteps(int aSteps){
        steps += aSteps;
    }
    public void setReachedEnd(boolean reachedEnd) {
        this.reachedEnd = reachedEnd;
    }

    public Color getColor(){
        return figureColor;
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

    public void kill() {
        steps = 0;
        isHome = true;
        reachedEnd = false;
    }
}
