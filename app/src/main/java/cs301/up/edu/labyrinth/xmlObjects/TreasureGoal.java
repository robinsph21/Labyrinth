package cs301.up.edu.labyrinth.xmlObjects;

import android.view.View;

import cs301.up.edu.labyrinth.enums.TreasureType;

public class TreasureGoal extends XMLObject{

    private TreasureType treasure;

    public TreasureGoal(View v) {
        super(v);
    }

    public TreasureType getTreasure() {
        return this.treasure;
    }

    public void setTreasure(TreasureType treasure) {
        this.treasure = treasure;
    }
}
