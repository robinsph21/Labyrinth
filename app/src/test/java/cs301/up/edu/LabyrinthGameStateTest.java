package cs301.up.edu;

import org.junit.Test;

import cs301.up.edu.labyrinth.LabyrinthGameState;
import cs301.up.edu.labyrinth.enums.Arrow;

import static org.junit.Assert.*;

//TODO: Comment this file

/**
 * Unit test for gamestate
 *
 * @author Erik Torkelson, Spencer Nelson, Spencer Rose, Philip Robinson
 * Date: 2/27/2019
 */
public class LabyrinthGameStateTest {

    // String containing all test information. Will be printed to text View
    private final String info;

    // Required game state instances
    private LabyrinthGameState firstInstance;
    private LabyrinthGameState secondInstance;
    private LabyrinthGameState thirdInstance;
    private LabyrinthGameState fourthInstance;

    /**
     * Ctor for unit test. Initializes a string that contains all information
     * on the tests that are called.
     */
    public LabyrinthGameStateTest() {
        this.info = this.init();
    }

    /**
     * Initialize method that calls a test method on all of the actions the user
     * can take.
     *
     * @return The string containing the information on all of the tests that
     *          were called. This string is created using a string builder.
     */
    private String init() {
        // First round of testing
        this.firstInstance = new LabyrinthGameState();
        this.secondInstance = new LabyrinthGameState(this.firstInstance,
                this.firstInstance.getPlayerTurn().ordinal());

        // Second round of testing
        this.thirdInstance = new LabyrinthGameState();
        this.fourthInstance = new LabyrinthGameState(this.thirdInstance,
                this.firstInstance.getPlayerTurn().ordinal());

        /**
         External Citation
         Date: 27 February 2019
         Problem: Didn't know how to efficiently build up a long string.
         Resource:
            https://docs.oracle.com/javase/tutorial/java/data/buffers.html
         Solution: I used the example code from this post as a reference to
            create a string builder.
         */
        StringBuilder textToPrint = new StringBuilder();

        // Run Main Menu test
        this.testMainMenu(this.firstInstance);
        textToPrint.append("Main Menu has no effect on gamestate");

        // Run Rotate Tile test. The tile till be rotated clockwise.
        this.testRotate(this.firstInstance, true);
        textToPrint.append("Tile rotated clockwise once!\n");

        // Run Slide Tile test. Tile will be inserted in top left position.
        this.testSlideTile(this.firstInstance,Arrow.TOP_LEFT);
        textToPrint.append("Tile shifted in from top left arrow!\n");

        // Run Move pawn Test. The pawn will be moved to the first available
        // location
        textToPrint.append(this.testMovePawn(this.firstInstance) + "\n");

        // Run End Turn test
        this.testEndTurn(this.firstInstance);
        textToPrint.append("The player has ended their turn");

        // Run Reset board test
        this.testReset(this.firstInstance);
        textToPrint.append("Next player has reset the labyrinth");


        // Check States are Equal
        String second = this.secondInstance.toString();
        String fourth = this.fourthInstance.toString();

        // Append the information about second and fourth instances
        textToPrint.append(second);
        textToPrint.append(fourth);

        return textToPrint.toString();
    }

    /**
     * Test to see if the current player is able to end their turn. This should
     * always return true based on the passed in game state.
     *
     * @param state LabyrinthGameState containing the information to test.
     */
    public void testEndTurn(LabyrinthGameState state) {
        assertTrue(state.checkEndTurn());
    }

    /**
     * Test to see if the player is able to access the main menu. This should
     * always return true based on the passed in state.
     *
     * @param state LabyrinthGameState containing the information to test.
     */
    public void testMainMenu(LabyrinthGameState state) {
        assertTrue(state.checkMainMenu());
    }

    /**
     * Test to see if the player is able to reset the board. This should always
     * return true based on the passed in state.
     *
     * @param state LabyrinthGameState containing the information to test.
     */
    public void testReset(LabyrinthGameState state) {
        assertTrue(state.checkReset());
    }

    /**
     * Test to see if the player is able to rotate the current tile. This should
     * always return true based on the passed in state.
     *
     * @param state LabyrinthGameState containing the information to test.
     * @param clockwise True if we wish to rotate the current tile clockwise
     *                  False if we wish to rotate the current tile counter
     *                  clockwise
     */
    public void testRotate(LabyrinthGameState state, boolean clockwise) {
        assertTrue(state.checkRotate(clockwise));
    }

    /**
     * Test to see if the player is able to move their pawn.
     *
     * @param state LabyrinthGameState containing the information to test.
     * @return String containing where the player moved.
     */
    public String testMovePawn(LabyrinthGameState state) {

        // Will be set to true of the player was able to move. Will be false
        // if the player was unable to move.
        boolean check = false;
        int i = 0;
        int j = 0;
        // Loop through the board
        for (i = 0; i < 7; i++) {
            for (j = 0; j < 7; j++) {
                // If the player is able to move to this x, y spot, move their
                // and stop looping
                if (this.firstInstance.checkMovePawn(i,j)) {
                    check = true;
                    break;
                }
            }
            //keep looping until we have checked every spot or we we have found
            // a spot that the player is able to move to.
            if (check) break;
        }

        // create a string based on whether the player was able to move or not.
        if (check) {
            return "Played moved to " + i + j;
        } else {
            return "Player unable to move due to board conditions";
        }
    }

    /**
     * Test to see if the player is able to insert the current tile at an arrow.
     * This should always return true based on the passed in state.
     *
     * @param state LabyrinthGameState containing the information to test.
     * @param topLeft Arrow on the board that will be tested
     */
    public void testSlideTile(LabyrinthGameState state, Arrow topLeft) {
        assertTrue(state.checkSlideTile(topLeft));
    }


    /**
     * Method to run all the tests.
     */
    @Test
    public void test() {
        LabyrinthGameStateTest test = new LabyrinthGameStateTest();
    }

    /**
     * Getter for the string containing all test information.
     *
     * @return String that will be sent to text view.
     */
    public String getInfo() {
        return info;
    }
}