package com.block_puzzle.ui.views.menu;

import com.block_puzzle.Game;
import com.block_puzzle.app.player.Player;
import com.block_puzzle.ui.views.game.GameView;
import com.block_puzzle.ui.views.menu.dialog.StartGameDialog;
import com.block_puzzle.ui.views.select_player.SelectPlayerView;
import com.block_puzzle.ui.views.statistics.StatisticsView;
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
            Optional<Player> currentPlayer = Game.getInstance().getPlayerManager().getCurrentPlayer();
            if (currentPlayer.isPresent()) {
                new StartGameDialog(currentPlayer.get()).init().showAndWait().ifPresent(startGameOptions ->
                        Game.getInstance().getStageManager().loadView(new GameView(startGameOptions).init()));
            } else {
                UIComponents.showTextOnlyDialog("Please select the player!", Game.getInstance().getStageManager().getStage());
            }
        });
        addMenuItem("Statistics", () -> {
            Optional<Player> currentPlayer = Game.getInstance().getPlayerManager().getCurrentPlayer();
            if (currentPlayer.isPresent()) {
                Game.getInstance().getStageManager().loadView(new StatisticsView(currentPlayer.get()).init());
            } else {
                UIComponents.showTextOnlyDialog("Please select the player!", Game.getInstance().getStageManager().getStage());
            }
        });
        addMenuItem("Choose Player", () -> {
            Game.getInstance().getStageManager().loadView(new SelectPlayerView(Game.getInstance().getPlayerManager().getPlayers()).init());
        });
        return this;
    }
}
