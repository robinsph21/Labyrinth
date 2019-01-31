package cs301.up.edu.xmlObjects;

import android.view.View;

public class XMLObject implements View.OnClickListener{

    private View xmlObj;

    public XMLObject() {

    }

    public View getXmlObj() {
        return xmlObj;
    }

    public void setXmlObj(View xmlObj) {
        this.xmlObj = xmlObj;
    }

    @Override
    public void onClick(View v) {

    }
}
