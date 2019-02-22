package cs301.up.edu.labyrinth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import cs301.up.edu.enums.Arrow;
import cs301.up.edu.enums.Player;
import cs301.up.edu.enums.TileType;
import cs301.up.edu.enums.TreasureType;
import cs301.up.edu.game.GamePlayer;
import cs301.up.edu.game.infoMsg.GameState;


public class LabyrinthGameState extends GameState {

    // to satisfy Serializable interface
    private static final long serialVersionUID = 7737393762469851826L;

    // Constants
    private final static int NUM_RANDOM_PIECES = 34;
    private final static int NUM_TREASURE_PER_PLAYER = 6;
    private final static int NUM_PLAYERS = 4;

    // Instance Variables for LabyrinthState
    private Player playerTurn;
    private Tile [][] gameBoard = new Tile[7][7];
    private Tile currentTile;
    private List<List<TreasureType>> treasureDecks =
            new ArrayList<>(4);
    private Arrow disabledArrow;

    /**
     * Constructor used to initialize a gamestate
     *
     */
    public LabyrinthGameState() {

        // First player turn is randomized
        double randomChoice = Math.random();
        if (randomChoice < 0.25) {
            this.playerTurn = Player.RED;
        } else if (randomChoice < 0.5) {
            this.playerTurn = Player.YELLOW;
        } else if (randomChoice < 0.75) {
            this.playerTurn = Player.BLUE;
        } else {
            this.playerTurn = Player.GREEN;
        }

        // Set up Board
        this.initBoard();

        // Set up treasure decks for the 4 players
        this.initDecks();

        // Players can slide the first piece in anywhere they want
        this.disabledArrow = Arrow.None;

    }

    private void initDecks() {
        // Get an array of all TreasureTypes and convert it to a list
        TreasureType[] allTreasures = TreasureType.values();
        List<TreasureType> treasureList = new ArrayList<>(24);
        for (TreasureType type : allTreasures) {
            treasureList.add(type);
        }
        treasureList.remove(0); // Delete None TreasureType
        Collections.shuffle(treasureList);

        for (int i = 0; i < NUM_PLAYERS; i++) {
            this.treasureDecks.add(new ArrayList<TreasureType>(6));
        }

        for (int i = 0; i < NUM_TREASURE_PER_PLAYER; i++) {
            this.treasureDecks.get(0).add(treasureList.remove(0));
            this.treasureDecks.get(1).add(treasureList.remove(0));
            this.treasureDecks.get(2).add(treasureList.remove(0));
            this.treasureDecks.get(3).add(treasureList.remove(0));
        }
    }

    private void initBoard() {
        // Generate arraylist of all randomized pieces for board
        List<Tile> randomPieces = new ArrayList<>(NUM_RANDOM_PIECES);
        int numIntersection = 6;
        int numStraight = 13;
        int numCorner = 15;

        for (int i = 0; i < NUM_RANDOM_PIECES; i++) {
            int randomRotation = 0; // TODO: Make this randomly 0, 90, 180, 270

            if (numIntersection > 0) {
                // TODO: 6 Intersections Need Treasures
                randomPieces.add(new Tile(TileType.INTERSECTION, randomRotation));
                numIntersection--;
            } else if (numStraight > 0) {
                randomPieces.add(new Tile(TileType.STRAIGHT, randomRotation));
                numStraight--;
            } else if (numCorner > 0) {
                // TODO: 6 Corners Need Treasures
                randomPieces.add(new Tile(TileType.CORNER, randomRotation));
                numCorner--;
            }
        }

        Collections.shuffle(randomPieces);

        // Initialize Game Board
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if ((i % 2 != 0) || (j % 2 != 0)) {
                    // Movable Piece
                    this.gameBoard[i][j] = randomPieces.remove(0);
                }
            }
        }

        // Set current tile to last piece
        this.currentTile = randomPieces.remove(0);

        // Non-movable pieces
        this.gameBoard[0][0] = new Tile(
                TileType.RED_ENTRY,
                0,
                TreasureType.NONE,
                Player.RED);

        this.gameBoard[6][0] = new Tile(
                TileType.YELLOW_ENTRY,
                0,
                TreasureType.NONE,
                Player.YELLOW);

        this.gameBoard[6][6] = new Tile(
                TileType.GREEN_ENTRY,
                0,
                TreasureType.NONE,
                Player.GREEN);

        this.gameBoard[0][6] = new Tile(
                TileType.BLUE_ENTRY,
                0,
                TreasureType.NONE,
                Player.BLUE);

        this.gameBoard[0][2] = new Tile(
                TileType.INTERSECTION,
                180,
                TreasureType.NONE,
                Player.None);

        this.gameBoard[0][4] = new Tile(
                TileType.INTERSECTION,
                180,
                TreasureType.NONE,
                Player.None);

        this.gameBoard[2][0] = new Tile(
                TileType.INTERSECTION,
                180,
                TreasureType.NONE,
                Player.None);

        this.gameBoard[2][2] = new Tile(
                TileType.INTERSECTION,
                180,
                TreasureType.NONE,
                Player.None);

        this.gameBoard[2][4] = new Tile(
                TileType.INTERSECTION,
                180,
                TreasureType.NONE,
                Player.None);

        this.gameBoard[2][6] = new Tile(
                TileType.INTERSECTION,
                180,
                TreasureType.NONE,
                Player.None);

        this.gameBoard[4][0] = new Tile(
                TileType.INTERSECTION,
                180,
                TreasureType.NONE,
                Player.None);

        this.gameBoard[4][2] = new Tile(
                TileType.INTERSECTION,
                180,
                TreasureType.NONE,
                Player.None);

        this.gameBoard[4][4] = new Tile(
                TileType.INTERSECTION,
                180,
                TreasureType.NONE,
                Player.None);

        this.gameBoard[4][6] = new Tile(
                TileType.INTERSECTION,
                180,
                TreasureType.NONE,
                Player.None);

        this.gameBoard[6][2] = new Tile(
                TileType.INTERSECTION,
                180,
                TreasureType.NONE,
                Player.None);

        this.gameBoard[6][4] = new Tile(
                TileType.INTERSECTION,
                180,
                TreasureType.NONE,
                Player.None);
    }


    /**
     * Copy Constructor makes a deep copy of the gamestate for a
     * specific player
     *
     * @param state The state of game that needs to be copied
     */
    public LabyrinthGameState(LabyrinthGameState state, GamePlayer player) {

        this.playerTurn = Player.valueOf(state.getPlayerTurn().name());

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                this.gameBoard[i][j] = new Tile(
                        TileType.valueOf(
                                state.gameBoard[i][j].getType().name()),
                        state.gameBoard[i][j].getRotation(),
                        TreasureType.valueOf(
                                state.gameBoard[i][j].getTreasure().name()));
            }
        }

        this.currentTile = new Tile(
                TileType.valueOf(state.currentTile.getType().name()),
                state.currentTile.getRotation(),
                TreasureType.valueOf(state.currentTile.getTreasure().name()));

        for (List<TreasureType> deck : state.treasureDecks) {
            // TODO: Deep copy treasureDecks
        }

        this.disabledArrow = Arrow.valueOf(state.getDisabledArrow().name());

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
            case BLUE: return treasureDecks.get(2).size();
            case GREEN: return treasureDecks.get(3).size();
            default: return -1;
        }
    }

    public Arrow getDisabledArrow() {
        return disabledArrow;
    }

    public void setDisabledArrow(Arrow disabledArrow) {
        this.disabledArrow = disabledArrow;
    }

    /**
     * toString returns a string version of the gamestate
     *
     * @return String version of the gamestate
     */
    @Override
    public String toString() {
        return "Turn: " + this.playerTurn.name() + ";" +
                "Disabled Arrow: " + this.disabledArrow.name() + ";" +
                "Red Deck: " + this.treasureDecks.get(0).toString() + ";" +
                "Yellow Deck: " + this.treasureDecks.get(1).toString() + ";" +
                "Blue Deck: " + this.treasureDecks.get(2).toString() + ";" +
                "Green Deck: " + this.treasureDecks.get(3).toString() + ";" +
                "Current Tile: " + this.currentTile.toString() + ";";
    }


    /** All actions that can be taken */


    public boolean checkMainMenu(GamePlayer player) {
        return true;
    }

    public boolean checkRotateClockwise(GamePlayer player) {
        return true;
    }

    public boolean checkRotateCounterClockwise(GamePlayer player) {
        return true;
    }

    public boolean checkEndTurn(GamePlayer player) {
        return true;
    }

    public boolean checkReset(GamePlayer player) {
        return true;
    }

    public boolean checkSlideTile(GamePlayer player) {
        return true;
    }

    public boolean checkMovePawn(GamePlayer player) {
        return true;
    }
}
