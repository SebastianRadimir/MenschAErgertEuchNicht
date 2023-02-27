package GuiStuff;

import javax.swing.*;
import java.awt.*;

public class NewWindow extends JFrame{
    JTextArea label = new JTextArea("1. Jeder Spieler erhält x Figuren in Farbe y.\n" +
            "2. Jeder würfelt 1x und derjenige mit der größten Zahl fängt an. Bei gleicher Zahl wird erneut gewürfelt.\n" +
            "3. Wenn keine Figuren der eigenen Farbe auf dem Feld stehen, wird 3x gewürfelt. Wenn keine 6 gewürfelt wird, ist der \n" +
            "nächste dran.\n" +
            "4. Wenn eine 6 gewürfelt wird, muss derjenige eine Figur von seinem \"Lager\" auf den Anfangskreis stellen. \n" +
            "Anschließend wird erneut gewürfelt und die Figur auf dem Anfangskreis muss zuerst bewegt werden. Selbst dann, \n" +
            "wenn er eine andere Figur oder seine eigene dadurch aus dem Spiel schlägt.\n" +
            "5. Die 4 Figuren starten immer im Anfangskreis und werden in Pfeilrichtung Richtung Endkreis (Ziel) gezogen.\n" +
            "6. Die eigene Figur wird um die Augenzahl des Würfels vorwärts bewegt.\n" +
            "7. Wenn man auf ein Feld setzen könnte, das bereits belegt ist: Wenn auf diesem eine gegnerische Figur steht \n" +
            "-> schlagen, Figur gelangt wieder zum Lager.\n" +
            "Wenn auf diesem eine freundliche Figur steht -> andere Figur bewegen oder eigene Figur schlagen.\n" +
            "8. Über die im Weg stehenden eigenen und feindlichen Figuren wird gesprungen, das besetzte Feld aber mitgezählt.\n" +
            "9. Hat eine Figur das äußere Kreuz vollständig umrundet, so rückt dieselbe auf die Kreise ihrer Farbe ins Ziel ein. \n" +
            "In die Endfelder kann nur eingerückt werden, wenn die exakte Zahl auf die freien Felder gewürfelt wird.\n" +
            "10. Wenn alle Felder besetzt sind, hat dieser Spieler gewonnen.");
    NewWindow(){
        setTitle("Regeln");//funktioniert noch nicht
        label.setFont(new Font(null, Font.PLAIN,15));
        label.setBounds(0,0,800,400);
        add(label);
        setTitle("Mensch ärgere dich nicht");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
