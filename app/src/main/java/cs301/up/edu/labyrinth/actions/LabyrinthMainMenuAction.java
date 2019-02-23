package cs301.up.edu.labyrinth.actions;

import cs301.up.edu.game.GamePlayer;
import cs301.up.edu.game.actionMsg.GameAction;

/**
 * A LabyrinthEndTurnAction is an action that is a "move" the game: either increasing
 * or decreasing the counter value.
 * 
 * @author Steven R. Vegdahl
 * @author Andrew M. Nuxoll
 * @version September 2012
 */
public class LabyrinthMainMenuAction extends GameAction {

	// to satisfy the serializable interface
	private static final long serialVersionUID = 1290686476413L;

	/**
	 * Constructor for the LabyrinthEndTurnAction class.
	 *
	 * @param player
	 *            the player making the move
	 */
	public LabyrinthMainMenuAction(GamePlayer player) {
		super(player);
	}

}//class LabyrinthEndTurnAction
