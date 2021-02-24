package com.tmge.ui.components;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.Getter;

import java.util.Optional;

@Getter
public class TextOnlyDialog extends AbstractDialog<Void> {

    private final String titleString;
    private final Label titleLabel;
    private final Label contentLabel;

    public TextOnlyDialog(String title, String content, Window window) {
        super(window);
        this.titleString = title;
        this.titleLabel = UIComponents.createTitleLabel(title);
        this.contentLabel = UIComponents.createLabel(content);
    }

    @Override
    protected Optional<Void> buildResult() {
        return Optional.empty();
    }

    @Override
    protected String getButtonOkString() {
        return "";
    }

    @Override
    protected String getButtonCancelString() {
        return "Close";
    }

    @Override
    protected Node buildContent() {
        VBox content = new VBox(5);
        getContentLabel().getStyleClass().add(StyleClass.MESSAGE_LABEL);
        content.getChildren().addAll(getContentLabel());
        return content;
    }

}
