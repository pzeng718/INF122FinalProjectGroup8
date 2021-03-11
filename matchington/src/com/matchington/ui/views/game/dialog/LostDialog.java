package com.matchington.ui.views.game.dialog;

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
public class LostDialog extends AbstractDialog<Boolean> {


    public LostDialog(Window window) {
        super(window);
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
        return createVBoxForNodes(UIComponents.createTitleLabel("Lost! Out of moves."));
    }

    @Override
    protected String getTitleString() {
        return "Lost!";
    }
}
