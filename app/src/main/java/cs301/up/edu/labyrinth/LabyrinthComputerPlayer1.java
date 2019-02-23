package cs301.up.edu.labyrinth;

import cs301.up.edu.game.GameComputerPlayer;
import cs301.up.edu.game.infoMsg.GameInfo;
import cs301.up.edu.game.util.Tickable;
import cs301.up.edu.labyrinth.actions.LabyrinthMovePawnAction;

/**
 * A computer-version of a counter-player.  Since this is such a simple game,
 * it just sends "+" and "-" commands with equal probability, at an average
 * rate of one per second.
 *
 * @author Steven R. Vegdahl
 * @author Andrew M. Nuxoll
 * @version September 2013
 */
public class LabyrinthComputerPlayer1 extends GameComputerPlayer
        implements Tickable {

    //Instance Variables
    private LabyrinthGameState state;

    /**
     * Constructor for objects of class CounterComputerPlayer1
     *
     * @param name
     * 		the player's name
     */
    public LabyrinthComputerPlayer1(String name) {
        // invoke superclass constructor
        super(name);

        // start the timer, ticking 20 times per second
        getTimer().setInterval(50);
        getTimer().start();
    }

    /**
     * callback method--game's state has changed
     *
     * @param info
     * 		the information (presumably containing the game's state)
     */
    @Override
    protected void receiveInfo(GameInfo info) {
        // ignore the message if it's not a CounterState message
        if (!(info instanceof LabyrinthGameState)) return;

        // update our state
        this.state = (LabyrinthGameState)info;
    }

    /**
     * callback method: the timer ticked
     */
    protected void timerTicked() {
        // Calculate what to do based on state

        // send the move-action to the game
        game.sendAction(new LabyrinthMovePawnAction(this));
    }
}
