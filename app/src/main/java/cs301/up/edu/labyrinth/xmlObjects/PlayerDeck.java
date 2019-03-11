package cs301.up.edu.labyrinth.xmlObjects;

import android.view.View;
import android.widget.ImageView;

import cs301.up.edu.labyrinth.enums.Player;

public class PlayerDeck extends XMLObject {

    private Player playerColor;

    public PlayerDeck(View v, Player color) {
        super(v);
        this.playerColor = color;
    }

    public Player getColor() {
        return this.playerColor;
    }

    @Override
    public ImageView getXmlObj() {
        return (ImageView)super.getXmlObj();
    }
}
