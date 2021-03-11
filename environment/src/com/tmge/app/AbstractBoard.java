package com.tmge.app;
import com.tmge.app.collection.AbstractTilesCollection;
import lombok.Getter;

@Getter
public abstract class AbstractBoard {
    private final AbstractTilesCollection tilesCollection;

    public AbstractBoard() {
        this.tilesCollection = createTilesCollection();
    }

    protected abstract AbstractTilesCollection createTilesCollection();
}
