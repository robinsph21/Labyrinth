package cs301.up.edu.labyrinth.xmlObjects;

import android.view.View;

import cs301.up.edu.game.Game;
import cs301.up.edu.game.GamePlayer;
import cs301.up.edu.game.actionMsg.GameAction;
import cs301.up.edu.labyrinth.actions.LabyrinthRotateAction;

public class Rotate extends XMLObject {

    private final GamePlayer player;
    private Game game;
    private boolean clockwise;
    
    public Rotate(View v, boolean clockwise, GamePlayer player, Game game) {
        super(v);
        this.clockwise = clockwise;
        this.player = player;
        this.game = game;
        this.getXmlObj().setOnClickListener(this);
    }

    public void onClick(View v) {
        // if we are not yet connected to a game, ignore
        if (this.game == null) return;

        // Construct the action and send it to the game
        GameAction action = null;

        action = new LabyrinthRotateAction(this.player, this.clockwise);

        game.sendAction(action);
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return this.game;
    }
}
