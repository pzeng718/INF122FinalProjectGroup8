package com.bejeweled.ui;

import com.bejeweled.Game;
import com.tmge.ui.AbstractStageManager;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

/**
 * @author vladshaternik on 2/21/21
 */
public class StageManager extends AbstractStageManager {

    public StageManager() {
        super();
        Game.getInstance().setStageManager(this);
    }

    @Override
    protected Parent firstView() {
        return new BorderPane();
    }
}
