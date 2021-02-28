package com.tmge.app.tile;

import javafx.scene.effect.Light;
import lombok.Getter;

import java.util.Set;
import java.util.function.Consumer;
import java.awt.Point;

@Getter

public class AbstractTile {
    public TileTypeInfo tileTypeInfo;
    public Set<Consumer<Point>> changeListeners;
    public Point xyCoordinates;

    public AbstractTile(TileTypeInfo tileTypeInfo) { this.tileTypeInfo = tileTypeInfo; }

    public TileTypeInfo getTileTypeInfo(){ return this.tileTypeInfo; }

    public void addChangeListener(Consumer<Point> consumer){
    }

    public void notifyChangeListeners(Point point){}
}
