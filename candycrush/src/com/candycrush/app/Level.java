package com.candycrush.app;
import com.candycrush.app.tile.TileType;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter
public enum Level {
    ONE (1, TileType.DIAMOND, 10, 20, 10, 5),
    TWO (2, TileType.CRYSTAL, 15, 15, 15, 15),
    THREE (3, TileType.COIN, 20, 10, 10, 10);

    private final int id;
    private final TileType objectiveTileType;
    private final int objectiveQuantity;
    private final int turns;
    private final int height;
    private final int width;

    Level(int id, TileType objectiveTileType, int objectiveQuantity, int turns, int height, int width) {
        this.id = id;
        this.objectiveTileType = objectiveTileType;
        this.objectiveQuantity = objectiveQuantity;
        this.turns = turns;
        this.height = height;
        this.width = width;
    }

    public static List<Level> getLevelsBelowOrEqualToId(int id) {
        List<Level> levels = new ArrayList<>();
        for (Level value : values()) {
            if (value.getId() <= id) {
                levels.add(value);
            }
        }
        return levels;
    }

    public static Optional<Level> findLevelById(int id) {
        return Arrays.stream(values()).filter(l -> l.getId() == id).findFirst();
    }
}
