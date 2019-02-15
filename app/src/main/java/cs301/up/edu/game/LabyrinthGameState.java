package cs301.up.edu.game;

import cs301.up.edu.enums.Player;

public class LabyrinthGameState {

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
