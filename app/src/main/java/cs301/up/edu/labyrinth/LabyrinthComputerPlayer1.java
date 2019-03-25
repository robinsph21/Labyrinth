package cs301.up.edu.labyrinth;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cs301.up.edu.game.GameComputerPlayer;
import cs301.up.edu.game.actionMsg.GameAction;
import cs301.up.edu.game.infoMsg.GameInfo;
import cs301.up.edu.game.infoMsg.IllegalMoveInfo;
import cs301.up.edu.game.infoMsg.NotYourTurnInfo;
import cs301.up.edu.game.util.Tickable;
import cs301.up.edu.labyrinth.actions.LabyrinthEndTurnAction;
import cs301.up.edu.labyrinth.actions.LabyrinthMovePawnAction;
import cs301.up.edu.labyrinth.actions.LabyrinthRotateAction;
import cs301.up.edu.labyrinth.actions.LabyrinthSlideTileAction;
import cs301.up.edu.labyrinth.enums.Arrow;
import cs301.up.edu.labyrinth.enums.Player;

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
            this.state = (LabyrinthGameState) info;
        }
        if (state.getPlayerTurn().ordinal() == playerNum) {
            if (this.queue.size() > 0) {
                try {
                    Thread.sleep(500);
                } catch (Exception e) {

                }
                this.game.sendAction(this.pop());
            } else {
                if (info instanceof LabyrinthGameState) {
                    this.state = (LabyrinthGameState) info;
                    this.calculateNextMoves();
                    this.game.sendAction(this.pop());
                } else if (info instanceof NotYourTurnInfo) {
                    ;
                } else if (info instanceof IllegalMoveInfo) {
                    ;
                }
            }
        }


    }

    private void calculateNextMoves() {
        //Figure out actions and push to queue

        //Choose a random arrow
        Arrow randomArrow = state.getDisabledArrow();
        int randomArrowChoice;
        while (randomArrow == state.getDisabledArrow()) {
            randomArrowChoice = new Random().nextInt(12);
            randomArrow = Arrow.values()[randomArrowChoice];
        }

        //Get the spot the AI is currently occupying
        Tile playerSpot = state.getPlayerLoc(Player.values()[playerNum]);
        int x = playerSpot.getX();
        int y = playerSpot.getY();

        //Move to the first available connected tile
        //If there are no connected tiles the AI will remain on its current tile
        for (Tile thisSpot : playerSpot.getConnectedTiles()) {
            if (thisSpot != null) {
                x = thisSpot.getX();
                y = thisSpot.getY();
            }
        }

        //Create game actions based in the info we calculated above
        GameAction rotate = new LabyrinthRotateAction(this,true);
        GameAction slideTile = new LabyrinthSlideTileAction(this,
                randomArrow);
        GameAction movePawn = new LabyrinthMovePawnAction(this,x,y);
        GameAction endTurn = new LabyrinthEndTurnAction(this);

        //Push actions to AI turn Queue
        this.push(rotate);
        this.push(slideTile);
        this.push(movePawn);
        this.push(endTurn);
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
