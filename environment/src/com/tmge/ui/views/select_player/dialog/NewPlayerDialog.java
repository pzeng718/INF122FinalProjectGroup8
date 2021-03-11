package com.tmge.ui.views.select_player.dialog;

import com.tmge.app.player.DefaultPlayer;
import com.tmge.ui.components.AbstractDialog;
import com.tmge.ui.components.UIComponents;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import lombok.Getter;

import java.util.Optional;

/**
 * @author vladshaternik on 2/24/21
 */
@Getter
public class NewPlayerDialog<T extends DefaultPlayer> extends AbstractDialog<T> {

    private final TextField usernameTextField;
    private final T player;

    public NewPlayerDialog(T player, Window window) {
        super(window);
        this.usernameTextField = new TextField();
        this.player = player;
    }

    @Override
    protected void addOkButtonFilter(ActionEvent event) {
        String username = getUsernameTextField().getText();
        if (username == null || username.isEmpty()) {
            UIComponents.showTextOnlyDialog("Please enter a valid Username!", getDialogPane().getScene().getWindow());
            event.consume();
        }
    }

    @Override
    protected Optional<T> buildResult() {
        getPlayer().setUsername(getUsernameTextField().getText());
        return Optional.of(getPlayer());
    }

    @Override
    protected Node buildContent() {
        return createVBoxForNodes(UIComponents.createLabelWithNodePane("Please enter username:", getUsernameTextField(), Orientation.VERTICAL));
    }

    @Override
    protected String getTitleString() {
        return "New User";
    }
}
