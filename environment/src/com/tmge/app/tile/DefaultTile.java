package com.tmge.app.tile;

import lombok.Getter;

import java.util.Set;
import java.util.function.Consumer;
import java.awt.Point;

@Getter
public class DefaultTile {
    public TileTypeInfo tileTypeInfo;
    public Set<Consumer<Point>> changeListeners;

    public DefaultTile(TileTypeInfo tileTypeInfo) {
        this.tileTypeInfo = tileTypeInfo;
    }

    public void addChangeListener(Consumer<Point> consumer) {
        getChangeListeners().add(consumer);
    }

    public void notifyChangeListeners(Point point) {
        for (Consumer<Point> changeListener : getChangeListeners()) {
            changeListener.accept(point);
        }
    }
}
