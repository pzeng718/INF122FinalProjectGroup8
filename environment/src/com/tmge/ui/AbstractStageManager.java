package com.tmge.ui;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;

/**
 * @author vladshaternik on 2/21/21
 */
@Getter
@Setter(AccessLevel.PRIVATE)
public abstract class AbstractStageManager extends Application {

    private Stage stage;

    @Override
    public void start(Stage primaryStage) {
        setStage(primaryStage);
        loadView(firstView());
        getStage().show();
    }

    protected abstract Parent firstView();

    public void loadView(Parent parent) {
        getStage().setScene(new Scene(parent, 1200, 800));
        getStage().getScene().getStylesheets().add(ClassLoader.getSystemResource("dark.css").toExternalForm());
    }
}
