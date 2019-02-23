package cs301.up.edu.xmlObjects;

import android.view.View;

import cs301.up.edu.game.Game;
import cs301.up.edu.game.GamePlayer;
import cs301.up.edu.game.actionMsg.GameAction;
import cs301.up.edu.labyrinth.actions.LabyrinthSlideTileAction;

public class BoardEdge extends BoardSpot {

    private boolean clickable;
    private GamePlayer player;
    private Game game;

    public BoardEdge(int locX, int locY, boolean clickable,
                     GamePlayer player, Game game) {
        super(locX,locY);
        this.clickable = clickable;
        this.player = player;
        this.game = game;
    }

    public boolean isClickable() {
        return this.clickable;
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    @Override
    public void onClick(View v) {
        // if we are not yet connected to a game, ignore
        if (this.game == null) return;

        // Construct the action and send it to the game
        GameAction action = null;

        action = new LabyrinthSlideTileAction(this.player);

        game.sendAction(action);
    }
}
