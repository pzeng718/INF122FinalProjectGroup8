package com.candycrush.app;

import com.candycrush.app.tile.TileType;
import com.tmge.app.collection.SwapResponse;
import com.tmge.app.collection.impl.AbstractDefaultTilesCollection;
import com.tmge.app.tile.DefaultTile;
import com.tmge.app.tile.TileTypeInfo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

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

    @Override
    protected SwapResponse calcPoints(Set<DefaultTile> matchedTiles)  {
        int basePoint = 20;
        int numberOfMatchedTiles = matchedTiles.size();
        // More matched tiles will give a higher multiplier, for example:
        // multiplier for 4 matched tiles is 1.1, multiplier for 6 matched tiles is 1.3
        double multiplier = 1 + (numberOfMatchedTiles - 3) * 0.1;

        return new SwapResponseImpl((int) (basePoint * numberOfMatchedTiles * multiplier));
    }
}
