package GuiStuff;

import Backend.Player;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

import static GuiStuff.Settings.*;

public class Winscreen extends JPanel {

    private final Color color;
    private final String name;
    private final Star[] stars;
    private final Star specialSnowflake;
    int maxStars = 500;

    public Winscreen(Player winingP){

        this.color = winingP.getColor();
        this.name = winingP.getPlayerName();
        stars = new Star[maxStars];
        for (int i = 0; i <maxStars; i++) {
            int s = new Random().nextInt(10,50);
            stars[i] = new Star(new Random().nextInt(1,board_width+(buttonSize*4)), new Random().nextInt(-board_width*2,-10),s, new Random().nextInt(3,100)/100.0, new Random().nextInt(3,9));
        }
        specialSnowflake = new Star(-10,0, 1, new Random().nextInt(3,100)/100.0, 3);
    }
    public boolean passedEnough(){
        return specialSnowflake.isDeepEnough();
    }

    public void paintWinner(Graphics g) {

        paintComponent(g);
    }
    @Override
    public void paintComponent(Graphics g) {
        g.setColor(board_bg_color);
        g.fillRect((Settings.board_width-(Settings.buttonSize*2)), (Settings.board_height-Settings.buttonSize), buttonSize,buttonSize);
        Color c = board_bg_color;
        g.setColor(new Color(c.getRed(),c.getGreen(),c.getBlue(),128+32).brighter());
        g.fillRect(0,0,board_width,board_height);

        g.setFont(new Font(null, Font.PLAIN, buttonSize));

        g.setColor(this.color);

        String winTitle = "\""+this.name+"\" hat gewonnen!";
        int l = winTitle.length();


        g.drawString(winTitle, (int)(boardCenterX-((l/4.0)*buttonSize)), boardCenterY);

        g.setColor(new Color(255, 206, 46));

        for (int i = 0; i < maxStars; i++) {
            g.fillPolygon(stars[i].nextIter());
        }
        g.fillPolygon(specialSnowflake.nextIter());

        reload();
        repaint();
        updateUI();
    }

}
class Star{

    private final int xMin;
    private int yMin;
    private final int xSize;
    private final double modifier;
    private final int fallSpeed;

    public Star(int xMin, int yMin, int size, double modifier, int fallSpeed){
        this.xMin = xMin;
        this.yMin = yMin;
        this.xSize = size;
        this.modifier = modifier;
        this.fallSpeed = fallSpeed;
    }
    public Polygon nextIter(){

        int spikes = (int)(modifier*10)+2;

        double radius = xSize/2.0;
        double minDist = xSize/6.0;
        double centerX = (xMin+radius);
        double centerY = (yMin+radius);

        int[] xPos = new int[(spikes*2)+1];
        int[] yPos = new int[(spikes*2)+1];

        double PI2 = Math.PI*2;

        double div = PI2/(spikes*2);

        for (int i = 0; i < (spikes*2)+1; i++) {

            double angle = (div*i)+((yMin)/50.0);

            double v = ((i % 2) * (radius - minDist)) + minDist;
            xPos[i] = (int)(centerX+(Math.cos(angle)*v));
            yPos[i] = (int)(centerY+(Math.sin(angle)*v));

        }

        this.yMin+=this.fallSpeed;
        //xMin = (int)(xMin+(Math.sin(yMin/(modifier))*modifier*modifier*modifier*modifier*modifier));

        return new Polygon(xPos,yPos,(spikes*2));

    }
    public boolean isDeepEnough(){
        return yMin>=board_height;
    }
}
