package cs301.up.edu.labyrinth.actions;

import cs301.up.edu.game.GamePlayer;
import cs301.up.edu.game.actionMsg.GameAction;

/**
 * A LabyrinthMovePawnAction is an action that is a "move" the game: either increasing
 * or decreasing the counter value.
 * 
 * @author Steven R. Vegdahl
 * @author Andrew M. Nuxoll
 * @version September 2012
 */
public class LabyrinthMovePawnAction extends GameAction {
	
	// to satisfy the serializable interface
	private static final long serialVersionUID = 2800982462013L;
	
	/**
	 * Constructor for the LabyrinthMovePawnAction class.
	 * 
	 * @param player
	 *            the player making the move
	 */
	public LabyrinthMovePawnAction(GamePlayer player) {
		super(player);
	}

}//class LabyrinthMovePawnAction
