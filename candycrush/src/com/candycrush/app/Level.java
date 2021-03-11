package com.candycrush.app;
import com.candycrush.app.tile.TileType;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter
public enum Level {
    ONE (1, TileType.DIAMOND, 1500, 20, 8, 4),
    TWO (2, TileType.CRYSTAL, 2000, 15, 8, 6),
    THREE (3, TileType.COIN, 2500, 10, 8, 8);

    private final int id;
    private final TileType objectiveTileType;
    private final int objectiveScore; // The score player needs to reach to complete this level
    private final int objectiveMoves;
    private final int height;
    private final int width;

    Level(int id, TileType objectiveTileType, int objectiveScore, int objectiveMoves, int height, int width) {
        this.id = id;
        this.objectiveTileType = objectiveTileType;
        this.objectiveScore = objectiveScore;
        this.objectiveMoves = objectiveMoves;
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
