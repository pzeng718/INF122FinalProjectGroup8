package com.bejeweled.app.player;

import com.tmge.app.player.DefaultPlayer;
import javafx.beans.property.SimpleIntegerProperty;

public class Player extends DefaultPlayer {

    private final transient SimpleIntegerProperty currentScore = new SimpleIntegerProperty();

    private final transient SimpleIntegerProperty currentMoves = new SimpleIntegerProperty();

    public int getCurrentScore() {
        return currentScore.get();
    }

    public SimpleIntegerProperty currentScoreProperty() {
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore.set(currentScore);
    }

    public int getCurrentMoves() {
        return currentMoves.get();
    }

    public SimpleIntegerProperty currentMovesProperty() {
        return currentMoves;
    }

    public void setCurrentMoves(int currentMoves) {
        this.currentMoves.set(currentMoves);
    }
}
