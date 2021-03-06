package com.tmge.app.player;

import com.google.gson.annotations.SerializedName;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author vladshaternik on 2/21/21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DefaultPlayer {

    @Builder.Default
    @SerializedName("id")
    private final String id = UUID.randomUUID().toString();

    @Builder.Default
    @SerializedName("username")
    private final String username = "";

    @Builder.Default
    @SerializedName("level")
    private int level = 1;

    @Builder.Default
    @SerializedName("totalScore")
    private int totalScore = 0;

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

    @Override
    public String toString() {
        return getUsername();
    }
}
