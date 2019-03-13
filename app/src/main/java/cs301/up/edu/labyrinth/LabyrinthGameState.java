package cs301.up.edu.labyrinth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import cs301.up.edu.labyrinth.enums.Arrow;
import cs301.up.edu.labyrinth.enums.Player;
import cs301.up.edu.labyrinth.enums.TileType;
import cs301.up.edu.labyrinth.enums.TreasureType;
import cs301.up.edu.game.infoMsg.GameState;

/**
 * Defines a gamestate for our labyrinth game. Has all the information needed
 * for a game to be played.
 *
 * @author Erik Torkelson, Spencer Nelson, Spencer Rose, Philip Robinson
 * Date: 2/27/2019
 */
public class LabyrinthGameState extends GameState {


    // To satisfy Serializable interface
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
    private LabyrinthGameState prevState;
    private boolean shiftedLabyrinthThisTurn;
    private boolean foundTreasureThisTurn;

    /**
     * Constructor used to initialize a gamestate. It creates the gameboard by
     * randomizing the starting player, randomizing player decks, and randomizing
     * specific pieces on the board.
     *
     */
    public LabyrinthGameState() {

        // First player turn is randomized
        double randomChoice = new Random(6161997).nextDouble();
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
        this.disabledArrow = Arrow.NONE;
        this.shiftedLabyrinthThisTurn = false;
        this.foundTreasureThisTurn = false;

        this.prevState = null;
    }

    public Arrow getDisabledArrow() {
        return this.disabledArrow;
    }

    public Tile getCurrentTile() {
        return this.currentTile;
    }

    public TreasureType getCurrentTreasure(int playerID) {
        return treasureDecks.get(playerID).get(0);
    }

    public Tile getTile(int x, int y) {
        return this.gameBoard[x][y];
    }

    /**
     * Updates array containing sizes of each players treasure deck
     */
    private void updateDeckSizes() {
        for (int i = 0; i < NUM_PLAYERS; i++) {
            this.deckSizes[i] = this.treasureDecks.get(i).size();
        }
    }

    public void setPrevState(LabyrinthGameState state) {
        this.prevState = state;
    }

    /**
     * Called by ctor to initialize all player decks randomly
     */
    private void initDecks() {
        // Get an array of all TreasureTypes and convert it to a list
        TreasureType[] allTreasures = TreasureType.values();
        List<TreasureType> treasureList = new ArrayList<>(25);
        for (TreasureType type : allTreasures) {
            treasureList.add(type);
        }
        treasureList.remove(0); // Delete NONE TreasureType
        Collections.shuffle(treasureList);

        //Create an arraylist of size 6 for each player within the outer one
        for (int i = 0; i < NUM_PLAYERS; i++) {
            this.treasureDecks.add(new ArrayList<TreasureType>(6));
        }

        //Add all random treasures to each player deck
        for (int i = 0; i < NUM_TREASURE_PER_PLAYER; i++) {
            this.treasureDecks.get(0).add(treasureList.remove(0));
            this.treasureDecks.get(1).add(treasureList.remove(0));
            this.treasureDecks.get(2).add(treasureList.remove(0));
            this.treasureDecks.get(3).add(treasureList.remove(0));
        }
    }

    /**
     * Called by ctor to initialize the gameboard with static and random pieces
     */
    private void initBoard() {
        // Generate arraylist of all randomized pieces for board
        List<Tile> randomPieces = new ArrayList<>(NUM_RANDOM_TILES);
        int numIntersection = 6;
        int numStraight = 13;
        int numCorner = 15;

        List<TreasureType> intersectionTreasures = new ArrayList<>(6);
        List<TreasureType> cornerTreasures = new ArrayList<>(15);

        for (int i = 0; i < 9; i++) {
            cornerTreasures.add(TreasureType.NONE);
        }

        cornerTreasures.add(TreasureType.OWL);
        cornerTreasures.add(TreasureType.SPIDER);
        cornerTreasures.add(TreasureType.BOW);
        cornerTreasures.add(TreasureType.MOUSE);
        cornerTreasures.add(TreasureType.MOTH);
        cornerTreasures.add(TreasureType.FOX);

        intersectionTreasures.add(TreasureType.URN);
        intersectionTreasures.add(TreasureType.BAT);
        intersectionTreasures.add(TreasureType.GOBLET);
        intersectionTreasures.add(TreasureType.DRAGON);
        intersectionTreasures.add(TreasureType.GHOST);
        intersectionTreasures.add(TreasureType.SHIELD);


        // Create each of the random tiles and place in arraylist
        for (int i = 0; i < NUM_RANDOM_TILES; i++) {
            int randomRotation = (new Random().nextInt(4))*90;

            if (numIntersection > 0) {
                randomPieces.add(new Tile(TileType.INTERSECTION, randomRotation,
                        intersectionTreasures.remove(0), this.gameBoard));
                numIntersection--;
            } else if (numStraight > 0) {
                randomPieces.add(new Tile(TileType.STRAIGHT, randomRotation,
                        TreasureType.NONE, this.gameBoard));
                numStraight--;
            } else if (numCorner > 0) {
                randomPieces.add(new Tile(TileType.CORNER, randomRotation,
                        cornerTreasures.remove(0), this.gameBoard));
                numCorner--;
            }
        }


        Collections.shuffle(randomPieces);

        // Place all random pieces in gameBoard
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if ((i % 2 != 0) || (j % 2 != 0)) {
                    // Movable Piece
                    this.gameBoard[i][j] = randomPieces.remove(0);
                    this.gameBoard[i][j].setLoc(i,j);
                }
            }
        }

        // Set current tile to last piece
        this.currentTile = randomPieces.remove(0);

        // Non-movable pieces
        this.gameBoard[0][0] = new Tile(
                TileType.RED_ENTRY,
                0,
                new boolean[] {true, false, false, false, false},
                this.gameBoard,
                0,0);

        this.gameBoard[6][0] = new Tile(
                TileType.YELLOW_ENTRY,
                0,
                new boolean[] {false, true, false, false, false},
                this.gameBoard,
                6,0);

        this.gameBoard[6][6] = new Tile(
                TileType.BLUE_ENTRY,
                0,
                new boolean[] {false, false, true, false, false},
                this.gameBoard,
                6,6);

        this.gameBoard[0][6] = new Tile(
                TileType.GREEN_ENTRY,
                0,
                new boolean[] {false, false, false, true, false},
                this.gameBoard,
                0,6);

        // Below is the setting of the fixed tiles on the board
        this.gameBoard[0][2] = new Tile(
                TileType.INTERSECTION,
                90,
                TreasureType.MAP,
                this.gameBoard,
                0,2);

        this.gameBoard[0][4] = new Tile(
                TileType.INTERSECTION,
                90,
                TreasureType.RING,
                this.gameBoard,
                0,4);

        this.gameBoard[2][0] = new Tile(
                TileType.INTERSECTION,
                180,
                TreasureType.BOOK,
                this.gameBoard,
                2,0);

        this.gameBoard[2][2] = new Tile(
                TileType.INTERSECTION,
                90,
                TreasureType.CROWN,
                this.gameBoard,
                2,2);

        this.gameBoard[2][4] = new Tile(
                TileType.INTERSECTION,
                0,
                TreasureType.CHEST,
                this.gameBoard,
                2,4);

        this.gameBoard[2][6] = new Tile(
                TileType.INTERSECTION,
                0,
                TreasureType.CANDLES,
                this.gameBoard,
                2,6);

        this.gameBoard[4][0] = new Tile(
                TileType.INTERSECTION,
                180,
                TreasureType.COINS,
                this.gameBoard,
                4,0);

        this.gameBoard[4][2] = new Tile(
                TileType.INTERSECTION,
                180,
                TreasureType.KEYS,
                this.gameBoard,
                4,2);

        this.gameBoard[4][4] = new Tile(
                TileType.INTERSECTION,
                270,
                TreasureType.GEM,
                this.gameBoard,
                4,4);

        this.gameBoard[4][6] = new Tile(
                TileType.INTERSECTION,
                0,
                TreasureType.HELMET,
                this.gameBoard,
                4,6);

        this.gameBoard[6][2] = new Tile(
                TileType.INTERSECTION,
                270,
                TreasureType.SKULL,
                this.gameBoard,
                6,2);

        this.gameBoard[6][4] = new Tile(
                TileType.INTERSECTION,
                270,
                TreasureType.SWORD,
                this.gameBoard,
                6,4);


        // Check connections for each tile
        this.updateTiles();

    }


    /**
     * Copy Constructor makes a deep copy of the gamestate
     *
     * @param state The state of game that needs to be copied
     */
    public LabyrinthGameState(LabyrinthGameState state) {

        //Copy player turn
        this.playerTurn = Player.valueOf(state.playerTurn.name());

        //Copy the entire gameboard by creating new tiles for each spot
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                boolean[] tempPlayer = new boolean[5];
                for (int k = 0; k < 5; k++) {
                    tempPlayer[k] = state.gameBoard[i][j].getPawn()[k];
                }
                this.gameBoard[i][j] = new Tile(
                        TileType.valueOf(
                                state.gameBoard[i][j].getType().name()),
                        state.gameBoard[i][j].getRotation(),
                        TreasureType.valueOf(
                                state.gameBoard[i][j].getTreasure().name()),
                        tempPlayer,
                        this.gameBoard,
                        i,j);
            }
        }

        //Copy current tile
        this.currentTile = new Tile(
                TileType.valueOf(state.currentTile.getType().name()),
                state.currentTile.getRotation(),
                TreasureType.valueOf(state.currentTile.getTreasure().name()),
                this.gameBoard,
                -1, -1);

        //Update tile positions
        this.updateTiles();

        //Copy treasure decks
        for (int i = 0; i < NUM_PLAYERS; i++) {
            this.treasureDecks.add(new ArrayList<TreasureType>(6));
            for (TreasureType treasure : state.treasureDecks.get(i)) {
                this.treasureDecks.get(i).add(
                        TreasureType.valueOf(treasure.name()));
            }
        }

        //Copy disabled arrow
        this.disabledArrow = Arrow.valueOf(state.disabledArrow.name());

        //Copy deck sizes
        for (int i = 0; i < NUM_PLAYERS; i++) {
            this.deckSizes[i] = state.deckSizes[i];
        }

        //Copy if someone has made a move
        this.shiftedLabyrinthThisTurn = state.shiftedLabyrinthThisTurn;
        this.foundTreasureThisTurn = state.foundTreasureThisTurn;

        // Doesn't matter if deepCopy has a reference to itself since
        // this variable is never used by deepCopies. Hence it is a shallow copy
        this.prevState = state.prevState;
    }

    /**
     * Calls main copy constructor and then removes information
     * about other players
     *
     * @param state gamestate to copy
     * @param playerID of player who gets this
     */
    public LabyrinthGameState(LabyrinthGameState state, int playerID) {
        this(state);

        // Delete all other decks
        for (int i = 0; i < NUM_PLAYERS; i++) {
            if (i != playerID) {
                this.treasureDecks.set(i ,null);
            }
        }

        // Find size of your deck
        int yourDeckSize = this.treasureDecks.get(playerID).size();

        // Remove all but your first treasure
        if (yourDeckSize > 0) {
            for (int i = yourDeckSize - 1; i > 0; i--) {
                this.treasureDecks.get(playerID).remove(i);
            }
        }
    }

    /**
     *
     * Get current player turn
     *
     * @return whos turn it is
     */
    public Player getPlayerTurn() {
        return this.playerTurn;
    }

    /**
     * Gets deck size for specific player
     *
     * @param player who you want deck size of
     * @return int size of deck
     */
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
                "Red Deck: " + this.deckSizes[0] + "; " +
                "Yellow Deck: " + this.deckSizes[1] + "; " +
                "Blue Deck: " + this.deckSizes[2] + "; " +
                "Green Deck: " + this.deckSizes[3] + "; " +
                "Current Tile: " + this.currentTile.toString() + "; " +
                "GameBoard: { " + board + " }" ;
    }

    /**
     * returns a string used by toString representing the board
     * (Helper method)
     *
     * @return a string of the board
     */
    private String convertBoardToString() {
        StringBuilder returnValue = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                returnValue.append(this.gameBoard[i][j].toString());
            }
        }
        return returnValue.toString();
    }

    /**
     * Update the connections of all tiles on the board
     */
    private void updateTiles() {
        for (Tile[] row1 : this.gameBoard) {
            for (Tile spot1 : row1) {
                spot1.calculateConnections();
            }
        }
        for (Tile[] row2 : this.gameBoard) {
            for (Tile spot2 : row2) {
                spot2.calculateConnectedTiles();
            }
        }
    }

    /**
     * Recursive search algorithm to find if there is a path between 2 tiles
     *
     * @param orig Starting tile
     * @param search Ending tile
     * @param checkedSpots arraylist containing searched spots
     * @return true if there is a path
     */
    private boolean searchConnectedTile(Tile orig, Tile search,
                                        List<Tile> checkedSpots) {

        // Get the tiles surrounding the current tile
        List<Tile> connectedTiles = Arrays.asList(orig.getConnectedTiles());
        /**
        //Check if you have searched this spot
        if (checkedSpots.contains(orig)) {
            return false;
        //Check if surrounding tiles contain goal
        } else if (connectedTiles.contains(search)) {
            checkedSpots.addAll(connectedTiles);
            return true;
        } else {
            // Call the algorithm on the other tiles if they exist
            List<Boolean> checks = new ArrayList<>(4);
            for (Tile spot : connectedTiles) {
                if (spot != null) {
                    if (checkedSpots.contains(spot)) return false;
                    if (searchConnectedTile(spot, search, checkedSpots)) {
                        checks.add(Boolean.TRUE);
                    } else {
                        checks.add(Boolean.FALSE);
                    }
                    checkedSpots.add(spot);
                }
            }
            checkedSpots.add(orig);
            return checks.contains(Boolean.TRUE);
        }*/

        return connectedTiles.contains(search);
    }

    /**
     * Returns the tile a specific player is on
     *
     * @param p player you are searching for
     * @return Tile that player is on
     */
    public Tile getPlayerLoc(Player p) {
        Tile playerTile = null;
        boolean found = false;
        for (Tile[] row : this.gameBoard) {
            for ( Tile spot : row) {
                if (spot.getPawn()[p.ordinal()]) {
                    playerTile = spot;
                    found = true;
                    break;
                }
            }
            if (found) break;
        }
        return playerTile;
    }


    /** All actions that can be taken */


    /**
     * Main Menu Action doesn't affect the gamestate
     *
     * @return true always
     */
    public boolean checkMainMenu() {
        // Always able to quit your game
        return true;
    }

    /**
     * Rotate Action rotates the current tile
     *
     * @param clockwise if you want to rotate clockwise
     * @return true if you can rotate
     */
    public boolean checkRotate(boolean clockwise) {
        if (!this.shiftedLabyrinthThisTurn) {
            if (clockwise) {
                this.currentTile.rotateClockwise();
            } else {
                this.currentTile.rotateCounterClockwise();
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * End Turn Action tries to end player turn and start next turn
     *
     * @return true if it ended
     */
    public boolean checkEndTurn() {
        if (this.shiftedLabyrinthThisTurn) {
            this.shiftedLabyrinthThisTurn = false;
            this.foundTreasureThisTurn = false;
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

    /**
     * Reset Action resets the gamestate to beginning of turn
     *
     * @return true if reset
     */
    public boolean checkReset() {
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
            this.shiftedLabyrinthThisTurn = temp.shiftedLabyrinthThisTurn;
            return true;
        }
    }

    /**
     * Slide Tile Action tries to slide in the current tile to an arrow location
     *
     * @param clickedArrow the arrow clicked
     * @return true if you can slide it
     */
    public boolean checkSlideTile(Arrow clickedArrow) {
        if (clickedArrow != this.disabledArrow && !shiftedLabyrinthThisTurn) {
            Tile tempTile = this.currentTile; //Tile to slide in

            switch (clickedArrow){
                case LEFT_TOP:
                    this.currentTile = this.gameBoard[6][1];
                    currentTile.setLoc(-1,-1);
                    for(int i = 0; i < 7; i++ ) {
                        if (i == 6) {
                            this.gameBoard[0][1] = tempTile;
                        }
                        else {
                            this.gameBoard[6 - i][1] = this.gameBoard[5 - i][1];
                        }
                        this.gameBoard[6 - i][1].setLoc(6 - i, 1);
                    }
                    break;

                case LEFT_MIDDLE:
                    this.currentTile = this.gameBoard[6][3];
                    currentTile.setLoc(-1,-1);
                    for(int i = 0; i < 7; i++ ) {
                        if (i == 6) {
                            this.gameBoard[0][3] = tempTile;
                        }
                        else {
                            this.gameBoard[6 - i][3] = this.gameBoard[5 - i][3];
                        }
                        this.gameBoard[6 - i][3].setLoc(6 - i, 3);
                    }
                    break;

                case LEFT_BOTTOM:
                    this.currentTile = this.gameBoard[6][5];
                    currentTile.setLoc(-1,-1);
                    for(int i = 0; i < 7; i++ ) {
                        if (i == 6) {
                            this.gameBoard[0][5] = tempTile;
                        }
                        else {
                            this.gameBoard[6 - i][5] = this.gameBoard[5 - i][5];
                        }
                        this.gameBoard[6 - i][5].setLoc(6 - i, 5);
                    }
                    break;


                case RIGHT_TOP:
                    this.currentTile = this.gameBoard[0][1];
                    currentTile.setLoc(-1,-1);
                    for(int i = 0; i < 6; i++ ) {
                        this.gameBoard[i][1] = this.gameBoard[i+1][1];
                        this.gameBoard[i][1].setLoc(i, 1);
                    }
                    this.gameBoard[6][1] = tempTile;
                    this.gameBoard[6][1].setLoc(6,1);
                    break;

                case RIGHT_MIDDLE:
                    this.currentTile = this.gameBoard[0][3];
                    currentTile.setLoc(-1,-1);
                    for(int i = 0; i < 6; i++ ) {
                        this.gameBoard[i][3] = this.gameBoard[i+1][3];
                        this.gameBoard[i][3].setLoc(i, 3);
                    }
                    this.gameBoard[6][3] = tempTile;
                    this.gameBoard[6][3].setLoc(6,3);
                    break;

                case RIGHT_BOTTOM:
                    this.currentTile = this.gameBoard[0][5];
                    currentTile.setLoc(-1,-1);
                    for(int i = 0; i < 6; i++ ) {
                        this.gameBoard[i][5] = this.gameBoard[i+1][5];
                        this.gameBoard[i][5].setLoc(i, 5);
                    }
                    this.gameBoard[6][5] = tempTile;
                    this.gameBoard[6][5].setLoc(6,5);
                    break;


                case TOP_LEFT:
                    this.currentTile = this.gameBoard[1][6];
                    currentTile.setLoc(-1,-1);
                    for(int i = 0; i < 7; i++ ) {
                        if (i == 6) {
                            this.gameBoard[1][0] = tempTile;
                        }
                        else {
                            this.gameBoard[1][6 - i] = this.gameBoard[1][5-i];
                        }
                        this.gameBoard[1][6-i].setLoc(1, 6-i);
                    }
                    break;

                case TOP_MIDDLE:
                    this.currentTile = this.gameBoard[3][6];
                    currentTile.setLoc(-1,-1);
                    for(int i = 0; i < 7; i++ ) {
                        if (i == 6) {
                            this.gameBoard[3][0] = tempTile;
                        }
                        else {
                            this.gameBoard[3][6-i] = this.gameBoard[3][5-i];
                        }
                        this.gameBoard[3][6-i].setLoc(3, 6-i);
                    }
                    break;

                case TOP_RIGHT:
                    this.currentTile = this.gameBoard[5][6];
                    currentTile.setLoc(-1,-1);
                    for(int i = 0; i < 7; i++ ) {
                        if (i == 6) {
                            this.gameBoard[5][0] = tempTile;
                        }
                        else {
                            this.gameBoard[5][6-i] = this.gameBoard[5][5-i];
                        }
                        this.gameBoard[5][6-i].setLoc(5, 6-i);
                    }
                    break;


                case BOTTOM_LEFT:
                    this.currentTile = this.gameBoard[1][0];
                    currentTile.setLoc(-1,-1);
                    for(int i = 0; i < 6; i++ ) {
                        this.gameBoard[1][i] = this.gameBoard[1][i+1];
                        this.gameBoard[1][i].setLoc(1, i);
                    }
                    this.gameBoard[1][6] = tempTile;
                    this.gameBoard[1][6].setLoc(1,6);
                    break;

                case BOTTOM_MIDDLE:
                    this.currentTile = this.gameBoard[3][0];
                    currentTile.setLoc(-1,-1);
                    for(int i = 0; i < 6; i++ ) {
                        this.gameBoard[3][i] = this.gameBoard[3][i+1];
                        this.gameBoard[3][i].setLoc(3, i);
                    }
                    this.gameBoard[3][6] = tempTile;
                    this.gameBoard[3][6].setLoc(3,6);
                    break;

                case BOTTOM_RIGHT:
                    this.currentTile = this.gameBoard[5][0];
                    currentTile.setLoc(-1,-1);
                    for(int i = 0; i < 6; i++ ) {
                        this.gameBoard[5][i] = this.gameBoard[5][i+1];
                        this.gameBoard[5][i].setLoc(5, i);
                    }
                    this.gameBoard[5][6] = tempTile;
                    this.gameBoard[5][6].setLoc(5,6);
                    break;
                }

            boolean[] temp = this.currentTile.getPawn();

            switch (clickedArrow) {
                case LEFT_TOP: {
                    this.disabledArrow = Arrow.RIGHT_TOP;
                    if (!temp[4]) {
                        this.currentTile.setPawn(new boolean[]
                                {false, false, false, false, true});
                        this.gameBoard[0][1].setPawn(temp);
                    }
                } break;

                case LEFT_MIDDLE: {
                    this.disabledArrow = Arrow.RIGHT_MIDDLE;
                    if (!temp[4]) {
                        this.currentTile.setPawn(new boolean[]
                                {false, false, false, false, true});
                        this.gameBoard[0][3].setPawn(temp);
                    }
                } break;

                case LEFT_BOTTOM: {
                    this.disabledArrow = Arrow.RIGHT_BOTTOM;
                    if (!temp[4]) {
                        this.currentTile.setPawn(new boolean[]
                                {false, false, false, false, true});
                        this.gameBoard[0][5].setPawn(temp);
                    }
                } break;

                case RIGHT_TOP: {
                    this.disabledArrow = Arrow.LEFT_TOP;
                    if (!temp[4]) {
                        this.currentTile.setPawn(new boolean[]
                                {false, false, false, false, true});
                        this.gameBoard[6][1].setPawn(temp);
                    }
                } break;

                case RIGHT_MIDDLE: {
                    this.disabledArrow = Arrow.LEFT_MIDDLE;
                    if (!temp[4]) {
                        this.currentTile.setPawn(new boolean[]
                                {false, false, false, false, true});
                        this.gameBoard[6][3].setPawn(temp);
                    }
                } break;

                case RIGHT_BOTTOM: {
                    this.disabledArrow = Arrow.LEFT_BOTTOM;
                    if (!temp[4]) {
                        this.currentTile.setPawn(new boolean[]
                                {false, false, false, false, true});
                        this.gameBoard[6][5].setPawn(temp);
                    }
                } break;

                case TOP_LEFT: {
                    this.disabledArrow = Arrow.BOTTOM_LEFT;
                    if (!temp[4]) {
                        this.currentTile.setPawn(new boolean[]
                                {false, false, false, false, true});
                        this.gameBoard[1][0].setPawn(temp);
                    }
                } break;

                case TOP_MIDDLE: {
                    this.disabledArrow = Arrow.BOTTOM_MIDDLE;
                    if (!temp[4]) {
                        this.currentTile.setPawn(new boolean[]
                                {false, false, false, false, true});
                        this.gameBoard[3][0].setPawn(temp);
                    }
                } break;

                case TOP_RIGHT: {
                    this.disabledArrow = Arrow.BOTTOM_RIGHT;
                    if (!temp[4]) {
                        this.currentTile.setPawn(new boolean[]
                                {false, false, false, false, true});
                        this.gameBoard[5][0].setPawn(temp);
                    }
                } break;

                case BOTTOM_LEFT: {
                    this.disabledArrow = Arrow.TOP_LEFT;
                    if (!temp[4]) {
                        this.currentTile.setPawn(new boolean[]
                                {false, false, false, false, true});
                        this.gameBoard[1][6].setPawn(temp);
                    }
                } break;

                case BOTTOM_MIDDLE: {
                    this.disabledArrow = Arrow.TOP_MIDDLE;
                    if (!temp[4]) {
                        this.currentTile.setPawn(new boolean[]
                                {false, false, false, false, true});
                        this.gameBoard[3][6].setPawn(temp);
                    }
                } break;

                case BOTTOM_RIGHT: {
                    this.disabledArrow = Arrow.TOP_RIGHT;
                    if (!temp[4]) {
                        this.currentTile.setPawn(new boolean[]
                                {false, false, false, false, true});
                        this.gameBoard[5][6].setPawn(temp);
                    }
                } break;

            }

            this.updateTiles();

            this.shiftedLabyrinthThisTurn = true;

            return true;
        } else {
            return false;
        }

    }

    /**
     *  Move Pawn Action checks if there is a path from their pawn to location
     *
     * @param locX x cord of desired move location
     * @param locY y cord of desired move location
     * @return true if you can move there
     */
    public boolean checkMovePawn(int locX, int locY) {
        //Only can move if shifted labyrinth
        if (this.shiftedLabyrinthThisTurn & !this.foundTreasureThisTurn) {
            // Find Player Tile
            Tile playerTile = this.getPlayerLoc(this.playerTurn);

            List<Tile> checkedSpots = new ArrayList<>(49);
            boolean valid;
            valid = this.searchConnectedTile(playerTile, this.gameBoard[locX][locY],
                    checkedSpots);
            if (valid) {
                // Remove old pawn loc
                boolean[] temp = playerTile.getPawn();
                temp[this.playerTurn.ordinal()] = false;
                if (!temp[0] & !temp[1] & !temp[2] & !temp[3]) temp[4] = true;

                // Set new pawn loc
                boolean[] temp2 = this.gameBoard[locX][locY].getPawn();
                temp2[this.playerTurn.ordinal()] = true;
                temp2[4] = false;

                if (this.gameBoard[locX][locY].getTreasure() ==
                        this.treasureDecks.get(this.playerTurn.ordinal()).get(0)) {
                    this.gameBoard[locX][locY].setTreasure(TreasureType.NONE);
                    this.treasureDecks.get(this.playerTurn.ordinal()).remove(0);
                    this.updateDeckSizes();
                    this.foundTreasureThisTurn = true;
                }
                return true;
            } else {
                return false;
            }
        }
        else return false;
    }

}