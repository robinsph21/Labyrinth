package cs301.up.edu;

import org.junit.Test;

import cs301.up.edu.game.GamePlayer;
import cs301.up.edu.labyrinth.LabyrinthComputerPlayer1;

import static org.junit.Assert.*;


/**
 * Unit test for computer player 1
 *
 * @author Erik Torkelson, Spencer Nelson, Spencer Rose, Philip Robinson
 * Date: 3/25/2019
 */
public class LabyrinthComputerPlayer1Test {

    @Test
    public void testPlayer() {
        GamePlayer player1 = new LabyrinthComputerPlayer1("TestPlayer");
        assertNotNull(player1);
        assertFalse(player1.requiresGui());
        assertFalse(player1.supportsGui());
    }

}