package Backend;

import java.awt.*;

import static GuiStuff.Settings.*;

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
        f.setHome(false);
        this.holdingFigure = f;
    }

    public Figure getFigure(){
        return this.holdingFigure;
    }

    public Figure clearField(){
        this.holdingFigure.setHome(true);
        Figure pf = this.holdingFigure;
        this.holdingFigure = null;
        return pf;
    }

    public int getX(){
        return posX;
    }
    public int getY(){
        return posY;
    }

    public void draw(Graphics g, int mousePosX, int mousePosY) {

        if (isOccupied()) {
            g.setColor(getFigure().getColor());
        } else {
            g.setColor(board_tile_color);
        }

        g.fillOval(posX - (fieldSize / 2), posY - (fieldSize / 2), fieldSize, fieldSize);

        if (Math.sqrt(((mousePosX - posX)*(mousePosX - posX)) + ((mousePosY - posY) * (mousePosY - posY))) <= (fieldSize/2.0)) {
            g.setColor(highlight_color);
            g.drawOval(posX - (fieldSize / 2), posY - (fieldSize / 2), fieldSize, fieldSize);
        }else {
            g.setColor(board_tile_color);
            g.drawOval(posX - (fieldSize / 2), posY - (fieldSize / 2), fieldSize, fieldSize);
        }
    }

}
