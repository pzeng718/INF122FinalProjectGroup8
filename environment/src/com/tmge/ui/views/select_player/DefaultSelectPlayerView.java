package com.tmge.ui.views.select_player;

import com.tmge.app.player.DefaultPlayer;
import com.tmge.ui.components.UIComponents;
import com.tmge.ui.views.select_player.dialog.NewPlayerDialog;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lombok.Getter;

import java.util.List;

/**
 * @author vladshaternik on 2/21/21
 */
@Getter
public class DefaultSelectPlayerView extends VBox {

    private final ListView<DefaultPlayer> playersListView;
    private final Button addPlayerButton;
    private final Button selectButton;

    public DefaultSelectPlayerView(List<DefaultPlayer> players) {
        this.playersListView = new ListView<>(FXCollections.observableArrayList(players));
        this.addPlayerButton = UIComponents.createInfoButton("Add User");
        this.selectButton = UIComponents.createSuccessButton("Select");
    }

    public DefaultSelectPlayerView init() {
        setAlignment(Pos.CENTER);
        getAddPlayerButton().setOnAction(actionEvent ->
                new NewPlayerDialog(getScene().getWindow()).init().showAndWait().ifPresent(player -> {
                    getPlayersListView().getItems().add(player);
                    onNewPlayerAdded(player);
                }));
        getSelectButton().setOnAction(actionEvent -> {
            DefaultPlayer selectedItem = playersListView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                onPlayerSelected(selectedItem);
            } else {
                UIComponents.showTextOnlyDialog("Please Choose Player!", getScene().getWindow());
            }
        });
        getPlayersListView().setMaxWidth(400);
        VBox content = new VBox(10);
        content.setAlignment(Pos.CENTER);
        HBox buttonsContainer = new HBox(10);
        buttonsContainer.getChildren().addAll(getAddPlayerButton(), getSelectButton());
        buttonsContainer.prefWidthProperty().bind(getPlayersListView().widthProperty());
        buttonsContainer.setAlignment(Pos.CENTER);
        content.getChildren().addAll(UIComponents.createTitleLabel("Choose Player:"), getPlayersListView(), buttonsContainer);
        getChildren().add(content);
        return this;
    }

    protected void onNewPlayerAdded(DefaultPlayer player) {
    }

    protected void onPlayerSelected(DefaultPlayer player) {
    }
}
