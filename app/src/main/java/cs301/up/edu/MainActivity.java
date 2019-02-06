package cs301.up.edu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import cs301.up.edu.xmlObjects.CurrentTile;
import cs301.up.edu.xmlObjects.EndTurn;
import cs301.up.edu.xmlObjects.MainMenu;
import cs301.up.edu.xmlObjects.PlayerDeck;
import cs301.up.edu.xmlObjects.Reset;
import cs301.up.edu.xmlObjects.Rotate;
import cs301.up.edu.xmlObjects.TreasureGoal;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * External Citation
         *  Date:        1/25/19
         *  Problem:     Wanted to get rid of status bars
         *  Resource:    https://developer.android.com/training/system-ui/navigation#java
         *  Solution:    Used code from website and read possible view options to add immersive
         */
        //Remove layout borders from tablet
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN |
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);


        /**
         * External Citation
         *  Date:        1/30/19
         *  Problem:     Wanted to iterate through ID's to find xml objects
         *  Resource:    https://stackoverflow.com/questions/32515054/android-iterating-through-
         *                  button-ids-with-a-for-loop-in-java
         *  Solution:    Used code from website and adapted to fit our images
         */
        //Create an array to store our tiles of the board in java
        Board ourGameBoard = new Board();
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 10; j++) {
                ImageView imageObj = findViewById(getResources()
                        .getIdentifier("cell_" + i + j, "id", this.getPackageName()));
                ourGameBoard.getBoardSpot(i, j).setXmlObj(imageObj);
            }
        }


        //Get all interaction buttons and images in menu as objects
        MainMenu mainMenuButton = new MainMenu(findViewById(R.id.mainMenuButton));
        PlayerDeck player1Deck = new PlayerDeck(findViewById(R.id.player1Deck), "Red");
        PlayerDeck player2Deck = new PlayerDeck(findViewById(R.id.player2Deck), "Yellow");
        PlayerDeck player3Deck = new PlayerDeck(findViewById(R.id.player3Deck), "Green");
        PlayerDeck player4Deck = new PlayerDeck(findViewById(R.id.player4Deck), "Blue");
        TreasureGoal currentTreasure = new TreasureGoal(findViewById(R.id.currentTreasure));
        Rotate rotateClockwise = new Rotate(findViewById(R.id.rotateClockwise), true);
        Rotate rotateCounterClockwise = new Rotate(findViewById(R.id.rotateCounterClockwise), false);
        CurrentTile currentTile = new CurrentTile(findViewById(R.id.currentTile));
        EndTurn endTurn = new EndTurn(findViewById(R.id.endTurn));
        Reset reset = new Reset(findViewById(R.id.reset));

    }


    @Override
    protected void onResume() {
        super.onResume();

        //Remove layout borders from tablet
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN |
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }
}
