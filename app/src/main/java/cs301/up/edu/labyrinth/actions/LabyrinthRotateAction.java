package cs301.up.edu.labyrinth.actions;

import cs301.up.edu.game.GamePlayer;
import cs301.up.edu.game.actionMsg.GameAction;

/**
 * A LabyrinthRotateAction is an action that is a "move" the game: either increasing
 * or decreasing the counter value.
 *
 * @author Steven R. Vegdahl
 * @author Andrew M. Nuxoll
 * @version September 2012
 */
public class LabyrinthRotateAction extends GameAction {

    // to satisfy the serializable interface
    private static final long serialVersionUID = 87806886413L;
    private boolean clockwise;

    /**
     * Constructor for the LabyrinthRotateAction class.
     *
     * @param player
     *            the player making the move
     */
    public LabyrinthRotateAction(GamePlayer player, boolean clockwise) {
        super(player);
        this.clockwise = clockwise;
    }

    public boolean isClockwise() {
        return clockwise;
    }

}//class LabyrinthRotateAction
