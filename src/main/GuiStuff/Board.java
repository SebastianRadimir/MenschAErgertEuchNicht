package GuiStuff;

import javax.swing.*;
import java.awt.*;

import static GuiStuff.Settings.*;

public class Board extends JPanel {

    public Board() {

        this.setSize(board_width, board_height);

        this.setBackground(new Color(board_bg_color));


    }
}
