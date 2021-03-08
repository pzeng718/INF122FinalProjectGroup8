package com.bejeweled.app.tile;

import com.tmge.app.tile.TileTypeInfo;
import javafx.scene.image.Image;
import lombok.Getter;
import lombok.extern.java.Log;

import java.io.IOException;

/**
 * @author vladshaternik on 2/27/21
 */
@Getter
@Log
public enum TileType implements TileTypeInfo {
    DIAMOND("diamond.png", "Diamond"),
    CRYSTAL("crystal.png", "Crystal"),
    COIN("coin.png", "Coin"),
    CANDY("candy.png", "Candy")
    ;

    private final String src;
    private final String name;

    TileType(String src, String name) {
        this.src = src;
        this.name = name;
    }

    @Override
    public Image getImage() {
        try {
            return new Image(ClassLoader.getSystemResource(getSrc()).openStream());
        } catch (IOException e) {
            log.info("Unable to load image resource");
        }
        return null;
    }
}
