package GuiStuff;

import Backend.Figure;
import Backend.Player;

import javax.swing.*;
import java.awt.*;
import java.net.CookieHandler;
import java.util.ArrayList;

public class PlayerList extends JPanel {

    //Class Variables-----------------------------------
    private int heightPerPlayer = 100;
    private int widthOfWindow = 200;
    private final Player[] playerlist;
    private ArrayList<JPanel> playerPanelList = new ArrayList<>();
    private Color defaultBackground = Color.WHITE;
    //Constructor---------------------------------------

    /***
     * initilaze a PlayerList JPanel where every Player is listed
     * @param playerlist_p
     * @param heightPerPlayer_p
     * @param widthOfWindow_p
     */
    public PlayerList(Player[] playerlist_p, int heightPerPlayer_p,int widthOfWindow_p){
        playerlist = playerlist_p;
        heightPerPlayer = heightPerPlayer_p;
        widthOfWindow = widthOfWindow_p;
        createPanel();
    }

    /***
     * initilaze a PlayerList JPanel where every Player is listed
     * @param players_p
     */
    public PlayerList(Player[] players_p){
        playerlist = players_p;
        createPanel();
    }
    //Functions-----------------------------------------
    private void createPanel(){
        setLayout(new GridLayout(playerlist.length + 1,1));
        setBackground(Color.gray);

        JPanel topPanel = new JPanel(new GridLayout(1,2));
        topPanel.add(new JLabel("Spielerliste"));
        topPanel.add(new JLabel("Figuren Zuhause"));
        add(topPanel);

        //add for every player a new row with name and maybe stats
        for (Player player:playerlist) {
            JPanel playerPanel = new JPanel(new GridLayout(1,2));
            playerPanel.setName(player.getPlayerName());

            JLabel playerLabel = new JLabel(player.getPlayerName());
            playerPanel.add(playerLabel);

            int figureThatAreHome = 0;
            Figure[] figureList = player.getFigures();
            for(Figure figureSingle:figureList){
                if(figureSingle.isHome()){
                    figureThatAreHome += 1;
                }
            }

            JLabel labelFigureHome = new JLabel(figureThatAreHome + "/" + player.getFigures().length);
            playerPanel.add(labelFigureHome);

            playerPanelList.add(playerPanel);
            add(playerPanel);
        }
    }

    /**
     * Sets the whole playerPanel to a green Color and resets every other playerPanel to the defaultColor
     * @param playerName insert the playername, u can use player.getName()
     */
    public void setPlayerToGreen(String playerName){
        for(JPanel panel:playerPanelList){
            panel.setBackground(defaultBackground);
            if(playerName.equals(panel.getName())){
                panel.setBackground(new Color(0,150,0));
            }
        }
    }


    /***
     * new main function to test manuel the JPanel
     */
    public static void main (String[] args){
        JFrame frame = new JFrame();

        ArrayList<Player> listOfPlayer = new ArrayList<>();
        for( int i = 1; i <= 3; i++){
            listOfPlayer.add(new Player(i,3,Color.WHITE,"Player " + i));
        }
        PlayerList test = new PlayerList(listOfPlayer.toArray(new Player[listOfPlayer.size()]));


        frame.add(test);
        frame.setSize(300,200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        /*
        //my own "sleep" thing xD
        System.out.println("1 green");
        test.setPlayerToGreen("Player 1");
        int i = 0;
        while (i <= 5000000){
            i = i + 1;
            System.out.println(i);
        }
        System.out.println("2 green");
        test.setPlayerToGreen("Player 2");
        */
    }
}
