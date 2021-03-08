package com.bejeweled.app;

import com.bejeweled.app.tile.TileType;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter
public enum Level {
    ONE(1,8, 10, 5),
    TWO(2,6, 15, 15),
    THREE(3,4, 10, 10);

    private final int id;
    private final int tilesLeft;
    private final int height;
    private final int width;

    Level(int id, int tilesLeft, int height, int width) {
        this.id = id;
        this.tilesLeft = tilesLeft;
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
