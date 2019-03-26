package cs301.up.edu;

import org.junit.Test;

import cs301.up.edu.labyrinth.LabyrinthComputerPlayer1;


/**
 * Unit test for computer player 1
 *
 * @author Erik Torkelson, Spencer Nelson, Spencer Rose, Philip Robinson
 * Date: 3/25/2019
 */
public class LabyrinthComputerPlayer1Test {

    @Test
    public void testPlayer() {
        LabyrinthComputerPlayer1 testPlayer = new
                LabyrinthComputerPlayer1("P0");
        testPlayer.test();
    }

}