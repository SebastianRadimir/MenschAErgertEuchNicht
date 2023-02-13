package GuiStuff;

import Backend.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PlayerList extends JPanel {

    //Class Variables-----------------------------------
    private int heightPerPlayer = 100;
    private int widthOfWindow = 200;
    private Player[] playerlist;
    private ArrayList<JLabel> playerLabelsList = new ArrayList<>();
    //Constructor---------------------------------------
    public PlayerList(Player[] playerlist_p, int heightPerPlayer_p,int widthOfWindow_p){
        playerlist = playerlist_p;
        heightPerPlayer = heightPerPlayer_p;
        widthOfWindow = widthOfWindow_p;
        createPanel();
    }
    public PlayerList(Player[] players_p){
        playerlist = players_p;
        createPanel();
    }
    //Functions-----------------------------------------
    private void createPanel(){
        setLayout(new GridLayout(playerlist.length + 1,2));
        setBackground(Color.gray);
        setSize(widthOfWindow - 10,heightPerPlayer * playerlist.length + 100);

        JLabel playlerListLabel = new JLabel("Spielerliste");
        add(playlerListLabel);
        JLabel figuesHomeLabel = new JLabel("Figuren Zuhause");
        add(figuesHomeLabel);

        for (Player player:playerlist) {
            JLabel label = new JLabel(player.getPlayerName());
            add(label);


            JLabel labelFigureHome = new JLabel(player.getPlayerIndex() + "/" + player.getFigures().length);
            add(labelFigureHome);
        }
    }

    //manual test main function
    public static void main (String[] args){
        JFrame frame = new JFrame();

        ArrayList<Player> listOfPlayer = new ArrayList<>();
        for( int i = 1; i <= 4; i++){
            listOfPlayer.add(new Player(i,5,Color.WHITE,"Player " + i));
        }

        frame.add(new PlayerList(listOfPlayer.toArray(new Player[listOfPlayer.size()])));
        frame.setSize(200,800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
