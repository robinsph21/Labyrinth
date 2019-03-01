package cs301.up.edu.labyrinth;

import cs301.up.edu.labyrinth.enums.Player;
import cs301.up.edu.game.GameHumanPlayer;
import cs301.up.edu.game.GameMainActivity;
import cs301.up.edu.R;
import cs301.up.edu.game.infoMsg.GameInfo;
import cs301.up.edu.labyrinth.enums.TreasureType;
import cs301.up.edu.labyrinth.xmlObjects.*;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.View;
import android.widget.Button;
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
    private BoardTile currentTile;
    private EndTurn endTurn;
    private Reset reset;

    // the most recent game state, as given to us by the LabyrinthLocalGame
    private LabyrinthGameState state;

    // the android activity that we are running
    private GameMainActivity myActivity;

    // Possible treasures
    private final static int[] allTreasureCards = new int[]
            {R.drawable.empty, R.drawable.card_bat, R.drawable.card_book,
            R.drawable.card_bow, R.drawable.card_candles,
            R.drawable.card_chest, R.drawable.card_coins,
            R.drawable.card_crown, R.drawable.card_dragon,
            R.drawable.card_fox, R.drawable.card_gem,
            R.drawable.card_ghost, R.drawable.card_goblet,
            R.drawable.card_helmet, R.drawable.card_keys,
            R.drawable.card_map, R.drawable.card_moth,
            R.drawable.card_mouse, R.drawable.card_owl,
            R.drawable.card_ring, R.drawable.card_shield,
            R.drawable.card_skull, R.drawable.card_spider,
            R.drawable.card_sword, R.drawable.card_urn};

    private final static int[] allTreasures = new int[]
            {R.drawable.empty, R.drawable.tile_bat, R.drawable.tile_book,
                    R.drawable.tile_bow, R.drawable.tile_candles,
                    R.drawable.tile_chest, R.drawable.tile_coins,
                    R.drawable.tile_crown, R.drawable.tile_dragon,
                    R.drawable.tile_fox, R.drawable.tile_gem,
                    R.drawable.tile_ghost, R.drawable.tile_goblet,
                    R.drawable.tile_helmet, R.drawable.tile_keys,
                    R.drawable.tile_map, R.drawable.tile_moth,
                    R.drawable.tile_mouse, R.drawable.tile_owl,
                    R.drawable.tile_ring, R.drawable.tile_shield,
                    R.drawable.tile_skull, R.drawable.tile_spider,
                    R.drawable.tile_sword, R.drawable.tile_urn};

    private final static int[] allPlayers = new int[]
            {R.drawable.pawn_red, R.drawable.pawn_yellow,
                    R.drawable.pawn_blue, R.drawable.pawn_green,
                    R.drawable.empty};

    private final static int[] allTiles = new int[]
            {R.drawable.tile_straight, R.drawable.tile_intersection,
            R.drawable.tile_corner, R.drawable.entry_red,
            R.drawable.entry_yellow, R.drawable.entry_blue,
            R.drawable.entry_green};

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
        // TODO: Finish all updating of display

        // Update current treasure
        int treasureIndex = this.state.getCurrentTreasure
                (this.playerNum).ordinal();
        this.currentTreasure.getXmlObj().setImageResource
                (allTreasureCards[treasureIndex]);

        // Update gameBoard
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                Tile current = this.state.getTile(i,j);

                // Check if treasure on tile
                int treasure = current.getTreasure().ordinal();
                int type = current.getType().ordinal();
                if (treasure == 0) {
                    //If no treasure, set based on type
                    ourGameBoard.getBoardSpot(i+1,j+1).getXmlObj().
                            setBackgroundResource(allTiles[type]);
                } else {
                    //If treasure, set the background to tile treasure
                    ourGameBoard.getBoardSpot(i+1, j+1).getXmlObj().
                            setBackgroundResource(allTreasures[treasure]);
                }

                // Check rotation of tile
                float rotation = (float)current.getRotation();
                ourGameBoard.getBoardSpot(i+1,j+1).getXmlObj().
                        setRotation(rotation);

                // Check if pawn on tile
                int pawn = current.getPawn().ordinal();
                ourGameBoard.getBoardSpot(i+1,j+1).getXmlObj().
                        setImageResource(allPlayers[pawn]);

            }
        }


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



        ourGameBoard = new Board(this, this.game);

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

        currentTile = new BoardTile(-1,-1,false,
                this,this.game);
        currentTile.setXmlObj(this.myActivity.findViewById(R.id.currentTile));

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

