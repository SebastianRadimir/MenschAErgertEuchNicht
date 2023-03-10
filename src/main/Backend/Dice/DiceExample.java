package Backend.Dice;

import java.awt.*;

import static GuiStuff.Settings.board_height;
import static GuiStuff.Settings.board_width;

public class DiceExample {

    public static void main(String[] args) {
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

        board_width = size.width;
        board_height = size.height;

        int d = DiceDialogManager.showDialog();
        System.out.println("value:" + d);
    }
}
