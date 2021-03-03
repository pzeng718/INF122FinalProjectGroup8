package com.candycrush.app;

import com.candycrush.Game;
import com.candycrush.app.tile.TileType;
import com.tmge.app.AbstractTilesCollection;
import com.tmge.app.player.DefaultPlayer;
import com.tmge.app.tile.DefaultTile;
import com.tmge.app.tile.TileChange;
import com.tmge.app.tile.TileChangeType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.awt.Point;

@Getter
@Setter(AccessLevel.PRIVATE)
public class SquareTilesCollection extends AbstractTilesCollection {
    private final List<List<DefaultTile>> columnsList;
    private int width;
    private int height;
    private int objectiveScore;
    private int numOfMovesAllowed;
    private int numOfMovesAttempted;

    public SquareTilesCollection() {
        this.columnsList = new ArrayList<>();
    }

    public SquareTilesCollection init(Level level) {
        this.objectiveScore = level.getObjectiveScore();
        this.numOfMovesAllowed = level.getTurns();
        this.numOfMovesAttempted = 0;

        setWidth(level.getWidth());
        setHeight(level.getHeight());
        for (int col = 0; col < getWidth(); col++) {
            getColumnsList().add(new ArrayList<>());
            for (int row = 0; row < getHeight(); row++) {
                TileType tileType = TileType.values()[(int) (TileType.values().length * Math.random())];
                DefaultTile tile = new DefaultTile(tileType);
                add(tile, new Point(col, row));
            }
        }
        return this;
    }

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
    public void swap(DefaultTile firstTile, DefaultTile secondTile) {
        Optional<Point> firstPoint = getPointByTile(firstTile);
        Optional<Point> secondPoint = getPointByTile(secondTile);
        if (firstPoint.isPresent() && secondPoint.isPresent()) {
            swap(firstPoint.get(), secondPoint.get());
        }
    }

    @Override
    public void swap(Point firstPoint, Point secondPoint) {
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
            getMatchedTiles(firstTile, firstTileMatchedTiles);

            Set<DefaultTile> secondTileMatchedPoints = new HashSet<>();
            getMatchedTiles(secondTile, secondTileMatchedPoints);

            Optional<DefaultPlayer> currentPlayerOptional = Game.getInstance().getPlayerManager().getCurrentPlayer();
            DefaultPlayer currentPlayer = (currentPlayerOptional.isPresent()) ? currentPlayerOptional.get() : null;

            // For debugging purpose
            int numOfRemovedTiles = 0;

            if(firstTileMatchedTiles.size() > 2) {
                currentPlayer.addScore(calcPoints(firstTileMatchedTiles));
                numOfRemovedTiles += firstTileMatchedTiles.size();
                removeMatchedTiles(firstTile, firstTileMatchedTiles);
            }

            if(secondTileMatchedPoints.size() > 2) {
                currentPlayer.addScore(calcPoints(secondTileMatchedPoints));
                numOfRemovedTiles += secondTileMatchedPoints.size();
                removeMatchedTiles(secondTile, secondTileMatchedPoints);
            }

            // For debugging purpose
            System.out.format("%d tiles were removed\n", numOfRemovedTiles);

            checkGameResult(currentPlayer);
        }
    }

    // Check whether the player wins the game by reaching the objectiveScore or loses the game by running out of moves
    private void checkGameResult(DefaultPlayer currentPlayer){
        setNumOfMovesAttempted(getNumOfMovesAttempted() + 1);
        if(getNumOfMovesAttempted() >= getNumOfMovesAllowed()){ // Use has no move left
            if(currentPlayer.getScore() < getObjectiveScore()){
                System.out.println("You ran out of moves!");
            }else{ // User reaches the objective in the last move
                System.out.println("You completed this level. You can move on to next level.");
            }
        }else {
            if(currentPlayer.getScore() < getObjectiveScore()){
                System.out.format("%s has %d points right now. %d points left to the objective [%d].\n\t%d moves left.\n",
                        currentPlayer.getUsername(), currentPlayer.getScore(),
                        getObjectiveScore() - currentPlayer.getScore(), getObjectiveScore(),
                        getNumOfMovesAllowed() - getNumOfMovesAttempted());
            }else{
                System.out.println("You completed this level. You can move on to next level.");
                // TODO:
                //  1. notify game to advance to next level
                //  2. Create UI to show score, moves left and win/lose message
            }
        }
    }

    private void removeMatchedTiles(DefaultTile tile, Set<DefaultTile> matchedTiles){
        for (DefaultTile matchedTile : matchedTiles) {
            int colToAdd = getPointByTile(matchedTile).get().x;
            remove(matchedTile);
            TileType tileType = TileType.values()[(int) (TileType.values().length * Math.random())];
            add(new DefaultTile(tileType), colToAdd);
        }
    }

    public int calcPoints(Set<DefaultTile> matchedTiles) {
        int basePoint = 20;
        int numberOfMatchedTiles = matchedTiles.size();
        // More matched tiles will give a higher multiplier, for example:
        // multiplier for 4 matched tiles is 1.1, multiplier for 6 matched tiles is 1.3
        double multiplier = 1 + (numberOfMatchedTiles - 3) * 0.1;

        return (int) (basePoint * numberOfMatchedTiles * multiplier);
    }

    public void calcObjectives(List<Point> pointList) {
    }

    @Override
    public void getMatchedTilesPositions(DefaultTile tile, Set<DefaultTile> tiles) {
        /*
        Return the xy positions of all the tiles that can be eliminated after the swap.
        Otherwise, return an empty list if can't get more than 3 same tiles in either row or column.
        */
        return;
    }

    private List<DefaultTile> getRow(int rowIndex){
        List<DefaultTile> row = new ArrayList<>();
        for(List<DefaultTile> col : columnsList){
            row.add(col.get(rowIndex));
        }

        return row;
    }

    private void getMatchedTiles(DefaultTile tile, Set<DefaultTile> tiles) {
        Optional<Point> pointByTile = getPointByTile(tile);
        if (pointByTile.isPresent()) {
            Point point = pointByTile.get();
            List<DefaultTile> col = columnsList.get(point.x);
            List<DefaultTile> row = getRow(point.y);

            List<DefaultTile> colMatchedTiles = new ArrayList<>();
            colMatchedTiles.addAll(getMatchedTilesRecursive(col.subList(0, point.y), colMatchedTiles, tile,
                    false)); // Find matching tiles to the top of current tile
            colMatchedTiles.addAll(getMatchedTilesRecursive(col.subList(point.y + 1, height), colMatchedTiles, tile,
                    true)); // Find matching tiles to the bottom of current tile

            List<DefaultTile> rowMatchedTiles = new ArrayList<>();
            rowMatchedTiles.addAll(getMatchedTilesRecursive(row.subList(0, point.x), rowMatchedTiles, tile,
                    false)); // Find matching tiles to the left of current tile
            rowMatchedTiles.addAll(getMatchedTilesRecursive(row.subList(point.x + 1, width), rowMatchedTiles, tile,
                    true)); // Find matching tiles to the right of current tile


            if(colMatchedTiles.size() > 2 || rowMatchedTiles.size() > 2){
                tiles.add(tile);
                tiles.addAll(colMatchedTiles);
                tiles.addAll(rowMatchedTiles);
            }

//            Point point = pointByTile.get();
//            Point topPoint = new Point(point.x, point.y + 1);
//            Point bottomPoint = new Point(point.x, point.y - 1);
//            Point rightPoint = new Point(point.x + 1, point.y);
//            Point leftPoint = new Point(point.x - 1, point.y);
//            checkChildrenPoints(tile, topPoint, tiles);
//            checkChildrenPoints(tile, bottomPoint, tiles);
//            checkChildrenPoints(tile, rightPoint, tiles);
//            checkChildrenPoints(tile, leftPoint, tiles);

        }
    }

    /* Take in a list that contains tiles, either start checking for matching tiles from the start (if the list
       contains tiles to the bottom or right of current tile) or from the end (if the list contains tiles to the top
       or left of current tile)
    */
    private List<DefaultTile> getMatchedTilesRecursive(List<DefaultTile> targetTiles, List<DefaultTile> matchedTiles,
                                                       DefaultTile tileToMatch,
                                                       boolean fromStart){
        int startIndex = (fromStart) ? 0: targetTiles.size() - 1;
        int newStartIndex = (fromStart) ? 1: 0;

        if(targetTiles.size() > 0 && targetTiles.get(startIndex).getTileTypeInfo() == tileToMatch.getTileTypeInfo()){
            matchedTiles.add(targetTiles.get(startIndex));
            return getMatchedTilesRecursive(targetTiles.subList(newStartIndex, targetTiles.size() + newStartIndex - 1),
                    matchedTiles, tileToMatch, fromStart);
        }else{
            return matchedTiles;
        }
    }

//    private void checkChildrenPoints(DefaultTile tile, Point point, Set<DefaultTile> tiles) {
//        Optional<DefaultTile> tileByPoint = getTileByPoint(point);
//        if (tileByPoint.isPresent()) {
//            DefaultTile neighbourTile = tileByPoint.get();
//            if (!tiles.contains(neighbourTile)) {
//                if (neighbourTile.getTileTypeInfo() == tile.getTileTypeInfo()) {
//                    tiles.add(neighbourTile);
//                    getMatchedTilesPositions(neighbourTile, tiles);
//                }
//            }
//        }
//    }
}
