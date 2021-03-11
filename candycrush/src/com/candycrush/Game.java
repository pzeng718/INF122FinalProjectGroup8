package com.candycrush;


import com.candycrush.app.player.Player;
import com.candycrush.ui.StageManager;
import com.tmge.app.player.PlayerManager;
import com.tmge.ui.AbstractStageManager;
import lombok.Getter;
import lombok.Setter;
import javafx.application.Application;

/**
 * @author vladshaternik on 2/21/21
 */
@Getter
@Setter
public class Game {

    private AbstractStageManager stageManager;
    private final PlayerManager<Player> playerManager;

    private Game() {
        this.playerManager = new PlayerManager<>();
    }

    private static Game instance;

    public static Game getInstance() {
        return instance;
    }

    public static void start() {
        instance = new Game();
        getInstance().load();
    }

    public void load() {
        Application.launch(StageManager.class);
    }
}
