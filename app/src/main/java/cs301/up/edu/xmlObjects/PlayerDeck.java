package cs301.up.edu.xmlObjects;

import android.view.View;

public class PlayerDeck extends XMLObject {

    private int numCards;
    private String color; //TODO:Change to enum

    public PlayerDeck(View v, String color) {
        super(v);
        this.color = color;
        this.numCards = 6;
    }

    public int getNumCards() {
        return this.numCards;
    }

    public void decrementDeck() {
        this.numCards = this.numCards - 1;
    }

    public String getColor() {
        return this.color;
    }
}
