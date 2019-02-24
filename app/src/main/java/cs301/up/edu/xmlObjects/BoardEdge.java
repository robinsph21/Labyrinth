package cs301.up.edu.xmlObjects;

import android.view.View;

import cs301.up.edu.enums.Arrow;
import cs301.up.edu.game.Game;
import cs301.up.edu.game.GamePlayer;
import cs301.up.edu.game.actionMsg.GameAction;
import cs301.up.edu.labyrinth.actions.LabyrinthSlideTileAction;

public class BoardEdge extends BoardSpot {

    private boolean clickable;
    private GamePlayer player;
    private Game game;
    private Arrow thisArrow;

    public BoardEdge(int locX, int locY, boolean clickable,
                     GamePlayer player, Game game) {
        super(locX,locY);
        this.clickable = clickable;
        this.player = player;
        this.game = game;
        switch ("" + locX + locY) {
            case "20": this.thisArrow = Arrow.TOP_LEFT;
            case "40": this.thisArrow = Arrow.TOP_MIDDLE;
            case "60": this.thisArrow = Arrow.TOP_RIGHT;

            case "02": this.thisArrow = Arrow.LEFT_TOP;
            case "04": this.thisArrow = Arrow.LEFT_MIDDLE;
            case "06": this.thisArrow = Arrow.LEFT_BOTTOM;

            case "82": this.thisArrow = Arrow.RIGHT_TOP;
            case "84": this.thisArrow = Arrow.RIGHT_MIDDLE;
            case "86": this.thisArrow = Arrow.RIGHT_BOTTOM;

            case "28": this.thisArrow = Arrow.BOTTOM_LEFT;
            case "48": this.thisArrow = Arrow.BOTTOM_MIDDLE;
            case "68": this.thisArrow = Arrow.BOTTOM_RIGHT;
        }
    }

    @Override
    public void onClick(View v) {
        // if we are not yet connected to a game, ignore
        if (this.game == null) return;
        if (this.clickable) {
            // Construct the action and send it to the game
            GameAction action = null;

            action = new LabyrinthSlideTileAction(this.player, this.thisArrow);

            game.sendAction(action);
        }
    }
}
