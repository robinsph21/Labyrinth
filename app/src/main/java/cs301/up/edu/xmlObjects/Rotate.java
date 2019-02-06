package cs301.up.edu.xmlObjects;

import android.view.View;

public class Rotate extends XMLObject {

    private boolean clockwise;
    
    public Rotate(View v, boolean clockwise) {
        super(v);
        this.clockwise = clockwise;
    }

    public boolean isClockwise() {
        return this.clockwise;
    }
}
