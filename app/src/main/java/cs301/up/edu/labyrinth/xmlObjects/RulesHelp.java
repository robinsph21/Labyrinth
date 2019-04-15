package cs301.up.edu.labyrinth.xmlObjects;

import android.view.View;
import android.widget.ImageView;

import cs301.up.edu.game.Game;
import cs301.up.edu.game.GameMainActivity;
import cs301.up.edu.game.GamePlayer;
import cs301.up.edu.R;
import cs301.up.edu.labyrinth.enums.RulesButtons;

/**
 * Defines the Rules/Help button at the top of the GUI that will
 * take the player to a page with all of the rules of the game
 *
 * @author Erik Torkelson, Spencer Nelson, Spencer Rose, Philip Robinson
 * Date: 4/1/2019
 */
public class RulesHelp extends XMLObject {

    /* Instance Variables */
    private final GameMainActivity activity;
    private GamePlayer player;
    private Game game;

    /**
     * Ctor for the RulesHelp object to initialize all variables
     * @param v the imageview of the Rules/Help button
     * @param player the player who pressed it
     * @param game the current game running
     * @param activity the current main activity of the game
     */
    public RulesHelp(View v, GamePlayer player, Game game,
                     GameMainActivity activity) {
        //calling the super constructor of XMLObject
        super(v);
        //setting instance variables
        this.game = game;
        this.player = player;
        this.getXmlObj().setOnClickListener(this);
        this.activity = activity;
    }

    /**
     * required onClick method for the button
     * @param v the view that was clicked
     */
    @Override
    public void onClick(View v) {
        //changes the current GUI layout to the rules layout
        this.activity.setContentView(R.layout.labyrinth_rules);

        //creates a RuleSwitch listener for both the left and right
        //arrows in the rules layout
        RulesSwitch prev = new RulesSwitch(
                this.activity.findViewById(R.id.rules_previous),
                RulesButtons.PREVIOUS,
                (ImageView)this.activity.findViewById(R.id.rules_slide),
                activity, player);
        RulesSwitch next = new RulesSwitch(
                this.activity.findViewById(R.id.rules_next),
                RulesButtons.NEXT,
                (ImageView)this.activity.findViewById(R.id.rules_slide),
                activity, player);
        //creates a RulesReturn listener for the top bar or bottom X so
        //the player can return to the game without scrolling through
        //all the rules.
        RulesSwitch title = new RulesSwitch(
                this.activity.findViewById(R.id.rules_title),
                RulesButtons.EXIT,
                (ImageView)this.activity.findViewById(R.id.rules_slide),
                activity, player);

        RulesSwitch exit = new RulesSwitch(
                this.activity.findViewById(R.id.rules_return),
                RulesButtons.EXIT,
                (ImageView)this.activity.findViewById(R.id.rules_slide),
                activity, player);
    }

    /**
     * setter for the RulesHelp's game
     * @param game the game that you want to set
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Getter for the RulesHelp's game
     * @return returns the game
     */
    public Game getGame() {
        return this.game;
    }
}
