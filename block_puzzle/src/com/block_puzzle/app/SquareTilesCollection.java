package com.block_puzzle.app;

import com.block_puzzle.app.tile.TileType;
import com.tmge.app.collection.SwapResponse;
import com.tmge.app.collection.impl.AbstractDefaultTilesCollection;
import com.tmge.app.tile.DefaultTile;
import com.tmge.app.tile.TileChange;
import com.tmge.app.tile.TileChangeType;
import com.tmge.app.tile.TileTypeInfo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.awt.Point;
import java.util.List;
import java.util.*;

@Getter
@Setter(AccessLevel.PRIVATE)
public class SquareTilesCollection extends AbstractDefaultTilesCollection {

    @Override
    protected TileTypeInfo generateRandomTileTypeInfo() {
        return TileType.values()[(int) (TileType.values().length * Math.random())];
    }

    public SquareTilesCollection init(Level level) {
        return (SquareTilesCollection) init(level.getWidth(), level.getHeight());
    }

    public long getTilesLeft() {
        return getColumnsList().stream().mapToLong(Collection::size).sum();
    }

    @Override
    protected SwapResponse calcPoints(Set<DefaultTile> matchedTiles)  {
        throw new UnsupportedOperationException("This method is not used");
    }

    @Override
    public SwapResponse swap(Point firstPoint, Point secondPoint) {
        Optional<DefaultTile> firstTileOptional = getTileByPoint(firstPoint);
        Optional<DefaultTile> secondTileOptional = getTileByPoint(secondPoint);
        if (firstTileOptional.isPresent() && secondTileOptional.isPresent()) {
            DefaultTile firstTile = firstTileOptional.get();
            DefaultTile secondTile = secondTileOptional.get();
            if (firstPoint.x == secondPoint.x) {
                List<DefaultTile> col = getColumnsList().get(secondPoint.x);
                Collections.swap(col, firstPoint.y, secondPoint.y);
            } else {
                List<DefaultTile> firstCol = getColumnsList().get(firstPoint.x);
                List<DefaultTile> secondCol = getColumnsList().get(secondPoint.x);
                firstCol.set(firstPoint.y, secondTile);
                secondCol.set(secondPoint.y, firstTile);
            }
            notifyTileChangeListeners(new TileChange(firstTile, secondPoint, TileChangeType.SWAPPED));
            Set<DefaultTile> firstTileMatchedTiles = new HashSet<>();
            getMatchedTilesPositions(firstTile, firstTileMatchedTiles);
            int removedTiles = 0;
            if (firstTileMatchedTiles.size() > 2) {
                for (DefaultTile matchedTile : firstTileMatchedTiles) {
                    remove(matchedTile);
                    removedTiles++;
                }
            }
            Set<DefaultTile> secondTileMatchedPoints = new HashSet<>();
            getMatchedTilesPositions(secondTile, secondTileMatchedPoints);
            if (secondTileMatchedPoints.size() > 2) {
                for (DefaultTile matchedTail : secondTileMatchedPoints) {
                    remove(matchedTail);
                    removedTiles++;
                }
            }
            return new SwapResponseImpl(removedTiles);
        }
        return new SwapResponseImpl(0);
    }

    private void getMatchedTilesPositions(DefaultTile tile, Set<DefaultTile> tiles) {
        Optional<Point> pointByTile = getPointByTile(tile);
        if (pointByTile.isPresent()) {
            Point point = pointByTile.get();
            Point topPoint = new Point(point.x, point.y + 1);
            Point bottomPoint = new Point(point.x, point.y - 1);
            Point rightPoint = new Point(point.x + 1, point.y);
            Point leftPoint = new Point(point.x - 1, point.y);
            checkChildrenPoints(tile, topPoint, tiles);
            checkChildrenPoints(tile, bottomPoint, tiles);
            checkChildrenPoints(tile, rightPoint, tiles);
            checkChildrenPoints(tile, leftPoint, tiles);
        }
    }

    private void checkChildrenPoints(DefaultTile tile, Point point, Set<DefaultTile> tiles) {
        Optional<DefaultTile> tileByPoint = getTileByPoint(point);
        if (tileByPoint.isPresent()) {
            DefaultTile neighbourTile = tileByPoint.get();
            if (!tiles.contains(neighbourTile)) {
                if (neighbourTile.getTileTypeInfo() == tile.getTileTypeInfo()) {
                    tiles.add(neighbourTile);
                    getMatchedTilesPositions(neighbourTile, tiles);
                }
            }
        }
    }
}
