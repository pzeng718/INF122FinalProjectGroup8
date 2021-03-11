package com.bejeweled.ui.views.select_player;

import com.bejeweled.Game;
import com.bejeweled.app.player.Player;
import com.bejeweled.ui.views.menu.MenuView;
import com.tmge.app.player.DefaultPlayer;
import com.tmge.ui.views.select_player.AbstractSelectPlayerView;

import java.util.List;

/**
 * @author vladshaternik on 2/24/21
 */
public class SelectPlayerView extends AbstractSelectPlayerView<Player> {

    public SelectPlayerView(List<Player> players) {
        super(players);
    }

    @Override
    protected Player createPlayer() {
        return new Player();
    }

    @Override
    protected void onNewPlayerAdded(Player player) {
        Game.getInstance().getPlayerManager().getPlayers().add(player);
    }

    @Override
    protected void onPlayerSelected(Player player) {
        Game.getInstance().getPlayerManager().setCurrentPlayer(player);
        Game.getInstance().getStageManager().loadView(new MenuView().init());
    }
}
