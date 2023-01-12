package GuiStuff;

import javax.swing.*;
import java.awt.*;

import static GuiStuff.Settings.*;

public class Board extends JPanel {

    public Board() {

        this.setSize(board_width, board_height);
        ImageIcon field = new ImageIcon("src/images/field.png");
        JLabel field_image = new JLabel(field);
        this.add(field_image);

        this.setBackground(new Color(board_bg_color));

    }
}
