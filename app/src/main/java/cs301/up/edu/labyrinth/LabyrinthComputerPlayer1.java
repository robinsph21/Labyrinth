package cs301.up.edu.labyrinth;

import java.util.ArrayList;
import java.util.List;

import cs301.up.edu.game.GameComputerPlayer;
import cs301.up.edu.game.actionMsg.GameAction;
import cs301.up.edu.game.infoMsg.GameInfo;
import cs301.up.edu.game.infoMsg.IllegalMoveInfo;
import cs301.up.edu.game.infoMsg.NotYourTurnInfo;
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
public class LabyrinthComputerPlayer1 extends GameComputerPlayer {

    //TODO: basic AI using queue

    //Instance Variables
    private LabyrinthGameState state;

    private List<GameAction> queue = new ArrayList<>();

    /**
     * Constructor for objects of class CounterComputerPlayer1
     *
     * @param name
     * 		the player's name
     */
    public LabyrinthComputerPlayer1(String name) {
        // invoke superclass constructor
        super(name);
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
        if (info instanceof LabyrinthGameState) {
            // update our state; then update the display
            this.state = (LabyrinthGameState) info;

            //If moves are already calculated, send next one
            //otherwise, calculate moves
            if (this.queue.size() > 0) {
                this.game.sendAction(this.pop());
            } else {
                this.calculateNextMoves();
            }

        } else if (info instanceof NotYourTurnInfo) {
            ;
        } else if (info instanceof IllegalMoveInfo) {
            ;
        }
    }

    private void calculateNextMoves() {
        //Figure out actions and push to queue
    }

    private void push(GameAction action) {
        this.queue.add(action);
    }


    private GameAction pop() {
        if (this.queue.size() > 0) {
            return this.queue.remove(0);
        } else {
            return null;
        }
    }
}
