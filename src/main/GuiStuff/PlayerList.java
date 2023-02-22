package GuiStuff;

import Backend.Figure;
import Backend.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PlayerList extends JPanel {

    //Class Variables-----------------------------------
    private int heightPerPlayer = 100;
    private int widthOfWindow = 200;
    private final Player[] playerlist;
    private final ArrayList<JPanel> colorPanelList = new ArrayList<>();
    private String playerNameTitle = "Spielerliste";
    private String figuresAtHomeTitle = "Figuren Zuhause";
    private final Color notPlayingColor = new Color(200,0,0);
    private final Color isPlayingColor = new Color(0,200,0);
    private Player actualPlayer;
    private Color defaultBackground;
    //Constructor---------------------------------------
    /**
     * initilaze a PlayerList JPanel where every Player is listed
     * @param playerlist_p Array of all Players that need to be shown
     * @param heightPerPlayer_p Height per Player
     * @param widthOfWindow_p Width of the whole Window
     */
    public PlayerList(Player[] playerlist_p, int heightPerPlayer_p,int widthOfWindow_p){
        playerlist = playerlist_p;
        defaultBackground = Settings.board_bg_color;
        heightPerPlayer = heightPerPlayer_p;
        widthOfWindow = widthOfWindow_p;
        createPanel();
    }

    /**
     * initilaze a PlayerList JPanel where every Player is listed
     * @param players_p Array of all Players that need to be shown
     */
    public PlayerList(Player[] players_p){
        playerlist = players_p;
        defaultBackground = Settings.board_bg_color;
        createPanel();
        actualPlayerUpdater();
    }
    //Functions-----------------------------------------
    private void createPanel(){
        setLayout(new GridLayout(playerlist.length + 1,1));
        setSize(widthOfWindow,(playerlist.length + 1) * heightPerPlayer + 10);
        setBackground(defaultBackground);

        add(createRowWithPlayerAndStats(defaultBackground,playerNameTitle,figuresAtHomeTitle));

        //add for every player a new row with Statuscolor,Name and Figures at Home
        for (Player player:playerlist) {
            int figureThatAreHome = 0;
            Figure[] figureList = player.getFigures();
            for(Figure figureSingle:figureList){
                if(figureSingle.isHome()){
                    figureThatAreHome += 1;
                }
            }
            add(createRowWithPlayerAndStats(player.getColor(),player.getPlayerName(), figureThatAreHome + "/" + player.getFigures().length));
        }
    }
    private JPanel createRowWithPlayerAndStats(Color playerColor_p,String playerName_p,String figuresAtHome_p){
        JPanel rowPanel = new JPanel(new BorderLayout());
        rowPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));

        JPanel statusPanel = new JPanel();
        statusPanel.setName(playerName_p);
        statusPanel.setSize(30,rowPanel.getHeight());
        statusPanel.setBackground(notPlayingColor);
        colorPanelList.add(statusPanel);
        rowPanel.add(statusPanel,BorderLayout.LINE_START);

        JPanel nameAndStatsPanel = new JPanel(new GridLayout(1,2));
        nameAndStatsPanel.setBorder(BorderFactory.createLineBorder(playerColor_p,5));
        nameAndStatsPanel.setBackground(defaultBackground);

        JLabel playerNameLabel = new JLabel(playerName_p);
        playerNameLabel.setForeground(invertColor(defaultBackground));
        nameAndStatsPanel.add(playerNameLabel,BorderLayout.LINE_START);

        JLabel figuresAtHomeLabel = new JLabel(figuresAtHome_p);
        figuresAtHomeLabel.setForeground(invertColor(defaultBackground));
        nameAndStatsPanel.add(figuresAtHomeLabel,BorderLayout.CENTER);

        rowPanel.add(nameAndStatsPanel,BorderLayout.CENTER);

        return rowPanel;
    }
    private Color invertColor(Color c){
        //Bruda akzeptier mal so #StackOverFlow
        return (299 * c.getRed() + 587 * c.getGreen() + 114 * c.getBlue()) / 1000.0 >= 128 ? Color.black : Color.white;
    }
    private void actualPlayerUpdater(){
        Runnable helloRunnable = () -> updateactuallPlayerColor();
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(helloRunnable, 1000, 200, TimeUnit.MILLISECONDS);
    }
    private void updateactuallPlayerColor(){
        if(colorPanelList.get(0).getBackground() != actualPlayer.getColor()){
            for(JPanel panel:colorPanelList){
                if(playerNameTitle.equals(panel.getName())){
                    panel.setBackground(actualPlayer.getColor());
                }
            }
        }
    }

    /**
     * Sets the whole playerPanel to a green Color and resets every other playerPanel to the defaultColor
     * @param playerName_p insert the playername, u can use player.getName()
     */
    public void setPlayerToGreen(String playerName_p){
        for(JPanel panel:colorPanelList){
            panel.setBackground(notPlayingColor);
            if(playerName_p.equals(panel.getName())){
                panel.setBackground(isPlayingColor);
                for (Player p:playerlist) {
                    if(p.getPlayerName().equals(playerName_p)){
                        actualPlayer = p;
                    }
                }
            }
        }
    }
    /**
     * Sets the whole playerPanel to a green Color and resets every other playerPanel to the defaultColor
     * @param pIndex insert the player Index
     */
    public void setPlayerToGreen(int pIndex){
        for(Player p: playerlist){
            if (p.getPlayerIndex() == pIndex){
                setPlayerToGreen(p.getPlayerName());
                return;
            }
        }
    }
}