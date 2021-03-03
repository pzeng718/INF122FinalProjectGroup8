package com.tmge.app;

import com.tmge.app.tile.DefaultTile;
import com.tmge.app.tile.TileChange;
import lombok.Getter;

import java.awt.Point;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

@Getter
public abstract class AbstractTilesCollection {
    private final Set<Consumer<TileChange>> tileChangeListeners = new HashSet<>();

    public abstract void add(DefaultTile tile, Point point);

    public abstract void add(DefaultTile tile, int x);

    public abstract void remove(DefaultTile tile);

    public abstract Optional<DefaultTile> getTileByPoint(Point point);

    public abstract Optional<Point> getPointByTile(DefaultTile point);

    public abstract int swap(DefaultTile firstTile, DefaultTile secondTile);

    public abstract int swap(Point firstPoint, Point secondPoint);

    public abstract void getMatchedTilesPositions(DefaultTile tile, Set<DefaultTile> tiles);

    public void addTileChangeListener(Consumer<TileChange> consumer) {
        getTileChangeListeners().add(consumer);
    }

    public void notifyTileChangeListeners(TileChange tileChange) {
        for (Consumer<TileChange> tileChangeListener : getTileChangeListeners()) {
            tileChangeListener.accept(tileChange);
        }
    }
}
