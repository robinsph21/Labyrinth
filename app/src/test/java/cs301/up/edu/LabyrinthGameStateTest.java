package cs301.up.edu;

import org.junit.Test;

import cs301.up.edu.labyrinth.LabyrinthGameState;
import cs301.up.edu.labyrinth.enums.Arrow;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class LabyrinthGameStateTest {

    private LabyrinthGameState firstInstance;
    private LabyrinthGameState secondInstance;
    private LabyrinthGameState thirdInstance;
    private LabyrinthGameState fourthInstance;

    public LabyrinthGameStateTest() {
        this.init();
    }

    private void init() {
        // First round of testing
        this.firstInstance = new LabyrinthGameState();
        this.secondInstance = new LabyrinthGameState(this.firstInstance,
                this.firstInstance.getPlayerTurn().ordinal());

        // Second round of testing
        this.thirdInstance = new LabyrinthGameState();
        this.fourthInstance = new LabyrinthGameState(this.thirdInstance,
                this.firstInstance.getPlayerTurn().ordinal());


        StringBuilder textToPrint = new StringBuilder();

        // Main Menu
        this.testMainMenu(this.firstInstance);
        textToPrint.append("Main Menu has no effect on gamestate");

        // Rotate Tile clockwise
        this.testRotate(this.firstInstance, true);
        textToPrint.append("Tile rotated clockwise once!\n");

        // Slide Tile in to top right
        this.testSlideTile(this.firstInstance,Arrow.TOP_LEFT);
        textToPrint.append("Tile shifted in from top left arrow!\n");

        // Move pawn to first available location
        textToPrint.append(this.testMovePawn(this.firstInstance) + "\n");

        // Check Reset
        this.testReset(this.firstInstance);
        textToPrint.append("Player reset the labyrinth");

        // Rotate Tile clockwise
        this.testRotate(this.firstInstance, true);
        textToPrint.append("Tile rotated clockwise once!\n");

        // Slide tile again
        this.testSlideTile(this.firstInstance,Arrow.TOP_LEFT);
        textToPrint.append("Tile shifted in from top left arrow!\n");

        // Move pawn to first available location
        textToPrint.append(this.testMovePawn(this.firstInstance) + "\n");

        // Check end turn
        this.testEndTurn(this.firstInstance);
        textToPrint.append("The player has ended their turn");
        


        // Check States are Equal
        String second = this.secondInstance.toString();
        String fourth = this.fourthInstance.toString();
    }

    @Test
    public void testEndTurn(LabyrinthGameState state) {
        assertTrue(state.checkEndTurn(state.getPlayerTurn().ordinal()));
    }

    @Test
    public void testMainMenu(LabyrinthGameState state) {
        assertTrue(state.checkMainMenu(state.getPlayerTurn().ordinal()));
    }

    @Test
    public void testReset(LabyrinthGameState state) {
        assertTrue(state.checkReset(state.getPlayerTurn().ordinal()));
    }

    @Test
    public void testRotate(LabyrinthGameState state, boolean clockwise) {
        assertTrue(state.checkRotate(state.getPlayerTurn().ordinal(),clockwise));
    }

    @Test
    public String testMovePawn(LabyrinthGameState state) {
        boolean check = false;
        int i = 0;
        int j = 0;
        for (i = 0; i < 7; i++) {
            for (j = 0; j < 7; j++) {
                if (this.firstInstance.checkMovePawn(
                        state.getPlayerTurn().ordinal(),
                        i,j)) {
                    check = true;
                    break;
                }
            }
            if (check) break;
        }
        if (check) {
            return "Played moved to " + i + j;
        } else {
            return "Player unable to move due to board conditions";
        }
    }

    @Test
    public void testSlideTile(LabyrinthGameState state, Arrow topLeft) {
        assertTrue(state.checkSlideTile(state.getPlayerTurn().ordinal(), topLeft));
    }

}