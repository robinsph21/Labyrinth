package cs301.up.edu.labyrinth.xmlObjects;

import android.view.View;

public class XMLObject implements View.OnClickListener{

    private View xmlObj;

    public XMLObject() {

    }

    public XMLObject(View v) {
        setXmlObj(v);
    }

    public View getXmlObj() {
        return this.xmlObj;
    }

    public void setXmlObj(View xmlObj) {
        this.xmlObj = xmlObj;
    }

    @Override
    public void onClick(View v) {
        //Do Nothing
    }
}
