package com.candycrush.app;

import com.candycrush.app.tile.TileType;
import com.tmge.app.AbstractTilesCollection;
import com.tmge.app.tile.DefaultTile;
import lombok.Getter;

import java.util.*;
import java.awt.Point;

@Getter
public class SquareTilesCollection extends AbstractTilesCollection {
    List<List<DefaultTile>> tiles;
    int tilesToDispense;
    int width;
    int height;

    public SquareTilesCollection(int width, int height){
        this.tiles = new ArrayList<>(width);
        this.width = width;
        this.height = height;
        for(int i = 0; i < width; i++){
            List<DefaultTile> col = new ArrayList<>();
            generateTiles(col, i, height);
            tiles.add(col);
        }
    }

    private void generateTiles(List<DefaultTile> col, int colIndex, int height){
        Random rand = new Random();
        TileType[] tileTypes = new TileType[]{TileType.DIAMOND, TileType.CRYSTAL, TileType.COIN};

        for(int rowIndex = 0; rowIndex < height; rowIndex++) {
            int typeIndex = rand.nextInt(3);
            TileType tileType = tileTypes[typeIndex];
            DefaultTile tile = new DefaultTile(tileType);
            col.add(tile);
        }
    }

    public void add(DefaultTile tile, Point point){
        List<DefaultTile> col = tiles.get(point.x);
        col.add(point.y, tile);
    }

    public void add(DefaultTile tile, int colNum){
        List<DefaultTile> col = tiles.get(colNum);
        col.add(tile);
    }

    public void remove(Point point){
        List<DefaultTile> col = tiles.get(point.x);
        col.remove(point.y);
    }

    public DefaultTile get(Point point){
        return tiles.get(point.x).get(point.y);
    }

    public Point getPosition(DefaultTile tile){
        Point point = new Point(0, 0);
        for(int colIndex = 0; colIndex < width; colIndex ++){
            List<DefaultTile> col = tiles.get(colIndex);
            for(int rowIndex = 0; rowIndex < height; rowIndex ++){
                if(col.get(rowIndex) == tile){
                    point.x = colIndex;
                    point.y = rowIndex;
                }
            }
        }
        return point;
    }

    public void swap(DefaultTile firstTile, DefaultTile secondTile){
        Point firstPoint = getPosition(firstTile);
        Point secondPoint = getPosition(secondTile);

        swapTile(firstTile, firstPoint, secondTile, secondPoint);

//        List<Point> matchedTilesPositions = getMatchedTilesPositions();
//        if(!matchedTilesPositions.isEmpty()){
//            int points = calcPoints(matchedTilesPositions);
//            removeMatchedTiles(matchedTilesPositions);
//            // TODO: Add score to current player
//        }else{
//            swapTile(firstTile, firstPoint, secondTile, secondPoint);
//        }
    }

    public void swap(Point firstPoint, Point secondPoint){
//        DefaultTile firstTile = get(firstPoint);
//        DefaultTile secondTile = get(secondPoint);
//
//        swapTile(firstTile, firstPoint, secondTile, secondPoint);
        swap(get(firstPoint), get(secondPoint));
    }

    private List<Point> getMatchedTilesPositions(){
        return null;
    }

    private void removeMatchedTiles(List<Point> matchedTilesPositions){
        for(Point point : matchedTilesPositions){
            remove(point);
        }
    }

    private void swapTile(DefaultTile firstTile, Point firstPoint, DefaultTile secondTile, Point secondPoint) {
        remove(firstPoint);
        remove(secondPoint);
        add(secondTile, firstPoint);
        add(firstTile, secondPoint);
    }

    public int calcPoints(List<Point> matchedTilesPositions) {
        return 1;
    }
}
