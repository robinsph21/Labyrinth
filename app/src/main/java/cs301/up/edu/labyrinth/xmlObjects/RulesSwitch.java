package cs301.up.edu.labyrinth.xmlObjects;

import android.view.View;
import android.widget.ImageView;
import cs301.up.edu.R;
import cs301.up.edu.game.GameMainActivity;
import cs301.up.edu.game.GamePlayer;
import cs301.up.edu.labyrinth.enums.RulesButtons;

/**
 * Defines the behavior of the arrow buttons in the Rules/Help layout.
 * this handles both the left and right arrows
 *
 * @author Erik Torkelson, Spencer Nelson, Spencer Rose, Philip Robinson
 * Date: 4/1/2019
 */
public class RulesSwitch extends XMLObject {

    /* Instance Variables */


    private RulesButtons rulesButton;
    private ImageView display;
    private GameMainActivity activity;
    private GamePlayer player;

    //Counter used to determine which slide the user is on
    private static int counter = 0;
    //the maximum number of rule image slides
    private final static int MAX_SLIDES = 5;

    /**
     * Ctor for the RulesSwitch
     * @param v the view that the RuleSwitch is listening for
     * @param rulesButton a boolean to determine if it the left or right arrow (left = false)
     * @param display the imageview that will be replaced to see each new rule
     * @param activity the current players game activity
     * @param player the id of the player who is trying to look at the rules
     */
    public RulesSwitch(View v, RulesButtons rulesButton, ImageView display,
                       GameMainActivity activity, GamePlayer player) {
        super(v);
        this.rulesButton = rulesButton;
        this.display = display;
        this.activity = activity;
        this.player = player;
        this.getXmlObj().setOnClickListener(this);
    }

    /**
     * The required onClick method that is run whenever a player
     * clicks one of the arrows in the help menu
     * @param v the view that is being clicked (the arrows)
     */
    @Override
    public void onClick(View v) {
        //if the arrow pressed is the right one, and they are not at the
        //last rule slide, increment counter
        if (this.rulesButton.ordinal() == 0) {
            if (counter < MAX_SLIDES - 1) {
                counter++;
            } else {
                //if the user presses the right arrow on the last slide
                //they are returned to the regular game activity
                counter = 0;
                this.player.setAsGui(activity);
            }
            //if the left arrow was pressed and counter is not 0, decrement it
        } else if (this.rulesButton.ordinal() == 1) {
            if (counter > 0) {
                counter--;
            } else {
                //if the user presses the left arrow on the first slide
                //they are returned to the regular game activity
                counter = 0;
                this.player.setAsGui(activity);
            }
        } else {
            counter = 0;
            this.player.setAsGui(activity);
        }
        //Depending on the value of counter, the middle imageview of the
        //rule layout is changed to reflect the user clicking an arrow
        //to see the next or previous rule
        switch (counter) {
            case 0:
                this.display.setImageResource(R.drawable.rules_slide_0);
                break;
            case 1:
                this.display.setImageResource(R.drawable.rules_slide_1);
                break;
            case 2:
                this.display.setImageResource(R.drawable.rules_slide_2);
                break;
            case 3:
                this.display.setImageResource(R.drawable.rules_slide_3);
                break;
            case 4:
                this.display.setImageResource(R.drawable.rules_slide_4);
                break;
        }
    }
}
