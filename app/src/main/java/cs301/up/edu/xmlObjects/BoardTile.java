package cs301.up.edu.xmlObjects;

import android.view.View;

public class BoardTile extends BoardSpot {

    private boolean fixed;
    private String treasure;

    public BoardTile(int locX, int locY, boolean fixed) {
        super(locX,locY);
        this.fixed = fixed;
    }

    public boolean isFixed() {
        return this.fixed;
    }

    public String getTreasure() {
        return this.treasure;
    }

    public void setTreasure(String treasure) {
        this.treasure = treasure;
    }

    @Override
    public void onClick(View v) {

    }
}
