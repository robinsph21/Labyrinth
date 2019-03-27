package cs301.up.edu.labyrinth;

public class AINode {

    private LabyrinthGameState myState;

    public AINode(LabyrinthGameState myState) {
        this.myState = myState;
    }

    public LabyrinthGameState copyState() {
        return new LabyrinthGameState(this.myState);
    }

    public LabyrinthGameState getState() {
        return this.myState;
    }
}
