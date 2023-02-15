package GuiStuff;

import Backend.Player;

import javax.swing.*;
import javax.swing.UIManager.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomeGUI extends JFrame {
    private JLabel AnzSpieler;
    private JLabel AnzSpielfiguren;
    private JLabel AnzSpielfelder;

    private JButton StartGameButton;

    private JComboBox AnzahlSpielfelderText;

    private JComboBox anzahlDerSpielfiguren;

    private JComboBox anzahlDerSpieler;

    private JPanel MainPanel;
    private JButton Rules;

    private int anzahlSpielfelder;
    private int anzahlSpieler;
    private int anzahlSpielfiguren;

    public WelcomeGUI() {
        setContentPane(MainPanel);
        AnzahlSpielfelderText.setSelectedIndex(7);
        anzahlDerSpielfiguren.setSelectedIndex(3);
        anzahlDerSpieler.setSelectedIndex(2);

        setTitle("Mensch ärgere dich nicht");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        StartGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (AnzahlSpielfelderText.getSelectedIndex() == -1 || anzahlDerSpieler.getSelectedIndex() == -1 || anzahlDerSpielfiguren.getSelectedIndex() == -1) { // || containsOnlyValues(AnzSpielerText) || containsOnlyValues(AnzahlSpielfigurenText)
                    JOptionPane.showMessageDialog(null, "Fehlerhafte Eingabe");
                } else {
                    anzahlSpielfiguren = Integer.parseInt(anzahlDerSpielfiguren.getSelectedItem().toString().trim());
                    anzahlSpieler = Integer.parseInt(anzahlDerSpieler.getSelectedItem().toString().trim());
                    anzahlSpielfelder = Integer.parseInt(AnzahlSpielfelderText.getSelectedItem().toString().trim());

                    PlayerConfigurator pc = new PlayerConfigurator(anzahlSpieler, anzahlSpielfiguren);          //start Customizer

                    anzahlDerSpielfiguren.setSelectedIndex(3);
                    anzahlDerSpieler.setSelectedIndex(2);
                    AnzahlSpielfelderText.setSelectedIndex(7);
                }
            }
        });
        Rules.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == Rules) {
                        NewWindow ruleWindow = new NewWindow();
                }
            }
        });

    }

    public static void main(String[] args) {
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }
        new WelcomeGUI();
    }

    public int getAnzahlSpielfelder(){
        return anzahlSpielfelder;
    }

    public int getAnzahlSpieler(){
        return anzahlSpieler;
    }

    public int getAnzahlSpielfiguren(){
        return anzahlSpielfiguren;
    }
}