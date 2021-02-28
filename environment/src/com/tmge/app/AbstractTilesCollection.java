package com.tmge.app;

import com.tmge.app.tile.DefaultTile;

import java.awt.Point;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public abstract class AbstractTilesCollection {
    Set<Consumer<Point>> tileAddedListeners;

    public abstract void add(DefaultTile tile, Point point);

    public abstract void add(DefaultTile tile, int x);

    public abstract void remove(Point point);

    public abstract DefaultTile get(Point point);

    public abstract void swap(DefaultTile firstTile, DefaultTile secondTile);

    public abstract void swap(Point firstPoint, Point secondPoint);

//    public abstract void addTileAddedListener(Consumer<Point> consumer);
//
//    public abstract void notifyTileAddedListener(Point point);

    public abstract int calcPoints(List<Point> pointList);

//    public abstract void calcObjectives(List<Point> pointList);
//
//    public abstract boolean validMove(Point firstPoint, Point secondPoint);
}
