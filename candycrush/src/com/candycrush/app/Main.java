package com.candycrush.app;

import com.candycrush.app.tile.TileType;
import com.tmge.app.tile.DefaultTile;

import java.awt.*;
import java.util.List;

/*
    This class is only intended to use for testing the SquareTilesCollection class.
    Needs to delete this class once we are able to visualize the board.
 */
public class Main {
    private static final int WIDTH = 4;
    private static final int HEIGHT = 6;

    public static void main(String[] args){
        SquareTilesCollection squareTilesCollection = new SquareTilesCollection(WIDTH, HEIGHT);
        List<List<DefaultTile>> tiles = squareTilesCollection.getTiles();
        displayBoard(tiles);

        squareTilesCollection.remove(new Point(0, 0));
        squareTilesCollection.add(new DefaultTile(TileType.CANDY), 0);

        System.out.println("Board After Remove");
        displayBoard(tiles);

        squareTilesCollection.swap(new Point(0, 0), new Point(2, 0));

        System.out.println("Board After Swap");
        displayBoard(tiles);
    }

    public static void displayBoard(List<List<DefaultTile>> tiles){
        // Displays the board with numbers
        for(int rowIndex = HEIGHT - 1; rowIndex >= 0; rowIndex--){
            for(int colIndex = 0; colIndex < WIDTH; colIndex++){
                int intDisplay = 0;
                String name = tiles.get(colIndex).get(rowIndex).getTileTypeInfo().getName();
                switch(name) {
                    case "Crystal":
                        intDisplay = 1;
                        break;
                    case "Coin":
                        intDisplay = 2;
                        break;
                    case "Candy":
                        intDisplay = 3;
                        break;
                }
                System.out.format("%d ", intDisplay);
            }
            System.out.println();
        }
    }
}
