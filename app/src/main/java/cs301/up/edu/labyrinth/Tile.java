package cs301.up.edu.labyrinth;

import java.io.Serializable;

import cs301.up.edu.labyrinth.enums.TileType;
import cs301.up.edu.labyrinth.enums.TreasureType;


/**
 * Defines a board tile. The gameboard is made up of these tile objects.
 * They contain all information about a specific space.
 *
 * @author Erik Torkelson, Spencer Nelson, Spencer Rose, Philip Robinson
 * Date: 2/27/2019
 */
public class Tile implements Serializable {

    private static final long serialVersionUID = 323737276246984926L;

    /* INSTANCE VARIABLES */

    // Type of tile, Straight, intersection, corner or entry
    private final TileType type;

    // Rotation based off drawing located in drawables. Will be 0, 90, 180, 270
    private int rotation;

    // array of connection for tile. True represents and entry point the this
    // tile. Arrays are in the following order Left, Up, Right, Down
    private boolean[] connections = new boolean[4];

    // array of tiles that are to the right, above, left and below this tile.
    // used to determine whether the two tiles share a path.
    private Tile[] connectedTiles = new Tile[4];

    // Type of treasure on this tile (see TreasureType enum).
    private TreasureType treasure;

    // Tile x,y location in the board.
    private int x;
    private int y;

    // Type of pawn on this tile (see Player enum)
    private boolean[] pawn;

    // Array of tiles on the board.
    private Tile[][] board;

    /**
     * Ctor for Tile. Will initialize all instance variables.
     *
     * @param type Type of tile, Straight, intersection, corner or entry
     * @param rotation Rotation of tile 0, 90, 180, 270
     * @param treasure Type of treasure on this tile
     * @param pawn Type of pawn on this tile
     * @param board Array of tiles on the board
     * @param x x location of the tile in the board
     * @param y y location of the tile in the board
     */
    public Tile(TileType type, int rotation, TreasureType treasure,
                boolean[] pawn, Tile[][] board, int x, int y) {
        this.type = type;
        this.rotation = rotation;
        this.treasure = treasure;
        this.x = x;
        this.y = y;
        this.board = board;
        this.pawn = pawn;
        this.calculateConnections();
    }

    /**
     * Ctor for Tile. Used initialize the tile with no player pawn on it.
     *
     * @param type Type of tile, Straight, intersection, corner or entry
     * @param rotation Rotation of tile 0, 90, 180, 270
     * @param treasure Type of treasure on this tile
     * @param board Array of tiles on the board
     * @param x x location of the tile in the board
     * @param y y location of the tile in the board
     */
    public Tile(TileType type, int rotation, TreasureType treasure,
                Tile[][] board, int x, int y) {
        this(type, rotation, treasure,
                new boolean[]{false, false, false, false, true}, board, x, y);
    }

    /**
     * Ctor for Tile. Used to initialize the "current tile" which will have an
     * x,y of -1,-1
     *
     * @param type Type of tile, Straight, intersection, corner or entry
     * @param rotation Rotation of tile 0, 90, 180, 270
     * @param treasure Type of treasure on this tile
     * @param board Array of tiles on the board
     */
    public Tile(TileType type, int rotation, TreasureType treasure,
                Tile[][] board) {
        this(type, rotation, treasure,
                new boolean[]{false, false, false, false, true},
                board, -1, -1);
    }

    /**
     * Ctor for tile. Used to initialize all tiles that have a NONe tpe treasure
     * on them.
     *
     * @param type Type of tile, Straight, intersection, corner or entry
     * @param rotation Rotation of tile 0, 90, 180, 270
     * @param board Array of tiles on the board
     * @param x x location of the tile in the board
     * @param y y location of the tile in the board
     */
    public Tile(TileType type, int rotation, boolean[] pawn, Tile[][] board,
                int x, int y) {
        this(type, rotation, TreasureType.NONE, pawn, board, x, y);
    }

    /**
     * Rotate the current tile clockwise. Will always result in a rotation of
     * 0, 90, 180, or 270
     */
    public void rotateClockwise() {
        this.rotation = (this.rotation + 90) % 360;
    }

    /**
     * Rotate the current tile counter clockwise. Will always result in a
     * rotation of 0, 90, 180, or 270
     */
    public void rotateCounterClockwise() {
        this.rotation = (this.rotation + 270) % 360;
    }

    /**
     * Helper method to determine which tiles are surrounding this tile
     */
    public void calculateConnectedTiles() {

        //There are four entry points to this tile. Left, above, right and below
        for (int i = 0; i < 4; i++) {
            // If there is entry point to this tile get the tile touching
            // it from the appropriate direction
            if (this.connections[i]) {
                int [] loc = new int[2];
                switch (i) {
                    // There is an entry point to the left of this tile
                    // so find the tile to the left of this tile
                    case 0: loc[0] = this.x - 1;
                            loc[1] = this.y;
                            break;
                    // There is an entry point to the top of this tile
                    // so find the tile above this tile
                    case 1: loc[0] = this.x;
                            loc[1] = this.y - 1;
                            break;
                    // There is an entry point to the right of this tile
                    // so find the tile to the right of this tile
                    case 2: loc[0] = this.x + 1;
                            loc[1] = this.y;
                            break;
                    // There is an entry point to the bottom of this tile
                    // so find the tile below this tile
                    case 3: loc[0] = this.x;
                            loc[1] = this.y + 1;
                            break;
                }

                // If the tile we just got is not out of bounds
                if ((loc[0] >= 0 && loc[0] <= 6) &&
                        (loc[1] >= 0 && loc[1] <= 6)) {

                    // If the tile we got above has a entry point in the correct
                    // direction indicated there is a connection between the
                    // two. example, a tile to the left of this tile would have
                    // to have it right entry point set to TRUE.
                    if (this.board[loc[0]][loc[1]].connections[(i+2)%4]) {
                        this.connectedTiles[i] = this.board[loc[0]][loc[1]];
                    } else {
                        this.connectedTiles[i] = null; //Update previously valid
                    }
                } else {
                    this.connectedTiles[i] = null;
                } // if else array out out bounds
            } else {
                this.connectedTiles[i] = null;
            } // if else entry point of connected tile is true.
        }
    }

    /**
     * Helper method to determine which entry points of the current tile should
     * be set to TRUE. This method is called from the construction to initialize
     * this tile.
     */
    public void calculateConnections() {

        // Determine what type of tile this is
        switch (this.type) {

            /*
            * The 0 degree rotation for a corner piece can be seen below. 90,
            * 180, 270 rotations are based of clockwise rotations of the of the
            * 0 degree rotation.
            *
            *       ______________
            *       |
            *       |     ________   <-- entry point
            *       |     |
            *       |     |
            *       |     |
            *       |     |
            *          ^
            *          |
            *     entry point
            *
            */
            case CORNER: {
                // The array of boolean connections is in the following order
                // Left, Up, Right, Down
                switch (rotation) {
                    case 0: this.connections = new boolean[]
                            {false, false, true, true}; break;
                    case 90: this.connections = new boolean[]
                            {true, false, false, true}; break;
                    case 180: this.connections = new boolean[]
                            {true, true, false, false}; break;
                    case 270: this.connections = new boolean[]
                            {false, true, true, false}; break;
                    default: this.connections = new boolean[]
                            {false, false, false, false}; break;
                }
            } break;

            /*
            * The 0 degree rotation for a straight  piece can be seen below. 90,
            * 180, 270 rotations are based of clockwise rotations of the of the
            * 0 degree rotation.
            *
            *                    ______________
            *
            *     entry point -> ______________ <- entry point
            *
            */
            case STRAIGHT: {
                // The array of boolean connections is in the following order
                // Left, Up, Right, Down
                switch (rotation) {
                    case 0: this.connections = new boolean[]
                            {true, false, true, false}; break;
                    case 90: this.connections = new boolean[]
                            {false, true, false, true}; break;
                     case 180: this.connections = new boolean[]
                            {true, false, true, false}; break;
                    case 270: this.connections = new boolean[]
                            {false, true, false, true}; break;
                    default: this.connections = new boolean[]
                            {false, false, false, false}; break;
                }
            } break;

            /*
            * The 0 degree rotation for a straight  piece can be seen below. 90,
            * 180, 270 rotations are based of clockwise rotations of the of the
            * 0 degree rotation.
            *
            *                           |     |
            *                           |     |
            *                           |     |
            *                   ________|  ^  |________
            *                              |
            *     entry point ->      entry point      <- entry point
            *                   _______________________
            *
            * */
            case INTERSECTION: {
                // The array of boolean connections is in the following order
                // Left, Up, Right, Down
                switch (rotation) {
                    case 0: this.connections = new boolean[]
                            {true, true, true, false}; break;
                    case 90: this.connections = new boolean[]
                            {false, true, true, true}; break;
                    case 180: this.connections = new boolean[]
                            {true, false, true, true}; break;
                    case 270: this.connections = new boolean[]
                            {true, true, false, true}; break;
                    default: this.connections = new boolean[]
                            {false, false, false, false}; break;
                }
            } break;

            case RED_ENTRY: this.connections = new boolean[]
                    {false, false, true, true}; break;
            case YELLOW_ENTRY: this.connections = new boolean[]
                    {true, false, false, true}; break;
            case BLUE_ENTRY: this.connections = new boolean[]
                    {true, true, false, false}; break;
            case GREEN_ENTRY: this.connections = new boolean[]
                    {false, true, true, false}; break;
        }
    }

    /**
     * Method to create a string containing all tile information. Used for
     * debugging
     *
     * @return The string containing all the information
     */
    @Override
    public String toString() {
        return "{ Tile Type:" + this.type.name() + "; " +
                "Rotation: " + this.rotation + "; " +
                "Connections: " +
                    this.connections[0] + ", " +
                    this.connections[1] + ", " +
                    this.connections[2] + ", " +
                    this.connections[3] + "; " +
                "Treasure: " + this.treasure.name() + "; " +
                "Players on Tile: " +
                    "Red: " + this.pawn[0] +
                    "Yellow: " + this.pawn[1] +
                    "Blue: " + this.pawn[2] +
                    "Green: " + this.pawn[3] + " }";
    }

    /* GETTERS AND SETTERS */

    /**
     * X location of tile
     *
     * @return x loc
     */
    public int getX() {
        return this.x;
    }

    /**
     * Y location of tile
     *
     * @return y loc
     */
    public int getY() {
        return this.y;
    }

    public int[] getLoc() {
        int[] loc = new int[2];
        loc[0] = this.x;
        loc[1] = this.y;
        return loc;
    }

    /**
     * get the enum value of the type of tile this is
     *
     * @return tile type
     */
    public TileType getType() {
        return this.type;
    }

    /**
     * Get the rotation of this tile
     *
     * @return 0, 90, 180 or 270 degrees
     */
    public int getRotation() {
        return this.rotation;
    }

    /**
     * Get the type og pawn on this tile. Will be none if there is no pawn
     *
     * @return pawn color or none
     */
    public boolean[] getPawn() {
        return this.pawn;
    }

    /**
     * Set the pawn for this tile. See player enum for types of pawn
     *
     * @param pawn color or none
     */
    public void setPawn(boolean[] pawn) {
        this.pawn = pawn;
    }

    /**
     * Get the treasure type on this tile. Will be none if no treasure is
     * present
     *
     * @return treasure type
     */
    public TreasureType getTreasure() {
        return this.treasure;
    }

    /**
     * Set the type of treasure for this tile. See treasure type enum for types
     *
     * @param treasure treasure type
     */
    public void setTreasure(TreasureType treasure) {
        this.treasure = treasure;
    }

    /**
     * Set the x and y locations of this tile.
     * @param x x location
     * @param y y location
     */
    public void setLoc(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get all the tiles that are connected to this tile.
     *
     * @return array of connected tiles
     */
    public Tile[] getConnectedTiles() {
        return this.connectedTiles;
    }
}
