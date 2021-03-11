package com.tmge.ui.views.select_player;

import com.tmge.app.player.DefaultPlayer;
import com.tmge.ui.components.UIComponents;
import com.tmge.ui.views.select_player.dialog.NewPlayerDialog;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;

import java.util.List;

/**
 * @author vladshaternik on 2/21/21
 */
@Getter
public abstract class AbstractSelectPlayerView<T extends DefaultPlayer> extends VBox {

    private final ListView<T> playersListView;
    private final Button addPlayerButton;
    private final Button selectButton;

    public AbstractSelectPlayerView(List<T> players) {
        this.playersListView = new ListView<>(FXCollections.observableArrayList(players));
        this.addPlayerButton = UIComponents.createInfoButton("Add User");
        this.selectButton = UIComponents.createSuccessButton("Select");
    }

    public AbstractSelectPlayerView<T> init() {
        setAlignment(Pos.CENTER);
        getAddPlayerButton().setOnAction(actionEvent ->
                new NewPlayerDialog<T>(createPlayer(), getScene().getWindow()).init().showAndWait().ifPresent(player -> {
                    getPlayersListView().getItems().add(player);
                    onNewPlayerAdded(player);
                }));
        getSelectButton().setOnAction(actionEvent -> {
            T selectedItem = getPlayersListView().getSelectionModel().getSelectedItem();
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

    protected abstract T createPlayer();

    protected void onNewPlayerAdded(T player) {
    }

    protected void onPlayerSelected(T player) {
    }
}
