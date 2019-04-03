package cs301.up.edu.labyrinth.xmlObjects;

import android.view.View;

import cs301.up.edu.game.Game;
import cs301.up.edu.game.GamePlayer;
import cs301.up.edu.game.actionMsg.GameAction;
import cs301.up.edu.labyrinth.actions.LabyrinthRotateAction;
/**
 * Represents the Rotate button on the GUI that rotates the
 * players current tile
 *
 * @author Erik Torkelson, Spencer Nelson, Spencer Rose, Philip Robinson
 * Date: 2/27/2019
 */
public class Rotate extends XMLObject {

    /* Instance Variables */
    private final GamePlayer player;
    private Game game;
    private boolean clockwise;

    /**
     * Ctor for the Rotate XMLObject
     * @param v the buttons view in the GUI activity
     * @param clockwise a boolean used to decide if this is a clockwise or c-clockwise button
     * @param player the player who can see the button
     * @param game the current game
     */
    public Rotate(View v, boolean clockwise, GamePlayer player, Game game) {
        //calling super constructor
        super(v);
        this.clockwise = clockwise;
        this.player = player;
        this.game = game;
        this.getXmlObj().setOnClickListener(this);
    }

    /**
     * Required onClick method for button listener
     * @param v the rotate button view that is being clicked
     */
    public void onClick(View v) {
        // if we are not yet connected to a game, ignore
        if (this.game == null) return;

        // Construct the action and send it to the game
        GameAction action = null;

        //define the action as a clockwise rotation action
        action = new LabyrinthRotateAction(this.player, this.clockwise);

        game.sendAction(action);
    }

    /**
     * mutator for the game in the rotate object
     * @param game the game that you want to set
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * accessor for the game in the rotate object
     * @return returns the game
     */
    public Game getGame() {
        return this.game;
    }
}
