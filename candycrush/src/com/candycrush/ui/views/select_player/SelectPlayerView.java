package com.candycrush.ui.views.select_player;

import com.candycrush.Game;
import com.candycrush.ui.views.menu.MenuView;
import com.tmge.app.player.DefaultPlayer;
import com.tmge.ui.views.select_player.DefaultSelectPlayerView;

import java.util.List;

/**
 * @author vladshaternik on 2/24/21
 */
public class SelectPlayerView extends DefaultSelectPlayerView {

    public SelectPlayerView(List<DefaultPlayer> players) {
        super(players);
    }

    @Override
    protected void onNewPlayerAdded(DefaultPlayer player) {
        Game.getInstance().getPlayerManager().getPlayers().add(player);
    }

    @Override
    protected void onPlayerSelected(DefaultPlayer player) {
        Game.getInstance().getPlayerManager().setCurrentPlayer(player);
        Game.getInstance().getStageManager().loadView(new MenuView().init());
    }
}
