package cs301.up.edu.xmlObjects;


import android.view.View;

public class BoardSpot extends XMLObject{

    private int locX;
    private int locY;
    private int rotation;

    public BoardSpot(int locX, int locY) {
        this.locX = locX;
        this.locY = locY;
    }

    public int getLocX() {
        return locX;
    }

    public int getLocY() {
        return locY;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

}
