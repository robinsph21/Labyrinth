package cs301.up.edu;

import org.junit.Test;

import cs301.up.edu.labyrinth.LabyrinthGameState;
import cs301.up.edu.labyrinth.enums.Arrow;

import static org.junit.Assert.*;

/**
 * Unit test for gamestate
 *
 * @author Erik Torkelson, Spencer Nelson, Spencer Rose, Philip Robinson
 * Date: 2/27/2019
 */
public class LabyrinthGameStateTest {

    private final String info;
    private LabyrinthGameState firstInstance;
    private LabyrinthGameState secondInstance;
    private LabyrinthGameState thirdInstance;
    private LabyrinthGameState fourthInstance;

    public LabyrinthGameStateTest() {
        this.info = this.init();
    }

    private String init() {
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

        // Check end turn
        this.testEndTurn(this.firstInstance);
        textToPrint.append("The player has ended their turn");

        // Check Reset
        this.testReset(this.firstInstance);
        textToPrint.append("Next player has reset the labyrinth");


        // Check States are Equal
        String second = this.secondInstance.toString();
        String fourth = this.fourthInstance.toString();

        textToPrint.append(second);
        textToPrint.append(fourth);

        return textToPrint.toString();
    }

    public void testEndTurn(LabyrinthGameState state) {
        assertTrue(state.checkEndTurn());
    }

    public void testMainMenu(LabyrinthGameState state) {
        assertTrue(state.checkMainMenu());
    }

    public void testReset(LabyrinthGameState state) {
        assertTrue(state.checkReset());
    }

    public void testRotate(LabyrinthGameState state, boolean clockwise) {
        assertTrue(state.checkRotate(clockwise));
    }

    public String testMovePawn(LabyrinthGameState state) {
        boolean check = false;
        int i = 0;
        int j = 0;
        for (i = 0; i < 7; i++) {
            for (j = 0; j < 7; j++) {
                if (this.firstInstance.checkMovePawn(i,j)) {
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

    public void testSlideTile(LabyrinthGameState state, Arrow topLeft) {
        assertTrue(state.checkSlideTile(topLeft));
    }


    @Test
    public void test() {
        LabyrinthGameStateTest test = new LabyrinthGameStateTest();
    }

    public String getInfo() {
        return info;
    }
}