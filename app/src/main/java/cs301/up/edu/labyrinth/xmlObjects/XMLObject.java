package cs301.up.edu.labyrinth.xmlObjects;

import android.view.View;

/**
 * The class that other XML Objects will inherit from. Defines methods that will
 * be useful for the other classes
 *
 * @author Erik Torkelson, Spencer Nelson, Spencer Rose, Philip Robinson
 * Date: 2/27/2019
 */
public class XMLObject implements View.OnClickListener{

    /*Instance variables*/
    private View xmlObj; //This view object

    /**
     * Basic Ctor for XML object. All child classes will have their own Ctor so
     * this is never used.
     */
    public XMLObject() {
    //not used
    }

    /**
     * Ctor that will set the XML object when it is instantiated
     * @param v
     */
    public XMLObject(View v) {
        setXmlObj(v);
    }

    /**
     * Getter for XML Objects
     * @return The requested XML Object
     */
    public View getXmlObj() {
        return this.xmlObj;
    }

    /**
     * Setter for XML Objects
     * @param xmlObj The XML you wish to set
     */
    public void setXmlObj(View xmlObj) {
        this.xmlObj = xmlObj;
    }

    @Override
    /**
     * Required onClick for on click listeners
     */
    public void onClick(View v) {
        //Do Nothing
    }
}
