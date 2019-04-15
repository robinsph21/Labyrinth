package cs301.up.edu.labyrinth.xmlObjects;

import android.view.View;
import cs301.up.edu.game.GameMainActivity;
import cs301.up.edu.game.GamePlayer;

/**
 * Defines the title bar at the top of the rules GUI as a button that will
 * return the user back to the game as a way out
 *
 * @author Erik Torkelson, Spencer Nelson, Spencer Rose, Philip Robinson
 * Date: 4/15/2019
 */

public class RulesReturn extends XMLObject implements View.OnClickListener {

    /* Instance Variables */
    private GameMainActivity activity;
    private GamePlayer player;

    /**
     * Ctor for the RulesHelp object to initialize all variables
     * @param v the imageview of the Rules/Hel button
     * @param activity the current main activity of the game
     * @param player the player who pressed it
     */

    public RulesReturn(View v, GameMainActivity activity, GamePlayer player) {
        //calling the super constructor of XMLObject
        super(v);
        //setting instance variables
        this.activity = activity;
        this.player = player;
        this.getXmlObj().setOnClickListener(this);
    }

    /**
     * required onClick method for the button
     * @param v the view that was clicked
     */
    @Override
    public void onClick(View v) {
        //sets the layout back to the original game
        this.player.setAsGui(activity);
    }
}
