package GuiStuff;

import javax.swing.*;
import java.awt.*;

public class ArrowPanel extends JPanel {

    private Color bgColor;
    private Color color;
    private final double arrowDirection;
    private final ArrowPolygon arrowP;

    public ArrowPanel(double arrowDirection, Color color, Color backgroundColor, boolean normalArrow){
        this.color = color;
        this.arrowP = new ArrowPolygon(normalArrow);
        this.arrowDirection = arrowDirection;
        this.bgColor = backgroundColor;

    }

    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg);
        this.bgColor = bg;
    }

    @Override
    public void paintComponent(Graphics g){

        Dimension d = this.getSize();

        int w = d.width;
        int h = d.height;

        g.setColor(bgColor);
        g.fillRect(0,0,w+10,h+10);

        g.setColor(this.color);
        this.arrowP.drawShape(g, 0,0,w,h,this.arrowDirection);
    }

    public void setColor(Color c) {
        super.setBackground(c);
        this.color = c;
    }

    public interface ComputeGraphics {
        void drawShape(Graphics g, int xMin, int yMin, int xSize, int ySize, double modifier);
    }

    public static class ArrowPolygon implements ComputeGraphics {
        private final boolean normal;

        public ArrowPolygon(boolean normalArrow){
            this.normal = normalArrow;
        }

        @Override
        public void drawShape(Graphics g, int xMin, int yMin, int xSize, int ySize, double modifier) {
            if (modifier>1.0){
                modifier = 1.0;
            }
            if (modifier < -1.0){
                modifier = -1.0;
            }
            if (modifier>=0.0){
                g.fillPolygon(rightArrow(xMin, yMin, xSize, ySize, modifier));
                return;
            }
            if (modifier<0.0){
                g.fillPolygon(leftArrow(xMin, yMin, xSize, ySize, modifier));
            }
        }

        private Polygon rightArrow(int xMin, int yMin, int xSize, int ySize, double modifier) {


            int[] xPos = new int[8];
            int[] yPos = new int[8];

            int xMax = xMin+xSize;
            int yMax = yMin+ySize;

            int xMid = xMin+((xMax-xMin)/2);
            int yMid = yMin+((yMax-yMin)/2);
            int yTrd = yMin+((yMax-yMin)/3);
            int xTrd = xMin+((xMax-xMin)/3);

            xPos[0] = (int)(xMid-(xMid*modifier));
            yPos[0] = yTrd;

            xPos[1] = (int)(xMid-(xMid*modifier));
            yPos[1] = yTrd*2;

            xPos[2] = xMid;
            yPos[2] = yTrd*2;

            if (this.normal) {
                xPos[3] = xMid;
            }else {
                xPos[3] = xTrd;
            }
            yPos[3] = yMax;

            xPos[4] = xMax;
            yPos[4] = yMid;

            if (this.normal) {
                xPos[5] = xMid;
            }else {
                xPos[5] = xTrd;
            }
            yPos[5] = yMin;

            xPos[6] = xMid;
            yPos[6] = yTrd;

            xPos[7] = (int)(xMid-(xMid*modifier));
            yPos[7] = yTrd;

            return new Polygon(xPos, yPos,7);
        }

        private Polygon leftArrow(int xMin, int yMin, int xSize, int ySize, double modifier) {

            modifier = -modifier;

            int[] xPos = new int[8];
            int[] yPos = new int[8];

            int xMax = xMin+xSize;
            int yMax = yMin+ySize;

            int xMid = xMin+((xMax-xMin)/2);
            int yMid = yMin+((yMax-yMin)/2);
            int yTrd = yMin+((yMax-yMin)/3);
            int xTrd = xMin+((xMax-xMin)/3);

            xPos[0] = xMin;
            yPos[0] = yMid;

            if (this.normal) {
                xPos[1] = xMid;
            }else {
                xPos[1] = xTrd*2;
            }
            yPos[1] = yMax;

            xPos[2] = xMid;
            yPos[2] = yTrd*2;

            xPos[3] = (int)(xMid+(xMid*modifier));
            yPos[3] = yTrd*2;

            xPos[4] = (int)(xMid+(xMid*modifier));
            yPos[4] = yTrd;

            xPos[5] = xMid;
            yPos[5] = yTrd;

            if (this.normal) {
                xPos[6] = xMid;
            }else {
                xPos[6] = xTrd*2;
            }
            yPos[6] = yMin;

            xPos[7] = xMin;
            yPos[7] = yMid;

            return new Polygon(xPos, yPos,7);
        }
    }

}
