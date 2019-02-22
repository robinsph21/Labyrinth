package cs301.up.edu.labyrinth;

import cs301.up.edu.enums.Player;
import cs301.up.edu.game.GamePlayer;
import cs301.up.edu.game.LocalGame;
import cs301.up.edu.game.actionMsg.GameAction;
import cs301.up.edu.labyrinth.actions.LabyrinthMoveAction;

import android.util.Log;

/**
 * A class that represents the state of a game. In our , the only
 * relevant piece of information is the value of the game's counter. The
 * CounterState object is therefore very simple.
 * 
 * @author Steven R. Vegdahl
 * @author Andrew M. Nuxoll
 * @version July 2013
 */
public class LabyrinthLocalGame extends LocalGame {

	// When a labyrinth game is played, any number of players. The first player
	// is trying to get the counter value to TARGET_MAGNITUDE; the second player,
	// if present, is trying to get the counter to -TARGET_MAGNITUDE. The
	// remaining players are neither winners nor losers, but can interfere by
	// modifying the counter.
	public static final int TARGET_MAGNITUDE = 10;

	// the game's state
	private LabyrinthGameState gameState;
	
	/**
	 * can this player move
	 * 
	 * @return
	 * 		true if playerID is the turn of the gamestate
	 */
	@Override
	protected boolean canMove(int playerIdx) {
		if (this.gameState.getPlayerTurn().ordinal() == playerIdx) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This ctor should be called when a new labyrinth game is started
	 */
	public LabyrinthLocalGame() {
		// initialize the game state
		this.gameState = new LabyrinthGameState();
	}

	/**
	 * The only type of GameAction that should be sent is LabyrinthMoveAction
	 */
	@Override
	protected boolean makeMove(GameAction action) {
		Log.i("action", action.getClass().toString());
		
		if (action instanceof LabyrinthMoveAction) {
		
			// cast so that we Java knows it's a LabyrinthMoveAction
			LabyrinthMoveAction cma = (LabyrinthMoveAction)action;
			
			// denote that this was a legal/successful move
			return true;
		}
		else {
			// denote that this was an illegal move
			return false;
		}
	}//makeMove
	
	/**
	 * send the updated state to a given player
	 */
	@Override
	protected void sendUpdatedStateTo(GamePlayer p) {
		// this is a perfect-information game, so we'll make a
		// complete copy of the state to send to the player
		p.sendInfo(new LabyrinthGameState(gameState, this.getPlayerIdx(p)));
		
	}//sendUpdatedSate
	
	/**
	 * Check if the game is over. It is over, return a string that tells
	 * who the winner(s), if any, are. If the game is not over, return null;
	 * 
	 * @return
	 * 		a message that tells who has won the game, or null if the
	 * 		game is not over
	 */
	@Override
	protected String checkIfGameOver() {
		// TODO: Add also back to starting point
		if (this.gameState.getPlayerDeckSize(Player.RED) == 0) {
			return "Red Player has won!";
		} else if (this.gameState.getPlayerDeckSize(Player.YELLOW) == 0) {
			return "Yellow Player has won!";
		} else if (this.gameState.getPlayerDeckSize(Player.BLUE) == 0) {
			return "Blue Player has won!";
		} else if (this.gameState.getPlayerDeckSize(Player.GREEN) == 0) {
			return "Green Player has won!";
		} else {
			return null;
		}
	}

}// class LabyrinthLocalGame
