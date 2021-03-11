package com.tmge.app.tile;

import javafx.scene.image.Image;

import java.io.IOException;

public interface TileTypeInfo {
    String getSrc();

    String getName();

    default Image getImage() {
        try {
            return new Image(ClassLoader.getSystemResource(getSrc()).openStream());
        } catch (IOException e) {
            System.out.println("Unable to load image resource");
        }
        return null;
    }
}
