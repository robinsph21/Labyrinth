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
 * A computer-version of a Labyrinth-player. This si the smarter AI
 * that will generate all of its possible moves for a turn and
 * evaluate each one based on a heuristic we made
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

    /**
     * Run method for the threads that are spawned to calculate
     * AI possible moves
     */
    public void run() {
        //Start the root of the tree that will hold all possible AI moves
        AINode root = new AINode(this.state);

        //make all 4 possible rotations of the current tile
        //and spawn a thread to calculate the rest of the
        //possible move actions
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

    /**
     * This method will call a method to generate all
     * the moves the AI can make, and store them, then
     * call another method to decide which is the
     * best move, then generate the final move action
     */
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

    /**
     * Method to generate all the possible moves the AI
     * can make
     * @param root the root of the actions tree
     * @param i based on the rotation number of the node
     */
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


    /**
     * Will generate the game actions corresponding to the movement
     * actions decided earlier and send them to the gamestate
     * @param move the array of move choices of the AI
     */
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
        final double treasureValTotal = 75; //Based on number of treasures left
        final double nearTreasureValTotal = 15; //Based on proximity to treasure
        final double typeTileValTotal = 3; //Based on which tile you are on
        final double numberOfConnectionsValTotal = 7; //Based on how many places you can move

        double treasureVal = 0;
        double nearTreasureVal = 0;
        double typeTileVal = 0;
        double numberOfConnectionsVal = 0;

        //calculating your treasure points
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

        //calculating points for the type of tile it can end on
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

        //calculating points based on # of connections created
        numberOfConnectionsVal = ((double)generatePossibleMoveActions(state).size()
                /49.0*numberOfConnectionsValTotal);

        //calculating final score
        score = (treasureVal + nearTreasureVal +
                typeTileVal + numberOfConnectionsVal)/100.0;

        return score;
    }

    /**
     * Calculates distance between any 2 given tiles
     * @param pos1 the x&y pos of one tile
     * @param pos2 the x&y pos of another tile
     * @return the distance between them
     */
    private double findDistance(int[] pos1, int[] pos2) {
        return sqrt((pos1[0]-pos2[0])*(pos1[0]-pos2[0])+
                (pos1[1]-pos2[1])*(pos1[1]-pos2[1]));
    }

    /**
     * Calculate which board corner is home
     * based on the player number
     * @return the x&y pos of the home corner
     */
    private int[] findHome() {
        int[] loc = new int[2];
        switch (playerNum) {
            case 0: //player RED's home
                loc[0] = 0;
                loc[1] = 0;
                break;
            case 1: //player YELLOW's home
                loc[0] = 6;
                loc[1] = 0;
                break;
            case 2: //player GREEN's home
                loc[0] = 6;
                loc[1] = 6;
                break;
            case 3: //player BLUE's home
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

    /**
     * Will generate an ArrayList of every possible locations on the board
     * that the player can move to
     * @param state the current state of the game
     * @return an arraylist of integer coordinates
     */
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

    /**
     * Will recursively look for available locations to move to
     * and add them to the instance array list of availableSpots
     * @param orig
     * @param availableSpots
     */
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

    /**
     * Method for pushing actions onto the queue
     * @param action the action to be pushed
     */
    private void push(GameAction action) {
        this.queue.add(action);
    }


    /**
     * Method to pull actions off the top of the queue
     * @return the action returned
     */
    private GameAction pull() {
        if (this.queue.size() > 0) {
            return this.queue.remove(0);
        } else {
            return null;
        }
    }
}
