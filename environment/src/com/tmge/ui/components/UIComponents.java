package com.tmge.ui.components;

import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vladshaternik on 2/24/21
 */
public class UIComponents {

    public static Label createLabel(String text) {
        return new Label(text);
    }

    public static Label createTitleLabel(String text) {
        Label label = createLabel(text);
        label.getStyleClass().add(StyleClass.TITLE_LABEL);
        return label;
    }

    public static Pane createLabelWithNodePane(String title, Node node, Orientation orientation) {
        Pane pane;
        if (orientation == Orientation.VERTICAL) {
            VBox box = new VBox(5);
            box.setAlignment(Pos.CENTER_LEFT);
            pane = box;
        } else {
            HBox box = new HBox(5);
            box.setAlignment(Pos.CENTER_LEFT);
            pane = box;
        }
        pane.getChildren().addAll(new Label(title), node);
        return pane;
    }

    public static void showTextOnlyDialog(String text, Window window) {
        showTextOnlyDialog("", text, window);
    }

    public static void showTextOnlyDialog(String title, String text, Window window) {
        Platform.runLater(() -> new TextOnlyDialog(title, text, window).init().showAndWait());
    }

    public static Button createInfoButton(String text) {
        Button button = new Button(text);
        button.getStyleClass().addAll(StyleClass.BUTTON, StyleClass.INFO_BUTTON);
        return button;
    }

    public static Button createSuccessButton(String text) {
        Button button = new Button(text);
        button.getStyleClass().addAll(StyleClass.BUTTON, StyleClass.SUCCESS_BUTTON);
        return button;
    }

    public static Button createDangerButton(String text) {
        Button button = new Button(text);
        button.getStyleClass().addAll(StyleClass.BUTTON, StyleClass.DANGER_BUTTON);
        return button;
    }

    public static ButtonType[] buildDialog(Dialog<?> dialog, Window owner, Node content, String buttonOk, String buttonCancel) {
        dialog.initModality(Modality.NONE);
        dialog.initOwner(owner);
        dialog.getDialogPane().setContent(content);
        List<ButtonType> buttonTypeList = new ArrayList<>();
        if (buttonOk != null && !buttonOk.isEmpty()) {
            ButtonType buttonTypeCreate = new ButtonType(buttonOk, ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().add(buttonTypeCreate);
            final Button okButton = (Button) dialog.getDialogPane().lookupButton(buttonTypeCreate);
            okButton.getStyleClass().addAll(StyleClass.BUTTON, StyleClass.INFO_BUTTON);
            buttonTypeList.add(buttonTypeCreate);
        }
        ButtonType buttonTypeCancel = new ButtonType(buttonCancel, ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeCancel);
        final Button cancelButton = (Button) dialog.getDialogPane().lookupButton(buttonTypeCancel);
        cancelButton.getStyleClass().addAll(StyleClass.BUTTON, StyleClass.DANGER_BUTTON);
        buttonTypeList.add(buttonTypeCancel);
        return buttonTypeList.toArray(new ButtonType[0]);
    }
}
