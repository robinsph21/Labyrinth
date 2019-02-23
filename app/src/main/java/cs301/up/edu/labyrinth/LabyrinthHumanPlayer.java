package cs301.up.edu.labyrinth;

import cs301.up.edu.enums.Player;
import cs301.up.edu.game.GameHumanPlayer;
import cs301.up.edu.game.GameMainActivity;
import cs301.up.edu.R;
import cs301.up.edu.game.infoMsg.GameInfo;
import cs301.up.edu.xmlObjects.*;

import android.view.View;
import android.widget.ImageView;

/**
 * A GUI of a counter-player. The GUI displays the current value of the counter,
 * and allows the human player to press the '+' and '-' buttons in order to
 * send moves to the game.
 *
 * Just for fun, the GUI is implemented so that if the player presses either button
 * when the counter-value is zero, the screen flashes briefly, with the flash-color
 * being dependent on whether the player is player 0 or player 1.
 *
 * @author Steven R. Vegdahl
 * @author Andrew M. Nuxoll
 * @version July 2013
 */
public class LabyrinthHumanPlayer extends GameHumanPlayer {

    /* instance variables */
    private Board ourGameBoard;
    private MainMenu mainMenuButton;
    private PlayerDeck player1Deck;
    private PlayerDeck player2Deck;
    private PlayerDeck player3Deck;
    private PlayerDeck player4Deck;
    private TreasureGoal currentTreasure;
    private Rotate rotateClockwise;
    private Rotate rotateCounterClockwise;
    private CurrentTile currentTile;
    private EndTurn endTurn;
    private Reset reset;

    // the most recent game state, as given to us by the LabyrinthLocalGame
    private LabyrinthGameState state;

    // the android activity that we are running
    private GameMainActivity myActivity;

    /**
     * constructor
     * @param name
     * 		the player's name
     */
    public LabyrinthHumanPlayer(String name) {
        super(name);
    }

    /**
     * Returns the GUI's top view object
     *
     * @return
     * 		the top object in the GUI's view heirarchy
     */
    public View getTopView() {
        return myActivity.findViewById(R.id.topGUI);
    }

    /**
     * Update everything displayed on tablet
     */
    public void updateDisplay() {
        // TODO: Modify XML objects to mirror gamestate variable
    }


    /**
     * callback method when we get a message (e.g., from the game)
     *
     * @param info
     * 		the message
     */
    @Override
    public void receiveInfo(GameInfo info) {
        // ignore the message if it's not a CounterState message
        if (!(info instanceof LabyrinthGameState)) return;

        // update our state; then update the display
        this.state = (LabyrinthGameState)info;
        updateDisplay();
    }

    /**
     * callback method--our game has been chosen/rechosen to be the GUI,
     * called from the GUI thread
     *
     * @param activity
     * 		the activity under which we are running
     */
    public void setAsGui(GameMainActivity activity) {

        // remember the activity
        this.myActivity = activity;

        // Load the layout resource for our GUI
        activity.setContentView(R.layout.labyrinth_human_player);

        /**
         * External Citation
         *  Date:        1/25/19
         *  Problem:     Wanted to get rid of status bars
         *  Resource:    https://developer.android.com/training/system-ui/
         *                  navigation#java
         *  Solution:    Used code from website and read possible view options
         *                  to add immersive
         */
        //Remove layout borders from tablet
        View decorView = this.myActivity.getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);



        ourGameBoard = new Board();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                ImageView imageObj = this.myActivity.findViewById(
                        this.myActivity.getResources().getIdentifier
                                ("cell_" + i + j, "id",
                                this.myActivity.getPackageName()));
                ourGameBoard.getBoardSpot(i, j).setXmlObj(imageObj);
            }
        }

        mainMenuButton = new MainMenu(this.myActivity.findViewById
                (R.id.mainMenuButton),
                this, this.game);

        player1Deck = new PlayerDeck(this.myActivity.findViewById
                (R.id.player1Deck), Player.RED);

        player2Deck = new PlayerDeck(this.myActivity.findViewById
                (R.id.player2Deck), Player.YELLOW);

        player3Deck = new PlayerDeck(this.myActivity.findViewById
                (R.id.player3Deck), Player.GREEN);

        player4Deck = new PlayerDeck(this.myActivity.findViewById
                (R.id.player4Deck), Player.BLUE);

        currentTreasure = new TreasureGoal(this.myActivity.findViewById
                (R.id.currentTreasure));

        rotateClockwise = new Rotate(this.myActivity.findViewById
                (R.id.rotateClockwise), true,
                this, this.game);

        rotateCounterClockwise = new Rotate(this.myActivity.findViewById
                (R.id.rotateCounterClockwise), false,
                this, this.game);

        currentTile = new CurrentTile(this.myActivity.findViewById
                (R.id.currentTile));

        endTurn = new EndTurn(this.myActivity.findViewById
                (R.id.endTurn),
                this, this.game);

        reset = new Reset(this.myActivity.findViewById
                (R.id.reset),
                this, this.game);



        // if we have a game state, "simulate" that we have just received
        // the state from the game so that the GUI values are updated
        if (state != null) {
            receiveInfo(state);
        }
    }

}// class CounterHumanPlayer

