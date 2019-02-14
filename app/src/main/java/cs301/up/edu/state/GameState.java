package cs301.up.edu.state;

import cs301.up.edu.enums.Player;

public class GameState {

    private Player playerTurn;
    private Tile [][] gameBoard = new Tile[7][7];


    public GameState() {

    }

    //Clone Constructor
    public GameState(GameState state) {

    }

    public Player getPlayerTurn() {
        return this.playerTurn;
    }

    public Tile[][] getGameBoard() {
        return gameBoard;
    }
}
