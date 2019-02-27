package cs301.up.edu.labyrinth.xmlObjects;

import android.view.View;
import android.widget.ImageView;

import cs301.up.edu.labyrinth.enums.Player;

public class PlayerDeck extends XMLObject {

    private int numCards;
    private Player playerColor;

    public PlayerDeck(View v, Player color) {
        super(v);
        this.playerColor = color;
        this.numCards = 6;
    }

    public int getNumCards() {
        return this.numCards;
    }

    public void decrementDeck() {
        this.numCards = this.numCards - 1;
    }

    public Player getColor() {
        return this.playerColor;
    }

    @Override
    public ImageView getXmlObj() {
        return (ImageView)super.getXmlObj();
    }
}
