package GuiStuff;

import java.io.*;
import javax.swing.*;
import javax.swing.UIManager.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomeGUI extends JFrame {
    private JTextField AnzahlSpielfigurenText;
    private JTextField AnzSpielerText;

    private JLabel AnzSpieler;
    private JLabel AnzSpielfiguren;
    private JLabel AnzSpielfelder;

    private JButton StartGameButton;

    private JComboBox AnzahlSpielfelderText;

    private JPanel MainPanel;
    private JButton Rules;

    private int AnzahlSpielfelder;
    private int anzahlSpieler;
    private int anzahlSpielfiguren;
    private int anzahlSpielfelder;

    public WelcomeGUI() {
        setContentPane(MainPanel);
        AnzahlSpielfelderText.setSelectedIndex(-1);
        AnzahlSpielfigurenText.setText("");
        AnzSpielerText.setText("");
        setTitle("Mensch ärgere dich nicht");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        StartGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (AnzahlSpielfigurenText.getText().equals("") || AnzSpielerText.getText().equals("") || AnzahlSpielfelderText.getSelectedIndex() == -1) { // || containsOnlyValues(AnzSpielerText) || containsOnlyValues(AnzahlSpielfigurenText)
                    JOptionPane.showMessageDialog(null, "Fehlerhafte Eingabe");
                } else {
                    anzahlSpielfiguren = Integer.parseInt(AnzahlSpielfigurenText.getText());
                    anzahlSpieler = Integer.parseInt(AnzSpielerText.getText());
                    AnzahlSpielfelder = Integer.parseInt(AnzahlSpielfelderText.getSelectedItem().toString().trim());

                    AnzahlSpielfigurenText.setText("");
                    AnzSpielerText.setText("");
                    AnzahlSpielfelderText.setSelectedIndex(-1);
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

//   private boolean containsOnlyValues(JTextField field){
//       if (field.getText().matches(".*[a-z].*")) {
//           return false;
//       }
//       return true;
//   }
}