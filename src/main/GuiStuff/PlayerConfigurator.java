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

import static GuiStuff.Settings.*;
import static GuiStuff.Settings.figureAmount;

public class PlayerConfigurator extends JFrame
{
    private int numPlayers;
    private int figureAmount;
    private final Color[] color;
    private final JTextField[] playerNameFields;
    private final JButton[] colorButtons;

    public PlayerConfigurator(int pNumPlayers, int pFigureAmount)
    {
        numPlayers = pNumPlayers;
        figureAmount = pFigureAmount;
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

        for (int i = 0; i < numPlayers; i++)
        {
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

        backButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                dispose();
            }
        });

        JLabel emptyLabel = new JLabel(" ");
        mainPanel.add(emptyLabel);


        JButton confirmButton = new JButton("Confirm");
        confirmButton.setBounds(10, 40, 140, 25);
        mainPanel.add(confirmButton);

        confirmButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                boolean iName = invalidName();
                boolean sColor = invalidColor();
                if(iName || sColor)
                {
                    if(iName && sColor)
                    {
                        JOptionPane.showMessageDialog(null, "Invalid Name and same Color used for multiple Players!","Error", JOptionPane.ERROR_MESSAGE);
                    }
                    else if(iName)
                    {
                        JOptionPane.showMessageDialog(null, "Invalid Name used!","Error", JOptionPane.ERROR_MESSAGE);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Same Color used for multiple Players!","Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else
                {
                    Player[] ps = new Player[numPlayers];
                    for (int i = 0; i < numPlayers; i++)
                    {
                        ps[i] = new Player(i,figureAmount, color[i], playerNameFields[i].getText());
                    }
                    dispose();
                    startGame(ps);
                }
                //setVisible(false);
            }
        });


        add(mainPanel);
        setVisible(true);
    }


    private boolean invalidName()
    {
        boolean emptyName = false;
        for(int n = 0; n < numPlayers; n++)
        {
            if(playerNameFields[n].getText().isBlank())
            {
                emptyName = true;
                break;
            }
        }
        return emptyName;
    }

    private boolean invalidColor()
    {
        boolean sameColor = false;
        for(int n = 0; n < numPlayers - 1; n++)
        {
            if(Objects.equals(color[n], color[n + 1]))
            {
                sameColor = true;
            }
        }
        return sameColor;
    }

    private void startGame(Player[] ps)
    {
        JFrame gameJFame = new JFrame("Game");
        gameJFame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        gameJFame.setResizable(false);
        gameJFame.setUndecorated(true);
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

        board_width = size.width;
        board_height = size.height;
        fieldPerPerson = 10;  // max 10
        playerAmount = 4;  // max 20
        figureAmount = 4;  // max 25

        reload();
        gameJFame.setSize(board_width,board_height);

        gameJFame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Game g = new Game(new Board(ps));

        gameJFame.add(g);

        gameJFame.setVisible(true);
    }


    //public Color setColor(Color c, int i)
    //{
    //    this.color[i] = c;
    //    return c;
    //}

    //public Player[] getPlayerArray(int anzahlSpieler, int anzahlSpielfiguren, String name[], Color color[]) {
    //    Player[] ps = new Player[anzahlSpieler];
    //    for (int i = 0; i < anzahlSpieler; i++)
    //    {
    //        p[i] = new Player(i, anzahlSpielfiguren, color[i], name[i]);
    //    }
    //   return p;
    //}


    public static void main(String[] args)
    {
        try
        {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch (Exception e)
        {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }
    }
}


