package Backend.Dice;

import GuiStuff.Settings;

import java.awt.*;
import java.util.Random;

public enum DiceSide {

    ONE((g, xMin, yMin, xSize, ySize) -> {

        drawBackground(g, xMin, yMin, xSize, ySize);

        double radius = xSize/10.0;
        double centerX = (xSize/2.0);
        double centerY = (ySize/2.0);

        g.setColor(new Color(0, 0, 0));
        g.fillOval(xMin+(int)(centerX-radius),yMin+(int)(centerY-radius),(int)(radius*2),(int)(radius*2));

    }, 1),
    TWO((g, xMin, yMin, xSize, ySize) -> {

        drawBackground(g, xMin, yMin, xSize, ySize);

        double gridX = xSize/4.0;
        double gridY = ySize/4.0;

        double radius = xSize/10.0;

        g.setColor(new Color(0, 0, 0));

        g.fillOval(xMin+(int)((gridX)-radius),yMin+(int)((gridY)-radius),(int)(radius*2),(int)(radius*2));
        g.fillOval(xMin+(int)((gridX*3)-radius),yMin+(int)((gridY*3)-radius),(int)(radius*2),(int)(radius*2));

    },2),
    THREE((g, xMin, yMin, xSize, ySize) -> {

        drawBackground(g, xMin, yMin, xSize, ySize);

        double radius = xSize/10.0;
        double centerX = (xSize/2.0);
        double centerY = (ySize/2.0);

        double gridX = xSize/4.0;
        double gridY = ySize/4.0;

        g.setColor(new Color(0, 0, 0));
        g.fillOval(xMin+(int)(centerX-radius),yMin+(int)(centerY-radius),(int)(radius*2),(int)(radius*2));
        g.fillOval(xMin+(int)((gridX)-radius),yMin+(int)((gridY)-radius),(int)(radius*2),(int)(radius*2));
        g.fillOval(xMin+(int)((gridX*3)-radius),yMin+(int)((gridY*3)-radius),(int)(radius*2),(int)(radius*2));

    },3),
    FOUR((g, xMin, yMin, xSize, ySize) -> {

        drawBackground(g, xMin, yMin, xSize, ySize);

        double gridX = xSize/4.0;
        double gridY = ySize/4.0;
        double radius = xSize/10.0;

        g.setColor(new Color(0, 0, 0));
        g.fillOval(xMin+(int)((gridX)-radius),yMin+(int)((gridY)-radius),(int)(radius*2),(int)(radius*2));
        g.fillOval(xMin+(int)((gridX)-radius),yMin+(int)((gridY*3)-radius),(int)(radius*2),(int)(radius*2));
        g.fillOval(xMin+(int)((gridX*3)-radius),yMin+(int)((gridY)-radius),(int)(radius*2),(int)(radius*2));
        g.fillOval(xMin+(int)((gridX*3)-radius),yMin+(int)((gridY*3)-radius),(int)(radius*2),(int)(radius*2));
    },4),
    FIVE((g, xMin, yMin, xSize, ySize) -> {

        drawBackground(g, xMin, yMin, xSize, ySize);

        double gridX = xSize/4.0;
        double gridY = ySize/4.0;
        double radius = xSize/10.0;
        double centerX = (xSize/2.0);
        double centerY = (ySize/2.0);

        g.setColor(new Color(0, 0, 0));
        g.fillOval(xMin+(int)((gridX)-radius), yMin+(int)((gridY)-radius),(int)(radius*2),(int)(radius*2));
        g.fillOval(xMin+(int)((gridX)-radius), yMin+(int)((gridY*3)-radius),(int)(radius*2),(int)(radius*2));
        g.fillOval(xMin+(int)((gridX*3)-radius), yMin+(int)((gridY)-radius),(int)(radius*2),(int)(radius*2));
        g.fillOval(xMin+(int)((gridX*3)-radius), yMin+(int)((gridY*3)-radius),(int)(radius*2),(int)(radius*2));
        g.fillOval(xMin+(int)(centerX-radius), yMin+(int)(centerY-radius),(int)(radius*2),(int)(radius*2));

    },5),
    SIX((g, xMin, yMin, xSize, ySize) -> {

        drawBackground(g, xMin, yMin, xSize, ySize);

        double gridX = xSize/4.0;
        double gridY = ySize/6.0;
        double radius = xSize/10.0;

        g.setColor(new Color(0, 0, 0));
        g.fillOval(xMin+(int)((gridX)-radius),yMin+(int)((gridY)-radius),(int)(radius*2),(int)(radius*2));
        g.fillOval(xMin+(int)((gridX*3)-radius),yMin+(int)((gridY)-radius),(int)(radius*2),(int)(radius*2));
        g.fillOval(xMin+(int)((gridX)-radius),yMin+(int)((gridY*3)-radius),(int)(radius*2),(int)(radius*2));
        g.fillOval(xMin+(int)((gridX*3)-radius),yMin+(int)((gridY*3)-radius),(int)(radius*2),(int)(radius*2));
        g.fillOval(xMin+(int)((gridX)-radius),yMin+(int)((gridY*5)-radius),(int)(radius*2),(int)(radius*2));
        g.fillOval(xMin+(int)((gridX*3)-radius),yMin+(int)((gridY*5)-radius),(int)(radius*2),(int)(radius*2));


    },6);

    private final DiceDrawInterface ddi;
    private final int value;
    DiceSide(DiceDrawInterface drawInterface, int points) {
        ddi = drawInterface;
        value = points;
    }
    public int getPoints(){
        return value;
    }
    public void draw(Graphics g, int xMin, int yMin, int xSize, int ySize){
        ddi.drawDice(g, xMin, yMin, xSize, ySize);
    }
    public static DiceSide getRandomDiceSide(){
        return DiceSide.values()[new Random().nextInt(0,DiceSide.values().length)];
    }

    public static DiceSide getSideByVal(int value) {
        return DiceSide.values()[value-1];
    }
    public static void drawBackground(Graphics g, int xMin, int yMin, int xSize, int ySize){

        int w = xSize;
        int h = ySize;

        int minsize = Math.min(w,h);
        w = minsize;
        h = minsize;

        int barSize = h/5;

        g.setColor(Settings.dice_BG_color);

        g.fillRect(xMin,yMin+barSize,w,h-(barSize*2));
        g.fillRect(xMin+barSize,yMin,w-(barSize*2),h);

        g.fillOval(xMin,yMin,barSize*2,barSize*2);
        g.fillOval(xMin+(h-(barSize*2)),yMin,barSize*2,barSize*2);
        g.fillOval(xMin,yMin+(h-(barSize*2)),barSize*2,barSize*2);
        g.fillOval(xMin+(h-(barSize*2)),yMin+(h-(barSize*2)),barSize*2,barSize*2);
    }

}
