package com.tmge.app.player;

import com.google.gson.annotations.SerializedName;
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
    @SerializedName("password")
    private final String password = "";

    @Builder.Default
    @SerializedName("level")
    private int level = 1;

    @Builder.Default
    @SerializedName("score")
    private int score = 0;

    @Builder.Default
    @SerializedName("lives")
    private int lives = 0; // needs changed


    public void addLives(int l) {
        lives += l;
    }
    public void removeLives(int l) {
        lives -= l;
    }

    public void addScore(int s) {
        score += s;
    }

    public void incrementLevel() {
        level++;
    }

    public boolean login() {
        return (username == "" && password == ""); // change this
        // checks database to see if username and password matches
    }

    @Override
    public String toString() {
        return getUsername();
    }
}
