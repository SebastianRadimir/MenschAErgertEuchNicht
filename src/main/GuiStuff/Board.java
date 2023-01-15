package GuiStuff;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

import static GuiStuff.Settings.*;
import static java.lang.Math.min;

public class Board extends JPanel {
    private int randX;
    private int randY;
    private int playerAmount;
    private int figureAmount;
    private double PI2 = Math.PI*2;
    private int circlePrecision = 360;
    private double circlePrecisionInterval = PI2/circlePrecision;
    private int xOffset = board_width/2;
    private int yOffset = board_height/2;
    private int circleSize = min(xOffset/2,yOffset/2);
    private double perc = 0.5;
    private double amplification = 100;
    private int tileAmo;
    private int tileSize = 30;

    public int[] xArr;
    public int[] yArr;

    private int[] xPlayerPos;
    private int[] yPlayerPos;
    private int playerDistance = min(xOffset-100,yOffset-100);
    public Board(int playerAmount, int figureAmount) {
        this.playerAmount = playerAmount;
        this.figureAmount = figureAmount;

        tileAmo = playerAmount*10;

        randX = new Random().nextInt();
        randY = new Random().nextInt();

        this.setSize(board_width, board_height);

        this.setBackground(board_bg_color);

        xArr = new int[circlePrecision];
        yArr = new int[circlePrecision];

        for (int i = 0; i < circlePrecision; i++) {

            double ni = circlePrecisionInterval*i;

            double xPos = (circleSize*Math.cos(ni)) + xOffset;
            double yPos = (circleSize*Math.sin(ni)) + yOffset;

            double pn = PerlinNoise.noise((Math.cos(ni)/perc)+randX,(Math.sin(ni)/perc)+randY);

            xArr[i] = (int)(xPos+(pn*amplification));
            yArr[i] = (int)(yPos+(pn*amplification));

        }

        xPlayerPos = new int[playerAmount];
        yPlayerPos = new int[playerAmount];

        for (int i = 0; i < playerAmount; i++) {

            double ni = (PI2/playerAmount)*i;

            xPlayerPos[i] = (int)(playerDistance*Math.cos(ni)) + xOffset;
            yPlayerPos[i] = (int)(playerDistance*Math.sin(ni)) + yOffset;
        }

    }
    @Override
    public void paintComponent(Graphics g){

        g.setColor(board_bg_color);
        g.fillRect(0,0,board_width+10,board_height+10);

        g.setColor(board_line_color);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(tileSize/2));
        g.drawPolygon(xArr, yArr,circlePrecision);

        g.setColor(board_tile_color);
        for (int i = 0; i < tileAmo; i++) {

            double ni = (PI2/tileAmo)*i;

            double xPos = (circleSize*Math.cos(ni)) + xOffset;
            double yPos = (circleSize*Math.sin(ni)) + yOffset;

            double pn = PerlinNoise.noise((Math.cos(ni)/perc)+randX,(Math.sin(ni)/perc)+randY);

            g.fillOval((int)(xPos+(pn*amplification))-(tileSize/2), (int)(yPos+(pn*amplification))-(tileSize/2), tileSize, tileSize);
        }

        for (int p = 0; p < playerAmount; p++) {

            for (int i = 0; i < figureAmount; i++) {

                double ni = (PI2/figureAmount)*i;

                int x = (int)(tileSize*Math.cos(ni)) + xPlayerPos[p];
                int y = (int)(tileSize*Math.sin(ni)) + yPlayerPos[p];

                g.fillOval(x,y,tileSize,tileSize);
            }
        }

    }
}
