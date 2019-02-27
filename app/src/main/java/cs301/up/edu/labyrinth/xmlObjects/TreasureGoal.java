package cs301.up.edu.labyrinth.xmlObjects;

import android.view.View;
import android.widget.ImageView;

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

    @Override
    public ImageView getXmlObj() {
        return (ImageView)super.getXmlObj();
    }
}
