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
public class NewPlayerDialog extends AbstractDialog<DefaultPlayer> {

    private final TextField usernameTextField;

    public NewPlayerDialog(Window window) {
        super(window);
        this.usernameTextField = new TextField();
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
    protected Optional<DefaultPlayer> buildResult() {
        return Optional.of(DefaultPlayer.builder()
                .username(getUsernameTextField().getText())
                .build());
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
