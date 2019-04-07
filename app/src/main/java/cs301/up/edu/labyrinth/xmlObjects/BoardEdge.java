package cs301.up.edu.labyrinth.xmlObjects;

import android.view.View;

import cs301.up.edu.labyrinth.enums.Arrow;
import cs301.up.edu.game.Game;
import cs301.up.edu.game.GamePlayer;
import cs301.up.edu.game.actionMsg.GameAction;
import cs301.up.edu.labyrinth.actions.LabyrinthSlideTileAction;
/**
 * Defines a BoardEdge, mainly used to specify what arrow a user
 * is touching to slide in a tile
 *
 * @author Erik Torkelson, Spencer Nelson, Spencer Rose, Philip Robinson
 * Date: 2/27/2019
 */
public class BoardEdge extends BoardSpot {

    /* Instance Variables */
    private boolean clickable;
    private GamePlayer player;
    private Arrow thisArrow;

    /**
     * Ctor for a board edge
     * @param v the specific image view the object interacts with
     * @param locX x-coordinate of the edge
     * @param locY y-coordinate of the edge
     * @param clickable a boolean if the edge can be clicked
     * @param player the player seeing the image
     * @param game the current game
     */
    public BoardEdge(View v, int locX, int locY, boolean clickable,
                     GamePlayer player, Game game) {
        super(v, locX,locY);
        this.clickable = clickable;
        this.player = player;
        this.game = game;
        //setting the appropriate arrow type based on the
        //x and y location of the edge
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

    /**
     * Mutator for setting if the edge can be clicked
     * @param clickable
     */
    public void setClickable (boolean clickable) {
        this.clickable = clickable;
    }

    /**
     * Accessor or the type of arrow the edge is
     * @return
     */
    public Arrow getThisArrow() {
        return thisArrow;
    }

    /**
     * Required onClick method
     * @param v
     */
    @Override
    public void onClick(View v) {
        // if we are not yet connected to a game, ignore
        if (this.game == null) return;
        if (this.clickable) {
            // Construct the action and send it to the game
            GameAction action = null;

            //make the action a slideTile action
            action = new LabyrinthSlideTileAction(this.player, this.thisArrow);

            game.sendAction(action);
        }
    }

}
