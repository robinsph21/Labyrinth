package cs301.up.edu.labyrinth.xmlObjects;

import android.view.View;

import cs301.up.edu.labyrinth.enums.Player;
import cs301.up.edu.labyrinth.enums.TreasureType;
import cs301.up.edu.game.Game;
import cs301.up.edu.game.GamePlayer;
import cs301.up.edu.game.actionMsg.GameAction;
import cs301.up.edu.labyrinth.actions.LabyrinthMovePawnAction;
/**
 * Defines a BoardTile, which is a tile on the game board in the GUI
 *
 * @author Erik Torkelson, Spencer Nelson, Spencer Rose, Philip Robinson
 * Date: 2/27/2019
 */
public class BoardTile extends BoardSpot {

    /* Instance Variables */
    private boolean fixed;
    private TreasureType treasure; //the type of treasure on the tile
    private GamePlayer player;
    private Player pawn;


    /**
     * Ctor for a BoardTile
     * @param v the imageview of each boardtile
     * @param locX the x-coordinate of the tile on the board
     * @param locY the y-coordinate of the tile on the board
     * @param fixed a boolean of whether the tile is fixed or movable
     * @param player the player viewing the GUI
     * @param game the current game
     */
    public BoardTile(View v, int locX, int locY, boolean fixed,
                     GamePlayer player, Game game) {
        //calling parent constructor
        super(v, locX,locY);
        this.fixed = fixed;
        this.player = player;
        this.game = game;
        this.getXmlObj().setOnClickListener(this);
    }

    /**
     * An accessor for the fixed boolean
     * @return
     */
    public boolean isFixed() {
        return this.fixed;
    }

    /**
     * An accessor for the treasure type
     * @return
     */
    public TreasureType getTreasure() {
        return this.treasure;
    }

    /**
     * Mutator for the treasure type
     * @param treasure
     */
    public void setTreasure(TreasureType treasure) {
        this.treasure = treasure;
    }

    /**
     * Accessor for the type of pawn on the tile
     * @return
     */
    public Player getPawn() {
        return this.pawn;
    }

    /**
     * Mutator for the pawn on the tile
     * @param pawn
     */
    public void setPawn(Player pawn) {
        this.pawn = pawn;
    }

    /**
     * Required on click method
     * @param v the view being clicked
     */
    @Override
    public void onClick(View v) {
        // if we are not yet connected to a game, ignore
        if (this.game == null) return;

        // Construct the action and send it to the game
        GameAction action = null;

        //make the action a new movement action
        action = new LabyrinthMovePawnAction(this.player,
                this.getLocX() - 1, this.getLocY() - 1);

        //send the action
        game.sendAction(action);
    }
}
