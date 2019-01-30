package cs301.up.edu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

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
                ourGameBoard.getBoardSpot(i, j).setXmlImage(imageObj);
            }
        }


        //Get all interaction buttons and images in menu as objects
        Button mainMenuButton = findViewById(R.id.mainMenuButton);
        ImageView player1Deck = findViewById(R.id.player1Deck);
        ImageView player2Deck = findViewById(R.id.player2Deck);
        ImageView player3Deck = findViewById(R.id.player3Deck);
        ImageView player4Deck = findViewById(R.id.player4Deck);
        ImageView currentTreasure = findViewById(R.id.currentTreasure);
        Button rotateClockwise = findViewById(R.id.rotateClockwise);
        Button rotateCounterClockwise = findViewById(R.id.rotateCounterClockwise);
        ImageView currentTile = findViewById(R.id.currentTile);
        Button endTurn = findViewById(R.id.endTurn);
        Button reset = findViewById(R.id.reset);

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
