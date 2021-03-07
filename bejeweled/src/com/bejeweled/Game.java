package com.bejeweled;


import com.bejeweled.ui.StageManager;
import com.tmge.app.player.PlayerManager;
import com.tmge.ui.AbstractStageManager;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import javafx.application.Application;

/**
 * @author vladshaternik on 2/21/21
 */
@Log
@Getter
@Setter
public class Game {

    private AbstractStageManager stageManager;
    private final PlayerManager playerManager;

    private Game() {
        this.playerManager = new PlayerManager();
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
