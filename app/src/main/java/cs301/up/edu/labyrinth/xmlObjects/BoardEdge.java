package cs301.up.edu.labyrinth.xmlObjects;

import android.view.View;

import cs301.up.edu.labyrinth.enums.Arrow;
import cs301.up.edu.game.Game;
import cs301.up.edu.game.GamePlayer;
import cs301.up.edu.game.actionMsg.GameAction;
import cs301.up.edu.labyrinth.actions.LabyrinthSlideTileAction;

public class BoardEdge extends BoardSpot {

    private boolean clickable;
    private GamePlayer player;
    private Arrow thisArrow;

    public BoardEdge(View v, int locX, int locY, boolean clickable,
                     GamePlayer player, Game game) {
        super(v, locX,locY);
        this.clickable = clickable;
        this.player = player;
        this.game = game;
        switch ("" + locX + locY) {
            case "20": this.thisArrow = Arrow.TOP_LEFT; break;
            case "40": this.thisArrow = Arrow.TOP_MIDDLE; break;
            case "60": this.thisArrow = Arrow.TOP_RIGHT; break;

            case "02": this.thisArrow = Arrow.LEFT_TOP; break;
            case "04": this.thisArrow = Arrow.LEFT_MIDDLE; break;
            case "06": this.thisArrow = Arrow.LEFT_BOTTOM; break;

            case "82": this.thisArrow = Arrow.RIGHT_TOP; break;
            case "84": this.thisArrow = Arrow.RIGHT_MIDDLE; break;
            case "86": this.thisArrow = Arrow.RIGHT_BOTTOM; break;

            case "28": this.thisArrow = Arrow.BOTTOM_LEFT; break;
            case "48": this.thisArrow = Arrow.BOTTOM_MIDDLE; break;
            case "68": this.thisArrow = Arrow.BOTTOM_RIGHT; break;
        }
        this.getXmlObj().setOnClickListener(this);
    }

    public void setClickable (boolean clickable) {
        this.clickable = clickable;
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
