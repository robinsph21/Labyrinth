package cs301.up.edu.xmlObjects;

import android.view.View;

public class TreasureGoal extends XMLObject{

    private String treasure; //TODO: Change to enum

    public TreasureGoal(View v) {
        super(v);
    }

    public String getTreasure() {
        return this.treasure;
    }

    public void setTreasure(String treasure) {
        this.treasure = treasure;
    }
}
