package com.candycrush.ui.views.menu;

import com.candycrush.Game;
import com.candycrush.ui.views.select_player.SelectPlayerView;
import com.candycrush.ui.views.statistics.StatisticsView;
import com.tmge.app.player.DefaultPlayer;
import com.tmge.ui.components.UIComponents;
import com.tmge.ui.views.menu.DefaultMenuView;

import java.util.Optional;

/**
 * @author vladshaternik on 2/24/21
 */
public class MenuView extends DefaultMenuView {

    @Override
    public DefaultMenuView init() {
        super.init();
        addMenuItem("Start", () -> {
        });
        addMenuItem("Options", () -> {
        });
        addMenuItem("Statistics", () -> {
            Optional<DefaultPlayer> currentPlayer = Game.getInstance().getPlayerManager().getCurrentPlayer();
            if(currentPlayer.isPresent())
            {
                Game.getInstance().getStageManager().loadView(new StatisticsView(currentPlayer.get()).init());
            }
            else{
                UIComponents.showTextOnlyDialog("Please select the player!", Game.getInstance().getStageManager().getStage());
            }


        });
        addMenuItem("Choose Player", () -> {
            Game.getInstance().getStageManager().loadView(new SelectPlayerView(Game.getInstance().getPlayerManager().getPlayers()).init());
        });
        return this;
    }
}
