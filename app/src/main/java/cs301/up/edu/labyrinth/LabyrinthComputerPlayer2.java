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
import cs301.up.edu.labyrinth.actions.LabyrinthResetAction;
import cs301.up.edu.labyrinth.actions.LabyrinthRotateAction;
import cs301.up.edu.labyrinth.actions.LabyrinthSlideTileAction;
import cs301.up.edu.labyrinth.enums.Arrow;
import cs301.up.edu.labyrinth.enums.Player;

import static java.lang.Math.sqrt;

/**
 * A computer-version of a counter-player.  Since this is such a simple game,
 * it just sends "+" and "-" commands with equal probability, at an average
 * rate of one per second.
 *
 * @author Steven R. Vegdahl
 * @author Andrew M. Nuxoll
 * @version September 2013
 */
public class LabyrinthComputerPlayer2 extends GameComputerPlayer {

    //Instance Variables
    private LabyrinthGameState state;

    private List<GameAction> queue = new ArrayList<>();

    /**
     * Constructor for objects of class CounterComputerPlayer1
     *
     * @param name
     * 		the player's name
     */
    public LabyrinthComputerPlayer2(String name) {
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
                /**
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                }
                 */
                this.game.sendAction(this.pop());
            } else if (info instanceof LabyrinthGameState) {
                this.state = (LabyrinthGameState) info;
                this.calculateNextMoves();
                this.game.sendAction(this.pop());
            }
        }


    }

    private void calculateNextMoves() {

        //Find All Possible Locations to Move To
        //List<int[]> moves = this.generatePossibleMoveActions();

        //Choose a random arrow
        Arrow randomArrow = state.getDisabledArrow();
        int randomArrowChoice;
        while (randomArrow == state.getDisabledArrow()) {
            randomArrowChoice = new Random().nextInt(12);
            randomArrow = Arrow.values()[randomArrowChoice];
        }

        //Create game actions based in the info we calculated above
        GameAction rotate = new LabyrinthRotateAction(this,true);
        GameAction slideTile = new LabyrinthSlideTileAction(this,
                randomArrow);
        GameAction endTurn = new LabyrinthEndTurnAction(this);

        //Push actions to AI turn Queue
        this.push(rotate);
        this.push(slideTile);
        this.push(endTurn);
    }

    /**
     * Evaluates the current gamestate and assigns a score between 0 and 1
     * with 1 being you are about to win and 0 being you will lose.
     *
     *
     * @return a score of how good the state is for the current player
     */
    private double evalState() {
        double score = 0;

        //Assign Percentages of Score
        final double treasureValTotal = 70; //Based on number of treasures left
        final double nearTreasureValTotal = 10; //Based on proximity to treasure
        final double typeTileValTotal = 10; //Based on which tile you are on
        final double numberOfConnectionsValTotal = 10; //Based on how many places you can move

        double treasureVal = 0;
        double nearTreasureVal = 0;
        double typeTileVal = 0;
        double numberOfConnectionsVal = 0;

        treasureVal = (6.0 - (double)(state.getPlayerDeckSize(
                Player.values()[playerNum])))/6.0*treasureValTotal;

        int [] yourPos = state.getPlayerLoc(Player.values()[playerNum]).getLoc();
        int [] treasurePos = state.findTreasureLoc(Player.values()[playerNum]);
        if (treasurePos[0] == -1) {
            nearTreasureVal = (10.0 - findDistance(yourPos, findHome()))
                    / 10.0 * nearTreasureValTotal;
        } else {
            nearTreasureVal = (10.0 - findDistance(yourPos, treasurePos))
                    / 10.0 * nearTreasureValTotal;
        }

        switch (state.getPlayerLoc(Player.values()[playerNum]).getType()) {
            case STRAIGHT:
                typeTileVal = 5.0 / 10.0 * typeTileValTotal;
                break;
            case INTERSECTION:
                typeTileVal = typeTileValTotal;
                break;
            default:
                typeTileVal = 3.0 / 10.0 * typeTileValTotal;
                break;
        }

        //TODO: Calculate number of connections

        score = (treasureVal + nearTreasureVal +
                typeTileVal + numberOfConnectionsVal)/100.0;
        return score;
    }

    private double findDistance(int[] pos1, int[] pos2) {
        return sqrt((pos1[0]-pos2[0])*(pos1[0]-pos2[0])+
                (pos1[1]-pos2[1])*(pos1[1]-pos2[1]));
    }

    private int[] findHome() {
        int[] loc = new int[2];
        switch (playerNum) {
            case 0:
                loc[0] = 0;
                loc[1] = 0;
                break;
            case 1:
                loc[0] = 6;
                loc[1] = 0;
                break;
            case 2:
                loc[0] = 6;
                loc[1] = 6;
                break;
            case 3:
                loc[0] = 0;
                loc[1] = 6;
                break;
            default:
                loc[0] = -1;
                loc[1] = -1;
                break;
        }
        return loc;
    }

    private List<int[]> generatePossibleMoveActions() {
        List<Tile> tiles = new ArrayList<>();
        Tile orig = state.getPlayerLoc(Player.values()[playerNum]);
        tiles.add(orig);
        this.calculatePossibleMoves(orig, tiles);
        List<int[]> locations = new ArrayList<>();
        for (Tile spot : tiles) {
            int[] loc = new int[2];
            loc[0] = spot.getX();
            loc[1] = spot.getY();
            locations.add(loc);
        }
        return locations;
    }

    private void calculatePossibleMoves(Tile orig, List<Tile> availableSpots) {
        for (Tile spot : orig.getConnectedTiles()) {
            if (spot != null) {
                if (!availableSpots.contains(spot)) {
                    availableSpots.add(spot);
                    this.calculatePossibleMoves(spot, availableSpots);
                }
            }
        }
    }



    /**
     * Queue Stuff
     */

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

    public void test(LabyrinthComputerPlayer2 testPlayer) {

    }
}
