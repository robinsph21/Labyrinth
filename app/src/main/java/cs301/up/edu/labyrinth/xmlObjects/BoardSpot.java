package cs301.up.edu.labyrinth.xmlObjects;

import android.view.View;
import android.widget.ImageView;

import cs301.up.edu.game.Game;

public class BoardSpot extends XMLObject{

    private int locX;
    private int locY;
    protected Game game;

    public BoardSpot(View v, int locX, int locY) {
        super(v);
        this.locX = locX;
        this.locY = locY;
    }

    public int getLocX() {
        return this.locX;
    }

    public int getLocY() {
        return this.locY;
    }

    @Override
    public ImageView getXmlObj() {
        return (ImageView)super.getXmlObj();
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return this.game;
    }
}
