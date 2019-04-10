package cs301.up.edu.labyrinth;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cs301.up.edu.game.GameComputerPlayer;
import cs301.up.edu.game.GamePlayer;
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
 * A computer-version of a Labyrinth-player. This player will randomly insert
 * a tile into the board, and try and move one space in a random direction.
 *
 * @author Steven R. Vegdahl
 * @author Andrew M. Nuxoll
 * @author Erik Torkelson, Spencer Nelson, Spencer Rose, Philip Robinson
 * @version 3/1/19
 */
public class LabyrinthComputerPlayer1 extends GameComputerPlayer {

    //Instance Variables
    private LabyrinthGameState state;
    private List<GameAction> queue = new ArrayList<>(); //Queue of AI move
                                                        //actions

    /**
     * Constructor for objects of class LabyrinthComputerPlayer1
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
        //Ignore the message if it's not a LabyrinthState message
        if (info instanceof LabyrinthGameState) {
            this.state = (LabyrinthGameState) info;
        }
        //Make sure it is the correct player's turn
        if (state.getPlayerTurn().ordinal() == playerNum) {
            //If there are any actions in the queue
            if (this.queue.size() > 0) {
                try {
                    //Slow down so a human can watch the AI take actions
                    Thread.sleep(500);
                } catch (Exception e) {

                }
                //Send the action action
                this.game.sendAction(this.pop());
            //If there are no actions in the queue
            } else {
                //Ignore the message if it's not a LabyrinthState message
                if (info instanceof LabyrinthGameState) {
                    //Calculate the actions to take
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

    /**
     * Method to calculate AI moves. Will randomly insert a tile, and will
     * move to first available space it can reach
     */
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

    /**
     * Method to add an action to the action queue
     * @param action The action to add
     */
    private void push(GameAction action) {
        this.queue.add(action);
    }


    /**
     * Method to remove an action from the queue
     * @return The updated queue
     */
    private GameAction pop() {
        if (this.queue.size() > 0) {
            return this.queue.remove(0);
        } else {
            return null;
        }
    }

    /**
     * Method to test the AI player. Called from the test package
     */
    public void test() {
        //Set the correct states
        LabyrinthGameState testState = new LabyrinthGameState();
        this.state = testState;

        //Make some moves
        this.calculateNextMoves();

        //Make sure the correct actions are in the queue
        assert (queue.size() == 4);
        assert (queue.get(0) instanceof LabyrinthRotateAction);
        assert (queue.get(1) instanceof LabyrinthSlideTileAction);
        assert (queue.get(2) instanceof LabyrinthMovePawnAction);
        assert (queue.get(3) instanceof LabyrinthEndTurnAction);

        //Send the move actions and assert that they are the expected actions
        LabyrinthRotateAction move1 = (LabyrinthRotateAction)pop();
        assert (move1.isClockwise());

        LabyrinthSlideTileAction move2 = (LabyrinthSlideTileAction)pop();
        assert (move2.getThisArrow() != Arrow.NONE);

        LabyrinthMovePawnAction move3 = (LabyrinthMovePawnAction)pop();
        assert (move3.getLocX() == 0 | move3.getLocX() == 1) &
                (move3.getLocY() == 0 | move3.getLocY() == 1);

        LabyrinthEndTurnAction move4 = (LabyrinthEndTurnAction) pop();
        assert (move4 != null);

        //Make sure the queue is empty after all the moves are made
        assert (queue.size() == 0);
    }
}
