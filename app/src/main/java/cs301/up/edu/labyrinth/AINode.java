package cs301.up.edu.labyrinth;

/**
 * Defines a complete gameState. The AI will create multiple nodes so that it
 * can determine its best possible move.
 *
 * @author Erik Torkelson, Spencer Nelson, Spencer Rose, Philip Robinson
 * Date: 3/27/2019
 */
public class AINode {

    //Instance variables
    private LabyrinthGameState myState;

    /**
     * Ctor for AINode. Requires a complete gameState
     * @param myState The current state of the game to analyze
     */
    public AINode(LabyrinthGameState myState) {
        this.myState = myState;
    }

    /**
     * Method to copy the a node. This will be called by the AI so that multiple
     * Nodes can be created, each with different gameState information.
     * @return
     */
    public LabyrinthGameState copyState() {
        return new LabyrinthGameState(this.myState);
    }

    /**
     * Getter for the current node state
     * @return The state
     */
    public LabyrinthGameState getState() {
        return this.myState;
    }
}
