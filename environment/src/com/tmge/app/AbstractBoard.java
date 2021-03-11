package com.tmge.app;
import com.tmge.app.collection.AbstractTilesCollection;
import com.tmge.app.tile.DefaultTile;
import lombok.Getter;

@Getter
public abstract class AbstractBoard<T extends DefaultTile> {
    private final AbstractTilesCollection<T> tilesCollection;

    public AbstractBoard() {
        this.tilesCollection = createTilesCollection();
    }

    protected abstract AbstractTilesCollection<T> createTilesCollection();
}
