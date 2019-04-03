package cs301.up.edu.labyrinth.xmlObjects;

import android.view.View;
import android.widget.ImageView;

import cs301.up.edu.labyrinth.enums.Player;

/**
 * GUI representation of the treasure decks of each player
 *
 * @author Erik Torkelson, Spencer Nelson, Spencer Rose, Philip Robinson
 * Date: 2/27/2019
 */

public class PlayerDeck extends XMLObject {

    /* Instance Variables*/
    //the color of the deck based on the player
    private Player playerColor;

    /**
     * Ctor for the PlayerDeck
     * @param v the image view that will show the deck
     * @param color the color the deck will be
     */
    public PlayerDeck(View v, Player color) {
        super(v);
        this.playerColor = color;
    }

    /**
     * Accessor for the color of a player deck
     * @return the color
     */
    public Player getColor() {
        return this.playerColor;
    }

    /**
     * Accessor for the playerdeck itself
     * @return
     */
    @Override
    public ImageView getXmlObj() {
        return (ImageView)super.getXmlObj();
    }
}
