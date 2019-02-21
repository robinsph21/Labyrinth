package cs301.up.edu.labyrinth;

import java.io.Serializable;

import cs301.up.edu.enums.Player;
import cs301.up.edu.enums.TileType;
import cs301.up.edu.enums.TreasureType;

public class Tile implements Serializable {

    private static final long serialVersionUID = 323737276246984926L;

    private final TileType type;
    private int rotation;
    private boolean[] connections = new boolean[4]; //Left, Up, Right, Down
    private TreasureType treasure;
    private Player pawn;

    public Tile(TileType type, int rotation, TreasureType treasure, Player pawn) {
        this.type = type;
        this.rotation = rotation;
        this.treasure = treasure;
        this.connections = calculateConnections();
        this.pawn = pawn;
    }

    public Tile(TileType type, int rotation, TreasureType treasure) {
        this(type, rotation, treasure, Player.None);
    }

    public Tile(TileType type, int rotation, Player pawn) {
        this(type, rotation, TreasureType.NONE, pawn);
    }

    public Tile(TileType type, int rotation) {
        this(type, rotation, TreasureType.NONE, Player.None);
    }

    public void rotateClockwise() {
        this.rotation = (this.rotation + 90) % 360;
        this.connections = calculateConnections();
    }

    public void rotateCounterClockwise() {
        this.rotation = (this.rotation + 270) % 360;
        this.connections = calculateConnections();
    }

    public TileType getType() {
        return this.type;
    }

    public int getRotation() {
        return this.rotation;
    }

    public boolean[] getConnections() {
        return this.connections;
    }

    public Player getPawn() {
        return pawn;
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

    private boolean[] calculateConnections() {
        switch (this.type) {
            case CORNER: {
                switch (rotation) {
                    case 0: return new boolean[]{false, false, true, true};
                    case 90: return new boolean[]{true, false, false, true};
                    case 180: return new boolean[]{true, true, false, false};
                    case 270: return new boolean[]{false, true, true, false};
                    default: return new boolean[]{false, false, false, false};
                }
            }
            case STRAIGHT: {
                switch (rotation) {
                    case 0: return new boolean[]{true, false, true, false};
                    case 90: return new boolean[]{false, true, false, true};
                    case 180: return new boolean[]{true, false, true, false};
                    case 270: return new boolean[]{false, true, false, true};
                    default: return new boolean[]{false, false, false, false};
                }
            }
            case INTERSECTION: {
                switch (rotation) {
                    case 0: return new boolean[]{true, true, false, true};
                    case 90: return new boolean[]{true, true, true, false};
                    case 180: return new boolean[]{false, true, true, true};
                    case 270: return new boolean[]{true, false, true, true};
                    default: return new boolean[]{false, false, false, false};
                }
            }
            default: {
                return new boolean[]{false, false, false, false};
            }
        }
    }
}
