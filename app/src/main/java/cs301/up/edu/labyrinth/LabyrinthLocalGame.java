package cs301.up.edu.labyrinth;

import cs301.up.edu.labyrinth.enums.Player;
import cs301.up.edu.labyrinth.enums.TileType;
import cs301.up.edu.game.GamePlayer;
import cs301.up.edu.game.LocalGame;
import cs301.up.edu.game.actionMsg.GameAction;
import cs301.up.edu.labyrinth.actions.LabyrinthEndTurnAction;
import cs301.up.edu.labyrinth.actions.LabyrinthMovePawnAction;
import cs301.up.edu.labyrinth.actions.LabyrinthResetAction;
import cs301.up.edu.labyrinth.actions.LabyrinthRotateAction;
import cs301.up.edu.labyrinth.actions.LabyrinthSlideTileAction;

/**
 * A class that represents the state of a game. In our , the only
 * relevant piece of information is the value of the game's counter. The
 * CounterState object is therefore very simple.
 * 
 * @author Steven R. Vegdahl
 * @author Andrew M. Nuxoll
 * @author Erik Torkelson, Spencer Nelson, Spencer Rose, Philip Robinson
 * @version July 2013
 */
public class LabyrinthLocalGame extends LocalGame {

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

		LabyrinthGameState copy = new LabyrinthGameState(this.gameState);
		this.gameState.setPrevState(copy);
	}

	/**
	 * The only type of GameAction that should be sent is LabyrinthMovePawnAction
	 */
	@Override
	protected boolean makeMove(GameAction action) {

		if (action instanceof LabyrinthRotateAction) {
			LabyrinthRotateAction act = (LabyrinthRotateAction) action;
			return this.gameState.checkRotate(act.isClockwise());

		} else if (action instanceof LabyrinthEndTurnAction) {
			LabyrinthEndTurnAction act = (LabyrinthEndTurnAction) action;
			return this.gameState.checkEndTurn();

		} else if (action instanceof LabyrinthResetAction) {
			LabyrinthResetAction act = (LabyrinthResetAction) action;
			return this.gameState.checkReset();

		} else if (action instanceof LabyrinthSlideTileAction) {
			LabyrinthSlideTileAction act = (LabyrinthSlideTileAction) action;
			return this.gameState.checkSlideTile(act.getThisArrow());

		} else if (action instanceof LabyrinthMovePawnAction) {
			LabyrinthMovePawnAction act = (LabyrinthMovePawnAction) action;
			return this.gameState.checkMovePawn(act.getLocX(), act.getLocY());

		} else {
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
		if (this.gameState.getPlayerDeckSize(Player.RED) == 0) {
			if (this.gameState.getPlayerLoc(Player.RED).getType() ==
					TileType.RED_ENTRY) {
				return "Red Player has won!";
			}
		}
		if (this.gameState.getPlayerDeckSize(Player.YELLOW) == 0) {
			if (this.gameState.getPlayerLoc(Player.YELLOW).getType() ==
					TileType.YELLOW_ENTRY) {
				return "Yellow Player has won!";
			}
		}
		if (this.gameState.getPlayerDeckSize(Player.BLUE) == 0) {
			if (this.gameState.getPlayerLoc(Player.BLUE).getType() ==
					TileType.BLUE_ENTRY) {
				return "Blue Player has won!";
			}
		}
		if (this.gameState.getPlayerDeckSize(Player.GREEN) == 0) {
			if (this.gameState.getPlayerLoc(Player.GREEN).getType() ==
					TileType.GREEN_ENTRY) {
				return "Green Player has won!";
			}
		}
		return null;
	}

}// class LabyrinthLocalGame
