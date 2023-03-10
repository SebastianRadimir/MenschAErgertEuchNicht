package GuiStuff;

import Backend.Player;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

import static GuiStuff.Settings.*;

public class Winscreen extends JPanel {

    private Color color;
    private String name;
    private Star[] stars;
    int maxStars = 200;

    public Winscreen(Player winingP){

        this.color = winingP.getColor();
        this.name = winingP.getPlayerName();
        stars = new Star[maxStars];
        for (int i = 0; i <maxStars; i++) {
            int s = new Random().nextInt(10,50);
            stars[i] = new Star(new Random().nextInt(1,board_width+(buttonSize*4)), new Random().nextInt(-board_width,-10),s,s, new Random().nextInt(3,100)/100.0, new Random().nextInt(3,9));
        }
    }

    public void paintWinner(Graphics g) {

        paintComponent(g);
    }
    @Override
    public void paintComponent(Graphics g) {
        g.setColor(board_bg_color);
        g.fillRect((Settings.board_width-(Settings.buttonSize*2)), (Settings.board_height-Settings.buttonSize), buttonSize,buttonSize);
        Color c = board_bg_color;
        g.setColor(new Color(c.getRed(),c.getGreen(),c.getBlue(),128).brighter());
        g.fillRect(0,0,board_width,board_height);

        g.setFont(new Font(null, Font.PLAIN, buttonSize));

        g.setColor(this.color);

        g.drawString("\""+this.name+"\" hat gewonnen!", boardCenterX,boardCenterY);

        g.setColor(new Color(255, 206, 46));

        for (int i = 0; i < maxStars; i++) {
            g.fillPolygon(stars[i].nextIter());
        }
        reload();
        repaint();
        updateUI();
    }

}
class Star{

    private int xMin;
    private int yMin;
    private int xSize;
    private int ySize;
    private double modifier;
    private int fallSpeed;

    public Star(int xMin, int yMin, int xSize, int ySize, double modifier, int fallSpeed){
        this.xMin = xMin;
        this.yMin = yMin;
        this.xSize = xSize;
        this.ySize = ySize;
        this.modifier = modifier;
        this.fallSpeed = fallSpeed;
    }
    public Polygon nextIter(){

        int spikes = (int)(modifier*10)+2;
        int yMax = yMin+ySize;

        double radius = xSize/2.0;
        double minDist = xSize/6.0;
        double centerX = (xMin+radius);
        double centerY = (yMin+radius);

        int[] xPos = new int[(spikes*2)+1];
        int[] yPos = new int[(spikes*2)+1];

        double PI2 = Math.PI*2;

        double div = PI2/(spikes*2);

        for (int i = 0; i < (spikes*2)+1; i++) {

            double angle = div*i;

            double v = ((i % 2) * (radius - minDist)) + minDist;
            xPos[i] = (int)(centerX+(Math.cos(angle)*v));
            yPos[i] = (int)(centerY+(Math.sin(angle)*v));

        }

        this.yMin+=this.fallSpeed;
        xMin = (int)(xMin+(Math.sin(yMin/(modifier*modifier)*modifier*modifier*modifier*modifier*modifier)));

        return new Polygon(xPos,yPos,(spikes*2));

    }
}
