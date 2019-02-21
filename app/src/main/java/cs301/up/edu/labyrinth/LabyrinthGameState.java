package cs301.up.edu.labyrinth;

import java.util.ArrayList;

import cs301.up.edu.enums.Arrow;
import cs301.up.edu.enums.Player;
import cs301.up.edu.enums.TileType;
import cs301.up.edu.enums.TreasureType;
import cs301.up.edu.game.infoMsg.GameState;


public class LabyrinthGameState extends GameState {

    // to satisfy Serializable interface
    private static final long serialVersionUID = 7737393762469851826L;


    private Player playerTurn;
    private Tile [][] gameBoard = new Tile[7][7];
    private Tile currentTile;
    private ArrayList<TreasureType>[] treasureDecks = new ArrayList[4];
    private Arrow disabledArrow;


    public LabyrinthGameState() {
        this.playerTurn = Player.RED;

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                this.gameBoard[i][j] = new Tile(TileType.STRAIGHT,
                        0, TreasureType.NONE);
            }
        }

        this.currentTile = new Tile(TileType.STRAIGHT, 0,
                TreasureType.NONE);


    }


    //Clone Constructor
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
            case RED: return treasureDecks[0].size();
            case YELLOW: return treasureDecks[1].size();
            case GREEN: return treasureDecks[2].size();
            case BLUE: return treasureDecks[3].size();
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
