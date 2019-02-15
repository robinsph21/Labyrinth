package cs301.up.edu.labyrinth;

import cs301.up.edu.enums.Player;
import cs301.up.edu.game.infoMsg.GameState;


public class LabyrinthGameState extends GameState {

    // to satisfy Serializable interface
    private static final long serialVersionUID = 7737393762469851826L;

    private Player playerTurn;
    private Tile [][] gameBoard = new Tile[7][7];


    public LabyrinthGameState() {

    }

    //Clone Constructor
    public LabyrinthGameState(LabyrinthGameState state) {

    }

    public Player getPlayerTurn() {
        return this.playerTurn;
    }

    public Tile[][] getGameBoard() {
        return gameBoard;
    }
}
