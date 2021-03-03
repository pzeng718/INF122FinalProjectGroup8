package com.tmge.ui.components;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;
import java.util.concurrent.Future;

@Setter(AccessLevel.PRIVATE)
@Getter(AccessLevel.PROTECTED)
public abstract class AbstractDialog<R> extends Dialog<R> {

    private ButtonType[] buttonTypes;
    @Getter(AccessLevel.PRIVATE)
    private Future<?> delayFuture;

    public AbstractDialog(Window window) {
        super();
        initOwner(window);
    }

    public AbstractDialog<R> init() {
        initModality(Modality.APPLICATION_MODAL);
        setTitle(getTitleString());
        Node content = buildContent();
        String okButtonString = getButtonOkString();
        String cancelButtonString = getButtonCancelString();
        ButtonType[] buttonTypes = UIComponents.buildDialog(this, getOwner(), content, okButtonString, cancelButtonString);
        setButtonTypes(buttonTypes);
        int buttonIndexCounter = 0;
        if (okButtonString != null && !okButtonString.isEmpty()) {
            Button okButton = (Button) getDialogPane().lookupButton(buttonTypes[buttonIndexCounter++]);
            if (okButton != null) {
                okButton.addEventFilter(ActionEvent.ACTION, this::addOkButtonFilter);
            }
        }
        if (cancelButtonString != null && !cancelButtonString.isEmpty()) {
            Button cancelButton = (Button) getDialogPane().lookupButton(buttonTypes[buttonIndexCounter]);
            if (cancelButton != null) {
                cancelButton.addEventFilter(ActionEvent.ACTION, this::addCancelButtonFilter);
            }
        }
        setResultConverter(param -> {
            if (getDelayFuture() != null) {
                getDelayFuture().cancel(false);
            }
            return buttonTypes.length > 1 && buttonTypes[0] == param ? buildResult().orElse(null) : null;
        });
        return this;
    }

    protected abstract Optional<R> buildResult();

    protected abstract Node buildContent();

    protected abstract String getTitleString();

    protected String getButtonOkString() {
        return "Ok";
    }

    protected String getButtonCancelString() {
        return "Cancel";
    }

    protected void addOkButtonFilter(ActionEvent event) {
    }

    protected void addCancelButtonFilter(ActionEvent event) {
    }

    protected VBox createVBoxForNodes(Node... nodes) {
        return createVBoxForNodes(10, nodes);
    }

    protected VBox createVBoxForNodes(int spacing, Node... nodes) {
        VBox box = new VBox(spacing);
        for (Node node : nodes) {
            box.getChildren().add(node);
        }
        return box;
    }
}
