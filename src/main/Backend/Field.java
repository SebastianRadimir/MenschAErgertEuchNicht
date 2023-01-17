package Backend;

import java.awt.*;

import static GuiStuff.Settings.board_tile_color;
import static GuiStuff.Settings.fieldSize;

public class Field{

    private final int posX;
    private final int posY;
    private Figure holdingFigure;
    private final int posIndex;

    public Field(int index, int posX, int posY){
        holdingFigure = null;
        posIndex = index;

        this.posX = posX;
        this.posY = posY;

    }
    public int getIndex(){
        return posIndex;
    }

    public boolean isOccupied(){
        return this.holdingFigure != null;
    }

    public void setFigure(Figure f){
        this.holdingFigure = f;
    }

    public Figure getFigure(){
        return this.holdingFigure;
    }

    public Figure clearField(){
        Figure pf = this.holdingFigure;
        this.holdingFigure = null;
        return pf;
    }

    public void draw(Graphics g) {

        if (isOccupied()) {
            g.setColor(getFigure().getColor());
        }else {
            g.setColor(board_tile_color);
        }

        g.fillOval(posX-(fieldSize/2), posY-(fieldSize/2), fieldSize,fieldSize);

    }

}
