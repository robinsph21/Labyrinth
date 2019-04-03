package cs301.up.edu.labyrinth.xmlObjects;

import android.view.View;

import cs301.up.edu.game.Game;
import cs301.up.edu.game.GamePlayer;
import cs301.up.edu.game.actionMsg.GameAction;
import cs301.up.edu.labyrinth.actions.LabyrinthEndTurnAction;

/**
 * Represents the EndTurn button on the GUI
 *
 * @author Erik Torkelson, Spencer Nelson, Spencer Rose, Philip Robinson
 * Date: 2/27/2019
 */

public class EndTurn extends XMLObject {

    /* Instance Variables */
    private GamePlayer player;
    private Game game;

    /**
     * Ctor for the EndTurn object
     * @param v the EndTurn buttons view in the layout
     * @param player the player who can see this button
     * @param game the current game
     */
    public EndTurn(View v, GamePlayer player, Game game) {
        super(v);
        this.game = game;
        this.player = player;
        this.getXmlObj().setOnClickListener(this);
    }

    /**
     * Required onClick method
     * @param v the view being clicked
     */
    @Override
    public void onClick(View v){
        // if we are not yet connected to a game, ignore
        if (this.game == null) return;

        // Construct the action and send it to the game
        GameAction action = null;

        //make the action an EndTurn Action
        action = new LabyrinthEndTurnAction(this.player);

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
