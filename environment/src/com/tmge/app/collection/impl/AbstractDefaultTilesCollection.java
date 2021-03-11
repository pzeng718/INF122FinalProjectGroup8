package com.tmge.app.collection.impl;

import com.tmge.app.collection.AbstractTilesCollection;
import com.tmge.app.collection.SwapResponse;
import com.tmge.app.tile.DefaultTile;
import com.tmge.app.tile.TileChange;
import com.tmge.app.tile.TileChangeType;
import com.tmge.app.tile.TileTypeInfo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.List;
import java.util.*;

@Getter
@Setter(AccessLevel.PRIVATE)
public abstract class AbstractDefaultTilesCollection extends AbstractTilesCollection {

    private final DefaultTilesCollectionHelper defaultTilesCollectionHelper;
    private final List<List<DefaultTile>> columnsList;
    private int width;
    private int height;

    public AbstractDefaultTilesCollection() {
        this.defaultTilesCollectionHelper = new DefaultTilesCollectionHelper(this);
        this.columnsList = new ArrayList<>();
    }

    public AbstractDefaultTilesCollection init(int width, int height) {
        setWidth(width);
        setHeight(height);
        for (int col = 0; col < getWidth(); col++) {
            getColumnsList().add(new ArrayList<>());
            for (int row = 0; row < getHeight(); row++) {
                TileTypeInfo tileType = generateRandomTileTypeInfo();
                DefaultTile tile = new DefaultTile(tileType);
                add(tile, new Point(col, row));
            }
        }
        return this;
    }

    protected abstract TileTypeInfo generateRandomTileTypeInfo();

    @Override
    public void add(DefaultTile tile, Point point) {
        if (point.x >= 0 && point.x < getColumnsList().size()) {
            List<DefaultTile> col = getColumnsList().get(point.x);
            if (col.size() < getHeight()) {
                if (point.y >= 0 && point.y < col.size()) {
                    col.add(point.y, tile);
                    notifyTileChangeListeners(new TileChange(tile, point, TileChangeType.CREATED));
                    for (int i = point.y + 1; i < col.size(); i++) {
                        DefaultTile tileToNotify = col.get(i);
                        notifyTileChangeListeners(new TileChange(tileToNotify, new Point(point.x, i), TileChangeType.MOVED));
                    }
                } else {
                    col.add(tile);
                    notifyTileChangeListeners(new TileChange(tile, new Point(point.x, col.size() - 1), TileChangeType.CREATED));
                }
            }
        }
    }

    @Override
    public void add(DefaultTile tile, int colNum) {
        if (colNum >= 0 && colNum < getColumnsList().size()) {
            List<DefaultTile> rows = getColumnsList().get(colNum);
            if (rows.size() < getHeight()) {
                rows.add(tile);
                notifyTileChangeListeners(new TileChange(tile, new Point(colNum, rows.size() - 1), TileChangeType.CREATED));
            }
        }
    }

    @Override
    public void remove(DefaultTile tile) {
        Optional<Point> pointByTile = getPointByTile(tile);
        if (pointByTile.isPresent()) {
            Point point = pointByTile.get();
            if (point.x >= 0 && point.x < getColumnsList().size()) {
                List<DefaultTile> col = getColumnsList().get(point.x);
                if (point.y >= 0 && point.y < col.size()) {
                    DefaultTile removedTile = col.remove(point.y);
                    notifyTileChangeListeners(new TileChange(removedTile, point, TileChangeType.REMOVED));
                    for (int i = point.y; i < col.size(); i++) {
                        DefaultTile currentTile = col.get(i);
                        notifyTileChangeListeners(new TileChange(currentTile, new Point(point.x, i), TileChangeType.MOVED));
                    }
                }
            }
        }
    }

    @Override
    public Optional<DefaultTile> getTileByPoint(Point point) {
        if (point.x >= 0 && point.x < getColumnsList().size()) {
            List<DefaultTile> rows = getColumnsList().get(point.x);
            if (rows != null) {
                if (point.y >= 0 && point.y < rows.size()) {
                    return Optional.of(rows.get(point.y));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Point> getPointByTile(DefaultTile tile) {
        List<List<DefaultTile>> columns = getColumnsList();
        for (int i = 0; i < columns.size(); i++) {
            List<DefaultTile> rows = columns.get(i);
            for (int j = 0; j < rows.size(); j++) {
                DefaultTile currentTile = rows.get(j);
                if (currentTile.equals(tile)) {
                    return Optional.of(new Point(i, j));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public SwapResponse swap(DefaultTile firstTile, DefaultTile secondTile) {
        Optional<Point> firstPoint = getPointByTile(firstTile);
        Optional<Point> secondPoint = getPointByTile(secondTile);
        if (firstPoint.isPresent() && secondPoint.isPresent()) {
            return swap(firstPoint.get(), secondPoint.get());
        }
        return calcPoints(Collections.emptySet());
    }

    @Override
    public SwapResponse swap(Point firstPoint, Point secondPoint) {
        Optional<DefaultTile> firstTileOptional = getTileByPoint(firstPoint);
        Optional<DefaultTile> secondTileOptional = getTileByPoint(secondPoint);
        if (firstTileOptional.isPresent() && secondTileOptional.isPresent()) {
            DefaultTile firstTile = firstTileOptional.get();
            DefaultTile secondTile = secondTileOptional.get();
            if (firstPoint.x == secondPoint.x) {
                // Same col
                List<DefaultTile> col = getColumnsList().get(secondPoint.x);
                Collections.swap(col, firstPoint.y, secondPoint.y);
            } else {
                // Same row
                List<DefaultTile> firstCol = getColumnsList().get(firstPoint.x);
                List<DefaultTile> secondCol = getColumnsList().get(secondPoint.x);
                firstCol.set(firstPoint.y, secondTile);
                secondCol.set(secondPoint.y, firstTile);
            }
            notifyTileChangeListeners(new TileChange(firstTile, secondPoint, TileChangeType.SWAPPED));

            Set<DefaultTile> firstTileMatchedTiles = new HashSet<>();
            getDefaultTilesCollectionHelper().getMatchedTiles(firstTile, firstTileMatchedTiles);

            Set<DefaultTile> secondTileMatchedPoints = new HashSet<>();
            getDefaultTilesCollectionHelper().getMatchedTiles(secondTile, secondTileMatchedPoints);

            Set<DefaultTile> tiles = new HashSet<>();
            tiles.addAll(firstTileMatchedTiles);
            tiles.addAll(secondTileMatchedPoints);
            if (firstTileMatchedTiles.size() > 2) {
                getDefaultTilesCollectionHelper().removeMatchedTiles(firstTileMatchedTiles);
            }
            if (secondTileMatchedPoints.size() > 2) {
                getDefaultTilesCollectionHelper().removeMatchedTiles(secondTileMatchedPoints);
            }
            return calcPoints(tiles);
        }
        return calcPoints(Collections.emptySet());
    }

    protected abstract SwapResponse calcPoints(Set<DefaultTile> matchedTiles);
}
