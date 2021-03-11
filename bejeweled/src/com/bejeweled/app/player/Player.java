package com.bejeweled.app.player;

import com.tmge.app.player.DefaultPlayer;
import javafx.beans.property.SimpleIntegerProperty;

public class Player extends DefaultPlayer {

    private final transient SimpleIntegerProperty tilesDestroyed = new SimpleIntegerProperty();

    public int getTilesDestroyed() {
        return tilesDestroyed.get();
    }

    public SimpleIntegerProperty tilesDestroyedProperty() {
        return tilesDestroyed;
    }

    public void setTilesDestroyed(int tilesDestroyed) {
        this.tilesDestroyed.set(tilesDestroyed);
    }
}
