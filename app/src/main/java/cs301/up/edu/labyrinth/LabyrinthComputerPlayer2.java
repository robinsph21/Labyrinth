package cs301.up.edu.labyrinth;

import cs301.up.edu.game.GameComputerPlayer;
import cs301.up.edu.game.infoMsg.GameInfo;
import cs301.up.edu.game.util.Tickable;
import cs301.up.edu.labyrinth.actions.LabyrinthEndTurnAction;

/**
 * A computer-version of a counter-player.  Since this is such a simple game,
 * it just sends "+" and "-" commands with equal probability, at an average
 * rate of one per second.
 *
 * @author Steven R. Vegdahl
 * @author Andrew M. Nuxoll
 * @version September 2013
 */
public class LabyrinthComputerPlayer2 extends GameComputerPlayer implements Tickable {

    /**
     * Constructor for objects of class CounterComputerPlayer1
     *
     * @param name
     * 		the player's name
     */
    public LabyrinthComputerPlayer2(String name) {
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
        // Do nothing, as we ignore all state in deciding our next move. It
        // depends totally on the timer and random numbers.
    }

    /**
     * callback method: the timer ticked
     */
    protected void timerTicked() {


        // send the move-action to the game
        game.sendAction(new LabyrinthEndTurnAction(this));
    }
}
