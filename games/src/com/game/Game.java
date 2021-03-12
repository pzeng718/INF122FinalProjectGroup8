package com.game;

import lombok.Getter;

/**
 * @author vladshaternik on 3/7/21
 */
@Getter
public enum Game {
    MATCHINGTON("Matchington", com.matchington.Launcher.class),
    BLOCK_PUZZLE("Block Puzzle", com.block_puzzle.Launcher.class),
    ;

    private final String name;
    private final Class<?> launcherClass;

    Game(String name, Class<?> launcherClass) {
        this.name = name;
        this.launcherClass = launcherClass;
    }
}
