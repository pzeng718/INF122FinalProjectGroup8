package com.tmge.app.tile;

import lombok.Getter;

@Getter
public class DefaultTile {
    public TileTypeInfo tileTypeInfo;

    public DefaultTile(TileTypeInfo tileTypeInfo) {
        this.tileTypeInfo = tileTypeInfo;
    }
}
