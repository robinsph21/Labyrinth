package cs301.up.edu.labyrinth;

import java.util.ArrayList;
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
    private final static int NUM_RANDOM_TILES = 34;
    private final static int NUM_TREASURE_PER_PLAYER = 6;
    private final static int NUM_PLAYERS = 4;

    // Instance Variables for LabyrinthState
    private Player playerTurn;
    private Tile [][] gameBoard = new Tile[7][7];
    private Tile currentTile;
    private List<List<TreasureType>> treasureDecks =
            new ArrayList<>(4);
    private int[] deckSizes = new int[4];
    private Arrow disabledArrow;
    private boolean shiftedLabyrinthThisTurn;
    private LabyrinthGameState prevState;

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
        this.updateDeckSizes();

        // Players can slide the first piece in anywhere they want
        this.disabledArrow = Arrow.None;
        this.shiftedLabyrinthThisTurn = false;

        this.prevState = null;
    }

    private void updateDeckSizes() {
        for (int i = 0; i < NUM_PLAYERS; i++) {
            this.deckSizes[i] = this.treasureDecks.get(i).size();
        }
    }

    private void initDecks() {
        // Get an array of all TreasureTypes and convert it to a list
        TreasureType[] allTreasures = TreasureType.values();
        List<TreasureType> treasureList = new ArrayList<>(25);
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
        List<Tile> randomPieces = new ArrayList<>(NUM_RANDOM_TILES);
        int numIntersection = 6;
        int numStraight = 13;
        int numCorner = 15;

        for (int i = 0; i < NUM_RANDOM_TILES; i++) {
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

        // Below is the setting of the fixed tiles on the board

        this.gameBoard[0][2] = new Tile(
                TileType.INTERSECTION,
                270,
                TreasureType.NONE,
                Player.None);

        this.gameBoard[0][4] = new Tile(
                TileType.INTERSECTION,
                270,
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
                270,
                TreasureType.NONE,
                Player.None);

        this.gameBoard[2][6] = new Tile(
                TileType.INTERSECTION,
                0,
                TreasureType.NONE,
                Player.None);

        this.gameBoard[4][0] = new Tile(
                TileType.INTERSECTION,
                180,
                TreasureType.NONE,
                Player.None);

        this.gameBoard[4][2] = new Tile(
                TileType.INTERSECTION,
                90,
                TreasureType.NONE,
                Player.None);

        this.gameBoard[4][4] = new Tile(
                TileType.INTERSECTION,
                0,
                TreasureType.NONE,
                Player.None);

        this.gameBoard[4][6] = new Tile(
                TileType.INTERSECTION,
                0,
                TreasureType.NONE,
                Player.None);

        this.gameBoard[6][2] = new Tile(
                TileType.INTERSECTION,
                90,
                TreasureType.NONE,
                Player.None);

        this.gameBoard[6][4] = new Tile(
                TileType.INTERSECTION,
                90,
                TreasureType.NONE,
                Player.None);
    }


    /**
     * Copy Constructor makes a deep copy of the gamestate for a
     * specific player
     *
     * @param state The state of game that needs to be copied
     */
    public LabyrinthGameState(LabyrinthGameState state) {

        this.playerTurn = Player.valueOf(state.playerTurn.name());

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                this.gameBoard[i][j] = new Tile(
                        TileType.valueOf(
                                state.gameBoard[i][j].getType().name()),
                        state.gameBoard[i][j].getRotation(),
                        TreasureType.valueOf(
                                state.gameBoard[i][j].getTreasure().name()),
                        Player.valueOf(state.gameBoard[i][j].getPawn().name()));
            }
        }

        this.currentTile = new Tile(
                TileType.valueOf(state.currentTile.getType().name()),
                state.currentTile.getRotation(),
                TreasureType.valueOf(state.currentTile.getTreasure().name()));


        for (int i = 0; i < NUM_PLAYERS; i++) {
            this.treasureDecks.add(new ArrayList<TreasureType>(6));
            for (TreasureType treasure : state.treasureDecks.get(i)) {
                this.treasureDecks.get(i).add(
                        TreasureType.valueOf(treasure.name()));
            }
        }

        this.disabledArrow = Arrow.valueOf(state.disabledArrow.name());

        for (int i = 0; i < NUM_PLAYERS; i++) {
            this.deckSizes[i] = state.deckSizes[i];
        }

        this.shiftedLabyrinthThisTurn = state.shiftedLabyrinthThisTurn;

        // Doesn't matter if deepcopy has a reference to itself since
        // this variable is never used by deepcopies
        this.prevState = state.prevState;
    }

    public LabyrinthGameState(LabyrinthGameState state, int playerID) {
        this(state);

        // TODO: Remove Extra Player Info
    }

    public Player getPlayerTurn() {
        return this.playerTurn;
    }

    public int getPlayerDeckSize(Player player) {
        switch (player) {
            case RED: return deckSizes[0];
            case YELLOW: return deckSizes[1];
            case BLUE: return deckSizes[2];
            case GREEN: return deckSizes[3];
            default: return -1;
        }
    }

    /**
     * toString returns a string version of the gamestate
     *
     * @return String version of the gamestate
     */
    @Override
    public String toString() {
        String board = convertBoardToString();
        return "Turn: " + this.playerTurn.name() + "; " +
                "Disabled Arrow: " + this.disabledArrow.name() + "; " +
                "Red Deck: " + this.treasureDecks.get(0).toString() + "; " +
                "Yellow Deck: " + this.treasureDecks.get(1).toString() + "; " +
                "Blue Deck: " + this.treasureDecks.get(2).toString() + "; " +
                "Green Deck: " + this.treasureDecks.get(3).toString() + "; " +
                "Current Tile: " + this.currentTile.toString() + "; " +
                "GameBoard: { " + board + " }" ;
    }

    private String convertBoardToString() {
        StringBuilder returnValue = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                returnValue.append(this.gameBoard[i][j].toString());
            }
        }
        return returnValue.toString();
    }


    /** All actions that can be taken */


    public boolean checkMainMenu(int playerID) {
        // Always able to quit your game
        return true;
    }

    public boolean checkRotate(int playerID, boolean clockwise) {
        if (clockwise) {
            this.currentTile.rotateClockwise();
        } else {
            this.currentTile.rotateCounterClockwise();
        }
        return true;
    }

    public boolean checkEndTurn(int playerID) {
        if (this.shiftedLabyrinthThisTurn) {
            this.shiftedLabyrinthThisTurn = false;
            switch (this.playerTurn) {
                case RED: this.playerTurn = Player.YELLOW; break;
                case YELLOW: this.playerTurn = Player.BLUE; break;
                case BLUE: this.playerTurn = Player.GREEN; break;
                case GREEN: this.playerTurn = Player.RED; break;
            }
            this.prevState = new LabyrinthGameState(this);
            return true;
        } else {
            return false;
        }
    }

    public boolean checkReset(int playerID) {
        if (this.prevState == null) {
            return false;
        } else {
            LabyrinthGameState temp = new LabyrinthGameState(this.prevState);
            this.playerTurn = temp.playerTurn;
            this.gameBoard = temp.gameBoard;
            this.currentTile = temp.currentTile;
            this.treasureDecks = temp.treasureDecks;
            this.deckSizes = temp.deckSizes;
            this.disabledArrow = temp.disabledArrow;
            return true;
        }
    }

    public boolean checkSlideTile(int playerID, Arrow clickedArrow) {
        if (clickedArrow != this.disabledArrow) {
            // TODO: Slide Tiles

            // TODO: If pawn moves to currentTile, move to other end

            this.shiftedLabyrinthThisTurn = true;

            switch (clickedArrow) {
                case LEFT_TOP: this.disabledArrow = Arrow.RIGHT_TOP; break;
                case LEFT_MIDDLE: this.disabledArrow = Arrow.RIGHT_MIDDLE; break;
                case LEFT_BOTTOM: this.disabledArrow = Arrow.RIGHT_BOTTOM; break;

                case RIGHT_TOP: this.disabledArrow = Arrow.LEFT_TOP; break;
                case RIGHT_MIDDLE: this.disabledArrow = Arrow.LEFT_MIDDLE; break;
                case RIGHT_BOTTOM: this.disabledArrow = Arrow.LEFT_BOTTOM; break;

                case TOP_LEFT: this.disabledArrow = Arrow.BOTTOM_LEFT; break;
                case TOP_MIDDLE: this.disabledArrow = Arrow.BOTTOM_MIDDLE; break;
                case TOP_RIGHT: this.disabledArrow = Arrow.BOTTOM_RIGHT; break;

                case BOTTOM_LEFT: this.disabledArrow = Arrow.TOP_LEFT; break;
                case BOTTOM_MIDDLE: this.disabledArrow = Arrow.TOP_MIDDLE; break;
                case BOTTOM_RIGHT: this.disabledArrow = Arrow.TOP_RIGHT; break;
            }
            return true;
        } else {
            return false;
        }

    }

    public boolean checkMovePawn(int playerID) {
        // TODO: SEE IF YOU CAN MOVE CURRENT PLAYER PAWN
        return true;
    }
}
