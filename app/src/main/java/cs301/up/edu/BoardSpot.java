package cs301.up.edu;

import android.widget.ImageView;

class BoardSpot {

    private int locX;
    private int locY;
    private ImageView xmlImage;
    private int rotation;

    protected BoardSpot() {
        this.locX = 0;
        this.locY = 0;
    }

    protected BoardSpot(int locX, int locY) {
        this.locX = locX;
        this.locY = locY;
    }

    protected int getLocX() {
        return locX;
    }

    protected int getLocY() {
        return locY;
    }

    protected ImageView getXmlImage() {
        return xmlImage;
    }

    protected void setXmlImage(ImageView xmlImage) {
        this.xmlImage = xmlImage;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }
}
