package Backend;

import java.awt.*;

import static GuiStuff.Settings.*;

public class House {

    private final Player owner;
    private final Field[] rooms;

    public House(Player owner, Field[] rooms){

        this.rooms = rooms;
        this.owner = owner;

    }
    public Field getRoom(int i){
        if (i>=rooms.length || i<0){
            return null;
        }
        return rooms[i];
    }
    public boolean movePlayerInHome(Field selected, int amount){

        boolean fieldIsOurs = false;
        for (Field room:rooms) {
            if (room.equals(selected)){
                fieldIsOurs = true;
                break;
            }
        }
        if (amount<=0 || !fieldIsOurs || !selected.isOccupied()){
            return false;
        }
        int selectedFieldI = selected.getIndex();
        Field fieldInQuestion = getRoom(selectedFieldI+amount);
        if (fieldInQuestion != null){
            if (!fieldInQuestion.isOccupied()){
                Figure f = selected.clearField();
                fieldInQuestion.setFigure(f);

                return true;
            }
        }
        return false;
    }

    public Field getField(int xPos, int yPos){

        for (Field f : rooms) {

            int x = f.getX();
            int y = f.getY();

            if (Math.sqrt(((xPos - x) * (xPos - x)) + ((yPos - y) * (yPos - y))) <= (fieldSize / 2.0)) {
                return f;
            }
        }
        return null;
    }

    public void draw(Graphics g, int mpx, int mpy){

        g.setColor(board_line_color);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(fieldSize/5.0f));

        Color c = owner.getColor();
        for (Field room : rooms) {
            int x = room.getX();
            int y = room.getY();
            room.draw(g, mpx, mpy);
            g.setColor(c);
            g.drawOval(x - (fieldSize / 2), y - (fieldSize / 2), fieldSize, fieldSize);

            if (Math.sqrt(((mpx - x) * (mpx - x)) + ((mpy - y) * (mpy - y))) <= (fieldSize / 2.0)) {
                g2.setStroke(new BasicStroke(fieldSize/3.0f));
                g.setColor(highlight_color);
                g.drawOval(x - (fieldSize / 2), y - (fieldSize / 2), fieldSize, fieldSize);
            }
        }
    }

}
