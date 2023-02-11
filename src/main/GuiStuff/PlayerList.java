package GuiStuff;

import Backend.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerList extends JPanel {

    private int heightPerPlayer = 100;



    public PlayerList(Player[] playerlist){
        setLayout(new FlowLayout());
        setBackground(Color.gray);
        setSize(200,heightPerPlayer * playerlist.length + 100);

        JLabel headerLabel = new JLabel("Spielerliste");
        add(headerLabel);

        for (Player player:playerlist) {
            JLabel label = new JLabel();
            label.setSize(200,heightPerPlayer * playerlist.length);
            label.setText(player.getPlayerName() + "                   ");
            add(label);
        }
        
    }

    public static void main (String[] args){
        JFrame frame = new JFrame();

        ArrayList<Player> listOfPlayer = new ArrayList<>();
        for( int i = 1; i <= 4; i++){
            listOfPlayer.add(new Player(i,1,Color.WHITE,"Player " + i));
        }

        frame.add(new PlayerList(listOfPlayer.toArray(new Player[listOfPlayer.size()])));
        frame.setSize(200,800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
