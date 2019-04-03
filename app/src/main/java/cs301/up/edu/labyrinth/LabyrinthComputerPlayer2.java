package cs301.up.edu.labyrinth;

import java.util.ArrayList;
import java.util.List;

import cs301.up.edu.game.GameComputerPlayer;
import cs301.up.edu.game.actionMsg.GameAction;
import cs301.up.edu.game.infoMsg.GameInfo;
import cs301.up.edu.labyrinth.actions.LabyrinthEndTurnAction;
import cs301.up.edu.labyrinth.actions.LabyrinthMovePawnAction;
import cs301.up.edu.labyrinth.actions.LabyrinthRotateAction;
import cs301.up.edu.labyrinth.actions.LabyrinthSlideTileAction;
import cs301.up.edu.labyrinth.enums.Arrow;
import cs301.up.edu.labyrinth.enums.Player;

import static java.lang.Math.sqrt;

/**
 * A computer-version of a Labyrinth-player.  Since this is such a simple game,
 * it just sends "+" and "-" commands with equal probability, at an average
 * rate of one per second.
 *
 * @author Steven R. Vegdahl
 * @author Andrew M. Nuxoll
 * @author Erik Torkelson, Spencer Nelson, Spencer Rose, Philip Robinson
 * @version 3/1/19
 */
public class LabyrinthComputerPlayer2 extends GameComputerPlayer
        implements Runnable{

    //Instance Variables
    private LabyrinthGameState state;

    private List<GameAction> queue = new ArrayList<>();

    private List<double[]> possibleMoves = new ArrayList<>();
    private int possibleRotation;

    /**
     * Constructor for objects of class LabyrinthComputerPlayer1
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
        // ignore the message if it's not a LabyrinthState message
        if (info instanceof LabyrinthGameState) {
            this.state = (LabyrinthGameState) info;
        }
        if (state.getPlayerTurn().ordinal() == playerNum) {
            if (this.queue.size() > 0) {
                try {
                    Thread.sleep(250);
                } catch (Exception e) {
                }
                this.game.sendAction(this.pull());
            } else if (info instanceof LabyrinthGameState) {
                this.state = (LabyrinthGameState) info;
                this.calculateActions();
                this.game.sendAction(this.pull());
            }
        }


    }

    public void run() {
        AINode root = new AINode(this.state);
        switch (this.possibleRotation) {
            case 0:
                this.possibleRotation++;
                Thread instance2 = new Thread(this);
                instance2.start();
                this.generateMoves(root, 0);
                try {
                    instance2.join();
                } catch (InterruptedException e) {
                }
                break;
            case 1:
                this.possibleRotation++;
                Thread instance3 = new Thread(this);
                instance3.start();
                this.generateMoves(root, 1);
                try {
                    instance3.join();
                } catch (InterruptedException e) {
                }
                break;
            case 2:
                this.possibleRotation++;
                Thread instance4 = new Thread(this);
                instance4.start();
                this.generateMoves(root, 2);
                try {
                    instance4.join();
                } catch (InterruptedException e) {
                }
                break;
            case 3:
                this.generateMoves(root, 3);
                break;
        }

    }

    private void calculateActions() {

        //Generate All Possible Moves In Tree
        this.possibleMoves.clear();
        this.possibleRotation = 0;
        Thread instance = new Thread(this);
        instance.start();
        try {
            instance.join();
        } catch (InterruptedException e) {}

        //Choose Best Move From List
        double [] move = chooseBestMove(possibleMoves);

        //Calculate Actions from this move and push to queue
        generateActions(move);
    }

    private void generateMoves(AINode root, int i) {

        //Make All Rotates
        AINode rotateNode = new AINode(root.copyState());
        for (int j = 0; j <= i; j++) {
            rotateNode.getState().checkRotate(true);
        }

        //Check Slides
        for (int j = 0; j < 12; j++ ) {
            if (rotateNode.getState().getDisabledArrow().ordinal() == j) {
                continue;
            }
            AINode slideNode = new AINode(rotateNode.copyState());
            slideNode.getState().checkSlideTile(Arrow.values()[j]);

            //Check Moves
            List<int[]> movePlaces = generatePossibleMoveActions(
                    slideNode.getState());
            for (int[] spot : movePlaces) {
                AINode moveNode = new AINode(slideNode.copyState());
                moveNode.getState().checkMovePawn(spot[0], spot[1]);

                double eval = evalState(moveNode.getState());
                synchronized (possibleMoves) {
                    possibleMoves.add(new double[]{i, j, spot[0], spot[1], eval});
                }
            }
        }
    }



    private void generateActions(double[] move) {
        //Rotate Actions
        for (int i = 0; i <= move[0]; i++) {
            GameAction act1 = new LabyrinthRotateAction(this,true);
            this.push(act1);
        }

        //Slide Action
        GameAction act2 = new LabyrinthSlideTileAction(this,
                Arrow.values()[(int)move[1]]);
        this.push(act2);

        //Move Action
        GameAction act3 = new LabyrinthMovePawnAction(this,
                (int)move[2],(int)move[3]);
        this.push(act3);

        //End Turn Action
        GameAction act4 = new LabyrinthEndTurnAction(this);
        this.push(act4);
    }

    /**
     * Chooses the best scored move out of the list
     *
     *                        [Rotation,Arrow,x,y,score]
     * @param possibleMoves = [0-3,0-11,0-6,0-6,0-1]
     */
    private double[] chooseBestMove(List<double[]> possibleMoves) {
        double bestScore = 0;
        int indexBest = 0;
        for (int i = 0; i < possibleMoves.size(); i++) {
            if (possibleMoves.get(i)[4] > bestScore) {
                bestScore = possibleMoves.get(i)[4];
                indexBest = i;
            }
        }
        return possibleMoves.get(indexBest);
    }

    /**
     * Evaluates the current gamestate and assigns a score between 0 and 1
     * with 1 being you are about to win and 0 being you will lose.
     *
     *
     * @return a score of how good the state is for the current player
     */
    private double evalState(LabyrinthGameState state) {
        double score = 0;

        //Assign Percentages of Score
        final double treasureValTotal = 80; //Based on number of treasures left
        final double nearTreasureValTotal = 15; //Based on proximity to treasure
        final double typeTileValTotal = 1; //Based on which tile you are on
        final double numberOfConnectionsValTotal = 4; //Based on how many places you can move

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

        numberOfConnectionsVal = ((double)generatePossibleMoveActions(state).size()
                /49.0*numberOfConnectionsValTotal);

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

    private List<int[]> generatePossibleMoveActions(LabyrinthGameState state) {
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


    private GameAction pull() {
        if (this.queue.size() > 0) {
            return this.queue.remove(0);
        } else {
            return null;
        }
    }

    public void test() {

    }
}
