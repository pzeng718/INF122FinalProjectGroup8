package com.tmge.app.collection;

import com.tmge.app.tile.DefaultTile;
import com.tmge.app.tile.TileChange;
import lombok.Getter;

import java.awt.Point;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

@Getter
public abstract class AbstractTilesCollection<T extends DefaultTile> {
    private final Set<Consumer<TileChange<T>>> tileChangeListeners = new HashSet<>();

    public abstract void add(T tile, Point point);

    public abstract void add(T tile, int x);

    public abstract void remove(T tile);

    public abstract Optional<T> getTileByPoint(Point point);

    public abstract Optional<Point> getPointByTile(T point);

    public abstract SwapResponse swap(T firstTile, T secondTile);

    public abstract SwapResponse swap(Point firstPoint, Point secondPoint);

    public void addTileChangeListener(Consumer<TileChange<T>> consumer) {
        getTileChangeListeners().add(consumer);
    }

    public void notifyTileChangeListeners(TileChange<T> tileChange) {
        for (Consumer<TileChange<T>> tileChangeListener : getTileChangeListeners()) {
            tileChangeListener.accept(tileChange);
        }
    }
}
