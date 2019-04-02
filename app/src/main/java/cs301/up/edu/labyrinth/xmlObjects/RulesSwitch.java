package cs301.up.edu.labyrinth.xmlObjects;

import android.view.View;
import android.widget.ImageView;
import cs301.up.edu.R;
import cs301.up.edu.game.GameMainActivity;
import cs301.up.edu.game.GamePlayer;

public class RulesSwitch extends XMLObject {

    private boolean isNext;
    private ImageView display;
    private GameMainActivity activity;
    private GamePlayer player;

    private static int counter = 0;
    private final static int MAX_SLIDES = 2;

    public RulesSwitch(View v, boolean isNext, ImageView display,
                       GameMainActivity activity, GamePlayer player) {
        super(v);
        this.isNext = isNext;
        this.display = display;
        this.activity = activity;
        this.player = player;
        this.getXmlObj().setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (isNext) {
            if (counter< MAX_SLIDES - 1) {
                counter++;
            }
        } else {
            if (counter>0) {
                counter--;
            } else {
                this.player.setAsGui(activity);
            }
        }
        switch (counter) {
            case 0:
                this.display.setImageResource(R.drawable.rules_slide_1);
                break;
            case 1:
                this.display.setImageResource(R.drawable.empty);
                break;
        }
    }

}
