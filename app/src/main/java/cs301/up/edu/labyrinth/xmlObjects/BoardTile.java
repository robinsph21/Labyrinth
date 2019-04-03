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
    private TreasureType treasure;
    private GamePlayer player;
    private Player pawn;

    //TODO: Finish Commenting this
    /**
     * Ctor for a BoardTile
     * @param v the imageview of each boardtile
     * @param locX the x-coordinate
     * @param locY
     * @param fixed
     * @param player
     * @param game
     */
    public BoardTile(View v, int locX, int locY, boolean fixed,
                     GamePlayer player, Game game) {
        super(v, locX,locY);
        this.fixed = fixed;
        this.player = player;
        this.game = game;
        this.getXmlObj().setOnClickListener(this);
    }

    public boolean isFixed() {
        return this.fixed;
    }

    public TreasureType getTreasure() {
        return this.treasure;
    }

    public void setTreasure(TreasureType treasure) {
        this.treasure = treasure;
    }

    public Player getPawn() {
        return this.pawn;
    }

    public void setPawn(Player pawn) {
        this.pawn = pawn;
    }

    @Override
    public void onClick(View v) {
        // if we are not yet connected to a game, ignore
        if (this.game == null) return;

        // Construct the action and send it to the game
        GameAction action = null;

        action = new LabyrinthMovePawnAction(this.player,
                this.getLocX() - 1, this.getLocY() - 1);

        game.sendAction(action);
    }
}
