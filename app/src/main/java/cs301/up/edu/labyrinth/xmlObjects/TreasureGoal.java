package cs301.up.edu.labyrinth.xmlObjects;

import android.view.View;
import android.widget.ImageView;


public class TreasureGoal extends XMLObject{

    public TreasureGoal(View v) {
        super(v);
    }

    @Override
    public ImageView getXmlObj() {
        return (ImageView)super.getXmlObj();
    }
}
