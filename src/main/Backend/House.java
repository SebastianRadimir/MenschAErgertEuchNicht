package Backend;

import java.awt.*;

import static GuiStuff.Settings.*;

public class House {

    private final Player owner;
    public Field[] rooms;

    public House(Player owner, Field[] rooms){

        this.rooms = rooms;
        this.owner = owner;

    }

    public boolean[] getFreeRooms(){

        boolean[] returnArr = new boolean[rooms.length];

        for (int i = 0; i < rooms.length; i++) {
            returnArr[i] = !rooms[i].isOccupied();
        }

        return returnArr;
    }
    public Field getRoom(int i){
        if (i>=rooms.length){
            return null;
        }
        return rooms[i];
    }
    public Field[] getRooms(){
        return rooms;
    }

    public void draw(Graphics g, int mpx, int mpy){

        g.setColor(board_line_color);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(fieldSize/5.0f));

        Color c = owner.getColor();
        for (Field room : rooms) {
            int x = room.getX();
            int y = room.getY();
            room.draw(g,mpx ,mpy);
            g.setColor(c);
            g.drawOval(x-(fieldSize/2), y-(fieldSize/2), fieldSize,fieldSize);
        }
    }

}
