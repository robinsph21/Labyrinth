package cs301.up.edu.labyrinth.xmlObjects;

import android.view.View;
import android.widget.ImageView;
/**
 * GUI representation of the current treasure that a player is
 * going for
 *
 * @author Erik Torkelson, Spencer Nelson, Spencer Rose, Philip Robinson
 * Date: 2/27/2019
 */
public class TreasureGoal extends XMLObject{

    /**
     * Ctor for TreasureGoal
     * @param v the view of the treasure
     */
    public TreasureGoal(View v) {
        super(v);
    }

    @Override
    /**
     * Use the super's getter for this object
     */
    public ImageView getXmlObj() {
        return (ImageView)super.getXmlObj();
    }
}
