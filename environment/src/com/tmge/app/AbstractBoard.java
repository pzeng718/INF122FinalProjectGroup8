package com.tmge.app;
import lombok.Getter;

@Getter
public abstract class AbstractBoard {
    private final AbstractTilesCollection tiles;

    protected AbstractBoard() {
        this.tiles = createTilesCollection();
    }

    public abstract AbstractTilesCollection createTilesCollection();
}