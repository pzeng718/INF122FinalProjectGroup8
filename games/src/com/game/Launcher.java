package com.game;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author vladshaternik on 3/7/21
 */
@Getter
public class Launcher extends Application {

    public static void main(String[] args) {
        Application.launch(Launcher.class);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane parent = new BorderPane();
        ListView<Game> listView = new ListView<>();
        listView.getItems().addAll(Game.values());
        listView.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() > 1) {
                Game selectedItem = listView.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    try {
                        launchGame(selectedItem.getLauncherClass());
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        VBox content = new VBox(10);
        content.setAlignment(Pos.CENTER);
        content.getChildren().addAll(new Label("(Double-click desired game)"), parent);
        parent.setCenter(listView);
        primaryStage.setScene(new Scene(content, 200, 300));
        primaryStage.show();
    }

    public void launchGame(Class<?> klass) throws IOException, InterruptedException {
        String javaHome = System.getProperty("java.home");
        String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
        String modulePath = System.getProperty("jdk.module.path");
        String module = klass.getModule().getName() + "/" + klass.getName();

        List<String> command = new LinkedList<>();
        command.add(javaBin);
        command.add("-p");
        command.add(modulePath);
        command.add("-m");
        command.add(module);

        ProcessBuilder builder = new ProcessBuilder(command);
        builder.inheritIO().start();
    }
}
