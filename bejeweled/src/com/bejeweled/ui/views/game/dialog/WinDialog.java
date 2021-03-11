package com.bejeweled.ui.views.game.dialog;

import com.bejeweled.app.player.Player;
import com.tmge.ui.components.AbstractDialog;
import com.tmge.ui.components.UIComponents;
import javafx.scene.Node;
import javafx.stage.Window;
import lombok.Getter;

import java.util.Optional;

/**
 * @author vladshaternik on 3/2/21
 */
@Getter
public class WinDialog extends AbstractDialog<Boolean> {

    private final Player player;

    public WinDialog(Player player, Window window) {
        super(window);
        this.player = player;
    }

    @Override
    protected Optional<Boolean> buildResult() {
        return Optional.of(true);
    }

    @Override
    protected String getButtonOkString() {
        return "Main Menu";
    }

    @Override
    protected String getButtonCancelString() {
        return "";
    }

    @Override
    protected Node buildContent() {
        return createVBoxForNodes(UIComponents.createTitleLabel(getPlayer().getUsername() + " Wins!"));
    }

    @Override
    protected String getTitleString() {
        return getPlayer().getUsername() + " Wins!";
    }
}
