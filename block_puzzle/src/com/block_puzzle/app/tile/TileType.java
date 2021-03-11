package com.block_puzzle.app.tile;

import com.tmge.app.tile.TileTypeInfo;
import lombok.Getter;
import lombok.extern.java.Log;

/**
 * @author vladshaternik on 2/27/21
 */
@Getter
@Log
public enum TileType implements TileTypeInfo {

    DIAMOND("diamond-pictures/diamond1.png", "Diamond"),
    CROWN("crown-pictures/crown3.png", "Crown"),
    EGG("egg-pictures/egg2.png", "Egg"),
    COIN("coin-pictures/coin1.png", "Coin")
    ;

    private final String src;
    private final String name;

    TileType(String src, String name) {
        this.src = src;
        this.name = name;
    }
}
