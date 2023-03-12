package GuiStuff;

import javax.swing.*;
import java.awt.*;

public class RuleWindow extends JFrame{
    JTextArea label = new JTextArea("""
            1. Jeder Spieler erhält x Figuren in Farbe y.
            2. Wenn keine Figuren der eigenen Farbe auf dem Feld stehen, wird 3x gewürfelt. Wenn keine 6 gewürfelt wird, ist der\s
            nächste dran.
            3. Wenn eine 6 gewürfelt wird, muss derjenige eine Figur von seinem "Lager" auf den Anfangskreis stellen.\s
            Anschließend wird erneut gewürfelt, und die Figur auf dem Anfangskreis muss zuerst bewegt werden. Selbst dann,\s
            wenn er eine andere Figur oder seine eigene dadurch aus dem Spiel schlägt.
            4. Die 4 Figuren starten immer im Anfangskreis und werden in Pfeilrichtung Richtung Endkreis (Ziel) gezogen.
            5. Die eigene Figur wird um die Augenzahl des Würfels vorwärts bewegt.
            6. Wenn man auf ein Feld setzen könnte, das bereits belegt ist: Wenn auf diesem eine gegnerische Figur steht\s
            -> schlagen, die gegnerische Figur gelangt wieder in ihr Lager.
            Wenn auf diesem eine Figur der eigenen Farbe steht -> andere Figur bewegen oder eigene Figur schlagen.
            7. Über die im Weg stehenden eigenen und feindlichen Figuren wird gesprungen, das besetzte Feld aber mitgezählt.
            8. Hat eine Figur das äußere Kreuz vollständig umrundet, so rückt sie auf die Kreise ihrer Farbe ins Ziel ein.\s
            In die Endfelder kann nur eingerückt werden, wenn die Würfelaugen nicht die Zahl der freien Felder übersteigt.\s
            Überspringen im Haus geht, aber doppelt besetzen nicht.
            9. Der Spieler, dessen Felder im Haus als erstes besetzt sind, hat gewonnen.""");
    public RuleWindow(){
        setResizable(false);
        setTitle("Regeln");//funktioniert noch nicht
        label.setFont(new Font(null, Font.PLAIN,15));
        label.setBounds(0,0,800,400);
        add(label);
        setTitle("Mensch, ärgert euch nicht");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
