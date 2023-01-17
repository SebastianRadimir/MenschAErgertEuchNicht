package Backend;

public class Board {

    private Player[] players;
    private int currentPlayerIndex;
    private int playerAmount;

    public Board(Player[] players){
        this.players = players;
        currentPlayerIndex = 0;
        playerAmount = this.players.length;


    }

    public int nextPlayer(){
        currentPlayerIndex = (currentPlayerIndex+1)%this.playerAmount;
        return currentPlayerIndex;
    }
}
