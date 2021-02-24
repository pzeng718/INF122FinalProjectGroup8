package com.tmge.ui.views.menu;

import com.tmge.ui.components.UIComponents;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lombok.AccessLevel;
import lombok.Getter;

/**
 * @author vladshaternik on 2/21/21
 */
@Getter
public class DefaultMenuView extends VBox {

    private final VBox menuItems = new VBox();

    public DefaultMenuView init() {
        setAlignment(Pos.CENTER);
        getMenuItems().setAlignment(Pos.CENTER);
        getMenuItems().setSpacing(10);
        getChildren().addAll(getMenuItems());
        return this;
    }

    public void addMenuItem(String title, Runnable action) {
        Label menuItem = createMenuItem();
        menuItem.setText(title);
        menuItem.setOnMouseClicked(mouseEvent -> action.run());
        getMenuItems().getChildren().add(menuItem);
    }

    protected Label createMenuItem() {
        Label label = UIComponents.createLabel("");
        label.setPadding(new Insets(10));
        label.setStyle("-fx-background-color: orange; -fx-font-size: 24px;");
        return label;
    }
}
