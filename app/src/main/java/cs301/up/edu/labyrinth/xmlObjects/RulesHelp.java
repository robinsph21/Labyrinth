package cs301.up.edu.labyrinth.xmlObjects;

import android.view.View;
import android.widget.ImageView;

import cs301.up.edu.game.Game;
import cs301.up.edu.game.GameMainActivity;
import cs301.up.edu.game.GamePlayer;
import cs301.up.edu.R;

public class RulesHelp extends XMLObject {

    private final GameMainActivity activity;
    private GamePlayer player;
    private Game game;

    public RulesHelp(View v, GamePlayer player, Game game,
                     GameMainActivity activity) {
        super(v);
        this.game = game;
        this.player = player;
        this.getXmlObj().setOnClickListener(this);
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        this.activity.setContentView(R.layout.labyrinth_rules);

        RulesSwitch prev = new RulesSwitch(
                this.activity.findViewById(R.id.rules_previous),
                false,
                (ImageView)this.activity.findViewById(R.id.rules_slide),
                activity, player);
        RulesSwitch next = new RulesSwitch(
                this.activity.findViewById(R.id.rules_next),
                true,
                (ImageView)this.activity.findViewById(R.id.rules_slide),
                activity, player);

    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return this.game;
    }
}
