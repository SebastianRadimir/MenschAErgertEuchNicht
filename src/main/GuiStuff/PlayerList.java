package GuiStuff;

import Backend.Figure;
import Backend.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PlayerList extends JPanel {

    //Class Variables-----------------------------------
    private int heightPerPlayer = 100;
    private int widthOfWindow = 200;
    private final Player[] playerlist;
    private final ArrayList<JPanel> playerPanelList = new ArrayList<>();
    private final ArrayList<JPanel> colorPanelList;
    private Color defaultBackground = Color.WHITE;
    //Constructor---------------------------------------
    /**
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

    /**
     * initilaze a PlayerList JPanel where every Player is listed
     * @param players_p
     */
    public PlayerList(Player[] players_p){
        playerlist = players_p;
        createPanel();
    }
    //Functions-----------------------------------------
    private void createPanel(){
        setLayout(new GridLayout(playerlist.length + 1,2));
        setBackground(defaultBackground);

        JPanel topPanel = new JPanel(new BorderLayout());

        JPanel actualPlayer = new JPanel();
        actualPlayer.setSize(30,getHeight());
        actualPlayer.setBackground(new Color(150,0,0));
        topPanel.add(actualPlayer,BorderLayout.LINE_START);
        topPanel.add(new JLabel("Spielerliste"),BorderLayout.CENTER);
        topPanel.add(new JLabel("Figuren Zuhause   "),BorderLayout.LINE_END);


        add(topPanel);

        //add for every player a new row with name and maybe stats
        for (Player player:playerlist) {
            JPanel playerPanel = new JPanel(new BorderLayout());
            //JPanel playerPanel = new JPanel(new GridLayout(1,3));
            playerPanel.setName(player.getPlayerName());

            JLabel playerLabel = new JLabel(player.getPlayerName());
            playerPanel.add(playerLabel,BorderLayout.CENTER);

            int figureThatAreHome = 0;
            Figure[] figureList = player.getFigures();
            for(Figure figureSingle:figureList){
                if(figureSingle.isHome()){
                    figureThatAreHome += 1;
                }
            }

            add(createRowWithPlayerAndStats(player.getPlayerName(), figureThatAreHome + "/" + player.getFigures().length))
            JLabel labelFigureHome = new JLabel();
            playerPanel.add(labelFigureHome,BorderLayout.LINE_END);

            playerPanelList.add(playerPanel);
            add(playerPanel);
        }
    }

    private JPanel createRowWithPlayerAndStats(String playerName_p,String figuresAtHome){
        JPanel rowPanel = new JPanel(new BorderLayout());

        JPanel statusPanel = new JPanel();
        statusPanel.setSize(30,rowPanel.getHeight());
        statusPanel.setBackground(new Color(0,150,0));
        rowPanel.add(statusPanel,BorderLayout.LINE_START);

        JPanel nameAndStatsPanel = new JPanel(new GridLayout(1,2));
        nameAndStatsPanel.add(new Label(playerName_p),BorderLayout.LINE_START);
        nameAndStatsPanel.add(new Label(figuresAtHome),BorderLayout.CENTER);

        rowPanel.add(nameAndStatsPanel,BorderLayout.CENTER);

        return rowPanel;
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

    /**
     * allaws to set the defaultBackgroundColor
     * @param defaultColor Color Object
     */
    public void setDefaultBackground(Color defaultColor){
        defaultBackground = defaultColor;
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
