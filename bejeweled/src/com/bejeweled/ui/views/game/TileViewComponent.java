package com.bejeweled.ui.views.game;

import com.tmge.app.tile.DefaultTile;
import com.tmge.ui.components.StyleClass;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import lombok.Getter;


/**
 * @author vladshaternik on 2/28/21
 */
@Getter
public class TileViewComponent extends VBox {

    private final ImageView imageView;
    private final DefaultTile tile;

    public TileViewComponent(Image image, DefaultTile tile) {
        this.imageView = new ImageView(image);
        this.tile = tile;
    }

    public TileViewComponent init() {
        setAlignment(Pos.CENTER);
        getStyleClass().add(StyleClass.TILE);
        setPrefWidth(50);
        setPrefHeight(50);
        getImageView().setFitWidth(50);
        getImageView().setFitHeight(50);
        getImageView().setPreserveRatio(true);
        getChildren().add(getImageView());
        return this;
    }
}
