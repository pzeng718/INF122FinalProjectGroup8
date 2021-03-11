package com.tmge.app.collection.impl;

import com.tmge.app.tile.DefaultTile;
import com.tmge.app.tile.TileTypeInfo;
import lombok.AccessLevel;
import lombok.Getter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Getter(AccessLevel.PRIVATE)
public class DefaultTilesCollectionHelper {

    private final AbstractDefaultTilesCollection abstractDefaultTilesCollection;

    public DefaultTilesCollectionHelper(AbstractDefaultTilesCollection abstractDefaultTilesCollection) {
        this.abstractDefaultTilesCollection = abstractDefaultTilesCollection;
    }

    public void getMatchedTiles(DefaultTile tile, Set<DefaultTile> tiles) {
        Optional<Point> pointByTile = getAbstractDefaultTilesCollection().getPointByTile(tile);
        if (pointByTile.isPresent()) {
            Point point = pointByTile.get();
            java.util.List<DefaultTile> col = getAbstractDefaultTilesCollection().getColumnsList().get(point.x);
            java.util.List<DefaultTile> row = getRow(point.y);

            java.util.List<DefaultTile> colMatchedTiles = new ArrayList<>();
            colMatchedTiles.addAll(getMatchedTilesRecursive(col.subList(0, point.y), colMatchedTiles, tile,
                    false)); // Find matching tiles to the top of current tile
            colMatchedTiles.addAll(getMatchedTilesRecursive(col.subList(point.y + 1, getAbstractDefaultTilesCollection().getHeight()), colMatchedTiles, tile,
                    true)); // Find matching tiles to the bottom of current tile

            java.util.List<DefaultTile> rowMatchedTiles = new ArrayList<>();
            rowMatchedTiles.addAll(getMatchedTilesRecursive(row.subList(0, point.x), rowMatchedTiles, tile,
                    false)); // Find matching tiles to the left of current tile
            rowMatchedTiles.addAll(getMatchedTilesRecursive(row.subList(point.x + 1, getAbstractDefaultTilesCollection().getWidth()), rowMatchedTiles, tile,
                    true)); // Find matching tiles to the right of current tile


            if (colMatchedTiles.size() > 2 || rowMatchedTiles.size() > 2) {
                tiles.add(tile);
                tiles.addAll(colMatchedTiles);
                tiles.addAll(rowMatchedTiles);
            }
        }
    }

    /* Take in a list that contains tiles, either start checking for matching tiles from the start (if the list
       contains tiles to the bottom or right of current tile) or from the end (if the list contains tiles to the top
       or left of current tile)
    */
    public java.util.List<DefaultTile> getMatchedTilesRecursive(java.util.List<DefaultTile> targetTiles, List<DefaultTile> matchedTiles,
                                                                 DefaultTile tileToMatch,
                                                                 boolean fromStart) {
        int startIndex = (fromStart) ? 0 : targetTiles.size() - 1;
        int newStartIndex = (fromStart) ? 1 : 0;

        if (targetTiles.size() > 0 && targetTiles.get(startIndex).getTileTypeInfo() == tileToMatch.getTileTypeInfo()) {
            matchedTiles.add(targetTiles.get(startIndex));
            return getMatchedTilesRecursive(targetTiles.subList(newStartIndex, targetTiles.size() + newStartIndex - 1),
                    matchedTiles, tileToMatch, fromStart);
        } else {
            return matchedTiles;
        }
    }

    private List<DefaultTile> getRow(int rowIndex) {
        List<DefaultTile> row = new ArrayList<>();
        for (List<DefaultTile> col : getAbstractDefaultTilesCollection().getColumnsList()) {
            row.add(col.get(rowIndex));
        }

        return row;
    }

    public void removeMatchedTiles(Set<DefaultTile> matchedTiles) {
        for (DefaultTile matchedTile : matchedTiles) {
            getAbstractDefaultTilesCollection().getPointByTile(matchedTile).ifPresent(point -> {
                int colToAdd = point.x;
                getAbstractDefaultTilesCollection().remove(matchedTile);
                TileTypeInfo tileType = getAbstractDefaultTilesCollection().generateRandomTileTypeInfo();
                getAbstractDefaultTilesCollection().add(new DefaultTile(tileType), colToAdd);
            });
        }
    }
}
