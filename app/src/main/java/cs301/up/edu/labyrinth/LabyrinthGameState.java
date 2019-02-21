package cs301.up.edu.labyrinth;

import java.util.ArrayList;
import java.util.List;

import cs301.up.edu.enums.Arrow;
import cs301.up.edu.enums.Player;
import cs301.up.edu.enums.TileType;
import cs301.up.edu.enums.TreasureType;
import cs301.up.edu.game.infoMsg.GameState;


public class LabyrinthGameState extends GameState {

    // to satisfy Serializable interface
    private static final long serialVersionUID = 7737393762469851826L;

    // Constants
    private final static int NUM_RANDOM_PIECES = 34;

    // Instance Variables for LabyrinthState
    private Player playerTurn;
    private Tile [][] gameBoard = new Tile[7][7];
    private Tile currentTile;
    private List<List<TreasureType>> treasureDecks =
            new ArrayList<>(4);
    private Arrow disabledArrow;


    public LabyrinthGameState() {

        // First player turn is randomized
        double randomChoice = Math.random();
        if (randomChoice < 0.25) {
            this.playerTurn = Player.RED;
        } else if (randomChoice < 0.5) {
            this.playerTurn = Player.GREEN;
        } else if (randomChoice < 0.75) {
            this.playerTurn = Player.BLUE;
        } else {
            this.playerTurn = Player.YELLOW;
        }

        // Generate arraylist of all randomized pieces for board
        List<Tile> randomPieces = new ArrayList<>(NUM_RANDOM_PIECES);
        for (int i = 0; i < NUM_RANDOM_PIECES; i++) {
            new Tile(TileType.STRAIGHT, 0, TreasureType.NONE);
        }

        // Initialize Game Board
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if ((i % 2 != 0) || (j % 2 != 0)) {
                    // Non-movable piece
                } else {
                    // Movable Piece
                    this.gameBoard[i][j] = randomPieces.remove(0);
                }
            }
        }

        // Set current tile to last piece
        this.currentTile = randomPieces.remove(0);


    }


    /**
     * Copy Constructor makes a deep copy of the a gamestate variable
     *
     * @param state The state of game that needs to be copied
     */
    public LabyrinthGameState(LabyrinthGameState state) {
        /**
        this.playerTurn = state.getPlayerTurn();

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                this.getGameBoard()[i][j] = new Tile(
                        state.getGameBoard()[i][j].getType(),
                        state.getGameBoard()[i][j].getRotation(),
                        state.getGameBoard()[i][j].getTreasure() );
            }
        }

        this.setCurrentTile(new Tile(
                state.getCurrentTile().getType(),
                state.getCurrentTile().getRotation(),
                state.getCurrentTile().getTreasure() ) );

        */
    }

    public Player getPlayerTurn() {
        return this.playerTurn;
    }

    public Tile[][] getGameBoard() {
        return this.gameBoard;
    }

    public Tile getCurrentTile() {
        return this.currentTile;
    }

    public void setCurrentTile(Tile currentTile) {
        this.currentTile = currentTile;
    }

    public int getPlayerDeckSize(Player player) {
        switch (player) {
            case RED: return treasureDecks.get(0).size();
            case YELLOW: return treasureDecks.get(1).size();
            case GREEN: return treasureDecks.get(2).size();
            case BLUE: return treasureDecks.get(3).size();
            default: return -1;
        }
    }

    public Arrow getDisabledArrow() {
        return disabledArrow;
    }

    public void setDisabledArrow(Arrow disabledArrow) {
        this.disabledArrow = disabledArrow;
    }
}
