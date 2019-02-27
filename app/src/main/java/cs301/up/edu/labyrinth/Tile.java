package cs301.up.edu.labyrinth;

import java.io.Serializable;

import cs301.up.edu.labyrinth.enums.Player;
import cs301.up.edu.labyrinth.enums.TileType;
import cs301.up.edu.labyrinth.enums.TreasureType;

// TODO: Comment this file

/**
 * Defines a board tile. The gameboard is made up of these tile objects.
 * They contain all information about a specific space.
 *
 * @author Erik Torkelson, Spencer Nelson, Spencer Rose, Philip Robinson
 * Date: 2/27/2019
 */
public class Tile implements Serializable {

    private static final long serialVersionUID = 323737276246984926L;

    private final TileType type;
    private int rotation;
    private boolean[] connections = new boolean[4]; //Left, Up, Right, Down
    private Tile[] connectedTiles = new Tile[4];
    private TreasureType treasure;
    private int x;
    private int y;
    private Player pawn;
    private Tile[][] board;

    public Tile(TileType type, int rotation, TreasureType treasure, Player pawn,
                Tile[][] board, int x, int y) {
        this.type = type;
        this.rotation = rotation;
        this.treasure = treasure;
        this.x = x;
        this.y = y;
        this.board = board;
        this.pawn = pawn;
        this.calculateConnections();
    }

    public Tile(TileType type, int rotation, TreasureType treasure,
                Tile[][] board, int x, int y) {
        this(type, rotation, treasure, Player.NONE, board, x, y);
    }

    public Tile(TileType type, int rotation, TreasureType treasure,
                Tile[][] board) {
        this(type, rotation, treasure, Player.NONE, board, -1, -1);
    }

    public Tile(TileType type, int rotation, Player pawn, Tile[][] board,
                int x, int y) {
        this(type, rotation, TreasureType.NONE, pawn, board, x, y);
    }

    public void rotateClockwise() {
        this.rotation = (this.rotation + 90) % 360;
    }

    public void rotateCounterClockwise() {
        this.rotation = (this.rotation + 270) % 360;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public TileType getType() {
        return this.type;
    }

    public int getRotation() {
        return this.rotation;
    }

    public Player getPawn() {
        return this.pawn;
    }

    public void setPawn(Player pawn) {
        this.pawn = pawn;
    }

    public TreasureType getTreasure() {
        return this.treasure;
    }

    public void setTreasure(TreasureType treasure) {
        this.treasure = treasure;
    }

    public void setLoc(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void calculateConnectedTiles() {
        for (int i = 0; i < 4; i++) {
            if (this.connections[i]) {
                int [] loc = new int[2];
                switch (i) {
                    case 0: loc[0] = this.x - 1;
                            loc[1] = this.y;
                            break;
                    case 1: loc[0] = this.x;
                            loc[1] = this.y - 1;
                            break;
                    case 2: loc[0] = this.x + 1;
                            loc[1] = this.y;
                            break;
                    case 3: loc[0] = this.x;
                            loc[1] = this.y + 1;
                            break;
                }
                if ((loc[0] >= 0 && loc[0] <= 6) &&
                        (loc[1] >= 0 && loc[1] <= 6)) {
                    if (this.board[loc[0]][loc[1]].connections[(i+2)%4]) {
                        this.connectedTiles[i] = this.board[loc[0]][loc[1]];
                    }
                } else {
                    this.connectedTiles[i] = null;
                }
            } else {
                this.connectedTiles[i] = null;
            }
        }
    }

    public Tile[] getConnectedTiles() {
        return this.connectedTiles;
    }

    public void calculateConnections() {
        switch (this.type) {
            case CORNER: {
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
            }
            case STRAIGHT: {
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
            }
            case INTERSECTION: {
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
            }
        }

    }

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
                "Player on Tile: " + this.pawn.name() + " }";
    }
}
