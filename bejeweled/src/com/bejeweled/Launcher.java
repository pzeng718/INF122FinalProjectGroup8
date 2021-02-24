package com.bejeweled;

/**
 * @author vladshaternik on 2/21/21
 */
public class Launcher {

    public static void main(String... args) {
        AppSettings settings = new AppSettings();
        Game.start(settings);
    }
}
