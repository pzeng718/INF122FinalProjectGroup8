package com.candycrush.ui.views.menu;

import com.candycrush.Game;
import com.candycrush.ui.views.select_player.SelectPlayerView;
import com.tmge.ui.views.menu.DefaultMenuView;

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
        });
        addMenuItem("Choose Player", () -> {
            Game.getInstance().getStageManager().loadView(new SelectPlayerView(Game.getInstance().getPlayerManager().getPlayers()).init());
        });
        return this;
    }
}
