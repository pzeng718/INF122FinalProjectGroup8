package com.game;

import lombok.Getter;

/**
 * @author vladshaternik on 3/7/21
 */
@Getter
public enum Game {
    CANDY_CRUSH("Candy Crush", com.candycrush.Launcher.class),
    BEJEWELED("Bejeweled", com.bejeweled.Launcher.class),
    ;

    private final String name;
    private final Class<?> launcherClass;

    Game(String name, Class<?> launcherClass) {
        this.name = name;
        this.launcherClass = launcherClass;
    }
}
