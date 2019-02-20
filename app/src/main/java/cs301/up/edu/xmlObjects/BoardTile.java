package cs301.up.edu.xmlObjects;

import android.view.View;

import cs301.up.edu.enums.TreasureType;

public class BoardTile extends BoardSpot {

    private boolean fixed;
    private TreasureType treasure;

    public BoardTile(int locX, int locY, boolean fixed) {
        super(locX,locY);
        this.fixed = fixed;
    }

    public boolean isFixed() {
        return this.fixed;
    }

    public TreasureType getTreasure() {
        return this.treasure;
    }

    public void setTreasure(TreasureType treasure) {
        this.treasure = treasure;
    }

    @Override
    public void onClick(View v) {

    }
}
