package com.candycrush.app.tile;

import com.tmge.app.tile.TileTypeInfo;
import javafx.scene.image.Image;
import lombok.Getter;

/**
 * @author vladshaternik on 2/27/21
 */
@Getter
public enum TileType implements TileTypeInfo {
    DIAMOND("/resources/diamond.png", "Diamond"),
    CRYSTAL("/resources/crystal.png", "Crystal"),
    COIN("/resources/coin.png", "Coin"),
    CANDY("/resources/candy.png", "Candy")
    ;

    private final String src;
    private final String name;

    TileType(String src, String name) {
        this.src = src;
        this.name = name;
    }

    @Override
    public Image getImage() {
        //TODO - implement
        return new Image("");
    }
}
