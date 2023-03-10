package GuiStuff;


import Backend.Board;
import Backend.Game;
import Backend.Player;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.Random;

import javax.swing.*;

public class PlayerConfigurator extends JFrame{
    private final int numPlayers;
    private final int figureAmount;
    private final int fieldPerPerson;
    private final Color[] color;
    private final JTextField[] playerNameFields;
    private final JButton[] colorButtons;

    public PlayerConfigurator(int pNumPlayers, int pFigureAmount, int pFieldPerPerson){
        numPlayers = pNumPlayers;
        figureAmount = pFigureAmount;
        fieldPerPerson = pFieldPerPerson;
        color = new Color[numPlayers];
        setTitle("Player Customization");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(0, 3, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        playerNameFields = new JTextField[numPlayers];
        colorButtons = new JButton[numPlayers];

        for (int i = 0; i < numPlayers; i++){
            color[i] = new Color((int)(new Random().nextFloat()*255), (int)(new Random().nextFloat()*255), (int)(new Random().nextFloat()*255));
            JLabel playerLabel = new JLabel("Player " + (i + 1) + " Name: ");
            mainPanel.add(playerLabel);

            playerNameFields[i] = new JTextField();
            mainPanel.add(playerNameFields[i]);

            colorButtons[i] = new JButton("Choose Color");
            colorButtons[i].setPreferredSize(new Dimension(120, 30));
            int index = i;
            colorButtons[index].setBackground(color[index]);
            colorButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Color c = JColorChooser.showDialog(null, "Choose Color", Color.WHITE);
                    if(c != null)
                    {
                        color[index] = c;
                    }
                    colorButtons[index].setBackground(color[index]);
                }
            });
            mainPanel.add(colorButtons[i]);
        }


        JButton backButton = new JButton("Back");
        backButton.setBounds(10, 40, 140, 25);
        mainPanel.add(backButton);

        backButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                dispose();
                new WelcomeGUI();
            }
        });

        JLabel emptyLabel = new JLabel(" ");
        mainPanel.add(emptyLabel);


        JButton confirmButton = new JButton("Confirm");
        confirmButton.setBounds(10, 40, 140, 25);
        mainPanel.add(confirmButton);

        confirmButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){

                StringBuilder sb = new StringBuilder();
                if(invalidName()){
                    sb.append("\nInvalid Name used!");
                }
                if (sameNames()){
                    sb.append("\n2 Players cannot have the same Name!");
                }
                if (sameColor()){
                    sb.append("\n2 Players cannot have the same color!");
                }

                if (!sb.isEmpty()){
                    JOptionPane.showMessageDialog(null, sb.toString(),"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

                Settings.board_width = size.width;
                Settings.board_height = size.height;
                Settings.fieldPerPerson = fieldPerPerson;  // max 10
                Settings.playerAmount = numPlayers;  // max 20
                Settings.figureAmount = figureAmount;  // max 25

                Settings.reload();

                Player[] ps = new Player[numPlayers];
                for (int i = 0; i < numPlayers; i++){
                    String rs;
                    try{
                        rs = playerNameFields[i].getText().substring(0,20);
                    }catch (Exception ignore){
                        rs = playerNameFields[i].getText();
                    }
                    ps[i] = new Player(i,figureAmount, color[i], rs);
                }
                dispose();
                startGame(ps);
            }
        });


        add(mainPanel);
        setVisible(true);
    }


    private boolean invalidName(){
        for(int n = 0; n < numPlayers; n++){
            if(playerNameFields[n].getText().isBlank()){
                return true;
            }
        }
        return false;
    }
    private boolean sameNames(){
        for(int n = 0; n < numPlayers - 1; n++){
            for(int i = n + 1; i < numPlayers; i++){
                if(playerNameFields[n].getText().equals(playerNameFields[i].getText())){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean sameColor(){
        for(int n = 0; n < numPlayers - 1; n++){
            for(int i = n + 1; i < numPlayers; i++){
                if(Objects.equals(color[n], color[i])){
                    return true;
                }
            }
        }
        return false;
    }

    private void startGame(Player[] ps){
        JFrame gameJFame = new JFrame("Game");
        gameJFame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        gameJFame.setResizable(false);
        gameJFame.setUndecorated(true);
        gameJFame.setSize(Settings.board_width,Settings.board_height);

        gameJFame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Game g = new Game(new Board(ps),gameJFame);

        gameJFame.add(g);

        gameJFame.setVisible(true);
    }
}