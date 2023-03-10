package GuiStuff.WinnerScreen;

import Backend.Player;
import GuiStuff.WelcomeGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WinnerScreen extends JFrame{

    public WinnerScreen(Player winner){
        setLayout(new BorderLayout(5, 5));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout(5, 5));

        Icon icon = new ImageIcon(System.getProperty("user.dir") + "/src/main/GuiStuff/WinnerScreen/ChickenDinner.gif");
        JLabel imageLabel = new JLabel(icon);
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        JPanel basePanel = new JPanel();
        basePanel.setOpaque(false);
        basePanel.setLayout(new BorderLayout(5, 5));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2, 2, 5, 5));

        JLabel userLabel = new JLabel("Glückwunsch " + winner.getPlayerName() + ". Du hast das Spiel gewonnen!",JLabel.CENTER);
        userLabel.setForeground(Color.RED);
        userLabel.setFont(new Font("Serif", Font.BOLD, 20));
        userLabel.setBackground(new Color(0,0,0,0));

        topPanel.add(userLabel);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);

        JButton restartButton = new JButton("Neues Spiel starten");
        restartButton.setForeground(Color.BLACK);
        restartButton.setHorizontalTextPosition(AbstractButton.CENTER);
        restartButton.setVerticalTextPosition(AbstractButton.BOTTOM);
        bottomPanel.add(restartButton);
        restartButton.addActionListener(e -> {
            dispose();
            WelcomeGUI welcomeGUI = new WelcomeGUI();
        });

        basePanel.add(topPanel, BorderLayout.CENTER);
        basePanel.add(bottomPanel, BorderLayout.PAGE_END);

        imageLabel.setLayout(new GridBagLayout());
        imageLabel.add(basePanel);

        contentPane.add(imageLabel, BorderLayout.CENTER);
        setContentPane(contentPane);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
 // public static void main(String... args)
 // {
 //     SwingUtilities.invokeLater(new Runnable()
 //     {
 //         Player winner = new Player(1 , 4 , Color.BLACK, "Leo");
 //         public void run()
 //         {
 //             new WinnerScreen(winner);
 //         }
 //     });
 // }
}
