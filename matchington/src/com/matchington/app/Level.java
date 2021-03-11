package com.matchington.app;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter
public enum Level {
    ONE (1, 1500, 20, 8, 4),
    TWO (2, 2000, 15, 8, 6),
    THREE (3, 2500, 10, 8, 8);

    private final int id;
    private final int objectiveScore; // The score player needs to reach to complete this level
    private final int objectiveMoves;
    private final int height;
    private final int width;

    Level(int id, int objectiveScore, int objectiveMoves, int height, int width) {
        this.id = id;
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
