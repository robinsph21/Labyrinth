package cs301.up.edu;

import cs301.up.edu.xmlObjects.CurrentTile;
import cs301.up.edu.xmlObjects.PlayerDeck;
import cs301.up.edu.xmlObjects.TreasureGoal;

public class Player {

    private PlayerDeck deck;
    private CurrentTile tile;
    private TreasureGoal goal;

    public Player(PlayerDeck deck, CurrentTile tile, TreasureGoal goal) {
        this.deck = deck;
        this.tile = tile;
        this.goal = goal;
    }

    public PlayerDeck getDeck() {
        return this.deck;
    }

    public CurrentTile getTile() {
        return this.tile;
    }

    public TreasureGoal getGoal() {
        return this.goal;
    }
}
