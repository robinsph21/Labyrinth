package cs301.up.edu.labyrinth;

import cs301.up.edu.game.infoMsg.IllegalMoveInfo;
import cs301.up.edu.game.infoMsg.NotYourTurnInfo;
import cs301.up.edu.labyrinth.enums.Arrow;
import cs301.up.edu.labyrinth.enums.Player;
import cs301.up.edu.game.GameHumanPlayer;
import cs301.up.edu.game.GameMainActivity;
import cs301.up.edu.R;
import cs301.up.edu.game.infoMsg.GameInfo;
import cs301.up.edu.labyrinth.xmlObjects.*;

import android.view.View;
import android.widget.Toast;

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

    //TODO: Update Greyed Card Images with Prettier Pictures

    /* instance variables */
    private Board ourGameBoard;
    private BoardEdge[] arrows = new BoardEdge[12];
    private MainMenu mainMenuButton;
    private PlayerDeck playerRedDeck;
    private PlayerDeck playerYellowDeck;
    private PlayerDeck playerGreenDeck;
    private PlayerDeck playerBlueDeck;
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

    private final static int[] multiplePlayers = new int[]
            {R.drawable.pawn_red_yellow_green_blue,
            R.drawable.pawn_red_yellow_blue,
            R.drawable.pawn_red_yellow_green,
            R.drawable.pawn_red_yellow,
            R.drawable.pawn_red_green_blue,
            R.drawable.pawn_red_blue,
            R.drawable.pawn_red_green,
            R.drawable.pawn_yellow_green_blue,
            R.drawable.pawn_yellow_blue,
            R.drawable.pawn_yellow_green,
            R.drawable.pawn_green_blue};

    private final static int[] allTiles = new int[]
            {R.drawable.tile_straight, R.drawable.tile_intersection,
            R.drawable.tile_corner, R.drawable.entry_red,
            R.drawable.entry_yellow, R.drawable.entry_blue,
            R.drawable.entry_green};
    private int[] number = new int[]
            {R.drawable.empty, R.drawable.number_1, R.drawable.number_2,
            R.drawable.number_3, R.drawable.number_4, R.drawable.number_5,
            R.drawable.number_6};

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

        //Update Decks
        this.playerRedDeck.getXmlObj().setBackgroundResource
                (R.drawable.card_back_red_greyed);
        this.playerYellowDeck.getXmlObj().setBackgroundResource
                (R.drawable.card_back_yellow_greyed);
        this.playerBlueDeck.getXmlObj().setBackgroundResource
                (R.drawable.card_back_blue_greyed);
        this.playerGreenDeck.getXmlObj().setBackgroundResource
                (R.drawable.card_back_green_greyed);

        switch (this.state.getPlayerTurn()) {
            case RED:
                this.playerRedDeck.getXmlObj().setBackgroundResource
                        (R.drawable.card_back_red);
                break;
            case YELLOW:
                this.playerYellowDeck.getXmlObj().setBackgroundResource
                        (R.drawable.card_back_yellow);
                break;
            case BLUE:
                this.playerBlueDeck.getXmlObj().setBackgroundResource
                        (R.drawable.card_back_blue);
                break;
            case GREEN:
                this.playerGreenDeck.getXmlObj().setBackgroundResource
                        (R.drawable.card_back_green);
                break;
        }

        //Update Number of Treasures
        int numCards = state.getPlayerDeckSize(Player.RED);
        this.playerRedDeck.getXmlObj().setImageResource(number[numCards]);

        numCards = state.getPlayerDeckSize(Player.YELLOW);
        this.playerYellowDeck.getXmlObj().setImageResource(number[numCards]);

        numCards = state.getPlayerDeckSize(Player.BLUE);
        this.playerBlueDeck.getXmlObj().setImageResource(number[numCards]);

        numCards = state.getPlayerDeckSize(Player.GREEN);
        this.playerGreenDeck.getXmlObj().setImageResource(number[numCards]);

        // Update current treasure
        int treasureIndex = this.state.getCurrentTreasure
                (this.playerNum).ordinal();
        this.currentTreasure.getXmlObj().setImageResource
                (allTreasureCards[treasureIndex]);

        //Update current tile
        int treasure1 = state.getCurrentTile().getTreasure().ordinal();
        int type1 = state.getCurrentTile().getType().ordinal();
        if (treasure1 == 0) {
            //If no treasure, set based on type
            this.currentTile.getXmlObj().
                    setBackgroundResource(allTiles[type1]);
        } else {
            //If treasure, set the background to tile treasure
            this.currentTile.getXmlObj().
                    setBackgroundResource(allTreasures[treasure1]);
        }
        float rotation1 = (float)state.getCurrentTile().getRotation();
        this.currentTile.getXmlObj().setRotation(rotation1);

        //Update clickable arrows

        //All arrows clickable and showing
        for (BoardEdge member : arrows) {
            member.setClickable(true);
            switch (member.getThisArrow()) {
                case LEFT_TOP: {
                    member.getXmlObj().setImageResource(R.drawable.arrow_right);
                } break;

                case LEFT_MIDDLE: {
                    member.getXmlObj().setImageResource(R.drawable.arrow_right);
                } break;

                case LEFT_BOTTOM: {
                    member.getXmlObj().setImageResource(R.drawable.arrow_right);
                } break;

                case RIGHT_TOP: {
                    member.getXmlObj().setImageResource(R.drawable.arrow_left);
                } break;

                case RIGHT_MIDDLE: {
                    member.getXmlObj().setImageResource(R.drawable.arrow_left);
                } break;

                case RIGHT_BOTTOM: {
                    member.getXmlObj().setImageResource(R.drawable.arrow_left);
                } break;

                case TOP_LEFT: {
                    member.getXmlObj().setImageResource(R.drawable.arrow_down);
                } break;

                case TOP_MIDDLE: {
                    member.getXmlObj().setImageResource(R.drawable.arrow_down);
                } break;

                case TOP_RIGHT: {
                    member.getXmlObj().setImageResource(R.drawable.arrow_down);
                } break;

                case BOTTOM_LEFT: {
                    member.getXmlObj().setImageResource(R.drawable.arrow_up);
                } break;

                case BOTTOM_MIDDLE: {
                    member.getXmlObj().setImageResource(R.drawable.arrow_up);
                } break;

                case BOTTOM_RIGHT: {
                    member.getXmlObj().setImageResource(R.drawable.arrow_up);
                } break;
            }

        }

        switch (state.getDisabledArrow()) {
            case LEFT_TOP: {
                arrows[Arrow.LEFT_TOP.ordinal()].setClickable(false);
                arrows[Arrow.LEFT_TOP.ordinal()].getXmlObj().
                        setImageResource(R.drawable.empty);
            } break;

            case LEFT_MIDDLE: {
                arrows[Arrow.LEFT_MIDDLE.ordinal()].setClickable(false);
                arrows[Arrow.LEFT_MIDDLE.ordinal()].getXmlObj().
                        setImageResource(R.drawable.empty);
            } break;

            case LEFT_BOTTOM: {
                arrows[Arrow.LEFT_BOTTOM.ordinal()].setClickable(false);
                arrows[Arrow.LEFT_BOTTOM.ordinal()].getXmlObj().
                        setImageResource(R.drawable.empty);
            } break;

            case RIGHT_TOP: {
                arrows[Arrow.RIGHT_TOP.ordinal()].setClickable(false);
                arrows[Arrow.RIGHT_TOP.ordinal()].getXmlObj().
                        setImageResource(R.drawable.empty);
            } break;

            case RIGHT_MIDDLE: {
                arrows[Arrow.RIGHT_MIDDLE.ordinal()].setClickable(false);
                arrows[Arrow.RIGHT_MIDDLE.ordinal()].getXmlObj().
                        setImageResource(R.drawable.empty);
            } break;

            case RIGHT_BOTTOM: {
                arrows[Arrow.RIGHT_BOTTOM.ordinal()].setClickable(false);
                arrows[Arrow.RIGHT_BOTTOM.ordinal()].getXmlObj().
                        setImageResource(R.drawable.empty);
            } break;

            case TOP_LEFT: {
                arrows[Arrow.TOP_LEFT.ordinal()].setClickable(false);
                arrows[Arrow.TOP_LEFT.ordinal()].getXmlObj().
                        setImageResource(R.drawable.empty);
            } break;

            case TOP_MIDDLE: {
                arrows[Arrow.TOP_MIDDLE.ordinal()].setClickable(false);
                arrows[Arrow.TOP_MIDDLE.ordinal()].getXmlObj().
                        setImageResource(R.drawable.empty);
            } break;

            case TOP_RIGHT: {
                arrows[Arrow.TOP_RIGHT.ordinal()].setClickable(false);
                arrows[Arrow.TOP_RIGHT.ordinal()].getXmlObj().
                        setImageResource(R.drawable.empty);
            } break;

            case BOTTOM_LEFT: {
                arrows[Arrow.BOTTOM_LEFT.ordinal()].setClickable(false);
                arrows[Arrow.BOTTOM_LEFT.ordinal()].getXmlObj().
                        setImageResource(R.drawable.empty);
            } break;

            case BOTTOM_MIDDLE: {
                arrows[Arrow.BOTTOM_MIDDLE.ordinal()].setClickable(false);
                arrows[Arrow.BOTTOM_MIDDLE.ordinal()].getXmlObj().
                        setImageResource(R.drawable.empty);
            } break;

            case BOTTOM_RIGHT: {
                arrows[Arrow.BOTTOM_RIGHT.ordinal()].setClickable(false);
                arrows[Arrow.BOTTOM_RIGHT.ordinal()].getXmlObj().
                        setImageResource(R.drawable.empty);
            } break;
        }

        // Update gameBoard
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                Tile current = this.state.getTile(i,j);

                // Check rotation of tile
                float rotation = (float)current.getRotation();
                ourGameBoard.getBoardSpot(i+1,j+1).getXmlObj().
                        setRotation(rotation);

                // Check if pawn on tile
                boolean[] pawn = current.getPawn();
                if (pawn[4]) {
                    ourGameBoard.getBoardSpot(i+1,j+1).getXmlObj().
                            setImageResource(allPlayers[4]);
                } else if (pawn[0]) {
                    if (pawn[1]) {
                        if (pawn[2]) {
                            if (pawn[3]) {
                                ourGameBoard.getBoardSpot(i+1,j+1).
                                        getXmlObj().
                                        setImageResource(multiplePlayers[0]);
                            } else {
                                ourGameBoard.getBoardSpot(i+1,j+1).
                                        getXmlObj().
                                        setImageResource(multiplePlayers[1]);
                            }
                        } else if (pawn[3]){
                            ourGameBoard.getBoardSpot(i+1,j+1).
                                    getXmlObj().
                                    setImageResource(multiplePlayers[2]);
                        } else {
                            ourGameBoard.getBoardSpot(i+1,j+1).
                                    getXmlObj().
                                    setImageResource(multiplePlayers[3]);
                        }
                    } else if (pawn[2]) {
                        if (pawn[3]) {
                            ourGameBoard.getBoardSpot(i+1,j+1).
                                    getXmlObj().
                                    setImageResource(multiplePlayers[4]);
                        } else {
                            ourGameBoard.getBoardSpot(i+1,j+1).
                                    getXmlObj().
                                    setImageResource(multiplePlayers[5]);
                        }
                    } else if (pawn[3]) {
                        ourGameBoard.getBoardSpot(i+1,j+1).
                                getXmlObj().
                                setImageResource(multiplePlayers[6]);
                    } else {
                        ourGameBoard.getBoardSpot(i+1,j+1).
                                getXmlObj().
                                setImageResource(allPlayers[0]);
                    }
                } else if (pawn[1]) {
                    if (pawn[2]) {
                        if (pawn[3]) {
                            ourGameBoard.getBoardSpot(i+1,j+1).
                                    getXmlObj().
                                    setImageResource(multiplePlayers[7]);
                        } else {
                            ourGameBoard.getBoardSpot(i+1,j+1).
                                    getXmlObj().
                                    setImageResource(multiplePlayers[8]);
                        }
                    } else if (pawn[3]) {
                        ourGameBoard.getBoardSpot(i+1,j+1).
                                getXmlObj().
                                setImageResource(multiplePlayers[9]);
                    } else {
                        ourGameBoard.getBoardSpot(i+1,j+1).
                                getXmlObj().
                                setImageResource(allPlayers[1]);
                    }

                } else if (pawn[2]) {
                    if (pawn[3]) {
                        ourGameBoard.getBoardSpot(i+1,j+1).
                                getXmlObj().
                                setImageResource(multiplePlayers[10]);
                    } else {
                        ourGameBoard.getBoardSpot(i+1,j+1).
                                getXmlObj().
                                setImageResource(allPlayers[2]);
                    }
                } else if (pawn[3]) {
                    ourGameBoard.getBoardSpot(i+1,j+1).
                            getXmlObj().
                            setImageResource(allPlayers[3]);
                }

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
                    //If current treasure, highlight it
                    if (treasureIndex == treasure) {
                        ourGameBoard.getBoardSpot(i+1, j+1).getXmlObj().
                                setImageResource(R.drawable.highlight);
                    } else if (current.getPawn()[4]) {
                        ourGameBoard.getBoardSpot(i+1, j+1).getXmlObj().
                                setImageResource(R.drawable.empty);
                    }
                }


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
        if (info instanceof LabyrinthGameState) {

            // Check if game variable has been acquired
            if (mainMenuButton.getGame() == null) {
                ourGameBoard.setGame(this.game);
                endTurn.setGame(this.game);
                mainMenuButton.setGame(this.game);
                reset.setGame(this.game);
                rotateClockwise.setGame(this.game);
                rotateCounterClockwise.setGame(this.game);
            }

            // update our state; then update the display
            this.state = (LabyrinthGameState) info;
            updateDisplay();

        } else if (info instanceof NotYourTurnInfo) {
            Toast.makeText(this.myActivity, "Not Your Turn!",
                    Toast.LENGTH_SHORT).show();

        } else if (info instanceof IllegalMoveInfo) {
            Toast.makeText(this.myActivity, "Illegal Move!!",
                    Toast.LENGTH_SHORT).show();
        }
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



        ourGameBoard = new Board(this, this.game, this.myActivity);

        mainMenuButton = new MainMenu(this.myActivity.findViewById
                (R.id.mainMenuButton),
                this, this.game);

        switch (this.playerNum) {

            case 0:
                playerRedDeck = new PlayerDeck(this.myActivity.findViewById
                        (R.id.yourDeck), Player.RED);

                playerYellowDeck = new PlayerDeck(this.myActivity.findViewById
                        (R.id.oponent1Deck), Player.YELLOW);

                playerBlueDeck = new PlayerDeck(this.myActivity.findViewById
                        (R.id.oponent2Deck), Player.BLUE);

                playerGreenDeck = new PlayerDeck(this.myActivity.findViewById
                        (R.id.oponent3Deck), Player.GREEN);
                break;

            case 1:
                playerRedDeck = new PlayerDeck(this.myActivity.findViewById
                        (R.id.oponent1Deck), Player.RED);

                playerYellowDeck = new PlayerDeck(this.myActivity.findViewById
                        (R.id.yourDeck), Player.YELLOW);

                playerBlueDeck = new PlayerDeck(this.myActivity.findViewById
                        (R.id.oponent2Deck), Player.BLUE);

                playerGreenDeck = new PlayerDeck(this.myActivity.findViewById
                        (R.id.oponent3Deck), Player.GREEN);
                break;

            case 2:
                playerRedDeck = new PlayerDeck(this.myActivity.findViewById
                        (R.id.oponent1Deck), Player.RED);

                playerYellowDeck = new PlayerDeck(this.myActivity.findViewById
                        (R.id.oponent2Deck), Player.YELLOW);

                playerBlueDeck = new PlayerDeck(this.myActivity.findViewById
                        (R.id.oponent3Deck), Player.BLUE);

                playerGreenDeck = new PlayerDeck(this.myActivity.findViewById
                        (R.id.yourDeck), Player.GREEN);
                break;

            case 3:
                playerRedDeck = new PlayerDeck(this.myActivity.findViewById
                        (R.id.oponent1Deck), Player.RED);

                playerYellowDeck = new PlayerDeck(this.myActivity.findViewById
                        (R.id.oponent2Deck), Player.YELLOW);

                playerBlueDeck = new PlayerDeck(this.myActivity.findViewById
                        (R.id.yourDeck), Player.BLUE);

                playerGreenDeck = new PlayerDeck(this.myActivity.findViewById
                        (R.id.oponent3Deck), Player.GREEN);
                break;

        }

        currentTreasure = new TreasureGoal(this.myActivity.findViewById
                (R.id.currentTreasure));

        rotateClockwise = new Rotate(this.myActivity.findViewById
                (R.id.rotateClockwise), true,
                this, this.game);

        rotateCounterClockwise = new Rotate(this.myActivity.findViewById
                (R.id.rotateCounterClockwise), false,
                this, this.game);

        currentTile = new BoardTile(this.myActivity.
                findViewById(R.id.currentTile),-1,-1,false,
                this,this.game);

        endTurn = new EndTurn(this.myActivity.findViewById
                (R.id.endTurn),
                this, this.game);

        reset = new Reset(this.myActivity.findViewById
                (R.id.reset),
                this, this.game);


        arrows[0] = (BoardEdge)ourGameBoard.getBoardSpot(2,0);
        arrows[1] = (BoardEdge)ourGameBoard.getBoardSpot(4,0);
        arrows[2] = (BoardEdge)ourGameBoard.getBoardSpot(6,0);
        arrows[3] = (BoardEdge)ourGameBoard.getBoardSpot(8,2);
        arrows[4] = (BoardEdge)ourGameBoard.getBoardSpot(8,4);
        arrows[5] = (BoardEdge)ourGameBoard.getBoardSpot(8,6);
        arrows[6] = (BoardEdge)ourGameBoard.getBoardSpot(6,8);
        arrows[7] = (BoardEdge)ourGameBoard.getBoardSpot(4,8);
        arrows[8] = (BoardEdge)ourGameBoard.getBoardSpot(2,8);
        arrows[9] = (BoardEdge)ourGameBoard.getBoardSpot(0,6);
        arrows[10] = (BoardEdge)ourGameBoard.getBoardSpot(0,4);
        arrows[11] = (BoardEdge)ourGameBoard.getBoardSpot(0,2);


        // if we have a game state, "simulate" that we have just received
        // the state from the game so that the GUI values are updated
        if (state != null) {
            receiveInfo(state);
        }
    }

}// class CounterHumanPlayer

