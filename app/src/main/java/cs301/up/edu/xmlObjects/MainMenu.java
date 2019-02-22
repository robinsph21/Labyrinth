package cs301.up.edu.xmlObjects;

import android.view.View;

import cs301.up.edu.game.Game;
import cs301.up.edu.game.GamePlayer;
import cs301.up.edu.game.actionMsg.GameAction;
import cs301.up.edu.labyrinth.actions.LabyrinthMoveAction;

public class MainMenu extends XMLObject {

    private GamePlayer player;
    private Game game;

    public MainMenu(View v, GamePlayer player, Game game) {
        super(v);
        this.game = game;
        this.player = player;
        this.getXmlObj().setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // if we are not yet connected to a game, ignore
        if (this.game == null) return;

        // Construct the action and send it to the game
        GameAction action = null;

        action = new LabyrinthMoveAction(this.player);

        game.sendAction(action);
    }

}
