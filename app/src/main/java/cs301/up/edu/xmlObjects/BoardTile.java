package cs301.up.edu.xmlObjects;

import android.view.View;

import cs301.up.edu.enums.Player;
import cs301.up.edu.enums.TreasureType;
import cs301.up.edu.game.Game;
import cs301.up.edu.game.GamePlayer;
import cs301.up.edu.game.actionMsg.GameAction;
import cs301.up.edu.labyrinth.actions.LabyrinthMovePawnAction;

public class BoardTile extends BoardSpot {

    private boolean fixed;
    private TreasureType treasure;
    private GamePlayer player;
    private Game game;
    private Player pawn;

    public BoardTile(int locX, int locY, boolean fixed,
                     GamePlayer player, Game game) {
        super(locX,locY);
        this.fixed = fixed;
        this.player = player;
        this.game = game;
    }

    public boolean isFixed() {
        return this.fixed;
    }

    public TreasureType getTreasure() {
        return this.treasure;
    }

    public void setTreasure(TreasureType treasure) {
        this.treasure = treasure;
    }

    public Player getPawn() {
        return this.pawn;
    }

    public void setPawn(Player pawn) {
        this.pawn = pawn;
    }

    @Override
    public void onClick(View v) {
        // if we are not yet connected to a game, ignore
        if (this.game == null) return;

        // Construct the action and send it to the game
        GameAction action = null;

        action = new LabyrinthMovePawnAction(this.player,
                this.getLocX(), this.getLocY());

        game.sendAction(action);
    }
}
