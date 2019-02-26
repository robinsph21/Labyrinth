package cs301.up.edu.labyrinth.xmlObjects;

import android.view.View;

public class CurrentTile extends XMLObject {

    private BoardTile tile;

    public CurrentTile(View v) {
        super(v);
    }

    public BoardTile getTile() {
        return tile;
    }

    public void setTile(BoardTile tile) {
        this.tile = tile;
    }
}
