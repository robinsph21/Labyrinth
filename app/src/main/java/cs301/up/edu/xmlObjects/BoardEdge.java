package cs301.up.edu.xmlObjects;

import android.view.View;

public class BoardEdge extends BoardSpot {

    private boolean clickable;

    public BoardEdge(int locX, int locY, boolean clickable) {
        super(locX,locY);
        this.clickable = clickable;
    }

    public boolean isClickable() {
        return this.clickable;
    }

    @Override
    public void onClick(View v) {

    }
}
