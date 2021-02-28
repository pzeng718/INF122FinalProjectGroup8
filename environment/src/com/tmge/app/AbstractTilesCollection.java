package com.tmge.app;

import com.tmge.app.tile.AbstractTile;

import java.awt.Point;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public abstract class AbstractTilesCollection {
    Set<Consumer<Point>> tileAddedListeners;

    abstract void add(AbstractTile tile, Point point);

    abstract void add(AbstractTile tile, int x);

    abstract void remove(AbstractTile tile);

    abstract AbstractTile get(Point point);

    abstract void swap(AbstractTile firstTile, AbstractTile secondTile);

    abstract void swap(Point firstPoint, Point secondPoint);

    abstract void addTileAddedListener(Consumer<Point> consumer);

    abstract void notifyTileAddedListener(Point point);

    abstract void calcPoints(List<Point> pointList);

    abstract void calcObjectives(List<Point> pointList);

    abstract boolean validMove(Point firstPoint, Point secondPoint);
}