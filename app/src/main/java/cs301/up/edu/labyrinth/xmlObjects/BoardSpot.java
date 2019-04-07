package cs301.up.edu.labyrinth.xmlObjects;

import android.view.View;
import android.widget.ImageView;

import cs301.up.edu.game.Game;

/**
 * Defines a BoardSpot, which is a specific spot anywhere on the GUI,
 * not just a tile
 *
 * @author Erik Torkelson, Spencer Nelson, Spencer Rose, Philip Robinson
 * Date: 2/27/2019
 */
public class BoardSpot extends XMLObject{

    /* Instance Variables */
    private int locX;
    private int locY;
    protected Game game;

    /**
     * Ctor for a BoardSpot
     * @param v the image view
     * @param locX the x-coordinate of the spot
     * @param locY the y-coordinate of the spot
     */
    public BoardSpot(View v, int locX, int locY) {
        super(v);
        this.locX = locX;
        this.locY = locY;
    }

    /**
     * Accessor for the x-coordinate
     * @return
     */
    public int getLocX() {
        return this.locX;
    }

    /**
     * Accessor for the y-coordinate
     * @return
     */
    public int getLocY() {
        return this.locY;
    }

    /**
     * Accessor for the object itself
     * @return
     */
    @Override
    public ImageView getXmlObj() {
        return (ImageView)super.getXmlObj();
    }

    /**
     * Mutator for the board spots current game
     * @param game
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Accessor for the current game of the spot
     * @return
     */
    public Game getGame() {
        return this.game;
    }
}
